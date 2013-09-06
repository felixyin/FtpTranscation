package com.fy.ftp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import com.fy.ftp.FtpConstant;
import com.fy.ftp.LoginBean;

public class FtpUtil {

	public static FTPClient login(String server, int port, String username, String password) throws Exception {
		FTPClient ftpClient = new FTPClient();// ftpClient不能共享
		ftpClient = new FTPClient();
		ftpClient.connect(server, port);

		//TODO 中文文件名或路径处理
		ftpClient.setControlEncoding("GBK");
		FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_NT);
		config.setServerLanguageCode("zh");

		boolean lb = ftpClient.login(username, password);
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			ftpClient.disconnect();
			throw new Exception("未连接到FTP，用户名或密码错误。");
		}
		if (lb) {
			return ftpClient;
		}
		throw new Exception("登录ftp失败，请检查连接参数");
	}

	/**
	 * 
	 * @param linkBean
	 * @return
	 * @throws Exception
	 */
	public static FTPClient login(LoginBean linkBean) throws Exception {
		FTPClient ftpClient = new FTPClient();// ftpClient不能共享
		ftpClient.connect(linkBean.getServer(), linkBean.getPort());
		boolean lb = ftpClient.login(linkBean.getUsername(), linkBean.getPassword());
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			ftpClient.disconnect();
			throw new Exception("未连接到FTP，用户名或密码错误。");
		}
		if (lb) {
			return ftpClient;
		}
		throw new Exception("登录ftp失败，请检查连接参数");
	}

	// 增删改查方法
	/**
	 * 上传ftp服务器
	 * 
	 * @param local
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	public static boolean upload(FTPClient ftpClient, String local, String remote) throws Exception {
		InputStream input = new FileInputStream(local);
		return upload(ftpClient, input, remote);
	}

	public static boolean upload(FTPClient ftpClient, InputStream input, String remote) throws Exception {
		try {
			boolean b1 = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			boolean b2 = ftpClient.storeFile(remote, input);
			return b1 && b2;
		} finally {
			if (null != input) {
				input.close();
			}
		}
	}

	/**
	 * 文件 在ftp服务器上是否存在
	 * 
	 * @param fileRemote
	 * @return
	 * @throws Exception
	 */
	public static boolean isFileExist(FTPClient ftpClient, String fileRemote) throws Exception {
		return ftpClient.getStatus(fileRemote) == null ? false : true;
	}

	/**
	 * 下载ftp服务器上的文件到本地
	 * 
	 * @param remote
	 * @param local
	 * @return
	 * @throws Exception
	 */
	public static boolean download(FTPClient ftpClient, String remote, String local) throws Exception {
		OutputStream output = new FileOutputStream(local);
		try {
			boolean b1 = ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			boolean b2 = ftpClient.retrieveFile(remote, output);
			return b1 && b2;
		} finally {
			if (null != output) {
				output.close();
			}
		}
	}

	/**
	 * 下载ftp服务器上的文件到本地
	 * 
	 * @param remote
	 * @param local
	 * @return
	 * @throws Exception
	 */
	public static InputStream download(FTPClient ftpClient, String remote) throws Exception {
		InputStream inputStream = null;
		// try {
		inputStream = ftpClient.retrieveFileStream(remote);
		// } finally {
		// if (null != inputStream) {
		// inputStream.close();
		// }
		// }
		return inputStream;
	}

	/**
	 * copy ftp服务器上的 文件 to ftp服务器上的另一个路径
	 * 
	 * @param sourceRemote
	 * @param targetRemote
	 * @return
	 * @throws Exception
	 */
	public static boolean copy(FTPClient ftpClient, String sourceRemote, String targetRemote) throws Exception {
		// InputStream input = download(ftpClient, sourceRemote);
		// boolean ub = upload(ftpClient, input, targetRemote);
		// return ub;
		String tempPath = FtpUtil.class.getResource("").getPath() + sourceRemote.replace("/", "");
		boolean db = download(ftpClient, sourceRemote, tempPath);
		if (db) {
			boolean ub = upload(ftpClient, tempPath, targetRemote);
			if (ub) {
				File tempFile = new File(tempPath);
				if (tempFile.exists()) {
					tempFile.delete();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 删除 ftp服务器上的文件
	 * 
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	public static boolean delete(FTPClient ftpClient, String remote) throws Exception {
		return ftpClient.deleteFile(remote);
	}

	/**
	 * 重命名 ftp服务器上的文件，或者移动某个文件到另一个目录下
	 * 
	 * @param sourceRemote
	 * @param targetRemote
	 * @return
	 * @throws Exception
	 */
	public static boolean rename(FTPClient ftpClient, String sourceRemote, String targetRemote) throws Exception {
		return ftpClient.rename(sourceRemote, targetRemote);
	}

	/**
	 * 在ftp服务器上创建目录
	 * 
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	public static boolean mkdir(FTPClient ftpClient, String remote) throws Exception {
		String[] folders = remote.split(FtpConstant.sep);
		for (int i = 0; i < folders.length; i++) {
			String folder = folders[i];
			if ("".equals(folder)) {
				continue;
			}
			if (!ftpClient.changeWorkingDirectory(folder)) {
				boolean mb = ftpClient.makeDirectory(folder);
				if (!mb) {
					return false;
				} else {
					ftpClient.changeWorkingDirectory(folder);
				}
			}
		}
		return true;
	}

	private static boolean diGui(FTPClient ftpClient, String pathname) throws Exception {
		if (!pathname.startsWith(FtpConstant.sep)) {
			pathname = FtpConstant.sep + pathname;
		}
		if (pathname.endsWith(FtpConstant.sep)) {
			pathname = pathname.substring(0, pathname.length() - 1);
		}
		int sepIndex = pathname.lastIndexOf(FtpConstant.sep);
		if (sepIndex != -1) {
			String workDir = pathname.substring(0, sepIndex);
			boolean cb = ftpClient.changeWorkingDirectory(workDir);
			if (cb) {
				String deleDir = pathname.substring(1 + sepIndex);
				boolean rb = ftpClient.removeDirectory(deleDir);
				if (rb) {
					diGui(ftpClient, workDir);
					return true;
				}
			}
		}
		return false;
	}

	public static boolean rmdir(FTPClient ftpClient, String pathname) throws Exception {
		return diGui(ftpClient, pathname);
	}

	public static boolean listFile(FTPClient ftpClient, String folderRemote) throws Exception {
		return false;
	}

	public static boolean logout(FTPClient ftpClient) throws Exception {
		boolean b = ftpClient.logout();
		ftpClient.disconnect();
		return b;
	}

}
