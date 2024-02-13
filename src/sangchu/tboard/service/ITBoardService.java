package sangchu.tboard.service;

import java.util.List;

import sangchu.adminMode.vo.EnquiryVO;
import sangchu.category.vo.CatMiddleVO;
import sangchu.main.vo.PageVO;
import sangchu.tboard.vo.TBoardVO;
import sangchu.trade.vo.TradeVO;
import sangchu.uView.vo.UViewVO;

public interface ITBoardService {

	public List<TBoardVO> selectTBoard(String tid);
	
	public TBoardVO selectTBoardOne(String tid);

	public TradeVO selectTradeOne(String tid);

	public List<String> tImage(String tid);

	public int addUView(UViewVO uvo);

	public int tImageInsert(TBoardVO tbvo);

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
