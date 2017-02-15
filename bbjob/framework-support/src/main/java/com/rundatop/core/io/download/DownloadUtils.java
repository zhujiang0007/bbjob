package com.rundatop.core.io.download;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class DownloadUtils {
	
	public static ResponseEntity<byte[]> download(String fileName, File file,String charset) throws IOException {
		String  dfileName = new String(fileName.getBytes(charset), "iso8859-1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", dfileName);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	/**
	 * 默认utf-8字符集
	 * @param fileName
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> download(String fileName, File file) throws IOException {
		String  dfileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", dfileName);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
}
