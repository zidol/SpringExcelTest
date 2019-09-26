<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://unpkg.com/vue"></script>
</head>
<body>
<div align="center" id="article">
		<table border="1" cellpadding="0" cellspacing="0" width="700">
			<tr>
				<th bgcolor="orange" width="100">글 번호</th>
				<th bgcolor="orange" width="200">제목</th>
				<th bgcolor="orange" width="150">작성자</th>
				<th bgcolor="orange" width="150">등록 일자</th>
			<tr>
			<tr v-for="(c, index) in articleList">
				<td align="center">{{index+1}}</td>
				<td align="center"><a v-bind:href="'/article/'+ c.id" >{{c.title}}</a></td>
				<td align="left">{{c.writer}}</td>
				<td align="left">{{new Date(c.regDate).toLocaleDateString()}}</td>
			</tr>
		</table>
		<br>   	
	   	
		<br>
		<a href="/article/insertArticle">게시들 등록</a>
		<br>
	</div>
	
	<script type="text/javascript">
	
		var vm = new Vue({
			el : "#article",
			data : {
				articleList : ${list},
			},

		});
	</script>
</body>
</html>