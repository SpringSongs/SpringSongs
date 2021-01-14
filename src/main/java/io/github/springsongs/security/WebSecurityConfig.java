package io.github.springsongs.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.alibaba.fastjson.JSON;

import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.interceptor.FileHeaderCheckInterceptor;
import io.github.springsongs.modules.sys.dto.SpringLoginLogDTO;
import io.github.springsongs.modules.sys.dto.UserLogonDTO;
import io.github.springsongs.modules.sys.service.ISpringLoginLogService;
import io.github.springsongs.util.Constant;
import io.github.springsongs.util.HttpUtils;
import io.github.springsongs.util.IpKit;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	// @Autowired
	// private AnthencationProder provider;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ISpringLoginLogService baseLoginLogService;

	@Autowired
	private UrlAccessDecisionManager urlAccessDecisionManager;

	@Autowired
	private UrlFilterInvocationSecurityMetadaSource urlFilterInvocationSecurityMetadaSource;

	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FileHeaderCheckInterceptor()) 
                .addPathPatterns("/**"); 
    }
	/**
	 * 忽略静态文件
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/SpringUser/Invalidate", "/v2/api-docs", "/swagger-resources/**", "/css/**",
				"/img/**", "/js/**", "/**.ico","/**.jpeg","/**.jpg","/**.png","/**.gif","/webjars/**", "/", "/jquery-easyui/**", "/error", "/bootstrap/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
			@Override
			public <O extends FilterSecurityInterceptor> O postProcess(O o) {
				// TODO 在这里处理权限
				o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadaSource);
				o.setAccessDecisionManager(urlAccessDecisionManager);
				return o;
			}
		}).anyRequest().authenticated().and().formLogin().loginPage("/Login").loginProcessingUrl("/Login")
				.defaultSuccessUrl("/Admin", true).failureUrl("/Login?error").permitAll()
				.failureHandler(loginFailureHandler()).successHandler(loginSuccessHandler()).and().logout()
				.logoutUrl("/Logout").logoutSuccessUrl("/Login").permitAll()
				.logoutSuccessHandler(logoutSuccessHandler()).deleteCookies("JSESSIONID", "SESSION").and()
				.sessionManagement().invalidSessionUrl("/SpringUser/Invalidate").sessionFixation().changeSessionId()
				.maximumSessions(1).maxSessionsPreventsLogin(false)// false之后登录踢掉之前登录,true则不允许之后登录
				.expiredSessionStrategy(sessionInformationExpiredStrategy());// 登录被踢掉时的自定义操作;
		http.headers().frameOptions().disable();
		http.csrf().disable().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		http.cors();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() { // 登出处理
		return new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
					Authentication authentication) throws IOException, ServletException {

				try {
					MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();

					// 记录登录信息
					SpringLoginLogDTO entity = new SpringLoginLogDTO();
					entity.setDescription("注销");
					entity.setCreatedUserId(userDetails.getBaseEntityUser().getId());
					entity.setCreatedBy(userDetails.getBaseEntityUser().getUserName());
					entity.setCreatedIp(IpKit.getRealIp(httpServletRequest));
					entity.setCreatedOn(new Date());
					baseLoginLogService.insert(entity);

				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				long endTime = System.currentTimeMillis();
				if (HttpUtils.isAjaxRequest(httpServletRequest)) {
					httpServletResponse.setContentType("application/json;charset=utf-8");
					PrintWriter out = null;
					try {
						out = httpServletResponse.getWriter();
						out.append(JSON.toJSONString(ResponseDTO.successed(null, ResultCode.LOGOUT_SUCCESSED)));
					} catch (IOException e) {
						logger.error(e.getMessage());
					} finally {
						if (out != null) {
							out.flush();
							out.close();
						}
					}
				}

				if (!HttpUtils.isAjaxRequest(httpServletRequest)) {
					httpServletResponse.sendRedirect("/login");
				}
			}
		};
	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { // 登入处理
		return new SavedRequestAwareAuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();

				// 记录登录信息
				SpringLoginLogDTO entity = new SpringLoginLogDTO();
				entity.setDescription("登录");
				entity.setCreatedUserId(userDetails.getBaseEntityUser().getId());
				entity.setCreatedBy(userDetails.getBaseEntityUser().getUserName());
				entity.setCreatedIp(IpKit.getRealIp(request));
				entity.setCreatedOn(new Date());
				baseLoginLogService.insert(entity);

				if (HttpUtils.isAjaxRequest(request)) {
					response.setContentType("application/json;charset=utf-8");
					PrintWriter out = null;
					try {
						out = response.getWriter();
						UserLogonDTO userLogonDto = new UserLogonDTO();
						userLogonDto.setId(userDetails.getBaseEntityUser().getId());
						userLogonDto.setUserName(userDetails.getBaseEntityUser().getUserName());
						out.append(JSON.toJSONString(ResponseDTO.successed(userLogonDto, ResultCode.LOGIN_SUCCESSED)));
					} catch (IOException e) {
						logger.error(e.getMessage());
					} finally {
						if (out != null) {
							out.flush();
							out.close();
						}
					}
				}

				// response.sendRedirect("/Admin");
				super.onAuthenticationSuccess(request, response, authentication);

			}
		};
	}

	@Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				if (HttpUtils.isAjaxRequest(request)) {
					response.setContentType("application/json;charset=utf-8");
					PrintWriter out = response.getWriter();

					ResponseDTO<String> responseDto = null;
					if (exception instanceof UsernameNotFoundException) {
						logger.error(exception.getMessage());
						responseDto = ResponseDTO.successed(null, ResultCode.USER_NOT_FOUND);
					} else if (exception instanceof BadCredentialsException) {
						logger.error(exception.getMessage());
						responseDto = ResponseDTO.successed(null, ResultCode.BAD_CREDENTIALSEXCEPTION);
					} else if (exception instanceof LockedException) {
						logger.error(exception.getMessage());
						responseDto = ResponseDTO.successed(null, ResultCode.LOCKED_EXCEPTION);
					} else if (exception instanceof AccountExpiredException) {
						logger.error(exception.getMessage());
						responseDto = ResponseDTO.successed(null, ResultCode.ACCOUNT_EXPIRE_EXCEPTION);
					} else if (exception instanceof DisabledException) {
						logger.error(exception.getMessage());
						responseDto = ResponseDTO.successed(null, ResultCode.ACCOUNT_DISABLED_EXCEPTION);
					} else if (exception instanceof CredentialsExpiredException) {
						logger.error(exception.getMessage());
						responseDto = ResponseDTO.successed(null, ResultCode.CREDENTIALS_EXCPIRE_EXCEPTION);
					} else {
						logger.error(Constant.LOGIN_FAIL);
						responseDto = ResponseDTO.successed(null, ResultCode.LOGIN_FAIL);
					}
					out.write(JSON.toJSONString(responseDto));
					out.flush();
					out.close();
				} else {
					if (exception instanceof UsernameNotFoundException) {
						logger.error(exception.getMessage());
						throw new UsernameNotFoundException(ResultCode.USER_NOT_FOUND.getMessage());
					} else if (exception instanceof BadCredentialsException) {
						logger.error(exception.getMessage());
						throw new UsernameNotFoundException(ResultCode.BAD_CREDENTIALSEXCEPTION.getMessage());
					} else if (exception instanceof LockedException) {
						logger.error(exception.getMessage());
						throw new UsernameNotFoundException(ResultCode.LOCKED_EXCEPTION.getMessage());
					} else if (exception instanceof AccountExpiredException) {
						logger.error(exception.getMessage());
						throw new UsernameNotFoundException(ResultCode.ACCOUNT_EXPIRE_EXCEPTION.getMessage());
					} else if (exception instanceof DisabledException) {
						logger.error(exception.getMessage());
						throw new UsernameNotFoundException(ResultCode.ACCOUNT_DISABLED_EXCEPTION.getMessage());
					} else if (exception instanceof CredentialsExpiredException) {
						logger.error(exception.getMessage());
						throw new UsernameNotFoundException(ResultCode.CREDENTIALS_EXCPIRE_EXCEPTION.getMessage());
					} else {
						logger.error(Constant.LOGIN_FAIL);
						throw new UsernameNotFoundException(ResultCode.LOGIN_FAIL.getMessage());
					}
				}
			}
		};
	}

	@Bean
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
		return new SessionInformationExpiredStrategy() {
			@Override
			public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
				event.getResponse().setContentType("application/json;charset=UTF-8");
				event.getResponse().getWriter().write("并发登录!");
			}
		};
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				response.setCharacterEncoding("UTF-8");
				if (HttpUtils.isAjaxRequest(request)) {
					response.setContentType("application/json;charset=utf-8");
					PrintWriter pw = response.getWriter();
					pw.write(JSON.toJSONString(ResponseDTO.successed(null, ResultCode.FORBIDDEN)));
					pw.flush();
					pw.close();
				} else {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
				}
			}
		};
	}

	// String idForEncode = "bcrypt";
	// Map encoders = new HashMap<>();
	// encoders.put(idForEncode, new BCryptPasswordEncoder());
	// encoders.put("noop", NoOpPasswordEncoder.getInstance());
	// encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
	// encoders.put("scrypt", new SCryptPasswordEncoder());
	// encoders.put("sha256", new StandardPasswordEncoder());
	//
	// PasswordEncoder passwordEncoder =
	// new DelegatingPasswordEncoder(idForEncode, encoders);
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		// auth.authenticationProvider(provider);
		// logger.info(passwordEncoder().encode("qweasd"));
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
