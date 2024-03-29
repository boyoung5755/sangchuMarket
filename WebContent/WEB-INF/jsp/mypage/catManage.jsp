<%@page import="sangchu.main.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%
	MemberVO vo = (MemberVO)request.getSession().getAttribute("memVO");
	String email = vo.getEmail();
%>

<meta charset="UTF-8">
<title>관심카테고리 관리...</title>
<!--
	최초 작성자 : 김석호
	최초 작성일 : 2023.08.16 11:11
	
	
	관심카테고리를 관리하는 페이지입니다.
 -->
</head>
<body>
	<div id="cat-print-space">
		<div id="inner-large">
		</div>
		<div id="inner-middle">
		</div>
	</div>
	<div id="print-mycatList">
	</div>
	<button type="button" onclick="addcatbtn()">추가</button>
</body>
<script>
	$(function(){
		// 대분류 카테고리 가져오는 ajax
		$.ajax({
			url : "<%=request.getContextPath()%>/getCategory.do",
			data : "command=large",
			dataType : 'json',
			type : 'post',
			success : function(res){
				if(res.sw=="ok"){
					let firstCode = '<select id="largecat" size="'+res.size+'">';
					$.each(res.datas,function(i,v){
						firstCode += '<option value="' + v.c_largecat+'" ';
						if(i==0){
							firstCode += 'selected';
						}
						firstCode += '>' + v.c_name + '</option>';
					});
					firstCode += '</select>'
					$('#inner-large').append(firstCode);
					$('select#largecat').trigger('change');
				}
			},
			error : function(xhr){
				alert('오류 : '+xhr.status);
			}
		});
		// mycatList를 가져오는 ajax
		$.ajax({
			url : "<%=request.getContextPath()%>/getCategory.do",
			data : "command=mylist",
			dataType : 'json',
			type : 'post',
			success : function(res){
				secondCode ="";
				if(res.sw=="ok"){
					$.each(res.datas,function(i,v){
						secondCode += '<div class="inter-list-print" id="' + v.c_middlecat + '">'
						secondCode += '<input type="text" readonly value="'+v.c_name+'">';
						secondCode += '<button type="button" class="remove" onclick="removecat(this)">x</button></div>';
					});
					$('#print-mycatList').append(secondCode);
					
				}
			},
			error : function(xhr){
				alert('오류 : '+ xhr.status);
			}
		});
	});//$function 끝 위치

// largecat을 다른걸 선택했을 때
$('#inner-large').on('change','select#largecat',function(){
	let largecatCode = $('#largecat option:selected').attr('value');
	console.log(largecatCode);
	$.ajax({
		url: "<%=request.getContextPath()%>/getCategory.do",
		data : "command=middle&largecatcode="+largecatCode,
		dataType : 'json',
		type : 'post',
		success : function(res){
			$('#inner-middle').empty();
			thirdCode = '<select id="middlecat" size="'+res.size+'">';
			if(res.sw=="ok"){
				$.each(res.datas,function(i,v){
					thirdCode += '<option value="' + v.c_middlecat+'">' + v.c_name + '</option>';
				});
			}
			thirdCode += '</select>'
			$('#inner-middle').append(thirdCode);
		},
		error : function(xhr){
			alert('오류 : '+ xhr.status);
		}
	})
	
})
// 카테고리 삭제버튼 눌렀을 때
function removecat(x){
	let targetdiv = $(x).parent();
	let catforremove = targetdiv.attr('id');
	console.log(catforremove);
	$.ajax({
		url : "<%=request.getContextPath()%>/getCategory.do",
		data : "command=delete&deleteformiddlecode="+catforremove,
		dataType : 'json',
		type : 'post',
		success : function(res){
			// 삭제 성공했을 시
			if(res.sw=="성공"){
				targetdiv.remove();
			}else{
				alert("관심카테고리 삭제에 실패했습니다.");
			}
		},
		error : function(xhr){
			alert('오류 : '+xhr.status);
		}
	});
}
// 등록버튼 눌렀을 때
function addcatbtn(){
	let targetcode = $('#middlecat option:selected').attr('value');
	let targervalue = $('#middlecat option:selected').text();
	if(targetcode==null|| targetcode==""){
		alert('중분류를 선택해주세요!');
		return false;
	}
	console.log(targetcode);
	console.log(targervalue);
	$.ajax({
		url : "<%=request.getContextPath()%>/getCategory.do",
		data : "command=insert&insertformiddlecode="+targetcode,
		dataType : 'json',
		type : 'post',
		success : function(res){
			// 등록 성공했을 시
			if(res.sw=="성공"){
				finalCode = '<div class="inter-list-print" id="' + targetcode + '">';
				finalCode += '<input type="text" readonly value="'+targervalue+'">';
				finalCode += '<button type="button" class="remove" onclick="removecat(this)">x표시</button></div>';
				$('#print-mycatList').append(finalCode);
				
			}else if(res.sw=="기등록"){
				alert('이미 관심목록에 등록되어있는 카테고리입니다.')
			}else if(res.sw=="초과"){
				alert('관심카테고리는 5개까지만 등록할 수 있습니다.');
			}else{
				// 실패일 때
				alert('알 수 없는 오류로인해 등록에 실패했습니다.');
			}
		},
		error : function(xhr){
			alert('오류 : '+xhr.status);
		}
	});
	
}



</script>
</html>