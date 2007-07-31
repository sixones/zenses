using System;
using System.Collections;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Zenses.Lib.Gui
{
	public class ShrinkArea
	{
		private Panel _hPanelControl;
		private SplitContainer _hSplitContainer;
		private int _iHeight;
		private bool _bExpanded = true;
		Hashtable _dControls;

		public bool Expanded
		{
			get
			{
				return this._bExpanded;
			}
		}

		public ShrinkArea(Panel panel, SplitContainer splitContainer, int topPosition, int bottomPosition)
		{
			this._hPanelControl = panel;
			this._hSplitContainer = splitContainer;

			this._iHeight = bottomPosition - topPosition;

			this._dControls = new Hashtable();

			foreach (Control control in this._hPanelControl.Controls)
			{
				if (topPosition <= control.Location.Y && control.Location.Y < bottomPosition)
				{
					this._dControls.Add(control, null);
				}
			}
		}

		public void Toggle()
		{
			int diff = this._bExpanded ? -this._iHeight : this._iHeight;

			// temporarily suspend the layout
			this._hSplitContainer.SuspendLayout();

			foreach (Control control in this._hPanelControl.Controls)
			{
				if (this._dControls.Contains(control))
				{
					control.Visible = !this._bExpanded;
				}
			}

			this._hSplitContainer.SplitterDistance = this._hSplitContainer.SplitterDistance + diff;

			// resume the layout
			this._hSplitContainer.ResumeLayout();

			this._bExpanded = !this._bExpanded;
		}
	}


	//public class ShrinkArea
	//{
	//    private Form _hPanelControl;
	//    private int _iHeight;
	//    private bool _bExpanded = true;
	//    Hashtable _dControls;
	//    Hashtable _dBelowControls;

	//    public ShrinkArea(Form form, int topPosition, int bottomPosition)
	//    {
	//        this._hPanelControl = form;
	//        this._iHeight = bottomPosition - topPosition;

	//        this._dControls = new Hashtable();
	//        this._dBelowControls = new Hashtable();

	//        foreach (Control control in this._hPanelControl.Controls)
	//        {
	//            if (topPosition <= control.Location.Y &&
	//                control.Location.Y < bottomPosition)
	//            {
	//                this._dControls.Add(control, null);
	//            }

	//            if (control.Location.Y >= bottomPosition)
	//            {
	//                this._dBelowControls.Add(control, null);
	//            }
	//        }
	//    }

	//    public void Toggle()
	//    {
	//        int diff = this._bExpanded ? -this._iHeight : this._iHeight;

	//        // temporarily suspend the layout
	//        this._hPanelControl.SuspendLayout();

	//        foreach (Control control in this._hPanelControl.Controls)
	//        {
	//            if (this._dControls.Contains(control))
	//            {
	//                control.Visible = !this._bExpanded;
	//            }

	//            if (this._dBelowControls.Contains(control))
	//            {
	//                control.Top += diff;
	//            }
	//        }

	//        this._hPanelControl.Size = new Size(this._hPanelControl.Size.Width, this._hPanelControl.Size.Height + diff);

	//        // resume the layout
	//        this._hPanelControl.ResumeLayout();

	//        this._bExpanded = !this._bExpanded;
	//    }
	//}
}
