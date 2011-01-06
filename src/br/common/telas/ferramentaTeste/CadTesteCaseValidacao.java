package br.common.telas.ferramentaTeste;

import br.org.fdte.AtualizacaoTela;
import br.org.fdte.ColumnConfiguration;
import br.org.fdte.ComboBoxAtributo;
import br.org.fdte.OGrid;
import br.org.fdte.OGridTableModel;
import br.org.fdte.dao.AtributoDAO;
import br.org.fdte.dao.CaracterizacaoTstValidacaoDAO;
import br.org.fdte.dao.ClasseValidacaoDAO;
import br.org.fdte.dao.DocumentoDAO;
import br.org.fdte.dao.EspecificoDAO;
import br.org.fdte.persistence.CaracterizacaoTesteValidacao;
import br.org.fdte.persistence.ClasseValidacao;
import br.org.fdte.persistence.Especificos;
import br.org.fdte.persistence.TemplateDocumento;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;

public class CadTesteCaseValidacao extends javax.swing.JPanel implements AtualizacaoTela {

    private JFramePrincipal jFramePrincipal;
    private OGrid gridPositivos;
    private OGrid gridNegativos;
    private OGrid gridOpcionais;
    private OGrid gridRepeticoes;

    private final static String actionInserir = "Inserir";     

    /** Creates new form CadTesteCaseValidacao */
    public CadTesteCaseValidacao(JFramePrincipal jFramePrincipal)  {
        this.jFramePrincipal = jFramePrincipal;

        initComponents();
        initCombos();       

        setBounds(200,0,jFramePrincipal.PANEL_WIDTH,jFramePrincipal.PANEL_HEIGHT);
        
        gridPositivos = new OGrid();
        gridNegativos = new OGrid();
        gridOpcionais = new OGrid();
        gridRepeticoes = new OGrid();
        
        gridPositivos.setBounds(0,180,400,190); //x,y,width,height
        gridNegativos.setBounds(400,180,400,190); //x,y,width,height
        gridOpcionais.setBounds(0,435,400,190);
        gridRepeticoes.setBounds(400,435,400,190);
        
        ColumnConfiguration c = new ColumnConfiguration();
        c.setTitle("id");
        c.setFieldType(ColumnConfiguration.FieldType.SEQ);
        c.setEditable(false);
        c.setWidth(20);
        gridPositivos.addColumn(c);
        gridNegativos.addColumn(c);
        gridOpcionais.addColumn(c);
        gridRepeticoes.addColumn(c);
       
        // ID_TEXT_COMBO
        c = new ColumnConfiguration();
        c.setTitle("Atributo");
        c.setFieldType(ColumnConfiguration.FieldType.ID_TEXT_COMBO);
        c.setcBoxDataSource(new ComboBoxAtributo());
        c.setWidth(200);
        gridPositivos.addColumn(c);
        gridNegativos.addColumn(c);
        gridOpcionais.addColumn(c);
        gridRepeticoes.addColumn(c);

        // CHECKBOX
        c = new ColumnConfiguration();
        c.setTitle("Todos");
        c.setFieldType(ColumnConfiguration.FieldType.CHECKBOX);
        //c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(60);
        gridPositivos.addColumn(c);
        gridNegativos.addColumn(c);
        gridOpcionais.addColumn(c);
        gridRepeticoes.addColumn(c);

        c= new ColumnConfiguration();
        c.setTitle("N");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(40);
        gridPositivos.addColumn(c);
        gridNegativos.addColumn(c);
        gridOpcionais.addColumn(c);
        gridRepeticoes.addColumn(c);

        // render table
        gridPositivos.render();
        gridPositivos.setVisible(true);

        gridNegativos.render();
        gridNegativos.setVisible(true);

        gridOpcionais.render();
        gridOpcionais.setVisible(true);

        gridRepeticoes.render();
        gridRepeticoes.setVisible(true);

        add(gridPositivos);
        add(gridNegativos);
        add(gridOpcionais);
        add(gridRepeticoes);        
    }

     public void setRegistro(String nome) {
       // initCombos();

        if (nome.equalsIgnoreCase("") == true) {
            limparRegistro();
        }
        else {
            limparRegistro();
            popularRegistro(nome);
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

        buttonGroupPositivo = new javax.swing.ButtonGroup();
        buttonGroupNegativo = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jcmbDocEntrada = new javax.swing.JComboBox();
        jlblDocEntrada = new javax.swing.JLabel();
        jTxtFieldComentario = new javax.swing.JTextField();
        jButtonSalvar = new javax.swing.JButton();
        jButtonSair = new javax.swing.JButton();
        jrdbNPos = new javax.swing.JRadioButton();
        jrdbTodosRep = new javax.swing.JRadioButton();
        jTextFieldNPos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jlblPositivo = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jrdbNNeg = new javax.swing.JRadioButton();
        jrdbTodosNeg = new javax.swing.JRadioButton();
        jTextFieldNNeg = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jcmbDocSaidaNeg = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jcmbDocSaidaPos = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jrdbTodosPos = new javax.swing.JRadioButton();
        jrdbTodosOp = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jTxtFieldName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jcmbClassSaidaPos = new javax.swing.JComboBox();
        jcmbClassSaidaNeg = new javax.swing.JComboBox();

        setLayout(null);

        jLabel1.setText("Nome");
        add(jLabel1);
        jLabel1.setBounds(0, 0, 34, 14);

        add(jcmbDocEntrada);
        jcmbDocEntrada.setBounds(160, 20, 100, 20);

        jlblDocEntrada.setText("Documento Entrada");
        add(jlblDocEntrada);
        jlblDocEntrada.setBounds(160, 0, 130, 14);
        add(jTxtFieldComentario);
        jTxtFieldComentario.setBounds(0, 70, 300, 20);

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });
        add(jButtonSalvar);
        jButtonSalvar.setBounds(640, 630, 73, 23);

        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });
        add(jButtonSair);
        jButtonSair.setBounds(720, 630, 60, 23);

        buttonGroupPositivo.add(jrdbNPos);
        jrdbNPos.setText("N");
        add(jrdbNPos);
        jrdbNPos.setBounds(0, 120, 33, 20);

        jrdbTodosRep.setSelected(true);
        jrdbTodosRep.setText("Todos");
        add(jrdbTodosRep);
        jrdbTodosRep.setBounds(400, 400, 110, 20);
        add(jTextFieldNPos);
        jTextFieldNPos.setBounds(40, 120, 60, 20);

        jLabel4.setText("max");
        add(jLabel4);
        jLabel4.setBounds(510, 130, 34, 20);

        jlblPositivo.setText("Positivos");
        add(jlblPositivo);
        jlblPositivo.setBounds(0, 100, 100, 20);

        jLabel6.setText("Repetições");
        add(jLabel6);
        jLabel6.setBounds(400, 380, 100, 20);

        buttonGroupNegativo.add(jrdbNNeg);
        jrdbNNeg.setText("N");
        add(jrdbNNeg);
        jrdbNNeg.setBounds(400, 120, 33, 20);

        buttonGroupNegativo.add(jrdbTodosNeg);
        jrdbTodosNeg.setSelected(true);
        jrdbTodosNeg.setText("Todos");
        add(jrdbTodosNeg);
        jrdbTodosNeg.setBounds(400, 140, 110, 20);
        add(jTextFieldNNeg);
        jTextFieldNNeg.setBounds(440, 120, 60, 20);

        jLabel5.setText("max");
        add(jLabel5);
        jLabel5.setBounds(110, 130, 34, 20);

        jLabel7.setText("Específicos");
        add(jLabel7);
        jLabel7.setBounds(410, 420, 70, 20);

        jLabel8.setText("Específicos");
        add(jLabel8);
        jLabel8.setBounds(10, 160, 70, 20);

        jLabel9.setText("Negativos");
        add(jLabel9);
        jLabel9.setBounds(400, 100, 100, 20);

        jLabel10.setText("Específicos");
        add(jLabel10);
        jLabel10.setBounds(410, 160, 70, 20);

        jLabel11.setText("Específicos");
        add(jLabel11);
        jLabel11.setBounds(10, 420, 70, 20);

        jLabel12.setText("Opcionais");
        add(jLabel12);
        jLabel12.setBounds(0, 380, 100, 20);

        add(jcmbDocSaidaNeg);
        jcmbDocSaidaNeg.setBounds(530, 20, 100, 20);

        jLabel2.setText("Documento Saida Negativa");
        add(jLabel2);
        jLabel2.setBounds(530, 0, 170, 14);

        add(jcmbDocSaidaPos);
        jcmbDocSaidaPos.setBounds(340, 20, 100, 20);

        jLabel3.setText("Documento Saida Positiva");
        add(jLabel3);
        jLabel3.setBounds(340, 0, 150, 14);

        buttonGroupPositivo.add(jrdbTodosPos);
        jrdbTodosPos.setSelected(true);
        jrdbTodosPos.setText("Todos");
        add(jrdbTodosPos);
        jrdbTodosPos.setBounds(0, 140, 110, 20);

        jrdbTodosOp.setSelected(true);
        jrdbTodosOp.setText("Todos");
        add(jrdbTodosOp);
        jrdbTodosOp.setBounds(0, 400, 110, 20);

        jLabel13.setText("Comentário");
        add(jLabel13);
        jLabel13.setBounds(0, 50, 100, 14);
        add(jTxtFieldName);
        jTxtFieldName.setBounds(0, 20, 100, 20);

        jLabel14.setText(" Validação Saida Positiva");
        add(jLabel14);
        jLabel14.setBounds(340, 50, 170, 14);

        jLabel15.setText("Validação Saida Negativa");
        add(jLabel15);
        jLabel15.setBounds(530, 50, 170, 14);

        add(jcmbClassSaidaPos);
        jcmbClassSaidaPos.setBounds(340, 70, 100, 20);

        add(jcmbClassSaidaNeg);
        jcmbClassSaidaNeg.setBounds(530, 70, 100, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed

       if (validarCampos() < 0)
            return;

       CaracterizacaoTesteValidacao tstVal = CaracterizacaoTstValidacaoDAO.getCaracterizacaoTesteValidacao(jTxtFieldName.getText());
       if (tstVal == null )
            tstVal = new CaracterizacaoTesteValidacao();
       else {
         if (JOptionPane.YES_OPTION !=
                JOptionPane.showConfirmDialog(this,"Teste de Validação " + jTxtFieldName.getText() + " já existe. Deseja sobrescrevê-lo?","Sobrescrever entidade",2))
             return;
       }

       tstVal = new CaracterizacaoTesteValidacao();
       tstVal.setNome(jTxtFieldName.getText());
       tstVal.setComentario(jTxtFieldComentario.getText());
       tstVal.setDocumentoSaidaNegativa(DocumentoDAO.getDocumento(jcmbDocSaidaNeg.getSelectedItem().toString()));
       tstVal.setDocumentoSaidaPositiva(DocumentoDAO.getDocumento(jcmbDocSaidaPos.getSelectedItem().toString()));
       tstVal.setClasseValidacaoSaidaNegativa(jcmbClassSaidaNeg.getSelectedItem().toString());
       tstVal.setClasseValidacaoSaidaPositiva(jcmbClassSaidaPos.getSelectedItem().toString());
       tstVal.setDocumentoEntrada(DocumentoDAO.getDocumento(jcmbDocEntrada.getSelectedItem().toString()));       

       if (jrdbNPos.isSelected())
           tstVal.setCasosPositivos(Integer.parseInt(jTextFieldNPos.getText()));
       else
           tstVal.setCasosPositivos(0);

       if(jrdbNNeg.isSelected())
           tstVal.setCasosNegativos(Integer.parseInt(jTextFieldNNeg.getText()));
       else
           tstVal.setCasosNegativos(0);

       int retorno = CaracterizacaoTstValidacaoDAO.save(tstVal);

       

       //salvar os grids específicos
       removerGrids();
       salvarGrids();

        //se foi insercao de cadastro de validação o retorno é 0;
        if (retorno == 0) {
            jFramePrincipal.addNode(jTxtFieldName.getText());
            jFramePrincipal.atualizarCampos(AtualizacaoTela.entidadeTesteValidacao);
        }
        else {
           JOptionPane.showMessageDialog(this,"Caracterizacao de Teste de Validação " + jTxtFieldName.getText() + " atualizado");
         }
             
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        //setVisible(false);
        jFramePrincipal.disablePanels();
    }//GEN-LAST:event_jButtonSairActionPerformed
  
    private void popularRegistro(String nome) {

      CaracterizacaoTesteValidacao tstVal = CaracterizacaoTstValidacaoDAO.getCaracterizacaoTesteValidacao(nome);

      if (tstVal != null) {

          jTxtFieldName.setText(tstVal.getNome());
          jTxtFieldName.setEditable(false);
          jTxtFieldComentario.setText(tstVal.getComentario());
          jcmbDocEntrada.setSelectedItem(tstVal.getDocumentoEntrada().getNome());
          jcmbClassSaidaPos.setSelectedItem(tstVal.getClasseValidacaoSaidaPositiva());
          jcmbClassSaidaNeg.setSelectedItem(tstVal.getClasseValidacaoSaidaNegativa());
          if (tstVal.getDocumentoSaidaPositiva() != null)
             jcmbDocSaidaPos.setSelectedItem(tstVal.getDocumentoSaidaPositiva().getNome());
          if (tstVal.getDocumentoSaidaNegativa() != null)
             jcmbDocSaidaNeg.setSelectedItem(tstVal.getDocumentoSaidaNegativa().getNome());
          

          if (tstVal.getCasosPositivos() > 0) {
              jrdbNPos.setSelected(true);
              jTextFieldNPos.setText(((Integer)tstVal.getCasosPositivos()).toString());
          }
          else {
              jTextFieldNPos.setText("");              
              jrdbTodosRep.setSelected(true);
          }

          if(tstVal.getCasosNegativos() > 0) {
              jrdbNNeg.setSelected(true);
              jTextFieldNNeg.setText(((Integer)tstVal.getCasosNegativos()).toString());
          }
          else {
              jTextFieldNNeg.setText("");              
              jrdbTodosNeg.setSelected(true);
          }

          
          Vector <Object> vectorEspecifico = null;
          Vector <Vector> vectorPositivo = new Vector();
          Vector <Vector> vectorNegativo = new Vector();
          Vector <Vector> vectorOpcionais = new Vector();
          Vector <Vector> vectorRepeticao = new Vector();                 
          
          
          for (Especificos especifico : tstVal.getEspecificosCollection()) {
              vectorEspecifico = new Vector();
              
              vectorEspecifico.add(especifico.getOrderId());
              vectorEspecifico.add(especifico.getAtributo().getId());
              Boolean todos;
              if (especifico.getQuantidade() > 0) {
                  todos = false;    
                  vectorEspecifico.add(todos);
                  vectorEspecifico.add(especifico.getQuantidade());
              }
              else {    
                 todos = true;               
                 vectorEspecifico.add(todos);
                 vectorEspecifico.add(0);
              }
              
              if (especifico.getTipo().equalsIgnoreCase("P"))
                  vectorPositivo.addElement(vectorEspecifico);
              
              if (especifico.getTipo().equalsIgnoreCase("N"))
                 vectorNegativo.addElement(vectorEspecifico);
              
              if (especifico.getTipo().equalsIgnoreCase("O"))
                  vectorOpcionais.addElement(vectorEspecifico);
              
              if (especifico.getTipo().equalsIgnoreCase("R"))
                  vectorRepeticao.addElement(vectorEspecifico);
          }           
          
          gridPositivos.fillGrid(vectorPositivo);
          gridNegativos.fillGrid(vectorNegativo);
          gridOpcionais.fillGrid(vectorOpcionais);
          gridRepeticoes.fillGrid(vectorRepeticao);
        }       
    }

    private void limparRegistro() {
        jTxtFieldName.setText("");
        jTxtFieldName.setEditable(true);
        jTxtFieldComentario.setText("");
        jcmbDocEntrada.setSelectedItem("");
        jcmbDocSaidaPos.setSelectedItem("");
        jcmbDocSaidaNeg.setSelectedItem("");
        jcmbClassSaidaPos.setSelectedItem("");
        jcmbClassSaidaNeg.setSelectedItem("");
        jTextFieldNNeg.setText("");
        jTextFieldNPos.setText("");

        jrdbTodosPos.setSelected(true);
        jrdbTodosNeg.setSelected(true);
        jrdbTodosRep.setSelected(true);
        jrdbTodosOp.setSelected(true);
        
        
        limparGrid( (OGridTableModel)gridPositivos.getOGridTableModel());
        limparGrid( (OGridTableModel)gridNegativos.getOGridTableModel());
        limparGrid( (OGridTableModel)gridOpcionais.getOGridTableModel());
        limparGrid( (OGridTableModel)gridRepeticoes.getOGridTableModel());
       
    }
    
    private void limparGrid( OGridTableModel tableModel) {        
        //Limpar registros que estejam no grid de Valores       
        int nRow =  tableModel.getRowCount();
        for (int i = 0; i < nRow; i++)
            tableModel.deleteLine(i);
    }

    private void initCombos() {       
         
        jcmbClassSaidaPos.removeAllItems();
        jcmbClassSaidaNeg.removeAllItems();         
        jcmbClassSaidaPos.addItem("");
        jcmbClassSaidaNeg.addItem("");

        jcmbDocEntrada.addItem("");
        jcmbDocSaidaPos.addItem("");
        jcmbDocSaidaNeg.addItem("");

         List<TemplateDocumento> lstDocumento = DocumentoDAO.getAll();
         for (TemplateDocumento doc : lstDocumento) {
             jcmbDocEntrada.addItem(doc.getNome());
             jcmbDocSaidaPos.addItem(doc.getNome());
             jcmbDocSaidaNeg.addItem(doc.getNome());
         }

         List<ClasseValidacao> lstClasse = ClasseValidacaoDAO.getAll();
         for (ClasseValidacao cv : lstClasse) {
             jcmbClassSaidaPos.addItem(cv.getNome());
             jcmbClassSaidaNeg.addItem(cv.getNome());
         }
     }

    private void salvarGrids() {
       salvarGrid("P",gridPositivos);
       salvarGrid("N",gridNegativos);
       salvarGrid("O",gridOpcionais);
       salvarGrid("R",gridRepeticoes);
    }

    private void removerGrids() {
        CaracterizacaoTesteValidacao tstVal =
                    CaracterizacaoTstValidacaoDAO.getCaracterizacaoTesteValidacao(jTxtFieldName.getText());
        CaracterizacaoTstValidacaoDAO.removeEspecificos(tstVal.getId());       
    }

    private void salvarGrid(String tipo, OGrid grid) {
       CaracterizacaoTesteValidacao caracTesteVal;

       caracTesteVal = CaracterizacaoTstValidacaoDAO.getCaracterizacaoTesteValidacao(jTxtFieldName.getText());

       Vector<Especificos> especificos = new Vector();
       Vector<Vector> dataGrid = grid.getGridData();
       Vector <Object> vector = new Vector(4);

        for (int index = 0; index < dataGrid.size(); index++) {

            Especificos especifico = new Especificos();
            vector.clear();

            for(int i = 0; i < 4; i++) {
                vector.add(i, null);
            }

            for (int indexC = 0; indexC < dataGrid.get(index).size(); indexC++){
                vector.set(indexC,dataGrid.get(index).get(indexC));
            }

           if (vector.get(0)!= null)
               especifico.setOrderId(Long.parseLong(vector.get(0).toString()));

           if (vector.get(1) != null)
                especifico.setAtributo(AtributoDAO.get(Long.parseLong(vector.get(1).toString())));

            if (vector.get(2) == null || (Boolean)vector.get(2) == false) {
                if (vector.get(3) != null && Integer.parseInt(vector.get(3).toString()) > 0) {
                    especifico.setQuantidade(Integer.parseInt(vector.get(3).toString()));
                }
            }            

            especifico.setTipo(tipo);
            especifico.setIdCaracterizacaoTesteValidacao(caracTesteVal);

            especificos.add(especifico);
        }

        for (int i = 0; i<especificos.size(); i++) {
            EspecificoDAO.save(especificos.get(i));
        }
    }

    private int validarCampos() {
         int retorno = 0;
         if (jcmbDocEntrada.getSelectedItem() == "") {
            JOptionPane.showMessageDialog(this,"Escolha um Documento de Entrada");
            retorno = -1;
         }
         return retorno;
 }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupNegativo;
    private javax.swing.ButtonGroup buttonGroupPositivo;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextFieldNNeg;
    private javax.swing.JTextField jTextFieldNPos;
    private javax.swing.JTextField jTxtFieldComentario;
    private javax.swing.JTextField jTxtFieldName;
    private javax.swing.JComboBox jcmbClassSaidaNeg;
    private javax.swing.JComboBox jcmbClassSaidaPos;
    private javax.swing.JComboBox jcmbDocEntrada;
    private javax.swing.JComboBox jcmbDocSaidaNeg;
    private javax.swing.JComboBox jcmbDocSaidaPos;
    private javax.swing.JLabel jlblDocEntrada;
    private javax.swing.JLabel jlblPositivo;
    private javax.swing.JRadioButton jrdbNNeg;
    private javax.swing.JRadioButton jrdbNPos;
    private javax.swing.JRadioButton jrdbTodosNeg;
    private javax.swing.JRadioButton jrdbTodosOp;
    private javax.swing.JRadioButton jrdbTodosPos;
    private javax.swing.JRadioButton jrdbTodosRep;
    // End of variables declaration//GEN-END:variables

    @Override
    public void atualizarTela() {

        jcmbDocEntrada.removeAllItems();
        jcmbDocSaidaPos.removeAllItems();
        jcmbDocSaidaNeg.removeAllItems();

        jcmbDocEntrada.addItem("");
        jcmbDocSaidaPos.addItem("");
        jcmbDocSaidaNeg.addItem("");

         List<TemplateDocumento> lstDocumento = DocumentoDAO.getAll();
         for (TemplateDocumento doc : lstDocumento) {
             jcmbDocEntrada.addItem(doc.getNome());
             jcmbDocSaidaPos.addItem(doc.getNome());
             jcmbDocSaidaNeg.addItem(doc.getNome());
         }

        ComboBoxAtributo cba = new ComboBoxAtributo();
        gridPositivos.getOGridTableModel().getColumnConfiguration(1).setcBoxDataSource(cba);
        gridPositivos.configureTableColumns();
        gridNegativos.getOGridTableModel().getColumnConfiguration(1).setcBoxDataSource(cba);
        gridNegativos.configureTableColumns();
        gridOpcionais.getOGridTableModel().getColumnConfiguration(1).setcBoxDataSource(cba);
        gridOpcionais.configureTableColumns();
        gridRepeticoes.getOGridTableModel().getColumnConfiguration(1).setcBoxDataSource(cba);
        gridRepeticoes.configureTableColumns();
    }

}
