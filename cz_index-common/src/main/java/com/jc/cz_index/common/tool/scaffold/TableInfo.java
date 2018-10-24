package com.jc.cz_index.common.tool.scaffold;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableInfo
{
	private static final String	ID		= "id";
	private final String		ENDL	= "\n";
	private final String		TAB		= "    ";
	private final String		TAB2	= TAB + TAB;
	private final String		TAB3	= TAB2 + TAB;
	private final String		TAB4	= TAB2 + TAB2;
	private String				name;
	private String				primaryKey;
	private List<ColumnInfo>	columns;
	private List<FieldInfo>		fields;
	
	
	public TableInfo(String name)
	{
		this.name = name;
	}
	

	public String getName()
	{
		return name;
	}
	

	public void setName(String name)
	{
		this.name = name;
	}
	

	public String getPrimaryKey()
	{
		return primaryKey;
	}
	

	public void setPrimaryKey(String primaryKey)
	{
		this.primaryKey = primaryKey;
	}
	

	public List<ColumnInfo> getColumns()
	{
		return columns;
	}
	

	public void setColumns(List<ColumnInfo> columns)
	{
		this.columns = columns;
	}
	

	public void addColumn(ColumnInfo column)
	{
		if (columns == null)
			columns = new ArrayList<ColumnInfo>();
		if (!columns.contains(column))
		{
			columns.add(column);
			String type = column.parseJavaType();
			String name = column.parseFieldName();
			String columnExplain=column.getComments();
			addFiled(new FieldInfo(type, name,columnExplain));
		}
	}
	

	public void addFiled(FieldInfo field)
	{
		if (fields == null)
			fields = new ArrayList<FieldInfo>();
		if (field.getName().equalsIgnoreCase(ID))
			return;
		if (!fields.contains(field))
		{
			fields.add(field);
		}
		
	}
	

	public List<FieldInfo> getFields()
	{
		return fields;
	}
	

	public String getFieldsDeclareInfo()
	{
		StringBuffer sb = new StringBuffer();
		for (FieldInfo field : fields)
		{
			if (field.getName().equalsIgnoreCase(ID))
			{
				continue;// id property is in the BaseModel
			}
			sb.append(TAB);
			sb.append("//");
			sb.append(getComments(field));
			sb.append(ENDL);
			sb.append(TAB);
			sb.append("private ");
			sb.append(field.getType());
			sb.append(" ");
			sb.append(field.getName());
			sb.append(";");
			sb.append(ENDL);
		}
		
		for (FieldInfo field : fields)
		{
			if (field.getName().equalsIgnoreCase(ID))
			{
				continue;// id property is in the BaseModel
			}
			String fieldName = field.getName();
			String replaceFirst = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
			sb.append("    public void set" + replaceFirst);
			sb.append("(" + field.getType() + " " + fieldName + ")").append(ENDL);
			sb.append("    {\n");
			sb.append("        this." + fieldName + "=" + fieldName + ";\n");
			sb.append("    }\n");
			sb.append("    public " + field.getType() + " get" + replaceFirst);
			sb.append("()").append(ENDL);
			sb.append("    {\n");
			sb.append("        return this." + fieldName + ";\n");
			sb.append("    }\n");
		}
		
		return sb.toString();
	}
	
	private String getComments(FieldInfo field)
	{
		String str = "";
		for (ColumnInfo ci : columns)
		{
			if (field.getName().equals(ci.parseFieldName()))
			{
				str = ci.getComments();
			}
		}
		return str;
	}
	
	public String getInsertStatement()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("insert into " + name.toUpperCase());
		sb.append("( " + this.getColumnNames() + " ) values (");
		ColumnInfo col = null;
		sb.append("#{id},");
		for (int i = 0; i < columns.size(); i++)
		{
			col = columns.get(i);
			sb.append("#{" + col.parseFieldName() + "}");
			if (i + 1 != columns.size())
			{
				sb.append(",");
			}
		}
		sb.append(" )");
		return sb.toString();
	}
	
	public String getUpdateStatement()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("update " + name.toUpperCase() + " set ");
		ColumnInfo col = null;
		for (int i = 0; i < columns.size(); i++)
		{
			col = columns.get(i);
			sb.append(col.getName() + "=#{" + col.parseFieldName() + "}");
			if (i + 1 != columns.size())
			{
				sb.append(",");
			}
		}
		sb.append(" where " + primaryKey + "=#{id}");
		return sb.toString();
	}
	

	public String getResultMap()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(TAB3);
		sb.append("<result property=\"id\"");
		sb.append(" column=\"" + primaryKey + "\"");
		sb.append(" />");
//		sb.append(" jdbcType=\"NUMERIC\" />");
		sb.append(ENDL);
		for (ColumnInfo col : columns)
		{
			sb.append(TAB3);
			sb.append("<result property=\"" + col.parseFieldName() + "\" column=\"" + col.getName());
//			if (!StringUtil.isBlank(col.parseJdbcType()))
//			{
//				sb.append("\" jdbcType=\"" + col.parseJdbcType());
//			}
			sb.append("\" />");
			
			sb.append(ENDL);
		}
		return sb.toString();
	}
	
	public String getBaseCol(){
		StringBuffer sb = new StringBuffer();
		sb.append(TAB2);
		sb.append(getPrimaryKey());
		for (ColumnInfo col : columns)
		{
			sb.append("," + col.getName());
		}
		sb.append(ENDL);
		return sb.toString();
	}
	
	public String getWhereSql(){
		StringBuffer sb = new StringBuffer();
		sb.append(TAB3);
		sb.append("<if test=\"id != null\"><![CDATA[ and " +getPrimaryKey()+ " = #{id} ]]></if>");
		sb.append(ENDL);
		sb.append(TAB3);
		sb.append("<if test=\"id_r_min != null\"><![CDATA[ and " +getPrimaryKey()+ " >= #{id_r_min} and id <= #{id_r_max} ]]></if>");
		sb.append(ENDL);
		sb.append(TAB3);
		sb.append("<if test=\"id_enum != null\">");
		sb.append(ENDL);
		sb.append(TAB4);
		sb.append("<![CDATA[ and id in ]]>");
		sb.append(ENDL);
		sb.append(TAB4);
		sb.append("<foreach collection=\"id_enum\" item=\"value\" open=\"(\" separator=\",\" close=\")\">");
		sb.append(ENDL);
		sb.append(TAB2+TAB3);
		sb.append("<![CDATA[ #{value} ]]>");
		sb.append(ENDL);
		sb.append(TAB4);
		sb.append("</foreach>");
		sb.append(ENDL);
		sb.append(TAB3);
		sb.append("</if>");
		sb.append(ENDL);
		for (ColumnInfo col : columns)
		{
			sb.append(TAB3);
			sb.append("<if test=\"" +col.parseFieldName()+ " != null\"><![CDATA[ and ");
			sb.append(" " + col.getName() + " = #{" + col.parseFieldName());
			if (!StringUtil.isBlank(col.parseJdbcType()))
			{
				sb.append(":" + col.parseJdbcType());
			}
			sb.append("}");
			sb.append("]]></if>");
			sb.append(ENDL);
			
			sb.append(TAB3);
			sb.append("<if test=\""+col.parseFieldName()+"_r_min != null\"><![CDATA[ and " +col.getName()+ " >= #{"
					+ col.parseFieldName()+"_r_min} and "+col.getName()+" <= #{"+col.parseFieldName()+"_r_max} ]]></if>");
			sb.append(ENDL);
			sb.append(TAB3);
			sb.append("<if test=\""+col.parseFieldName()+"_enum != null\">");
			sb.append(ENDL);
			sb.append(TAB4);
			sb.append("<![CDATA[ and "+col.getName()+" in ]]>");
			sb.append(ENDL);
			sb.append(TAB4);
			sb.append("<foreach collection=\""+col.parseFieldName()+"_enum\" item=\"value\" open=\"(\" separator=\",\" close=\")\">");
			sb.append(ENDL);
			sb.append(TAB2+TAB3);
			sb.append("<![CDATA[ #{value} ]]>");
			sb.append(ENDL);
			sb.append(TAB4);
			sb.append("</foreach>");
			sb.append(ENDL);
			sb.append(TAB3);
			sb.append("</if>");
			sb.append(ENDL);
		}
		return sb.toString();
	}
	
	public String getOrderSql(){
		StringBuffer sb = new StringBuffer();
		sb.append(TAB3);
		sb.append("<if test=\"_orderBy == 'id_desc'\"><![CDATA[ id desc, ]]></if>");
		sb.append(ENDL);
		sb.append(TAB3);
		sb.append("<if test=\"_orderBy == 'id_asc'\"><![CDATA[ id, ]]></if>");
		sb.append(ENDL);
		for (ColumnInfo col : columns)
		{
			sb.append(TAB3);
			sb.append("<if test=\"_orderBy == '" +col.parseFieldName()+ "_desc'\" ><![CDATA[ "+col.getName()+" desc, ]]></if>");
			sb.append(ENDL);
			sb.append(TAB3);
			sb.append("<if test=\"_orderBy == '" +col.parseFieldName()+ "_asc'\" ><![CDATA[ "+col.getName()+", ]]></if>");
			sb.append(ENDL);
		}
		
		sb.append(TAB3);
		sb.append("<if test=\"_orderBy_enum != null\">");
		sb.append(ENDL);
		sb.append(TAB4);
		sb.append("<foreach collection=\"_orderBy_enum\" item=\"orderItem\" open=\"\" separator=\",\" close=\"\">");
		sb.append(ENDL);
		
		sb.append(TAB2+TAB3);
		sb.append("<if test=\"orderItem == 'id_desc'\"><![CDATA[ id desc ]]></if>");
		sb.append(ENDL);
		sb.append(TAB2+TAB3);
		sb.append("<if test=\"orderItem == 'id_asc'\"><![CDATA[ id ]]></if>");
		sb.append(ENDL);
		for (ColumnInfo col : columns)
		{
			sb.append(TAB2+TAB3);
			sb.append("<if test=\"orderItem == '" +col.parseFieldName()+ "_desc'\" ><![CDATA[ "+col.getName()+" desc ]]></if>");
			sb.append(ENDL);
			sb.append(TAB2+TAB3);
			sb.append("<if test=\"orderItem == '" +col.parseFieldName()+ "_asc'\" ><![CDATA[ "+col.getName()+" ]]></if>");
			sb.append(ENDL);
		}
		sb.append(TAB4);
		sb.append("</foreach>");
		sb.append(ENDL);
		sb.append(TAB3);
		sb.append("</if>");
		sb.append(ENDL);
		
		return sb.toString();
	}
	
	public String getUpdateSetSql() {
		StringBuffer sb = new StringBuffer();
		for (ColumnInfo col : columns)
		{
			sb.append(TAB3);
			sb.append("<if test=\"" +col.parseFieldName()+ " != null\" ><![CDATA[ and ");
			sb.append(" " + col.getName() + " = #{" + col.parseFieldName() +"}]]></if>");
			sb.append(ENDL);
		}
		return sb.toString();
	}
	
	private String getColumnNames()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(primaryKey + ",");
		ColumnInfo column = null;
		for (int i = 0; i < columns.size(); i++)
		{
			column = columns.get(i);
			sb.append(column.getName());
			if (i + 1 != columns.size())
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @Title: fillCommentsForColumns
	 * @Description: TODO
	 * @param comments
	 * @return void
	 * @throws
	 */
	public void fillCommentsForColumns(Map<String, String> comments)
	{
		if (comments == null)
		{
			return;
		}
		if (comments.size() == 0)
		{
			return;
		}
		for (ColumnInfo col : this.columns)
		{
			String colName = col.getName().toUpperCase();
			if (!(comments.containsKey(colName)))
			{
				continue;
			}
			col.setComments(comments.get(colName));
		}
		
	}
	
	
	
    /**
     * 
     * 描述：获取批量插入字段
     * 
     * @return
     * @author yangyongchuan 2017年12月1日 上午10:33:12
     * @version 1.0
     */
    public String getInsertListFields() {
        StringBuffer sb = new StringBuffer();
//        sb.append(TAB);
        for (ColumnInfo col : columns) {
            sb.append(col.getName()).append(",");
        }
        String insertListFields = sb.substring(0, sb.length() - 1);
        return insertListFields;
    }



    /**
     * 
     * 描述：获取批量插入字段values
     * 
     * @return
     * @author yangyongchuan 2017年12月1日 上午10:33:20
     * @version 1.0
     */
    public String getInsertListFieldsValues() {
        StringBuffer sb = new StringBuffer();
//        sb.append(TAB);
        for (FieldInfo field : fields) {
            sb.append("#{item.").append(field.getName()).append("}").append(",");
        }
        String insertListFieldsValues = sb.substring(0, sb.length() - 1);
        return insertListFieldsValues;
    }
}
