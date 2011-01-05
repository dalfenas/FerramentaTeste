package br.org.fdte;

import javax.swing.tree.DefaultMutableTreeNode;

public class ExecutionTreeNode extends DefaultMutableTreeNode{

    Boolean isGolden;

     public ExecutionTreeNode(Boolean isGolden) {
        super();
        this.isGolden = isGolden;
    }

    public ExecutionTreeNode(Object obj, boolean isGolden) {
        super(obj);
        this.isGolden = isGolden;
    }

    public Boolean isGolden() {
        return isGolden;
    }


}
