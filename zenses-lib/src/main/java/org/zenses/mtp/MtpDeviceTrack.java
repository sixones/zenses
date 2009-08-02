package org.zenses.mtp;

import java.math.BigInteger;

public interface MtpDeviceTrack {

	String getArtist();

	String getTitle();

	Long getPlayCount();

	String getPersistentId();

	String getAlbum();

	String getZenId();

	String getFilename();

	BigInteger getLength();

	String getDeviceId();

}