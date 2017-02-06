package com.runda.core.mail;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * <ul>
 * JavaMail 实际发送类
 * <li>发送简单邮件</li>
 * <li>发送Mime邮件</li>
 * </ul>
 */
public class EmailSender {

	private static final Log log = LogFactory.getLog(EmailSender.class);

	/**
	 * <p>
	 * 默认编码[UTF-8]
	 * </p>
	 */
	private static final String DEFAULT_CHARACTOR = "UTF-8";

	/**
	 * 注入Spring Java Mail Sender
	 */
	@Autowired
	private JavaMailSenderImpl springMailSender;

	/**
	 * 
	 * 
	 * @param sender
	 *          发送者(全0:系统发送/UUID:会员,后台用户id)
	 * 
	 * @param subject
	 *          消息主题(邮件主题,可以为空)
	 * 
	 * @param message
	 *          消息内容(邮件的主体内容)
	 * 
	 * @param emails
	 *          消息目标(以,分隔的电子邮件地址)
	 * 
	 * 
	 */
	public void SendEmail(String sender, String subject, String message, String[] emails) throws Exception {

		// 建立简单邮件消息
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setTo(emails);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);

		springMailSender.send(simpleMailMessage);

	}

	/**
	 * <p>
	 * 发送简单邮件
	 * </p>
	 * 
	 * @param email
	 */
	public void sendSimpleMail(MailEntity email) {

		if (null == email) {
			if (log.isDebugEnabled()) {
				log.debug("The mail content is null.");
			}
		}

		// 建立简单邮件消息
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(email.getFrom());
		simpleMailMessage.setTo(email.getTo());
		simpleMailMessage.setSubject(email.getSubject());
		simpleMailMessage.setText(email.getText());

		springMailSender.send(simpleMailMessage);
	}

	/**
	 * 发送含有附件或图片的邮件
	 * 
	 * @param email
	 */
	public void sendMimeMail(MailEntity email) throws Exception {

		if (null == email) {
			if (log.isDebugEnabled()) {
				log.debug("The mail content is null.");
			}
		}

		// 建立邮件消息
		MimeMessage message = springMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = null;

		messageHelper = new MimeMessageHelper(message, true, DEFAULT_CHARACTOR);
		messageHelper.setSentDate(new Date());
		messageHelper.setTo(email.getTo());
		messageHelper.setFrom(email.getFrom());
		messageHelper.setSubject(email.getSubject());

		messageHelper.setText(email.getText(), true);
		Map<String, String> attachment = email.getAttachment();
		Map<String, String> img = email.getImg();

		if (attachment != null) {
			this.addAttachmentOrImg(messageHelper, attachment, true);
		}
		if (img != null) {
			this.addAttachmentOrImg(messageHelper, img, false);
		}

		springMailSender.send(message);
	}

	/**
	 * <p>
	 * 为邮件添加附件或图片
	 * </p>
	 * 
	 * @param messageHelper
	 * @param map
	 * @param isAttachment
	 *          添加附件还是图片
	 * @throws MessagingException
	 */
	private void addAttachmentOrImg(MimeMessageHelper messageHelper, Map<String, String> map, boolean isAttachment) throws MessagingException {

		for (String key : map.keySet()) {
			// 取出key对应的value
			String value = map.get(key);

			FileSystemResource file = new FileSystemResource(new File(value));

			if (!file.exists())
				continue;

			if (isAttachment) {
				// 添加附件
				messageHelper.addAttachment(key, file);
			} else {
				// 添加图片,需要一个cid值
				messageHelper.addInline(key, file);
			}

		}
	}

	public void setSpringMailSender(JavaMailSenderImpl springMailSender) {
		this.springMailSender = springMailSender;
	}
	/**
     * 发送HTML信息的邮件. <br>   发送人的邮箱   标题  内容  接收者的邮箱
     * @author ices 2012-12-4下午4:09:49<br>
     */
    public void sendHtmlMessage(String from, String subject, String message, String[] emails) {
       
        try {  
           MimeMessage mailMessage = springMailSender.createMimeMessage();  
           //设置utf-8或GBK编码，否则邮件会有乱码  
           MimeMessageHelper messageHelper =new MimeMessageHelper(mailMessage,true,"utf-8");  
       
           messageHelper.setTo(emails);
           messageHelper.setFrom(from); 
           messageHelper.setSubject(subject);
           //邮件内容，参数true，表示启用html格式  
           messageHelper.setText(message,true);  
           springMailSender.send(mailMessage);  
        } catch (Exception e) {  
           
        }  
    }
}