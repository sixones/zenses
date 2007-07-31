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

		public List<EntryObject> CheckedItems
		{
			get
			{
				List<EntryObject> entries = new List<EntryObject>();

				foreach (ListViewItem listViewItem in this._cTrackContentView.CheckedItems)
				{
					entries.Add((EntryObject)listViewItem.Tag);
				}

				return entries;
			}
		}

		public ContentView()
		{
			this.InitializeComponent();
			this._lEntries = new List<EntryObject>();
		}

		public void BindData(List<EntryObject> entries, bool withCheckboxes)
		{
			this._lEntries = entries;
			this._cTrackContentView.CheckBoxes = withCheckboxes;
			
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
					listItem.Tag = entry;

					this._cTrackContentView.Items.Add(listItem);
				}
			}
		}
	}
}
