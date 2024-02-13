package sangchu.qnaBoard.service;

import java.util.List;

import sangchu.main.dao.IMainDao;
import sangchu.main.dao.MainDaoImpl;
import sangchu.main.service.IMainService;
import sangchu.main.service.MainServiceImpl;
import sangchu.main.vo.PageVO;
import sangchu.qnaBoard.dao.IQNABoardDao;
import sangchu.qnaBoard.dao.QNABoardDaoImpl;
import sangchu.qnaBoard.vo.QNABoardVO;

public class QNABoardServiceImpl implements IQNABoardService{

	private IQNABoardDao dao;
	private static IQNABoardService service;
	private QNABoardServiceImpl() {
		dao = QNABoardDaoImpl.getInstance();
	}
	public static IQNABoardService getInstance()  {
		if(service==null) service = new QNABoardServiceImpl();
		return service;
	}
	
	
	
	@Override
	public int insertQNABoard(QNABoardVO vo) {
		// TODO Auto-generated method stub
		return dao.insertQNABoard(vo);
	}
	@Override
	public List<QNABoardVO> qnaBoardList(QNABoardVO vo) {
		// TODO Auto-generated method stub
		return dao.qnaBoardList(vo);
	}
	@Override
	public PageVO pageInfo(QNABoardVO vo) {
		//전체글갯수 구하기 
		int count = dao.qnaBoardListCounting(vo);
		
		//전체페이지수 구하기
		int perList = 6;
		int totalPage = (int)Math.ceil(count / (double)perList);
		
		//start, end 구하기   19 21
		int start = (vo.getPage() -1) * perList + 1;
		int end = start + perList - 1;
		if(end>count) end = count;
		
		//시작페이지끝페이지 
		int perPage = 3;
		int startPage = ((vo.getPage()-1)/perPage*perPage)+1;
		int endPage = startPage+perPage-1;
		if(endPage>totalPage) endPage = totalPage;
		
		PageVO  pvo = new PageVO();
		pvo.setStart(start);
		pvo.setEnd(end);
		pvo.setStartPage(startPage);
		pvo.setEndPage(endPage);
		pvo.setTotalPage(totalPage);
		
		return pvo;
	}
	@Override
	public int qnaBoardListCounting(QNABoardVO vo) {
		// TODO Auto-generated method stub
		return dao.qnaBoardListCounting(vo);
	}
	@Override
	public int deleteQNABoard(QNABoardVO vo) {
		// TODO Auto-generated method stub
		return dao.deleteQNABoard(vo);
	}
	@Override
	public int updateQNABoard(QNABoardVO vo) {
		// TODO Auto-generated method stub
		return dao.updateQNABoard(vo);

	}
	@Override
	public QNABoardVO selectQnaInfo(String cm_no) {
		// TODO Auto-generated method stub
		return dao.selectQnaInfo(cm_no);
	}
	@Override
	public List<QNABoardVO> qnaBoardManageList(QNABoardVO vo) {
		// TODO Auto-generated method stub
		return dao.qnaBoardManageList(vo);
	}
	@Override
	public int insertReply(QNABoardVO vo) {
		// TODO Auto-generated method stub
		return dao.insertReply(vo);
	}
	@Override
	public PageVO AdminpageInfo(QNABoardVO vo) {
		//전체글갯수 구하기 
		int count = dao.AdminqnaBoardListCounting(vo);
		
		//전체페이지수 구하기
		int perList = 8;
		int totalPage = (int)Math.ceil(count / (double)perList);
		
		//start, end 구하기   19 21
		int start = (vo.getPage() -1) * perList + 1;
		int end = start + perList - 1;
		if(end>count) end = count;
		
		//시작페이지끝페이지 
		int perPage = 3;
		int startPage = ((vo.getPage()-1)/perPage*perPage)+1;
		int endPage = startPage+perPage-1;
		if(endPage>totalPage) endPage = totalPage;
		
		PageVO  pvo1 = new PageVO();
		pvo1.setStart(start);
		pvo1.setEnd(end);
		pvo1.setStartPage(startPage);
		pvo1.setEndPage(endPage);
		pvo1.setTotalPage(totalPage);
		
		return pvo1;
	}

}