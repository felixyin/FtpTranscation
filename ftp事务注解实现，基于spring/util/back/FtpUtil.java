package com.app.platform.util.back;

import java.io.FileInputStream;

import DBstep.UpDownPDF;

import com.frame.utils.properties.PropertiesUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yinbin
 * Date: 12-11-2
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
public class FtpUtil {


    public static UpDownPDF login() throws Exception {
        String ftpIp = PropertiesUtil.properties.get("ftp.ip").toString();
        int ftpPort = Integer.parseInt(PropertiesUtil.properties.get("ftp.port").toString());
        String ftpUsername = PropertiesUtil.properties.get("ftp.username").toString();
        String ftpPassword = PropertiesUtil.properties.get("ftp.password").toString();
        UpDownPDF fUp = new UpDownPDF(ftpIp, ftpPort, ftpUsername, ftpPassword);
        fUp.login();
        return fUp;
    }


    /**
     * @param local
     * @param remote
     * @return
     * @throws Exception
     */
    public static String uploadFile(String local, String remote) throws Exception {
        UpDownPDF fUp= login();
        FileInputStream fin = new FileInputStream(local);
        byte[] data = new byte[fin.available()];
        fin.read(data, 0, data.length);
        fUp.upFile(data, remote);
        fUp.logout();
        return remote;
    }

    /**
     * @param remote
     * @param local
     * @return
     * @throws Exception
     */
    public  static boolean downloadFile(String remote, String local) throws Exception {
        UpDownPDF fUp= login();
        fUp.downFile(remote, local);
        fUp.logout();
        return true;
    }

}
