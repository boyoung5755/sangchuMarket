package sangchu.qnaBoard.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.config.MybatisUtil;
import sangchu.main.dao.IMainDao;
import sangchu.main.dao.MainDaoImpl;
import sangchu.main.vo.MainProdVO;
import sangchu.qnaBoard.vo.QNABoardVO;

public class QNABoardDaoImpl implements IQNABoardDao{

	private static IQNABoardDao dao;
	
	private QNABoardDaoImpl() {}
	private SqlSession session;
	public static IQNABoardDao getInstance() {
		if(dao==null) dao = new QNABoardDaoImpl();
		return dao;
	}
	
	
	@Override
	public int insertQNABoard(QNABoardVO vo) {
		SqlSession session = null;
		int cnt = 0;
		
		try {
			session = MybatisUtil.getSqlSession();
			cnt = session.insert("qna.insertQNABoard", vo);
			if(cnt>0) {
				// 작업이 성공하면 AutoCommit이 비활성화된 상태이기 때문에 직접 commit을 실행해야 한다.
				session.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null ) session.close();
		}
		
		return cnt;
	}


	@Override
	public List<QNABoardVO> qnaBoardList(QNABoardVO vo) {

		List<QNABoardVO> list=null;
		try { 
			session=MybatisUtil.getSqlSession();
			list = session.selectList("qna.qnaBoardList", vo);
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(session!=null) session.close();
		}
		return list;
	}


	@Override
	public int qnaBoardListCounting(QNABoardVO vo) {
		int res = 0;
		
		try {
			session = MybatisUtil.getSqlSession();
			res = session.selectOne("qna.qnaBoardListCounting" ,vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null ) session.close();
		}
		
		return res;
	}


	@Override
	public int deleteQNABoard(QNABoardVO vo) {
		SqlSession session = null;
		int cnt1 = 0;
		int cnt2 = 0;
		
		try {
			session = MybatisUtil.getSqlSession();
			cnt1 = session.delete("qna.deleteQNAReply", vo);
			cnt2 = session.delete("qna.deleteQNABoard", vo);
		
			if(cnt2>0) {
				// 작업이 성공하면 AutoCommit이 비활성화된 상태이기 때문에 직접 commit을 실행해야 한다.
				session.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null ) session.close();
		}
		
		return cnt2;
	}


	@Override
	public int updateQNABoard(QNABoardVO vo) {
		
		SqlSession session = null;
		int cnt = 0;
		
		try {
			session = MybatisUtil.getSqlSession();
			cnt = session.update("qna.updateQNABoard", vo);
			if(cnt>0) {
				// 작업이 성공하면 AutoCommit이 비활성화된 상태이기 때문에 직접 commit을 실행해야 한다.
				session.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null ) session.close();
		}
		
		return cnt;
	}


	@Override
	public QNABoardVO selectQnaInfo(String cm_no) {

		SqlSession session = null;
		QNABoardVO vo = null;
		
		try {
			session = MybatisUtil.getSqlSession();
			vo = session.selectOne("qna.selectQnaInfo", cm_no);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null ) session.close();
		}
		
		return vo;
	}


	@Override
	public List<QNABoardVO> qnaBoardManageList(QNABoardVO vo) {
		List<QNABoardVO> list=null;
		try { 
			session=MybatisUtil.getSqlSession();
			list = session.selectList("qna.qnaBoardManageList", vo);
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(session!=null) session.close();
		}
		return list;
	}


	@Override
	public int insertReply(QNABoardVO vo) {
		SqlSession session = null;
		int cnt = 0;
		
		try {
			session = MybatisUtil.getSqlSession();
			cnt = session.insert("qna.insertReply", vo);
			if(cnt>0) {
				// 작업이 성공하면 AutoCommit이 비활성화된 상태이기 때문에 직접 commit을 실행해야 한다.
				session.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null ) session.close();
		}
		
		return cnt;
	}


	@Override
	public int AdminqnaBoardListCounting(QNABoardVO vo) {
		int res = 0;
		
		try {
			session = MybatisUtil.getSqlSession();
			res = session.selectOne("qna.AdminqnaBoardListCounting" ,vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null ) session.close();
		}
		
		return res;
	}


	
	
}
