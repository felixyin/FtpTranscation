package com.app.platform.util.mongo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.cxf.helpers.IOUtils;

import com.app.platform.util.FileConnection;
import com.app.platform.util.LoginBean;
import com.mongodb.DB;

/**
 * @author yinbin
 */
public class MongoConnection implements FileConnection {

    private DB db;

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public MongoConnection() {
    }

    private boolean autoCommit = true;

    public boolean isAutoCommit() {
        return autoCommit;
    }


    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    private List<HistoryBean> history = new ArrayList<HistoryBean>();

    @Override
    public boolean login(String server, int port, String username, String password) throws Exception {

        return false;
    }

    public boolean login(LoginBean bean) throws Exception {
        db = MongoUtil.login(bean.getIp(), bean.getPort(), bean.getServerName(), bean.getUsername(), bean.getPassword());
        return true;
    }

    @Override
    public boolean changeWD2Root() throws Exception {
        return false;
    }

    public boolean logout() throws Exception {
        return true;
    }

    public boolean upload(String local, String remote) throws Exception {
        InputStream inputstream = new FileInputStream(new File(local));
        return upload(inputstream, null, remote);
    }

    public boolean upload(InputStream local, String remote) throws Exception {
        return upload(local, null, remote);
    }

    private boolean upload(InputStream localInputStream, String localString, String remote) throws Exception {
        history.add(new HistoryBean(null, remote, null));
        return MongoUtil.saveFile(localInputStream, remote);
    }

    public boolean download(String remote, String local) throws Exception {
        InputStream is = MongoUtil.loadFile(remote);
        IOUtils.copy(is, new FileOutputStream(local));
        return true;
    }

    public InputStream download(String remote) throws Exception {
        return MongoUtil.loadFile(remote);
    }


    private boolean isOpenTranscation() {
        return !autoCommit;
    }

    public void commit() throws Exception {
        if (isOpenTranscation()) {
            for (Iterator<HistoryBean> iter = history.iterator(); iter.hasNext(); ) {
                HistoryBean hb = (HistoryBean) iter.next();
                MongoUtil.delOlds(hb.getFrom());
            }
        }
    }

    public void rollback() throws Exception {
        if (isOpenTranscation()) {
            for (Iterator<HistoryBean> iter = history.iterator(); iter.hasNext(); ) {
                HistoryBean hb = (HistoryBean) iter.next();
                MongoUtil.delNew(hb.getFrom());
            }
        }
    }


}

class HistoryBean {

    private String type;
    private String desc;
    private String from;
    private String to;

    public HistoryBean() {
        super();
    }

    public HistoryBean(String type, String from, String to) {
        super();
        this.type = type;
        this.from = from;
        this.to = to;
    }

    public HistoryBean(String type, String desc, String from, String to) {
        super();
        this.type = type;
        this.desc = desc;
        this.from = from;
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
