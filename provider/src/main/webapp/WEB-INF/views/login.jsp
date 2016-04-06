<!DOCTYPE html>
<%@ include file="commons/head_inc.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html>
<head>
<title>增加/修改</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<%=contextPath%>/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=contextPath%>/static/jquery/js/jquery-1.9.1.js"></script>
<script src="<%=contextPath%>/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<form action="login.do" class="form-horizontal" method="post">
		<fieldset>
            <legend>用户登录</legend>
            <c:if test="${ msg!=null }">
            <div class="alert alert-error">
 				${ msg }
			</div>
			</c:if>
            <div class="control-group">
                <label  class="control-label">用户名</label> 
                 <div class="controls">
                    <input type="text" name="user"  required>                        
                </div>        
            </div>
            
            <div class="control-group">
                <label  class="control-label">密码</label> 
                 <div class="controls">
                    <input type="password" name="password"  required>                        
                </div>        
            </div>
            
            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn" id="submitButton">登录</button>
                </div>
                
            </div>
        </fieldset>
	</form>
</body>
</html>