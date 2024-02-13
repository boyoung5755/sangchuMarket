package sangchu.login.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sangchu.login.service.ILoginService;
import sangchu.login.service.LoginServiceImpl;

@WebServlet("/duplicateInspection.do")
public class DuplicateInspection extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   //닉네임
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	   String nickname = request.getParameter("nickname");
	   
	   ILoginService service = LoginServiceImpl.getInstance();
	  String res = service.checkNickname(nickname);
	  
	  System.out.println("checkEmail.do 서블릿 - nickname => "+ nickname);
      System.out.println("checkEmail.do 서블릿 - res => "+ res);
	  
	  request.setAttribute("result", res);
      request.getRequestDispatcher("/WEB-INF/jsp/login/duplicateInspection.jsp").forward(request, response);
   
   }

   //이메일
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
      String email = request.getParameter("email");
      
      ILoginService service = LoginServiceImpl.getInstance();
      String res = service.checkEmail(email);
      
      System.out.println("checkEmail.do 서블릿 - email => "+ email);
      System.out.println("checkEmail.do 서블릿 - res => "+ res);
      
      request.setAttribute("result", res);
      request.getRequestDispatcher("/WEB-INF/jsp/login/duplicateInspection.jsp").forward(request, response);
   }

}