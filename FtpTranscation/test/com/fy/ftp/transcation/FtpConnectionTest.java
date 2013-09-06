package com.fy.ftp.transcation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fy.ftp.FtpHistory;

public class FtpConnectionTest {
	private boolean isLogout = true;
	private FtpConnection ftpConnection;

	@Before
	public void setUp() throws Exception {
		// new FtpConnection("localhost", 2121, "admin", "ybkk1027");
		ftpConnection = new FtpConnection();
		ftpConnection.login("localhost", 2121, "admin", "ybkk1027");
	}

	@After 
	public void tearDown() throws Exception {
//		ftpConnection.logout();
	}

	@Test
	public void testGetFtpClient() {
		assertNotNull(ftpConnection.getFtpClient());
	}

	public void upload() throws Exception {
		String local = "D:\\pdf\\ff.pdf";
		String remote = "/PDF/中文/2012/6/ff.pdf";
		boolean b = ftpConnection.upload(local, remote);
		assertTrue(b);
	}

	@Test
	public void testUpload() throws Exception {
		testTrancation(new TranscationCallBack() {
			@Override
			public void invoke() throws Exception {
				upload();
//				int i = 1 / 0;
			}
		});
	}

	@Test
	public void testDownload() throws Exception {
		testTrancation(new TranscationCallBack() {
			@Override
			public void invoke() throws Exception {
				// upload
				upload();
				// download
				String local = "I:\\download.pdf";
				String remote = "/PDF/DUIZHANGDAN/2012/6/ff.pdf";
				File f = new File(local);
				if (f.exists()) {
					f.delete();
				}
				boolean b = ftpConnection.download(remote, local);
				assertTrue(b);
				int i = 1 / 0;
			}
		});
	}

	@Test
	public void testCopy() throws Exception {
		testTrancation(new TranscationCallBack() {
			@Override
			public void invoke() throws Exception {
				upload();
				String sourceRemote = "/PDF/DUIZHANGDAN/2012/6/ff.pdf";
				String targetRemote = "/PDF/DUIZHANGDAN/2012/6/ff_copy.pdf";
				boolean b = ftpConnection.copy(sourceRemote, targetRemote);
				assertTrue(b);
				int i = 1 / 0;
			}
		});
	}

	@Test
	public void testRename() throws Exception {
		testTrancation(new TranscationCallBack() {
			@Override
			public void invoke() throws Exception {
				// upload
				upload();
				// download
				String sourceRemote = "/PDF/DUIZHANGDAN/2012/6/ff.pdf";
				String targetRemote = "/PDF/DUIZHANGDAN/2012/6/ff_rename.pdf";
				boolean b = ftpConnection.rename(sourceRemote, targetRemote);
				assertTrue(b);
				int i = 1 / 0;
			}
		});
	}

	@Test
	public void testDelete() throws Exception {
		testTrancation(new TranscationCallBack() {
			@Override
			public void invoke() throws Exception {
				// upload
				upload();
				// download
				String remote = "/PDF/DUIZHANGDAN/2012/6/ff.pdf";
				boolean b = ftpConnection.delete(remote);
				assertTrue(b);
				int i = 1 / 0;
			}
		});
	}

	@Test
	public void testMkdir() throws Exception {
		testTrancation(new TranscationCallBack() {
			@Override
			public void invoke() throws Exception {
				boolean b = ftpConnection.mkdir("/PDF/DUIZHANGDAN/2012/6/");
				assertTrue(b);
				int i = 1 / 0;
			}
		});
	}

	@Test
	public void testIsFileExist() throws Exception {
		testTrancation(new TranscationCallBack() {
			@Override
			public void invoke() throws Exception {
				// upload
				upload();
				// isExist
				String fileRemote = "/PDF/DUIZHANGDAN/2012/6/ff.pdf";
				boolean b = ftpConnection.isFileExist(fileRemote);
				assertTrue(b);
				int i = 1 / 0;
			}
		});
	}

	@Test
	public void testGetFtpHistory() throws Exception {

		/*
		 * 连续测试 测试完毕一个方法后，并不注销登录，关闭连接。 测试完毕此方法后，手动退出
		 */
		this.isLogout = false;

		testUpload();
		testCopy();
		testDelete();
		testDownload();
		testRename();
		testMkdir();
		testIsFileExist();
		testMkdir();
		FtpHistory ftpHistory = ftpConnection.getFtpHistory();
		List<HistoryBean> historyBeans = ftpHistory.getHistory();
		for (HistoryBean historyBean : historyBeans) {
			// System.out.println(historyBean.getType() + "\t"
			// + historyBean.getFrom() + "\t" + historyBean.getTo());
		}
		ftpConnection.logout();
	}

	public void testTrancation(TranscationCallBack call) throws Exception {
		try {
			ftpConnection.setAutoCommit(false);
			call.invoke();
			ftpConnection.commit();
		} catch (Exception e) {
			ftpConnection.rollback();
		} finally {
			if (isLogout) {
				ftpConnection.logout();
			}
		}
	}

}

interface TranscationCallBack {
	void invoke() throws Exception;
}
