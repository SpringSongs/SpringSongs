package io.github.springsongs.common.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("Login")
	public String login(ModelMap map, HttpServletRequest request,HttpServletResponse response) {
		response.addHeader("x-frame-options","SAMEORIGIN");
		if (null != request.getParameter("sucess")) {
			try {
				map.addAttribute("sucess", URLDecoder.decode(request.getParameter("sucess").trim(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("SecurityController::login " + e.getMessage());
			}
		}
		if (null != request.getParameter("error")) {
			try {
				map.addAttribute("error", URLDecoder.decode(request.getParameter("error").trim(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("SecurityController::login " + e.getMessage());
			}
		}
		return "login";
	}
}
