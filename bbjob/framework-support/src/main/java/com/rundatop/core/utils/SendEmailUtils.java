package com.rundatop.core.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.rundatop.core.spring.annotation.Config;

@Service("sendEmailUtils")
public class SendEmailUtils {
	
		//需要配置
		/*public static final String HOST = "smtp.163.com";
		public static final String PROTOCOL = "smtp";
		public static final int PORT = 25;
		public static final String SENDER = "nijiaboge521@163.com";//此处需要与host相同的网站
		public static final String SENDERPWD = "zhb19920215";//
*/		
		  /* <property name="host" value="${email.host}"/>
	       <property name="protocol" value="${email.protocol}"/>
	       <property name="port" value="${email.port}"/>
	       <property name="sender" value="${email.sender}"/>
	       <property name="senderpwd" value="${email.senderpwd}"/>*/
	    @Config(value="email.host")	
	    public   String HOST ;
	    @Config(value="email.protocol")	
		public   String PROTOCOL; 
	    @Config(value="email.port")	
	    public   int PORT ;
	    @Config(value="email.sender")	
		public   String SENDER; 
	    @Config(value="email.senderpwd")	
		public   String SENDERPWD;
		
		
	/*	
		public String getHOST() {
			return HOST;
		}

		public void setHOST(String hOST) {
			HOST = hOST;
		}

		public String getPROTOCOL() {
			return PROTOCOL;
		}

		public void setPROTOCOL(String pROTOCOL) {
			PROTOCOL = pROTOCOL;
		}

		public int getPORT() {
			return PORT;
		}

		public void setPORT(int pORT) {
			PORT = pORT;
		}

		public String getSENDER() {
			return SENDER;
		}

		public void setSENDER(String sENDER) {
			SENDER = sENDER;
		}

		public String getSENDERPWD() {
			return SENDERPWD;
		}

		public void setSENDERPWD(String sENDERPWD) {
			SENDERPWD = sENDERPWD;
		}
*/
		/**
		 * 获取用于发送邮件的Session
		 * @return
		 */
		public  Session getSession() {
			Properties props = new Properties();
			props.put("mail.smtp.host", HOST);//设置服务器地址
	        props.put("mail.store.protocol" , PROTOCOL);//设置协议
	        props.put("mail.smtp.port", PORT);//设置端口
	      //  props.put("mail.smtp.auth" , true);
	        props.put("mail.smtp.auth", "true");
	        Authenticator authenticator = new Authenticator() {
	        	@Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(SENDER, SENDERPWD);
	            }
			};
	        Session session = Session.getDefaultInstance(props,authenticator);
	        return session;
		}
		
		/**
		 * 发送邮件
		 * @param receiver
		 * @param content
		 */
		public  void send(String receiver, String content) {
			Session session = getSession();
			try {
				System.out.println("-------开始发送-------");
				Message msg = new MimeMessage(session);
				//设置message属性
				msg.setFrom(new InternetAddress(SENDER));
				InternetAddress[] addrs = {new InternetAddress(receiver)};
				msg.setRecipients(Message.RecipientType.TO, addrs);
				msg.setSubject("人邮社专业自主学习资源库-邮箱验证");
				msg.setSentDate(new Date());
				msg.setContent(content,"text/html;charset=utf-8");
				//开始发送
				Transport.send(msg);
	                        System.out.println("-------发送完成-------");
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		
	}


