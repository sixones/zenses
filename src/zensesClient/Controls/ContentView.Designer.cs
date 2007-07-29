namespace Zenses.Client.Controls
{
	partial class ContentView
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

		#region Component Designer generated code

		/// <summary> 
		/// Required method for Designer support - do not modify 
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this._cTrackContentView = new System.Windows.Forms.ListView();
			this._cTitleColumn = new System.Windows.Forms.ColumnHeader();
			this._cArtistNameColumn = new System.Windows.Forms.ColumnHeader();
			this._cAlbumNameColumn = new System.Windows.Forms.ColumnHeader();
			this._cDurationColumn = new System.Windows.Forms.ColumnHeader();
			this._cColumn = new System.Windows.Forms.ColumnHeader();
			this.SuspendLayout();
			// 
			// _cTrackContentView
			// 
			this._cTrackContentView.BorderStyle = System.Windows.Forms.BorderStyle.None;
			this._cTrackContentView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this._cTitleColumn,
            this._cArtistNameColumn,
            this._cAlbumNameColumn,
            this._cDurationColumn,
            this._cColumn});
			this._cTrackContentView.Dock = System.Windows.Forms.DockStyle.Fill;
			this._cTrackContentView.FullRowSelect = true;
			this._cTrackContentView.GridLines = true;
			this._cTrackContentView.Location = new System.Drawing.Point(0, 0);
			this._cTrackContentView.Name = "_cTrackContentView";
			this._cTrackContentView.Size = new System.Drawing.Size(804, 402);
			this._cTrackContentView.TabIndex = 0;
			this._cTrackContentView.UseCompatibleStateImageBehavior = false;
			this._cTrackContentView.View = System.Windows.Forms.View.Details;
			// 
			// _cTitleColumn
			// 
			this._cTitleColumn.Text = "Title";
			this._cTitleColumn.Width = 264;
			// 
			// _cArtistNameColumn
			// 
			this._cArtistNameColumn.Text = "Artist";
			this._cArtistNameColumn.Width = 158;
			// 
			// _cAlbumNameColumn
			// 
			this._cAlbumNameColumn.Text = "Album";
			this._cAlbumNameColumn.Width = 188;
			// 
			// _cDurationColumn
			// 
			this._cDurationColumn.Text = "Duration";
			this._cDurationColumn.Width = 95;
			// 
			// _cColumn
			// 
			this._cColumn.Text = "Play Count";
			this._cColumn.Width = 83;
			// 
			// ContentView
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.Controls.Add(this._cTrackContentView);
			this.Name = "ContentView";
			this.Size = new System.Drawing.Size(804, 402);
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.ListView _cTrackContentView;
		private System.Windows.Forms.ColumnHeader _cTitleColumn;
		private System.Windows.Forms.ColumnHeader _cArtistNameColumn;
		private System.Windows.Forms.ColumnHeader _cAlbumNameColumn;
		private System.Windows.Forms.ColumnHeader _cDurationColumn;
		private System.Windows.Forms.ColumnHeader _cColumn;
	}
}
