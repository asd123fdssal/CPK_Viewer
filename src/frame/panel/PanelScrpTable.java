package frame.panel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import utils.DataIO;

@SuppressWarnings("serial")
public class PanelScrpTable extends JPanel {

	private JScrollPane scrp;
	private JTable table;

	public PanelScrpTable() {
		table = new JTable();
		scrp = new JScrollPane(table);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrp.setAutoscrolls(true);
		
		setLayout(new BorderLayout());
		add(scrp);
		
		refreshTable();
	}

	public void refreshTable() {
		setDatas();
		repaint();
		revalidate();
		resizeColumnWidth();
	}

	private void setDatas() {
		DataIO.getInstance().getDatas();
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		tm.setDataVector(DataIO.getInstance().getArrData(), DataIO.getInstance().getArrColumn());
	}
	
	private void resizeColumnWidth() {
		final TableColumnModel colModel = table.getColumnModel();
		for(int i = 0; i < table.getColumnCount(); i++) {
			int width = 150;
			for(int j = 0; j < table.getRowCount(); j++) {
				TableCellRenderer tcr = table.getCellRenderer(j, i);
				Component comp = table.prepareRenderer(tcr, j, i);
				width = Math.max(comp.getPreferredSize().width, width);
			}
			colModel.getColumn(i).setPreferredWidth(width);
		}
	}

}
