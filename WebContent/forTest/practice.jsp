<%@page import="sangchu.main.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="utf-8">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/addTrade.do" method="post"
		enctype="multipart/form-data">
		<div class="bg-light p-30 mb-5">
			<div class="row">
				<div class="col-md-6 form-group">
					<label><i class="fa fa-image"></i> 상품이미지</label> <input
						class="form-control" type="file" name="file" multiple="multiple">
				</div>
			</div>
		</div>
		<input type="submit">
	</form>
</body>

</html>