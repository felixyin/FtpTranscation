package com.fy.ftp;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.xml.ws.http.HTTPException;

import com.fy.ftp.transcation.FtpConnection;

public class Test {

	/**
	 * 模拟实际情景测试 之 手动事务支持
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		testTranscation();
	}

	/**
	 * 想了解具体执行过程和原理，请debug
	 * 
	 * @throws Exception
	 */
	private static void testTranscation() throws Exception {

		// jdbcConnection jdbc=new xxx();
		FtpConnection ftpConnection = new FtpConnection();

		try {

			// jdbc=JdbcDriver.getConnection("oracle","localhost","ORCL","root","youknow");
			ftpConnection.login("localhost", 2121, "admin", "ybkk1027");// 最好放在try下一行

			// jdbc.setAutoCommit(false);
			ftpConnection.setAutoCommit(false);

			/*
			 * 下面开始业务方法，不涉及业务的代码都是，为了实现事务必须的代码
			 */
			// jdbc.insert(sql,new Object[]{"02382384233","name","your name"});

			// ftp上传
			boolean ub = ftpConnection.upload("D:\\pdf\\20120507110913518.pdf",
					"/PDF/DUIZHANGDAN/2012/6/ff.pdf");
			assertTrue(ub);

			// makeException();

			File f = new File("F:\\download.pdf");
			if (f.exists())
				f.delete();
			boolean b = ftpConnection.download(
					"/PDF/DUIZHANGDAN/2012/6/ff.pdf", "F:\\download.pdf");
			assertTrue(b);

			// makeException();

			boolean cb = ftpConnection.copy("/PDF/DUIZHANGDAN/2012/6/ff.pdf",
					"/PDF/DUIZHANGDAN/2012/6/ff_copy.pdf");
			assertTrue(cb);

			// makeException();

			boolean rb = ftpConnection.rename(
					"/PDF/DUIZHANGDAN/2012/6/ff_copy.pdf",
					"/PDF/DUIZHANGDAN/2012/6/ff_rename.pdf");
			assertTrue(rb);

//			makeException();

			boolean db = ftpConnection
					.delete("/PDF/DUIZHANGDAN/2012/6/ff_rename.pdf");
			assertTrue(db);

			makeException();

			// jdbc.commit();
			ftpConnection.commit();// 必须放在catch上一行
		} catch (Exception e) {

			// jdbc.rollback();
			ftpConnection.rollback();// 必须放入catch块中
			throw e;
		} finally {
			if (null != ftpConnection) {

				// jdbc.logout();
				ftpConnection.logout();// 必须放入finally块中
			}
		}
	}

	/**
	 * 一个方法在执行过程中，任何一步都可能报异常，各种异常
	 */
	private static void makeException() throws Exception {
		// 比如：
		// getJdbcConnection();//有可能数据库连接不上，密码错误等
		// jdbc.update(sql,params)//有可能参数在某种特定条件下，长度超长等等原因导致报错
		// webservic.invokeTcs("做什么事情标志或请求的方法名",params参数列表);//由于种种原因报错。或者返回了false，业务上需要回滚一切操作
		// int i = 1 / 0;//等等一切throwable类及子类型的异常
		// throw new Exception("未知异常");
		// throw new InvalidSignatureValueException("额");
		// throw new NullPointerException();
		// throw new ArrayIndexOutOfBoundsException();
		// throw new ClassNotFoundException();
		// throw new RuntimeException();
		// throw new FileNotFoundException();
		throw new HTTPException(500);
		// ...
	}

}
