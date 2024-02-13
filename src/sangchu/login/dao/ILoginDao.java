package sangchu.login.dao;

import java.util.List;

import sangchu.category.vo.CatMiddleVO;
import sangchu.main.vo.MemberVO;
import sangchu.qnaBoard.vo.QNABoardVO;

public interface ILoginDao {

	/**
	 * 문의리스트 전체 불러오기
	 * @return List<QNABoardVO>
	 */
	public List<QNABoardVO> qnAList ();	
	/**
	 * email를 통해, all_users 테이블에서 MemberVO 가져오기
	 * @param email
	 * @return MemberVO
	 */
	public MemberVO getAllUsersVO (String email);
	
	/**
	 * 회원가입시, 이메일 중복검사 메소드. 회원 email을 입력받아, email 반환
	 * @param String email
	 * @return String email
	 */
	public String checkEmail(String email);
	
	/**
	 * 회원가입시, memVo를 넘겨받아 insert
	 * @param memVo
	 * @return int 성공시 1, 실패시 0반환
	 */
	public int insertFristSignup(MemberVO memVo);
	
	/**
	 * 2차 회원가입시, email을 넘겨받아 사용자의 a_code(회원가입상태) 확인
	 * @param email
	 * @return String acode
	 */
	public String checkACode(String email);
	
	/**
	 * 2차 회원가입시, 닉네임 중복검사
	 * @param String nickname
	 * @return String nickname
	 */
	public String checkNickname(String nickname);
	
	
	/**
	 * email로 all_users, users 테이블에서 모든 member의 정보를 가져오기
	 * @param email
	 * @return memVo
	 */
	public MemberVO getAllMemberInfo(String email);
	
	/**
	 * 로그인 시도 횟수 증가 (로그인 실패시)
	 * @param email
	 * @return 성공 1 , 실패 0
	 */
	public int updateLoginTry (String email);
	
	
	/**
	 * 로그인 시도 횟수 0으로 (로그인 성공시)
	 * @param email 
	 * @return 성공 1 , 실패 0
	 */
	public int updateTryToZero (String email);
	
	/**
	 * 	[회원가입] 대분류 카테고리 이름을 매개변수로  중분류 카테고리 이름들을 가져오는 메소드
	 * @param LaCatName
	 * @return CatMiddleVO
	 */
	public List<CatMiddleVO> getMiCatfromLaCat (String LaCatName);
	
	/**
	 * [2차회원가입]
	 * @param memVO
	 * @return 성공1, 실패0
	 */
	public int insertSecondSighup (MemberVO memVO);
	
	/**
	 * [회원가입] 성공시, a_code update (1 인증대기 -> 2 사용가능상태)
	 * @param email
	 * @return 성공1 실패0
	 */
	public int updateA_code(String email);
	
	/**
	 * memVO 에 담긴 정보로 이메일 찾기
	 * @param memVO
	 * @return List<String> 메일리스트
	 */
	public List<String> findEmail (MemberVO memVO);
	
	/**
	 * memVO 에 담긴 정보로 비밀번호 찾기
	 * @param memVO
	 * @return String pass
	 */
	public String findPass (MemberVO memVO);
	
	/**
	 * 비밀번호 찾기 시, 발급된 임시 비밀번호로 변경
	 * @param MemberVO memVO
	 * @return 성공 1, 실패 2
	 */
	public int updateTempPassword (MemberVO memVO);
	
	/**
	 * 총 회원수 (all_users 테이블에서)
	 * @return 총 회원수
	 */
	public int countAllusers ();
	
	/**
	 * 오늘 로그인한 회원수(all_users 테이블에서)
	 * @return 오늘 로그인한 회원수
	 */
	public int countToday();
	
		/**
	 * 오늘 가입한 회원수(users 테이블에서)
	 * @return 오늘 가입한 회원수
	 */
	public int countSignupToday();
	
	/**
	 * 로그인 성공하면 로그인 날짜를 오늘날짜로 
	 * @param email
	 * @return 성공1, 실패0
	 */
	public int updatelogindate (String email);
	
	/**
	 * 해당 성별의 회원수
	 * @param u_gender
	 * @return 성별수
	 */
	public int countGender (String u_gender);
}
