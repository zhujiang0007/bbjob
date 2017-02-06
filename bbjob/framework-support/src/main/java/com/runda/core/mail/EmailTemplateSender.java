package com.runda.core.mail;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

public class EmailTemplateSender extends EmailSender {

	private static final Log logger = LogFactory.getLog(EmailTemplateSender.class);

	/**
	 * 注入FreeMarker基础组件类(用于发送模板邮件)
	 */
	private FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * 使用邮件模板,构造邮件内容
	 */
	private String getMailTextByTemplate(String ftlTemplate, String propertyName, String originalText) {

		String htmlText = originalText;

		try {
			// (01)
			// 通过指定的模板名称,获取FreeMarker模板实例
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate(ftlTemplate);
			// (02)
			// 通过Map,传递动态数据
			Map<String, String> map = new HashMap<String, String>();
			// 模板文件中的动态属性的属性名
			map.put(propertyName, originalText);

			// (03)
			// 解析模板,替换动态数据
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

		} catch (Exception e) {

			logger.warn("解析FreeMarker模板失败.使用原始的字符串内容:" + originalText);

			if (logger.isDebugEnabled()) {
				e.printStackTrace();
			}

		}

		return htmlText;

	}

	/**
	 * 发送模板邮件
	 */
	public void sendTemplateMimeMail(String ftlTemplate, String propertyName, MailEntity email) throws Exception {

		// 原始的字符串内容
		String originalText = email.getText();

		email.setText(this.getMailTextByTemplate(ftlTemplate, propertyName, originalText));

		this.sendMimeMail(email);

	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}
}
