package sangchu.login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sangchu.Mailing;
import sangchu.login.service.ILoginService;
import sangchu.login.service.LoginServiceImpl;
import sangchu.main.vo.MemberVO;

@WebServlet("/signup.do")
public class Signup extends HttpServlet {
   private static final long serialVersionUID = 1L;
   
   /*
      최초 작성자 : 박민주
      최초 작성일 : 2023.08.07
      
      2023.08.07 김석호
      수정 내역 : 메일링 서비스 토대 마련
   */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.getRequestDispatcher("/WEB-INF/jsp/login/signup1.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {
      request.setCharacterEncoding("utf-8");
      
      String userId = request.getParameter("mail");
      String domainInput = request.getParameter("domain");
      String domainSelected = request.getParameter("selectDomain");
      
      System.out.println(userId);
      String domain=null;
      
      if(domainSelected.equals("1")) {
         domain=domainInput;
      }else {
         domain=domainSelected;
      }
      
      
      //아래 변수들 중 email만 url 에 담아서 요청 예정
      String email = userId + '@' + domain;
      String name = request.getParameter("name");
      String pass = request.getParameter("pass");
      
      
      String message = "[상추마켓 이메일 인증]\n\n";
      message += name + "(" + email +  ") 님 안녕하세요 !\n";
      message += "아래 URL 클릭시 회원가입 페이지로 이동합니다.\n";
      message += "http://192.168.35.36/signup2.do?email=" + email;
      
      String subject="[상추마켓] 이메일 인증요청 메일";

      Mailing mail = Mailing.getMailservice();
      
      // email : 유저 email(String타입)
      // message : 2차회원가입 url + 유저 이메일정보가 담긴 쿼리스트링 (String타입)
      // subject : 메일 제목
      // 리턴값 : 메일 발송 성공:1 실패:0
      int resMail = mail.mailing(email, message, subject); 
      
      MemberVO memVo = new MemberVO();
      memVo.setEmail(email);
      memVo.setName(name);
      memVo.setPass(pass);
      
      System.out.println("email ==> " + email);
      
      System.out.println("memVo ==> " +memVo.toString());
      ILoginService service = LoginServiceImpl.getInstance();
      int resInsert =service.insertFristSignup(memVo); // 쿼리 insert 성공 1, 실패0
      
      System.out.println("resMail ==>" + resMail);
      System.out.println("resInsert ==>" + resInsert);
      
      /*
       * 메일발송 하면서 1차 정보 insert
       * url 타고 들어와서 (url에는 email 포함)
       * email가지고 a_code판단 (2차 페이지로 이동 또는 유효하지 않은 ~ 에러메시지 페이지로 이동)
       * */
      
      if(resMail>0) { //메일발송 성공
         if(resInsert>0) { //insert 성공  - 회원가입 완료 페이지로 리다이렉트

        	 response.setContentType("text/html; charset=utf-8");
        	 PrintWriter writer = response.getWriter();
        	 writer.println("<script>alert('인증메일을 발송하였습니다. 메일함을 확인해주세요.');");
        	 writer.println("location.href= '"+ request.getContextPath() + "/loginMain.do'</script>");
        	 writer.close();
        	 
        	 
         
         
         }else { //insert 실패 - 
            request.setAttribute("errorMessage", "예기치 못한 error 발생하였습니다.");
            request.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(request, response);
         }
      }else { //메일 발송 실패
         request.setAttribute("errorMessage", "예기치 못한 error 발생하였습니다.");
         request.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(request, response);
      }
      
   }

}