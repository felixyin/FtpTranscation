package com.fy.ftp.deploy;

import java.io.File;

import com.fy.ftp.transcation.FtpConnection;

public class WebProjectDeploy {
	private static FtpConnection ftpConnection;
	private static String projectName = "signature";

	public static void main(String[] args) throws Exception {
		String local = "C:\\workspaces\\IntellijIDEA\\work\\signature\\classes\\artifacts\\signature_Web_exploded";
		ftpConnection = new FtpConnection();
		ftpConnection.login("10.135.106.62", 2121, "admin", "admin");
		File webRoot = new File(local);
		if (!webRoot.isDirectory()) {
			throw new Exception("webroot 必须是目录");
		}
		digui(webRoot, local);
		ftpConnection.logout();
	}

	private static void digui(File webRoot, String local) throws Exception {
		File[] files = webRoot.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				digui(file, local);
			} else if (file.isFile()) {
				String path = file.getPath();
				String relativePath = path.replace(local, "");
				path = path.replace("\\", "/");
				relativePath = relativePath.replace("\\", "/");
				// if (relativePath.indexOf("class") != -1) {
				ftpConnection.getFtpClient().changeWorkingDirectory("/");
				boolean b = ftpConnection.upload(path, "/" + projectName
						+ relativePath);
				System.out.println(relativePath + "------->" + b);
				// System.out.println(b);
				// } else if ("".equals("all")) {
				// ftpConnection.getFtpClient().changeWorkingDirectory("/");
				// boolean b = ftpConnection.upload(path, relativePath);
				// System.out.println(b);
				// }
			}
		}
	}

}
