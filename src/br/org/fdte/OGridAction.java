
package br.org.fdte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OGridAction implements ActionListener {

    private OGrid oGrid;
    private OGridTableModel tmodel;
    private String action;
    static final String bUP = "bUP";
    static final String bDN = "bDN";
    static final String bNL = "bNL";
    static final String bIL = "bIL";
    static final String bDL = "bDL";

    OGridAction(String action, OGrid ogrid){
          oGrid = ogrid;
          tmodel = oGrid.getOGridTableModel();
          this.action = action;
    } // constructor

    public void actionPerformed(ActionEvent e) {
        System.out.println("Invoked " + action);
        if ( bNL.equals(action)) {
            addNewLine();
            return;
        }
        if ( bIL.equals(action)) {
            insertLine();
            return;
        }

        if ( bDL.equals(action)) {
            deleteLine();
            return;
        }

        if ( bDN.equals(action)) {
            moveLineDown();
            return;
        }

        if ( bUP.equals(action)) {
            moveLineUp();
            return;
        }


    } //actionPerformed

    private void moveLineDown() {
        int atLine = oGrid.getCurrentSelectedLine();
        System.out.println("moving down " + atLine);
        if (atLine >= 0) {
            tmodel.moveLineDown(atLine);
        }
        oGrid.unSelectLine();
    } // moveLineDown

    private void moveLineUp() {
        int atLine = oGrid.getCurrentSelectedLine();
        System.out.println("moving up " + atLine);
        if (atLine >= 0) {
            tmodel.moveLineUp(atLine);
        }
        oGrid.unSelectLine();
    } // modeLineUp

    private void addNewLine() {
       int r = tmodel.getRowCount();
       System.out.println("addNew " + r);
       tmodel.setValueAt(null, r, 1);
       //tmodel.reseq();
       oGrid.setFocusOnLine(r);
    } // addNewLine

    private void insertLine() {
        int atLine = oGrid.getCurrentSelectedLine();
        System.out.println("inserting " + atLine);
        if (atLine >= 0) {
            tmodel.insertLine(atLine);
        }
        oGrid.unSelectLine();
    } // insertLine

    private void deleteLine() {
        int atLine = oGrid.getCurrentSelectedLine();
        System.out.println("removing " + atLine);
        if (atLine >= 0) {
            tmodel.deleteLine(atLine);
        }
        oGrid.unSelectLine();
    } // deleteLine
} //OGridAction

