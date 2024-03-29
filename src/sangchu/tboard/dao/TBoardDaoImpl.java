package sangchu.tboard.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.config.MybatisUtil;
import sangchu.adminMode.vo.EnquiryVO;
import sangchu.category.vo.CatMiddleVO;
import sangchu.tboard.vo.TBoardVO;
import sangchu.trade.vo.TradeVO;
import sangchu.uView.vo.UViewVO;

public class TBoardDaoImpl implements ITBoardDao {

	private static ITBoardDao dao;
	
	public static ITBoardDao getInstance() {
		if(dao==null)dao=new TBoardDaoImpl();
		return dao;
	}
	
	@Override
	public List<TBoardVO> selectTBoard(String mid) {
		
		List<TBoardVO> list=null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			list = sql.selectList("tboard.selectTBoard", mid);
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(sql!=null) sql.close();
		}
		return list;
	}
	
	
	

	@Override
	public TBoardVO selectTBoardOne(String tid) {
		
		TBoardVO vo = null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			vo = sql.selectOne("tboard.selectTBoardOne", tid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sql.close();
		}
		return vo;
	}

	@Override
	public TradeVO selectTradeOne(String tid) {
		
		TradeVO vo = null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			vo = sql.selectOne("tboard.selectTradeOne", tid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql!=null) sql.close();
		}
		return vo;
	}

	@Override
	public List<String> tImage(String tid) {
		
		List<String> list=null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			list = sql.selectList("tboard.tImage", tid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sql.close();
		}
		
		return list;
	}

	@Override
	public int addUView(UViewVO uvo) {
		
		int res=0;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.insert("tboard.addUView", uvo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(res>0) sql.commit();
			if(sql!=null) sql.close();
		}
		return res;
	}

	@Override
	public int tImageInsert(TBoardVO tvo) {
		
		int res=0;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.insert("tboard.tImageInsert", tvo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(res>0) sql.commit();
			if(sql!=null) sql.close();
		}
		return res;
	}

	@Override
	public int addTrade(TradeVO tvo) {
		
		int res=0;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.insert("tboard.addTrade", tvo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(res>0) sql.commit();
			if(sql!=null) sql.close();
		}
		return res;
	}

	@Override
	public int addTBorad(TBoardVO tbvo) {
		
		int res=0;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.insert("tboard.addTBorad", tbvo);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(res>0) sql.commit();
			if(sql!=null) sql.close();
		}
		return res;
	}

	@Override
	public List<CatMiddleVO> selectCatMiddleOne(String lCategory) {
		
		List<CatMiddleVO> list=null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			list = sql.selectList("tboard.selectCatMiddleOne", lCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql!=null) sql.close();
		}
		return list;
	}

	@Override
	public int upCnt(String tid) {
		
		int res=0;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.update("tboard.upCnt", tid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(res>0) sql.commit();
			if(sql!=null) sql.close();
		}
		return res;
	}

	@Override
	public int checkJJim(String tid) {
		
		int Jjim=0;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			Jjim = sql.selectOne("tboard.checkJJim", tid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(Jjim>0) sql.commit();
			if(sql!=null) sql.close();
		}
		
		return Jjim;
	}

	@Override
	public List<TradeVO> selectTrade(String mid) {
		
		List<TradeVO> list=null;

		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			list = sql.selectList("tboard.selectTrade", mid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql!=null) sql.close();
		}
		
		return list;
	}

	@Override
	public String selectMiddleName(String mid) {
		
		String res=null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.selectOne("tboard.selectMiddleName", mid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql!=null) sql.close();
		}
		
		return res;
	}

	@Override
	public String selectMaxTNo() {
		
		String res=null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.selectOne("tboard.selectMaxTNo");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql!=null) sql.close();
		}
		
		return res;
	}

	@Override
	public String getUNick(String email) {
		
		String res=null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.selectOne("tboard.getUNick",email);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql!=null) sql.close();
		}
		
		return res;
	}

	@Override
	public int addEnquiry(EnquiryVO vo) {
		
		int res=0;

		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.insert("tboard.addEnquiry",vo);
			if(res>0) {
				System.out.println("신고됬잖아! 분명! 맞지!?");
				
			}else {
				System.out.println("신고 실패 했어!? 또!?");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(res>0)sql.commit();
			if(sql!=null) sql.close();
		}
		
		return res;
	}

	@Override
	public String tImageOne(String tid) {
		
		String res=null;
		
		SqlSession sql=MybatisUtil.getSqlSession();
		
		try {
			res = sql.selectOne("tboard.tImageOne",tid);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sql!=null)sql.close();
		}
		
		return res;
	}
}
