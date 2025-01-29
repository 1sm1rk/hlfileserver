package de.homelabs.hlfileserver.entity;

import java.util.ArrayList;
import java.util.List;

public class FSDirectoryResult extends FSResult {

	public List<FSElement> list = new ArrayList<FSElement>();

	private FSDirectoryResult(boolean ok, String error, FSErrorCode errorCode, List<FSElement> valueList) {
		super(ok,error,errorCode);
		this.list = valueList;
	}	
	
	private FSDirectoryResult(boolean ok, String error, FSErrorCode errorCode) {
		super(ok,error,errorCode);
	}
	
	public static FSDirectoryResult ok(List<FSElement> valueList) {
		return new FSDirectoryResult(true, "OK", FSErrorCode.OK, valueList);
	}
	
	public static FSDirectoryResult error(String error, FSErrorCode errorCode) {
		return new FSDirectoryResult(false, error, errorCode);
	}
	
}
