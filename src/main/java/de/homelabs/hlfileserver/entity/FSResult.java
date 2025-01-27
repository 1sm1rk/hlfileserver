package de.homelabs.hlfileserver.entity;

public class FSResult {
	public boolean ok;
	public String error;
	public FSErrorCode errorCode;
	
	private FSResult(boolean ok, String error, FSErrorCode errorCode) {
		this.ok = ok;
		this.error = error;
		this.errorCode = errorCode;
	}
	
	public static FSResult ok() {
		return new FSResult(true, "OK", FSErrorCode.OK);
	}
	
	public static FSResult error(String error, FSErrorCode errorCode) {
		return new FSResult(false, error, errorCode);
	}
}
