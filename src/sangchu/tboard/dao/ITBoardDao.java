package sangchu.tboard.dao;

import java.util.List;

import sangchu.adminMode.vo.EnquiryVO;
import sangchu.category.vo.CatMiddleVO;
import sangchu.tboard.vo.TBoardVO;
import sangchu.trade.vo.TradeVO;
import sangchu.uView.vo.UViewVO;

public interface ITBoardDao {

	/**
	 * 중카테고리 번호를 기준으로 trade 테이블 전체 조회 
	 * @param mid c_middlecat
	 * @return
	 */
	public List<TBoardVO> selectTBoard(String mid);
	
	
	
	

	/**
	 * t_no를 기준으로 t_board 테이블 컬럼 조회
	 * @param tid t_no
	 * @return
	 */
	public TBoardVO selectTBoardOne(String tid);



	/**
	 * t_no를 기준으로 trade 테이블 컬럼 조회
	 * @param tid t_no
	 * @return
	 */
	public TradeVO selectTradeOne(String tid);



	/**
	 * t_no를 기준으로 image 불러오기
	 * @param tid t_no
	 * @return
	 */
	public List<String> tImage(String tid);




	/**
	 * t_no를 UView에 넣기(최근 본 상품)
	 * @param uvo
	 * @return
	 */
	public int addUView(UViewVO uvo);





	public int tImageInsert(TBoardVO tvo);





	public int addTrade(TradeVO tvo);





	public int addTBorad(TBoardVO tbvo);





	public List<CatMiddleVO> selectCatMiddleOne(String lCategory);





	public int upCnt(String tid);





	public int checkJJim(String tid);





	public List<TradeVO> selectTrade(String mid);





	public String selectMiddleName(String mid);





	public String selectMaxTNo();





	public String getUNick(String email);





	public int addEnquiry(EnquiryVO vo);





	public String tImageOne(String tid);
}
