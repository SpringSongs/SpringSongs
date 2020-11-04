package io.github.springsongs.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {
	public static boolean isAjaxRequest(HttpServletRequest request) {
		if (request.getHeader("accept").indexOf("application/json") > -1
				|| request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		} else {
			return false;
		}
	}
}
