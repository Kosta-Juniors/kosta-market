package kosta.market.global.util;

import java.util.Objects;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SecurityUtil {

	private SecurityUtil(){
		throw new IllegalStateException("Utility class");
	}
	private static final String AUTHENTICATION = "authentication";
	private static final String AUTHORIZATION = "authorization";

	/**
	 * 기능 : 연결된 세션 불러오기 <br>
	 * 설명 : 현재 클라이언트와 연결된 세션 객체를 반환한다. 만약 세션이 없는 경우, 세션을 생성한다.
	 * @return HttpSession
	 */
	public static HttpSession getCurrentSession(){
		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return servletRequestAttribute.getRequest().getSession(true);
	}

	/**
	 * 기능 : 로그인 여부 검사 <br>
	 * 설명 : Session 의 "authentication" 에 user_id 가 존재하는지 확인한다. <br>
	 * @return 로그인을 한 상태라면 true, 아니면 false
	 */
	public static boolean isAuthenticated(){
		return !Objects.equals(getCurrentSession().getAttribute(AUTHENTICATION), null);
	}

	/**
	 * 기능 : 판매자 여부 검사 <br>
	 * 설명 : Session 의 "authorization" 에 seller_id 가 존재하는지 확인한다. <br>
	 * @return 판매자 권한을 가졌다면 true, 아니면 false
	 */
	public static boolean hasAuthorization(){
		return !Objects.equals(getCurrentSession().getAttribute(AUTHORIZATION), null);
	}

	/**
	 * 기능 : 사용자 인증 정보 반환 <br>
	 * 설명 : 현재 접속한 사용자의 인증 정보를 Session 에서 가져온다
	 * @return 로그인을 한 경우 user_id, 아니면 null
	 */
	public static Object getAuthentication(){
		return getCurrentSession().getAttribute(AUTHENTICATION);
	}

	/**
	 * 기능 : 사용자 인증 정보 등록 <br>
	 * 설명 : 현재 접속한 사용자의 인증 정보를 Session 에 등록한다.
	 * @param value user_id 값
	 */
	public static void setAuthentication(Object value){
		getCurrentSession().setAttribute(AUTHENTICATION, value);
	}

	/**
	 * 기능 : 판매자 정보 반환 <br>
	 * 설명 : 현재 접속한 사용자의 판매자 정보를 Session 에서 가져온다.
	 * @return 판매자인 경우 seller_id, 아니면 null
	 */
	public static Object getAuthorization(){
		return getCurrentSession().getAttribute(AUTHORIZATION);
	}

	/**
	 * 기능 : 판매자 정보 설정 <br>
	 * 설명 : 현재 접속한 사용자의 판매자 정보를 Session 에 등록한다.
	 * @param value seller_id
	 */
	public static void setAuthorization(Object value){
		getCurrentSession().setAttribute(AUTHORIZATION, value);
	}

}
