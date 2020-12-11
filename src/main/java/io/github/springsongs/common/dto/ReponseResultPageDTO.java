package io.github.springsongs.common.dto;

import io.github.springsongs.enumeration.ResultCode;

public class ReponseResultPageDTO<T> {
	private int code;
	private T rows;
	private long total;
	private String message;

	public static ReponseResultPageDTO successed(Object data, long l, ResultCode resultCode) {
		ReponseResultPageDTO reponseResultPageDTO = new ReponseResultPageDTO();
		reponseResultPageDTO.setRows(data);
		reponseResultPageDTO.setCode(resultCode.getCode());
		reponseResultPageDTO.setMessage(resultCode.getMessage());
		reponseResultPageDTO.setTotal(l);
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

	public T getRows() {
		return rows;
	}

	public void setRows(T rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
