package com.codes.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 创建数据库脚本 主要用于生成Oracle用户、表空间的创建脚本
 * 
 * @author zhangguangyong
 *
 *         2015年11月27日 下午6:21:19
 */
public class DbScripts {
	// 行分隔符
	static final String LINE_SEPARATOR = System.lineSeparator();
	// oracle表空间的后缀
	static final String ORACLE_TABLESPACE_SUFFIX = ".dbf";

	// 数据库类型
	public static enum DbType {
		ORACLE, MYSQL, SQLSERVER, DB2
	};

	private DbType dbType;

	public static DbScripts on(DbType dbType) {
		return new DbScripts(dbType);
	}

	private DbScripts(DbType dbType) {
		this.dbType = dbType;
	}

	// oracle 临时表空间
	private String tempTablespaceName;
	private File tempFile;
	private String tempInitSize = "32M";
	private String tempNextSize = "32M";
	private String tempMaxSize = "UNLIMITED";
	private String tempAutoextend = "ON";
	private String tempExtent = "MANAGEMENT LOCAL";

	public DbScripts tempTablespace(String tablespaceName) {
		this.tempTablespaceName = tablespaceName;
		return this;
	}

	public DbScripts tempFile(File file) {
		this.tempFile = file;
		return this;
	}

	public DbScripts tempInitSize(String size) {
		this.tempInitSize = size;
		return this;
	}

	public DbScripts tempAutoextend(String autoextend) {
		this.tempAutoextend = autoextend;
		return this;
	}

	public DbScripts tempNextSize(String nextSize) {
		this.tempNextSize = nextSize;
		return this;
	}

	public DbScripts tempMaxSize(String maxSize) {
		this.tempMaxSize = maxSize;
		return this;
	}

	public DbScripts tempExtent(String extent) {
		this.tempExtent = extent;
		return this;
	}

	public String toCreateTemporaryTablespaceScript() {
		$.checkNotNull(tempTablespaceName);
		$.checkNotNull(tempFile);

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TEMPORARY TABLESPACE " + tempTablespaceName).append(LINE_SEPARATOR)
				.append("\tTEMPFILE '" + tempFile.getPath() + "'").append(LINE_SEPARATOR)
				.append("\tSIZE " + tempInitSize).append(LINE_SEPARATOR).append("\tAUTOEXTEND " + tempAutoextend)
				.append(LINE_SEPARATOR).append("\tNEXT " + tempNextSize + " MAXSIZE " + tempMaxSize)
				.append(LINE_SEPARATOR).append("\tEXTENT " + tempExtent + ";");
		return sb.toString();
	}

	// oracle 表空间
	private String tablespaceName;
	private File dataFile;
	private String initSize = "32M";
	private String nextSize = "32M";
	private String maxSize = "UNLIMITED";
	private String autoextend = "ON";
	private String extent = "MANAGEMENT LOCAL";

	public DbScripts tablespace(String tablespaceName) {
		this.tablespaceName = tablespaceName;
		return this;
	}

	public DbScripts dataFile(File file) {
		this.dataFile = file;
		return this;
	}

	public DbScripts initSize(String size) {
		this.initSize = size;
		return this;
	}

	public DbScripts autoextend(String autoextend) {
		this.autoextend = autoextend;
		return this;
	}

	public DbScripts nextSize(String nextSize) {
		this.nextSize = nextSize;
		return this;
	}

	public DbScripts maxSize(String maxSize) {
		this.maxSize = maxSize;
		return this;
	}

	public DbScripts extent(String extent) {
		this.extent = extent;
		return this;
	}

	public String toCreateTablespaceScript() {
		$.checkNotNull(tablespaceName);
		$.checkNotNull(dataFile);

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLESPACE " + tablespaceName).append(LINE_SEPARATOR).append("\tLOGGING")
				.append(LINE_SEPARATOR).append("\tDATAFILE '" + dataFile.getPath() + "'").append(LINE_SEPARATOR)
				.append("\tSIZE " + initSize).append(LINE_SEPARATOR).append("\tAUTOEXTEND " + autoextend)
				.append(LINE_SEPARATOR).append("\tNEXT " + nextSize + " MAXSIZE " + maxSize).append(LINE_SEPARATOR)
				.append("\tEXTENT " + extent + ";");
		return sb.toString();
	}

	public DbType getDbType() {
		return dbType;
	}

	// oracle user
	private String username;
	private String password;
	private String account = "UNLOCK";
	private String userDefaultTablespace;
	private String userTemporaryTablespace;

	public DbScripts username(String username) {
		this.username = username;
		return this;
	}

	public DbScripts password(String password) {
		this.password = password;
		return this;
	}

	public DbScripts account(String account) {
		this.account = account;
		return this;
	}

	public DbScripts userDefaultTablespace(String defaultTablespace) {
		this.userDefaultTablespace = defaultTablespace;
		return this;
	}

	public DbScripts userTemporaryTablespace(String temporaryTablespace) {
		this.userTemporaryTablespace = temporaryTablespace;
		return this;
	}

	public String toCreateUserScript() {
		$.checkNotNull(username);
		$.checkNotNull(password);

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE USER " + username + " IDENTIFIED BY " + password).append(LINE_SEPARATOR)
				.append("\tACCOUNT " + account).append(LINE_SEPARATOR)
				.append("\tDEFAULT TABLESPACE " + userDefaultTablespace).append(LINE_SEPARATOR)
				.append("\tTEMPORARY TABLESPACE " + userTemporaryTablespace + ";");
		return sb.toString();
	}

	// oracle grant
	private List<String> permissions;

	public DbScripts grant(String firstPermission, String... morePermissions) {
		$.checkNotNull(firstPermission);
		permissions = new ArrayList<String>();
		permissions.add(firstPermission);
		if ($.notEmpty(morePermissions)) {
			permissions.addAll(Arrays.asList(morePermissions));
		}
		return this;
	}

	public String toGrantScript() {
		$.checkNotNull(username);
		$.checkNotNull(permissions);
		return "GRANT " + Strings.join(permissions, " ") + " TO " + username + ";";
	}

	public static String createOracleUserScript(String username, String password, File saveDir) {
		String tablespaceName = "tbs_" + username;
		String tempTablespaceName = tablespaceName + "_temp";

		DbScripts dbScripts = DbScripts.on(DbType.ORACLE).username(username).password(password)
				.userDefaultTablespace(tablespaceName).userTemporaryTablespace(tempTablespaceName)
				.tempTablespace(tempTablespaceName)
				.tempFile(new File(saveDir, tempTablespaceName + ORACLE_TABLESPACE_SUFFIX)).tablespace(tablespaceName)
				.dataFile(new File(saveDir, tablespaceName + ORACLE_TABLESPACE_SUFFIX)).grant("DBA");

		StringBuilder sb = new StringBuilder();
		sb.append(dbScripts.toCreateTemporaryTablespaceScript()).append(LINE_SEPARATOR)
				.append(dbScripts.toCreateTablespaceScript()).append(LINE_SEPARATOR)
				.append(dbScripts.toCreateUserScript()).append(LINE_SEPARATOR).append(dbScripts.toGrantScript());
		return sb.toString();
	}

	public String getTempTablespaceName() {
		return tempTablespaceName;
	}

	public File getTempFile() {
		return tempFile;
	}

	public String getTempInitSize() {
		return tempInitSize;
	}

	public String getTempNextSize() {
		return tempNextSize;
	}

	public String getTempMaxSize() {
		return tempMaxSize;
	}

	public String getTempAutoextend() {
		return tempAutoextend;
	}

	public String getTempExtent() {
		return tempExtent;
	}

	public String getTablespaceName() {
		return tablespaceName;
	}

	public File getDataFile() {
		return dataFile;
	}

	public String getInitSize() {
		return initSize;
	}

	public String getNextSize() {
		return nextSize;
	}

	public String getMaxSize() {
		return maxSize;
	}

	public String getAutoextend() {
		return autoextend;
	}

	public String getExtent() {
		return extent;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getAccount() {
		return account;
	}

	public String getUserDefaultTablespace() {
		return userDefaultTablespace;
	}

	public String getUserTemporaryTablespace() {
		return userTemporaryTablespace;
	}

	public List<String> getPermissions() {
		return permissions;
	}

}
