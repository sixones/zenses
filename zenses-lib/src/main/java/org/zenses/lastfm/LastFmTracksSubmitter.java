package org.zenses.lastfm;

import java.io.IOException;

import net.roarsoftware.lastfm.scrobble.ResponseStatus;
import net.roarsoftware.lastfm.scrobble.Scrobbler;
import net.roarsoftware.lastfm.scrobble.Source;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.zenses.data.dto.DeviceTrackDto;

public class LastFmTracksSubmitter implements InitializingBean {

	private Logger logger = Logger.getLogger(LastFmTracksSubmitter.class);

	private String lastfmUser;

	private String lastfmPassword;

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
		initializeScrobbler(lastfmUser, lastfmPassword);
	}

	public synchronized void initializeScrobbler(String user, String pass) throws IOException {
		if (user == null || pass == null || user == "" || pass == "") {
			logger.error("Last.fm username or password not provided. Skipping authentication, no submissions will be made");
			return;
		}
		
		System.out.println("user -> " + user + " pass -> " + pass);
		
		scrobbler = Scrobbler.newScrobbler("zns", "3.0", user);
		ResponseStatus status = scrobbler.handshake(pass);
		Assert.isTrue(status.ok(), "Login to Last.fm failed: " + status.getMessage());
	}

	public synchronized void setLastfmUser(String lastfmUser) {
		this.lastfmUser = lastfmUser;
	}

	public synchronized void setLastfmPassword(String lastfmPassword) {
		this.lastfmPassword = lastfmPassword;
	}

}
