package io.github.springsongs.domain.dto;

import io.github.springsongs.enumeration.ResultCode;

public class ResponseDTO<T> {
	private int code;
	private T data;
	private String msg;

	public static ResponseDTO successed(Object data, ResultCode resultCode) {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setData(data);
		responseDTO.setCode(resultCode.getCode());
		responseDTO.setMsg(resultCode.getMessage());
		return responseDTO;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
