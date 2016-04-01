<!DOCTYPE html>
<%@ include file="../commons/head_inc.jsp"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<html>
<head>
<title>增加/修改</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<%=contextPath%>/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=contextPath%>/static/jquery/js/jquery-1.9.1.js"></script>
<script src="<%=contextPath%>/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("a[name='deleteButton']").click(
		function(){
			return confirm("你确定要删除?删除后数据无法恢复！");
		}	
	);
});
</script>
</head>
<body>
	<div class="navbar">
		<div class="navbar-inner">
			<a class="brand" href="#">导航</a>
			<ul class="nav">
				<li class="active"><a href="list.do">列表</a></li>
				<li><a href="toSave.do">增加</a></li>

			</ul>
		</div>
	</div>
	<table class="table table-bordered table-hover">
		<caption>代理商列表</caption>
		<thead>
			<tr>
				<th>id</th>
				<th>代理商Id</th>
				<th>代理商类型</th>
				<th>代理商状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ officePropertiesList }" var="officeProperties">

				<tr>
					<td>${ officeProperties.id }</td>
					<td>${ officeProperties.officeId }</td>
					<td>${ officeProperties.officeType == 1? "代理商":"航空公司" }</td>
					<td>${ officeProperties.status == 1? "有效":"无效" }</td>
					<td>
						<a href="toSave.do?id=${ officeProperties.id }" class="btn">修改</a> 
						<a href="delete.do?id=${ officeProperties.id }" class="btn" name="deleteButton">删除</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
</body>
</html>