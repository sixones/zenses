package org.zenses.data.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.zenses.mtp.MtpDeviceTrack;

@Entity
@Table(name = "DEVICE_TRACKS", uniqueConstraints=@UniqueConstraint(columnNames={"persistent_id","device_id"}))
@NamedQueries( { @NamedQuery(name = "DeviceTrackDto.findByZenId", query = "from DeviceTrackDto where zenId = :zenId") })
public class DeviceTrackDto implements MtpDeviceTrack {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "artist", length = 255, nullable = false)
	private String artist;

	@Column(name = "title", length = 255, nullable = false)
	private String title;

	@Column(name = "length", nullable = false)
	private BigInteger length;

	@Column(name = "play_count", nullable = false)
	private Long playCount;

	@Column(name = "persistent_id", length = 45, nullable = false)
	private String persistentId;

	@Column(name = "album", length = 255, nullable = false)
	private String album;

	@Column(name = "last_read", nullable = false)
	private Date lastRead;

	@Column(name = "zen_id", length = 15, nullable = false)
	private String zenId;

	@Column(name = "filename", length = 255, nullable = false)
	private String filename;

	@Column(name = "device_id", length = 45, nullable = false)
	private String deviceId;

	@OneToMany(mappedBy = "track", fetch = FetchType.EAGER)
	private List<LastFmSubmissionDto> submissions = new ArrayList<LastFmSubmissionDto>();

	public DeviceTrackDto(String zenId, String persistentId, String deviceId, String artist, String title,
			String album, BigInteger length, Long playCount, String filename) {
		this.artist = artist;
		this.title = title;
		this.length = length;
		this.playCount = playCount;
		this.persistentId = persistentId;
		this.deviceId = deviceId;
		this.album = album;
		this.lastRead = new Date();
		this.zenId = zenId;
		this.filename = filename;
	}

	public DeviceTrackDto() {
	}

	public Integer getId() {
		return id;
	}

	public String getArtist() {
		return artist;
	}

	public String getTitle() {
		return title;
	}

	public BigInteger getLength() {
		return length;
	}

	public Long getPlayCount() {
		return playCount;
	}

	public String getPersistentId() {
		return persistentId;
	}

	public String getAlbum() {
		return album;
	}

	public Date getLastRead() {
		return lastRead;
	}

	public String getZenId() {
		return zenId;
	}

	public String getFilename() {
		return filename;
	}

	public List<LastFmSubmissionDto> getSubmissions() {
		return submissions;
	}

	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}

	public void setLastRead(Date lastRead) {
		this.lastRead = lastRead;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public long getNumberOfPlaysToSubmit() {
		return this.playCount - this.submissions.size();
	}

	@Override
	public String toString() {
		return "DeviceTrackDto [album=" + album + ", artist=" + artist + ", deviceId=" + deviceId + ", filename="
				+ filename + ", id=" + id + ", lastRead=" + lastRead + ", length=" + length + ", persistentId="
				+ persistentId + ", playCount=" + playCount + ", submissions=" + submissions + ", title=" + title
				+ ", zenId=" + zenId + "]";
	}
}
