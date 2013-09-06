package com.app.platform.util.aspect;

import com.app.platform.util.FileConnection;
import com.app.platform.util.ConnectionImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: yinbin
 * Date: 12-11-26
 * Time: 下午4:14
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component
public class AroundFileStore {

    protected Log logger = LogFactory.getLog(getClass());

    @Pointcut("within(com.app..*)")
    public void businessService1() {
    }

    @Pointcut("within(com.signaturews..*)")
    private void businessService2() {}
   
 
    @Around("businessService1() && @annotation(com.app.platform.util.aspect.FileStoreTranscation)")
    public Object doExecute1(ProceedingJoinPoint pjp) throws Throwable {
    	return func(pjp);
    }
   
    @Around("businessService2() && @annotation(com.app.platform.util.aspect.FileStoreTranscation)")
    public Object doExecute2(ProceedingJoinPoint pjp) throws Throwable {
        return func(pjp);
    }

	private Object func(ProceedingJoinPoint pjp) throws Exception, Throwable {
		Object obj = null;
		        logger.info("\n\n文件事务开始");
		        //对于上级层次的方法，如果开启file store事务，则继承该事务
		//        Object fsaObject = FileContext.getFileConnection();
		
		//        if (null != fsaObject) {
		//            logger.info("在线程\"上下文\"中已经开启file事务连接，此处继承上下文的事务连接。");
		//            obj = pjp.proceed();
		//        } else {
		            logger.info("在线程\"上下文\"中没有找到file事务连接，此处开启新的事务连接。");
		            ConnectionImpl getConnection = new ConnectionImpl();
		            FileConnection fileConnection = getConnection.get();
		
		            boolean b = fileConnection.login(getConnection.getLoginBean());
		
		            try {
		                fileConnection.setAutoCommit(false);
		                FileContext.setFileConnection(fileConnection);
		                obj = pjp.proceed();
		                fileConnection.commit();
		            } catch (Throwable throwable) {
		                fileConnection.rollback();
		                throw throwable;
		            } finally {
		                fileConnection.logout();
		                logger.info("文件事务结束\n\n");
		            }
		//        }
		        return obj;
	}


}
