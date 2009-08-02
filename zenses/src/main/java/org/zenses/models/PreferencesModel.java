package org.zenses.models;

import java.util.prefs.Preferences;

import org.zenses.ZensesApplication;

public class PreferencesModel {
	protected String _lastfmUsername;
	protected String _lastfmPassword;
	protected int _intervalBetweenScrobbles;
	
	protected Preferences _preferences;
	
	public PreferencesModel()
	{
		this._preferences = Preferences.userNodeForPackage(ZensesApplication.class);
	}
	
	public void populate()
	{
		this._lastfmUsername = this._preferences.get("lastfm_username", "");
		this._lastfmPassword = this._preferences.get("lastfm_password", "");
		this._intervalBetweenScrobbles = this._preferences.getInt("interval_between_scrobbles", 0);
	}
	
	public void save()
	{
		// TODO: encrypt preference  data
		this._preferences.put("lastfm_username", this._lastfmUsername);
		this._preferences.put("lastfm_password", this._lastfmPassword);
		this._preferences.putInt("interval_between_scrobbles", this._intervalBetweenScrobbles);
	}
	
	// Properties
	
	public String get_lastfmUsername() {
		return this._lastfmUsername;
	}
	
	public void set_lastfmUsername(String username) {
		this._lastfmUsername = username;
	}

	public String get_lastfmPassword() {
		return this._lastfmPassword;
	}
	
	public void set_lastfmPassword(String password) {
		this._lastfmPassword = password;
	}
	
	public int getIntervalBetweenScrobbles() {
		return this._intervalBetweenScrobbles;
	}
	
	public void setIntervalBetweenScrobbles(int value) {
		this._intervalBetweenScrobbles = value;
	}
}
