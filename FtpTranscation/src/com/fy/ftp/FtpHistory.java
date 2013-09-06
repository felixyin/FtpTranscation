package com.fy.ftp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import com.fy.ftp.transcation.HistoryBean;
import com.fy.ftp.util.FtpUtil;

/**
 * @author yinbin
 * 
 */
public class FtpHistory {

	private FTPClient ftpClient;

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	// 一个容器属性
	private List<HistoryBean> history = new ArrayList<HistoryBean>();

	public List<HistoryBean> getHistory() {
		return history;
	}

	public void setHistory(List<HistoryBean> history) {
		this.history = history;
	}

	public void add(String type, String local, String remote) {
		HistoryBean historyBean = new HistoryBean(type, local, remote);
		history.add(historyBean);
	}

	public void go(int i) throws Exception {
		if (0 < i) {// 正数
			System.out.println("\n\t\t\tcommit:");
			List<HistoryBean> datas = this.getHistory();
			for (int k = 0; k < datas.size(); k++) {
				HistoryBean data = (HistoryBean) datas.get(k);
				String type = data.getType();
				String from = data.getFrom();
				String to = data.getTo();
				System.out.println(type + ":" + from + "--->" + to);
				if ("update".equals(type)) {
					FtpUtil.delete(ftpClient, to);
				} else if ("upload".equals(type)) {
				} else if ("rename".equals(type)) {
				} else if ("delete".equals(type)) {
					FtpUtil.delete(ftpClient, to);
				} else if ("copy".equals(type)) {
				} else if ("download".equals(type)) {
				} else if ("mkdir".equals(type)) {
				}
			}
		} else if (0 > i) {// 负数
			System.out.println("\n\t\t\troolback:");
			List<HistoryBean> datas = this.getHistory();
			Collections.reverse(datas);
			for (HistoryBean data : datas) {
				String type = data.getType();
				String from = data.getFrom();
				String to = data.getTo();
				System.out.println(type + ":" + from + "<---" + to);
				if ("update".equals(type)) {
					FtpUtil.rename(ftpClient, to, from);
				} else if ("upload".equals(type)) {
					FtpUtil.delete(ftpClient, to);
				} else if ("rename".equals(type)) {
					FtpUtil.rename(ftpClient, to, from);
				} else if ("delete".equals(type)) {
					FtpUtil.rename(ftpClient, to, from);
				} else if ("copy".equals(type)) {
					FtpUtil.delete(ftpClient, to);
				} else if ("download".equals(type)) {
					File local = new File(to);
					if (local.exists()) {
						local.delete();
					}
				} else if ("mkdir".equals(type)) {
					FtpUtil.rmdir(ftpClient, from);
				}
			}
		} else { // 0
			goAll();
		}
	}

	public void goAll() throws Exception {
		System.out.println("\n\t\t\tcommit:");
		List<HistoryBean> datas = this.getHistory();
		for (HistoryBean data : datas) {
			String type = data.getType();
			String from = data.getFrom();
			String to = data.getTo();
			System.out.println(type + ":" + from + "--->" + to);
			if ("update".equals(type)) {
				FtpUtil.delete(ftpClient, to);
			} else if ("upload".equals(type)) {
			} else if ("rename".equals(type)) {
			} else if ("delete".equals(type)) {
				FtpUtil.delete(ftpClient, to);
			} else if ("copy".equals(type)) {
			} else if ("download".equals(type)) {
			} else if ("mkdir".equals(type)) {
			}
		}
	}

	public void backAll() throws Exception {// 负数
		System.out.println("\n\t\t\troolback:");
		List<HistoryBean> datas = this.getHistory();
		Collections.reverse(datas);
		for (HistoryBean data : datas) {
			String type = data.getType();
			String from = data.getFrom();
			String to = data.getTo();
			System.out.println(type + ":" + from + "<---" + to);
			if ("update".equals(type)) {
				FtpUtil.rename(ftpClient, to, from);
			} else if ("upload".equals(type)) {
				FtpUtil.delete(ftpClient, to);
			} else if ("rename".equals(type)) {
				FtpUtil.rename(ftpClient, to, from);
			} else if ("delete".equals(type)) {
				FtpUtil.rename(ftpClient, to, from);
			} else if ("copy".equals(type)) {
				FtpUtil.delete(ftpClient, to);
			} else if ("download".equals(type)) {
				File local = new File(to);
				if (local.exists()) {
					local.delete();
				}
			} else if ("mkdir".equals(type)) {
				FtpUtil.rmdir(ftpClient, from);
			}
		}
	}
}
