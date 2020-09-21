package com.flying.cattle.mdg;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gitee.flying.cattle.mdg.entity.BasisInfo;
import com.gitee.flying.cattle.mdg.util.EntityInfoUtil;
import com.gitee.flying.cattle.mdg.util.Generator;
import com.gitee.flying.cattle.mdg.util.MySqlToJavaUtil;

/**
 * <p>说明： 自动生成工具</P>
 */
public class MyGenerator {
    // 基础信息：项目名、作者、版本
    public static final String PROJECT = "海上水文气象";
    public static final String AUTHOR = "binggleWang";
    public static final String VERSION = "V1.0";
    // 数据库连接信息：连接URL、用户名、秘密、数据库名
    public static final String URL = "jdbc:postgresql://localhost:5432/martitime-hydrometeorology?useSSL=false&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8&useInformationSchema=true";
    public static final String NAME = "postgres";
    public static final String PASS = "12345a";
    public static final String DATABASE = "martitime-hydrometeorology";
    // 类信息：类名、对象名（一般是【类名】的首字母小些）、类说明、时间
    //public static final String CLASSNAME = "friend";
    public static final String TABLE = "kono_mh_ship_type";
    public static final String CLASSCOMMENT = "我的项目";
    public static final String TIME = "2020年04月08日";
    public static final String AGILE = new Date().getTime() + "";
    // 路径信息，分开路径方便聚合工程项目，微服务项目
    //包名
    public static final String pre_package = "com.kono.mh";
    public static final String ENTITY_URL = pre_package + ".entity";
    public static final String DAO_URL = pre_package + ".mapper";
    public static final String XML_URL = pre_package + ".mapper.xml";
    public static final String SERVICE_URL = pre_package + ".service";
    public static final String SERVICE_IMPL_URL = pre_package + ".service.impl";
    public static final String CONTROLLER_URL = pre_package + ".web";
    //是否是Swagger配置
    public static final String IS_SWAGGER = "true";
		
	public static void main(String[] args) {
		BasisInfo bi = new BasisInfo(PROJECT, AUTHOR, VERSION, URL, NAME, PASS, DATABASE, TIME, AGILE,pre_package, ENTITY_URL,
				DAO_URL, XML_URL, SERVICE_URL, SERVICE_IMPL_URL, CONTROLLER_URL,IS_SWAGGER);
		bi.setTable(TABLE);
		bi.setEntityName(MySqlToJavaUtil.getClassName(TABLE));
		bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(TABLE));
		bi.setEntityComment(CLASSCOMMENT);
		try {
			Map basisAndTableInfo = EntityInfoUtil.getInfo(bi,true);
			bi = (BasisInfo)basisAndTableInfo.get("basisInfo");
			String fileUrl = "E:\\BaiduNetdiskDownload\\";// 生成文件存放位置
			//开始生成文件
			String aa1 = Generator.createEntity(fileUrl, bi).toString();
			String aa2 = Generator.createDao(fileUrl, bi).toString(); 
			String aa3 = Generator.createDaoImpl(fileUrl, bi).toString();
			String aa4 = Generator.createService(fileUrl, bi).toString(); 
			String aa5 = Generator.createServiceImpl(fileUrl, bi).toString(); 
			String aa6 = Generator.createController(fileUrl, bi).toString();
			// 是否创建swagger配置文件
			String aa7 = Generator.createSwaggerConfig(fileUrl, bi).toString();
			
			System.out.println(aa1);
			System.out.println(aa2); System.out.println(aa3); System.out.println(aa4);
			System.out.println(aa5); System.out.println(aa6); System.out.println(aa7);
			
			//System.out.println(aa7);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//生成全部
    /*public static void main(String[] args) throws Exception {
        BasisInfo bi = new BasisInfo(PROJECT, AUTHOR, VERSION, URL, NAME, PASS, DATABASE, TIME, AGILE, pre_package, ENTITY_URL,
                DAO_URL, XML_URL, SERVICE_URL, SERVICE_IMPL_URL, CONTROLLER_URL, IS_SWAGGER);
        Map basisAndTableInfo = EntityInfoUtil.getInfo(bi,false);
        List allTables = (List<Map<String,Object>>)basisAndTableInfo.get("allTables");

        for(Object item : allTables){
            Map table = (Map<String,Object>)item;
            String tableName = (String)table.get("tableName");
            String tableComment = (String)table.get("tableComment");

            bi.setTable(tableName);
            bi.setAgile(new Date().getTime() + "");
            bi.setEntityName(MySqlToJavaUtil.getClassName(tableName));
            bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(tableName));
            bi.setEntityComment(tableComment);
            Map basisAndTableInfoItem = EntityInfoUtil.getInfo(bi,true);
            bi = (BasisInfo) basisAndTableInfoItem.get("basisInfo");

            String fileUrl = "E:\\BaiduNetdiskDownload\\";// 生成文件存放位置
            //开始生成文件
            String aa1 = Generator.createEntity(fileUrl, bi).toString();
            String aa2 = Generator.createDao(fileUrl, bi).toString();
            String aa3 = Generator.createDaoImpl(fileUrl, bi).toString();
            String aa4 = Generator.createService(fileUrl, bi).toString();
            String aa5 = Generator.createServiceImpl(fileUrl, bi).toString();
            String aa6 = Generator.createController(fileUrl, bi).toString();
            System.out.println(aa1+","+aa2+","+aa3+","+aa4+","+aa5+","+aa6);
        }
    }*/
}
