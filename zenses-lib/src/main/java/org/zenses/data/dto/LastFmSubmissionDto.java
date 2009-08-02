package org.zenses.data.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LASTFM_SUBMISSIONS")
public class LastFmSubmissionDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "track_id", nullable = false, referencedColumnName = "id")
	private DeviceTrackDto track;

	@Column(name = "date_submitted", length = 255, nullable = false)
	private Date dateSubmitted;

	public LastFmSubmissionDto(DeviceTrackDto track, Date dateSubmitted) {
		this.track = track;
		this.dateSubmitted = dateSubmitted;
	}

	public LastFmSubmissionDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DeviceTrackDto getTrack() {
		return track;
	}

	public void setTrack(DeviceTrackDto track) {
		this.track = track;
	}

	public Date getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}
}
