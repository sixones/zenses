package org.zenses.mtp.jmtp;

import java.math.BigInteger;
import java.util.Date;

import org.zenses.mtp.MtpDeviceTrack;


import jmtp.PortableDeviceAudioObject;

public class MtpDeviceTrackImpl implements MtpDeviceTrack {

	private String artist;
	private String title;
	private Long playCount;
	private String persistentId;
	private String album;
	private Date lastRead;
	private String zenId;
	private String filename;
	private BigInteger length;
	private String deviceId;

	public MtpDeviceTrackImpl(String deviceId, PortableDeviceAudioObject pdao) {
		this.persistentId = pdao.getPersistentUniqueIdentifier();
		this.title = pdao.getTitle();
		this.artist = pdao.getArtist();
		this.album = pdao.getAlbum();
		this.playCount = pdao.getUseCount();
		this.filename = pdao.getOriginalFileName();
		this.zenId = pdao.getID();
		this.lastRead = new Date();
		this.length = pdao.getDuraction();
		this.deviceId = deviceId;
	}

	@Override
	public String toString() {
		return "DeviceTrack [album=" + album + ", artist=" + artist
				+ ", filename=" + filename + ", lastRead=" + lastRead
				+ ", length=" + length + ", persistentId=" + persistentId
				+ ", play_count=" + playCount + ", title=" + title
				+ ", zenId=" + zenId + "]";
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}

	public String getPersistentId() {
		return persistentId;
	}

	public void setPersistentId(String persistentId) {
		this.persistentId = persistentId;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Date getLastRead() {
		return lastRead;
	}

	public void setLastRead(Date lastRead) {
		this.lastRead = lastRead;
	}

	public String getZenId() {
		return zenId;
	}

	public void setZenId(String zenId) {
		this.zenId = zenId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public BigInteger getLength() {
		return length;
	}

	public void setLength(BigInteger length) {
		this.length = length;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}
}
