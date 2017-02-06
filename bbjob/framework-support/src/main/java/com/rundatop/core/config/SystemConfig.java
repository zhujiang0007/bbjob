package com.rundatop.core.config;

import com.rundatop.core.spring.annotation.Config;






public class SystemConfig {

	public static int appModule = 1;// 应用模式:1开发，2生产
	
	public static int executorPoolSize = 30;// 异步任务池最大线程数
	public static String ctx="";
	public static String cdn_path="http://192.168.0.89:8999/proedu_resource/static/";
	public static String template_path = "http://192.168.0.75:8080/test/";
	public static String user_unque;
	public static String ftp_ip;
	public static String ftp_ports;
	public static String pu;
	public static String readheadimg;//上传头像的路径
	
	
	
	public static String ftp_base="F:/apache-tomcat-7.0.55/webapps/proedu-static/resource/vedio/";

	public static String secretKey;
	public static String getCoverpath() {
		return coverpath;
	}

	public static void setCoverpath(String coverpath) {
		SystemConfig.coverpath = coverpath;
	}

	public static String coverpath;
	public static String coverSuffix;
	public static String getCoverSuffix() {
		return coverSuffix;
	}

	public static void setCoverSuffix(String coverSuffix) {
		SystemConfig.coverSuffix = coverSuffix;
	}

	public static String getSecretKey() {
		return secretKey;
	}

	

	public static String getReadheadimg() {
		return readheadimg;
	}

	public static void setReadheadimg(String readheadimg) {
		SystemConfig.readheadimg = readheadimg;
	}

	public static void setSecretKey(String secretKey) {
		SystemConfig.secretKey = secretKey;
	}

	public  static String defaultLearnerUrl;
	
	public  static String defaultAdminUrl;
	
	public  static String defaultLecturersUrl;
	
	public static String uploadpath;
	
	public static String getUploadpath() {
		return uploadpath;
	}

	public static void setUploadpath(String uploadpath) {
		SystemConfig.uploadpath = uploadpath;
	}

	public static String getFtp_ports() {
		return ftp_ports;
	}
	public static String getPu() {
		return pu;
	}

	public static void setPu(String pu) {
		SystemConfig.pu = pu;
	}
	public static void setFtp_ports(String ftp_ports) {
		SystemConfig.ftp_ports = ftp_ports;
	}

	public static String getDefaultLearnerUrl() {
		return defaultLearnerUrl;
	}

	public static void setDefaultLearnerUrl(String defaultLearnerUrl) {
		SystemConfig.defaultLearnerUrl = defaultLearnerUrl;
	}

	public static String getDefaultAdminUrl() {
		return defaultAdminUrl;
	}

	public static void setDefaultAdminUrl(String defaultAdminUrl) {
		SystemConfig.defaultAdminUrl = defaultAdminUrl;
	}

	public static String getDefaultLecturersUrl() {
		return defaultLecturersUrl;
	}

	public static void setDefaultLecturersUrl(String defaultLecturersUrl) {
		SystemConfig.defaultLecturersUrl = defaultLecturersUrl;
	}

	public static String user;
	public static String ftp_res;
	public static String import_res;

	public static String getImport_res() {
		return import_res;
	}

	public static void setImport_res(String import_res) {
		SystemConfig.import_res = import_res;
	}

	public static String ftp_img;
	public static String getFtp_img() {
		return ftp_img;
	}

	public static void setFtp_img(String ftp_img) {
		SystemConfig.ftp_img = ftp_img;
	}

	public static String getFtp_res() {
		return ftp_res;
	}

	public static void setFtp_res(String ftp_res) {
		SystemConfig.ftp_res = ftp_res;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		SystemConfig.user = user;
	}

	public static String passwrod;
	
	public static String getPasswrod() {
		return passwrod;
	}

	public static void setPasswrod(String passwrod) {
		SystemConfig.passwrod = passwrod;
	}

	public static String getFtp_ip() {
		return ftp_ip;
	}

	public static void setFtp_ip(String ftp_ip) {
		SystemConfig.ftp_ip = ftp_ip;
	}

	public static String getUser_unque() {
		return user_unque;
	}

	public static void setUser_unque(String user_unque) {
		SystemConfig.user_unque = user_unque;
	}

	public static final String SYSTEM_NAME = "system.name";
	
	public static final String SYSTEM_BANNER = "system.banner";
	
	public static final String SYSTEM_COPYRIGHT = "system.copyright";
	
	public static final String SYSTEM_KEYWORD = "system.keyword";
	
	public static final String SYSTEM_DESCRIPTION = "system.description";
	
	public static final String SYSTEM_ARTICLE_CATEGORY = "system.atricle.category";
	
	public static final String SYSTEM_ATRICLE_COVER_SIZE = "system.atricle.cover.size";
	
	public static final String UE_IMAGE_MAXSIZE = "ue.image.maxsize";
	
	public static final String UE_IMAGE_COMPRESSENABLE = "ue.image.compressEnable";
	
	public static final String UE_IMAGE_COMPRESSBORDER = "ue.image.compressBorder";
	
	public static final String UE_IMAGE_ALLOWFILES = "ue.image.allowfiles";
	
	public static final String UE_VIDEO_MAXSIZE = "ue.video.maxsize";
	
	public static final String UE_VIDEO_ALLOWFILES = "ue.video.allowfiles";
	
	public static final String UE_FILE_MAXSIZE = "ue.file.maxsize";
	
	public static final String UE_FILE_ALLOWFILES = "ue.file.allowfiles";
	
	
	public static int getAppModule() {
		return appModule;
	}

	public static void setAppModule(int appModule) {
		SystemConfig.appModule = appModule;
	}

	public static int getExecutorPoolSize() {
		return executorPoolSize;
	}

	public static void setExecutorPoolSize(int executorPoolSize) {
		SystemConfig.executorPoolSize = executorPoolSize;
	}

	public static String getCtx() {
		return ctx;
	}

	public static void setCtx(String ctx) {
		SystemConfig.ctx = ctx;
	}

	public static String getCdn_path() {
		return cdn_path;
	}

	public static void setCdn_path(String cdn_path) {
		SystemConfig.cdn_path = cdn_path;
	}

	public static String getTemplate_path() {
		return template_path;
	}

	public static void setTemplate_path(String template_path) {
		SystemConfig.template_path = template_path;
	}
	

}
