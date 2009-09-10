package org.zenses.mtp;

public class MTPException extends Exception {
	private static final long serialVersionUID = 3427953397646735420L;

	public MTPException() {
		super();
	}
	
	public MTPException(String error) {
		super(error);
	}
}
