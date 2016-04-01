package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.api.GetXmlService;
import junit.framework.TestCase;
import org.junit.Test;

import javax.annotation.Resource;

public class GetXmlServiceImplTest extends TestCase {
    @Resource
    GetXmlService getXmlService;
    @Test
    public void test() {
        GetXmlServiceImpl getXmlService=new GetXmlServiceImpl();
//        String s=getXmlService.getXml("com.qunar.pidshare.api.domestic.AVService", "pidAv");
//        String s=getXmlService.getXml("com.qunar.pidshare.common.api.service.PatService", "ssPat");

//        String s=getXmlService.getXml("com.qunar.ibeplus.api.itf.ITicketValidateService", "ticketValidate");

        String s=getXmlService.getXml("com.qunar.ibeplus.api.itf.IAirBookOrderService","bookOrder");
        System.out.println(s);
    }


}