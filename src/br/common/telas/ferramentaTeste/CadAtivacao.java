package br.common.telas.ferramentaTeste;

import br.org.fdte.ColumnConfiguration;
import br.org.fdte.OGrid;
import br.org.fdte.OGridDoubleClickListener;
import br.org.fdte.OGridTableModel;
import br.org.fdte.persistence.AtivacaoTesteValidacao;
import br.org.fdte.persistence.ExecucaoTesteValidacao;
import br.org.servicos.ExecucaoTstValidacaoServico;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class CadAtivacao extends javax.swing.JPanel implements OGridDoubleClickListener {

    private CadExecucao telaExecucao;
    private ExecucaoTesteValidacao execucaoTesteValidacao = null;
    private static OGrid g = null;

    public CadAtivacao(CadExecucao telaExecucao) {
        this.telaExecucao = telaExecucao;
        initComponents();
        initCombo();

        g = new OGrid();
        g.setBounds(0, 80, 800, 400);

        // SEQ
        ColumnConfiguration c = new ColumnConfiguration();
        c.setTitle("Seq");
        c.setFieldType(ColumnConfiguration.FieldType.SEQ);
        c.setEditable(false);
        c.setWidth(20);
        c.setFont(new Font("Verdana", Font.BOLD, 12));
        c.setColor(Color.ORANGE);
        c.setEditable(false);
        g.addColumn(c);


        c = new ColumnConfiguration();
        c.setTitle("Tipo");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(10);
        c.setEditable(false);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Inicio");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(100);
        c.setEditable(false);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Termino");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(100);
        c.setEditable(false);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("Teste");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(20);
        c.setEditable(false);
        g.addColumn(c);

        c = new ColumnConfiguration();
        c.setTitle("GoldenCompare");
        c.setFieldType(ColumnConfiguration.FieldType.TEXT);
        c.setWidth(30);
        c.setEditable(false);
        g.addColumn(c);

        // render table
        g.renderAndDisableButtons();
        g.setVisible(true);

        g.setDoubleClickListener(this);

        add(g);
    }

    public void setExecucao(ExecucaoTesteValidacao execucao) {

        if (execucao.getModoAtivacao().equals("G")) {
            jComboGolden.setSelectedItem(" ");
            jComboGolden.setEnabled(false);
            jLblGoldenCompare.setEnabled(false);
        } else {
            jComboGolden.setEnabled(true);
            jLblGoldenCompare.setEnabled(true);
            jComboGolden.setSelectedItem(" ");
        }

        jComboResultado.setSelectedIndex(0);

        execucaoTesteValidacao = execucao;
        jlblExecutionId.setText("Execucão " + execucao.getId().toString());
        limparGrid();
        obtemAtivacoes();
    }

    private void initCombo() {
        jComboResultado.removeAllItems();
        jComboResultado.addItem("Sucesso");
        jComboResultado.addItem("Falha");
        jComboResultado.addItem("Timeout");
        jComboResultado.addItem(" ");

        jComboGolden.removeAllItems();
        jComboGolden.addItem("Sucesso");
        jComboGolden.addItem("Falha");
        jComboGolden.addItem(" ");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblGoldenCompare = new javax.swing.JLabel();
        jComboGolden = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jlblExecutionId = new javax.swing.JLabel();
        jComboResultado = new javax.swing.JComboBox();
        jLblResultado = new javax.swing.JLabel();

        setLayout(null);

        jLblGoldenCompare.setText("GoldenCompare");
        add(jLblGoldenCompare);
        jLblGoldenCompare.setBounds(140, 40, 100, 14);

        jComboGolden.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboGoldenItemStateChanged(evt);
            }
        });
        jComboGolden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboGoldenActionPerformed(evt);
            }
        });
        add(jComboGolden);
        jComboGolden.setBounds(140, 60, 110, 20);

        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(700, 570, 60, 23);
        add(jlblExecutionId);
        jlblExecutionId.setBounds(10, 10, 160, 20);

        jComboResultado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboResultadoItemStateChanged(evt);
            }
        });
        jComboResultado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboResultadoActionPerformed(evt);
            }
        });
        add(jComboResultado);
        jComboResultado.setBounds(10, 60, 110, 20);

        jLblResultado.setText("Teste");
        add(jLblResultado);
        jLblResultado.setBounds(10, 40, 70, 14);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboGoldenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboGoldenItemStateChanged

        if (execucaoTesteValidacao == null || evt.getStateChange() != ItemEvent.SELECTED) {
            return;
        }

        if (execucaoTesteValidacao.getModoAtivacao().equalsIgnoreCase("G")) {
            return;
        }

        limparGrid();

        List<AtivacaoTesteValidacao> listaAtivacoes = new ArrayList<AtivacaoTesteValidacao>();

        Collection<AtivacaoTesteValidacao> colectionAtivacao = this.execucaoTesteValidacao.getAtivacaoTesteValidacaoCollection();

        switch (jComboGolden.getSelectedIndex()) {
            //Sucesso
            case 0:
                for (AtivacaoTesteValidacao ativ : colectionAtivacao) {
                    if (ativ.getGoldenCompare().equalsIgnoreCase("S")) {
                        listaAtivacoes.add(ativ);
                    }
                }
                break;
            //Falha
            case 1:
                for (AtivacaoTesteValidacao ativ : colectionAtivacao) {
                    if (ativ.getGoldenCompare().equalsIgnoreCase("F")) {
                        listaAtivacoes.add(ativ);
                    }
                }
                break;
        }

        sortByAtivId(listaAtivacoes);

        fillGrid(listaAtivacoes);

    }

    private void obtemAtivacoes() {

        List<AtivacaoTesteValidacao> listaAtivacoes = new ArrayList<AtivacaoTesteValidacao>();

        Collection<AtivacaoTesteValidacao> colectionAtivacao = this.execucaoTesteValidacao.getAtivacaoTesteValidacaoCollection();

        switch (jComboResultado.getSelectedIndex()) {
            //Sucesso
            case 0:
                for (AtivacaoTesteValidacao ativ : colectionAtivacao) {
                    if (ativ.getResultado().equalsIgnoreCase("S")) {
                        listaAtivacoes.add(ativ);
                    }
                }
                break;
            //Falha
            case 1:
                for (AtivacaoTesteValidacao ativ : colectionAtivacao) {
                    if (ativ.getResultado().equalsIgnoreCase("F")) {
                        listaAtivacoes.add(ativ);
                    }
                }
                break;
            //Timeout
            case 2:
                for (AtivacaoTesteValidacao ativ : colectionAtivacao) {
                    if (ativ.getResultado().equalsIgnoreCase("T")) {
                        listaAtivacoes.add(ativ);
                    }
                }
                break;
            case 3:
                for (AtivacaoTesteValidacao ativ : colectionAtivacao) {
                    listaAtivacoes.add(ativ);
                }
                break;
        }

        sortByAtivId(listaAtivacoes);

        fillGrid(listaAtivacoes);

    }//GEN-LAST:event_jComboGoldenItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        telaExecucao.setEnabledPanelFilho(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboResultadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboResultadoItemStateChanged
        if (execucaoTesteValidacao != null && evt.getStateChange() == ItemEvent.SELECTED) {

            if (jComboResultado.getSelectedItem() != " ") {
                jComboGolden.setSelectedItem(" ");
                jComboGolden.setEnabled(false);
            } else {
                if (!execucaoTesteValidacao.getModoAtivacao().equalsIgnoreCase("G")) {
                    jComboGolden.setSelectedIndex(0);
                    jComboGolden.setEnabled(true);
                }
            }

            limparGrid();
            obtemAtivacoes();
        }
    }//GEN-LAST:event_jComboResultadoItemStateChanged

    private void jComboResultadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboResultadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboResultadoActionPerformed

    private void jComboGoldenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboGoldenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboGoldenActionPerformed

    @Override
    public void event(int row, int column) {

        OGridTableModel tableModel = (OGridTableModel) g.getOGridTableModel();
        // id da ativacao esta setado na coluna 0
        Long idActivation = (Long) tableModel.getValueAt(row, 0);

        //buscar a imagem salva no banco pelo id da ativacao
        //AtivacaoTesteValidacao ativacao = AtivacaoTesteValidacaoDAO.findById(idActivation);
        AtivacaoTesteValidacao ativacao = new ExecucaoTstValidacaoServico().getActivation(idActivation);

        if (ativacao.getScreenshot() != null) {
            ImageIcon imageIcon2 = new ImageIcon(ativacao.getScreenshot());
            JOptionPane.showMessageDialog(null, imageIcon2);
        }

    }

    private void limparGrid() {
        //Limpar registros que estejam no grid de Ativacoes
        OGridTableModel tableModel = (OGridTableModel) g.getOGridTableModel();
        int nRow = tableModel.getRowCount();
        for (int i = 0; i < nRow; i++) {
            tableModel.deleteLine(i);
        }
    }

    private void sortByAtivId(List<AtivacaoTesteValidacao> listRecebida) {

        Collections.sort(listRecebida, new Comparator() {

            @Override
            public int compare(Object obj1, Object obj2) {
                AtivacaoTesteValidacao v1 = (AtivacaoTesteValidacao) obj1;
                AtivacaoTesteValidacao v2 = (AtivacaoTesteValidacao) obj2;

                if (v1.getSequencial() < v2.getSequencial()) {
                    return -1;
                } else if (v1.getSequencial() == v2.getSequencial()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    private void fillGrid(List<AtivacaoTesteValidacao> listaAtivacoes) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Vector<Object> vectorAtributo = null;
        Vector<Vector> vector = new Vector();

        for (AtivacaoTesteValidacao ativacao : listaAtivacoes) {
            vectorAtributo = new Vector();
            vectorAtributo.add(ativacao.getId());
            vectorAtributo.add(ativacao.getTipo());
            vectorAtributo.add(dateFormat.format(ativacao.getInicio()));
            vectorAtributo.add(dateFormat.format(ativacao.getTermino()));
            vectorAtributo.add(ativacao.getResultado());
            vectorAtributo.add(ativacao.getGoldenCompare());
            vector.addElement(vectorAtributo);
        }

        g.fillGrid(vector);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboGolden;
    private javax.swing.JComboBox jComboResultado;
    private javax.swing.JLabel jLblGoldenCompare;
    private javax.swing.JLabel jLblResultado;
    private javax.swing.JLabel jlblExecutionId;
    // End of variables declaration//GEN-END:variables
}
