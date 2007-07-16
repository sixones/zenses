using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Threading;
using System.Windows.Forms;

using Zenses.Lib.Constants;
using Zenses.Lib.Managers;
using Zenses.Lib.Objects;
using Zenses.Lib.Collections;

namespace Zenses.Tools.DeviceViewer
{
	public partial class DeviceView : Form
	{
		public DeviceView()
		{
			InitializeComponent();
			this.PopulateDeviceListView();
			Thread thread = new Thread(new ThreadStart(this.PopulateObjectListView));
			thread.Start();
		}

		public void PopulateObjectListView()
		{
			DeviceManager deviceManager = new DeviceManager();
			Device device = deviceManager.GetDevices()[0];

			TrackEntries entries = new TrackEntries(device);

			foreach (EntryObject entry in entries)
			{
				this.UpdateEntryView(entry);	
			}
		}

		public delegate void UpdateEntryViewEvent(EntryObject entry);

		public void UpdateEntryView(EntryObject entry)
		{
			if (this.InvokeRequired)
			{
				this.BeginInvoke(new UpdateEntryViewEvent(this.UpdateEntryView), entry);
			}
			else
			{
				string[] subItems = new string[] {
					entry.Id,
					entry.Title,
					entry.Artist,
					entry.Album,
					entry.Length.ToString(),
					entry.Size.ToString()
				};

				ListViewItem listViewItem = new ListViewItem(subItems);

				this._cDeviceListView.Items.Add(listViewItem);
			}
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