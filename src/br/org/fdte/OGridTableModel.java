
package br.org.fdte;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class OGridTableModel extends AbstractTableModel {

    private Vector<ColumnConfiguration> cols;
    private Vector<Vector> rawData = new Vector();

    OGridTableModel() {
        cols = new Vector<ColumnConfiguration>();
    } // constructor

    @Override
    public Class getColumnClass(int column) {
        Class c = String.class;
        if ( cols.elementAt(column).getFieldType().equals(ColumnConfiguration.FieldType.CHECKBOX))
            c = Boolean.class;
        return c;
    } // getlColumnClass

    void setFromVector(Vector value, int r) {
        for(int i=0; i < value.size(); i++) {
            setValueAt(value.elementAt(i), r, i);
        }
    } // setFromVector

    Vector makeCopy(int r) {
        Vector dest = new Vector();
        Vector src = rawData.elementAt(r);
        if (src == null) {
            return null;
        }
        for (int i = 0; i < src.size(); i++) {
            dest.add(src.elementAt(i));
        }
        return dest;
    } // makeCopy

    void moveLineUp(int r) {
       if ( r <= 0)
           return;
       Vector v = makeCopy(r-1);
       copy(r, r-1);
       setFromVector(v, r);
       reseq();
    } // moveLineUp


    void moveLineDown(int r) {
        if (r >= rawData.size()-1)
            return;
        Vector v = makeCopy(r+1);
        copy(r, r+1);
        setFromVector(v, r);
        reseq();
    } // moveLineDown
    
    Vector<Vector> getGridData() {
       Vector<Vector> data = new Vector<Vector>();
       for (int r = 0; r < rawData.size(); r++) {
           Vector rowdata = rawData.elementAt(r);
           Vector line = new Vector();
           for (int c = 0; c < rowdata.size(); c++) {
               Object value = rowdata.elementAt(c);
               if ( ColumnConfiguration.FieldType.ID_TEXT_COMBO.equals(cols.elementAt(c).getFieldType())) {
                   value = cols.elementAt(c).getIdForText(value);
               }
               line.add(value);
           } // for each column
           data.add(line);
       } // for each row
       return data;
    } // getGridData

    void fillGrid(Vector<Vector> data) {
       for (int r = 0; r < data.size(); r++) {
           Vector rowdata = data.elementAt(r);
           for (int c = 0; c < rowdata.size(); c++) {
               Object value = rowdata.elementAt(c);
               if ( ColumnConfiguration.FieldType.ID_TEXT_COMBO.equals(cols.elementAt(c).getFieldType())) {
                   value = cols.elementAt(c).getTextForId(value);
               }
               setValueAt( value, r, c);
           } // for each column
       } // for each row
    } // fillGrid

    public void deleteLine(int row) {
      int rows = rawData.size();
      if (rows == 0)
          return;
      for (int i = row; i < rows; i++) {
          copy(i+1, i);
      }
      reseq();
      rawData.setSize(rows-1);
      redraw();
    } // deleteLine

    private void redraw() {
        for (int r = 0; r < rawData.size()-1; r++) {
            for (int c = 0; c < cols.size(); c++) {
               fireTableCellUpdated(r, c);
            }
        }
    } // redraw
    
    private void clearRow(int row) {
        for (int col=0; col < cols.size(); col++) {
            setValueAt(null, row, col);
        } // each col
    } // removeData
    
    private void copy(int from, int to) {
        for (int col=0; col < cols.size(); col++) {
            Object v = getValueAt(from, col);
            setValueAt(v, to, col);
        } // each col
    } // copy

    void reseq() {
        int seqField = -1;
        for (int i=0; i < cols.size(); i++) {
            if ( cols.elementAt(i).getFieldType().equals(ColumnConfiguration.FieldType.SEQ)) {
                seqField = i;
                break;
            }
        } // for each column definition in the search for a SEQ field

        System.out.println(" seqField " + seqField);

        int seq = 0;
        int rows = rawData.size();
        for (int row=0; row < rows; row++) {
            Object v = getValueAt(row, seqField);
            setValueAt(new Integer(seq), row, seqField);
            seq++;
        } // for each row
    } // reseq

    void insertLine(int row) {
      int last = rawData.size()-1;
      for (int i = last; i >= row; i--) {
          copy(i, i+1);
      } // each row
      clearRow(row);
      reseq();
    } // insertLine

    @Override
    public String getColumnName(int col) {
        return cols.elementAt(col).getTitle();
    } // getColumnName

    @Override
    public boolean isCellEditable(int row, int col) {
        return cols.elementAt(col).isEditable();
    } // isCellEditable

    private void fillSeqField(int row) {
        for (int i=0; i < cols.size(); i++) {
            if ( cols.elementAt(i).getFieldType().equals(ColumnConfiguration.FieldType.SEQ)) {
                Vector lin = rawData.elementAt(row);
                lin.add(i, new Integer(row));
                break;
            }
        }
    } // fillSeqField

    @Override
    public void setValueAt(Object value, int rowp, int col) {
        int row = rowp;
        if (row < 0)
            row = 0;
        Vector lin = null;
        try {
           lin = rawData.elementAt(row);
           if (lin == null) {
               lin = new Vector();
               rawData.add(row, lin);
               fillSeqField(row);
           }
        } catch(Exception e) {
            lin = new Vector();
            //if (rawData.size() < row + 1) {
            //    rawData.setSize(row+1);
            //}
            rawData.add(row, lin);
            fillSeqField(row);
        }
        if (lin.size() < col+1) {
            lin.setSize(col+1);
        }
        lin.set(col, value);

        for (int i=0; i < cols.size(); i++) {
           fireTableCellUpdated(row, i);
        }
    } // setValueAt

    @Override
    public int getColumnCount() {
        return cols.size();
    }

    @Override
    public int getRowCount() {
        return rawData.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
          Vector lin = rawData.elementAt(rowIndex);
          return lin.elementAt(columnIndex);
        } catch(Exception e) {
            return null;
        }
    } // getValueAt

    void addColumn(ColumnConfiguration conf) {
        // TODO: verificar se a configuracao esta´ correta;
        // por exemplo, combobox exige DataSource
        cols.add(conf);
    } // addColumn

    public ColumnConfiguration getColumnConfiguration(int i) {
        try {
          return cols.elementAt(i);
        } catch (Exception e) {
            return null;
        }
    } // getColumnConfiguration
    

} //OGridTableModel

