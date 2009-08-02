package org.zenses.mtp;

import java.util.List;

public interface MtpDeviceService<T> {

	List<MtpDevice<T>> getDevices();

	List<MtpDeviceTrack> getTracks(MtpDevice<T> portableDevice);

}