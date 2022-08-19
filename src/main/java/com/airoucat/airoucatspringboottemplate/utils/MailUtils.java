package com.airoucat.airoucatspringboottemplate.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 邮件工具
 * author: huwei
 */
@Component
public class MailUtils {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public void sendCode(String to, int code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("您的验证码为:" + code);
        message.setSubject("qjyy flowdatacheck password reset");
        message.setFrom(sender);
        message.setTo(to);
//        message.setCc("抄送人");
//        message.setBcc("密送人");
        javaMailSender.send(message);
    }

    public void sendCodeWithHTML(String to, String code) throws MessagingException {
        MimeMessage mailMessage=javaMailSender.createMimeMessage();
        //需要借助Helper类
        MimeMessageHelper helper=new MimeMessageHelper(mailMessage);
        String context="<div style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;box-sizing:border-box;font-size:14px;width:100%!important;height:100%;line-height:1.6em;background-color:#f6f6f6;margin:0\" bgcolor=\"#f6f6f6\">\n" +
                "    <table style=\"font-size:14px;width:100%;background-color:#f6f6f6;margin:0\" bgcolor=\"#f6f6f6\">\n" +
                "        <tbody><tr style=\"box-sizing:border-box;font-size:14px;margin:0\">\n" +
                "            <td style=\"box-sizing:border-box;font-size:14px;vertical-align:top;margin:0\" valign=\"top\">\n" +
                "            </td>\n" +
                "            <td width=\"600\" style=\"box-sizing:border-box;font-size:14px;vertical-align:top;display:block!important;max-width:600px!important;clear:both!important;margin:0 auto\" valign=\"top\">\n" +
                "                <div style=\"box-sizing:border-box;font-size:14px;max-width:600px;display:block;margin:0 auto;padding:20px\">\n" +
                "                    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"box-sizing:border-box;font-size:14px;border-radius:3px;background-color:#fff;margin:0;border:1px solid #e9e9e9\" bgcolor=\"#fff\">\n" +
                "                        <tbody><tr style=\"box-sizing:border-box;font-size:14px;margin:0\">\n" +
                "                            <td style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;box-sizing:border-box;line-height: 2.5;font-size:22px;font-weight:bold;vertical-align:top;color:#fff;font-weight:500;text-align:center;border-radius:3px 3px 0 0;background-color:#008b7b;margin:0;padding:20px\" align=\"center\" bgcolor=\"#0073ba\" valign=\"top\">\n" +
                "                                重置密码\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr style=\"box-sizing:border-box;font-size:14px;margin:0\">\n" +
                "                            <td class=\"m_5931696195480848877content-wrap\" style=\"padding:20px\" valign=\"top\">\n" +
                "                                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"\">\n" +
                "                                    <tbody>                                    <tr>\n" +
                "                                        <td style=\"font-size:16px;color:#4a4a4a;line-height: 2.5;padding: 10px 20px;text-indent: 2em;\" valign=\"top\">您现在正在进行重置密码操作,如果不是您本人操作,请忽略此邮件。本次操作的验证码是:</td></tr><tr><td style=\"font-size: 38px;text-align: center;line-height:1em;margin:0;padding: 20px 0px 60px;\">"+code+"</td></tr>\n"+
                "                                    <tr>\n" +
                "                                        <td style=\"box-sizing:border-box;font-size:12px;color:#757575;vertical-align:top;margin:0;padding:0 0 20px\" valign=\"top\">\n" +
                "                                            (本邮件由系统自动发出，请勿直接回复)\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </tbody></table>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </tbody></table>\n" +
                "                    <div style=\"box-sizing:border-box;font-size:14px;width:100%;clear:both;color:#999;margin:0;padding:20px\">\n" +
                "                        <table width=\"100%\" style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;box-sizing:border-box;font-size:14px;margin:0\">\n" +
                "                            <tbody><tr style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;box-sizing:border-box;font-size:14px;margin:0\">\n" +
                "                                <td style=\"font-family:'Helvetica Neue',Helvetica,Arial,sans-serif;box-sizing:border-box;font-size:12px;vertical-align:top;color:#999;text-align:center;margin:0;padding:0\" align=\"center\" valign=\"top\">\n" +
                "                                    © 千金药业. All Rights Reserved.\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </tbody></table>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </td>\n" +
                "            <td style=\"box-sizing:border-box;font-size:14px;vertical-align:top;margin:0\" valign=\"top\">\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody></table></div>";
        helper.setFrom(sender);
        helper.setTo(to);
//        helper.setBcc("密送人");
        helper.setSubject("qjyy flowdatacheck password reset");
        helper.setSentDate(new Date());//发送时间
        helper.setText(context,true);
        //第一个参数要发送的内容，第二个参数是不是Html格式。
        //附件
//        helper.addAttachment("资料.xlsx",new File("/Users/gamedev/Desktop/测试数据 2.xlsx"));
        javaMailSender.send(mailMessage);

    }
}
