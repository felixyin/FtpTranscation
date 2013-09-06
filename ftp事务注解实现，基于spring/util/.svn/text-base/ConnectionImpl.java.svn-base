package com.app.platform.util;

import com.app.platform.util.ftp.FtpConnection;
import com.app.platform.util.mongo.MongoConnection;
import com.frame.utils.properties.PropertiesUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: yinbin
 * Date: 12-11-28
 * Time: 下午4:30
 * 获取连接接口
 */
public class ConnectionImpl {

    protected Log logger = LogFactory.getLog(getClass());

    private LoginBean loginBean = new LoginBean();

    public ConnectionImpl() {
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    /**
     * 读取配置文件，获取连接对象
     *
     * @return
     * @throws Exception
     */
    public FileConnection get() throws Exception {
        FileConnection fileConnection = null;
        com.frame.utils.properties.Properties ppt = PropertiesUtil.getInstance();
        String fileStorePlain = ppt.getProperty("file_store_plain");
        logger.info("文件存储方案：" + fileStorePlain);
        if ("mongo".equalsIgnoreCase(fileStorePlain)) {
            loginBean.setIp(ppt.getProperty("mongo.ip"));
            loginBean.setPort(Integer.parseInt(ppt.getProperty("mongo.port")));
            loginBean.setServerName(ppt.getProperty("mongo.serverName"));
            loginBean.setUsername(ppt.getProperty("mongo.username"));
            loginBean.setPassword(ppt.getProperty("mongo.password"));
            fileConnection=new MongoConnection();
        } else if ("ftp".equalsIgnoreCase(fileStorePlain)) {
            loginBean.setIp(ppt.getProperty("ftp.ip"));
            loginBean.setPort(Integer.parseInt(ppt.getProperty("ftp.port")));
            loginBean.setUsername(ppt.getProperty("ftp.username"));
            loginBean.setPassword(ppt.getProperty("ftp.password"));
            fileConnection = new FtpConnection();
        } else {
            logger.error("没有找到存储方案");
            throw new Exception("properties配置异常！");
        }
        return fileConnection;
    }
}