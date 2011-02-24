package br.common.telas.ferramentaTeste;

import br.org.fdte.AtualizacaoTela;
import br.org.fdte.ColumnConfiguration;
import br.org.fdte.ComboBoxCasoTeste;
import br.org.fdte.ComboBoxCasoTesteSeq;
import br.org.fdte.OGrid;
import br.org.fdte.OGridTableModel;
import br.org.fdte.persistence.CaracterizacaoTesteValidacao;
import br.org.fdte.persistence.SuiteTesteValidacao;
import br.org.fdte.persistence.SuiteValidacaoTesteValidacao;
import br.org.servicos.SuiteServico;
import java.awt.event.ItemEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

public class CadSuiteTeste extends javax.swing.JPanel implements AtualizacaoTela {

    private OGrid g;
    private JFramePrincipal jFramePrincipal;

    /** Creates new form CadSuiteTesteValidacao */
    public CadSuiteTeste(JFramePrincipal framePrincipal) {
        this.jFramePrincipal = framePrincipal;

        setBounds(200, 0, jFramePrincipal.PANEL_WIDTH, jFramePrincipal.PANEL_HEIGHT);
        // OGrid test
        g = new OGrid();

        //g.setBounds(0, 80, 800, 400);
        g.setBounds(0, 80, 1100, 400);
        // SEQ
        ColumnConfiguration c = new ColumnConfiguration();
        c.setTitle("id");
        c.setFieldType(ColumnConfiguration.FieldType.SEQ);
        c.setEditable(false);
        c.setWidth(20);
        g.addColumn(c);

        // ID_TEXT_COMBO
        c = new ColumnConfiguration();
        c.setTitle("Caso de Teste");
        c.setFieldType(ColumnConfiguration.FieldType.ID_TEXT_COMBO);
        c.setcBoxDataSource(new ComboBoxCasoTeste());
        //c.setWidth(200);
        c.setWidth(100);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Workflow");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(300);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("TestCase");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(300);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Results");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(300);
        g.addColumn(c);

        // render table
        g.render();
        g.setVisible(true);
        add(g);

        initComponents();
        initCombos();

    }

    public void setRegistro(String nome) {

        //Limpar registros que estejam no grid de Valores
        OGridTableModel tableModel = (OGridTableModel) g.getOGridTableModel();
        int nRow = tableModel.getRowCount();
        for (int i = 0; i < nRow; i++) {
            tableModel.deleteLine(i);
        }

        if (nome.equalsIgnoreCase("") == true) {
            jComboBoxTipo.setEnabled(true);
            limparRegistro();
        } else {
            jComboBoxTipo.setEnabled(false);
            popularRegistro(nome);
        }
    }

    @Override
    public void atualizarTela() {
        ComboBoxCasoTeste cbt = new ComboBoxCasoTeste();
        g.getOGridTableModel().getColumnConfiguration(1).setcBoxDataSource(cbt);
        g.configureTableColumns();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNome = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jlblTipo = new javax.swing.JLabel();
        jComboBoxTipo = new javax.swing.JComboBox();
        jButtonSalvar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();

        setLayout(null);

        lblNome.setText("Nome");
        add(lblNome);
        lblNome.setBounds(10, 10, 40, 14);
        add(jTextFieldName);
        jTextFieldName.setBounds(10, 30, 100, 20);

        jlblTipo.setText("Tipo");
        add(jlblTipo);
        jlblTipo.setBounds(130, 10, 40, 14);

        jComboBoxTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxTipoItemStateChanged(evt);
            }
        });
        add(jComboBoxTipo);
        jComboBoxTipo.setBounds(130, 30, 100, 20);

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });
        add(jButtonSalvar);
        jButtonSalvar.setBounds(610, 560, 80, 23);

        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });
        add(jButtonSair);
        jButtonSair.setBounds(700, 560, 70, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        //setVisible(false);
        jFramePrincipal.disablePanels();
    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed

        if (jTextFieldName.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Escolha um nome");
            return;
        }

        SuiteServico servicoSuite = new SuiteServico();
        SuiteTesteValidacao suite = new SuiteTesteValidacao();
        suite.setNome(jTextFieldName.getText());

        List<SuiteValidacaoTesteValidacao> list = popularGridCasoTeste(suite);

        boolean isNewDocument = servicoSuite.save(suite, list);

        //se foi insercao;
        if (isNewDocument) {
            jFramePrincipal.addNode(jTextFieldName.getText());
            JOptionPane.showMessageDialog(this, "Suite Validacao" + jTextFieldName.getText() + " criada");
        } else {
            JOptionPane.showMessageDialog(this, "Suite Validacao " + jTextFieldName.getText() + " atualizada");
        }


    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jComboBoxTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Item selecionado." + jComboBoxTipo.getSelectedItem());
            int index = jComboBoxTipo.getSelectedIndex();
            if (index == 0 || index == 1) {
                OGridTableModel tableModel = (OGridTableModel) g.getOGridTableModel();
                ColumnConfiguration c = tableModel.getColumnConfiguration(1);
                //c.setcBoxDataSource(null);
                switch (index) {
                    case 0:
                        c.setcBoxDataSource(new ComboBoxCasoTeste());
                        break;
                    case 1:
                        c.setcBoxDataSource(new ComboBoxCasoTesteSeq());
                        break;
                }
                g.configureTableColumns();
            }
        }
    }//GEN-LAST:event_jComboBoxTipoItemStateChanged

    private void popularRegistro(String nome) {

        SuiteServico suiteServico = new SuiteServico();

        List<SuiteValidacaoTesteValidacao> listSVCTV = suiteServico.getAllSuiteValTesteVal(nome);

        jTextFieldName.setText(nome);
        jTextFieldName.setEditable(false);
        jComboBoxTipo.setSelectedItem("validação");

        Collections.sort(listSVCTV, new Comparator() {
            @Override
            public int compare(Object obj1, Object obj2) {
                SuiteValidacaoTesteValidacao r1 = (SuiteValidacaoTesteValidacao) obj1;
                SuiteValidacaoTesteValidacao r2 = (SuiteValidacaoTesteValidacao) obj2;

                if (r1.getOrderId() < r2.getOrderId()) {
                    return -1;
                } else if (r1.getOrderId() == r2.getOrderId()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        Vector<Object> vectorCasoTeste = null;
        Vector<Vector> vector = new Vector();

        for (SuiteValidacaoTesteValidacao svctv : listSVCTV) {
            vectorCasoTeste = new Vector();
            vectorCasoTeste.add(svctv.getOrderId());
            vectorCasoTeste.add(svctv.getCaracterizacaoTesteValidacao().getId());
            vectorCasoTeste.add(svctv.getWorkflow());
            vectorCasoTeste.add(svctv.getTestCase());
            vectorCasoTeste.add(svctv.getResult());

            vector.addElement(vectorCasoTeste);
        }
        g.fillGrid(vector);

    }

    private List<SuiteValidacaoTesteValidacao> popularGridCasoTeste(SuiteTesteValidacao suite) {


        Vector<SuiteValidacaoTesteValidacao> vectorSuiteValTstVal = new Vector();
        Vector<Vector> dataGrid = g.getGridData();
        Vector<Object> vector = new Vector(5);

        for (int index = 0; index < dataGrid.size(); index++) {

            SuiteValidacaoTesteValidacao svct = new SuiteValidacaoTesteValidacao();
            vector.clear();

            for (int i = 0; i < 5; i++) {
                vector.add(i, null);
            }

            for (int indexC = 0; indexC < dataGrid.get(index).size(); indexC++) {
                vector.set(indexC, dataGrid.get(index).get(indexC));
            }

            if (vector.get(0) != null) {
                svct.setOrderId(Long.parseLong(vector.get(0).toString()));
            }
            if (vector.get(1) != null) {
                CaracterizacaoTesteValidacao caractVal = new CaracterizacaoTesteValidacao((Long) vector.get(1));
                svct.setCaracterizacaoTesteValidacao(caractVal);
                // svct.setCaracterizacaoTesteValidacao(CaracterizacaoTstValidacaoDAO.getCaracterizacaoTesteValidacao(((Long)vector.get(1)).intValue()));
            }
            if (vector.get(2) != null) {
                svct.setWorkflow(vector.get(2).toString());
            }
            if (vector.get(3) != null) {
                svct.setTestCase(vector.get(3).toString());
            }
            if (vector.get(4) != null) {
                svct.setResult(vector.get(4).toString());
            }

            vectorSuiteValTstVal.add(svct);
        }

        return vectorSuiteValTstVal;

    }

    private void limparRegistro() {
        jTextFieldName.setText("");
        jTextFieldName.setEditable(true);
        jComboBoxTipo.setSelectedIndex(0);
    }

    private void initCombos() {
        jComboBoxTipo.removeAllItems();
        jComboBoxTipo.addItem("validação");
        jComboBoxTipo.addItem("sequencia");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JComboBox jComboBoxTipo;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JLabel jlblTipo;
    private javax.swing.JLabel lblNome;
    // End of variables declaration//GEN-END:variables
}
