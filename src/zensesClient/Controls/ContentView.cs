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
		private bool _bAllSelected = false;

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

		public List<EntryObject> SelectedItems
		{
			get
			{
				List<EntryObject> selectedEntries = new List<EntryObject>();

				foreach (ListViewItem item in this._cTrackContentView.SelectedItems)
				{
					selectedEntries.Add((EntryObject)item.Tag);
				}

				return selectedEntries;
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

		public void SelectAll()
		{
			foreach (ListViewItem listItem in this._cTrackContentView.Items)
			{
				listItem.Checked = !this._bAllSelected;
			}

			this._bAllSelected = !this._bAllSelected;
		}

		public void SelectAllForArtist()
		{
			List<string> alreadySelected = new List<string>();

			foreach (EntryObject entry in this.SelectedItems)
			{
				if (!alreadySelected.Contains(entry.Artist))
				{
					this.SelectAllForArtist(entry.Artist);

					alreadySelected.Add(entry.Artist);
				}
			}
		}

		public void SelectAllForArtist(string artist)
		{
			foreach (ListViewItem item in this._cTrackContentView.Items)
			{
				if (((EntryObject)item.Tag).Artist == artist)
				{
					item.Checked = true;
				}
			} 
		}

		public void SelectAllForAlbum()
		{
			List<string> alreadySelected = new List<string>();

			foreach (EntryObject entry in this.SelectedItems)
			{
				if (!alreadySelected.Contains(entry.Album))
				{
					this.SelectAllForAlbum(entry.Album);

					alreadySelected.Add(entry.Album);
				}
			}
		}

		public void SelectAllForAlbum(string album)
		{
			foreach (ListViewItem item in this._cTrackContentView.Items)
			{
				if (((EntryObject)item.Tag).Album == album)
				{
					item.Checked = true;
				}
			}
		}
	}
}
