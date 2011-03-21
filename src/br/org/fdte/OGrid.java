package br.org.fdte;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import br.org.fdte.ComboBoxDataSource.DataItem;

public class OGrid extends JPanel {

	private JTable table;
	private JScrollPane tableScrollPane;
	private JButton bUp;
	private JButton bDn;
	private JButton bNewLine;
	private JButton bInsertLine;
	private JButton bDeleteLine;
	private OGridTableModel tmodel;
	
	// Fired for events: double click in a row
	private OGridDoubleClickListener doubleClickListener = null;

	private static final int BUTTON_W = 60;
	private static final int BUTTON_H = 25;

	public OGrid() {
		tmodel = new OGridTableModel();
		this.setLayout(null);
	} // constructor

	/**
	 * Fired when user double-clicks in a row
	 * @param listener OGridDoubleClickListener implementation
	 */
	public void setDoubleClickListener(OGridDoubleClickListener listener) {
		this.doubleClickListener = listener;
	}
	
	public Vector<Vector> getGridData() {
		return tmodel.getGridData();
	} // getGridData

	public void fillGrid(Vector<Vector> data) {
		tmodel.fillGrid(data);
	} // fillGrid

	int getCurrentSelectedLine() {
		ListSelectionModel selModel = table.getSelectionModel();
		return selModel.getMinSelectionIndex();
	} // getCurrentSelectedLine

	void unSelectLine() {
		ListSelectionModel selModel = table.getSelectionModel();
		selModel.setSelectionInterval(-1, -1);
	} // unSelectLine

	void setFocusOnLine(int row) {
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.setSelectionInterval(row, row);
	} // setFocusOnLine

	public OGridTableModel getOGridTableModel() {
		return tmodel;
	} // getOGridTableModel

	public void addColumn(ColumnConfiguration conf) {
		tmodel.addColumn(conf);
	} // addColumn

	private void showButtons() {
		int w = this.getWidth();
		int h = this.getHeight();

		int dy = h / 6;

		bUp = new JButton();
		bUp.setToolTipText("Move up selected line");
		bUp.setBounds(5, dy, BUTTON_W, BUTTON_H);
		bUp.setFont(new Font("Tahoma", Font.PLAIN, 8));
		bUp.addActionListener(new OGridAction(OGridAction.bUP, this));
		bUp.setText("UP");
		add(bUp);

		bDn = new JButton("D");
		bDn.setBounds(5, 2 * dy, BUTTON_W, BUTTON_H);
		bDn.setFont(new Font("Tahoma", Font.PLAIN, 8));
		bDn.addActionListener(new OGridAction(OGridAction.bDN, this));
		bDn.setText("DOWN");
		add(bDn);

		bNewLine = new JButton("A");
		bNewLine.setBounds(5, 3 * dy, BUTTON_W, BUTTON_H);
		bNewLine.setFont(new Font("Tahoma", Font.PLAIN, 8));
		bNewLine.addActionListener(new OGridAction(OGridAction.bNL, this));
		bNewLine.setText("NEW");
		add(bNewLine);

		bInsertLine = new JButton("I");
		bInsertLine.setBounds(5, 4 * dy, BUTTON_W, BUTTON_H);
		bInsertLine.setFont(new Font("Tahoma", Font.PLAIN, 8));
		bInsertLine.addActionListener(new OGridAction(OGridAction.bIL, this));
		bInsertLine.setText("INS");
		add(bInsertLine);

		bDeleteLine = new JButton("D");
		bDeleteLine.setBounds(5, 5 * dy, BUTTON_W, BUTTON_H);
		bDeleteLine.setFont(new Font("Tahoma", Font.PLAIN, 8));
		bDeleteLine.addActionListener(new OGridAction(OGridAction.bDL, this));
		bDeleteLine.setText("DEL");
		add(bDeleteLine);
	} // showButtons

	private Object makeObj(final String item) {
		return new Object() {
			@Override
			public String toString() {
				return item;
			}
		};
	} // makeObj

	public void configureTableColumns() {
		for (int i = 0; i < tmodel.getColumnCount(); i++) {
			ColumnConfiguration cc = tmodel.getColumnConfiguration(i);
			TableColumn tc = table.getColumnModel().getColumn(i);
			tc.setPreferredWidth(cc.getWidth());
			if (cc.hasNonDefaultGraphicalProperty()) {
				System.out.println("Setting cell renderer for " + cc.getTitle());
				tc.setCellRenderer(new OGridCellRenderer(this));
			}
			if (cc.getFieldType().equals(ColumnConfiguration.FieldType.CHECKBOX)) {
				tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));
			}
			if ((cc.getFieldType().equals(ColumnConfiguration.FieldType.COMBO))
					|| (cc.getFieldType().equals(ColumnConfiguration.FieldType.ID_TEXT_COMBO))) {
				JComboBox cb = new JComboBox();
				List<DataItem> data = cc.getcBoxDataSource().getItemList();
				for (DataItem di : data) {
					cc.addDataItem(di);
					cb.addItem(makeObj(di.value));
				}
				tc.setCellEditor(new DefaultCellEditor(cb));
			}
		} // for each column
	} // configureTableColumns

	private void assemblyTable() {
		table = new javax.swing.JTable(tmodel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(80, 10, this.getWidth() - 90, this.getHeight() - 10);
		tableScrollPane = new JScrollPane(table);
		tableScrollPane.setBounds(80, 10, this.getWidth() - 90, this.getHeight() - 10);
		table.setFillsViewportHeight(true);
		addListenersToTable(table);
		configureTableColumns();
		add(tableScrollPane);
	} // assemblyTable

	public void render() {
		assemblyTable();
		showButtons();
	} // render

	public void renderAndDisableButtons() {
		table = new javax.swing.JTable(tmodel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(5, 10, this.getWidth(), this.getHeight());
		tableScrollPane = new JScrollPane(table);
		tableScrollPane.setBounds(5, 10, this.getWidth() - 90, this.getHeight() - 10);
		table.setFillsViewportHeight(true);
		addListenersToTable(table);
		configureTableColumns();
		add(tableScrollPane);
	} // render

	private void addListenersToTable(JTable table) {

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				int row = e.getFirstIndex();
				System.out.println("MOUSE LISTENER: hey, look at that row (" + row + ")!");
			}
		});

		table.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					JTable target = (JTable) e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					doubleClickListener.event(row, column);
				}
			}
		});		
	}
	
} // OGrid
