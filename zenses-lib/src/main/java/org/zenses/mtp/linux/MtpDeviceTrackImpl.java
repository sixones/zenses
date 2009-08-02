package org.zenses.mtp.linux;

import java.math.BigInteger;

import org.zenses.mtp.MtpDeviceTrack;

public class MtpDeviceTrackImpl implements MtpDeviceTrack {

	private String artist;
	private String track;
	private String deviceId;

	public MtpDeviceTrackImpl(String artist, String track, String deviceId) {
		this.artist = artist;
		this.track = track;
		this.deviceId = deviceId;
	}

	@Override
	public String getAlbum() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPersistentId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getPlayCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		return track;
	}

	@Override
	public String getZenId() {
		// TODO Auto-generated method stub
		return null;
	}

}
