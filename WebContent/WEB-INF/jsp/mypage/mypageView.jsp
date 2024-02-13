<%@page import="sangchu.mypage.vo.ReviewVO"%>
<%@page import="sangchu.main.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String email="";

	MemberVO memVO = (MemberVO)session.getAttribute("memVO");
	
	if(memVO != null){
		email = memVO.getEmail();
	}
	ReviewVO rVo = (ReviewVO)session.getAttribute("tradeInfo");
	if(rVo==null){
		out.println("<script>location.href='"+request.getContextPath()+"/loginsessionError.do'</script>");
	}

%>     
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>



<!-- Customized Bootstrap Stylesheet -->
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/header.css" rel="stylesheet">
    <link href="/css/animate.min.css" rel="stylesheet">
    <link href="/css/owl.carousel.min.css" rel="stylesheet">
    <link href="/css/chat.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- JavaScript Libraries -->
    <script src="/js/jquery/jquery-3.7.0.min.js"></script>
    <script src="/js/main/owl.carousel.min.js"></script>
    <script src="/js/main/bootstrap.bundle.min.js"></script>
    <script src="/js/main/easing.min.js"></script>
    <script src="/js/main/owl.carousel.min.js"></script>
    <script src="/js/main/main.js"></script>
    
    
<script>
	
	$(function(){

		//등록 상품 버튼
		$('#regproduct').on('click',function(){
			$('#subcontent').empty();
			$('#subcontent').load("<%=request.getContextPath()%>/regList.do");
		});

		// 거래이력 버튼
		$('#tradelog').on('click',function(){
			$('#subcontent').empty();
			$('#subcontent').load("<%=request.getContextPath()%>/tradeLogList.do");
		});

		// 찜목록 버튼
		$('#jjimlist').on('click',function(){
			$('#subcontent').empty();
			$('#subcontent').load("<%=request.getContextPath()%>/jjimList.do");
		});

		// 상추톡 버튼
		$('#sangchutalk').on('click',function(){
			$('#subcontent').empty();
			$('#subcontent').load("<%=request.getContextPath()%>/openSangchuTalk.do");
		});

		// 거래후기 버튼
		$('#scoreboard').on('click',function(){
			$('#subcontent').empty();
			$('#subcontent').load("<%=request.getContextPath()%>/tradeReview.do");
		});

		$('#regproduct').click();
			

		$(".button").click(function(){
			if(!$(this).next().is(":visible")){
				$.each($(".button"), function(i, ele){
					$(this).next().slideUp();
				})
				$(this).next().slideToggle();
			}
		})
		
		// 닉네임 중복검사를 위한 변수
		var checkForNicknameChange = false;
		// 닉네임 정규식..?
		const regexForNickName = /^[ㄱ-ㅎ가-힣a-zA-Z0-9]+$/;
		
		$('#update-nickname').on('click',function(){
			$('#for-update-nickname').val($('#print-nickname').text());
			$('#print-nickname').css('display','none');
			$('#update-nickname').css('display','none');
			$('#for-update-nickname').css('display','block');
			$('#check-for-update-nickname').css('display','inline-block');
			$('#go-for-update-nickname').css('display','inline-block');
			$('#cancel-for-update-nickname').css('display','inline-block');
		})
		
		$('#cancel-for-update-nickname').on('click',function(){
			checkForNicknameChange = false;
			$('#print-nickname').css('display','block');
			$('#for-update-nickname').css('display','none');
			$('#update-nickname').css('display','inline-block');
			$('#check-for-update-nickname').css('display','none');
			$('#go-for-update-nickname').css('display','none');
			$('#cancel-for-update-nickname').css('display','none');
			$('#checkresult').text('');
		})
		
		$('#for-update-nickname').on('change',function(){
			checkForNicknameChange = false;
		})
		
		$('#go-for-update-nickname').on('click',function(){
				$('#checkresult').text("");
			if(!checkForNicknameChange){
				$('#checkresult').css('color','red');
				$('#checkresult').text("중복 검사를 진행해주세요");
				return false;
			}
			var email = "<%=memVO.getEmail()%>"
			let targetNick = $('#for-update-nickname').val();
			$.ajax({
				url : "<%=request.getContextPath()%>/forChangNickname.do",
				data : "nick="+targetNick,
				dataType : 'json',
				type : 'post',
				success : function(res){
					if(res.sw=="성공"){
						alert('닉네임 변경 성공!');
						location.href="<%=request.getContextPath()%>/mypageView.do"
					}
				},
				error : function(xhr){
					location.href=`<%=request.getContextPath()%>/goErrorPage.do?error=${xhr.status}`;
				}
			})
			
		});
		$('#check-for-update-nickname').on('click',function(){
			$('#for-spinner').css('display','inline-block');
			$('#checkresult').text("");
			let targetNick = $('#for-update-nickname').val();
			if(!regexForNickName.test(targetNick)){
				$('#for-spinner').css('display','none');
				$('#checkresult').css('color','red');
				$('#checkresult').text("부적합한 문자 포함");
				return false;
			}
			$.ajax({
				url : "<%=request.getContextPath()%>/forChangNickname.do",
				data : "targetnick="+targetNick,
				dataType : 'json',
				type : 'get',
				success : function(res){
					$('#for-spinner').css('display','none');
					if(res.sw=="실패"){
						$('#checkresult').css('color','green');
						$('#checkresult').text("사용 가능한 닉네임");
						checkForNicknameChange = true;
					}else{
						$('#checkresult').css('color','red');
						$('#checkresult').text("사용중인 닉네임");
					}
				},
				error : function(xhr){
					location.href=`<%=request.getContextPath()%>/goErrorPage.do?error=${xhr.status}`;
				}
			})
		})
		
		<%-- $("#content").load("<%=request.getContextPath()%>/mypageDefaultView.do"); --%>

	
	});
	
</script>
    
    
	
</head>
<body>

	<!--헤더-->
	
	<%    
	if(memVO == null){  

%> 
    <div class="topDiv">
        <div class="buttonToLogin">
            <a href="<%=request.getContextPath()%>/loginMain.do">로그인 / 회원가입</a>
        </div>
    </div>
<%
	}else{
%>   

    <div class="topDiv">
        <div class="buttonToLogin">
            <a href="<%=request.getContextPath()%>/mypageMain.do"><%=memVO.getU_nick() %> 님 환영합니다</a>　/　
            <a href="<%=request.getContextPath()%>/">로그아웃</a>
        </div>
    </div>
<% 
	}
%>   
 
    <div id="header" style="background: #03c75a;">
        <a class=logoSection href="main.html">
            <img src="/images/Logo.png" class=logoImg width="40" height="40" alt="상추마켓로고">
            <p class="font" style="color: #ffffff;">상추마켓 마이페이지 　　　　　　　　　　　　　　　　　　</p>
        </a>

        <div>
          
        </div>

<% 
	if(memVO == null){  //회원로그인이 안되어있을때
%>
        <div class="mypageNZzim2">
            <div class="mypageNZzim_img">

                <a href="<%=request.getContextPath() %>/loginMain.do">
                    <img src="/images/mypagewhite.png" alt="마이페이지">
                </a>
                <a href="<%=request.getContextPath() %>/loginMain.do">
                    <img src="/images/heartwhite.png" alt="찜">
                </a>
            </div>
        </div>
    </div>
<% 
	}else{ //회원로그인이 되어있을때
%>    
    <div class="mypageNZzim2">
            <div class="mypageNZzim_img">

                <a href="<%=request.getContextPath() %>/mypageMain.do">
                    <img src="/images/mypagewhite.png" alt="마이페이지">
                </a>
                <a href="mypage.html">
                    <img src="/images/heartwhite.png" alt="찜">
                </a>
                
            </div>
        </div>
    </div>
<% 
	}
%>     

    <!-- 헤더 끝 -->
    
    
    
    <!-- Navbar Start -->
    <div class="container-fluid bg-dark mb-30">
        <div class="row px-xl-5">
            <div class="col-lg-3 d-none d-lg-block">
            </div>
            <div class="col-lg-9">
                <nav class="navbar navbar-expand-lg bg-dark navbar-dark py-3 py-lg-0 px-0">
                     <a href="javascript:void(0);" onclick="memberUpdate()" class="nav-item nav-link">내 정보 수정</a>
                     <a href="javascript:void(0);" onclick="passwordUpdate()" class="nav-item nav-link">비밀번호 변경</a>
                     <a href="javascript:void(0);" onclick="keywordManage()" class="nav-item nav-link">키워드 관리</a>
                     <a href="javascript:void(0);" onclick="catManage()" class="nav-item nav-link">관심 카테고리 변경</a>
                     <a href="javascript:void(0);" onclick="blackManaga()" class="nav-item nav-link">차단 관리</a>
                     <a href="javascript:void(0);" onclick="offerManage()" class="nav-item nav-link">거래제시 관리</a>
                </nav>
            </div>
        </div>
    </div>
    <!-- Navbar End -->
	
    
    <!-- Shop Detail Start -->
    <div class="container-fluid pb-5">
        <div class="row px-xl-5">
            <div class="col-lg-5 mb-30">
               <div class="col-lg-7-1 h-auto mb-30">
                <div class="h-100 bg-light p-30">
                	<div style="text-align: center;">
	                   <button class="btn btn-primary px-3">
	                   		<i class="fa mr-1">　　　　　　회　원　정　보　　　　　　</i>
						</button><br><br>
	                	<div class="product-item bg-light">
	                        <div class="product-img position-relative overflow-hidden"
	                        	style=" height: 220px;width: 220px;border: 1px solid #ced4da;border-radius: 160px; margin-left: 18%;">
	                            <img src="<%=request.getContextPath() %>/imageViewforChat.do?email=<%=email%>" alt="">
	                        </div>
	                    </div><br>
						<h4 class="font-weight-semi-bold mb-4" id="print-nickname"><%=memVO.getU_nick() %></h4>
						<input type="text" class="font-weight-semi-bold mb-4" id="for-update-nickname" style="display:none;text-align: center;width: 100%;font-size: 24px;"><span id="checkresult"></span>
					 </div>
					<button type="button" class="btn btn-primary" id="update-nickname">닉네임변경</button>
					<button type="button" class="btn btn-primary" id="check-for-update-nickname" style="display:none;">중복검사</button>
					<button type="button" class="btn btn-primary fa fa-check" id="go-for-update-nickname" style="display:none;"></button>
					<button  type="button"class="btn btn-danger fa fa-times" id="cancel-for-update-nickname" style="display:none;"></button>
					   
                    <hr>
                    <form class="mb-15" action="">
	                    <div class="input-group">
	                        <div class="input-group-append">
	                            <button class="btn btn-primary">구매수</button>
	                        </div>
	                        <input style="background: white;" readonly type="text" class="form-control border-1 p-4" value="<%=rVo.getBuy_cnt() %>">
	                        <div class="input-group-append">
	                            <button class="btn btn-primary" >판매수</button>
	                        </div>
	                        <input style="background: white;" readonly type="text" class="form-control border-1 p-4" value="<%=rVo.getSell_cnt() %>">
	                        
	                    </div>
                	</form>
                    <form class="mb-15" action="">
	                    <div class="input-group">
	                        <div class="input-group-append">
	                            <button class="btn btn-primary">내 상추</button>
	                        </div>
	                        <input style="background: white;" readonly type="text" class="form-control border-1 p-4" value="<%=rVo.getScore() %>　점">
	                   		
	                    </div>
                	</form>
                	<a href="javascript:void(0);" onclick="escapeHere()" class=" mr-1">회원탈퇴</a>
                </div>
            </div>
            </div>

            <div class="col-lg-7 h-auto mb-30">
                <div id="content" class=" bg-light p-30" style="margin-left: -150px; height : 96%;">
                    <h1 style="text-align: center;" >환영합니다.</h1><br>
                    <img src="/images/reviewpoint.png" alt="" style="height: 400px; margin-left: 25%"><br><br>
                    <h3 style="text-align: center;">'<%=memVO.getU_nick() %>' 님의 마이페이지입니다.</h3>
                </div>
            </div>
        </div>
        <div class="row px-xl-5">
            <div class="col">
                <div class="bg-light p-30">
                    <div class="nav nav-tabs mb-4">
                        <a class="nav-item nav-link text-dark active" data-toggle="tab" href="#tab-pane-2" id="regproduct">등록상품</a>
                        <a class="nav-item nav-link text-dark" data-toggle="tab" href="#tab-pane-2" id="tradelog" >거래이력</a>
                        <a class="nav-item nav-link text-dark" data-toggle="tab" href="#tab-pane-2" id="jjimlist" >찜목록</a>
                        <a class="nav-item nav-link text-dark" data-toggle="tab" href="#tab-pane-2" id="sangchutalk" >상추톡</a>
                        <a class="nav-item nav-link text-dark" data-toggle="tab" href="#tab-pane-2" id="scoreboard" >거래후기</a>
                    </div>
                    <div class="tab-content"  id="subcontent">
                        <!-- <div class="tab-pane fade active show" id="tab-pane-1">
                         	<ul class ="acco-ul" style="margin-left: 5%;">
							  	<li class="acco-li ">
							    	<button class="button fa fa-star">　판매글</button>
							    	<p id="menu1" class="content-acco">
							    		<div id="regList-sell" class="row px-xl-5 pb-3">
										</div>
							    	</p>  
							  	</li>
							  	<li class="acco-li">
							    	<button class="button fa fa-star">　구매글</button>
							    	<p id="menu2" class="content-acco">
									</p>  
							  	</li>
							</ul>
                        </div> -->
<!--                         <div class="tab-pane fade" id="tab-pane-2"> -->
<!--                             <div > -->
<!--                             </div> -->
<!--                         </div> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Shop Detail End -->

	 <!--푸터시작-->
 	<div class="container-fluid bg-dark text-secondary mt-5 pt-5">
        <div class="row px-xl-5 pt-5">
            <div class="col-lg-4 col-md-12 mb-5 pr-3 pr-xl-5">
                <div class="title"><img src="../../images/Logo.png" alt="로고.png" class="logo" style="height: 30px;">상추마켓</div><br>
                <p class="mb-4">상호 : 상추마켓 | 대표자명 : 김석호 |<br> </p>
                <p class="mb-2"><i class="fa fa-map-marker-alt text-primary mr-3"></i>대전광역시
                        중구 계룡로 846</p>
                <p class="mb-2"><i class="fa fa-envelope text-primary mr-3"></i>kdy@ddit.com</p>
                <p class="mb-0"><i class="fa fa-phone-alt text-primary mr-3"></i>+010-1234-5678 </p>
            </div>
            <div class="col-lg-8 col-md-12">
                <div class="row">
                    <div class="col-md-4 mb-5">
                        <div class="d-flex flex-column justify-content-start">
                            <a class="text-secondary mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>마켓소개</a>
                            <a class="text-secondary mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>안심귀가서비스란?</a>
                            <a class="text-secondary mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>고객센터</a>
                            <a class="text-secondary mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>메인페이지</a>
                            <a class="text-secondary mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>마이페이지</a>
                            <a class="text-secondary" href="#"><i class="fa fa-angle-right mr-2"></i>커뮤니티</a>
                        </div>
                    </div>
                    <div class="col-md-4 mb-5">
                        <div class="d-flex flex-column justify-content-start">
                            <a class="text-secondary mb-2" href="#"><i class="fa fa-angle-right mr-2"></i>이용약관</a>
                            <a class="text-secondary" href="#"><i class="fa fa-angle-right mr-2"></i>개인정보처리방침</a>
                        </div>
                    </div>
                    <div class="col-md-4 mb-5">
                        <h6 class="text-secondary text-uppercase mt-4 mb-3">Follow Us</h6>
                        <div class="d-flex">
                            <a class="btn btn-primary btn-square mr-2" href="#"><i class="fab fa-twitter"></i></a>
                            <a class="btn btn-primary btn-square mr-2" href="#"><i class="fab fa-facebook-f"></i></a>
                            <a class="btn btn-primary btn-square mr-2" href="#"><i class="fab fa-linkedin-in"></i></a>
                            <a class="btn btn-primary btn-square" href="#"><i class="fab fa-instagram"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row border-top mx-xl-5 py-4" style="border-color: rgba(256, 256, 256, .1) !important;">
            <div class="col-md-6 px-xl-0">
                <p class="mb-md-0 text-center text-md-left text-secondary">
                    © <a class="text-primary" href="#">copyright</a>.상추마켓
                </p>
            </div>
            <div class="col-md-6 px-xl-0 text-center text-md-right">
                <img class="img-fluid" src="img/payments.png" alt="">
            </div>
        </div>
    </div>
 	
 	<!-- 푸터 끝 -->
    

    <!-- Back to Top -->
    <a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>

	

</body>

<script>

function memberUpdate(){
	$('#content').empty();
	// 회원정보 수정창
	location.href="<%=request.getContextPath()%>/memberUpdateForm.do";
};
function keywordManage(){
	$('#content').empty();
	$('#content').load("<%=request.getContextPath()%>/keywordRequest.do");
	// 키워드 관리창
};
function catManage(){
	$('#content').empty();
	$('#content').load("<%=request.getContextPath()%>/getCategory.do");
};
function commManage(){
	$('#content').empty();
	// 커뮤니티 관리창
};
function blackManaga(){
	$('#content').empty();
	$('#content').load("<%=request.getContextPath()%>/mypageBlackList.do");
};
// 도망쳐..
function escapeHere(){
	$('#content').empty();
	$('#content').load("<%=request.getContextPath()%>/escapeHere.do");
};
function passwordUpdate(){
	$('#content').empty();
	$('#content').load("<%=request.getContextPath()%>/passwordUpdate.do");
}

function offerManage(){
	$('#content').empty();
	// 거래 제시 관리창
	$('#content').load("<%=request.getContextPath()%>/getOffer.do");
};

</script>



</html>