package com.jc.cz_index.common.tool.scaffold;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jc.cz_index.common.utils.ConfigFileUtil;


public class ScaffoldGen
{
	
	private static final String	NULLABLE			= "NULLABLE";
	private static final String	DECIMAL_DIGITS		= "DECIMAL_DIGITS";
	private static final String	COLUMN_SIZE			= "COLUMN_SIZE";
	private static final String	TYPE_NAME			= "TYPE_NAME";
	private static final String	SQLSERVER			= "sqlserver";
	private static final String	COLUMN_NAME			= "COLUMN_NAME";
	private static final String	JDBC_PASSWORD		= "jdbc.password";
	private static final String	JDBC_USER			= "jdbc.username";
	private static final String	JDBC_URL			= "jdbc.url";
	private static final String	JDBC_DRIVER			= "jdbc.driverClassName";
	private static final String	JDBC_SCHEMA			= "jdbc.schema";
	private static final String	CONFIG_PROPERTIES	= "com/jc/cz_index/common/tool/config/jdbc.properties";
	private final Log			log					= LogFactory.getLog(getClass());
	private Connection			conn;
	private String				schema;
	private DatabaseMetaData	metaData;
	private final String		pkgName;
	private final String		clzName;
	private final String        clientOrManager;   // fsl: 客户端还是管理端
	private final String		tblName;
	private Map<String,String>	columnExplains;
	private boolean 			checkRequiredCol=true;
	
	public void disableCheckRequiredCol()
	{
		checkRequiredCol=false;
	}
	
	
	/**
	 * @param pkgName
	 *            包路径
	 * @param clzName
	 *            model 名称
	 */
	public ScaffoldGen(String pkgName, String clzName, String clientOrManager)
	{
		this(pkgName, clzName, clzName + "s", clientOrManager);
	}
	

	/**
	 * @param pkgName
	 *            包路径
	 * @param clzName
	 *            model 名称
	 * @param clientOrManager
     *            客户端or管理端
	 * @param tblName
	 *            表名
	 */
	// fsl: 客户端还是管理端
	public ScaffoldGen(String pkgName, String clzName, String tblName, String clientOrManager)
	{
		this.pkgName = pkgName;
		this.clzName = StringUtils.capitalize(clzName);
		this.tblName = tblName.toUpperCase();
        this.clientOrManager = clientOrManager;
	}
	
	

	public void execute() throws Exception
	{
		execute(false);
	}
	

	public void execute(boolean debug) throws Exception
	{
		// 初始化数据库信息
		if (!initConnection())
		{
			System.out.println("数据库连接设备,请检查ClassPath路径下的配置文件" + CONFIG_PROPERTIES);
			return;
		}
		// 初始化表信息
		TableInfo tableInfo = parseDbTable(this.tblName);
		if (tableInfo == null)
		{
			return;
		}
		// 生成全套文件
		ScaffoldBuilder sf = new ScaffoldBuilder(this.pkgName, this.clzName, this.clientOrManager, tableInfo);
		List<FileGenerator> list = sf.buildGenerators();
		for (FileGenerator gen : list)
		{
			gen.execute(debug);
		}
	}
	

	/**
	 * 初始化数据库连接环境
	 * 
	 * @return
	 */
	private boolean initConnection()
	{
		Configuration config;
		String driver = null;
		String url = StringUtils.EMPTY;
		String user = StringUtils.EMPTY;
		String password = StringUtils.EMPTY;
		String schema = StringUtils.EMPTY;
		
		try
		{
			config = ConfigFileUtil.getPropertiesReader(CONFIG_PROPERTIES);
			driver = config.getString(JDBC_DRIVER);
			url = config.getString(JDBC_URL);
			user = config.getString(JDBC_USER);
			password = config.getString(JDBC_PASSWORD);
			schema = config.getString(JDBC_SCHEMA);
			if (StringUtil.isNotBlank(schema))
			{
				this.schema = schema;
			}
			if (driver.toLowerCase().contains(SQLSERVER))
			{
				this.schema = "dbo";
			}
			if (StringUtil.isBlank(this.schema))
			{
				this.schema = user.toUpperCase();
			}
			Class.forName(driver);
		}
//		catch (ConfigurationException e1)
//		{
//			e1.printStackTrace();
//			log.fatal("Jdbc connection config file not found - " + CONFIG_PROPERTIES);
//			return false;
//		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			log.fatal("Jdbc driver not found - " + driver);
			return false;
		}
		
		try
		{
			conn = DriverManager.getConnection(url, user, password);
			if (conn == null)
			{
				log.fatal("Database connection is null");
				return false;
			}
			// 数据元数据
			metaData = conn.getMetaData();
			if (metaData == null)
			{
				log.fatal("Database MetaData is null");
				return false;
			}
			
		}
		catch (SQLException e)
		{
			log.fatal("Database connect failed");
			e.printStackTrace();
		}
		return true;
	}
	

	/**
	 * 初始化表信息
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private TableInfo parseDbTable(String tableName) throws Exception
	{
		TableInfo tableInfo = new TableInfo(tableName);
		ResultSet rs = null;
		log.trace("parseDbTable begin");
		try
		{
			// 获取表的主键
			rs = metaData.getPrimaryKeys(conn.getCatalog(), schema, tableName);
			if (rs.next())
			{
				tableInfo.setPrimaryKey(rs.getString(COLUMN_NAME));
			}
			if (rs.next())
			{
				System.out.println("该表为复合主键，不适用于代码脚手架生成工具");
				return null;
			}
		}
		catch (SQLException e)
		{
			log.error("Table " + tableName + " parse error.");
			e.printStackTrace();
			return null;
		}
		log.info("PrimaryKey : " + tableInfo.getPrimaryKey());
		
		try
		{
			// 获取表的元信息
			rs = metaData.getColumns(conn.getCatalog(), schema, tableName, null);
			if (!rs.next())
			{
				log.fatal("Table " + schema + "." + tableName + " not found.");
				return null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			while (rs.next()) {
				String columnName = rs.getString(COLUMN_NAME);
				String columnType = rs.getString(TYPE_NAME);
				int datasize = rs.getInt(COLUMN_SIZE);
				int digits = rs.getInt(DECIMAL_DIGITS);
				int nullable = rs.getInt(NULLABLE);
				ColumnInfo colInfo = new ColumnInfo(columnName, columnType,
						datasize, digits, nullable);
				log.info("DB column : " + colInfo);
				log.info("Java field : " + colInfo.parseFieldName() + " / "
						+ colInfo.parseJavaType());
				tableInfo.addColumn(colInfo);
			}
			String sql = "select column_name,column_comment as comments from information_schema.columns a  where  a.Table_Name='"
					+ tableName + "' ";
			// tableName + "' and a.owner=b.owner and a.owner='" +
			// this.user.toUpperCase() + "'";
			rs = this.conn.createStatement().executeQuery(sql);
			Map comments = new HashMap();
			while (rs.next()) {
				String k = rs.getString("COLUMN_NAME");
				String v = rs.getString("COMMENTS");
				if (StringUtils.isNotBlank(v)) {
					comments.put(k.toUpperCase(), v);
				}
			}
			this.log.info(comments);
			tableInfo.fillCommentsForColumns(comments);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			log.error("Table " + tableName + " parse error.");
		}
		log.trace("parseDbTable end");
		return tableInfo;
	}
	
}
