<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>Home</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://unpkg.com/vue"></script>
    <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
</head>
<body>
<div id="article">
	<form id="form1" name="form1" method="post" enctype="multipart/form-data">
	    <input type="file" id="fileInput" name="fileInput">
	    <button type="button" v-on:click="doExcelUploadProcess">엑셀업로드 작업</button>
	    <button type="button" v-on:click="doExcelDownloadProcess">엑셀다운로드 작업</button>
	 
	</form>
		<form id="form2" name="form2" action="">
			<table border="1" cellpadding="0" cellspacing="0" width="700">
				<tr>
					<th bgcolor="orange" width="100">글 번호</th>
					<th bgcolor="orange" width="200">이름</th>
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
			<button type="button" v-on:click="insertDB">저장</button>
		</form>
</div>
<script type="text/javascript">
	var vm = new Vue({
		el : "#article",
		data : {
			articleList : [],
		},
		methods : {
			doExcelUploadProcess : function(){
		        var vm = this;
		        var f = new FormData(document.getElementById('form1'));

		        $.ajax({
		            url: "uploadExcelFile",
		            data: f,
		            processData: false,
		            contentType: false,
		            type: "POST",
		            success: function(list){
		            	
		            	vm.articleList = dataConvertToJson(list);

		            }
		        })
		    },
		    
		    doExcelDownloadProcess : function() {
		    	var f = document.form1;
		        // f.action = "downloadExcelFile";
				// f.submit();
		        console.log(f);
		    },
		    
		    insertDB : function() {
		    		
		    		var listData = vm.articleList;
		    		
		    		$.ajax({
			            url: "insertData",
			            data: JSON.stringify(listData),
			            contentType: "application/json",
			            type: "POST",
			            success: function(){
							location.href="/articleList";
			            }
			        })
		    }
		}
	
	});

	
	//Number , String , Boolean , Function , Object , Null , undefined , Array 
	function dataConvertToJson (data) {
		console.log(typeof data);
		var type = typeof data;
		if (type == "string") {
			return JSON.parse(data);
		} else if (type == "object" || type == "Array" ) {
			return data;
		} else {
			console.log("데이터 변환 오류");
			console.log("DATA", data);
			return data;
		}
	}
	
</script>

</body>
</html>
