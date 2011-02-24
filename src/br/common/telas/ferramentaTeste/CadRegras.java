package br.common.telas.ferramentaTeste;

import br.org.fdte.ColumnConfiguration;
import br.org.fdte.ComboBoxDataSource;
import br.org.fdte.ComboBoxRelacaoEntao;
import br.org.fdte.ComboBoxRelacaoSe;
import br.org.fdte.OGrid;
import br.org.fdte.OGridTableModel;
import br.org.fdte.dao.AtributoDAO;
import br.org.fdte.dao.DocumentoDAO;
import br.org.fdte.dao.RegraDAO;
import br.org.fdte.dao.RelacaoEntaoDAO;
import br.org.fdte.dao.RelacaoSeDAO;
import br.org.fdte.persistence.Atributo;
import br.org.fdte.persistence.TemplateDocumento;
import br.org.fdte.persistence.Regra;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class CadRegras extends javax.swing.JPanel { // implements AtualizacaoTela {

    public class ComboBoxGeneric implements ComboBoxDataSource {

        List<DataItem> list = new ArrayList<DataItem>();

        @Override
        public List<DataItem> getItemList() {
            return list;
        }

        public void setItemList(List<Atributo> listAttr) {
            for (Atributo atr : listAttr) {
                list.add(new DataItem(atr.getId(), atr.getTag()));
            }
        }
    }
    private static TemplateDocumento doc;
    private OGrid g;
    private CadDocumento telaCadDoc;

    /** Creates new form CadRegras */
    public CadRegras(CadDocumento cadDoc) {
        telaCadDoc = cadDoc;

        initComponents();

        setBounds(200, 0, 900, 600);

        g = new OGrid();
        g.setBounds(0, 80, 800, 400);
        // SEQ
        ColumnConfiguration c = new ColumnConfiguration();
        c.setTitle("id");
        c.setFieldType(ColumnConfiguration.FieldType.SEQ);
        c.setEditable(false);
        c.setWidth(20);
        g.addColumn(c);

        // ID_TEXT_COMBO
        c = new ColumnConfiguration();
        c.setTitle("Se");
        c.setFieldType(ColumnConfiguration.FieldType.ID_TEXT_COMBO);
        c.setcBoxDataSource(new ComboBoxGeneric());
        c.setWidth(100);
        g.addColumn(c);

        // ID_TEXT_COMBO
        c = new ColumnConfiguration();
        c.setTitle("Relação");
        c.setFieldType(ColumnConfiguration.FieldType.ID_TEXT_COMBO);
        c.setcBoxDataSource(new ComboBoxRelacaoSe());
        c.setWidth(100);
        g.addColumn(c);


        c = new ColumnConfiguration();
        c.setTitle("Valor");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(100);
        g.addColumn(c);

        // ID_TEXT_COMBO
        c = new ColumnConfiguration();
        c.setTitle("Então");
        c.setFieldType(ColumnConfiguration.FieldType.ID_TEXT_COMBO);
        c.setcBoxDataSource(new ComboBoxGeneric());
        c.setWidth(100);
        g.addColumn(c);

        // ID_TEXT_COMBO
        c = new ColumnConfiguration();
        c.setTitle("Relação");
        c.setFieldType(ColumnConfiguration.FieldType.ID_TEXT_COMBO);
        c.setcBoxDataSource(new ComboBoxRelacaoEntao());
        c.setWidth(100);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Valor");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(100);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Comentário");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(200);
        g.addColumn(c);

        // render table
        g.render();
        g.setVisible(true);

        add(g);
    }

    public void setDocumento(TemplateDocumento doc) {
        CadRegras.doc = doc;

        jTextField1.setText(doc.getNome());

        //Limpar registros que estejam no grid de Valores
        OGridTableModel tableModel = (OGridTableModel) g.getOGridTableModel();
        int nRow = tableModel.getRowCount();
        for (int i = 0; i < nRow; i++) {
            tableModel.deleteLine(i);
        }

        ComboBoxGeneric cba = new ComboBoxGeneric();
        cba.setItemList((List<Atributo>) doc.getAtributoCollection());
        g.getOGridTableModel().getColumnConfiguration(1).setcBoxDataSource(cba);
        g.getOGridTableModel().getColumnConfiguration(4).setcBoxDataSource(cba);
        g.configureTableColumns();

        List<Regra> lstRegra = DocumentoDAO.getRegras(doc);

        Collections.sort(lstRegra, new Comparator() {
            @Override
            public int compare(Object obj1, Object obj2) {
                Regra r1 = (Regra) obj1;
                Regra r2 = (Regra) obj2;

                if (r1.getOrderId() < r2.getOrderId()) {
                    return -1;
                } else if (r1.getOrderId() == r2.getOrderId()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });


        if (lstRegra != null) {
            Vector<Object> vectorRegra = null;
            Vector<Vector> vector = new Vector();

            for (Regra regra : lstRegra) {
                vectorRegra = new Vector();
                vectorRegra.add(regra.getOrderId());
                vectorRegra.add(regra.getSeAtributo());
                vectorRegra.add(regra.getSeRelacao().getId());
                vectorRegra.add(regra.getSeValor());
                vectorRegra.add(regra.getEntaoAtributo());
                vectorRegra.add(regra.getEntaoRelacao().getId());
                vectorRegra.add(regra.getEntaoValor());
                vectorRegra.add(regra.getComentario());

                vector.addElement(vectorRegra);
            }
            g.fillGrid(vector);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonSalvar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setLayout(null);

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });
        add(jButtonSalvar);
        jButtonSalvar.setBounds(640, 570, 80, 23);

        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });
        add(jButtonSair);
        jButtonSair.setBounds(730, 570, 60, 23);

        jTextField1.setEditable(false);
        jTextField1.setBorder(null);
        add(jTextField1);
        jTextField1.setBounds(10, 30, 100, 14);

        jLabel1.setText("Documento");
        add(jLabel1);
        jLabel1.setBounds(10, 10, 80, 14);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed

        //Salvar regras relacionadas a este documento
        Vector<Regra> regras = new Vector();
        Vector<Vector> dataGrid = g.getGridData();
        Vector<Object> vector = new Vector(8);

        //Remove todas as regras relacionadas ao template de documento;
        DocumentoDAO.removeRegras(doc.getId());

        for (int i = 0; i < 8; i++) {
            vector.add("");
        }

        for (int index = 0; index < dataGrid.size(); index++) {

            Regra regra = new Regra();

            for (int indexC = 0; indexC < dataGrid.get(index).size(); indexC++) {
                vector.set(indexC, dataGrid.get(index).get(indexC));
            }
            regra.setOrderId(Long.parseLong(vector.get(0).toString()));
            regra.setSeAtributo(AtributoDAO.get((Long) vector.get(1)).getId());
            regra.setSeRelacao(RelacaoSeDAO.get((Long) vector.get(2)));
            regra.setSeValor((String) vector.get(3));
            regra.setEntaoAtributo(AtributoDAO.get((Long) vector.get(4)).getId());
            regra.setEntaoRelacao(RelacaoEntaoDAO.get((Long) vector.get(5)));
            regra.setEntaoValor((String) vector.get(6));
            regra.setIdTemplateDocumento(doc);
            regra.setComentario((String) vector.get(7));
            regras.add(regra);
        }

        for (int i = 0; i < regras.size(); i++) {
            RegraDAO.save(regras.get(i));
        }
}//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        //this.setVisible(false);
        telaCadDoc.setEnabledPanelFilho(false);
}//GEN-LAST:event_jButtonSairActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
