<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://unpkg.com/vue"></script>
</head>
<body>
<div align="center" id="table">
		<table border="1" cellpadding="0" cellspacing="0" width="700">
			<tr>
				<th bgcolor="orange" width="100">글 번호</th>
				<th bgcolor="orange" width="200">테이블 이름</th>
			<tr>
			<tr v-for="(c, index) in articleList">
				<td align="center">{{index+1}}</td>
				<td align="center"><a v-bind:href="'/' + c">{{c}}</a></td>
			</tr>
		</table>
		</table>
		<br>   	
		<br>
		<a href="/">게시들 등록</a>
		<br>
	</div>
	
	<script type="text/javascript">
	
		var vm = new Vue({
			el : "#table",
			data : {
				articleList : ${tableList}
			}
		});
	</script>
</body>
</html>