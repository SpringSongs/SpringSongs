package io.github.springsongs.enumeration;

import javax.servlet.http.HttpServletResponse;

import io.github.springsongs.util.Constant;

public enum ResultCode {

	SELECT_SUCCESSED(HttpServletResponse.SC_OK, Constant.SELECT_SUCCESSED),
	SAVE_SUCCESSED(HttpServletResponse.SC_OK, Constant.SAVE_SUCCESSED),
	UPDATE_SUCCESSED(HttpServletResponse.SC_OK, Constant.UPDATE_SUCCESSED),
	DELETE_SUCCESSED(HttpServletResponse.SC_OK, Constant.DELETE_SUCCESSED),
	HASED_CHILD_IDS_CANNOT_DELETE(HttpServletResponse.SC_BAD_REQUEST, Constant.HASED_CHILD_IDS_CANNOT_DELETE),
	INFO_NOT_FOUND(HttpServletResponse.SC_BAD_REQUEST, Constant.INFO_NOT_FOUND),
	SYSTEM_ERROR(HttpServletResponse.SC_BAD_REQUEST, Constant.SYSTEM_ERROR),
	FILE_UPLOAD_SECCUESSED(HttpServletResponse.SC_BAD_REQUEST, Constant.FILE_UPLOAD_SECCUESSED),
	INFO_CAN_NOT_EDIT(HttpServletResponse.SC_BAD_REQUEST, Constant.INFO_CAN_NOT_EDIT),
	INFO_CAN_NOT_DELETE(HttpServletResponse.SC_BAD_REQUEST, Constant.INFO_CAN_NOT_DELETE),
	ACCOUNT_HAS_REGISTER(HttpServletResponse.SC_BAD_REQUEST, Constant.ACCOUNT_HAS_REGISTER),
	PASSWORD_CAN_NOT_EMPTY(HttpServletResponse.SC_BAD_REQUEST, Constant.PASSWORD_CAN_NOT_EMPTY),
	PARAMETER_MORE_1000(HttpServletResponse.SC_BAD_REQUEST, Constant.PARAMETER_MORE_1000),
	SESSION_HAS_GONE(HttpServletResponse.SC_FORBIDDEN, Constant.SESSION_HAS_GONE),
	FORBIDDEN(HttpServletResponse.SC_BAD_REQUEST, Constant.AUTH_FORBIDDEN),
	PARAMETER_NOT_NULL_ERROR(HttpServletResponse.SC_BAD_REQUEST, Constant.PARAMETER_NOT_NULL_ERROR),
	USER_NOT_FOUND(HttpServletResponse.SC_BAD_REQUEST, Constant.USER_NOT_FOUND),
	BAD_CREDENTIALSEXCEPTION(HttpServletResponse.SC_BAD_REQUEST, Constant.BAD_CREDENTIALSEXCEPTION),
	LOCKED_EXCEPTION(HttpServletResponse.SC_BAD_REQUEST, Constant.LOCKED_EXCEPTION),
	ACCOUNT_EXPIRE_EXCEPTION(HttpServletResponse.SC_BAD_REQUEST, Constant.ACCOUNT_EXPIRE_EXCEPTION),
	ACCOUNT_DISABLED_EXCEPTION(HttpServletResponse.SC_BAD_REQUEST, Constant.ACCOUNT_DISABLED_EXCEPTION),
	CREDENTIALS_EXCPIRE_EXCEPTION(HttpServletResponse.SC_BAD_REQUEST, Constant.CREDENTIALS_EXCPIRE_EXCEPTION),
	LOGIN_FAIL(HttpServletResponse.SC_BAD_REQUEST, Constant.LOGIN_FAIL),
	LOGIN_SUCCESSED(HttpServletResponse.SC_OK, Constant.LOGIN_SUCCESSED),
	LOGOUT_SUCCESSED(HttpServletResponse.SC_OK, Constant.LOGOUT_SUCCESSED),
	DATA_EXIST(HttpServletResponse.SC_BAD_REQUEST, Constant.DATA_EXIST),
	ADD_TASK_FAIL(HttpServletResponse.SC_BAD_REQUEST, Constant.ADD_TASK_FAIL),
	TASK_HADED_SUBMIT(HttpServletResponse.SC_BAD_REQUEST, Constant.TASK_HADED_SUBMIT),
	UPDATE_TASK_FAIL(HttpServletResponse.SC_BAD_REQUEST, Constant.UPDATE_TASK_FAIL),
	PAUSE_TASK_FAIL(HttpServletResponse.SC_BAD_REQUEST, Constant.PAUSE_TASK_FAIL),
	DELETE_TASK_FAIL(HttpServletResponse.SC_BAD_REQUEST, Constant.DELETE_TASK_FAIL),
	RESUME_TASK_FAIL(HttpServletResponse.SC_BAD_REQUEST, Constant.RESUME_TASK_FAIL),
	MODEL_NOT_EXIST(HttpServletResponse.SC_BAD_REQUEST, Constant.MODEL_NOT_EXIST),
	EXPORT_SUCCESSED(HttpServletResponse.SC_OK, Constant.EXPORT_SUCCESSED),
	TASK_HADED_CONFIG(HttpServletResponse.SC_BAD_REQUEST, Constant.TASK_HADED_CONFIG),;
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
