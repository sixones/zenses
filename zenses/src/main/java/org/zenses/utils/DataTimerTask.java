package org.zenses.utils;

import java.util.TimerTask;

public abstract class DataTimerTask<T> extends TimerTask {
	private T _data;
	
	public DataTimerTask(T data) {
		this._data = data;
	}
	
	public T getData() {
		return this._data;
	}
}
