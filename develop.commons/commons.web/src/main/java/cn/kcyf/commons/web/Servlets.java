package cn.kcyf.commons.web;


import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Servlet工具类
 * 
 * @author bsq
 * 
 */
public class Servlets {


	private static final String NUKNOWN = "unknown";
	private static final String[] ADDR_HEADER = { "X-Forwarded-For",
			"Proxy-Client-IP", "WL-Proxy-Client-IP", "X-Real-IP" };

	/**
	 * 获得真实IP地址。在使用了反向代理时，直接用HttpServletRequest.getRemoteAddr()无法获取客户真实的IP地址。
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(ServletRequest request) {
		String addr = null;
		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsr = (HttpServletRequest) request;
			for (String header : ADDR_HEADER) {
				if (StringUtils.isEmpty(addr) || NUKNOWN.equalsIgnoreCase(addr)) {
					addr = hsr.getHeader(header);
				} else {
					break;
				}
			}
		}
		if (StringUtils.isEmpty(addr) || NUKNOWN.equalsIgnoreCase(addr)) {
			addr = request.getRemoteAddr();
		} else {
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按','分割
			int i = addr.indexOf(",");
			if (i > 0) {
				addr = addr.substring(0, i);
			}
		}
		return addr;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param filename
	 *            下载后的文件名.
	 */
	public static void setDownloadHeader(HttpServletResponse response,
                                         String filename) {
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + filename + "\"");
	}

	public static Map<String, String[]> parseQueryString(String queryString) {
		if (StringUtils.isEmpty(queryString)) {
			return Collections.emptyMap();
		}
		Map<String, String[]> queryMap = new TreeMap<String, String[]>();
		String[] params = StringUtils.split(queryString, "");
		for (String param : params) {
			int index = param.indexOf('=');
			if (index != -1) {
				String name = param.substring(0, index);
				// name为空值不保存
				if (StringUtils.isEmpty(name)) {
					continue;
				}
				String value = param.substring(index + 1);
				try {
					value = URLDecoder.decode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
				}
				if (queryMap.containsKey(name)) {
					String[] values = queryMap.get(name);
					queryMap.put(name, (String[]) ArrayUtils.addAll(values, new String[] { value }));
				} else {
					queryMap.put(name, new String[] { value });
				}
			}
		}
		return queryMap;
	}

	public static String getParameter(HttpServletRequest request,
                                      Map<String, String[]> queryMap, String name) {
		String[] values = getParameterValues(request, queryMap, name);
		return ArrayUtils.isNotEmpty(values) ? StringUtils.join(values, ',')
				: null;
	}

	public static String getParameter(HttpServletRequest request, String name) {
		String[] values = getParameterValues(request, name);
		return ArrayUtils.isNotEmpty(values) ? StringUtils.join(values, ',')
				: null;
	}

	public static String[] getParameterValues(HttpServletRequest request,
                                              Map<String, String[]> queryMap, String name) {
		Validate.notNull(request, "Request must not be null");
		String[] values = queryMap.get(name);
		if (values == null) {
			values = request.getParameterValues(name);
		}
		return values;
	}

	public static String[] getParameterValues(HttpServletRequest request,
                                              String name) {
		Validate.notNull(request, "Request must not be null");
		String qs = request.getQueryString();
		Map<String, String[]> queryMap = parseQueryString(qs);
		return getParameterValues(request, queryMap, name);
	}

	public static Map<String, String> getParameterMap(
            HttpServletRequest request, String prefix) {
		return getParameterMap(request, prefix, false);
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> getParameterMap(
            HttpServletRequest request, String prefix, boolean keyWithPrefix) {
		Validate.notNull(request, "Request must not be null");
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (prefix == null) {
			prefix = "";
		}
		String qs = request.getQueryString();
		Map<String, String[]> queryMap = parseQueryString(qs);
		int len = prefix.length();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String name = keyWithPrefix ? paramName : paramName
						.substring(len);
				String value = getParameter(request, queryMap, paramName);
				params.put(name, value);
			}
		}
		return params;
	}

	public static Map<String, String[]> getParameterValuesMap(
            HttpServletRequest request, String prefix) {
		return getParameterValuesMap(request, prefix, false);
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String[]> getParameterValuesMap(
            HttpServletRequest request, String prefix, boolean keyWithPrefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, String[]> params = new LinkedHashMap<String, String[]>();
		if (prefix == null) {
			prefix = "";
		}
		String qs = request.getQueryString();
		Map<String, String[]> queryMap = parseQueryString(qs);
		int len = prefix.length();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String name = keyWithPrefix ? paramName : paramName
						.substring(len);
				String[] values = getParameterValues(request, queryMap,
						paramName);
				if (values != null && values.length > 0) {
					params.put(name, values);
				}
			}
		}
		return params;
	}

	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}

	/**
	 * 输出html。并禁止客户端缓存。输出json也可以用这个方法。
	 * 
	 * contentType:text/html;charset=utf-8。
	 * 
	 * @param response
	 * @param s
	 */
	public static void writeHtml(HttpServletResponse response, String s) {
		response.setContentType("text/html;charset=utf-8");
		setNoCacheHeader(response);
		try {
			response.getWriter().write(s);
		} catch (IOException ex) {
           ex.printStackTrace();
		}
	}

	public static String getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must not be null");
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static boolean validateUrl(String url, Set<String> validDomains) {
		if (StringUtils.isEmpty(url)) {
			return true;
		}
		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(url);
		UriComponents uc = ucb.build();
		String host = uc.getHost();
		if (StringUtils.isEmpty(host) || validDomains.contains(host)) {
			return true;
		} else {
			return false;
		}
	}
}
