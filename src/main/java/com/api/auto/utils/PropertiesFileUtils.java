package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils{
	private static String config_path="./configuration/configs.properties";
	private static String token_path="./configuration/token.properties";
	
	public static String getProperty(String key) {
		Properties properties=new Properties();					//để tạo ra đối tượng chứa các cặp khóa key và giá trị value như 1 chuỗi
		FileInputStream fileInputStream=null;
		String value=null;
		try {
			fileInputStream=new FileInputStream(config_path);
			properties.load(fileInputStream);
			value=properties.getProperty(key);
			return value;
		}catch(Exception ex) {
			System.out.println("Xảy ra lỗi khi đọc giá trị " +key);
			ex.printStackTrace();
		}finally {
			if(fileInputStream!=null) {
				try{
					fileInputStream.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	public static void saveToken(String token, String value) {
		Properties properties=new Properties();
		FileOutputStream fileOutputStream=null;									//ghi byte vào 1 tệp theo key/value
		try{
			fileOutputStream=new FileOutputStream(token_path);
	        properties.setProperty(token, value);
			properties.store(fileOutputStream, value);
		}catch(IOException e){
				System.out.println("Xảy ra lỗi khi lưu giá trị token "+token);
				e.printStackTrace();
		}finally {
				if(fileOutputStream!=null) {
					try{
						fileOutputStream.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
	}
	public static String getToken(String token) {
		Properties	properties=new Properties();
		FileInputStream fileInputStream=null;
		String value=null;
		try{
			fileInputStream=new FileInputStream(token_path);
	        properties.load(fileInputStream);
			value=properties.getProperty(token);
			return value;
		}catch(IOException e){
				System.out.println("Xảy ra lỗi khi lấy giá trị token "+token);
				e.printStackTrace();
		}finally {
				if(fileInputStream!=null) {
					try{
						fileInputStream.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		return value;
	}
}

