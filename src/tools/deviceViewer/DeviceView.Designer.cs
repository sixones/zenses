namespace Zenses.Tools.DeviceViewer
{
	partial class DeviceView
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
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DeviceView));
			this._cDeviceListView = new System.Windows.Forms.ListView();
			this._cDeviceIdColumn = new System.Windows.Forms.ColumnHeader();
			this._cDeviceFriendlyNameColumn = new System.Windows.Forms.ColumnHeader();
			this._cDeviceModelColumn = new System.Windows.Forms.ColumnHeader();
			this._cDeviceManufacturerColumn = new System.Windows.Forms.ColumnHeader();
			this._cDeviceSerialNumberColumn = new System.Windows.Forms.ColumnHeader();
			this._cDeviceBatteryLevelColumn = new System.Windows.Forms.ColumnHeader();
			this._cSplitContainer = new System.Windows.Forms.SplitContainer();
			this._cMainToolbarStrip = new System.Windows.Forms.ToolStrip();
			this._cRefreshViewButton = new System.Windows.Forms.ToolStripButton();
			this._cSplitContainer.Panel1.SuspendLayout();
			this._cSplitContainer.Panel2.SuspendLayout();
			this._cSplitContainer.SuspendLayout();
			this._cMainToolbarStrip.SuspendLayout();
			this.SuspendLayout();
			// 
			// _cDeviceListView
			// 
			this._cDeviceListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this._cDeviceIdColumn,
            this._cDeviceFriendlyNameColumn,
            this._cDeviceModelColumn,
            this._cDeviceManufacturerColumn,
            this._cDeviceSerialNumberColumn,
            this._cDeviceBatteryLevelColumn});
			this._cDeviceListView.Dock = System.Windows.Forms.DockStyle.Fill;
			this._cDeviceListView.FullRowSelect = true;
			this._cDeviceListView.GridLines = true;
			this._cDeviceListView.Location = new System.Drawing.Point(0, 0);
			this._cDeviceListView.MultiSelect = false;
			this._cDeviceListView.Name = "_cDeviceListView";
			this._cDeviceListView.Size = new System.Drawing.Size(888, 170);
			this._cDeviceListView.Sorting = System.Windows.Forms.SortOrder.Ascending;
			this._cDeviceListView.TabIndex = 0;
			this._cDeviceListView.UseCompatibleStateImageBehavior = false;
			this._cDeviceListView.View = System.Windows.Forms.View.Details;
			// 
			// _cDeviceIdColumn
			// 
			this._cDeviceIdColumn.Text = "Device ID";
			this._cDeviceIdColumn.Width = 240;
			// 
			// _cDeviceFriendlyNameColumn
			// 
			this._cDeviceFriendlyNameColumn.Text = "Friendly Name";
			this._cDeviceFriendlyNameColumn.Width = 144;
			// 
			// _cDeviceModelColumn
			// 
			this._cDeviceModelColumn.Text = "Model";
			this._cDeviceModelColumn.Width = 134;
			// 
			// _cDeviceManufacturerColumn
			// 
			this._cDeviceManufacturerColumn.Text = "Manufacturer";
			this._cDeviceManufacturerColumn.Width = 86;
			// 
			// _cDeviceSerialNumberColumn
			// 
			this._cDeviceSerialNumberColumn.Text = "Serial Number";
			this._cDeviceSerialNumberColumn.Width = 193;
			// 
			// _cDeviceBatteryLevelColumn
			// 
			this._cDeviceBatteryLevelColumn.Text = "Battery Level";
			this._cDeviceBatteryLevelColumn.Width = 82;
			// 
			// _cSplitContainer
			// 
			this._cSplitContainer.Dock = System.Windows.Forms.DockStyle.Fill;
			this._cSplitContainer.FixedPanel = System.Windows.Forms.FixedPanel.Panel1;
			this._cSplitContainer.Location = new System.Drawing.Point(0, 0);
			this._cSplitContainer.Name = "_cSplitContainer";
			this._cSplitContainer.Orientation = System.Windows.Forms.Orientation.Horizontal;
			// 
			// _cSplitContainer.Panel1
			// 
			this._cSplitContainer.Panel1.Controls.Add(this._cMainToolbarStrip);
			this._cSplitContainer.Panel1MinSize = 20;
			// 
			// _cSplitContainer.Panel2
			// 
			this._cSplitContainer.Panel2.Controls.Add(this._cDeviceListView);
			this._cSplitContainer.Size = new System.Drawing.Size(888, 199);
			this._cSplitContainer.SplitterDistance = 25;
			this._cSplitContainer.TabIndex = 1;
			// 
			// _cMainToolbarStrip
			// 
			this._cMainToolbarStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this._cRefreshViewButton});
			this._cMainToolbarStrip.Location = new System.Drawing.Point(0, 0);
			this._cMainToolbarStrip.Name = "_cMainToolbarStrip";
			this._cMainToolbarStrip.Size = new System.Drawing.Size(888, 25);
			this._cMainToolbarStrip.TabIndex = 0;
			this._cMainToolbarStrip.Text = "toolStrip1";
			// 
			// _cRefreshViewButton
			// 
			this._cRefreshViewButton.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
			this._cRefreshViewButton.Image = ((System.Drawing.Image)(resources.GetObject("_cRefreshViewButton.Image")));
			this._cRefreshViewButton.ImageTransparentColor = System.Drawing.Color.Magenta;
			this._cRefreshViewButton.Name = "_cRefreshViewButton";
			this._cRefreshViewButton.Size = new System.Drawing.Size(93, 22);
			this._cRefreshViewButton.Text = "Refresh Devices";
			this._cRefreshViewButton.Click += new System.EventHandler(this._cRefreshViewButton_Click);
			// 
			// DeviceView
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(888, 199);
			this.Controls.Add(this._cSplitContainer);
			this.Name = "DeviceView";
			this.Text = "Zenses : Tools : Device Viewer";
			this._cSplitContainer.Panel1.ResumeLayout(false);
			this._cSplitContainer.Panel1.PerformLayout();
			this._cSplitContainer.Panel2.ResumeLayout(false);
			this._cSplitContainer.ResumeLayout(false);
			this._cMainToolbarStrip.ResumeLayout(false);
			this._cMainToolbarStrip.PerformLayout();
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.ListView _cDeviceListView;
		private System.Windows.Forms.ColumnHeader _cDeviceIdColumn;
		private System.Windows.Forms.ColumnHeader _cDeviceFriendlyNameColumn;
		private System.Windows.Forms.ColumnHeader _cDeviceModelColumn;
		private System.Windows.Forms.ColumnHeader _cDeviceManufacturerColumn;
		private System.Windows.Forms.ColumnHeader _cDeviceSerialNumberColumn;
		private System.Windows.Forms.ColumnHeader _cDeviceBatteryLevelColumn;
		private System.Windows.Forms.SplitContainer _cSplitContainer;
		private System.Windows.Forms.ToolStrip _cMainToolbarStrip;
		private System.Windows.Forms.ToolStripButton _cRefreshViewButton;
	}
}

