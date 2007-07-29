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
using Zenses.Lib.DataAccessObjects;

namespace Zenses.Tools.TrackBrowser
{
	public partial class TrackView : Form
	{
		private EntryObjectDAO _hEntryObjectDAO;

		public TrackView()
		{
			InitializeComponent();

			this._hEntryObjectDAO = new EntryObjectDAO();

			Thread thread = new Thread(new ThreadStart(this.PopulateObjectListView));
			thread.Start();
		}

		public void PopulateObjectListView()
		{
			DeviceManager deviceManager = new DeviceManager();
			Device device = deviceManager.GetDevices()[0];

			EntryManager entryManager = new EntryManager(device);
			
			DateTime startTime = DateTime.Now;

			entryManager.FoundNewMusicEntryEvent += new EntryManager.FoundTrackEntryDelegate(this.UpdateEntryView);

			this.UpdateStatus(true, "Loading ...");
			
			entryManager.BeginFetchContent();

			TimeSpan totalTime = DateTime.Now - startTime;

			this.UpdateStatus(true, "Tracks: " + this._cTrackListView.Items.Count.ToString() + ", Time Taken: " + totalTime.Duration().TotalSeconds + " seconds");
		}

		public delegate void UpdateStatusEvent(bool visible, string textContent);

		public void UpdateStatus(bool visible, string textContent)
		{
			if (this.InvokeRequired)
			{
				this.BeginInvoke(new UpdateStatusEvent(this.UpdateStatus), visible, textContent);
			}
			else
			{
				this._cStatusLabel.Text = textContent;
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
				this._hEntryObjectDAO.SaveObject(entry);

				string[] subItems = new string[] {
					entry.Id,
					entry.Name,
					entry.Artist,
					entry.Album,
					entry.Length.ToString(),
					entry.PlayCount.ToString()
				};

				ListViewItem listViewItem = new ListViewItem(subItems);

				this._cTrackListView.Items.Add(listViewItem);
			}
		}
	}
}