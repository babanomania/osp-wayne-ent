package com.dchdemo.osp.wayneent;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class BaseEndpoint {

	private static String OSP_CONFIG = "osp_config.properties";
	private static Properties ospConfig = new Properties();
	
	static String url_isb;
	
	static{
		try
		{
			if(! StringUtils.isEmpty(System.getenv( "url_isb" )) ){
				url_isb = System.getenv( "url_isb" );
			}else{
				ospConfig.load( BaseEndpoint.class.getClassLoader().getResourceAsStream( OSP_CONFIG ) );
				url_isb = ospConfig.getProperty("url_isb");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
