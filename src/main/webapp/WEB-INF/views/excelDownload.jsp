<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>Home</title>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <!-- <script src="https://unpkg.com/vue"></script> -->
    <!-- Load required Bootstrap and BootstrapVue CSS -->
<link type="text/css" rel="stylesheet" href="https://unpkg.com/bootstrap/dist/css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="https://unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.min.css" />

<!-- Load polyfills to support older browsers -->
<script src="//polyfill.io/v3/polyfill.min.js?features=es2015%2CIntersectionObserver" crossorigin="anonymous"></script>

<!-- Load Vue followed by BootstrapVue -->
<script src="https://unpkg.com/vue@latest/dist/vue.min.js"></script>
<script src="https://unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.min.js"></script>
    <link rel="shortcut icon" href="data:image/x-icon;" type="image/x-icon">
</head>
<body>
<div id="article">
	<form id="form1" name="form1" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	    <input type="file" id="fileInput" name="fileInput">
	    <button type="button" v-on:click="doExcelUploadProcess">엑셀업로드 작업</button>
	    <!-- <button type="button" v-on:click="doExcelDownloadProcess">엑셀다운로드 작업</button> -->
	 
	</form>
	<div>
		
		<label>헤더 위치</label>
		{{number}}
		<button class="decrement-button" @click="rowDecrement('rowDe')">−</button>
        <button class="increment-button" @click="rowIncrement('rowIn')">+</button>
		<label>데이터 시작</label>
		{{dataNumber}}
		<button class="decrement-button" @click="dataRowDecrement('colDe')">−</button>
        <button class="increment-button" @click="dataRowIncrement('colIn')">+</button>
		<!-- <label>시작 행</label> <input type="number" min="0" v-model="checkedNames"> -->
	</div>
		<form id="form2" name="form2" action="">
			<table border="1" cellpadding="0" cellspacing="0" width="700" v-if="isStatusOn">
				<!-- <tr>
					<th v-bind:style="bgc" width="150" v-for="c in headers">{{c.value}}</th>
					<th bgcolor="orange" width="200">이름</th>
					<th bgcolor="orange" width="150">가격</th>
					<th bgcolor="orange" width="150">개수</th>
				<tr> -->
				<tr v-for="(c, index) in articleList">
					<!-- <td>
						<input type="checkbox" v-bind:value="index" v-bind:id="'checked'+index" v-model="checkedNames"/> 
						<b-form-checkbox
					      v-bind:id="'checked'+index"
					      v-model="checkedNames"
					      name="checkbox-1"
					      v-bind:value="index"
					    >
					</td> -->
					<td align="center" v-for="(k, i) in c">
						<input :ref="'inp'+index" v-bind:id="'data'+index" type="text" v-model="k.value" :disabled="checkedNames.includes(index-1) ? true : false" >
					</td>
					<!-- <td align="left"><input id="price_id" type="text" v-model="c.부서"></td>
					<td align="left"><input id="quantity_id" type="text" v-model="c[headers[2]]"></td> -->
				</tr>
			</table>
			<button type="button" v-on:click="insertDB">저장</button>
		</form>
		<span>체크한 이름: {{ checkedNames }}</span>

</div>
<script type="text/javascript">

	var vm = new Vue({
		el : "#article",
		data : {
			articleList : [],
			headers : [],
			isStatusOn : false,
			bgc: {
				backgroundColor: ''
			},
			checkedNames: [],
			number : 0,
			dataNumber : 0
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
		            	console.log(list);
		            		//헤더 키값가져오기
			            	/* var keys =  Object.keys(list[0]);
			            	var header = new Array();
							for(var i in keys) {
								header[i] = keys[i];
							}
							vm.headers = header;
			            		vm.articleList = dataConvertToJson(list);
							console.log(header); */
		            	var keys =  Object.keys(list);
					var lists = list[keys];
					//vm.headers = dataConvertToJson(lists[0]);//헤더
					/* var datas = [];
					for(var i=1; i < lists.length; i++) {
						datas[i-1] = lists[i];
					} */
					//vm.bgc.backgroundColor=dataConvertToJson(lists[0])[0].color;//헤더 컬러
					vm.articleList = lists;//데이터
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
		    },
		
		    sliceData : function() {
		    		var data = vm.articleList;
					var index = vm.checkedNames;
		    		console.log(typeof(data))
		    		console.log("자를 데이터 ", data);
		    		console.log("체크한 박스 인덱스 : ", index);
		    		for(var i = 0; i < index.length; i++ ){
						data.splice(index[i]-1, 1);
					}
					console.log("첫번째 행 제거  : ", data);
		    },
		    
		    rowIncrement : function() {
				// var flag = a;
				// if(vm.number === vm.dataNumber) {
				// 	vm.dataNumber++;
				// }
		    	// vm.checkedNames.push(vm.number++);
				// console.log("refs : ", this.$refs.inp0);
				// this.$refs.inp0[0].disabled
				var a = vm.number;
				var b = 0;
				var c = 0;
				var keys =  Object.keys(this.$refs);
			    var header = new Array();
				for(var i in keys) {
					header[i] = keys[i];
				}
				// for(var i = 0; i < header.length; i++) {
					
				// }
				console.log(this.$refs)
				for(var j=0; j < this.$refs.inp0.length; j++) {
					this.$refs[header[a]][j].disabled = true;
					c=j;
				}
				/* for(var j=0; j < this.$refs.inp0.length-1; j++) {
					this.$refs[header[a]][j].style.fontWeight = 'normal';
				} */
				
				++a;
				for(var i=0; i < 3; i++) {
					this.$refs[header[a]][i].style.fontWeight = 'bold';
				}
				vm.number = a;
		    },
		    
		    rowDecrement : function() {
			    	/* if(vm.number == 0) {
		    			return;
		    		} 
		    		console.log(vm.number);
		    		vm.checkedNames.splice(vm.number-1, 1); */
		    		// vm.number--;
					var a = vm.number;
					if(a==0) {
						return;
					}
		    		var keys =  Object.keys(this.$refs);
				    var header = new Array();
					for(var i in keys) {
						header[i] = keys[i];
						console.log(header[i]);
					}
					for(var j=0; j < this.$refs.inp0.length; j++) {
						this.$refs[header[a-1]][j].disabled = false;
						this.$refs[header[a-1]][j].style.fontWeight = 'normal';
					}
					--a;
					vm.number = a;
					console.log(a)
		    }
		    
		    ,
		    dataRowIncrement : function() {
				if(vm.dataNumber == 0 || vm.dataNumber < vm.number){
					vm.dataNumber = vm.number;
				}
	    		vm.checkedNames.push(vm.dataNumber++);
				console.log(vm.dataNumber);
	    	},
	    
			dataRowDecrement : function() {
				// if(vm.dataNumber == 0 || vm.number == vm.dataNumber) {
				// 	return;
				// } 
				vm.checkedNames.splice(vm.dataNumber-1, 1);
				vm.dataNumber--;
				console.log(vm.dataNumber);
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
