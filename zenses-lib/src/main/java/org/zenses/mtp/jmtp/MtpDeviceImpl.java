package org.zenses.mtp.jmtp;

import jmtp.PortableDevice;

import org.zenses.mtp.MtpDevice;

public class MtpDeviceImpl implements MtpDevice<PortableDevice> {

	private PortableDevice delegate;
	
	public MtpDeviceImpl(PortableDevice dev) {
		this.delegate = dev;
	}

	public PortableDevice getDelegate() {
		return delegate;
	}

	@Override
	public String getName() {
		return delegate.getFriendlyName();
	}

	@Override
	public String toString() {
		return getName();
	}

}
