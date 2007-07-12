using System;
using System.Collections.Generic;
using System.Text;

namespace Zenses.Lib.Objects
{
	public class Device
	{
		private string _sId;
		private string _sFriendlyName;
		private string _sModel;
		private string _sManufacturer;
		private string _sSerialNumber;
		private int _iBatteryLevel;

		public string Id
		{
			get { return this._sId; }
			set { this._sId = value; }
		}

		public string FriendlyName
		{
			get { return this._sFriendlyName; }
			set { this._sFriendlyName = value; }
		}

		public string Model
		{
			get { return this._sModel; }
			set { this._sModel = value; }
		}

		public string Manufacturer
		{
			get { return this._sManufacturer; }
			set { this._sManufacturer = value; }
		}

		public string SerialNumber
		{
			get { return this._sSerialNumber; }
			set { this._sSerialNumber = value; }
		}

		public int BatteryLevel
		{
			get { return this._iBatteryLevel; }
			set { this._iBatteryLevel = value; }
		}
	}
}
