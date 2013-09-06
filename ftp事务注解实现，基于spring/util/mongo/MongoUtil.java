package com.app.platform.util.mongo;

import java.io.InputStream;
import java.util.Date;
import java.util.ListIterator;

import com.app.platform.util.LoginBean;
import org.apache.commons.lang.StringUtils;

import com.frame.utils.properties.PropertiesUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.apache.commons.net.ftp.FTPClient;

/**
 * Created by IntelliJ IDEA. User: yinbin Date: 12-11-2 Time: 下午2:45 To change
 * this template use File | Settings | File Templates.
 */
public class MongoUtil {

    private static DB db = null;

    private MongoUtil() {
    }

    public synchronized static DB getDB() throws Exception {
        if (null == db) {
            String ip = PropertiesUtil.getInstance().getProperty("mongo.ip");
            String port = PropertiesUtil.getInstance().getProperty("mongo.port");
            String serverName = PropertiesUtil.getInstance().getProperty("mongo.serverName");
            String username = PropertiesUtil.getInstance().getProperty("mongo.username");
            String passwordStr = PropertiesUtil.getInstance().getProperty("mongo.password");
            char[] password = {};
            if (StringUtils.isNotBlank(passwordStr)) {
                password = passwordStr.toCharArray();
            }
            Mongo mongo = new Mongo(ip, Integer.valueOf(port));
            db = mongo.getDB(serverName);
            if (StringUtils.isNotBlank(username) && password.length > 0) {
                db.authenticate(username, password);
            }
        }
        return db;
    }

    /**
     * 每次都取得最新的文件的输入流
     *
     * @param remotePath
     * @return
     * @throws Exception
     */
    public static InputStream loadFile(String remotePath) throws Exception {
        return getNewFile(remotePath).getInputStream();
    }

    /**
     * 得到最新的文件
     *
     * @param remotePath
     * @return
     * @throws Exception
     */
    public static GridFSDBFile getNewFile(String remotePath) throws Exception {
        GridFSDBFile maxTemp = null;
        GridFS gridFS = new GridFS(getDB());
        ListIterator<GridFSDBFile> lists = gridFS.find(remotePath).listIterator();
        Date minDate = new Date(300);
        System.out.println(minDate);
        while (lists.hasNext()) {
            GridFSDBFile temp = lists.next();
            Date tempDate = (Date) temp.get("uploadDate");
            if (tempDate.after(minDate)) {
                minDate = tempDate;
                maxTemp = temp;
            }
        }
        return maxTemp;
    }

    /**
     * 根据文件名保存文件
     *
     * @param inputStream
     * @param remotePath
     * @return
     * @throws Exception
     */
    public static boolean saveFile(InputStream inputStream, String remotePath) throws Exception {
        GridFS gridFS = new GridFS(getDB());
        GridFSInputFile gridFSInputFile = gridFS.createFile(inputStream, remotePath);
        gridFSInputFile.save();
        return true;
    }

    /**
     * 如果不成功的话，就删掉最新的文档
     *
     * @param remotePath
     * @throws Exception
     */
    public static void delNew(String remotePath) throws Exception {
        GridFS gridFS = new GridFS(getDB());
        GridFSDBFile gff = getNewFile(remotePath);
        if (null == gff)
            return;
        Date maxDate = gff.getUploadDate();
        BasicDBObject bdobj = new BasicDBObject();
        bdobj.put("uploadDate", maxDate);
        bdobj.put("filename", remotePath);
        gridFS.remove(bdobj);
    }

    /**
     * 最后成功的 话，就删掉所有以前的文档，只保留最新的文档
     *
     * @param remotePath
     * @throws Exception
     */
    public static void delOlds(String remotePath) throws Exception {
        GridFS gridFS = new GridFS(getDB());
        GridFSDBFile gff = getNewFile(remotePath);
        if (null == gff)
            return;
        Date maxDate = gff.getUploadDate();
        BasicDBObject bdobj = new BasicDBObject();
        bdobj.put("uploadDate", new BasicDBObject("$lt", maxDate));
        bdobj.put("filename", remotePath);
        gridFS.remove(bdobj);
    }

    public synchronized static DB login(String ip, int port, String serverName, String username, String passwordStr) throws Exception {
        if (null == db) {
            char[] password = {};
            if (StringUtils.isNotBlank(passwordStr)) {
                password = passwordStr.toCharArray();
            }
            Mongo mongo = new Mongo(ip, Integer.valueOf(port));
            db = mongo.getDB(serverName);
            if (StringUtils.isNotBlank(username) && password.length > 0) {
                db.authenticate(username, password);
            }
        }
        return db;
    }
}
