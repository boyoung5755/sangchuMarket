package sangchu.adminMode.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import sangchu.adminMode.service.AdminModeServiceImpl;
import sangchu.adminMode.service.IAdminModeService;
import sangchu.main.vo.MemberVO;

@WebServlet("/stopdateList.do")
public class StopdateList extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		IAdminModeService service = AdminModeServiceImpl.getInstance();
		List<MemberVO> list = service.getAllUSeraStopdate();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/jsp/adminMode/stopdateList.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
