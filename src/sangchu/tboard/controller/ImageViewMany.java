package sangchu.tboard.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sangchu.tboard.service.ITBoardService;
import sangchu.tboard.service.TBoardServiceImpl;

@WebServlet("/imageViewMany.do")
public class ImageViewMany extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 파라미터로 넘어온 이메일을 가져온다
		String tid = request.getParameter("id");
		
		// 이미지로 이미지 폴더에 있는 유저 이미지를 읽어온다.
		ITBoardService service=TBoardServiceImpl.getInstance();
		
		String fileName= service.tImageOne(tid);
		System.out.println("imageViewMany : "+ fileName);
		// 이미지가 저장된 폴더 설정
		String filePath = "d:/midlleproforImages";
		
		File file = null;
				
		if(fileName!=null) {   // 이미지 테이블에서 파일이 있다고 판단되면
				file = new File(filePath + File.separator+fileName);
		}else { // 없다면 기본이미지로 세팅
			file = new File(filePath + File.separator + "defaultUserImage.png");
		}
		if(!file.exists()) {
			file = new File(filePath + File.separator + "defaultUserImage.png");
		}
		
		printImage(file, response);
		
		//request.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(request, response);
	}
	
	private void printImage(File file, HttpServletResponse response) {
		BufferedInputStream bin = null;
		BufferedOutputStream bout = null;
		try {
			// 파일 입력용 스트림객체 생성
			bin = new BufferedInputStream(new FileInputStream(file));
			
			// 출력용 스트림 객체 생성
			bout = new BufferedOutputStream(response.getOutputStream());
			
			byte[] temp = new byte[1024];
			int len = 0;
			while((len = bin.read(temp)) > 0) {
				bout.write(temp, 0, len);
			}
			bout.flush();
			
		} catch (Exception e) {
			System.out.println("입출력 오류 : " + e.getMessage());
		} finally {
			if(bin!=null) try { bin.close(); }catch(IOException e) {}
			if(bout!=null) try { bout.close(); }catch(IOException e) {}
		}
	}

}
