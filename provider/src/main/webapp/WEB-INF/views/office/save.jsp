<!DOCTYPE html>
<%@ include file="../commons/head_inc.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html>
<head>
<title>添加/修改配置信息</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<%=contextPath%>/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=contextPath%>/static/jquery/js/jquery-1.9.1.js"></script>
<script src="<%=contextPath%>/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	
	<div class="navbar">
        <div class="navbar-inner">
            <a class="brand" href="#">导航</a>
            <ul class="nav">
                <li><a href="list.do">列表</a></li>
                <li class="active"><a href="toSave.do">增加</a></li>
                
            </ul>
        </div>
    </div>
	<form action="doSave.do" class="form-horizontal" method="post">
		<c:if test="${ officeProperties != null }">
            <input type="hidden" name="id" value="<c:out value="${ officeProperties.id }" />">
        </c:if>	
        <fieldset>
            <legend>添加/修改视频控制信息</legend>
            <div class="control-group">
                <label  class="control-label">代理商ID</label> 
                 <div class="controls">
                    <input type="text" name="officeId" value="<c:out value="${ officeProperties.officeId }" default=""/>" required>                        
                </div>        
            </div>
            
            <div class="control-group">
                <label  class="control-label">代理商类型</label> 
                     
                <div class="controls">
                    <select name="officeType">
                        <option value="1" ${  officeProperties.officeType==1 ? "selected=\"selected\"":"" } >代理商</option>
						<option value="2" ${  officeProperties.officeType==2 ? "selected=\"selected\"":"" } >航空公司</option>
                    </select>
                </div>
            </div>
            
            <div class="control-group">
                <label  class="control-label">代理商状态</label> 
                     
                <div class="controls">
                    <select name="status">
                        <option value="1" ${  officeProperties.status==1 ? "selected=\"selected\"":"" }>有效</option>
						<option value="0" ${  officeProperties.status==0 ? "selected=\"selected\"":"" }>无效</option>
                    </select>
                </div>
            </div>
            
            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn" id="submitButton">提交</button>
                </div>
                
            </div>
            
            
        </fieldset>
        
		
	</form>
</body>
</html>