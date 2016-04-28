package com.qunar.flight.autofile.servlet.controller;

import com.qunar.flight.autofile.pojo.AutofileRequest;
import com.qunar.flight.autofile.service.AutofileServiceImpl;
import com.qunar.flight.autofile.service.GetJsonServiceImpl;
import com.qunar.flight.autofile.service.GetXmlServiceImpl;
import com.qunar.flight.autofile.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Resource
    GetJsonServiceImpl getJsonService;
    @Resource
    GetXmlServiceImpl getXmlService;
    @Resource
    AutofileServiceImpl autofileServiceImpl;
    private final static Logger logger = LoggerFactory.getLogger(SearchServlet.class);
    @RequestMapping("/reflect")
    @ResponseBody
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String artifactString = request.getParameter("artifactString");
        String groupString = request.getParameter("groupString");
        String versionString = request.getParameter("versionString");
        String interfaceString = request.getParameter("interfaceString");
        String methodString = request.getParameter("methodString");
        String type = request.getParameter("type");//json或者xml

        String resultString = StringUtils.EMPTY;
        if (StringUtil.isEmptyOrNull(artifactString) || StringUtil.isEmptyOrNull(groupString) || StringUtil.isEmptyOrNull(versionString) || StringUtil.isEmptyOrNull(interfaceString) || StringUtil.isEmptyOrNull(methodString) || StringUtil.isEmptyOrNull(type)) {
            resultString = "参数全部都不能为空";
        } else {
            AutofileRequest autofileRequest = new AutofileRequest();
            autofileRequest.setGroupId(groupString);
            autofileRequest.setArtifactId(artifactString);
            autofileRequest.setVersion(versionString);
            autofileRequest.setInterfaceName(interfaceString);
            autofileRequest.setMethodName(methodString);
            autofileRequest.setType(type);

            resultString = autofileServiceImpl.autofile(autofileRequest);

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
