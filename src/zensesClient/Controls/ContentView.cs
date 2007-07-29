using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Text;
using System.Windows.Forms;

using Zenses.Lib.Objects;

namespace Zenses.Client.Controls
{
	public partial class ContentView : UserControl
	{
		private List<EntryObject> _lEntries;

		public List<EntryObject> Entries
		{
			get { return this._lEntries; }
			set { this._lEntries = value; }
		}

		public ContentView(List<EntryObject> entries)
		{
			InitializeComponent();

			this._lEntries = entries;

			if (this._lEntries != null && this._lEntries.Count > 0)
			{
				foreach (EntryObject entry in this._lEntries)
				{
					string[] items = new string[] {
						entry.Name,
						entry.Artist,
						entry.Album,
						entry.Length.ToString(),
						entry.PlayCount.ToString()
					};

					ListViewItem listItem = new ListViewItem(items);

					this._cTrackContentView.Items.Add(listItem);
				}
			}
		}
	}
}
