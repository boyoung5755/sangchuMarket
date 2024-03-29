package sangchu.login.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import sangchu.login.service.ILoginService;
import sangchu.login.service.LoginServiceImpl;
import sangchu.main.vo.MemberVO;

@WebServlet("/login.do")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
//		String email = request.getParameter("email");
//
//		MemberVO memVO = new MemberVO();
//		memVO.setEmail(email);
//
//		ILoginService service = LoginServiceImpl.getInstance();
//		MemberVO vo = service.getAllMemberInfo(email);
//
//		int tryLogin = vo.getU_try();
//		
//		Map<String, Integer> resMap = new HashMap<>();
//		resMap.put("tryLogin", tryLogin);
//		
//		Gson gson = new Gson();
//		String resJson = gson.toJson(resMap);
//		
//		response.setContentType("application/json; charset=utf-8");
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().write(resJson);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		ILoginService service = LoginServiceImpl.getInstance();

		String inputEmail = request.getParameter("email");
		String inputPass = request.getParameter("password");

		MemberVO memVO = new MemberVO();
		memVO = service.getAllUsersVO(inputEmail);
		
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		//1) 존재하는 이메일인지 판별 : memVO not null -> 계정존재
		if (memVO != null) { 
			
			MemberVO extendedMemVO = new MemberVO();
			//2) 이메일&비밀번호 일치
				if (memVO.getPass().equals(inputPass)) { 
					//일치할 경우, users의 정보가 담긴 두개의 테이블의 모든 정보를 extendedMemVo 객체에 담는다.
					
					String aCode = memVO.getA_code();
					if (aCode.equals("0")) { // admin 계정
						HttpSession session = request.getSession();
						session.setAttribute("memVO", memVO);
						session.setMaxInactiveInterval(30*60); // 초 단위 - 30분
						service.updatelogindate(inputEmail);
						request.getRequestDispatcher(request.getContextPath() + "/adminMainPage.do").forward(request, response);
						//로그인을 성공하면 redirect로 해야함
						
						return;
					}else if(aCode.equals("1")) { //이메일 인증 대기 상태
						extendedMemVO = service.getAllMemberInfo(inputEmail);
						//세션에 이 VO 전체를 저장할거라서, 비밀번호는 제외시켜줌
						extendedMemVO.setPass("");
//						session.setAttribute("memVO", memVO);
//						session.setMaxInactiveInterval(30*60); // 초 단위 - 30분
						PrintWriter writer = response.getWriter();
						writer.println("<script>alert('이메일 인증 후 다시 시도해주세요.'); history.back(); </script>");
					}
					else { // user 계정 a_code : 2 (사용가능 상태)
						
						extendedMemVO = service.getAllMemberInfo(inputEmail);
						if(extendedMemVO.getU_stopdate()!=null) {
							//세션에 이 VO 전체를 저장할거라서, 비밀번호는 제외시켜줌
							String stopdateStr = extendedMemVO.getU_stopdate().substring(0,10);
							LocalDate stopdateDate = LocalDate.parse(stopdateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							LocalDate todayCompare = LocalDate.now();
							System.out.println("정지일 " + stopdateStr);
							System.out.println("오늘 ==> "+ todayCompare);
							PrintWriter writer = response.getWriter();
							if(!stopdateDate.isBefore(todayCompare)) {
								//isBefore : 앞에있는날짜가 뒤에있는 날짜보다 빠르면 true
								writer.println("<script>alert('정지된 계정입니다.'); history.back(); </script>");
								return;
							}
						}
						// 사용자의 마지막 로그인 날짜를 오늘로 변경 & 로그인 시도 횟수를 0으로
						service.updatelogindate(inputEmail);
						service.updateTryToZero(inputEmail);
						//다오를 두개로하고 서비스를 하나로 !
						
						extendedMemVO = service.getAllMemberInfo(inputEmail);
						extendedMemVO.setPass("");
						HttpSession session = request.getSession();
						session.setAttribute("memVO", extendedMemVO);
						session.setMaxInactiveInterval(30*60); // 초 단위 - 30분
						response.sendRedirect(request.getContextPath()+"/sangchuMain.do");
						return; // 무한루프를 방지하기위해 return 사용 (sendRedirect를 사용시 발생하는 문제점)
						
					}
				}else {
					String aCode = memVO.getA_code();
					//a_code가 0또는 1인 경우에는 로그인 시도 횟수가 아예 존재하지 x
					if (aCode.equals("0") || aCode.equals("1")) { // admin 계정 or 인증대기중
						PrintWriter writer = response.getWriter();
						writer.println("<script>alert('아이디와 비밀번호가 일치하지 않습니다.'); history.back(); </script>");
					}	else { // user 계정 a_code : 2 (사용가능 상태) -> 여긴 로그인 횟수 있음
						extendedMemVO = service.getAllMemberInfo(inputEmail);
						
						int currentTry = extendedMemVO.getU_try()+1;
						String currentTryStr = String.valueOf(currentTry);
						System.out.println("로그인 시도 횟수 : ==> " + currentTry);
						
						int resUpdateLoginTry = service.updateLoginTry(extendedMemVO.getEmail());
						System.out.println("로그인 시도 횟수 update 성공 1, 실패 0==> " + resUpdateLoginTry);
						PrintWriter writer = response.getWriter();
						writer.println("<script>alert('아이디와 비밀번호가 일치하지 않습니다.'); history.back(); </script>");
						
						// 5회 초과시
						if (currentTry > 5) {
							request.setAttribute("currentTry", currentTryStr);
							request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/login/loginMain.jsp").forward(request, response);
						}
					}
				}
		}else {//1) 존재하는 이메일인지 판별 : memVO null -> 계정존재x
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('존재하지 않는 이메일입니다.'); history.back(); </script>");
		}
	}
}
