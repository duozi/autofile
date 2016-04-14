package com.qunar.flight.autofile.servlet.controller;

import com.qunar.flight.autofile.commom.StringUtil;
import com.qunar.flight.autofile.service.GetJsonServiceImpl;
import com.qunar.flight.autofile.service.GetXmlServiceImpl;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhouxi.zhou on 2016/4/13.
 */

public class SearchServlet extends HttpServlet {
    private static final String XML = "xml";
    private static final String JSON = "json";
    @Resource
    GetJsonServiceImpl getJsonService;
@Resource
    GetXmlServiceImpl getXmlService;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String interfaceString = request.getParameter("interfaceString");
        String methodString = request.getParameter("methodString");
        String type = request.getParameter("type");//json或者xml
//        StringBuilder ret = new StringBuilder();
        String resultString = StringUtils.EMPTY;
        if (StringUtil.isEmptyOrNull(interfaceString) || StringUtil.isEmptyOrNull(methodString) || StringUtil.isEmptyOrNull(type)) {
            resultString="所有参数都不能为空";
        } else {
            if (XML.equalsIgnoreCase(type)) {
                resultString = getJsonService.getJson(interfaceString, methodString);
            }else if(JSON.equalsIgnoreCase(type)){
                resultString=getXmlService.getXml(interfaceString,methodString);
            }
        }
        if(!StringUtil.isEmptyOrNull(resultString)){
            response.getWriter().write(resultString);
            response.getWriter().flush();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
