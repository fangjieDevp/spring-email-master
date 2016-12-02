package com.fangjie.email;

import com.fangjie.email.config.MailConfig;
import com.fangjie.email.service.EmailSenderService;
import com.fangjie.email.vo.EmailVO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by fangjie04 on 2016/12/1.
 */

public class ConfigTest extends BaseEmailTest {

    private AnnotationConfigApplicationContext context = null;
    private EmailVO vo;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext(EmailApplicationContext.class);
        vo = new EmailVO();
        vo.setCc(new String[]{});
        vo.setBcc(new String[]{});
        vo.setSubject("[主题][致亲爱的一封邮件]");
        vo.setEmailContent("走不去的是人生，忘不了的是真情");
        vo.setReceivers(new String[]{"631294101@qq.com"});
        vo.setSender("fangjiewd@126.com");
    }

    @Test
    public void testConfig() {
        MailConfig mailConfig = context.getBean(MailConfig.class);
        List<String> receivers = mailConfig.getMailReceivers();
        assertEquals(2, receivers.size());
    }

    @Test
    public void testSendEmail() {
        EmailSenderService senderService = context.getBean(EmailSenderService.class);
        senderService.sendEmailBySimpleText(vo);
    }

    @Test
    public void testSendHtmlEmail() throws Exception {
        EmailSenderService senderService = context.getBean(EmailSenderService.class);
        String text = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "<style>\n" +
                "#header {\n" +
                "    background-color:black;\n" +
                "    color:white;\n" +
                "    text-align:center;\n" +
                "    padding:5px;\n" +
                "}\n" +
                "#nav {\n" +
                "    line-height:30px;\n" +
                "    background-color:#eeeeee;\n" +
                "    height:300px;\n" +
                "    width:100px;\n" +
                "    float:left;\n" +
                "    padding:5px;\t      \n" +
                "}\n" +
                "#section {\n" +
                "    width:350px;\n" +
                "    float:left;\n" +
                "    padding:10px;\t \t \n" +
                "}\n" +
                "#footer {\n" +
                "    background-color:black;\n" +
                "    color:white;\n" +
                "    clear:both;\n" +
                "    text-align:center;\n" +
                "   padding:5px;\t \t \n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<div id=\"header\">\n" +
                "<h1>City Gallery</h1>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"nav\">\n" +
                "London<br>\n" +
                "Paris<br>\n" +
                "Tokyo<br>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"section\">\n" +
                "<h2>London</h2>\n" +
                "<p>\n" +
                "London is the capital city of England. It is the most populous city in the United Kingdom,\n" +
                "with a metropolitan area of over 13 million inhabitants.\n" +
                "</p>\n" +
                "<p>\n" +
                "Standing on the River Thames, London has been a major settlement for two millennia,\n" +
                "its history going back to its founding by the Romans, who named it Londinium.\n" +
                "</p>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"footer\">\n" +
                "Copyright ? W3Schools.com\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        vo.setEmailContent(text);
        senderService.sendEmailByHTMLText(vo);
    }

    @Test
    public void testSendHtmlAndAttachmentEmail() throws Exception {
        EmailSenderService senderService = context.getBean(EmailSenderService.class);
        String text = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "<style>\n" +
                "#header {\n" +
                "    background-color:black;\n" +
                "    color:white;\n" +
                "    text-align:center;\n" +
                "    padding:5px;\n" +
                "}\n" +
                "#nav {\n" +
                "    line-height:30px;\n" +
                "    background-color:#eeeeee;\n" +
                "    height:300px;\n" +
                "    width:100px;\n" +
                "    float:left;\n" +
                "    padding:5px;\t      \n" +
                "}\n" +
                "#section {\n" +
                "    width:350px;\n" +
                "    float:left;\n" +
                "    padding:10px;\t \t \n" +
                "}\n" +
                "#footer {\n" +
                "    background-color:black;\n" +
                "    color:white;\n" +
                "    clear:both;\n" +
                "    text-align:center;\n" +
                "   padding:5px;\t \t \n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<div id=\"header\">\n" +
                "<h1>City Gallery</h1>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"nav\">\n" +
                "London<br>\n" +
                "Paris<br>\n" +
                "Tokyo<br>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"section\">\n" +
                "<h2>London</h2>\n" +
                "<p>\n" +
                "London is the capital city of England. It is the most populous city in the United Kingdom,\n" +
                "with a metropolitan area of over 13 million inhabitants.\n" +
                "</p>\n" +
                "<p>\n" +
                "Standing on the River Thames, London has been a major settlement for two millennia,\n" +
                "its history going back to its founding by the Romans, who named it Londinium.\n" +
                "</p>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"footer\">\n" +
                "Copyright ? W3Schools.com\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        vo.setEmailContent(text);

        String path = ConfigTest.class.getResource("/images/attchment.jpg").getPath();
        File[] f = new File[]{new File(path)};
        vo.setAttachFile(f);
        senderService.sendEmailBySimpleTextAndAttachment(vo, true);
    }

    @Test
    public void testSendHtmlAndAttachmentEmail2() throws Exception {
        EmailSenderService senderService = context.getBean(EmailSenderService.class);
        String text = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "<style>\n" +
                "#header {\n" +
                "    background-color:black;\n" +
                "    color:white;\n" +
                "    text-align:center;\n" +
                "    padding:5px;\n" +
                "}\n" +
                "#nav {\n" +
                "    line-height:30px;\n" +
                "    background-color:#eeeeee;\n" +
                "    height:300px;\n" +
                "    width:100px;\n" +
                "    float:left;\n" +
                "    padding:5px;\t      \n" +
                "}\n" +
                "#section {\n" +
                "    width:350px;\n" +
                "    float:left;\n" +
                "    padding:10px;\t \t \n" +
                "}\n" +
                "#footer {\n" +
                "    background-color:black;\n" +
                "    color:white;\n" +
                "    clear:both;\n" +
                "    text-align:center;\n" +
                "   padding:5px;\t \t \n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<img src='cid:attchment'/>" +
                "\n" +
                "<div id=\"header\">\n" +
                "<h1>City Gallery</h1>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"nav\">\n" +
                "London<br>\n" +
                "Paris<br>\n" +
                "Tokyo<br>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"section\">\n" +
                "<h2>London</h2>\n" +
                "<p>\n" +
                "London is the capital city of England. It is the most populous city in the United Kingdom,\n" +
                "with a metropolitan area of over 13 million inhabitants.\n" +
                "</p>\n" +
                "<p>\n" +
                "Standing on the River Thames, London has been a major settlement for two millennia,\n" +
                "its history going back to its founding by the Romans, who named it Londinium.\n" +
                "</p>\n" +
                "</div>\n" +
                "\n" +
                "<div id=\"footer\">\n" +
                "Copyright ? W3Schools.com\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        vo.setEmailContent(text);

        ClassPathResource resource = new ClassPathResource("/images/attchment.jpg");
        vo.setClassPathResource(new ClassPathResource[]{resource});
        senderService.sendEmailByHTMLText(vo);
    }
}
