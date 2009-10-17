package org.zenses;

import java.io.File;
import java.util.List;

import jmtp.PortableDevice;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.zenses.data.service.DeviceTrackService;
import org.zenses.lib.ContextProvider;
import org.zenses.mtp.MTPException;
import org.zenses.mtp.MtpDevice;
import org.zenses.mtp.MtpDeviceService;
import org.zenses.mtp.MtpDeviceTrack;

public class Debugger {

	static {
		System.setProperty("mtplib", "jmtp");
		System.setProperty("dbtype", "hsql");
	}

	private final static Logger logger = Logger.getLogger(Debugger.class);

	private final MtpDeviceService<PortableDevice> mtpDeviceService;

	private final DeviceTrackService tracksService;

	@SuppressWarnings("unchecked")
	public Debugger() {
		this.mtpDeviceService = ContextProvider.getDeviceService();
		this.tracksService = ContextProvider.getDeviceTrackService();
		DriverManagerDataSource dataSource = ContextProvider.getSingleBeanOfType(DriverManagerDataSource.class);
		String url = new StringBuilder().append("jdbc:hsqldb:file:").append("c:\\temp").append(File.separator).append(
				"zenses.data;shutdown=true").toString();
		dataSource.setUrl(url);

	}

	public void debug() {
		List<MtpDevice<PortableDevice>> devices = mtpDeviceService.getDevices();
		if (devices.size() > 0) {
			logger.warn("Found " + devices.size() + " device(s)");
			for (MtpDevice<PortableDevice> mtpDevice : devices) {
				logger.warn("Processing device: " + mtpDevice.getName() + " - fetching tracks...");
				List<MtpDeviceTrack> tracks;
				try {
					tracks = mtpDeviceService.getTracks(mtpDevice, 200);
					logger.warn("Found " + tracks.size() + " tracks on the device [limited to 200 for performance]. "
							+ "Listing tracks with >0 listenCount:");
					for (MtpDeviceTrack track : tracks) {
						tracksService.createOrUpdate(track);
						if (track.getPlayCount() > 0)
							logger.info(track);
					}
				} catch (MTPException e) {
					logger.error("Failed with MTP exception: " + e.getMessage());
				}
			}
		} else {
			logger.error("No devices found!");
		}

	}

	public static void main(String[] args) {
		new Debugger().debug();
	}
}
