package sangchu.chat.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sangchu.chat.service.ChatServiceImpl;
import sangchu.chat.service.IChatService;

/**
 * Servlet implementation class ImageView
 */
@WebServlet("/imageViewforChat.do")
public class ImageViewforChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*
		최초 작성자 : 김석호
		최초 작성일 : 2023.08.10 01:23
		
		최근 수정자 : 김석호
		최근 수정일 : 2023.08.14 13:20
		
		미완 - 기본이미지 불러오는 부분 경로설정에 대해..?
		
		수정내역
		2023.08.14 13:20 - 김석호 : 이미지 읽어오는 부분 주석 수정
		
		채팅방에서 닉네임 옆에 이미지를 출력하기 위해 불러오는 서블릿입니다.
		
	*/
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 파라미터로 넘어온 이메일을 가져온다
		String email = request.getParameter("email");
		
		// 이메일로 이미지 폴더에 있는 유저 이미지를 읽어온다.
		IChatService service = ChatServiceImpl.getInstance();
		String imgsrc = service.emailImage(email);
		
		// 이미지가 저장된 폴더 설정
		String filePath = "d:/midlleproforImages";
		
		File file = null;
				
		if(imgsrc!=null) {   // 이미지 테이블에서 파일이 있다고 판단되면
			file = new File(filePath + File.separator + imgsrc);
		}else { // 없다면 기본이미지로 세팅
//			file = new File("D:/A_TeachingMeterial/2.Web/formiddleServer/sangchumarket/WebContent/images/defaultUserImage.png");
			file = new File(filePath + File.separator + "defaultUserImage.png");
		}
		if(!file.exists()) {
//			file = new File("D:/A_TeachingMeterial/2.Web/formiddleServer/sangchumarket/WebContent/images/defaultUserImage.png");
			file = new File(filePath + File.separator + "defaultUserImage.png");
		}
		// 출력
		printImage(file, response);
		return;
	}

	private synchronized void printImage(File file, HttpServletResponse response) {
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
