<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>Home</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://unpkg.com/vue"></script>
</head>
<body>
<div id="table">
		<form id="form1" name="form1" method="post" enctype="multipart/form-data">
			<table border="1" cellpadding="0" cellspacing="0" width="700">
				<tr>
					<th bgcolor="orange" width="100">번호</th>
					<th bgcolor="orange" width="200">과일이름</th>
					<th bgcolor="orange" width="150">가격</th>
					<th bgcolor="orange" width="150">개수</th>
				<tr>
				<tr v-for="(c, index) in articleList">
					<td align="center">{{index+1}}</td>
					<td align="center"><input id="name_id" type="text" v-model="c.name"></td>
					<td align="left"><input id="price_id" type="text" v-model="c.price"></td>
					<td align="left"><input id="quantity_id" type="text" v-model="c.quantity"></td>
				</tr>
			</table>
			<br>
	    		<button type="button" v-on:click="doExcelDownloadProcess">엑셀다운로드 작업</button>
		 </form>
		<br>
		<a href="/articleList">테이블 목록으로..</a> <a href="/">엑셀 업로드..</a>
	   
</div>
<script type="text/javascript">

	var vm = new Vue({
		el : "#table",
		data : {
			articleList : ${article}
		}, 
		methods : {
		   doExcelDownloadProcess : function() {
		    	// var f = document.form1;
		        // f.action = "downloadExcelFile";
				// f.submit();
				$.ajax({
					url : "downloadExcelFile",
					type : "POST",
					contentType: "application/json",
					data : JSON.stringify(vm.articleList),
					success : function() {
						console.log("성공")
					}
				})
		    }
		}
	});
	
</script>

</body>
</html>
