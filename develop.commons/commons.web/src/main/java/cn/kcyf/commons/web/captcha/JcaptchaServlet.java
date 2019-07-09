package cn.kcyf.commons.web.captcha;

import cn.kcyf.commons.web.session.SessionProvider;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 提供验证码图片的Servlet
 */
public class JcaptchaServlet extends HttpServlet {
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

	private ImageCaptchaService captchaService;
	private SessionProvider session;

	/**
     * 初始化方法
	 * @throws ServletException
	 */
	@Override
	public void init() throws ServletException {
		WebApplicationContext appCtx = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		captchaService = (ImageCaptchaService) BeanFactoryUtils
				.beanOfTypeIncludingAncestors(appCtx, ImageCaptchaService.class);
		session = (SessionProvider) BeanFactoryUtils
				.beanOfTypeIncludingAncestors(appCtx, SessionProvider.class);
	}

	/**
     * Get请求的处理
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws ServletException
     * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		byte[] captchaChallengeAsJpeg;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			String captchaId = session.getSessionId(request, response);
			BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, request.getLocale());
			ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
