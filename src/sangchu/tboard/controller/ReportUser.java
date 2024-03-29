package sangchu.tboard.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sangchu.adminMode.vo.EnquiryVO;
import sangchu.main.vo.MemberVO;
import sangchu.tboard.service.ITBoardService;
import sangchu.tboard.service.TBoardServiceImpl;

@WebServlet("/reportUser.do")
public class ReportUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	
    	String reported=request.getParameter("reported");
    	String content =request.getParameter("contents");
    	
    	System.out.println(reported);
    	System.out.println(content);
    	
    	HttpSession session = request.getSession();
		MemberVO memVO = (MemberVO)request.getSession().getAttribute("memVO");

		String email="";
		
		if(memVO != null){
			email = memVO.getEmail();
		}
    	
    	EnquiryVO vo=new EnquiryVO();
    	
    	vo.setEmail(email);
    	vo.setE_accuser(content);
    	vo.setE_target(reported);
    	
    	ITBoardService service=TBoardServiceImpl.getInstance();
    	
    	int res=service.addEnquiry(vo);
    	if(res>0) {
    		System.out.println("신고 성공");
    	}else {
    		System.out.println("신고 실패");
    	}
    	request.setAttribute("result", res);
    	
    	request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);
    	
	}

}
