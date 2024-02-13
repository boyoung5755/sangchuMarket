package sangchu.mypage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sangchu.mypage.service.IMypageService;
import sangchu.mypage.service.MypageServiceImpl;
import sangchu.mypage.vo.TradeLogVO;

@WebServlet("/tradeReview.do")
public class TradeReview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
		최초 작성자 : 김석호
		최초 작성일 : 2023.08.12 15:33
		
		
		
		거래후기 요청을 처리하는 서블릿입니다.
	*/

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/mypage/tradeReview.jsp").forward(request, response);
	}

	// 이거.. 만들고보니 거래이력이랑 같은데..ㅠ
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		
		IMypageService service = MypageServiceImpl.getInstance();
		
		List<TradeLogVO> list = service.forAllTradeLog(email);
		
		request.setAttribute("resultList", list);
		request.getRequestDispatcher("/WEB-INF/jsp/util/tradeLogList.jsp").forward(request, response);
	}
}