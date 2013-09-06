package com.fy.ftp.util;

import org.apache.commons.net.ftp.FTPClient;

public class FtpAgent {

	private FTPClient ftpClient;

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public boolean execute(CallBackInterface callBackInterface)
			throws Exception {
		return callBackInterface.invoke(ftpClient);
	}

}
