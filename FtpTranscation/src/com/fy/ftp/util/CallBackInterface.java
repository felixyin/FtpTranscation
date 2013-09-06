package com.fy.ftp.util;

import org.apache.commons.net.ftp.FTPClient;

public interface CallBackInterface {

	boolean invoke(FTPClient ftpClient);

}
