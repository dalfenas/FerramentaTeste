package br.org.fdte;


import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;


public class MyRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {
    private Icon iconGoldenExecution;
    private Icon iconTestExecution;

    private boolean flagIsSetIcon;

    public MyRenderer() {
        flagIsSetIcon = false;
       
        //iconGoldenExecution = new ImageIcon("golden.gif");
        iconGoldenExecution = new ImageIcon("J0222015.gif");
        iconTestExecution = new ImageIcon("printer.gif");
    }

    @Override
    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
  

        if (value instanceof ExecutionTreeNode) {
            if (((ExecutionTreeNode)value).isGolden()){
                setIcon(iconGoldenExecution);
            }
           /* else
                setIcon(iconTestExecution);*/
        }
        return this;
    }       
    
    public void setIcon() {
        this.setIcon(iconGoldenExecution);
    }

}

