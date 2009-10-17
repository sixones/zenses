package org.zenses.lastfm;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import net.roarsoftware.lastfm.Authenticator;
import net.roarsoftware.lastfm.Session;
import net.roarsoftware.lastfm.Track;
import net.roarsoftware.lastfm.User;
import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;
import net.roarsoftware.lastfm.scrobble.Source;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.zenses.data.dto.DeviceTrackDto;

public class LastFmTracksSubmitter implements InitializingBean {

	public static String API_KEY = "41840dbe9a09b7d4b6c303afe1b46ce4";
	public static String SECRET_KEY = "52056daef16b8a8a20996275a67bae80";
	public static String CLIENT_ID = "zns";
	public static String CLIENT_VERSION = "2.0";
	
	private Logger logger = Logger.getLogger(LastFmTracksSubmitter.class);

	private Session sessionToken = null;
	private User sessionUser = null;
	private String requestToken = "";
	
	private Scrobbler scrobbler;

	public void submitSingleTrack(DeviceTrackDto dto, long submissionTimestamp) throws IOException {
		Assert.notNull(scrobbler,
				"Not provided lastfm username or password - not connected, no submissions will be made");
		int trackLength = (int) (dto.getLength().doubleValue() / 100);
		ResponseStatus status = scrobbler.submit(dto.getArtist(), dto.getTitle(), dto.getAlbum(), trackLength, -1,
				Source.USER, submissionTimestamp);
		Assert.isTrue(status.ok(), "Submitting track to Last.fm failed: " + status.getMessage() + ", track: " + dto);
	}

	public void afterPropertiesSet() throws Exception {
		this.initializeScrobbler();
	}

	public synchronized void initializeScrobbler() throws IOException {
		if (this.getSession() == null) {
			logger.error("Last.fm session token not provided. Skipping authentication, no submissions will be made");
			return;
		}
		
		scrobbler = Scrobbler.newScrobbler(LastFmTracksSubmitter.CLIENT_ID, LastFmTracksSubmitter.CLIENT_VERSION, this.sessionUser.getName());
		ResponseStatus status = scrobbler.handshake(this.getSession());
		
		Assert.isTrue(status.ok(), "Login to Last.fm failed with status: " + status.getStatus() + " - " + status.getMessage());
	}
	
	public synchronized Date lastScrobbledTime() {
		if (this.sessionToken == null) {
			return null;
		}
		
		Collection<Track> tracks = User.getRecentTracks(this.sessionUser.getName(), 1, LastFmTracksSubmitter.API_KEY);

		for (Track track : tracks) {
			return track.getPlayedWhen();
		}
		
		return null;
	}
	
	public synchronized String getRequestToken() {
		return this.requestToken;
	}
	
	public synchronized Session getSession() {
		return this.sessionToken;
	}
	
	public synchronized User getSessionUser() {
		return this.sessionUser;
	}
	
	public synchronized boolean authenticatesNow(String token) throws IOException {
		if (token != null && token != "" && token.length() > 4) {	
			this.sessionToken = Session.createSession(LastFmTracksSubmitter.API_KEY, LastFmTracksSubmitter.SECRET_KEY, token);
			this.sessionUser = User.getInfo(this.sessionToken);
			
			if (this.sessionUser != null) {
				this.initializeScrobbler();
				return true;
			} else {
				return false;
			}
		} else {
			this.requestToken = Authenticator.getToken(LastFmTracksSubmitter.API_KEY);
			return false;
		}
	}

	public synchronized void authenticate() throws IOException {
		if (this.requestToken != "") {
			this.sessionToken = Authenticator.getSession(this.requestToken, LastFmTracksSubmitter.API_KEY, LastFmTracksSubmitter.SECRET_KEY);
			this.sessionUser = User.getInfo(this.sessionToken);
			
			this.initializeScrobbler();
		}
	}
}
