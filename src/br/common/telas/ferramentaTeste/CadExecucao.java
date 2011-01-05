package br.common.telas.ferramentaTeste;


import br.org.fdte.AtualizacaoTela;
import br.org.fdte.ColumnConfiguration;
import br.org.fdte.OGrid;
import br.org.fdte.OGridTableModel;
import br.org.fdte.dao.ExecucaoTesteValidacaoDAO;
import br.org.fdte.dao.SuiteTesteSequenciaDAO;
import br.org.fdte.dao.SuiteTesteValidacaoDAO;
import br.org.fdte.persistence.ExecucaoTesteValidacao;
import br.org.fdte.persistence.SuiteTesteSequencia;
import br.org.fdte.persistence.SuiteTesteValidacao;
import executortestevalidacao.ExecuteValidationTestException;
import executortestevalidacao.ExecutionCallback;
import java.util.List;
import java.awt.Font;
import java.awt.Color;
import executortestevalidacao.ExecutorTesteValidacao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import javax.swing.JOptionPane;



public class CadExecucao extends javax.swing.JPanel implements AtualizacaoTela, ExecutionCallback {

     private OGrid g;
     private JFramePrincipal jFramePrincipal;

     private ExecutorTesteValidacao ex;
    

     private static int line = 0;
     

    /** Creates new form CadExecucao */
    public CadExecucao(JFramePrincipal jFramePrincipal) {
        this.jFramePrincipal = jFramePrincipal;
       
        setBounds(200,0,jFramePrincipal.PANEL_WIDTH,jFramePrincipal.PANEL_HEIGHT);

        // OGrid test
        g = new OGrid();
        g.setBounds(0, 220, 875, 200);

        ColumnConfiguration c = new ColumnConfiguration();
        c.setTitle("TC");
        //c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setFieldType(ColumnConfiguration.FieldType.SEQ);
        c.setWidth(300);
        c.setFont(new Font("Verdana", Font.BOLD, 12));
        c.setColor(Color.ORANGE);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Status");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(100);
        c.setFont(new Font("Verdana", Font.BOLD, 12));
        c.setColor(Color.ORANGE);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Sucesso");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(100);
        c.setFont(new Font("Verdana", Font.BOLD, 12));
        c.setColor(Color.ORANGE);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Falha");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(100);
        c.setFont(new Font("Verdana", Font.BOLD, 12));
        c.setColor(Color.ORANGE);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Timeout");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(100);
        c.setFont(new Font("Verdana", Font.BOLD, 12));
        c.setColor(Color.ORANGE);
        g.addColumn(c);

        // render table
        g.renderAndDisableButtons();
        g.setVisible(true);

        add(g);

        initComponents();
        initCombos();     

        ex = new ExecutorTesteValidacao();

    }

    @Override
   public void executionEventHandler(ExecutionEvent event) {

        jTextLog.append("****** Got event " + event.eventType + " " + event.message + " " + event.objectType + " " + event.objectId + "\n" );
    
        System.out.println("****** Got event " + event.eventType + " " + event.message + " " + event.objectType + " " + event.objectId);
        System.out.println(jTextLog.getText());

        if (event.eventType == event.eventType.TEST_STARTED ||
                event.eventType == event.eventType.TEST_ENDED) {
            g.getOGridTableModel().setValueAt(event.message.toString(),line,0);
            g.getOGridTableModel().setValueAt(event.eventType.toString(),line,1);
            g.renderAndDisableButtons();
            line++;
        }        
      

    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    
    public void atualizarTela() {
        jComboBoxSuite.removeAllItems();
        initCombos();
    }

    public void setRegistro(SuiteTesteValidacao suite) {
        jComboBoxSuite.setSelectedItem(suite.getNome());
        jComboBoxSuite.setEnabled(false);
        jButtonExec.setEnabled(true);
        jButtonAbort.setEnabled(true);
        jrdbSimplesExec.setSelected(true);
        jrdbSimplesExec.setEnabled(true);
        jrdbGolden.setEnabled(true);
        jrdbTestSistema.setEnabled(true);
        jTxtInicio.setText("");
        jTxtTermino.setText("");

        //remover os dados do grid de execuções
        limparAreaGrid();

    }
    public void setRegistro(SuiteTesteValidacao suite, String grupoNode) {
        List<ExecucaoTesteValidacao> listExecs = ExecucaoTesteValidacaoDAO.getExecucoesTesteValidacaoPerGroup(suite);
        Vector<ExecucaoTesteValidacao> listExecValidas = new Vector();

        String grupoId;
        for (ExecucaoTesteValidacao exec : listExecs) {
            grupoId = "Grupo Exec" + exec.getIdGrupoExec();
            if (grupoId.equals(grupoNode))
                listExecValidas.add(exec);
        }

       jComboBoxSuite.setSelectedItem(suite.getNome());
       jComboBoxSuite.setEnabled(false);
       jButtonExec.setEnabled(false);
       jButtonAbort.setEnabled(false);

       if (listExecValidas.get(0).getModoAtivacao().equalsIgnoreCase("G")) {
           jrdbGolden.setSelected(true);
           jrdbGolden.setEnabled(true);
           jrdbTestSistema.setEnabled(false);
           jrdbSimplesExec.setEnabled(false);
       }
       else {
           if (listExecValidas.get(0).getModoAtivacao().equalsIgnoreCase("T")) {
               jrdbTestSistema.setSelected(true);
               jrdbTestSistema.setEnabled(true);
               jrdbGolden.setEnabled(false);
               jrdbSimplesExec.setEnabled(false);
           }
           else {
               jrdbSimplesExec.setSelected(true);
               jrdbSimplesExec.setEnabled(true);
               jrdbGolden.setEnabled(false);
               jrdbTestSistema.setEnabled(false);
           }
       }

       SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
       if (listExecValidas.get(0).getInicio() != null)
        jTxtInicio.setText(formatador.format(listExecValidas.get(0).getInicio()));
       if (listExecValidas.get(0).getTermino() != null)
        jTxtTermino.setText(formatador.format(listExecValidas.get(0).getTermino()));

       //remover os dados do grid de execuções
       limparAreaGrid();

       for (ExecucaoTesteValidacao exec : listExecValidas) {
           g.getOGridTableModel().setValueAt(exec.getIdCaracterizacaoTesteValidacao().getNome(),line,0);
           g.getOGridTableModel().setValueAt(exec.getCasosSucesso(),line,2);
           g.getOGridTableModel().setValueAt(exec.getCasosFalha(),line,3);
           g.getOGridTableModel().setValueAt(exec.getCasosTimeout(),line,4);
           line++;
       }
    }

    public void setRegistro(String execId) {
      // mostrar as ativacoes da execucao. em um outro grid.
      // melhor criar outra tela?
       String execID = execId.substring(20);
       int id = Integer.parseInt(execID);
       ExecucaoTesteValidacao exec = ExecucaoTesteValidacaoDAO.getExecucaoTesteValidacao(id);
       jComboBoxSuite.setSelectedItem(exec.getIdSuite().getNome());
       jComboBoxSuite.setEnabled(false);       
       jButtonExec.setEnabled(false);
       jButtonAbort.setEnabled(false);
       
       if (exec.getModoAtivacao().equalsIgnoreCase("G")) {
           jrdbGolden.setSelected(true);
           jrdbGolden.setEnabled(true);
           jrdbTestSistema.setEnabled(false);
           jrdbSimplesExec.setEnabled(false);
       }
       else {
           if (exec.getModoAtivacao().equalsIgnoreCase("T")) {
               jrdbTestSistema.setSelected(true);
               jrdbTestSistema.setEnabled(true);
               jrdbGolden.setEnabled(false);
               jrdbSimplesExec.setEnabled(false);
           }
           else {
               jrdbSimplesExec.setSelected(true);
               jrdbSimplesExec.setEnabled(true);
               jrdbGolden.setEnabled(false);
               jrdbTestSistema.setEnabled(false);
           }
       }

       SimpleDateFormat formatador = new SimpleDateFormat("HH:mm:ss");
       if (exec.getInicio() != null)
        jTxtInicio.setText(formatador.format(exec.getInicio()));
       if (exec.getTermino() != null)
        jTxtTermino.setText(formatador.format(exec.getTermino()));

       //remover os dados do grid de execuções
       limparAreaGrid();

       g.getOGridTableModel().setValueAt(exec.getIdCaracterizacaoTesteValidacao().getNome(),line,0);
       g.getOGridTableModel().setValueAt(exec.getCasosSucesso(),line,2);
       g.getOGridTableModel().setValueAt(exec.getCasosFalha(),line,3);
       g.getOGridTableModel().setValueAt(exec.getCasosTimeout(),line,4);      

    }

    private void initCombos() {
        List<SuiteTesteSequencia> lstTstSeq = SuiteTesteSequenciaDAO.getAll();
        List<SuiteTesteValidacao> lstTstVal = SuiteTesteValidacaoDAO.getAll();

        for(SuiteTesteValidacao s : lstTstVal ) {
           jComboBoxSuite.addItem(s.getNome());
        }
        for (SuiteTesteSequencia s : lstTstSeq) {
           jComboBoxSuite.addItem(s.getNome());
        }
     }
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBGExecutar = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxSuite = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jrdbGolden = new javax.swing.JRadioButton();
        jrdbTestSistema = new javax.swing.JRadioButton();
        jrdbSimplesExec = new javax.swing.JRadioButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldRelatorio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jChkbEThread = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jChkbAbortErr = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonSair = new javax.swing.JButton();
        jlblInicio = new javax.swing.JLabel();
        jlblTermino = new javax.swing.JLabel();
        jTxtInicio = new javax.swing.JTextField();
        jTxtTermino = new javax.swing.JTextField();
        jButtonAbort = new javax.swing.JButton();
        jButtonExec = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextLog = new javax.swing.JTextArea();

        setLayout(null);

        jLabel1.setText("Suite");
        add(jLabel1);
        jLabel1.setBounds(10, 10, 60, 14);

        add(jComboBoxSuite);
        jComboBoxSuite.setBounds(10, 30, 190, 20);

        jLabel2.setText("Executar como");
        add(jLabel2);
        jLabel2.setBounds(300, 10, 120, 14);

        jBGExecutar.add(jrdbGolden);
        jrdbGolden.setText("Geração de Goldenfile");
        add(jrdbGolden);
        jrdbGolden.setBounds(300, 30, 170, 23);

        jBGExecutar.add(jrdbTestSistema);
        jrdbTestSistema.setText("Teste de Sistema");
        add(jrdbTestSistema);
        jrdbTestSistema.setBounds(300, 80, 130, 23);

        jBGExecutar.add(jrdbSimplesExec);
        jrdbSimplesExec.setSelected(true);
        jrdbSimplesExec.setText("Simples Execução");
        add(jrdbSimplesExec);
        jrdbSimplesExec.setBounds(300, 110, 130, 23);

        jCheckBox1.setText("Pós_Validação");
        add(jCheckBox1);
        jCheckBox1.setBounds(340, 50, 130, 23);

        jLabel3.setText("Arquivo de relatório");
        add(jLabel3);
        jLabel3.setBounds(540, 10, 120, 14);
        add(jTextFieldRelatorio);
        jTextFieldRelatorio.setBounds(540, 30, 150, 20);

        jLabel4.setText("Executar em thread");
        add(jLabel4);
        jLabel4.setBounds(560, 60, 130, 20);
        add(jChkbEThread);
        jChkbEThread.setBounds(540, 60, 21, 21);

        jLabel5.setText("Abortar em erro");
        add(jLabel5);
        jLabel5.setBounds(560, 90, 100, 20);
        add(jChkbAbortErr);
        jChkbAbortErr.setBounds(540, 90, 21, 21);
        add(jSeparator1);
        jSeparator1.setBounds(0, 140, 800, 2);

        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });
        add(jButtonSair);
        jButtonSair.setBounds(710, 570, 60, 23);

        jlblInicio.setText("Início");
        add(jlblInicio);
        jlblInicio.setBounds(5, 150, 50, 20);

        jlblTermino.setText("Término");
        add(jlblTermino);
        jlblTermino.setBounds(5, 180, 50, 20);

        jTxtInicio.setEditable(false);
        add(jTxtInicio);
        jTxtInicio.setBounds(60, 150, 70, 20);

        jTxtTermino.setEditable(false);
        add(jTxtTermino);
        jTxtTermino.setBounds(60, 180, 70, 20);

        jButtonAbort.setText("Abortar");
        add(jButtonAbort);
        jButtonAbort.setBounds(620, 150, 80, 23);

        jButtonExec.setText("Executar");
        jButtonExec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExecActionPerformed(evt);
            }
        });
        add(jButtonExec);
        jButtonExec.setBounds(710, 150, 90, 23);

        jLabel6.setText("Andamento");
        add(jLabel6);
        jLabel6.setBounds(10, 220, 90, 14);

        jTextLog.setColumns(20);
        jTextLog.setRows(5);
        jScrollPane1.setViewportView(jTextLog);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 450, 780, 90);
    }// </editor-fold>//GEN-END:initComponents
   
    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        //setVisible(false);
        jFramePrincipal.disablePanels();
    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonExecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExecActionPerformed
        try {            
            line = 0;
            limparTextLog();

            SuiteTesteValidacao suite = SuiteTesteValidacaoDAO.getSuiteTesteValidacao(jComboBoxSuite.getSelectedItem().toString());
            List<ExecucaoTesteValidacao> execucoes;
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            ex.setExecutionCallback( this );

            if (jrdbGolden.isSelected()) {
                // demonstrate use of checkForExistingGoldenfile for any test
                // within a suite
                Collection<String> execs = ex.checkForExistingGoldenfile(jComboBoxSuite.getSelectedItem().toString());
                if ( execs != null && (execs.size() > 0)) {
                    if (JOptionPane.YES_OPTION !=
                        JOptionPane.showConfirmDialog(this,"Suite " + jComboBoxSuite.getSelectedItem().toString() + " contem execucoes previas que serao deletadas","Execucão de GoldenFile",2))
                        return;                    
                    for (String s : execs) {
                        System.out.println ("Execucao que sera´ removida " + s);
                    }
                }
                jFramePrincipal.removeExecs("G");
                ex.executeValidationSuite(jComboBoxSuite.getSelectedItem().toString(), ExecutorTesteValidacao.ExecutionMode.GOLDEN_FILE, 1, null, false);
                execucoes = ExecucaoTesteValidacaoDAO.getExecucoesTesteValidacao(suite);
                //Adicionando todas as execuçoes como nos da arvore da suite executada
                for ( ExecucaoTesteValidacao exec : execucoes ) {
                    jFramePrincipal.addNode(formatador.format(exec.getInicio()) + " " + exec.getId());
                }
            }
            else {
                if (jrdbTestSistema.isSelected()) {
                    jFramePrincipal.removeExecs("T");
                    ex.executeValidationSuite(jComboBoxSuite.getSelectedItem().toString(), ExecutorTesteValidacao.ExecutionMode.SYSTEM_TEST, 1, null, false);
                    execucoes = ExecucaoTesteValidacaoDAO.getExecucoesTesteValidacao(suite);
                    //Adicionando todas as execuçoes como nos da arvore da suite executada
                    for ( ExecucaoTesteValidacao exec : execucoes ) {
                        jFramePrincipal.addNode(formatador.format(exec.getInicio()) + " " + exec.getId());
                    }
                }
                else {
                    if (jrdbSimplesExec.isSelected())
                       ex.executeValidationSuite(jComboBoxSuite.getSelectedItem().toString(), ExecutorTesteValidacao.ExecutionMode.SYSTEM_EXERCIZE, 1, null, false);
                }
            }
        }
        catch(ExecuteValidationTestException except) {
            System.out.println(except.getMessage());
        }        
    }//GEN-LAST:event_jButtonExecActionPerformed

    private void limparAreaGrid() {

       line = 0;

       //Limpar registros que estejam no grid de Atributos
        OGridTableModel tableModel = (OGridTableModel)g.getOGridTableModel();
        int nRow =  tableModel.getRowCount();
        for (int i = 0; i < nRow; i++)
            tableModel.deleteLine(i);

    //    limparTextLog();
   }

    private void limparTextLog() {
        /*jTextLog = new JTextArea();
        jTextLog.setColumns(20);
        jTextLog.setRows(5);
        jScrollPane1.setViewportView(jTextLog);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 450, 780, 90);*/      
        jTextLog.setText("");        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup jBGExecutar;
    private javax.swing.JButton jButtonAbort;
    private javax.swing.JButton jButtonExec;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jChkbAbortErr;
    private javax.swing.JCheckBox jChkbEThread;
    private javax.swing.JComboBox jComboBoxSuite;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldRelatorio;
    private javax.swing.JTextArea jTextLog;
    private javax.swing.JTextField jTxtInicio;
    private javax.swing.JTextField jTxtTermino;
    private javax.swing.JLabel jlblInicio;
    private javax.swing.JLabel jlblTermino;
    private javax.swing.JRadioButton jrdbGolden;
    private javax.swing.JRadioButton jrdbSimplesExec;
    private javax.swing.JRadioButton jrdbTestSistema;
    // End of variables declaration//GEN-END:variables
}