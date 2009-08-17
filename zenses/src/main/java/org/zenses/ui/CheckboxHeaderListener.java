package org.zenses.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.JTable;

public class CheckboxHeaderListener implements ItemListener {
	protected JTable _table;
	
	public CheckboxHeaderListener(JTable table) {
		this._table = table;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		
		if (source instanceof AbstractButton == false) return;
		
		boolean checked = e.getStateChange() == ItemEvent.SELECTED;
		
		for (int x = 0, y = this._table.getRowCount(); x < y; x++) {
			this._table.setValueAt(new Boolean(checked), x, 0);
		}
	}
}
