/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.gitee.mybatis.fl.util
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.gitee.flying.cattle.mdg.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.gitee.flying.cattle.mdg.entity.BasisInfo;
import com.gitee.flying.cattle.mdg.entity.PropertyInfo;
import freemarker.template.utility.CollectionUtils;

/**
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明：  链接数据库并获取表信息</P>
 * @version: v3.0.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version          Description
 *---------------------------------------------------------------*
 * 2019年4月9日      		flying-cattle   v3.0.0           initialize
 */
public class EntityInfoUtil {
	public static Map<String,Object> getInfo(BasisInfo bi,boolean isGenerate) throws SQLException {
		Map<String,Object> resultInfo = new HashMap<>();
		List<Map<String,Object>> allTableNames = new ArrayList<>();
		List<PropertyInfo> columns= new ArrayList<PropertyInfo>();
		// 创建连接
		Connection con = null;
		PreparedStatement pstemt = null;
		ResultSet rs = null;
		//sql(mysql)
		//String sql="select column_name,data_type,'' column_comment from information_schema.columns where table_catalog='"+bi.getDatabase()+"' and table_name='"+bi.getTable()+"'";
		//postgresql
		String sql = "SELECT a.attname as column_name,format_type(a.atttypid,a.atttypmod) as data_type,col_description(a.attrelid,a.attnum) as column_comment FROM pg_class as c,pg_attribute as a where c.relname = '"+bi.getTable()+"' and a.attrelid = c.oid and a.attnum>0";
		try {
			con = DriverManager.getConnection(bi.getDbUrl(), bi.getDbName(), bi.getDbPassword());
			//获取全部表名字
			if(!isGenerate) {
				ResultSet tablesResults = con.getMetaData().getTables(bi.getDatabase(), null, null, new String[]{"TABLE"});
				while (tablesResults.next()) {
					Map<String, Object> tableInfo = new HashMap<>();
					tableInfo.put("tableName", tablesResults.getString(3));
					tableInfo.put("tableComment", tablesResults.getString("REMARKS"));
					allTableNames.add(tableInfo);
					//System.out.println(tablesResults.getString(3)+"::::"+tablesResults.getString("REMARKS"));
				}
				if (!allTableNames.isEmpty()) {
					resultInfo.put("allTables", allTableNames);
				}
			}

			if(isGenerate) {
				pstemt = con.prepareStatement(sql);
				rs = pstemt.executeQuery();
				while (rs.next()) {
					String column = rs.getString(1);
					String jdbcType = rs.getString(2);
					String comment = rs.getString(3);
					PropertyInfo ci = new PropertyInfo();
					ci.setColumn(column);
					if (jdbcType.equalsIgnoreCase("int")) {
						ci.setJdbcType("Integer");
					} else if (jdbcType.equalsIgnoreCase("datetime")) {
						ci.setJdbcType("timestamp");
					} else {
						ci.setJdbcType(jdbcType);
					}
					ci.setComment(comment);
					ci.setProperty(MySqlToJavaUtil.changeToJavaFiled(column));
					ci.setJavaType(MySqlToJavaUtil.jdbcTypeToJavaType(jdbcType));
					//设置注解类型
					if (column.equalsIgnoreCase("id")) {
						bi.setIdType(ci.getJavaType());
						bi.setIdJdbcType(ci.getJdbcType());
					}
					columns.add(ci);
					//添加包路径
					Set<String> pkgs = bi.getPkgs();
					pkgs.add(MySqlToJavaUtil.jdbcTypeToJavaTypePck(jdbcType));
					bi.setPkgs(pkgs);
				}
				bi.setCis(columns);
				// 完成后关闭
				rs.close();
				pstemt.close();
				con.close();
				if (null == columns || columns.size() == 0) {
					throw new RuntimeException("未能读取到表或表中的字段。请检查链接url，数据库账户，数据库密码，查询的数据名、是否正确。");
				}
				resultInfo.put("basisInfo", bi);
			}
			return resultInfo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("自动生成实体类错误："+e.getMessage());
		} finally {
            try{
                if(rs!=null) rs.close();
            }catch(SQLException se2){
            }
			// 关闭资源
            try{
                if(pstemt!=null) pstemt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(con!=null) con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
		}
	}
}
