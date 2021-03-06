package org.zenses.mtp.jmtp;

import java.util.ArrayList;
import java.util.List;

import jmtp.DeviceAlreadyOpenedException;
import jmtp.DeviceClosedException;
import jmtp.PortableDevice;
import jmtp.PortableDeviceAudioObject;
import jmtp.PortableDeviceFolderObject;
import jmtp.PortableDeviceManager;
import jmtp.PortableDeviceObject;
import jmtp.PortableDeviceStorageObject;
import jmtp.PortableDeviceType;

import org.apache.log4j.Logger;
import org.zenses.mtp.MTPException;
import org.zenses.mtp.MtpDevice;
import org.zenses.mtp.MtpDeviceService;
import org.zenses.mtp.MtpDeviceTrack;

public class MtpDeviceServiceImpl implements MtpDeviceService<PortableDevice> {

	private final static Logger logger = Logger.getLogger(MtpDeviceServiceImpl.class);

	public List<MtpDevice<PortableDevice>> getDevices() {
		PortableDeviceManager pdm = new PortableDeviceManager();
		
		PortableDevice[] devices = pdm.getDevices();
		List<MtpDevice<PortableDevice>> deviceWrappers = new ArrayList<MtpDevice<PortableDevice>>(devices.length);
		for (PortableDevice dev : devices) {
			try {
				dev.open();
			} catch (DeviceAlreadyOpenedException doe) {
				//that's fine, it was already open
			}
			if (dev.getType() == PortableDeviceType.MEDIA_PLAYER || dev.getType() == PortableDeviceType.VIDEO) {
				deviceWrappers.add(new MtpDeviceImpl(dev));
			}
			dev.close();

		}
		return deviceWrappers;
	}

	public List<MtpDeviceTrack> getTracks(MtpDevice<PortableDevice> mtpDevice) throws MTPException {
		return getTracks(mtpDevice, -1);
	}

	private void addTracks(String deviceId, PortableDeviceObject pdo, int level, List<MtpDeviceTrack> tracks,
			int maxNumberOfTracks) {
		if (tracks.size() > maxNumberOfTracks && maxNumberOfTracks != -1) {
			return;
		}
		if (pdo instanceof PortableDeviceStorageObject) {
			PortableDeviceStorageObject pdso = (PortableDeviceStorageObject) pdo;
			PortableDeviceObject[] childObjects = pdso.getChildObjects();
			for (PortableDeviceObject child : childObjects) {
				addTracks(deviceId, child, level + 1, tracks, maxNumberOfTracks);
			}
		} else if (pdo instanceof PortableDeviceFolderObject) {
			PortableDeviceFolderObject pdfo = (PortableDeviceFolderObject) pdo;
			PortableDeviceObject[] childObjects = pdfo.getChildObjects();
			for (PortableDeviceObject child : childObjects) {
				addTracks(deviceId, child, level + 1, tracks, maxNumberOfTracks);
			}
		} else if (pdo instanceof PortableDeviceAudioObject) {
			PortableDeviceAudioObject pdao = (PortableDeviceAudioObject) pdo;
			MtpDeviceTrackImpl track = new MtpDeviceTrackImpl(deviceId, pdao);
			tracks.add(track);
			logger.info("Found track [" + tracks.size() + "]: " + track);
		}
	}

	@Override
	public List<MtpDeviceTrack> getTracks(MtpDevice<PortableDevice> mtpDevice, int maxNumberOfTracks)
			throws MTPException {
		PortableDevice portableDevice = mtpDevice.getDelegate();
		try {
			portableDevice.open();

		} catch (DeviceAlreadyOpenedException doe) {
			// that's fine, it was already open
		}
		try {
			List<MtpDeviceTrack> tracks = new ArrayList<MtpDeviceTrack>();
			for (PortableDeviceObject pdo : portableDevice.getRootObjects()) {
				addTracks(portableDevice.getSerialNumber(), pdo, 0, tracks, maxNumberOfTracks);
			}
			portableDevice.close();

			return tracks;
		} catch (DeviceClosedException dce) {
			throw new MTPException(dce.getMessage());
		}

	}

}
