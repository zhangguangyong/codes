package com.codes.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;

/**
 * CMD工具类
 * @author zhangguangyong
 *
 * 2015年10月12日 下午2:57:07
 */
public abstract class Cmds {
	
	static final String LINE_SEPARATOR = "\r\n";
	public static void execCmd(String[] cmds){
		execCmd(Arrays.asList(cmds));
	}
	
	public static void execCmd(Iterable<String> cmds){
		String home = System.getProperty("user.home");
		try {
			StringBuilder sb = new StringBuilder("echo off" + LINE_SEPARATOR);
			Iterator<String> itera = cmds.iterator();
			while( itera.hasNext() ){
				sb.append( itera.next() + LINE_SEPARATOR );
			}
			System.out.println(sb.toString());
			File file = new File(home, System.currentTimeMillis() + ".bat");
			Writer out = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(out);
			bw.write(sb.toString());
			bw.flush();
			bw.close();
			Cmds.execCmd(file.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行CMD命令
	 * @param cmd
	 */
	public static void execCmd(String cmd){
		Process p = null;
		try {
			Runtime rt = Runtime.getRuntime();
			p = rt.exec(cmd);
			// 获取进程的标准输入流
			final InputStream is1 = p.getInputStream();
			// 获取进城的错误流
			final InputStream is2 = p.getErrorStream();
			// 启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
			new Thread() {
				public void run() {
					BufferedReader br1 = new BufferedReader(
							new InputStreamReader(is1));
					try {
						String line1 = null;
						while ((line1 = br1.readLine()) != null) {
							if (line1 != null) {
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							is1.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
				
			new Thread() {
				public void run() {
					BufferedReader br2 = new BufferedReader(
							new InputStreamReader(is2));
					try {
						String line2 = null;
						while ((line2 = br2.readLine()) != null) {
							if (line2 != null) {
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							is2.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
			
			p.waitFor();
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				/*if( p.getErrorStream() != null ){
					p.getErrorStream().close();
				}
				if( p.getInputStream() != null ){
					p.getInputStream().close();
				}
				if( p.getOutputStream() != null ){
					p.getOutputStream().close();
				}*/
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
}
