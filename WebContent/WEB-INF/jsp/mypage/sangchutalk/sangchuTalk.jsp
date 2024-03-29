<%@page import="sangchu.main.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<!--
	최초 작성자 : 김석호
	최초 작성일 : 2023.08.08 12:37
	
	최근 수정자 : 김석호
	최근 수정일 : 2023.08.18 10:31
	
	수정 이력 :
	2023.08.09 19:43 - 김석호 : 상추톡 서블릿 구현과 변수명 맞췄습니다.
	2023.08.09 23:48 - 김석호 : html바디 구현
	2023.08.10 02:22 - 김석호 : 채팅로그까지 구현
	2023.08.10 02:40 - 김석호 : 채팅방 채팅출력에서 이미지소스 불러오기 위해 오는 메시지에 email추가, 출력문 처리
	2023.08.10 10:00 - 김석호 : 채팅방 기능 구현 진짜 완료했습니다. css만 꾸며주면 끝나요
	2023.08.13 10:00 - 김석호 : css는 개뿔 일단  세션 null처리만 추가했습니다
	2023.08.18 10:31 - 김석호 : 채팅방 연계기능 추가 및 css 미량 수정
	
	마이페이지 우측 기본화면 하단 상추톡 페이지입니다.
 -->
<title>채팅방 완성-디버깅전단계</title>

</head>
<body>
	<div class="chat-container-fluid" style="background: white;">
		<div id="left">
		</div>
		<div id="right" style="margin-left: 4%; margin-bottom: 20px;">
		</div>
	</div>
	<br>
	<div style="display:block; margin-left: 52%;">
		거래 날짜　<input type="date" id="date">
		<button id="offerButton" type="button" onclick="offerButton()" class="btn btn-primary">현재 채팅방에 제시 보내기</button>
		<input type="hidden" id="whoReg">
	</div>
</body>
<% 
MemberVO vo= (MemberVO)session.getAttribute("memVO");
if(vo==null){
	out.println("<script>location.href='"+request.getContextPath()+"/loginsessionError.do'</script>");
} 
%>
<%-- <script src="<%=request.getContextPath()%>/js/jquery/jquery-3.7.0.min.js"></script> --%>
<script type="text/javascript">

   	var webSocket = null; // 웹소켓 변수 선언
   	var room = null;
   	var regEmail = null; // 채팅방 번호에 해당하는 거래글의 작성자를 담을 공간
	var contextPath = "<%=request.getContextPath()%>";
   	var userMail = "<%=vo.getEmail()%>"; // 이메일정보 여기서 세팅한다.
   	var userNick = "<%=vo.getU_nick()%>"; // 닉네임정보
	// 오른쪽에 열리는 채팅방의 입력란 가져오기
   	var messageText = null;
	if(contextPath==null){
		contextPath = "";
	}
	
	$(function(){
	   	// 처음 접속하기 버튼 누를때 입력했던 사용자 ID
	   	// 로그인 유저 정보 입력하는 곳
		
		
   		$.ajax({
   			url : contextPath+"/getAllChatList.do",
   			data : "email="+userMail,
   			dataType:'json',
   			type : 'get',
   			success : function(res){
   				// 왼쪽 창 정보 먼저 여기서 갖고와서 코드 어쩌구
   				listcode = "<aside><header><h3 style='text-align: center;'>채팅방 목록<h2><hr></header><ul style='overflow: auto;height: 400px;'>";
   				
   				if(res.sw=="ok"){
	   				$.each(res.datas,function(i,v){
	   					// getAllChatList
	   					listcode += '<li idx="'+v.c_no  +'" class="chatList"><a href="javascript:void(0);" onclick="please_chatLog('+v.c_no+')"><div><h2 style="margin-left:20px;">'+v.tb_title+'</h2></div></a></li><hr>';
	   				});
   				}else{
   					listcode += "<li>참여한 채팅목록이 없습니다</li>"
   				}
   				listcode += "</ul></aside>"
   				$('#left').html(listcode);
   				
   			},
   			error : function(xhr){
   				alert('오류 : '+xhr.status);
   			}
   		});
	}); //$function 끝위치
   	
	// 메시지를 출력하는 위치 가져오기
	
	// 여기 가져오는거.. 채팅방의 출력란 부모인 div를 가져와서 출력할 때 append로 해줘야함
	// ------------------------------------------------------------------------------
	
	


   	/*
   		왼쪽에서 채팅방을 클릭하면, 해당 채팅방의 번호를 가지고와서 오른쪽에 열어준다.
   		룸넘버를 room이라는 변수에 저장해야한다.
   	*/
   	/*
   		function 하나 열어서 왼쪽의 채팅방을 열면 ajax로 먼저 채팅 리스트 갖고 온다.
   		갖고 와서 오른쪽에 채팅이력 쭉 출력먼저 해주고,
   		그다음 connection함수를 호출한다.
		이 부분에서 오른쪽에 창 열어줘야함. 창 열때 채팅방번호 가지고 서블릿으로 갔다와야함
	*/
   	//채팅방 목록 리스트가 있는 그거! 클래스로 하면 될 듯
   	function please_chatLog(x){
   		room = x;
   		console.log(room);
   		if(webSocket!=null){
   			webSocket.close();
   		}
   		$('#right').empty();
   		defaultCode = '<section class="chatbox"><section class="chat-window"></section><form class="chat-input" onsubmit="return false;"><input type="text" id="messageText" autocomplete="on" placeholder="메시지를 입력하세요." /><button onclick="sendMessage()"><svg style="width:24px;height:24px" viewBox="0 0 24 24"><path fill="rgba(0,0,0,.38)" d="M17,12L12,17V14H8V10H12V7L17,12M21,16.5C21,16.88 20.79,17.21 20.47,17.38L12.57,21.82C12.41,21.94 12.21,22 12,22C11.79,22 11.59,21.94 11.43,21.82L3.53,17.38C3.21,17.21 3,16.88 3,16.5V7.5C3,7.12 3.21,6.79 3.53,6.62L11.43,2.18C11.59,2.06 11.79,2 12,2C12.21,2 12.41,2.06 12.57,2.18L20.47,6.62C20.79,6.79 21,7.12 21,7.5V16.5M12,4.15L5,8.09V15.91L12,19.85L19,15.91V8.09L12,4.15Z" /></svg></button></form></section>'; 
   		$('#right').html(defaultCode);
   		$('.chat-window').scrollTop(9999999);
   		// 여기서 connecting 함수를 호출하는 방식으로 연결한다.
   		connecting();
   		messageText = document.getElementById("messageText");
   		
   	}
   	/*
   	$('li').on('dblclick','ul .chatList',function(){
   		var room = $(this).attr('idx');
   		console.log(room);
   		//connectiong 호출하기 전에 먼저 websocket이 null인지 먼저 판단하고 null이 아닐경우 close함수를 호출한다.
   		if(webSocket!=null){
   			closing();
   		}
   		$('#right').empty();
   		defaultCode = '<section class="chatbox"><section class="chat-window"></section><form class="chat-input" onsubmit="return false;"><input type="text" id="messageText" autocomplete="on" placeholder="메시지를 입력하세요." /><button><svg style="width:24px;height:24px" viewBox="0 0 24 24"><path fill="rgba(0,0,0,.38)" d="M17,12L12,17V14H8V10H12V7L17,12M21,16.5C21,16.88 20.79,17.21 20.47,17.38L12.57,21.82C12.41,21.94 12.21,22 12,22C11.79,22 11.59,21.94 11.43,21.82L3.53,17.38C3.21,17.21 3,16.88 3,16.5V7.5C3,7.12 3.21,6.79 3.53,6.62L11.43,2.18C11.59,2.06 11.79,2 12,2C12.21,2 12.41,2.06 12.57,2.18L20.47,6.62C20.79,6.79 21,7.12 21,7.5V16.5M12,4.15L5,8.09V15.91L12,19.85L19,15.91V8.09L12,4.15Z" /></svg></button></form></section>'; 
   		$('#right').html(defaultCode);
   		// 여기서 connecting 함수를 호출하는 방식으로 연결한다.
   		connecting();
   	})
   	*/
   	
   	function connecting(){
	   	/*
	   		이 부분 아이피주소 테스트 환경에따라 계속 바꿔줘야함
	   		192.168.35.36
	   	*/
 		webSocket = new WebSocket("ws://192.168.35.36" + contextPath + "/webSocketSangchuTalk.do");
        
    	// 처음 접속 성공하면 
		webSocket.onopen = function onOpen(event){
    		// 여기서 ajax로 먼저 채팅방의 로그를 불러와준다.
    		$.ajax({
    			type: 'get',
    			url : contextPath+"/getChatLog.do",
    			data : "room="+room,
    			dataType : 'json',
    			success : function(res){
    				logCode = "";
    				if(res.sw=="ok"){
    					$.each(res.datas,function(i,v){
    						// v : ChatDetailVO
    						logCode += '<article class="msg-container ';
    						if(v.email==userMail){
    							logCode += 'msg-self';
    						}else{
    							logCode += 'msg-remote';
    						}
    						logCode += '"><div class="msg-box"><img class="user-img" src="'+contextPath+'/imageViewforChat.do?email='+v.email+'" alt="이미지" /><div class="flr"><div class="messages"><p class="msg">';
							logCode += v.chat_content;
							logCode += '</p></div><span class="timestamp"><span class="username">'+v.u_nick;
							logCode += '</span>&bull;<span class="posttime">'+v.chat_date;
							logCode += '</span></span></div></div></article>';
    					});
    				}else{
    					alert('불러 올 이전 대화 기록이 없습니다!');
    				}
    				$('.chat-window').append(logCode);
    			},
    			error : function(xhr){
    				alert('오류 : '+xhr.status);
    			}
    		});
    		
			webSocket.send( createMessage("connect", null) );
			
			
			
		}
        
		//메시지가 오면 messageTextArea요소에 메시지를 추가한다.
		webSocket.onmessage = function processMessge(message){
			// JSON 데이터를 먼저 풀고 채팅방 번호가 내가 접속해있는 채팅방 번호가 맞는지 먼저 확인해준다.
			// 해당 채팅방 번호가 맞으면 출력해준다.? 보낸사람 아이디에 따라 출력문 정렬 위치가 바뀌는건 물론
			// Json 풀기
			var jsonData = JSON.parse(message.data);
			if(jsonData.user=="system"){
				alert(jsonData.message);
			}else{
				if(jsonData.message != null) {
					chatCode = "";
					/*
						messageTextArea.value += jsonData.message + "\n";
						여기서 출력란의 부모요소를 가져와서 append해줘야함
						여기서는 nick이 날라옴
						
						
					*/
					var today = new Date();
					var year = today.getFullYear();
					var month = ('0' + (today.getMonth() + 1)).slice(-2);
					var day = ('0' + today.getDate()).slice(-2);
					// 여기도 이스케이프 써줘야하나..?
					var dateString = year + '/' + month  + '/' + day;
					
					chatCode += '<article class="msg-container ';
					if(jsonData.user==userNick){
						chatCode += 'msg-self';
					}else{
						chatCode += 'msg-remote';
					}
					chatCode += '"><div class="msg-box"><img class="user-img" src="'+contextPath+'/imageViewforChat.do?email='+jsonData.usermail+'" /><div class="flr"><div class="messages"><p class="msg">';
					chatCode += jsonData.message;
					chatCode += '</p></div><span class="timestamp"><span class="username">'+jsonData.user;
					chatCode += '</span>&bull;<span class="posttime">'+dateString;
					chatCode += '</span></span></div></div></article>';
					$('.chat-window').append(chatCode);
					// 여기에 출력문을 만드는데... jsonData.user 를 닉네임하고 비교해서 출력문을 다르게 한다.
					// 스크립트 변수 : userNick
					$('.chat-window').scrollTop(9999999);
				};
			}
			
			// 여기서 ajax 써서 제시여부를 계속 확인해준다.
			// 메시지가 올때마다 ajax로 Offer의 여부를 확인
			// getOffer메소드 호출

			
			
		}
        
		
		webSocket.onerror = function showErrorMsg(event) {
			alert("오류 : " + event.data);
		}
		
		webSocket.onclose = function(event){
			// 웹소켓에서 접속해제 했다는 메시지 날려주고 웹소켓을 null처리해 주는걸로
			webSocket.send( createMessage("runout", null) );
			webSocket.close();
		}
   	}
   	// connecting 함수 끝
   	
   
   	// 메시지 구조  {"command" : "명령종류", "room" : "채팅방번호", "message" : "메시지", "nickname" : "로그인유저vo의 닉네임정보" ,"email" : "로그인유저vo의이메일정보" }
   	// 명령 종류 : "connect" - 처음 접속, "message" - 메시지 전송, "runout" - 채팅방 나가기,  
   	
   	
	//메시지 보내기
	function sendMessage(){
		if(messageText.value.trim()==""){
			messageText.focus();
			return;
		}
		
		webSocket.send( createMessage("message", messageText.value) );
		messageText.value = ""; 
	}
  	
   	// 전송할 메시지를 작성하는 함수
   	// json객체 형태로 만들어서 서블릿에서 fromjson으로 분해
   	function createMessage(command, message){
   		return '{"command" : "' + command + '", "room" : "' + room + '", "message" : "' + message + '", "nickname" : "'+userNick +'", "email" : "' + userMail + '"}';
   	}
    
	function closing(){
		webSocket.close();
		webSocket = null;
	}
	
	window.onbeforeunload = function(){
		closing();
	}

	function enterMessage(){
		sendMessage();
		return false;
	}
//----------------------------------------------------------------------------------------------------
function offerButton(){
	let nowRoom = room;
	console.log(nowRoom);
	if(webSocket==null){
		alert('현재 열려있는 채팅방이 없습니다!');
		return false;
	}
	// 여기서 거래글 작성자인지 아닌지 판단
	$.ajax({
		url : "<%=request.getContextPath()%>/tradeOffer.do",
		data : "room="+nowRoom,
		dataType : 'json',
		type : 'get',
		success : function(res){
			if(res.sw=="ok"){
				console.log(res.datas);
				$('#whoReg').val(res.datas);
				console.log(res.datas);
			}
		},
		error : function(xhr){
			console.log('오류',xhr.status);
		}
	});
	gogogogo();
}
function gogogogo(){
	var whoReg = $('#whoReg').val();
	if(whoReg!=null){
		if(userMail==whoReg){
			alert('본인의 글에는 제시할 수 없습니다!');
		}else{
			tryOffer();
		}
	}else{
		alert('채팅방의 게시글 작성자 정보를 가져오지 못했습니다!')
	}
}
function tryOffer(){
	let nowRoom = room;
	let datef = $('#date').val()+"";
	
	if(datef==null||datef==""){
		alert('날짜를 먼저 선택해주세요!');
		return false;
	}
	$.ajax({
		url : "<%=request.getContextPath()%>/tradeOffer.do",
		data : {"cno" : nowRoom , "date" : datef},
		type : 'get',
		dataType : 'json',
		success : function(res){
			if(res.sw=="성공"){
				alert('거래제시에 성공했습니다!');
			}else{
				alert('이미 제시를 했거나 알 수없는 이유로 제시에 실패했습니다!');
			}
		},
		error : function(xhr){
			alert('오류 : '+ xhr.status);
		}
	});
}

</script>
</html>
