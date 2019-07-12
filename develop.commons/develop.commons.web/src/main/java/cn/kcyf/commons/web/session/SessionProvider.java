package cn.kcyf.commons.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Session提供者
 */
public interface SessionProvider {
	Serializable getAttribute(HttpServletRequest request, String name);

	void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value);

	String getSessionId(HttpServletRequest request, HttpServletResponse response);

	void logout(HttpServletRequest request, HttpServletResponse response);
}
