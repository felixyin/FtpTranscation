package com.fy.ftp.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fy.ftp.LoginBean;

public class FtpUtilTest {
	private static LoginBean linkBean;
	private static FTPClient ftpClient;
	static {
		linkBean = new LoginBean();
		linkBean.setServer("localhost");
		linkBean.setPort(2121);
		linkBean.setUsername("admin");
		linkBean.setPassword("ybkk1027");
	}

	@Before
	public void setUp() throws Exception {
		ftpClient = FtpUtil.login(linkBean);
		// testUpload();
	}

	@After
	public void tearDown() throws Exception {
		FtpUtil.logout(ftpClient);
	}

	@Test
	public void testUpload() throws Exception {
		String local = "D:\\pdf\\ff.pdf";
		String remote = "/ff.pdf";
		boolean b = FtpUtil.upload(ftpClient, local, remote);
		assertTrue(b);
	}

	@Test
	public void testUploadInputStream() throws Exception {
		InputStream localInputStream = new FileInputStream(new File(
				"D:\\pdf\\ff.pdf"));
		String remote = "/ff.pdf";
		boolean b = FtpUtil.upload(ftpClient, localInputStream, remote);
		assertTrue(b);
	}

	@Test
	public void testIsFileExist() throws Exception {
		String fileRemote = "/ff.pdf";
		boolean b = FtpUtil.isFileExist(ftpClient, fileRemote);
		assertTrue(b);
	}

	@Test
	public void testDownload() throws Exception {
		String local = "D:\\pdf\\ff.pdf";
		String remote = "/ff.pdf";
		boolean b = FtpUtil.download(ftpClient, remote, local);
		assertTrue(b);
	}

	@Test
	public void testDownloadInputStream() throws Exception {
		String remote = "/ff.pdf";
		InputStream input = FtpUtil.download(ftpClient, remote);
		byte[] data = IOUtils.toByteArray(input);
		FileOutputStream out = new FileOutputStream(new File("I:\\ff.pdf"));
		IOUtils.write(data, out);
		Assert.assertNotNull(input);
		out.close();
		input.close();
	}

	@Test
	public void testCopy() throws Exception {
		// testUploadInputStream();
		// testDownloadInputStream();
		// testDownload();
		// testUploadInputStream();
		// System.out.println("over");
		// testUpload();
		// testDownload();
		// testUpload();
		// testUploadInputStream();
		 String sourceRemote = "/ff.pdf";
		 String targetRemote = "/ff_copy.pdf";
		 boolean b = FtpUtil.copy(ftpClient, sourceRemote, targetRemote);
		 assertTrue(b);
	}

	@Test
	public void testDelete() throws Exception {
		String remote = "/ff_copy.pdf";
		boolean b = FtpUtil.delete(ftpClient, remote);
		assertTrue(b);
	}

	@Test
	public void testRename() throws Exception {
		String targetRemote = "ff_rename.pdf";
		String sourceRemote = "/ff.pdf";
		boolean b = FtpUtil.rename(ftpClient, sourceRemote, targetRemote);
		assertTrue(b);
		// FtpUtil.rename(targetRemote, sourceRemote);
	}

	@Test
	public void testMkdir() throws Exception {
		// assertTrue(FtpUtil.mkdir(ftpClient, "PDF/DUIZHANGDAN/2012/"));
		// assertTrue(FtpUtil.mkdir(ftpClient, "/PDF/DUIZHANGDAN/2012/6/"));
		assertTrue(FtpUtil.mkdir(ftpClient, "/PDF/DUIZHANGDAN/2012/6"));
	}

	@Test
	public void testRvdir() throws Exception {
		assertTrue(FtpUtil.rmdir(ftpClient, "PDF/DUIZHANGDAN/2012/6/"));
	}

	@Test
	public void testListFile() throws Exception {
		// String folderRemote = "";
		// boolean b = FtpUtil.listFile(ftpClient, folderRemote);
		// assertTrue(b);
	}

}
