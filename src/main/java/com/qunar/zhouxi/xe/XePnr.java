package com.qunar.zhouxi.xe;

/**
 * Created by zhouxi.zhou on 2015/11/2.
 */

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.qunar.fuwu.gdsproxy.api.GdsProxyService;
import com.qunar.fuwu.gdsproxy.request.uapi.UapiCalCelPnrCondition;
import com.qunar.fuwu.gdsproxy.request.uapi.UapiCreateSessionCondition;
import com.qunar.fuwu.gdsproxy.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import qunar.tc.qschedule.config.QSchedule;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class XePnr {
    private static final Logger logger = LoggerFactory.getLogger(XePnr.class);
    public static final String PNR_PATH = "/home/pnr";
    public static final String PCC = "7YI0";
    @Resource
    GdsProxyService gdsProxyService;

    @Resource
    JavaMailSenderImpl mailSender;

    static String emailFrom = "zhouxi.zhou@qunar.com";


    @QSchedule("qschedule.zhouxi.xepnr")
    public void run() {
        logger.info("----------test");
        List<String> pnrList = readPnrFromFile();
        logger.info("all pnr:{}", pnrList);
        invokeXE(pnrList);
        sendEmail("test");
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
            logger.error("read pnr file error");
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
                logger.info("----------cancel pnr{} is {}", pnr, cancelResponse.isSuccess());
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

    private synchronized boolean sendEmail(String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            String text=body;
            if(text!=null && text.length()>0){
                mimeMessageHelper.setFrom(emailFrom);
                mimeMessageHelper.setSubject("xepnr");
                mimeMessageHelper.setText(text);
                mimeMessageHelper.setTo("zhouxi.zhou@qunar.com");

                mailSender.send(mimeMessageHelper.getMimeMessage());
            }
            return true;
        } catch (Exception e) {
            logger.error("exception in send mail ", e);
            return false;
        }
    }

    public void setGdsProxyService(String gdsProxyService) {
    }

    public void setBaseCondition(String baseCondition) {
    }
}
