package org.zenses.data.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.zenses.data.dto.DeviceTrackDto;
import org.zenses.data.dto.LastFmSubmissionDto;

public class DeviceTrackDaoImpl extends HibernateDaoSupport implements DeviceTrackDao {

	public void save(LastFmSubmissionDto submission) {
		getHibernateTemplate().saveOrUpdate(submission);
	}

	@SuppressWarnings("unchecked")
	public DeviceTrackDto findByPersistentId(String persistentId, String deviceId) {
		List<DeviceTrackDto> tracks = getHibernateTemplate().findByNamedParam(
				"from DeviceTrackDto where persistentId = :persistentId and deviceId = :deviceId",
				new String[] { "persistentId", "deviceId" }, new Object[] { persistentId, deviceId });
		Assert.isTrue(tracks.size() <= 1, "Unexpected number of tracks for given id: " + tracks);
		if (tracks.size() == 1) {
			return tracks.get(0);
		} else {
			return null;
		}
	}

	public void saveOrUpdate(DeviceTrackDto deviceTrackDto) {
		getHibernateTemplate().saveOrUpdate(deviceTrackDto);
	}

	// Needed for Spring setup
	@Resource
	public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
		setSessionFactory(hibernateSessionFactory);
	}

	@SuppressWarnings("unchecked")
	public List<DeviceTrackDto> getUnsubmittedTracks() {
		List<DeviceTrackDto> tracks = getHibernateTemplate().find(
				"from DeviceTrackDto where playCount > submissions.size ORDER BY (playCount - submissions.size) DESC");
		return tracks;
	}

	@SuppressWarnings("unchecked")
	public List<LastFmSubmissionDto> getScrobbledTracks() {
		List<LastFmSubmissionDto> tracks = getHibernateTemplate().find(
			"from LastFmSubmissionDto s ORDER BY s.dateSubmitted DESC");
		return tracks;
	}
	
	public int getScrobbledTracksCount() {
		return this.getScrobbledTracks().size();
	}
	
	@SuppressWarnings("unchecked")
	public Date getMostRecentSubmission() {
		List<Date> find = getHibernateTemplate().find("select max(s.dateSubmitted) from LastFmSubmissionDto s");
		return find.get(0);
	}
}