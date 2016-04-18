package com.qunar.flight.autofile.servlet.controller;

import com.qunar.flight.autofile.commom.StringUtil;
import com.qunar.flight.autofile.service.GetJsonServiceImpl;
import com.qunar.flight.autofile.service.GetXmlServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhouxi.zhou on 2016/4/13.
 */
@Controller
public class SearchServlet {
    private static final String XML = "xml";
    private static final String JSON = "json";
    @Resource
    GetJsonServiceImpl getJsonService;
    @Resource
    GetXmlServiceImpl getXmlService;

    @RequestMapping("/reflect")
    @ResponseBody
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String interfaceString = request.getParameter("interfaceString");
        String methodString = request.getParameter("methodString");
        String type = request.getParameter("type");//json或者xml
//        StringBuilder ret = new StringBuilder();
        String resultString = StringUtils.EMPTY;
        if (StringUtil.isEmptyOrNull(interfaceString) || StringUtil.isEmptyOrNull(methodString) || StringUtil.isEmptyOrNull(type)) {
            resultString = "参数全部都不能为空";
        } else {
            if (JSON.equalsIgnoreCase(type)) {
                resultString = getJsonService.getJson(interfaceString, methodString);
            } else if (XML.equalsIgnoreCase(type)) {
                resultString = getXmlService.getXml(interfaceString, methodString);
            }
        }
        if (!StringUtil.isEmptyOrNull(resultString)) {
            response.getWriter().write(resultString);
            response.getWriter().flush();
        }


    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }


}
