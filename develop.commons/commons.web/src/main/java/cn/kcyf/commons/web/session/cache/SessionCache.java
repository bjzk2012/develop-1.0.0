package cn.kcyf.commons.web.session.cache;

import java.io.Serializable;
import java.util.Map;

public interface SessionCache {
	Serializable getAttribute(String root, String name);

	void setAttribute(String root, String name, Serializable value, int exp);

	void clear(String root);

	boolean exist(String root);

	Map<String, Serializable> getSession(String root);

	void setSession(String root, Map<String, Serializable> session, int exp);
}
