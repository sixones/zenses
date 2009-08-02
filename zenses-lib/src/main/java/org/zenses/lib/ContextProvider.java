package org.zenses.lib;

import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;
import org.zenses.data.service.DeviceTrackService;
import org.zenses.lastfm.LastFmTracksSubmitter;
import org.zenses.lastfm.TracksSubmitter;
import org.zenses.mtp.MtpDeviceService;

public class ContextProvider {

	private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml", 
			"mtplib-" + System.getProperty("mtplib") + "-context.xml"});

	@SuppressWarnings("unchecked")
	public static MtpDeviceService getDeviceService() {
		return getSingleBeanOfType(MtpDeviceService.class);
	}

	public static TracksSubmitter getTracksSubmitter() {
		return getSingleBeanOfType(TracksSubmitter.class);
	}

	public static DeviceTrackService getDeviceTrackService() {
		return getSingleBeanOfType(DeviceTrackService.class);
	}

	public static LastFmTracksSubmitter getLastFmTracksSubmitter() {
		return getSingleBeanOfType(LastFmTracksSubmitter.class);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getSingleBeanOfType(Class<T> beanClass) {
		Map<String, T> beansOfType = context.getBeansOfType(beanClass);
		Assert.isTrue(beansOfType.size() == 1, "Expected one bean but instead found: " + beansOfType);
		return beansOfType.values().iterator().next();
	}
}
