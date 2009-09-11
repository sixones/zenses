package org.zenses;

import java.util.List;

import jmtp.PortableDevice;

import org.apache.log4j.Logger;
import org.zenses.lib.ContextProvider;
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

	public Debugger() {
		this.mtpDeviceService = ContextProvider.getDeviceService();
	}

	public void debug() {
		List<MtpDevice<PortableDevice>> devices = mtpDeviceService.getDevices();
		if (devices.size() > 0) {
			logger.warn("Found " + devices.size() + " device(s)");
			for (MtpDevice<PortableDevice> mtpDevice : devices) {
				logger.warn("Processing device: " + mtpDevice.getName() + " - fetching tracks...");
				List<MtpDeviceTrack> tracks = mtpDeviceService.getTracks(mtpDevice, 200);
				logger
						.warn("Found "
								+ tracks.size()
								+ " tracks on the device [limited to 100 for performance]. Listing tracks with >0 listenCount:");
				for (MtpDeviceTrack track : tracks) {
					if (track.getPlayCount() > 0)
						logger.info(track);
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
