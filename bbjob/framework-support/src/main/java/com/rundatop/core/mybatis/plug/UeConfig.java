package com.rundatop.core.mybatis.plug;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.rundatop.core.config.SystemConfig;
import com.rundatop.core.spring.annotation.Config;
@Component("ueConfig")
@Lazy(false)
public class UeConfig{
	
	private static UeConfig config;
	
	private String ctx = "";
	
	//-------------------------------图片上传配置start-----------------------------------------
	private String imageActionName = "uploadImage";
	
	private String imageFieldName = "upfile";
	@Config(SystemConfig.UE_IMAGE_MAXSIZE)
	private long imageMaxSize = 2048000;
	@Config(SystemConfig.UE_IMAGE_COMPRESSENABLE)
	private boolean imageCompressEnable = true;//启用压缩
	@Config(SystemConfig.UE_IMAGE_COMPRESSBORDER)
	private int imageCompressBorder = 1000;
	@Config(SystemConfig.UE_IMAGE_ALLOWFILES)
	private String[] imageAllowFiles = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
	
	private String imageInsertAlign = "none";
	
	private String imageUrlPrefix ;
	
	private String imagePathFormat = "upload/image/{yyyy}{mm}{dd}/{time}{rand:6}";
	
	//-------------------------------图片上传配置end-----------------------------------------
	
	//-------------------------------视频上传配置start-----------------------------------------
	private String videoActionName = "uploadVideo";
	
	private String videoFieldName = "upfile";
	
	private String videoPathFormat = "upload/video/{yyyy}{mm}{dd}/{time}{rand:6}";
	
	private String videoUrlPrefix;
	@Config(SystemConfig.UE_VIDEO_MAXSIZE)
	private long videoMaxSize = 102400000;
	@Config(SystemConfig.UE_VIDEO_ALLOWFILES)
	private String[] videoAllowFiles = {".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg4", ".mpg",
	        ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid"};
	
	//-------------------------------视频上传配置end-----------------------------------------
	
	//-------------------------------文件上传配置start-----------------------------------------
	private String fileActionName = "uploadFile";
	
	private String fileFieldName = "upfile";
	
	private String filePathFormat = "upload/file/{yyyy}{mm}{dd}/{time}{rand:6}";
	
	private String fileUrlPrefix;
	@Config(SystemConfig.UE_FILE_MAXSIZE)
	private long fileMaxSize = 51200000;
	@Config(SystemConfig.UE_FILE_ALLOWFILES)
	private String[] fileAllowFiles = {".png", ".jpg", ".jpeg", ".gif", ".bmp",
	        ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg4", ".mpg",
	        ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
	        ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
	        ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml"};
	
	//-------------------------------文件上传配置end-----------------------------------------
	
	//-------------------------------列出指定目录下的图片start-----------------------------------------
	
	private String imageManagerActionName = "listImage";
	
	private String imageManagerListPath = "/upload/image/";
	
	private int imageManagerListSize = 20;
	
	private String imageManagerUrlPrefix;
	
	private String imageManagerInsertAlign = "none";
	private String[] imageManagerAllowFiles = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
	//-------------------------------列出指定目录下的图片end-----------------------------------------
	
	//-------------------------------列出指定目录下的文件start-----------------------------------------
	private String fileManagerActionName = "listFile";
	
	private String fileManagerListPath = "/upload/file/";
	
	private String fileManagerUrlPrefix;
	
	private int fileManagerListSize = 20;
	
	private String[] fileManagerAllowFiles = { ".png", ".jpg", ".jpeg", ".gif", ".bmp",
	        ".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg",
	        ".ogg", ".ogv", ".mov", ".wmv", ".mp4", ".webm", ".mp3", ".wav", ".mid",
	        ".rar", ".zip", ".tar", ".gz", ".7z", ".bz2", ".cab", ".iso",
	        ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".md", ".xml"};
	
	//-------------------------------列出指定目录下的文件end-----------------------------------------
	
	public String getCtx() {
		return SystemConfig.ctx;
	}

	public String getFileManagerActionName() {
		return fileManagerActionName;
	}

	public void setFileManagerActionName(String fileManagerActionName) {
		this.fileManagerActionName = fileManagerActionName;
	}

	public String getFileManagerListPath() {
		return getCtx()+fileManagerListPath;
	}

	public void setFileManagerListPath(String fileManagerListPath) {
		this.fileManagerListPath = fileManagerListPath;
	}

	public String getFileManagerUrlPrefix() {
		return getCtx();
	}

	public void setFileManagerUrlPrefix(String fileManagerUrlPrefix) {
		this.fileManagerUrlPrefix = fileManagerUrlPrefix;
	}

	public int getFileManagerListSize() {
		return fileManagerListSize;
	}

	public void setFileManagerListSize(int fileManagerListSize) {
		this.fileManagerListSize = fileManagerListSize;
	}

	public String[] getFileManagerAllowFiles() {
		return fileManagerAllowFiles;
	}

	public void setFileManagerAllowFiles(String[] fileManagerAllowFiles) {
		this.fileManagerAllowFiles = fileManagerAllowFiles;
	}

	public String getImageManagerActionName() {
		return imageManagerActionName;
	}

	public void setImageManagerActionName(String imageManagerActionName) {
		this.imageManagerActionName = imageManagerActionName;
	}

	public String getImageManagerListPath() {
		return getCtx()+imageManagerListPath;
	}

	public void setImageManagerListPath(String imageManagerListPath) {
		this.imageManagerListPath = imageManagerListPath;
	}

	public int getImageManagerListSize() {
		return imageManagerListSize;
	}

	public void setImageManagerListSize(int imageManagerListSize) {
		this.imageManagerListSize = imageManagerListSize;
	}

	public String getImageManagerUrlPrefix() {
		return getCtx();
	}

	public void setImageManagerUrlPrefix(String imageManagerUrlPrefix) {
		this.imageManagerUrlPrefix = imageManagerUrlPrefix;
	}

	public String getImageManagerInsertAlign() {
		return imageManagerInsertAlign;
	}

	public void setImageManagerInsertAlign(String imageManagerInsertAlign) {
		this.imageManagerInsertAlign = imageManagerInsertAlign;
	}

	public String[] getImageManagerAllowFiles() {
		return imageManagerAllowFiles;
	}

	public void setImageManagerAllowFiles(String[] imageManagerAllowFiles) {
		this.imageManagerAllowFiles = imageManagerAllowFiles;
	}

	public String getFileActionName() {
		return fileActionName;
	}

	public void setFileActionName(String fileActionName) {
		this.fileActionName = fileActionName;
	}

	public String getFileFieldName() {
		return fileFieldName;
	}

	public void setFileFieldName(String fileFieldName) {
		this.fileFieldName = fileFieldName;
	}

	public String getFilePathFormat() {
		return filePathFormat;
	}

	public void setFilePathFormat(String filePathFormat) {
		this.filePathFormat = filePathFormat;
	}

	public String getFileUrlPrefix() {
		return getCtx();
	}

	public void setFileUrlPrefix(String fileUrlPrefix) {
		this.fileUrlPrefix = fileUrlPrefix;
	}

	public long getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(long fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	public String[] getFileAllowFiles() {
		return fileAllowFiles;
	}

	public void setFileAllowFiles(String[] fileAllowFiles) {
		this.fileAllowFiles = fileAllowFiles;
	}

	public String getVideoActionName() {
		return videoActionName;
	}

	public void setVideoActionName(String videoActionName) {
		this.videoActionName = videoActionName;
	}

	public String getVideoFieldName() {
		return videoFieldName;
	}

	public void setVideoFieldName(String videoFieldName) {
		this.videoFieldName = videoFieldName;
	}

	public String getVideoPathFormat() {
		return videoPathFormat;
	}

	public void setVideoPathFormat(String videoPathFormat) {
		this.videoPathFormat = videoPathFormat;
	}

	public String getVideoUrlPrefix() {
		return getCtx();
	}

	public void setVideoUrlPrefix(String videoUrlPrefix) {
		this.videoUrlPrefix = videoUrlPrefix;
	}

	public long getVideoMaxSize() {
		return videoMaxSize;
	}

	public void setVideoMaxSize(long videoMaxSize) {
		this.videoMaxSize = videoMaxSize;
	}

	public String[] getVideoAllowFiles() {
		return videoAllowFiles;
	}

	public void setVideoAllowFiles(String[] videoAllowFiles) {
		this.videoAllowFiles = videoAllowFiles;
	}

	public void setCtx(String ctx) {
		this.ctx = ctx;
	}

	public String getImageActionName() {
		return imageActionName;
	}

	public void setImageActionName(String imageActionName) {
		this.imageActionName = imageActionName;
	}

	public String getImageFieldName() {
		return imageFieldName;
	}

	public void setImageFieldName(String imageFieldName) {
		this.imageFieldName = imageFieldName;
	}

	public long getImageMaxSize() {
		return imageMaxSize;
	}

	public void setImageMaxSize(long imageMaxSize) {
		this.imageMaxSize = imageMaxSize;
	}

	public boolean isImageCompressEnable() {
		return imageCompressEnable;
	}

	public void setImageCompressEnable(boolean imageCompressEnable) {
		this.imageCompressEnable = imageCompressEnable;
	}

	public int getImageCompressBorder() {
		return imageCompressBorder;
	}

	public void setImageCompressBorder(int imageCompressBorder) {
		this.imageCompressBorder = imageCompressBorder;
	}

	public String[] getImageAllowFiles() {
		return imageAllowFiles;
	}

	public void setImageAllowFiles(String[] imageAllowFiles) {
		this.imageAllowFiles = imageAllowFiles;
	}

	public String getImageInsertAlign() {
		return imageInsertAlign;
	}

	public void setImageInsertAlign(String imageInsertAlign) {
		this.imageInsertAlign = imageInsertAlign;
	}

	public String getImageUrlPrefix() {
		return getCtx();
	}

	public void setImageUrlPrefix(String imageUrlPrefix) {
		this.imageUrlPrefix = imageUrlPrefix;
	}

	public String getImagePathFormat() {
		return imagePathFormat;
	}

	public void setImagePathFormat(String imagePathFormat) {
		this.imagePathFormat = imagePathFormat;
	}

	public static UeConfig getConfig(){
		if(null == config)
			config = new UeConfig();
		return config;
	}
	
	 
}
