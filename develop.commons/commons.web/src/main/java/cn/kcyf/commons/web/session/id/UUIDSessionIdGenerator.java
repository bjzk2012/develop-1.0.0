package cn.kcyf.commons.web.session.id;


import java.util.UUID;

public class UUIDSessionIdGenerator implements SessionIdGenerator {
	public String get() {
		return UUID.randomUUID().toString().replace( "-", "");
	}

	public static void main(String[] args) {
		UUID.randomUUID();
		long time = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			UUID.randomUUID();
		}
		time = System.currentTimeMillis() - time;
		System.out.println(time);
	}
}
