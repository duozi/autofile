package com.qunar.flight.autofile.service;

import com.qunar.flight.autofile.pojo.AutofileRequest;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring.xml"})
public class AutofileServiceImplTest extends TestCase {
    @Resource
    AutofileServiceImpl autofileService;

    @Test
    public void test() {
//        AutofileRequest searchRequest = new AutofileRequest("com.qunar.flight.tts", "officemanage.api", "1.1.10", "com.qunar.flight.tts.officemanageapi.api.IIBEWhiteListService", "getExecuteType", "json");


        AutofileRequest searchRequest = new AutofileRequest("com.qunar.pidshare", "pidshare_dubbo.api", "1.3.16", "com.qunar.pidshare.common.api.service.AvService", "pidAv", "json");

        String s = autofileService.autofile(searchRequest);
        System.out.println(s);

    }

}