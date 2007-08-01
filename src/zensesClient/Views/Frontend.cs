using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

using Zenses.Client.Controls;
using Zenses.Client.Properties;

using Zenses.Lib.DataAccessObjects;
using Zenses.Lib.Gui;
using Zenses.Lib.Objects;
using Zenses.Lib.Managers;

namespace Zenses.Client.Views
{
	public partial class Frontend : Form
	{
		private Device _oDevice;
		private List<Device> _lConnectedDevices;
		private EntryObjectDAO _hEntryDAO;
		private ShrinkArea _hSubmitAdvancedOptionsArea;

		public Frontend()
		{
			this.InitializeComponent();

			// make shrink area and hide by default
			this._hSubmitAdvancedOptionsArea = new ShrinkArea(this._cSubmitSplitContainer.Panel1, this._cSubmitSplitContainer, 35, 140);
			this._hSubmitAdvancedOptionsArea.Toggle();

			//this.LoadRecentlyPlayed();
		}

		public void GetConnectedDevice()
		{
			if (this._lConnectedDevices == null)
			{
				DeviceManager deviceManager = new DeviceManager();
				this._lConnectedDevices = deviceManager.GetDevices();
			}

			if (this._lConnectedDevices != null && this._lConnectedDevices.Count > 0)
			{
				// select the first device
				this._oDevice = this._lConnectedDevices[0];
			}
		}

		public void InitializeSummaryTab()
		{
			this.GetConnectedDevice();

			// device information group
			if (this._oDevice != null)
			{
				this._cDeviceStatusLabelValue.Text = "Connected";
				this._cDeviceNameLabelValue.Text = this._oDevice.FriendlyName;
				this._cDeviceModelLabelValue.Text = this._oDevice.Model;
				this._cDeviceManufacturerLabelValue.Text = this._oDevice.Manufacturer;
			}
			else
			{
				this._cDeviceStatusLabelValue.Text = "Not Connected";
				this._cDeviceNameLabelValue.Text = "";
				this._cDeviceModelLabelValue.Text = "";
				this._cDeviceManufacturerLabelValue.Text = "";
			}

			// zenses information group
			this._hEntryDAO = new EntryObjectDAO();
			this._cInfoTracksStoredLabelValue.Text = this._hEntryDAO.FetchAll().Count.ToString();

			// last.fm information group
			this._cLastFmUsernameLabelValue.Text = Settings.Default.LastFM_Username;

			if (Settings.Default.Scrobble_LastDate == null || Settings.Default.Scrobble_LastDate == DateTime.MinValue)
			{
				this._cLastFmLastScrobbledLabelValue.Text = "Never";
			}
			else
			{
				this._cLastFmLastScrobbledLabelValue.Text = Settings.Default.Scrobble_LastDate.ToShortDateString();
			}
		}

		public void InitializeRecentlyPlayedTab()
		{
			EntryObjectDAO entryDAO = new EntryObjectDAO();
			this._cPlayedContentView.BindData(entryDAO.FetchNotSubmitted(), false);
		}

		public void InitializeScrobbleTracksTab()
		{
			if (Settings.Default.Scrobble_LastDate == null || Settings.Default.Scrobble_LastDate == DateTime.MinValue)
			{
				this._cSubmitFromDateValue.Value = DateTime.Now;
			}
			else
			{
				this._cSubmitFromDateValue.Value = Settings.Default.Scrobble_LastDate;
			}

			EntryObjectDAO entryDAO = new EntryObjectDAO();
			this._cSubmitContentView.BindData(entryDAO.FetchNotSubmitted(), true);
		}

		public void ScanDeviceForContent()
		{
			this._hEntryDAO = new EntryObjectDAO();

			this.GetConnectedDevice();

			EntryManager entryManager = new EntryManager(this._oDevice);
			entryManager.FoundNewMusicEntryEvent += new EntryManager.FoundTrackEntryDelegate(this.FoundEntryObject);

			entryManager.BeginFetchContent();
		}

		private int foundCount = 0;

		public void FoundEntryObject(EntryObject entry)
		{
			// save in storage
			this._hEntryDAO.SaveObject(entry);
			this.foundCount++;
			this._cStatusStripStatusValue.Text = "Found " + this.foundCount.ToString() + " entries";
		}

		private void _cSummaryTab_Enter(object sender, EventArgs e)
		{
			this.InitializeSummaryTab();
		}

		private void _cFetchContentButton_Click(object sender, EventArgs e)
		{
			this.ScanDeviceForContent();
		}

		private void _cSubmitSplitContainer_Panel2_Enter(object sender, EventArgs e)
		{
			if (this._cSubmitContentView.Entries == null || this._cSubmitContentView.Entries.Count == 0)
			{
				this.InitializeScrobbleTracksTab();
			}
		}

		private void _cPlayedTab_Enter(object sender, EventArgs e)
		{
			if (this._cPlayedContentView.Entries == null || this._cPlayedContentView.Entries.Count == 0)
			{
				this.InitializeRecentlyPlayedTab();
			}
		}

		private void _cSubmitShowAdvancedButton_Click(object sender, EventArgs e)
		{
			this._hSubmitAdvancedOptionsArea.Toggle();

			if (this._hSubmitAdvancedOptionsArea.Expanded)
			{
				this._cSubmitShowAdvancedButton.Text = "Hide";
			}
			else
			{
				this._cSubmitShowAdvancedButton.Text = "Advanced";
			}
		}

		private void _cSubmitTracksButton_Click(object sender, EventArgs e)
		{
			if (this._cSubmitContentView.CheckedItems.Count > 0)
			{
				ConfirmScrobble confirmScrobbleForm = new ConfirmScrobble();

				PlayedTrackManager trackManager = new PlayedTrackManager();
				List<PlayedTrack> playedTracks = trackManager.GenerateScrobbleTimes(this._cSubmitContentView.CheckedItems, this._cSubmitFromDateValue.Value);

				confirmScrobbleForm.Show();
				confirmScrobbleForm.DataBind(playedTracks);
			}
			else
			{
				MessageBox.Show("You need to select some tracks to scrobble first");
			}
		}

		private void _cSubmitSelectAllContextItem_Click(object sender, EventArgs e)
		{
			this._cSubmitContentView.SelectAll();
		}

		private void _cSubmitSelectArtistContextItem_Click(object sender, EventArgs e)
		{
			this._cSubmitContentView.SelectAllForArtist();
		}

		private void _cSubmitSelectAlbumContextItem_Click(object sender, EventArgs e)
		{
			this._cSubmitContentView.SelectAllForAlbum();
		}
	}
}