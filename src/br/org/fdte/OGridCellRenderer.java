
package br.org.fdte;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class OGridCellRenderer extends DefaultTableCellRenderer {
    private OGrid oGrid;

    OGridCellRenderer (OGrid ogrid) {
        this.oGrid = ogrid;
    } // constructor

    @Override
    public Component getTableCellRendererComponent (JTable table,
             Object value, boolean isSelected, boolean hasFocus,
             int row, int column) {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);

        System.out.println( "Rendering r,c" + row + " " + column);

        ColumnConfiguration cc = oGrid.getOGridTableModel().getColumnConfiguration(column);
        if (cc == null) {
            //System.out.println( "no CC for r,c=" + row + " " + column);
            return cell;
        }
        //System.out.println( "affecting rendering of cell " + row + " " + column);

        Font f = cc.getFont();
        if ( f != null) {
            cell.setFont(f);
        }
        Color c = cc.getColor();
        if ( c != null) {
            cell.setForeground(c);
        }
        return cell;
    } //getTableCellRendererComponent
} //OGridCellRenderer

