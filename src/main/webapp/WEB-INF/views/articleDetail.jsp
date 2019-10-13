<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://unpkg.com/vue"></script>
    <%-- <script src="<c:url value="/resources/js/jquery.fileDownload.js" />"></script> --%>
    <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">

</head>
<body>
<div id="table">
		<form id="form1" name="form1" method="post" action="downloadExcelFile">
			<input type="hidden" name="tableName" v-model="tableName">
			<table border="1" cellpadding="0" cellspacing="0" width="700">
				<!-- <tr>
					<th bgcolor="orange" width="100">번호</th>
					<th bgcolor="orange" width="200">과일이름</th>
					<th bgcolor="orange" width="150">가격</th>
					<th bgcolor="orange" width="150">개수</th>
				<tr>
				<tr v-for="(c, index) in articleList">
					<td align="center">{{c.id}}</td>
					<td align="center">{{c.name}}</td>
					<td align="left">{{c.price}}</td>
					<td align="left">{{c.quantity}}</td>
				</tr> -->
				<tr>
					<th bgcolor="orange" width="150" v-for="(c, index) in headers">{{c.replace(/\_/g," ")}}</th>
					<!-- <th bgcolor="orange" width="200">이름</th>
					<th bgcolor="orange" width="150">가격</th>
					<th bgcolor="orange" width="150">개수</th> -->
				<tr>
				<tr v-for="(c, index) in articleList">
					<td align="center" v-for="(k, index) in headers"><input id="name_id" type="text" v-model="c[k]"></td>
					<!-- <td align="left"><input id="price_id" type="text" v-model="c.부서"></td>
					<td align="left"><input id="quantity_id" type="text" v-model="c[headers[2]]"></td> -->
				</tr>
			</table>
			<br>
			<input type="submit" value="엑셀다운로드 작업">
	    <!-- <button type="button" v-on:click="doExcelDownloadProcess">엑셀다운로드 작업</button> -->
	    		<!-- <button type="button" v-on:click="doExcelDownloadProcess">엑셀다운로드 작업</button> -->
		 </form>
		<br>
		<a href="/articleList">테이블 목록으로..</a> <a href="/">엑셀 업로드..</a>
	   <form id="form1" name="form1" method="post" action="facebookPageCrawling.do">
			<input type="hidden" name="tableName" v-model="tableName">

			<input type="submit" value="페이스북">
		 </form>
</div>
<script type="text/javascript">
	
	var vm = new Vue({
		el : "#table",
		data : {
			articleList : ${article},
			tableName : '${tableName}',
			headers : [],
		}, 
		methods : {
		   doExcelDownloadProcess : function() {
		    /* 	var f = document.form1;
	        f.action = "downloadExcelFile";
			f.submit(); */
				/* $.ajax({
					url : "downloadExcelFile",
					type : "POST",
					contentType: "application/json",
					data : JSON.stringify(vm.articleList),
					success : function(data, status, xhr) { 
					  console.log(data);
					  console.log(status);
					  console.log(xhr);
					} 
				}) */
		    }
		}
	});
	var keys =  Object.keys(vm.articleList[0]);
	var header = new Array();
	for(var i in keys) {
		header[i] = keys[i];
	}
	vm.headers = header;
	console.log(header);
</script>

</body>
</html>
