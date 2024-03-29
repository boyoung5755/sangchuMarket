package sangchu.tboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import sangchu.category.service.CategoryServiceImpl;
import sangchu.category.service.ICategoryService;
import sangchu.category.vo.CatLargeVO;
import sangchu.category.vo.CatMiddleVO;

@WebServlet("/lCatList.do")
public class LCatList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
	    
	    ICategoryService categoryService = CategoryServiceImpl.getService();
	    
	    List<CatLargeVO> lalist = categoryService.selectCatLargeList();
	    
	    Gson gson = new Gson();
	    String json = gson.toJson(lalist);
	    
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
	}
}
