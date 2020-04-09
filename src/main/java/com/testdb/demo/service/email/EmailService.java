package com.testdb.demo.service.email;

import javax.mail.MessagingException;

public interface EmailService {
    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content)
            throws MessagingException;

    public void sendSimpleMail(String to, String subject, String content, String... cc)
            throws MessagingException;

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content)
            throws MessagingException;

    public void sendHtmlMail(String to, String subject, String content, String... cc)
            throws MessagingException;

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath)
            throws MessagingException;

    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc)
            throws MessagingException;

    /**
     * 发送正文中有静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     * @throws MessagingException
     */
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId)
            throws MessagingException;

    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc)
            throws MessagingException;

}