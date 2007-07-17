namespace Zenses.Tools.TrackBrowser
{
	partial class TrackView
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this._cSplitContainer = new System.Windows.Forms.SplitContainer();
			this._cStatusStrip = new System.Windows.Forms.StatusStrip();
			this._cStatusLabel = new System.Windows.Forms.ToolStripStatusLabel();
			this._cEntryIdColumn = new System.Windows.Forms.ColumnHeader();
			this._cEntryTitleColumn = new System.Windows.Forms.ColumnHeader();
			this._cEntryArtistColumn = new System.Windows.Forms.ColumnHeader();
			this._cEntryAlbumColumn = new System.Windows.Forms.ColumnHeader();
			this._cEntryContentTypeColumn = new System.Windows.Forms.ColumnHeader();
			this._cEntryPlayCountColumn = new System.Windows.Forms.ColumnHeader();
			this._cTrackListView = new System.Windows.Forms.ListView();
			this._cSplitContainer.Panel1.SuspendLayout();
			this._cSplitContainer.Panel2.SuspendLayout();
			this._cSplitContainer.SuspendLayout();
			this._cStatusStrip.SuspendLayout();
			this.SuspendLayout();
			// 
			// _cSplitContainer
			// 
			this._cSplitContainer.Dock = System.Windows.Forms.DockStyle.Fill;
			this._cSplitContainer.Location = new System.Drawing.Point(0, 0);
			this._cSplitContainer.Name = "_cSplitContainer";
			this._cSplitContainer.Orientation = System.Windows.Forms.Orientation.Horizontal;
			// 
			// _cSplitContainer.Panel1
			// 
			this._cSplitContainer.Panel1.Controls.Add(this._cTrackListView);
			// 
			// _cSplitContainer.Panel2
			// 
			this._cSplitContainer.Panel2.Controls.Add(this._cStatusStrip);
			this._cSplitContainer.Size = new System.Drawing.Size(888, 199);
			this._cSplitContainer.SplitterDistance = 170;
			this._cSplitContainer.TabIndex = 0;
			// 
			// _cStatusStrip
			// 
			this._cStatusStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this._cStatusLabel});
			this._cStatusStrip.Location = new System.Drawing.Point(0, 3);
			this._cStatusStrip.Name = "_cStatusStrip";
			this._cStatusStrip.Size = new System.Drawing.Size(888, 22);
			this._cStatusStrip.TabIndex = 0;
			// 
			// _cStatusLabel
			// 
			this._cStatusLabel.Name = "_cStatusLabel";
			this._cStatusLabel.Size = new System.Drawing.Size(71, 17);
			this._cStatusLabel.Text = "Loading ......";
			this._cStatusLabel.Visible = false;
			// 
			// _cEntryIdColumn
			// 
			this._cEntryIdColumn.Text = "Entry ID";
			this._cEntryIdColumn.Width = 61;
			// 
			// _cEntryTitleColumn
			// 
			this._cEntryTitleColumn.Text = "Title";
			this._cEntryTitleColumn.Width = 216;
			// 
			// _cEntryArtistColumn
			// 
			this._cEntryArtistColumn.Text = "Artist";
			this._cEntryArtistColumn.Width = 211;
			// 
			// _cEntryAlbumColumn
			// 
			this._cEntryAlbumColumn.Text = "Album";
			this._cEntryAlbumColumn.Width = 195;
			// 
			// _cEntryContentTypeColumn
			// 
			this._cEntryContentTypeColumn.Text = "Content Type";
			this._cEntryContentTypeColumn.Width = 101;
			// 
			// _cEntryPlayCountColumn
			// 
			this._cEntryPlayCountColumn.Text = "Play Count";
			this._cEntryPlayCountColumn.Width = 82;
			// 
			// _cTrackListView
			// 
			this._cTrackListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this._cEntryIdColumn,
            this._cEntryTitleColumn,
            this._cEntryArtistColumn,
            this._cEntryAlbumColumn,
            this._cEntryContentTypeColumn,
            this._cEntryPlayCountColumn});
			this._cTrackListView.Dock = System.Windows.Forms.DockStyle.Fill;
			this._cTrackListView.FullRowSelect = true;
			this._cTrackListView.GridLines = true;
			this._cTrackListView.Location = new System.Drawing.Point(0, 0);
			this._cTrackListView.MultiSelect = false;
			this._cTrackListView.Name = "_cTrackListView";
			this._cTrackListView.Size = new System.Drawing.Size(888, 170);
			this._cTrackListView.Sorting = System.Windows.Forms.SortOrder.Ascending;
			this._cTrackListView.TabIndex = 2;
			this._cTrackListView.UseCompatibleStateImageBehavior = false;
			this._cTrackListView.View = System.Windows.Forms.View.Details;
			// 
			// TrackView
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(888, 199);
			this.Controls.Add(this._cSplitContainer);
			this.Name = "TrackView";
			this.Text = "Zenses : Tools : Device Viewer : Track Browser";
			this._cSplitContainer.Panel1.ResumeLayout(false);
			this._cSplitContainer.Panel2.ResumeLayout(false);
			this._cSplitContainer.Panel2.PerformLayout();
			this._cSplitContainer.ResumeLayout(false);
			this._cStatusStrip.ResumeLayout(false);
			this._cStatusStrip.PerformLayout();
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.SplitContainer _cSplitContainer;
		private System.Windows.Forms.StatusStrip _cStatusStrip;
		private System.Windows.Forms.ToolStripStatusLabel _cStatusLabel;
		private System.Windows.Forms.ListView _cTrackListView;
		private System.Windows.Forms.ColumnHeader _cEntryIdColumn;
		private System.Windows.Forms.ColumnHeader _cEntryTitleColumn;
		private System.Windows.Forms.ColumnHeader _cEntryArtistColumn;
		private System.Windows.Forms.ColumnHeader _cEntryAlbumColumn;
		private System.Windows.Forms.ColumnHeader _cEntryContentTypeColumn;
		private System.Windows.Forms.ColumnHeader _cEntryPlayCountColumn;


	}
}