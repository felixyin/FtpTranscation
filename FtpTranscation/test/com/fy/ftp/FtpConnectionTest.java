package com.fy.ftp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fy.ftp.transcation.FtpConnection;
import com.fy.ftp.transcation.HistoryBean;

public class FtpConnectionTest {
	private FtpConnection ftpConnection;

	@Before
	public void setUp() throws Exception {
		// new FtpConnection("localhost", 2121, "admin", "ybkk1027");
		ftpConnection = new FtpConnection();
		ftpConnection.login("10.135.106.62", 21, "dzqztest", "test");
	}

	@After
	public void tearDown() throws Exception {
		ftpConnection.logout();
	}

	@Test
	public void testGetFtpClient() {
		assertNotNull(ftpConnection.getFtpClient());
	}

	@Test
	public void testUpload() throws Exception {
		String local = "D:\\pdf\\ff.pdf";
		String remote = "/PDF1/对账单/2012/6/ff.pdf";
		boolean b = ftpConnection.upload(local, remote);
		assertTrue(b);
	}

	@Test
	public void testUploadInputStream() throws Exception {
		InputStream localInputStream = new FileInputStream(new File(
				"D:\\pdf\\ff.pdf"));
		String remote = "/PDF1/对账单/2012/6/ff.pdf";
		boolean b = ftpConnection.upload(localInputStream, remote);
		assertTrue(b);
	}

	@Test
	public void testDownload() throws Exception {
		String local = "D:\\ff.pdf";
		File f = new File(local);
		if (f.exists()) {
			f.delete();
		}
		String remote = "/PDF1/对账单/2012/6/ff.pdf";
		boolean b = ftpConnection.download(remote, local);
		assertTrue(b);
	}

	@Test
	public void testDownloadInputStream() throws Exception {
		String remote = "/PDF1/对账单/2012/6/ff.pdf";
		InputStream remoteInputStream = ftpConnection.download(remote);
		Assert.assertNotNull(remoteInputStream);
	}

	@Test
	public void testCopy() throws Exception {
		String sourceRemote = "/PDF1/对账单/2012/6/ff.pdf";
		String targetRemote = "/PDF1/对账单/2012/6/ff_copy.pdf";
		boolean b = ftpConnection.copy(sourceRemote, targetRemote);
		assertTrue(b);
	}

	@Test
	public void testRename() throws Exception {
		String sourceRemote = "/PDF1/对账单/2012/6/ff_copy.pdf";
		String targetRemote = "/PDF1/对账单/2012/6/ff_rename.pdf";
		boolean b = ftpConnection.rename(sourceRemote, targetRemote);
		assertTrue(b);
	}

	@Test
	public void testDelete() throws Exception {
		String remote = "/PDF1/对账单/2012/6/ff_rename.pdf";
		boolean b = ftpConnection.delete(remote);
		assertTrue(b);
	}

	@Test
	public void testMkdir() throws Exception {
		assertTrue(ftpConnection.mkdir("/PDF1/对账单/2012/6"));
	}

	@Test
	public void testListFile() {
	}

	@Test
	public void testIsFileExist() throws Exception {
		String fileRemote = "/PDF1/对账单/2012/6/ff.pdf";
		boolean b = ftpConnection.isFileExist(fileRemote);
		assertTrue(b);
	}

	@Test
	public void testGetFtpHistory() throws Exception {
		testUpload();
		FtpHistory ftpHistory = ftpConnection.getFtpHistory();
		List<HistoryBean> historyBeans = ftpHistory.getHistory();
		for (HistoryBean historyBean : historyBeans) {
			System.out.println(historyBean.getType() + "\t"
					+ historyBean.getFrom() + "\t" + historyBean.getTo());
		}
	}

}
