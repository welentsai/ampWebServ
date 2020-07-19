package com.momo.amp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Properties;

public class ConfigParams {
	public static Properties props = null;
	private static String resFile;

	private ConfigParams(){
		props = new Properties();
		
		try{
			
			// read property from Jar , packed by Maven
			InputStream in = this.getClass().getResourceAsStream("/MomoAmp.properties");
			
			//System.out.println("載入組態檔 "+resFile);
			props.load(in);
			//System.out.println(props);
			in.close();
			
//			// read external file, using FileInputStream
//			InputStream in = null;
//			if (in==null){
//				resFile = getClass().getResource("/MomoAmp.properties").getFile();
//				System.out.println("載入組態檔 "+resFile);
//				resFile = "./conf/MomoAmp.properties";
//				resFile = URLDecoder.decode(resFile,"UTF-8");
//				in = new FileInputStream(resFile);
//			}
//			System.out.println("載入組態檔 "+resFile);
//			props.load(in);
//			//System.out.println(props);
//			in.close();
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public static void reload(){
		try{
			if (props!=null){
				InputStream in = new FileInputStream(resFile);
				props.load(in);
				in.close();
			}
		}catch(Exception ex){ex.printStackTrace();}
	}
	
	public static String getPropertyValue(String propertyName) {
		if (props==null) new ConfigParams();
		String value = props.getProperty(propertyName);
		if (value==null){
			System.out.println("參數 "+propertyName+" 未設定!!");
			return "";
		}
		try{
			value = new String(value.getBytes("ISO8859-1"),"UTF-8");
		}catch(Exception e){e.printStackTrace();}
		
		return value;
	}
	
	public static void setPropertyValue(String propertyName, String propertyValue){
		if (props==null) new ConfigParams();
		props.put(propertyName,propertyValue);
		
		try{
			OutputStream out = new FileOutputStream(resFile);
			props.store(out,null);
		}catch(Exception ex){ex.printStackTrace();}
	}
	
//	public static void main(String args[]){
//		//java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
//		//ConfigParams.setPropertyValue("Bus_Date",sdf.format(new java.util.Date()));
//		System.out.println(ConfigParams.getPropertyValue("URL"));
//	}
}