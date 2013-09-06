package com.fy.ftp.transcation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPListParseEngine;

public class FtpClient extends FTPClient {

	@Override
	public void disconnect() throws IOException {

		super.disconnect();
	}

	@Override
	public boolean login(String username, String password) throws IOException {

		return super.login(username, password);
	}

	@Override
	public boolean login(String username, String password, String account)
			throws IOException {

		return super.login(username, password, account);
	}

	@Override
	public boolean logout() throws IOException {

		return super.logout();
	}

	@Override
	public boolean changeWorkingDirectory(String pathname) throws IOException {

		return super.changeWorkingDirectory(pathname);
	}

	@Override
	public boolean changeToParentDirectory() throws IOException {

		return super.changeToParentDirectory();
	}

	@Override
	public boolean structureMount(String pathname) throws IOException {

		return super.structureMount(pathname);
	}

	@Override
	public void enterLocalActiveMode() {

		super.enterLocalActiveMode();
	}

	@Override
	public void enterLocalPassiveMode() {

		super.enterLocalPassiveMode();
	}

	@Override
	public boolean enterRemoteActiveMode(InetAddress host, int port)
			throws IOException {

		return super.enterRemoteActiveMode(host, port);
	}

	@Override
	public boolean enterRemotePassiveMode() throws IOException {

		return super.enterRemotePassiveMode();
	}

	@Override
	public boolean remoteRetrieve(String filename) throws IOException {

		return super.remoteRetrieve(filename);
	}

	@Override
	public boolean remoteStore(String filename) throws IOException {

		return super.remoteStore(filename);
	}

	@Override
	public boolean remoteStoreUnique(String filename) throws IOException {

		return super.remoteStoreUnique(filename);
	}

	@Override
	public boolean remoteStoreUnique() throws IOException {

		return super.remoteStoreUnique();
	}

	@Override
	public boolean remoteAppend(String filename) throws IOException {

		return super.remoteAppend(filename);
	}

	@Override
	public boolean completePendingCommand() throws IOException {

		return super.completePendingCommand();
	}

	@Override
	public boolean retrieveFile(String remote, OutputStream local)
			throws IOException {

		return super.retrieveFile(remote, local);
	}

	@Override
	public InputStream retrieveFileStream(String remote) throws IOException {

		return super.retrieveFileStream(remote);
	}

	@Override
	public boolean storeFile(String remote, InputStream local)
			throws IOException {
		// 判断是否开启事务
		boolean b = super.storeFile(remote, local);
		if (b) {
			// 添加事务日志
		}
		return b;
	}

	@Override
	public OutputStream storeFileStream(String remote) throws IOException {

		return super.storeFileStream(remote);
	}

	@Override
	public boolean appendFile(String remote, InputStream local)
			throws IOException {

		return super.appendFile(remote, local);
	}

	@Override
	public OutputStream appendFileStream(String remote) throws IOException {

		return super.appendFileStream(remote);
	}

	@Override
	public boolean storeUniqueFile(String remote, InputStream local)
			throws IOException {

		return super.storeUniqueFile(remote, local);
	}

	@Override
	public OutputStream storeUniqueFileStream(String remote) throws IOException {

		return super.storeUniqueFileStream(remote);
	}

	@Override
	public boolean storeUniqueFile(InputStream local) throws IOException {

		return super.storeUniqueFile(local);
	}

	@Override
	public OutputStream storeUniqueFileStream() throws IOException {

		return super.storeUniqueFileStream();
	}

	@Override
	public boolean allocate(int bytes) throws IOException {

		return super.allocate(bytes);
	}

	@Override
	public boolean allocate(int bytes, int recordSize) throws IOException {

		return super.allocate(bytes, recordSize);
	}

	@Override
	public boolean doCommand(String command, String params) throws IOException {

		return super.doCommand(command, params);
	}

	@Override
	public String[] doCommandAsStrings(String command, String params)
			throws IOException {

		return super.doCommandAsStrings(command, params);
	}

	@Override
	public boolean rename(String from, String to) throws IOException {
		// 判断是否开启事务
		boolean b = super.rename(from, to);
		if (b) {
			// 添加事务日志
		}
		return b;
	}

	@Override
	public boolean abort() throws IOException {

		return super.abort();
	}

	@Override
	public boolean deleteFile(String pathname) throws IOException {

		return super.deleteFile(pathname);
	}

	@Override
	public boolean removeDirectory(String pathname) throws IOException {

		return super.removeDirectory(pathname);
	}

	@Override
	public boolean makeDirectory(String pathname) throws IOException {

		return super.makeDirectory(pathname);
	}

	@Override
	public String printWorkingDirectory() throws IOException {

		return super.printWorkingDirectory();
	}

	@Override
	public boolean sendSiteCommand(String arguments) throws IOException {

		return super.sendSiteCommand(arguments);
	}

	@Override
	public String getSystemType() throws IOException {

		return super.getSystemType();
	}

	@Override
	public String listHelp() throws IOException {

		return super.listHelp();
	}

	@Override
	public String listHelp(String command) throws IOException {

		return super.listHelp(command);
	}

	@Override
	public boolean sendNoOp() throws IOException {

		return super.sendNoOp();
	}

	@Override
	public String[] listNames(String pathname) throws IOException {

		return super.listNames(pathname);
	}

	@Override
	public String[] listNames() throws IOException {

		return super.listNames();
	}

	@Override
	public FTPFile[] listFiles(String pathname) throws IOException {

		return super.listFiles(pathname);
	}

	@Override
	public FTPFile[] listFiles() throws IOException {

		return super.listFiles();
	}

	@Override
	public FTPFile[] listFiles(String pathname, FTPFileFilter filter)
			throws IOException {

		return super.listFiles(pathname, filter);
	}

	@Override
	public FTPFile[] listDirectories() throws IOException {

		return super.listDirectories();
	}

	@Override
	public FTPFile[] listDirectories(String parent) throws IOException {

		return super.listDirectories(parent);
	}

	@Override
	public FTPListParseEngine initiateListParsing() throws IOException {

		return super.initiateListParsing();
	}

	@Override
	public FTPListParseEngine initiateListParsing(String pathname)
			throws IOException {

		return super.initiateListParsing(pathname);
	}

	@Override
	public FTPListParseEngine initiateListParsing(String parserKey,
			String pathname) throws IOException {

		return super.initiateListParsing(parserKey, pathname);
	}

	@Override
	public void configure(FTPClientConfig config) {

		super.configure(config);
	}

	@Override
	public int sendCommand(String command, String args) throws IOException {

		return super.sendCommand(command, args);
	}

	@Override
	public int sendCommand(int command, String args) throws IOException {

		return super.sendCommand(command, args);
	}

	@Override
	public int sendCommand(String command) throws IOException {

		return super.sendCommand(command);
	}

	@Override
	public int sendCommand(int command) throws IOException {

		return super.sendCommand(command);
	}

	@Override
	public int user(String username) throws IOException {

		return super.user(username);
	}

	@Override
	public int pass(String password) throws IOException {

		return super.pass(password);
	}

	@Override
	public int acct(String account) throws IOException {

		return super.acct(account);
	}

	@Override
	public int abor() throws IOException {

		return super.abor();
	}

	@Override
	public int cwd(String directory) throws IOException {

		return super.cwd(directory);
	}

	@Override
	public int cdup() throws IOException {

		return super.cdup();
	}

	@Override
	public int quit() throws IOException {

		return super.quit();
	}

	@Override
	public int rein() throws IOException {

		return super.rein();
	}

	@Override
	public int smnt(String dir) throws IOException {

		return super.smnt(dir);
	}

	@Override
	public int port(InetAddress host, int port) throws IOException {

		return super.port(host, port);
	}

	@Override
	public int eprt(InetAddress host, int port) throws IOException {

		return super.eprt(host, port);
	}

	@Override
	public int pasv() throws IOException {

		return super.pasv();
	}

	@Override
	public int epsv() throws IOException {

		return super.epsv();
	}

	@Override
	public int type(int fileType, int formatOrByteSize) throws IOException {

		return super.type(fileType, formatOrByteSize);
	}

	@Override
	public int type(int fileType) throws IOException {

		return super.type(fileType);
	}

	@Override
	public int stru(int structure) throws IOException {

		return super.stru(structure);
	}

	@Override
	public int mode(int mode) throws IOException {

		return super.mode(mode);
	}

	@Override
	public int retr(String pathname) throws IOException {

		return super.retr(pathname);
	}

	@Override
	public int stor(String pathname) throws IOException {

		return super.stor(pathname);
	}

	@Override
	public int stou() throws IOException {

		return super.stou();
	}

	@Override
	public int stou(String pathname) throws IOException {

		return super.stou(pathname);
	}

	@Override
	public int appe(String pathname) throws IOException {

		return super.appe(pathname);
	}

	@Override
	public int allo(int bytes) throws IOException {

		return super.allo(bytes);
	}

	@Override
	public int feat() throws IOException {

		return super.feat();
	}

	@Override
	public int allo(int bytes, int recordSize) throws IOException {

		return super.allo(bytes, recordSize);
	}

	@Override
	public int rest(String marker) throws IOException {

		return super.rest(marker);
	}

	@Override
	public int mdtm(String file) throws IOException {

		return super.mdtm(file);
	}

	@Override
	public int mfmt(String pathname, String timeval) throws IOException {

		return super.mfmt(pathname, timeval);
	}

	@Override
	public int rnfr(String pathname) throws IOException {

		return super.rnfr(pathname);
	}

	@Override
	public int rnto(String pathname) throws IOException {

		return super.rnto(pathname);
	}

	@Override
	public int dele(String pathname) throws IOException {

		return super.dele(pathname);
	}

	@Override
	public int rmd(String pathname) throws IOException {

		return super.rmd(pathname);
	}

	@Override
	public int mkd(String pathname) throws IOException {

		return super.mkd(pathname);
	}

	@Override
	public int pwd() throws IOException {

		return super.pwd();
	}

	@Override
	public int list() throws IOException {

		return super.list();
	}

	@Override
	public int list(String pathname) throws IOException {

		return super.list(pathname);
	}

	@Override
	public int mlsd() throws IOException {

		return super.mlsd();
	}

	@Override
	public int mlsd(String path) throws IOException {

		return super.mlsd(path);
	}

	@Override
	public int mlst() throws IOException {

		return super.mlst();
	}

	@Override
	public int mlst(String path) throws IOException {

		return super.mlst(path);
	}

	@Override
	public int nlst() throws IOException {

		return super.nlst();
	}

	@Override
	public int nlst(String pathname) throws IOException {

		return super.nlst(pathname);
	}

	@Override
	public int site(String parameters) throws IOException {

		return super.site(parameters);
	}

	@Override
	public int syst() throws IOException {

		return super.syst();
	}

	@Override
	public int stat() throws IOException {

		return super.stat();
	}

	@Override
	public int stat(String pathname) throws IOException {

		return super.stat(pathname);
	}

	@Override
	public int help() throws IOException {

		return super.help();
	}

	@Override
	public int help(String command) throws IOException {

		return super.help(command);
	}

	@Override
	public int noop() throws IOException {

		return super.noop();
	}

}
