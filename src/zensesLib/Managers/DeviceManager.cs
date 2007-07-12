using System;
using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;

using PortableDeviceApiLib;

using Zenses.Lib.Constants;
using Zenses.Lib.Objects;

namespace Zenses.Lib.Managers
{
	public class DeviceManager
	{
		private PortableDeviceManager _hPortableDeviceManager;
		private IPortableDeviceValues _hClientDeviceValues;
		private uint _uDeviceCount;
		private string[] _aDeviceIds;

		public DeviceManager()
		{
			this._hPortableDeviceManager = new PortableDeviceManager();
			this._aDeviceIds = new string[1];
			this._uDeviceCount = 1;

			// setup client device values
			this._hClientDeviceValues = new PortableDeviceTypesLib.PortableDeviceValues() as IPortableDeviceValues;
			this._hClientDeviceValues.SetStringValue(ref PropertyKeys.WPD_CLIENT_NAME, "Zenses");
		}

		public List<Device> GetDevices()
		{
			this._hPortableDeviceManager.GetDevices(ref this._aDeviceIds[0], ref this._uDeviceCount);

			if (this._uDeviceCount > 0)
			{
				this._aDeviceIds = new string[this._uDeviceCount];
				this._hPortableDeviceManager.GetDevices(ref this._aDeviceIds[0], ref this._uDeviceCount);

				List<Device> availableDevices = new List<Device>();

				for (uint i = 0; i < this._uDeviceCount; i++)
				{
					Device device = new Device();
					device.Id = this._aDeviceIds[0];

					this.UpdateDeviceProperties(ref device);

					availableDevices.Add(device);
				}

				return availableDevices;
			}

			return null;
		}

		public void UpdateDeviceProperties(ref Device device)
		{
			Hashtable properties = GetDeviceProperties(device.Id);

			if (properties.Count != 0)
			{
				device.FriendlyName = (string)properties[PropertyNames.DEVICE_FRIENDLY_NAME];
				device.Model = (string)properties[PropertyNames.DEVICE_MODEL];
				device.Manufacturer = (string)properties[PropertyNames.DEVICE_MANUFACTURER];
				device.SerialNumber = (string)properties[PropertyNames.DEVICE_SERIAL_NUMBER];
				device.BatteryLevel = (int)((uint)properties[PropertyNames.DEVICE_BATTERY_LEVEL]);
			}
		}

		public Hashtable GetDeviceProperties(string deviceId)
		{
			Hashtable properties = new Hashtable();

			PortableDeviceClass deviceClass = new PortableDeviceClass();
			IPortableDeviceContent deviceContent;

			deviceClass.Open(deviceId, this._hClientDeviceValues);
			deviceClass.Content(out deviceContent);

			IPortableDeviceProperties deviceProperties;
			deviceContent.Properties(out deviceProperties);

			IPortableDeviceValues deviceValues;
			deviceProperties.GetValues("DEVICE", null, out deviceValues);

			uint devicePropertyCount = 0;
			deviceValues.GetCount(ref devicePropertyCount);

			for (uint i = 0; i < devicePropertyCount; i++)
			{
				_tagpropertykey tagPropertyKey = new _tagpropertykey();
				tag_inner_PROPVARIANT tagPropVariant = new tag_inner_PROPVARIANT();

				deviceValues.GetAt(i, ref tagPropertyKey, ref tagPropVariant);

				IntPtr ptrValue = Marshal.AllocHGlobal(Marshal.SizeOf(tagPropVariant));

				Marshal.StructureToPtr(tagPropVariant, ptrValue, false);

				PropVariant pvValue = (PropVariant)Marshal.PtrToStructure(ptrValue, typeof(PropVariant));

				if (pvValue.variantType == VariantTypes.VT_LPWSTR)
				{
					string stringValue = Marshal.PtrToStringUni(pvValue.pointerValue);
					properties.Add(PropertyManager.GetKeyName(tagPropertyKey.pid, tagPropertyKey.fmtid), stringValue);
				}
				else if (pvValue.variantType == VariantTypes.VT_DATE)
				{
					DateTime datetime = DateTime.FromOADate(pvValue.dateValue);
					properties.Add(PropertyManager.GetKeyName(tagPropertyKey.pid, tagPropertyKey.fmtid), datetime);
				}
				else if (pvValue.variantType == VariantTypes.VT_UI4)
				{
					uint intValue = pvValue.byteValue;
					properties.Add(PropertyManager.GetKeyName(tagPropertyKey.pid, tagPropertyKey.fmtid), intValue);
				}
				else if (pvValue.variantType == VariantTypes.VT_UI8)
				{
					long intValue = pvValue.longValue;
					properties.Add(PropertyManager.GetKeyName(tagPropertyKey.pid, tagPropertyKey.fmtid), intValue);
				}
				else if (pvValue.variantType == VariantTypes.VT_UINT)
				{
					long intValue = pvValue.longValue;
					properties.Add(PropertyManager.GetKeyName(tagPropertyKey.pid, tagPropertyKey.fmtid), intValue);
				}
				else
				{
					// OBJECT_CONTENT_TYPE
					// OBJECT_FORMAT
				}
			}

			return properties;
		}
	}
}
