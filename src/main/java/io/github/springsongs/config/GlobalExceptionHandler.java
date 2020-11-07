package io.github.springsongs.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.github.springsongs.common.dto.ResponseDTO;
import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.exception.SpringSongsException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SpringSongsException.class)
	@ResponseBody
	//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleUserNotExistException(SpringSongsException ex) {
		Map<String, Object> result = new HashMap<>();
		result.put("code", ex.getCode());
		result.put("msg", ex.getMessage());
		return result;
	}
	
	/**
	 * 未被关注的异常信息，统一返回给客户端为“系统异常”
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ResponseDTO<String> handler(RuntimeException e) {
		e.printStackTrace();
		return ResponseDTO.successed(null, ResultCode.SYSTEM_ERROR);
	}

	/**
	 * Hibernate Validator参数校验异常处理
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseDTO<String> handler(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		ObjectError objectError = bindingResult.getAllErrors().get(0);
		return ResponseDTO.successed(ResultCode.PARAMETER_NOT_NULL_ERROR.getCode(), objectError.getDefaultMessage());
	}

	/**
	 * Spring Validator参数校验异常处理
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseDTO<String> handler(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			String message = constraintViolation.getMessage();
			if (!StringUtils.isEmpty(message)) {
				return ResponseDTO.successed(ResultCode.PARAMETER_NOT_NULL_ERROR.getCode(), message);

			}
		}
		return ResponseDTO.successed(null, ResultCode.SYSTEM_ERROR);
	}
}
