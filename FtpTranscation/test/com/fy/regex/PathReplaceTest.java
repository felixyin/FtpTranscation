package com.fy.regex;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class PathReplaceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String s = File.separator;
		String remote = s + "PDF" + s + "DuiZhangDan" + s + "2012" + s + "6"
				+ s + "test.pdf";
		String[] folder = remote.split("\\\\");
		for (int i = 0; i < folder.length; i++) {
			System.out.println(folder[i]);
		}
	}

}
