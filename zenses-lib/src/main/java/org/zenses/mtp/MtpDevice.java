package org.zenses.mtp;

public interface MtpDevice<T> {

	T getDelegate();
	
	String getName();
}
