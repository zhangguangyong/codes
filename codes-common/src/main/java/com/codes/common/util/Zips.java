package com.codes.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 压缩工具类
 * 
 * @author zhangguangyong 2015年3月10日 下午7:23:59
 */
public class Zips {

	/**
	 * 压缩
	 * 
	 * @param source
	 *            源(文件或目录)
	 * @param target
	 *            目标(只能是目录)
	 * @throws IOException
	 */
	public static void zip(File source, File target) throws IOException {
		zip(source, target, null);
	}

	/**
	 * 压缩
	 * 
	 * @param source
	 *            源(文件或目录)
	 * @param target
	 *            目标(只能是目录)
	 * @throws IOException
	 */
	public static void zip(File[] sources, File target) throws IOException {
		zip(sources, target, null);
	}

	/**
	 * 压缩
	 * 
	 * @param source
	 *            源(文件或目录)
	 * @param target
	 *            目标(只能是目录)
	 * @param compressFileName
	 *            压缩后的文件名称(如果为Null,则使用源的名称)
	 * @throws Exception
	 */
	public static void zip(File source, File target, String compressFileName) throws IOException {
		$.notNull(source);
		zip(new File[] { source }, target, compressFileName);
	}

	/**
	 * 压缩
	 * 
	 * @param sources
	 *            源(文件或目录)可以是多个文件和多个文件夹
	 * @param target
	 *            目标(只能是目录)
	 * @param compressFileName
	 *            压缩后的文件名称(如果为Null,则使用源的名称)
	 * @throws Exception
	 */
	public static void zip(File[] sources, File target, String compressFileName) throws IOException {
		$.notEmpty(sources);
		$.notNull(target);
		if (!target.isDirectory()) {
			throw new IllegalArgumentException("The target can be a directory");
		}
		// 压缩后文件的名称
		compressFileName = $.notEmpty(compressFileName) ? compressFileName : sources[0].getName() + ".zip";
		FileOutputStream fileOut = new FileOutputStream(target.getAbsolutePath() + File.separator + compressFileName);
		ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(fileOut);
		for (int i = 0; i < sources.length; i++) {
			compress(sources[i], zipOut, null);
		}
		zipOut.close();
	}

	/**
	 * 压缩
	 * 
	 * @param source
	 *            文件或目录
	 * @param zipOut
	 *            压缩输出流
	 * @param zipEntryName
	 *            压缩文件名称
	 * @throws IOException
	 */
	private static void compress(File source, ZipArchiveOutputStream zipOut, String zipEntryName) throws IOException {

		String tempZipEntryName = $.notEmpty(zipEntryName) ? zipEntryName : source.getName();

		byte[] buff = new byte[512];
		int readLen = -1;
		if (source.isFile()) {
			// System.out.println("压缩文件：" + source.getAbsolutePath());
			ZipArchiveEntry entry = new ZipArchiveEntry(tempZipEntryName);
			zipOut.putArchiveEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
			while ((readLen = bis.read(buff)) != -1) {
				zipOut.write(buff, 0, readLen);
			}
			zipOut.closeArchiveEntry();
			IOUtils.closeQuietly(bis);
		} else if (source.isDirectory()) {
			// System.out.println("**** 压缩目录：" + source.getAbsolutePath() +
			ZipArchiveEntry entry = new ZipArchiveEntry(tempZipEntryName.concat(File.separator));
			zipOut.putArchiveEntry(entry);
			zipOut.closeArchiveEntry();
			File[] files = source.listFiles();
			if ($.notEmpty(files)) {
				for (File file : files) {
					compress(file, zipOut, tempZipEntryName + File.separator + file.getName());
				}
			}
		}
	}

	/**
	 * 解压缩
	 * 
	 * @param source
	 *            源(压缩文件)
	 * @param target
	 *            压缩输出文件夹
	 * @throws Exception
	 */
	public static void unzip(File source, File target) throws Exception {
		$.notNull(source);
		$.notNull(target);
		if (!source.isFile()) {
			throw new IllegalArgumentException("The source must be a file");
		}
		if (!target.isDirectory()) {
			throw new IllegalArgumentException("The target must be a directory");
		}
		// 压缩后文件的名称
		ZipFile zipFile = new ZipFile(source);
		Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
		while (entries.hasMoreElements()) {
			ZipArchiveEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				FileUtils.forceMkdir(new File(target, entry.getName()));
			} else {
				IOUtils.copy(zipFile.getInputStream(entry), new FileOutputStream(new File(target, entry.getName())));
			}
		}
		zipFile.close();
	}

}
