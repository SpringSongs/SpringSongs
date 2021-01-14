package io.github.springsongs.common.dto;

import io.github.springsongs.enumeration.ResultCode;

public class ReponseResultPageDTO<T> {
	private int code;
	private T data;
	private long count;
	private String message;

	public static ReponseResultPageDTO successed(Object data, long l, ResultCode resultCode) {
		ReponseResultPageDTO reponseResultPageDTO = new ReponseResultPageDTO();
		reponseResultPageDTO.setData(data);
		reponseResultPageDTO.setCode(resultCode.getCode());
		reponseResultPageDTO.setMessage(resultCode.getMessage());
		reponseResultPageDTO.setCount(l);
		return reponseResultPageDTO;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	

}
