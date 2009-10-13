package org.zenses.mtp.linux;

import java.math.BigInteger;

import org.zenses.mtp.MtpDeviceTrack;

public class MtpDeviceTrackImpl implements MtpDeviceTrack {

	private String artist;
	private String track;
	private String deviceId;
	private String album;

	public MtpDeviceTrackImpl(String artist, String track, String album, String deviceId) {
		this.artist = artist;
		this.track = track;
		this.album = album;
		this.deviceId = deviceId;
	}

	@Override
	public String getAlbum() {
		return album;
	}

	@Override
	public String getArtist() {
		return artist;
	}

	@Override
	public String getDeviceId() {
		return deviceId;
	}

	@Override
	public String getFilename() {
		return "filename";
	}

	@Override
	public BigInteger getLength() {
		return new BigInteger("100");
	}

	@Override
	public String getPersistentId() {
		return "abcd";
	}

	@Override
	public Long getPlayCount() {
		return 2L;
	}

	@Override
	public String getTitle() {
		return track;
	}

	@Override
	public String getZenId() {
		return "abcd";
	}

}
