package com.fy;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

public class MainTest {

	public static void main(String[] args) throws Exception {
		FTPClient ftpClient = new FTPClient();// ftpClient不能共享
		String server = "localhost";
		int port = 2121;
		String username = "admin";
		String password = "ybkk1027";
		ftpClient.connect(server, port);
		ftpClient.setControlEncoding("GBK");
		FTPClientConfig config=new FTPClientConfig(FTPClientConfig.SYST_NT);
		config.setServerLanguageCode("zh");
		boolean lb = ftpClient.login(username, password);
		if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			ftpClient.disconnect();
			throw new Exception("未连接到FTP，用户名或密码错误。");
		}
		if (lb) {//登录成功
			//TODO很重要额
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

			String local = "D:\\pdf\\ff.pdf";
			String remote = "/中文.pdf";
			
			remote=new String(remote.getBytes("UTF-8"),"ISO-8859-1");
			
			InputStream input = new FileInputStream(local);
			boolean b = ftpClient.storeFile(remote, input);
			System.out.println(b);
			System.out.println("over");
		}
		ftpClient.logout();

	}

}
