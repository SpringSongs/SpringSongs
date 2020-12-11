package io.github.springsongs.common.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping("")
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect("/Login");
	}
}
