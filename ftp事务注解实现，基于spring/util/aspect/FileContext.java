package com.app.platform.util.aspect;

import com.app.platform.util.FileConnection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinbin
 * 作用类似与struts2的ActionContext。
 */
public class FileContext implements Serializable {

    private static final String KEY = "FILE_CONNECTION";
    private static ThreadLocal fileStoreContext = new ThreadLocal();
    private Map<String, Object> context;

    private FileContext(Map<String, Object> context) {
        this.context = context;
    }

    private static void setContext(FileContext context) {
        fileStoreContext.set(context);
    }

    private static FileContext getContext() {
        return (FileContext) fileStoreContext.get();
    }

    private void setContextMap(Map<String, Object> contextMap) {
        getContext().context = contextMap;
    }

    private Map<String, Object> getContextMap() {
        return context;
    }

    private Object get(String key) {
        return context.get(key);
    }

    private void put(String key, Object value) {
        context.put(key, value);
    }

    public static FileConnection getFileConnection() {
        FileContext context = (FileContext) fileStoreContext.get();
        if (context != null) {
            return (FileConnection) context.get(KEY);
        }
        return null;
    }

    public static void setFileConnection(FileConnection fileConnection) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(KEY, fileConnection);
        FileContext fileContext = new FileContext(context);
        FileContext.setContext(fileContext);
    }
}
