package com.test.com.codes.common.util.test;

import java.io.File;

import org.junit.Test;

import com.codes.common.util.DbScripts;

public class DBScriptsTest {
	
	@Test
	public void testCreateTemporaryTablespaceScript(){
		String savePath = "D:/Software/oracle/product/10.2.0/oradata/tbs";
		String username = "test";
		String password = "test";
		
		String createOracleUserScript = DbScripts.createOracleUserScript(username, password, new File(savePath));
		System.out.println(createOracleUserScript);
		
	}
	
}
