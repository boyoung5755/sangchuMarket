package sangchu.adminMode.dao;

import java.util.List;

import sangchu.adminMode.vo.EnquiryNUsersVO;
import sangchu.adminMode.vo.EnquiryVO;
import sangchu.main.vo.MemberVO;

public interface IAdminModeDao {
	/**
	 * 관리자 페이지에서 회원정보 조회
	 * @param email
	 * @return MemberVO
	 */
	public MemberVO searchUserEmail (String email);
	
	/**
	 * 관리자 페이지에서 회원정지일 해제
	 * @param email
	 * @return 성공1, 실패0
	 */
	public int updateStopdate (String email);
	
	/**
	 * 정지일이 존재하는 회원의 수
	 * @return 정지일이 존재하는 회원의 수
	 */
	public int countUsersHavingStopdate();
	
	/**
	 * 정지일이 존재하는 회원 memVO list
	 * @return List<MemberVO>
	 */
	public List<MemberVO> getAllUSeraStopdate ();
	
	/**
	 * 정지일 일괄 해제
	 * @return 성공 1, 실패 0
	 */
	public int clearStopdateAll();
	
	/**
	 * 회원 한명의 정지일 해제 
	 * @return 성공 1, 실패 0
	 */
	public int clearStopdate(String email);
	
	/**
	 * 모든 신고글 가져오기
	 * @return List<EnquiryVO>
	 */
	public List<EnquiryVO> getAllEnquiry();
	
	/**
	 * e_no로 특정 신고글 가져오기
	 * @param e_no
	 * @return EnquiryVO
	 */
	public EnquiryVO getEnquiry(String e_no);
	
	/**
	 * 회원의 정지일 특정일로 업데이트
	 * @param memVO
	 * @return 성공1, 실패0
	 */
	public int setStopdate (MemberVO memVO);
	
	/**
	 * 문의테이블에서 e_targe의 정보 가져오기
	 * @param e_target
	 * @return EnquiryNUsersVO
	 */
	public EnquiryNUsersVO getStopdateNEnquiry (String e_target);
	
	/**
	 * tradeVO에서 게시글 대분류 카운트
	 * @param lCat
	 * @return 대분류 게시글 수
	 */
	public int countTradeLCat (String lCat);
}
