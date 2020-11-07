package io.github.springsongs.exception;

import io.github.springsongs.enumeration.ResultCode;

public class SpringSongsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;

	public SpringSongsException(ResultCode resultCode) {
		super(resultCode.getMessage());
		this.code = resultCode.getCode();
	}

	public SpringSongsException(ResultCode resultCode, String message) {
		super(message);
		this.code = resultCode.getCode();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
}
