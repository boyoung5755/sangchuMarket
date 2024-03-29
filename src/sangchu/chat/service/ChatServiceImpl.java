package sangchu.chat.service;

import java.util.List;

import sangchu.chat.dao.ChatDaoImpl;
import sangchu.chat.dao.IChatDao;
import sangchu.chat.vo.ChatDetailVO;
import sangchu.chat.vo.ChatVO;
import sangchu.chat.vo.ForTradeLogVO;
import sangchu.chat.vo.OfferVO;
import sangchu.chat.vo.TBoardOfferVO;
import sangchu.mypage.vo.TradeLogVO;
import sangchu.tboard.vo.TBoardVO;

public class ChatServiceImpl implements IChatService {
	private IChatDao dao;
	private static IChatService service;
	private ChatServiceImpl() {
		dao = ChatDaoImpl.getInstance();
	}
	public synchronized static IChatService getInstance() {
		if(service ==null) service = new ChatServiceImpl();
		return service;
	}
	
	
	@Override
	public ChatVO getChatVO(String c_no) {
		return dao.getChatVO(c_no);
	}

	@Override
	public List<ChatVO> getAllChatList(String email) {
		return dao.getAllChatList(email);
	}

	@Override
	public List<ChatDetailVO> getChatLog(String c_no) {
		return dao.getChatLog(c_no);
	}

	@Override
	public int insertChatLog(ChatDetailVO vo) {
		return dao.insertChatLog(vo);
	}

	@Override
	public int insertNewChat(String t_no) {
		return dao.insertNewChat(t_no);
	}
	@Override
	public String emailImage(String email) {
		return dao.emailImage(email);
	}
	@Override
	public String whoRegThis(String t_no) {
		return dao.whoRegThis(t_no);
	}
	@Override
	public int isThereAlreadyChatRoom(ChatVO chatVO) {
		return dao.isThereAlreadyChatRoom(chatVO);
	}
	@Override
	public int createNewChatRoom(ChatVO chatVO) {
		return dao.createNewChatRoom(chatVO);
	}
	@Override
	public OfferVO getOffer(String c_no) {
		return dao.getOffer(c_no);
	}
	@Override
	public int tradeOffer(OfferVO offerVO) {
		return dao.tradeOffer(offerVO);
	}
	@Override
	public int regTradeLog(ForTradeLogVO ftLogVO) {
		return dao.regTradeLog(ftLogVO);
	}
	@Override
	public int afterTrade(TradeLogVO tradeLogVO) {
		return dao.afterTrade(tradeLogVO);
	}
	@Override
	public int endTrade(String c_no) {
		return dao.endTrade(c_no);
	}
	@Override
	public int denyOffer(String c_no) {
		return dao.denyOffer(c_no);
	}
	@Override
	public int breakTrade(String c_no) {
		return dao.breakTrade(c_no);
	}
	@Override
	public String whoRegByCNO(String c_no) {
		return dao.whoRegByCNO(c_no);
	}
	@Override
	public TBoardOfferVO getTBoardInfoByCNO(String c_no) {
		return dao.getTBoardInfoByCNO(c_no);
	}
	@Override
	public String getTNOByCNO(String c_no) {
		return dao.getTNOByCNO(c_no);
	}

}
