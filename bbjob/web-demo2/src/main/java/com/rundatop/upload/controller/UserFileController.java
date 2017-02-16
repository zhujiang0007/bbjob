package com.rundatop.upload.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rundatop.core.utils.DateUtils;


@RestController
@RequestMapping("/userFile")
public class UserFileController{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 上传文件
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public Map<String, Object> upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, Object> result = new HashMap<String, Object>();
		String folder = "";
		try {
			folder = "E:/";
		} catch (Exception ex) {
			result.put("message", "获取folder失败");
			return result;
		}
		
		if (handler(request, response, result, folder)) {
			result.put("status", "success");
			result.put("message", "上传成功");
		}
		return result;
	}
	
	/**
	 * 处理文件上传
	 */
	public boolean handler(MultipartHttpServletRequest request, HttpServletResponse response, Map<String, Object> result, String folder) throws IOException{
		Date baseDate = new Date();
		String fileName = DateUtils.dateToString(baseDate, "yyyyMMddHHmmss");
		MultipartFile file = request.getFile("file");
		if (file == null) {// step-2 判断file
			return getError("文件内容为空", HttpStatus.UNPROCESSABLE_ENTITY, result, response, null);
		}
		String orgFileName = file.getOriginalFilename();
		orgFileName = (orgFileName == null) ? "" : orgFileName;
		Pattern p = Pattern.compile("\\s|\t|\r|\n");
        Matcher m = p.matcher(orgFileName);
        orgFileName = m.replaceAll("_");
		String realFilePath = folder  + File.separator + "admin" + File.separator;
		if(!(new File(realFilePath).exists())){
			new File(realFilePath).mkdirs();
		}
		String bigRealFilePath = realFilePath  + File.separator + FilenameUtils.getBaseName(orgFileName).concat(".") + fileName.concat(".").concat(FilenameUtils.getExtension(orgFileName).toLowerCase());
		if (file.getSize() > 0) {
			File targetFile = new File(bigRealFilePath);
			file.transferTo(targetFile);//写入目标文件
		}
//		//保存user file
//		UserFileDTO fileDTO = new UserFileDTO(1, 
//																		new Date(), 
//																		IpTool.getClientAddress(request), 
//																		orgFileName, 
//																		bigRealFilePath, 
//																		1);
//		fileDTO.setFileSize(String.valueOf(NumUtil.divideNumber(file.getSize(), 1024*1024)));
//		userFileService.save(fileDTO);
		return true;
	}
	
	boolean getError(String message, HttpStatus status, Map<String, Object> result, HttpServletResponse response, Exception ex) {
		response.setStatus(status.value());
		result.put("message", message);
//		LOG.warn(message, ex);
		return false;
	}
	
	
}