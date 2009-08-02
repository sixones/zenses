package org.zenses.mtp.linux;

import org.zenses.mtp.MtpDevice;

public class MtpDeviceImpl implements MtpDevice<FakeLibraryDevice> {

	private FakeLibraryDevice delegate;

	public MtpDeviceImpl(FakeLibraryDevice fakeLibraryDevice) {
		this.delegate = fakeLibraryDevice;
	}

	public FakeLibraryDevice getDelegate() {
		return delegate;
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public String toString() {
		return getName();
	}

}
