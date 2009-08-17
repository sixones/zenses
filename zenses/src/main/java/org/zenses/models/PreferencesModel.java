package org.zenses.models;

import java.util.prefs.Preferences;

import org.zenses.ZensesApplication;

public class PreferencesModel {
	private String _sessionToken;
	private int _intervalBetweenScrobbles;
	private String _dateFormat;
	
	protected Preferences _preferences;
	
	public PreferencesModel()
	{
		this._preferences = Preferences.userNodeForPackage(ZensesApplication.class);
	}
	
	public void populate()
	{
		this._sessionToken = this._preferences.get("session_token", "");
	
		this._intervalBetweenScrobbles = this._preferences.getInt("interval_between_scrobbles", 0);
		this._dateFormat = this._preferences.get("date_format", "dd/MM/yyyy");
	}
	
	public void save()
	{
		// TODO: encrypt preference  data
		this._preferences.put("session_token", this._sessionToken);

		this._preferences.putInt("interval_between_scrobbles", this._intervalBetweenScrobbles);
		this._preferences.put("date_format", this._dateFormat);
	}
	
	// Properties
	public String getSessionToken() {
		return this._sessionToken;
	}
	
	public void setSessionToken(String value) {
		this._sessionToken = value;
	}
	
	public int getIntervalBetweenScrobbles() {
		return this._intervalBetweenScrobbles;
	}
	
	public void setIntervalBetweenScrobbles(int value) {
		this._intervalBetweenScrobbles = value;
	}
	
	public String getDateFormat() {
		return this._dateFormat;
	}
	
	public void setDateFormat(String value) {
		this._dateFormat = value;
	}
}
