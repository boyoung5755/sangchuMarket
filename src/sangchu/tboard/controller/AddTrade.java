package sangchu.tboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import sangchu.main.vo.MemberVO;
import sangchu.tboard.service.ITBoardService;
import sangchu.tboard.service.TBoardServiceImpl;
import sangchu.tboard.vo.TBoardVO;
import sangchu.trade.vo.TradeVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024
		* 100)

@WebServlet("/addTrade.do")
public class AddTrade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		ITBoardService service = TBoardServiceImpl.getInstance();

		String tbTitle = request.getParameter("tradeTitle");
		String tbPirce = request.getParameter("tradeprice");
		String offer = request.getParameter("offer");
		System.out.println(offer);

		String cMiddleCat = request.getParameter("mCategory");
		String tbContent = request.getParameter("tContent");
		String tType = request.getParameter("tradeType");

		HttpSession session = request.getSession();
		MemberVO memVO = (MemberVO) session.getAttribute("memVO");
		String email = memVO.getEmail();

		double loc = Double.parseDouble(request.getParameter("loc"));
		double loc2 = Double.parseDouble(request.getParameter("loc2"));
		System.out.println(loc);
		System.out.println(loc2);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Gson gson = new Gson();
		String json = gson.toJson("Success"); // 예시로 "Success"를 반환
		response.getWriter().write(json);

		TradeVO tvo = new TradeVO();

		tvo.setC_middlecat(cMiddleCat);
		tvo.setT_type(tType);
		tvo.setEmail(email);
		System.out.println(cMiddleCat);
		System.out.println(tType);
		System.out.println(email);

		int res = service.addTrade(tvo);

		TBoardVO tbvo = new TBoardVO();
		
		String tNo = service.selectMaxTNo();

		if (res > 0) {
			tbvo.setT_no(tNo);
			tbvo.setTb_title(tbTitle);
			tbvo.setTb_content(tbContent);
			tbvo.setTb_loc(loc);
			tbvo.setTb_loc2(loc2);
			tbvo.setTb_price(tbPirce);
			tbvo.setTb_offer(offer);

			res = service.addTBorad(tbvo);
			if (res < 1) {
				System.out.println("여긴 addTBorad! addTBorad는 실패했다");
			}
		} else {
			System.out.println("여긴 ADDTRADE! TRADEADD는 실패했다");
		}

		String filePath = "d:/midlleproforImages";

		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}

		System.out.println("tNo는 " + tNo);

		String fileName = "";

		int number = 0;

		try {
			for (Part part : request.getParts()) {
				fileName = extraxtFileName(part);
				if (!"".equals(fileName)) {
					String jpg = fileName.substring(fileName.length() - 3);
					if (jpg.equals("jpg")) {
						number++;
						// sql문에서 t_no를 넣어서 넣을 예정
						fileName = tNo + "_0" + number + ".jpg";
						System.out.println("저장될 이미지 이름은 " + fileName);
						try {
							// 파일을 저장 ==> Part 객체의 write() 메서드 이용
							// 형식) Part객체.write("저장될 경로/저장될 파일명")
							part.write(filePath + File.separator + fileName); // 파일 저장
							tbvo.setT_no(tNo);
							tbvo.setFileName(fileName);
							res = service.tImageInsert(tbvo);
							if (res < 1) {
								System.out.println("여긴 tImageInsert! tImageInsert는 실패했다");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println("상품 이미지 삽입 실패");
			e.printStackTrace();
		} catch (ServletException e) {
			System.out.println("상품 이미지 삽입 실패2");
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/tBoardList.do?id=" + cMiddleCat);
	}

	private String extraxtFileName(Part part) {
		String fileName = "";

		String conDisposition = part.getHeader("content-disposition");
		System.out.println(conDisposition);
		String[] items = conDisposition.split(";");
		for (String item : items) {
			if (item.trim().startsWith("filename")) {
				fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
			}
		}

		return fileName;
	}

}
