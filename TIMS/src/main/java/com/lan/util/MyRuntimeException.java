package com.lan.util;

/**
 * 细化运行时异常,自己定义一个业务异常
 *
 * @author lanjian
 * */
public class MyRuntimeException extends RuntimeException implements LJException{
	private  int exceptionCode;
	private  String message;
	public MyRuntimeException() {
		super();
	}
	public MyRuntimeException(int excepitonCode, String message) {
		super();
		this.exceptionCode=excepitonCode;
		this.message=message;
	}
	public MyRuntimeException(int exceptionCode) {
		super();
		this.exceptionCode=exceptionCode;
	}
	public MyRuntimeException(String message) {
		super(message);
	}

	@Override
	public int getCode() {
		return this.exceptionCode;
	}

	@Override
	public String getDescription() {
		return this.message;
	}
}
