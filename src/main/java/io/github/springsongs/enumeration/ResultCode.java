package io.github.springsongs.enumeration;

import javax.servlet.http.HttpServletResponse;

import io.github.springsongs.util.Constant;

public enum ResultCode {

	SELECT_SUCCESSED(HttpServletResponse.SC_OK, Constant.SELECT_SUCCESSED),
	SAVE_SUCCESSED(HttpServletResponse.SC_OK, Constant.SAVE_SUCCESSED),
	UPDATE_SUCCESSED(HttpServletResponse.SC_OK, Constant.UPDATE_SUCCESSED),
	DELETE_SUCCESSED(HttpServletResponse.SC_OK, Constant.DELETE_SUCCESSED),
	HASED_CHILD_IDS_CANNOT_DELETE(HttpServletResponse.SC_OK, Constant.HASED_CHILD_IDS_CANNOT_DELETE),
	INFO_NOT_FOUND(HttpServletResponse.SC_OK, Constant.INFO_NOT_FOUND),
	SYSTEM_ERROR(HttpServletResponse.SC_BAD_REQUEST, Constant.SYSTEM_ERROR),
	FILE_UPLOAD_SECCUESSED(HttpServletResponse.SC_BAD_REQUEST, Constant.FILE_UPLOAD_SECCUESSED),
	INFO_CAN_NOT_EDIT(HttpServletResponse.SC_BAD_REQUEST, Constant.INFO_CAN_NOT_EDIT),
	INFO_CAN_NOT_DELETE(HttpServletResponse.SC_BAD_REQUEST, Constant.INFO_CAN_NOT_DELETE),
	ACCOUNT_HAS_REGISTER(HttpServletResponse.SC_BAD_REQUEST, Constant.ACCOUNT_HAS_REGISTER),
	PASSWORD_CAN_NOT_EMPTY(HttpServletResponse.SC_BAD_REQUEST, Constant.PASSWORD_CAN_NOT_EMPTY),
	PARAMETER_MORE_1000(HttpServletResponse.SC_BAD_REQUEST, Constant.PARAMETER_MORE_1000),
	PARAMETER_NOT_NULL_ERROR(HttpServletResponse.SC_BAD_REQUEST, Constant.PARAMETER_NOT_NULL_ERROR);
	

	final int code;
	final String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
