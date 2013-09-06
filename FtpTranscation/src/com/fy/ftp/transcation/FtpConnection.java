package com.fy.ftp.transcation;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import com.fy.ftp.FtpConstant;
import com.fy.ftp.FtpHistory;
import com.fy.ftp.util.FtpUtil;

/**
 * @author yinbin
 * 
 */
public class FtpConnection {

	private FTPClient ftpClient;

	// 登录注销封装

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public FtpConnection() {
	}

	public FtpConnection(String server, int port, String username,
			String password) throws Exception {
		if (!login(server, port, username, password)) {
			throw new Exception("无法登录ftp服务器");
		}
	}

	public boolean login(String server, int port, String username,
			String password) throws Exception {
		ftpClient = FtpUtil.login(server, port, username, password);
		ftpHistory.setFtpClient(ftpClient);
		return true;
	}

	public boolean logout() throws Exception {
		return FtpUtil.logout(ftpClient);
	}

	// 增删改查方法
	/**
	 * 
	 * @param local
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	public boolean upload(String local, String remote) throws Exception {
		return upload(null, local, remote);
	}

	/**
	 * 
	 * @param local
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	public boolean upload(InputStream local, String remote) throws Exception {
		return upload(local, null, remote);
	}

	/**
	 * 
	 * @param local
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	private boolean upload(InputStream localInputStream, String localString,
			String remote) throws Exception {
		remote=new String(remote.getBytes("UTF-8"),"ISO-8859-1");
		String folder = remote
				.substring(0, remote.lastIndexOf(FtpConstant.sep));
		boolean b = FtpUtil.mkdir(ftpClient, folder);
		if (b) {
			ftpHistory.add("mkdir", folder, null);
		} else {
			throw new Exception("在ftp上创建文件夹失败");
		}
		if (isOpenTranscation()) {
			if (FtpUtil.isFileExist(ftpClient, remote)) {
				boolean rb = FtpUtil.rename(ftpClient, remote,
						getBackRemote(remote));
				if (rb) {
					ftpHistory.add("update", remote, getBackRemote(remote));
				}
			}
		}
		boolean ub = false;
		if (null != localString) {
			ub = FtpUtil.upload(ftpClient, localString, remote);
		} else if (null != localInputStream) {
			ub = FtpUtil.upload(ftpClient, localInputStream, remote);
		}
		if (ub && isOpenTranscation()) {
			ftpHistory.add("upload", null, remote);
		}
		return ub;
	}

	public boolean download(String remote, String local) throws Exception {
		remote=new String(remote.getBytes("UTF-8"),"ISO-8859-1");
		boolean ib = FtpUtil.isFileExist(ftpClient, remote);
		if (!ib) {
			return ib;
		}
		boolean b = FtpUtil.download(ftpClient, remote, local);
		if (b && isOpenTranscation()) {
			ftpHistory.add("download", remote, local);
		}
		return b;
	}

	public InputStream download(String remote) throws Exception {
		remote=new String(remote.getBytes("UTF-8"),"ISO-8859-1");
		boolean ib = FtpUtil.isFileExist(ftpClient, remote);
		if (!ib) {
			return null;
		}
		InputStream inputStream = FtpUtil.download(ftpClient, remote);
		if (inputStream.available() == 0) {
			return null;
		}
		return inputStream;
	}

	public boolean copy(String sourceRemote, String targetRemote)
			throws Exception {
		sourceRemote=new String(sourceRemote.getBytes("UTF-8"),"ISO-8859-1");
		targetRemote=new String(targetRemote.getBytes("UTF-8"),"ISO-8859-1");
		boolean b = FtpUtil.copy(ftpClient, sourceRemote, targetRemote);
		if (b && isOpenTranscation()) {
			ftpHistory.add("copy", sourceRemote, targetRemote);
		}
		return b;
	}

	public boolean delete(String remote) throws Exception {
		remote=new String(remote.getBytes("UTF-8"),"ISO-8859-1");
		if (isOpenTranscation()) {
			boolean db = FtpUtil.rename(ftpClient, remote,
					getBackRemote(remote));
			if (db) {
				ftpHistory.add("delete", remote, getBackRemote(remote));
			}
			return db;
		}
		return FtpUtil.delete(ftpClient, remote);
	}

	public boolean rename(String sourceRemote, String targetRemote)
			throws Exception {
		sourceRemote=new String(sourceRemote.getBytes("UTF-8"),"ISO-8859-1");
		targetRemote=new String(targetRemote.getBytes("UTF-8"),"ISO-8859-1");
		boolean b = FtpUtil.rename(ftpClient, sourceRemote, targetRemote);
		if (b && isOpenTranscation()) {
			ftpHistory.add("rename", sourceRemote, targetRemote);
		}
		return b;
	}

	/**
	 * 
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	public boolean mkdir(String remote) throws Exception {
		remote=new String(remote.getBytes("UTF-8"),"ISO-8859-1");
		boolean b = FtpUtil.mkdir(ftpClient, remote);
		if (b && isOpenTranscation()) {
			ftpHistory.add("mkdir", remote, null);
		}
		return b;
	}

	public boolean listFile(String folderRemote) throws Exception {
		folderRemote=new String(folderRemote.getBytes("UTF-8"),"ISO-8859-1");
		throw new Exception("not yet!");
//		return false;
	}

	// 基础事务封装

	private boolean autoCommit = true;

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	private FtpHistory ftpHistory = new FtpHistory();

	public FtpHistory getFtpHistory() {
		return ftpHistory;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void commit() throws Exception {
		if (isOpenTranscation()) {
			System.out.println("\n\t\t\tcommit:");
			List<HistoryBean> datas = ftpHistory.getHistory();
			for (HistoryBean data : datas) {
				String type = data.getType();
				String from = data.getFrom();
				String to = data.getTo();
				System.out.println(type + ":" + from + "--->" + to);
				if ("update".equals(type)) {
					FtpUtil.delete(ftpClient, to);
				} else if ("upload".equals(type)) {
				} else if ("rename".equals(type)) {
				} else if ("delete".equals(type)) {
					FtpUtil.delete(ftpClient, to);
				} else if ("copy".equals(type)) {
				} else if ("download".equals(type)) {
				} else if ("mkdir".equals(type)) {
				}
			}
		}
	}

	public void rollback() throws Exception {
		if (isOpenTranscation()) {
			System.out.println("\n\t\t\troolback:");
			List<HistoryBean> datas = ftpHistory.getHistory();
			Collections.reverse(datas);
			for (HistoryBean data : datas) {
				String type = data.getType();
				String from = data.getFrom();
				String to = data.getTo();
				System.out.println(type + ":" + from + "<---" + to);
				if ("update".equals(type)) {
					FtpUtil.rename(ftpClient, to, from);
				} else if ("upload".equals(type)) {
					FtpUtil.delete(ftpClient, to);
				} else if ("rename".equals(type)) {
					FtpUtil.rename(ftpClient, to, from);
				} else if ("delete".equals(type)) {
					FtpUtil.rename(ftpClient, to, from);
				} else if ("copy".equals(type)) {
					FtpUtil.delete(ftpClient, to);
				} else if ("download".equals(type)) {
					File local = new File(to);
					if (local.exists()) {
						local.delete();
					}
				} else if ("mkdir".equals(type)) {
					FtpUtil.rmdir(ftpClient, from);
				}
			}
		}
	}

	// 工具方法

	private boolean isOpenTranscation() {
		return !autoCommit;
	}

	private String getBackRemote(String remote) {
		return remote + ".back";
	}

	public boolean isFileExist(String fileRemote) throws Exception {
		return ftpClient.getStatus(fileRemote) == null ? false : true;
	}

}
