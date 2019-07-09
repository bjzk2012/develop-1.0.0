package cn.kcyf.commons.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session 保持Servlet
 * 
 * @author Tom
 * 
 */
public class KeepSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(KeepSessionServlet.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
		request.getSession(false);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		logger.debug("keep session, method get.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		logger.debug("keep session, method post.");
	}

}
