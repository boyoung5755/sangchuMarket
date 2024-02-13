<%@page import="sangchu.main.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>키워드</title>
<!--
	최초 작성자 : 김석호
	최초 작성일 : 2023.08.15 00:41
	
	최근 수정자 : 김석호
	최근 수정일 : 2023.08.15 16:49
	
	수정 내역
	2023.08.15 16:49 - 김석호 : 스크립트 구축
	
	키워드를 관리하는 페이지입니다. 
 -->
</head>
<%
	MemberVO vo = (MemberVO) session.getAttribute("memVO");
if(vo==null){
	out.println("<script>location.href='"+request.getContextPath()+"/loginsessionError.do'</script>");
}
%>
<style>
.for-keyword {
	height : 500px;
	overflow: scroll;
	margin-top: 30px;
}
</style>
<body>
<!-- 바디 전체 시작부 -->
<div class="container-fluid">

<!-- 관심 키워드 부분 -->
<h4>관심 키워드</h4><hr>
	<div class="row for-keyword">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-8">
					<input type="text" id="interest-keyword" placeholder="관심 키워드">
				</div>
				<div class="col-md-4">
					<button type="button" id="insert-inter-keyword">+</button>
				</div>
			</div>
		</div>
		<hr>
		<!-- 목록 출력부 -->
		<div class="print-list" id="inter-list">
		</div>
	</div>
	<hr>
<!-- 차단 키워드 부분 -->
<h4>차단 키워드</h4><hr>
	<div class="row for-keyword">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-8">
					<input type="text" id="block-keyword" placeholder="차단 키워드">
				</div>
				<div class="col-md-4">
					<button type="button" id="block-inter-keyword">+</button>
				</div>
			</div>
		</div>
		<hr>
		<!-- 목록 출력부 -->
		<div class="print-list" id="block-list">
		</div>
	</div>
	
	
</div>
</body>
<script>
	$(function(){
		// 처음 로딩시 원래 있는 정보를 먼저 가져온다.
		var email= "<%=vo.getEmail()%>" ;
		// ajax1 : 관심키워드 로드
    	$.ajax({
    		url : "<%=request.getContextPath()%>/keywordManage.do",
    		data : {"k_code" : "1"},
    		dataType : 'json',
    		type : 'get',
    		success : function(res){
    			if(res.sw=="ok"){
    				// 리스트가 뭐라도 있을경우?
    				interCode = "";
    				$.each(res.datas,function(i,v){
    					interCode += '<div class="inter-list-detail keyword-list" id="' + v.k_keyword;
    					interCode += '"><input type="text" readonly value="' + v.k_keyword + '"><button type="button" onclick="removebutton(this)" class="remove">x</button></div>';
    				})
    				$('#inter-list').html(interCode);
    			}else{
    				// 리스트가 아예 없을경우?
    				// 아무일도 해주지 않는다..?
    			}
    		},
    		error : function(xhr){
    			alert('오류 : '+xhr.status);
    		}
    	})//ajax1 끝 위치
    	
		// ajax2 : 차단키워드 로드
    	$.ajax({
    		url : "<%=request.getContextPath()%>/keywordManage.do",
    		data : {"k_code" : "2"},
    		dataType : 'json',
    		type : 'get',
    		success : function(res){
    			if(res.sw=="ok"){
    				// 리스트가 뭐라도 있을경우?
    				blockCode = "";
    				$.each(res.datas,function(i,v){
    					blockCode += '<div class="block-list-detail keyword-list" id="'+v.k_keyword;
    					blockCode += '"><input type="text" readonly value="' + v.k_keyword + '"><button type="button" onclick="removebutton(this)" class="remove">x</button></div>';
    				})
    				$('#block-list').html(blockCode);
    			}else{
    				// 리스트가 아예 없을경우?
    				// 아무일도 해주지 않는다..?
    			}
    		},
    		error : function(xhr){
    			alert('오류 : '+xhr.status);
    		}
    	})//ajax2 끝 위치
	//---------- 페이지가 로드되면 처리되는 ajax 2개 끝 -------------------------------------------------------
    	
	});// $function끝위치

function removebutton(x){
	let targetdiv = $(x).parent();
	let keyforremove = targetdiv.attr('id');
	console.log(keyforremove);
	let command = 'remove';
	// 삭제요청 ajax
	$.ajax({
		url : "<%=request.getContextPath()%>/keywordManage.do",
		data : {"command":command,"keyword":keyforremove},
		dataType : 'json',
		type : 'post',
		success : function(res){
			// 삭제 성공했을 시
			if(res.sw=="성공"){
				targetdiv.remove();
			}else{
				alert("키워드 삭제에 실패했습니다.");
			}
		},
		error : function(xhr){
			alert('오류 : '+xhr.status);
		}
	});
	}
// 삭제버튼을 눌렀을 때
<%--
$('button.remove').on('click' , function() {
	let targetdiv = $(this).parent();
	let keyforremove = targetdiv.attr('id');
	console.log(keyforremove);
	let command = 'remove';
	// 삭제요청 ajax
	$.ajax({
		url : "<%=request.getContextPath()%>/keywordManage.do",
		data : {"command":command,"keyword":keyforremove},
		dataType : 'json',
		type : 'post',
		success : function(res){
			// 삭제 성공했을 시
			if(res.sw=="성공"){
				targetdiv.remove();
			}else{
				alert("키워드 삭제에 실패했습니다.");
			}
		},
		error : function(xhr){
			alert('오류 : '+xhr.status);
		}
	});
	
});
--%>

// 관심키워드 등록버튼을 눌렀을 때
$("#insert-inter-keyword").on("click", function() {
	let keyword = $('#interest-keyword').val();
	if(keyword==null||keyword.trim()==""){
		alert('키워드를 입력해주세요!');
		return false;
	}
	let command = 'inter';
	// 관심키워드 등록 요청 ajax
	$.ajax({
		url : "<%=request.getContextPath()%>/keywordManage.do" ,
		data : {"command":command,"keyword":keyword},
		dataType : 'json',
		type : 'post',
		success : function(res){
			interCode = "";
			// 등록 성공했을 때
			if(res.sw=="성공"){
				$('#interest-keyword').val("");
				interCode += '<div class="inter-list-detail keyword-list" id="' + keyword;
				interCode += '"><input type="text" readonly value="' + keyword + '"><button type="button" class="remove" onclick="removebutton(this)">x</button></div>';
				$('#inter-list').append(interCode);
			}else if(res.sw=="기등록"){ // 요청한 키워드가 이미 등록되어있을 때
				alert('이미 등록된 키워드입니다!');
			}else if(res.sw=="실패"){ // 등록 요청 실패 시
				alert('알수 없는 오류로 등록에 실패했습니다!');
			}else if(res.sw=="초과"){
				alert('키워드 구분 당 5개 초과로 등록할 수 없습니다');
			}// 이렇게 외에는 json으로 보내지 않음
		},
		error : function(xhr){
			alert('오류 : '+xhr.status);
		}
	});
});

// 차단키워드 등록버튼을 눌렀을 때
$("#block-inter-keyword").on("click", function() {
	let keyword = $('#block-keyword').val();
	if(keyword==null||keyword.trim()==""){
		alert('키워드를 입력해주세요!');
		return false;
	}
	let command = 'block';
	// 차단키워드 등록 요청 ajax
	$.ajax({
		url : "<%=request.getContextPath()%>/keywordManage.do",
		data : {"command":command,"keyword":keyword} ,
		dataType : 'json',
		type : 'post',
		success : function(res){
			blockCode = "";
			// 등록 성공했을 때
			if(res.sw=="성공"){
				$('#block-keyword').val("");
				blockCode += '<div class="inter-list-detail keyword-list" id="' + keyword ;
				blockCode += '"><input type="text" readonly value="' + keyword + '"><button type="button" class="remove">x</button></div>';
				$('#block-list').append(blockCode);
			}else if(res.sw == "기등록"){ // 요청한 키워드가 이미 등록되어있을 때
				alert('이미 등록된 키워드입니다!');
			}else if(res.sw=="실패"){ // 등록 요청 실패 시
				alert('알수 없는 오류로 등록에 실패했습니다!');
			}else if(res.sw=="초과"){
				alert('키워드 구분 당 5개 초과로 등록할 수 없습니다');
			}// 이렇게 외에는 json으로 보내지 않음
		},
		error : function(xhr){
			alert('오류 : '+xhr.status);
		}
	});
});

   </script>
</html>