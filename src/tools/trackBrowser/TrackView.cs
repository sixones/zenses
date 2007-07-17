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

namespace Zenses.Tools.TrackBrowser
{
	public partial class TrackView : Form
	{
		public TrackView()
		{
			InitializeComponent();

			Thread thread = new Thread(new ThreadStart(this.PopulateObjectListView));
			thread.Start();
		}

		public void PopulateObjectListView()
		{
			DeviceManager deviceManager = new DeviceManager();
			Device device = deviceManager.GetDevices()[0];

			EntryManager entryManager = new EntryManager(device);

			entryManager.FoundNewMusicEntryEvent += new EntryManager.FoundTrackEntryDelegate(this.UpdateEntryView);

			this.UpdateStatus(true);

			entryManager.BeginFetchContent();

			this.UpdateStatus(false);
		}

		public delegate void UpdateStatusEvent(bool visible);

		public void UpdateStatus(bool visible)
		{
			if (this.InvokeRequired)
			{
				this.BeginInvoke(new UpdateStatusEvent(this.UpdateStatus), visible);
			}
			else
			{
				this._cStatusLabel.Visible = visible;
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
					entry.Name,
					entry.Artist,
					entry.Album,
					entry.ContentType,
					entry.PlayCount.ToString()
				};

				ListViewItem listViewItem = new ListViewItem(subItems);

				this._cTrackListView.Items.Add(listViewItem);
			}
		}
	}
}