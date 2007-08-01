namespace Zenses.Client.Views
{
	partial class ConfirmScrobble
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
			this._cScobbleList = new System.Windows.Forms.ListView();
			this._cTrackTitle = new System.Windows.Forms.ColumnHeader();
			this._cTrackArtist = new System.Windows.Forms.ColumnHeader();
			this._cScrobbleDateTime = new System.Windows.Forms.ColumnHeader();
			this._cTrackAlbum = new System.Windows.Forms.ColumnHeader();
			this._cSplitContainer = new System.Windows.Forms.SplitContainer();
			this.button1 = new System.Windows.Forms.Button();
			this._cScrobbleCountLabel = new System.Windows.Forms.Label();
			this._cSplitContainer.Panel1.SuspendLayout();
			this._cSplitContainer.Panel2.SuspendLayout();
			this._cSplitContainer.SuspendLayout();
			this.SuspendLayout();
			// 
			// _cScobbleList
			// 
			this._cScobbleList.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this._cTrackTitle,
            this._cTrackArtist,
            this._cTrackAlbum,
            this._cScrobbleDateTime});
			this._cScobbleList.Dock = System.Windows.Forms.DockStyle.Fill;
			this._cScobbleList.FullRowSelect = true;
			this._cScobbleList.GridLines = true;
			this._cScobbleList.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.Nonclickable;
			this._cScobbleList.Location = new System.Drawing.Point(0, 0);
			this._cScobbleList.MultiSelect = false;
			this._cScobbleList.Name = "_cScobbleList";
			this._cScobbleList.Size = new System.Drawing.Size(655, 293);
			this._cScobbleList.TabIndex = 0;
			this._cScobbleList.UseCompatibleStateImageBehavior = false;
			this._cScobbleList.View = System.Windows.Forms.View.Details;
			// 
			// _cTrackTitle
			// 
			this._cTrackTitle.Text = "Title";
			this._cTrackTitle.Width = 227;
			// 
			// _cTrackArtist
			// 
			this._cTrackArtist.Text = "Artist";
			this._cTrackArtist.Width = 160;
			// 
			// _cScrobbleDateTime
			// 
			this._cScrobbleDateTime.Text = "Date - Time";
			this._cScrobbleDateTime.Width = 84;
			// 
			// _cTrackAlbum
			// 
			this._cTrackAlbum.Text = "Album";
			this._cTrackAlbum.Width = 161;
			// 
			// _cSplitContainer
			// 
			this._cSplitContainer.Dock = System.Windows.Forms.DockStyle.Fill;
			this._cSplitContainer.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
			this._cSplitContainer.IsSplitterFixed = true;
			this._cSplitContainer.Location = new System.Drawing.Point(0, 0);
			this._cSplitContainer.Name = "_cSplitContainer";
			this._cSplitContainer.Orientation = System.Windows.Forms.Orientation.Horizontal;
			// 
			// _cSplitContainer.Panel1
			// 
			this._cSplitContainer.Panel1.Controls.Add(this._cScrobbleCountLabel);
			this._cSplitContainer.Panel1.Controls.Add(this.button1);
			// 
			// _cSplitContainer.Panel2
			// 
			this._cSplitContainer.Panel2.Controls.Add(this._cScobbleList);
			this._cSplitContainer.Size = new System.Drawing.Size(655, 347);
			this._cSplitContainer.TabIndex = 1;
			// 
			// button1
			// 
			this.button1.Location = new System.Drawing.Point(568, 12);
			this.button1.Name = "button1";
			this.button1.Size = new System.Drawing.Size(75, 23);
			this.button1.TabIndex = 0;
			this.button1.Text = "Scrobble!";
			this.button1.UseVisualStyleBackColor = true;
			// 
			// _cScrobbleCountLabel
			// 
			this._cScrobbleCountLabel.AutoSize = true;
			this._cScrobbleCountLabel.Location = new System.Drawing.Point(13, 21);
			this._cScrobbleCountLabel.Name = "_cScrobbleCountLabel";
			this._cScrobbleCountLabel.Size = new System.Drawing.Size(157, 13);
			this._cScrobbleCountLabel.TabIndex = 1;
			this._cScrobbleCountLabel.Text = "0 tracks waiting to be scrobbled";
			// 
			// ConfirmScrobble
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(655, 347);
			this.ControlBox = false;
			this.Controls.Add(this._cSplitContainer);
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "ConfirmScrobble";
			this.ShowIcon = false;
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
			this.Text = "Confirm Scrobbles";
			this.TopMost = true;
			this._cSplitContainer.Panel1.ResumeLayout(false);
			this._cSplitContainer.Panel1.PerformLayout();
			this._cSplitContainer.Panel2.ResumeLayout(false);
			this._cSplitContainer.ResumeLayout(false);
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.ListView _cScobbleList;
		private System.Windows.Forms.ColumnHeader _cTrackTitle;
		private System.Windows.Forms.ColumnHeader _cTrackArtist;
		private System.Windows.Forms.ColumnHeader _cTrackAlbum;
		private System.Windows.Forms.ColumnHeader _cScrobbleDateTime;
		private System.Windows.Forms.SplitContainer _cSplitContainer;
		private System.Windows.Forms.Button button1;
		private System.Windows.Forms.Label _cScrobbleCountLabel;
	}
}