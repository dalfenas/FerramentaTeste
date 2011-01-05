package br.common.telas.ferramentaTeste;

import br.org.fdte.AtualizacaoTela;
import br.org.fdte.ColumnConfiguration;
import br.org.fdte.ComboBoxCasoTeste;
import br.org.fdte.ComboBoxCasoTesteSeq;
import br.org.fdte.OGrid;
import br.org.fdte.OGridTableModel;
import br.org.fdte.dao.CaracterizacaoTstSequenciaDAO;
import br.org.fdte.dao.CaracterizacaoTstValidacaoDAO;
import br.org.fdte.dao.SuiteSeqCarTstSeqDAO;
import br.org.fdte.dao.SuiteTesteSequenciaDAO;
import br.org.fdte.dao.SuiteTesteValidacaoDAO;
import br.org.fdte.dao.SuiteValCarTstValDAO;
import br.org.fdte.persistence.SuiteSequenciaPorCaracterizacaoTesteSequencia;
import br.org.fdte.persistence.SuiteTesteSequencia;
import br.org.fdte.persistence.SuiteTesteValidacao;
import br.org.fdte.persistence.SuiteValidacaoTesteValidacao;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

public class CadSuiteTeste extends javax.swing.JPanel implements AtualizacaoTela {

    private OGrid g;

    private JFramePrincipal jFramePrincipal;   

    /** Creates new form CadSuiteTesteValidacao */
    public CadSuiteTeste(JFramePrincipal framePrincipal) {
        this.jFramePrincipal = framePrincipal;        

       
        setBounds(200,0,jFramePrincipal.PANEL_WIDTH,jFramePrincipal.PANEL_HEIGHT);
        // OGrid test
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
        c.setTitle("Caso de Teste");
        c.setFieldType(ColumnConfiguration.FieldType.ID_TEXT_COMBO);      
        c.setcBoxDataSource(new ComboBoxCasoTeste());
        c.setWidth(200);
        g.addColumn(c);

        c= new ColumnConfiguration();
        c.setTitle("Comentário");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(400);
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
        OGridTableModel tableModel = (OGridTableModel)g.getOGridTableModel();
        int nRow =  tableModel.getRowCount();
        for (int i = 0; i < nRow; i++)
            tableModel.deleteLine(i);

        if (nome.equalsIgnoreCase("") == true) {
            jComboBoxTipo.setEnabled(true);
            limparRegistro();
        }
        else {
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
         int retorno = 0;

         if (jTextFieldName.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Escolha um nome");
            return;
         }

         if(jComboBoxTipo.getSelectedIndex() == 0) {
             SuiteTesteValidacao suiteTstVal = SuiteTesteValidacaoDAO.getSuiteTesteValidacao(jTextFieldName.getText());
             if (null == suiteTstVal) {
                 suiteTstVal = new SuiteTesteValidacao();
             }
             else {
                 if (JOptionPane.YES_OPTION !=
                    JOptionPane.showConfirmDialog(this,"Suite de Teste de Validação " + jTextFieldName.getText() + " já existe. Deseja sobrescrevê-la?","Sobrescrever entidade",2))
                 return;
             }
             suiteTstVal.setNome(jTextFieldName.getText());
             retorno = SuiteTesteValidacaoDAO.save(suiteTstVal);
         }
         else {
             SuiteTesteSequencia suiteTstSeq = SuiteTesteSequenciaDAO.getSuiteTesteSequencia(jTextFieldName.getText());
             if (null == suiteTstSeq) {
                 suiteTstSeq = new SuiteTesteSequencia();
             }
             else {
                 if (JOptionPane.YES_OPTION !=
                    JOptionPane.showConfirmDialog(this,"Suite de Teste de Sequência " + jTextFieldName.getText() + " já existe. Deseja sobrescrevê-la?","Sobrescrever entidade",2))
                 return;
             }
             suiteTstSeq.setNome(jTextFieldName.getText());
             retorno = SuiteTesteSequenciaDAO.save(suiteTstSeq);
         }
        

        popularGridCasoTeste(jComboBoxTipo.getSelectedIndex(), retorno);
        jFramePrincipal.atualizarCampos(entidadeSuiteValidacao);

         //se foi insercao de suite o retorno é 0;
         if (retorno == 0)
             jFramePrincipal.addNode(jTextFieldName.getText());
         else {
           JOptionPane.showMessageDialog(this,"Suite de Validação " + jTextFieldName.getText() + " atualizada");
         }
    }//GEN-LAST:event_jButtonSalvarActionPerformed
 
    private void jComboBoxTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxTipoItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            System.out.println("Item selecionado." + jComboBoxTipo.getSelectedItem());
            int index = jComboBoxTipo.getSelectedIndex();
            if (index == 0 || index == 1) {
               OGridTableModel tableModel = (OGridTableModel)g.getOGridTableModel();
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

      SuiteTesteValidacao suite = SuiteTesteValidacaoDAO.getSuiteTesteValidacao(nome);
      SuiteTesteSequencia suiteSeq = null;

      //eh uma suite de sequencia
      if (suite == null)
          suiteSeq = SuiteTesteSequenciaDAO.getSuiteTesteSequencia(nome);
      //eh uma suite de validacao
      if (suite != null) {          
          List<SuiteValidacaoTesteValidacao> listSVCTV = SuiteValCarTstValDAO.getSuiteVal(suite.getId());
         
          jTextFieldName.setText(suite.getNome());
          jTextFieldName.setEditable(false);
          jComboBoxTipo.setSelectedItem("validação");

          Vector <Object> vectorCasoTeste = null;
          Vector <Vector> vector = new Vector();

          for (SuiteValidacaoTesteValidacao svctv : listSVCTV) {
               vectorCasoTeste = new Vector();
               vectorCasoTeste.add(svctv.getOrderId());
               vectorCasoTeste.add(svctv.getCaracterizacaoTesteValidacao().getId());
               vectorCasoTeste.add(svctv.getCaracterizacaoTesteValidacao().getComentario());
               vector.addElement(vectorCasoTeste);
          }
          g.fillGrid(vector);
          //g.render();
           
      }
      else {
          if (suiteSeq != null) {
              List<SuiteSequenciaPorCaracterizacaoTesteSequencia> listSSCTS = SuiteSeqCarTstSeqDAO.getSuiteSeq(suiteSeq.getId());

              jTextFieldName.setText(suiteSeq.getNome());
              jTextFieldName.setEditable(false);
              jComboBoxTipo.setSelectedItem("sequencia");

              Vector <Object> vectorCasoTeste = null;
              Vector <Vector> vector = new Vector();

              for (SuiteSequenciaPorCaracterizacaoTesteSequencia sscts : listSSCTS) {
                   vectorCasoTeste = new Vector();
                   vectorCasoTeste.add(sscts.getOrderId());
                   vectorCasoTeste.add(sscts.getCaracterizacaoTesteSequencia().getId());
                   vectorCasoTeste.add(sscts.getCaracterizacaoTesteSequencia().getComentario());
                   vector.addElement(vectorCasoTeste);
               }
               g.fillGrid(vector);
              // g.render();
           }          
      }
    }

    private void popularGridCasoTeste(int index, int retorno) {

        if (index == 0)  {
        //em relação ao grid de caso de teste
         switch (retorno) {
          //insercao de suite
            case 0:
                //adicionar os dados do grid de casos de teste
                popularGridCasoTeste(SuiteTesteValidacaoDAO.getSuiteTesteValidacao(jTextFieldName.getText()));
            break;
            //update de suite de teste
            case 1:
                //primeiro remove os dados do grid de casos de teste relacionados a esta suite
                SuiteValCarTstValDAO.delete(SuiteTesteValidacaoDAO.getSuiteTesteValidacao(jTextFieldName.getText()));
                //adiciona os dados do grid de valores
                popularGridCasoTeste(SuiteTesteValidacaoDAO.getSuiteTesteValidacao(jTextFieldName.getText()));
            break;
         }
        }
        if (index == 1)  {
        //em relação ao grid de caso de teste
         switch (retorno) {
          //insercao de suite
            case 0:
                //adicionar os dados do grid de casos de teste
                popularGridCasoTeste(SuiteTesteSequenciaDAO.getSuiteTesteSequencia(jTextFieldName.getText()));
            break;
            //update de suite de teste
            case 1:
                //primeiro remove os dados do grid de casos de teste relacionados a esta suite
                SuiteSeqCarTstSeqDAO.delete(SuiteTesteSequenciaDAO.getSuiteTesteSequencia(jTextFieldName.getText()));
                //adiciona os dados do grid de valores
                popularGridCasoTeste(SuiteTesteSequenciaDAO.getSuiteTesteSequencia(jTextFieldName.getText()));
            break;
         }
        }

    }

    private void popularGridCasoTeste(SuiteTesteValidacao suite) {

       Vector<SuiteValidacaoTesteValidacao> vectorSuiteValTstVal = new Vector();
       Vector<Vector> dataGrid = g.getGridData();
       Vector <Object> vector = new Vector(3);

        for (int index = 0; index < dataGrid.size(); index++) {

            SuiteValidacaoTesteValidacao svct = new SuiteValidacaoTesteValidacao();
            vector.clear();

            for(int i = 0; i < 3; i++) {
                vector.add(i, null);
            }

            for (int indexC = 0; indexC < dataGrid.get(index).size(); indexC++){
                vector.set(indexC,dataGrid.get(index).get(indexC));
            }

            if (vector.get(0) != null)
                svct.setOrderId(Long.parseLong(vector.get(0).toString()));
            if (vector.get(1) != null)
                svct.setCaracterizacaoTesteValidacao(CaracterizacaoTstValidacaoDAO.getCaracterizacaoTesteValidacao(((Long)vector.get(1)).intValue()));

            svct.setSuiteTesteValidacao(SuiteTesteValidacaoDAO.getSuiteTesteValidacao(suite.getId().intValue()));

            vectorSuiteValTstVal.add(svct);
        }

        for (int i = 0; i<vectorSuiteValTstVal.size(); i++) {
            SuiteValCarTstValDAO.save(vectorSuiteValTstVal.get(i));
        }
    }

    private void popularGridCasoTeste(SuiteTesteSequencia suite) {

       Vector<SuiteSequenciaPorCaracterizacaoTesteSequencia> vectorSuiteTstSeq = new Vector();
       Vector<Vector> dataGrid = g.getGridData();
       Vector <Object> vector = new Vector(3);


        for (int index = 0; index < dataGrid.size(); index++) {

            SuiteSequenciaPorCaracterizacaoTesteSequencia svct = new SuiteSequenciaPorCaracterizacaoTesteSequencia();
            vector.clear();
            for(int i = 0; i < 3; i++) {
                vector.add(i, null);
            }

            for (int indexC = 0; indexC < dataGrid.get(index).size(); indexC++){
                vector.set(indexC,dataGrid.get(index).get(indexC));
            }

            if (vector.get(0) != null)
                svct.setOrderId(Long.parseLong(vector.get(0).toString()));

            if (vector.get(1) != null)
                svct.setCaracterizacaoTesteSequencia(CaracterizacaoTstSequenciaDAO.getCaracterizacaoTesteSequencia(((Long)vector.get(1)).intValue()));

            svct.setSuiteTesteSequencia(SuiteTesteSequenciaDAO.getSuiteTesteSequencia(suite.getId().intValue()));

            vectorSuiteTstSeq.add(svct);
        }

        for (int i = 0; i<vectorSuiteTstSeq.size(); i++) {
            SuiteSeqCarTstSeqDAO.save(vectorSuiteTstSeq.get(i));
        }
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