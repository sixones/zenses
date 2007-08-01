using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

using Zenses.Lib.Objects;

namespace Zenses.Client.Views
{
	public partial class ConfirmScrobble : Form
	{
		private List<PlayedTrack> _lEntries;

		public ConfirmScrobble()
		{
			InitializeComponent();
		}

		public void DataBind(List<PlayedTrack> entries)
		{
			this._lEntries = entries;

			if (this._lEntries != null && this._lEntries.Count > 0)
			{
				foreach (PlayedTrack track in this._lEntries)
				{
					string[] items = new string[] {
						track.EntryObject.Name,
						track.EntryObject.Artist,
						track.EntryObject.Album,
						track.DateSubmitted.ToString()
					};

					ListViewItem listItem = new ListViewItem(items);
					listItem.Tag = track;

					this._cScobbleList.Items.Add(listItem);
				}
			}
		}
	}
}