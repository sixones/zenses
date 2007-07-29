namespace Zenses.Client.Views
{
	partial class Options
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
			this._cCancelButton = new System.Windows.Forms.Button();
			this._cOKButton = new System.Windows.Forms.Button();
			this._cTableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
			this._cTableLayoutPanel.SuspendLayout();
			this.SuspendLayout();
			// 
			// _cCancelButton
			// 
			this._cCancelButton.Location = new System.Drawing.Point(314, 3);
			this._cCancelButton.Name = "_cCancelButton";
			this._cCancelButton.Size = new System.Drawing.Size(75, 23);
			this._cCancelButton.TabIndex = 0;
			this._cCancelButton.Text = "Cancel";
			this._cCancelButton.UseVisualStyleBackColor = true;
			// 
			// _cOKButton
			// 
			this._cOKButton.Location = new System.Drawing.Point(314, 147);
			this._cOKButton.Name = "_cOKButton";
			this._cOKButton.Size = new System.Drawing.Size(75, 23);
			this._cOKButton.TabIndex = 1;
			this._cOKButton.Text = "OK";
			this._cOKButton.UseVisualStyleBackColor = true;
			// 
			// _cTableLayoutPanel
			// 
			this._cTableLayoutPanel.ColumnCount = 2;
			this._cTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this._cTableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this._cTableLayoutPanel.Controls.Add(this._cCancelButton, 1, 0);
			this._cTableLayoutPanel.Controls.Add(this._cOKButton, 1, 1);
			this._cTableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
			this._cTableLayoutPanel.Location = new System.Drawing.Point(0, 0);
			this._cTableLayoutPanel.Name = "_cTableLayoutPanel";
			this._cTableLayoutPanel.RowCount = 2;
			this._cTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this._cTableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
			this._cTableLayoutPanel.Size = new System.Drawing.Size(623, 288);
			this._cTableLayoutPanel.TabIndex = 2;
			// 
			// Options
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(623, 288);
			this.Controls.Add(this._cTableLayoutPanel);
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "Options";
			this.ShowIcon = false;
			this.ShowInTaskbar = false;
			this.Text = "Options";
			this._cTableLayoutPanel.ResumeLayout(false);
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.Button _cOKButton;
		private System.Windows.Forms.Button _cCancelButton;
		private System.Windows.Forms.TableLayoutPanel _cTableLayoutPanel;


	}
}