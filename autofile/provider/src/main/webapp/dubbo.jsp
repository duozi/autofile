<%@page import="com.alibaba.dubbo.config.ProtocolConfig"%>
<%@ page session="false" %><%

if ("127.0.0.1".equals(request.getRemoteAddr())) {
	String action = request.getParameter("action");
	if ("stop".equals(action)) {
		ProtocolConfig.destroyAll();
		out.println("OK");
	} else {
		out.println("Error");
	}
} else {
	out.println("deny");
}
%>