package io.github.springsongs.util;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", 0);
		put("msg", "success");
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R error(String msg) {
		R r = new R();
		r.put("code", 400);
		r.put("msg", msg);
		return r;
	}

	public static R succeed(String msg) {
		R r = new R();
		r.put("code", 200);
		r.put("msg", msg);
		return r;
	}

	public static R timeout(String msg) {
		R r = new R();
		r.put("code", 500);
		r.put("msg", msg);
		return r;
	}

	public static R succeed(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R succeed() {
		R r = new R();
		r.put("code", 200);
		r.put("msg", "success");
		return r;
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
