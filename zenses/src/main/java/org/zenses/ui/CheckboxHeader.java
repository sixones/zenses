package org.zenses.ui;

import java.awt.Component;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class CheckboxHeader extends JCheckBox implements TableCellRenderer, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7801408062756082200L;
	protected CheckboxHeader _rendererComponent;
	protected int _column;
	protected boolean _mousePressed = false;
	
	public CheckboxHeader(ItemListener itemListener) {
		this._rendererComponent = this;
		this._rendererComponent.addItemListener(itemListener);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (table != null) {
			JTableHeader header = table.getTableHeader();
			
			if (header != null) {
				this._rendererComponent.setForeground(header.getForeground());
				this._rendererComponent.setBackground(header.getBackground());
				this._rendererComponent.setFont(header.getFont());
				
				header.addMouseListener(this._rendererComponent);
			}
		}
		
		this.setColumn(column);
		this._rendererComponent.setText("Select All");
		this.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		
		return this._rendererComponent;
	}
	
	protected void setColumn(int index) {
		this._column = index;
	}
	
	public int getColumn() {
		return this._column;
	}
	
	protected void handleClickEvent(MouseEvent e) {
		if (this._mousePressed) {
			this._mousePressed = false;
			
			JTableHeader header = (JTableHeader)e.getSource();
			JTable table = header.getTable();
			TableColumnModel column = table.getColumnModel();
			
			int viewColumn = column.getColumnIndexAtX(e.getX());
			int columnIndex = table.convertColumnIndexToModel(viewColumn);
			
			if (viewColumn == this._column && e.getClickCount() == 1 && columnIndex != -1) {
				this.doClick();
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		this.handleClickEvent(e);
		
		((JTableHeader)e.getSource()).repaint();
	}
	
	public void mousePressed(MouseEvent e) {
		this._mousePressed = true;
	}
	
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
}
