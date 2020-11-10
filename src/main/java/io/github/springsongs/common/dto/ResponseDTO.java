package io.github.springsongs.common.dto;

import io.github.springsongs.enumeration.ResultCode;
import io.github.springsongs.util.Constant;

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

	public static ResponseDTO successed(Object data, int code, String message) {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setData(data);
		responseDTO.setCode(code);
		responseDTO.setMsg(message);
		return responseDTO;
	}

	public static ResponseDTO successed(int code, String message) {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setData(null);
		responseDTO.setCode(code);
		responseDTO.setMsg(message);
		return responseDTO;
	}

	public static ResponseDTO successed(String data) {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setData(data);
		responseDTO.setCode(200);
		responseDTO.setMsg(Constant.SAVE_SUCCESSED);
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
