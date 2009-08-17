package org.zenses.utils;

public class CustomThread<T> extends Thread {
	private T _data;
	
	public CustomThread(T data) {
		this._data = data;
	}
	
	public T getData() {
		return this._data;
	}
}
