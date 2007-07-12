using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

using Zenses.Lib.Constants;
using Zenses.Lib.Managers;
using Zenses.Lib.Objects;

namespace Zenses.Tools.DeviceViewer
{
	public partial class DeviceView : Form
	{
		public DeviceView()
		{
			InitializeComponent();
			this.PopulateDeviceListView();
		}

		public void PopulateDeviceListView()
		{
			DeviceManager deviceManager = new DeviceManager();
			List<Device> devices = deviceManager.GetDevices();

			if (devices != null && devices.Count != 0)
			{
				foreach (Device device in devices)
				{
					string[] subItems = new string[] {
					device.Id,
					device.FriendlyName,
					device.Model,
					device.Manufacturer,
					device.SerialNumber,
					device.BatteryLevel.ToString()
				};

					ListViewItem listViewItem = new ListViewItem(subItems);

					this._cDeviceListView.Items.Add(listViewItem);
				}
			}
		}

		private void _cRefreshViewButton_Click(object sender, EventArgs e)
		{
			this.PopulateDeviceListView();
		}
	}
}