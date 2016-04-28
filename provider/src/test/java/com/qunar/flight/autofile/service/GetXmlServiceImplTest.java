package com.qunar.flight.autofile.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring.xml" })
public class GetXmlServiceImplTest extends TestCase {
    @Resource
//    GetXmlService getXmlService;
    @Test
    public void test() {
        GetXmlServiceImpl getXmlService=new GetXmlServiceImpl();
//        String s=getXmlService.getXml("com.qunar.pidshare.api.domestic.AVService", "pidAv");
//        String s=getXmlService.getXml("com.qunar.pidshare.common.api.service.PatService", "ssPat");

//        String s=getXmlService.getXml("com.qunar.ibeplus.api.itf.ITicketValidateService", "ticketValidate");

//        String s=getXmlService.getXml("com.qunar.pidshare.global.api.service.SsService", "ss");
//        System.out.println(s);
    }


}