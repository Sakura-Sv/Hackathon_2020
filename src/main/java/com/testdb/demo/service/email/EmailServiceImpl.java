package com.testdb.demo.service.email;
import com.testdb.demo.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.from}")
    private String from;

    /**
     * 发送文本文件
     * @param to 目标
     * @param subject 标题
     * @param content 内容
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendSimpleMail(String to, String subject, String content)
            throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    /**
     * 同上，增加了抄写对象
     * @param to
     * @param subject
     * @param content
     * @param cc 抄写者列表
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendSimpleMail(String to, String subject, String content, String... cc)
            throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }

    /**
     * 发送html邮件
     * @param to 目标
     * @param subject 标题
     * @param content 内容
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendHtmlMail(String to, String subject, String content)
            throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    /**
     * 同上，增加了抄写对象
     * @param to
     * @param subject
     * @param content
     * @param cc 抄写者列表
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendHtmlMail(String to, String subject, String content, String... cc)
            throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setCc(cc);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }

    /**
     * 发送 带附件的HTML邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath 文件路径
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendAttachmentsMail(String to, String subject, String content, String filePath)
            throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);

        javaMailSender.send(message);
    }

    /**
     * 同上 增加了抄写者
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @param cc
     * @throws MessagingException
     */
    @Override
    @Async
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc)
            throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setCc(cc);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);

        javaMailSender.send(message);
    }

    @Override
    @Async
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId)
            throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        javaMailSender.send(message);
    }

    @Override
    @Async
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc)
            throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setCc(cc);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        javaMailSender.send(message);
    }
}
