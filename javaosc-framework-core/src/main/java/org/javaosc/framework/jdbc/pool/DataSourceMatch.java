package org.javaosc.framework.jdbc.pool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.javaosc.framework.assist.PropertyConvert;
import org.javaosc.framework.constant.Constant;
import org.javaosc.framework.context.ConfigurationHandler;
import org.javaosc.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceMatch {
	
	private static final Logger log = LoggerFactory.getLogger(DataSourceMatch.class);
	
	public static DataSource get(){
		DataSource ds = null; 
		String dataSourceName = ConfigurationHandler.getDataSourceName();
		if(StringUtil.isBlank(dataSourceName)){
			log.error("pool.dataSource must be not null!");
		}
		
		if(dataSourceName.indexOf("java:")==0){ //tomcat jdbc
			try {
				Context c = new InitialContext();
				ds = (DataSource)c.lookup(dataSourceName);
			} catch (NamingException e) {
				log.error(Constant.JAVAOSC_EXCEPTION, e);
			}
		}else if(dataSourceName.indexOf(".c3p0.")>0){ //c3p0 pool
			C3p0Handler c3p0Handler = null;
			try {
				c3p0Handler = PropertyConvert.convertMapToEntity(ConfigurationHandler.getPoolParam(), C3p0Handler.class);
				ds = c3p0Handler.getDataSource();
			} catch (Exception e) {
				log.error(Constant.JAVAOSC_EXCEPTION, e);
			} 	
		}
		return ds;
	}
}