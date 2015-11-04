package com.qunar.zhouxi.xe;

/**
 * Created by zhouxi.zhou on 2015/11/2.
 */

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.qunar.fuwu.gdsproxy.api.GdsProxyService;
import com.qunar.fuwu.gdsproxy.request.BaseCondition;
import com.qunar.fuwu.gdsproxy.request.uapi.UapiCalCelPnrCondition;
import com.qunar.fuwu.gdsproxy.request.uapi.UapiCreateSessionCondition;
import com.qunar.fuwu.gdsproxy.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class XePnr {
    private static final Logger logger = LoggerFactory.getLogger(XePnr.class);
    public static final String PNR_PATH = "..\\cancelpnr\\src\\main\\resources\\test.txt";
    public static final String PCC = "7YI0";
    @Resource
    GdsProxyService gdsProxyService;

    private BaseCondition baseCondition=new BaseCondition();

    @QSchedule("qschedule.zhouxi.xepnr")
    public void run() {
        logger.info("----------test");
        List<String> pnrList = readPnrFromFile();
        logger.info("all pnr:{}", pnrList);
        invokeXE(pnrList);
        sendEmail("zhouxi.zhou@qunar.com", "xepnr", "test", "test");
    }

    /**
     * 获得文件里面所有pnr
     *
     * @return
     */
    public List<String> readPnrFromFile() {
        String testFilePath = PNR_PATH;
        File testFile = new File(testFilePath);
        List<String> lines = new ArrayList<String>();
        try {
            lines = Files.readLines(testFile, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            System.out.println(line);
        }
        return lines;
    }

    /**
     * cancel list里所有pnr
     *
     * @param pnrList
     */
    public void invokeXE(List<String> pnrList) {
        if (pnrList.size() == 0) {
            logger.info("no pnr to cancel");
        } else {
            UapiCreateSessionCondition uapiCreateSessionCondition= new UapiCreateSessionCondition();
            BaseResponse baseResponse = gdsProxyService.createSession(PCC, uapiCreateSessionCondition);
            for (String pnr : pnrList) {
                UapiCalCelPnrCondition uapiCalCelPnrCondition= new UapiCalCelPnrCondition();
                uapiCalCelPnrCondition.setPnrCode(pnr);
                BaseResponse cancelResponse = cancelOnePnr(baseResponse.getSessionId(), uapiCalCelPnrCondition);
                logger.info("----------cancel pnt{} is {}", pnr, cancelResponse.isSuccess());
            }
        }

    }

    /**
     * cancel一个pnr
     *
     * @param session
     * @param uapiCalCelPnrCondition
     * @return
     */
    public BaseResponse cancelOnePnr(String session, UapiCalCelPnrCondition uapiCalCelPnrCondition) {

        uapiCalCelPnrCondition.setPnrCode(uapiCalCelPnrCondition.getPnrCode());
        BaseResponse baseResponse = gdsProxyService.cancelPNR(session, uapiCalCelPnrCondition);
        return baseResponse;

    }

    public void sendEmail(String from, String to, String subject, String body) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //设定server host
        javaMailSender.setHost("mta3.corp.qunar.com");
        javaMailSender.setUsername("zhouxi.zhou");
        javaMailSender.setPassword("zxq1w2e3.");
        //建立邮件消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom(from);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.timeout", "25000");
        javaMailSender.setJavaMailProperties(properties);

        javaMailSender.send(mailMessage);

        logger.info("邮件发送成功………………………………");
    }

    public void setGdsProxyService(String gdsProxyService) {
    }

    public void setBaseCondition(String baseCondition) {
    }
}
