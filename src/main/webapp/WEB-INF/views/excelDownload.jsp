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
	    <!-- <button type="button" v-on:click="doExcelDownloadProcess">엑셀다운로드 작업</button> -->
	 
	</form>
		<form id="form2" name="form2" action="">
			<table border="1" cellpadding="0" cellspacing="0" width="700" v-if="isStatusOn">
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
			<button type="button" v-on:click="insertDB">저장</button>
		</form>
</div>
<script type="text/javascript">
	var vm = new Vue({
		el : "#article",
		data : {
			articleList : [],
			headers : [],
			isStatusOn : false,
		},
		methods : {
			doExcelUploadProcess : function(){
		        var vm = this;
		        var f = new FormData(document.getElementById('form1'));
		        this.isStatusOn = !this.isStatusOn;

		        $.ajax({
		            url: "uploadExcelFile",
		            data: f,
		            processData: false,
		            contentType: false,
		            type: "POST",
		            success: function(list){
		            	console.log(list[0]);
			            	var keys =  Object.keys(list[0]);
			            	var header = new Array();
							for(var i in keys) {
								header[i] = keys[i];
							}
							vm.headers = header;
			            		vm.articleList = dataConvertToJson(list);
							console.log(header);
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
		// console.log(typeof data);
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
