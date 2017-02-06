package com.alipay.config;

import java.io.InputStream;
import java.util.Properties;

import com.fivestars.interfaces.bbs.client.Client;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "";
	// 商户的私钥
	public static String key = "";
	// 支付宝帐号
	public static String zhifubaozhanghao = "";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "";
	
	// 签名方式 不需修改
	public static String sign_type = "";
	
	// 是否开启支付
	public static String zhifu_type = "";
	
	// 是否开启支付
	public static String zhifu_istype = "0";
	
	public static String getPartner() {
		return partner;
	}

	public static void setPartner(String partner) {
		AlipayConfig.partner = partner;
	}

	public static String getKey() {
		return key;
	}

	public static void setKey(String key) {
		AlipayConfig.key = key;
	}

	public static String getLog_path() {
		return log_path;
	}

	public static void setLog_path(String log_path) {
		AlipayConfig.log_path = log_path;
	}

	public static String getInput_charset() {
		return input_charset;
	}

	public static void setInput_charset(String input_charset) {
		AlipayConfig.input_charset = input_charset;
	}

	public static String getSign_type() {
		return sign_type;
	}

	public static void setSign_type(String sign_type) {
		AlipayConfig.sign_type = sign_type;
	}
	
	public static String getZhifubaozhanghao() {
		return zhifubaozhanghao;
	}

	public static void setZhifubaozhanghao(String zhifubaozhanghao) {
		AlipayConfig.zhifubaozhanghao = zhifubaozhanghao;
	}
	
	public static String getZhifu_type() {
		return zhifu_type;
	}

	public static void setZhifu_type(String zhifu_type) {
		AlipayConfig.zhifu_type = zhifu_type;
	}
	
	public static String getZhifu_istype() {
		return zhifu_istype;
	}

	public static void setZhifu_istype(String zhifu_istype) {
		AlipayConfig.zhifu_istype = zhifu_istype;
	}

	static {
	   /* InputStream in = Client.class.getClassLoader().getResourceAsStream("alipay.properties");
	    Properties properties = new Properties();
	    try {
			properties.load(in);
			partner = properties.getProperty("partner");
			key = properties.getProperty("key");
			log_path = properties.getProperty("log_path");
			input_charset = properties.getProperty("input_charset");
			sign_type = properties.getProperty("sign_type");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
