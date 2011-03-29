package br.common.telas.ferramentaTeste;

import br.org.fdte.AtualizacaoTela;
import br.org.fdte.ExecutionTreeNode;
import br.org.fdte.MyRenderer;

import java.awt.event.ActionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import java.util.List;

import br.org.fdte.persistence.TemplateDocumento;
import br.org.fdte.persistence.ClasseEquivalencia;
import br.org.fdte.persistence.CaracterizacaoTesteValidacao;
import br.org.fdte.persistence.SuiteTesteValidacao;
import br.org.fdte.persistence.Atributo;
import br.org.fdte.persistence.Especificos;
import br.org.fdte.persistence.ExecucaoTesteValidacao;
import br.org.fdte.persistence.Regra;
import br.org.fdte.persistence.SuiteValidacaoTesteValidacao;
import br.org.fdte.persistence.Valor;

import br.org.fdte.dao.AtivacaoTesteValidacaoDAO;
import br.org.fdte.dao.ExecucaoTesteValidacaoDAO;

import br.org.servicos.CaracterizacaoTesteValidacaoServico;
import br.org.servicos.ClasseEquivalenciaServico;
import br.org.servicos.DocumentoServico;

import br.org.servicos.SuiteServico;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.persistence.RollbackException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

enum Entidade {

    CLASSE_EQUIVALENCIA, DOCUMENTO, TESTE_VALIDACAO, SUITE_VALIDACAO, EXECUCAO
}

public class JFramePrincipal extends javax.swing.JFrame
        implements java.awt.event.ActionListener {

    private static DefaultMutableTreeNode selNode = null;
    private static CadClassEquivalencia currentCCE = null;
    private static CadDocumento currentDoc = null;
    private static CadTesteCaseValidacao currentTesteCase = null;
    private static CadSuiteTeste currentSuiteTeste = null;
    private static CadExecucao currentExec = null;
    private final MyRenderer renderCellNodes = new MyRenderer();
    public final int PANEL_WIDTH = 900;
    public final int PANEL_HEIGHT = 660;

    public JFramePrincipal() {

        initComponents();

        jTree1.setCellRenderer(renderCellNodes);
        popularArvore();

        jSplitPane1.setLeftComponent(jScrollPane1);

        setExtendedState(MAXIMIZED_BOTH);

        if (currentCCE == null) {
            currentCCE = new CadClassEquivalencia(this);
            add(currentCCE);
            currentCCE.setVisible(false);
        }
        if (currentDoc == null) {
            currentDoc = new CadDocumento(this);
            add(currentDoc);
            currentDoc.setVisible(false);
        }
        if (currentTesteCase == null) {
            currentTesteCase = new CadTesteCaseValidacao(this);
            add(currentTesteCase);
            currentTesteCase.setVisible(false);
        }
        if (currentSuiteTeste == null) {
            currentSuiteTeste = new CadSuiteTeste(this);
            add(currentSuiteTeste);
            currentSuiteTeste.setVisible(false);
        }
        if (currentExec == null) {
            currentExec = new CadExecucao(this);
            add(currentExec);
            currentExec.setVisible(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Inserir")) {
            currentCCE.setRegistro("");
            currentDoc.setRegistro("");
            currentTesteCase.setRegistro("");
            currentSuiteTeste.setRegistro("");

            int nPai = selNode.getRoot().getIndex(selNode);

            switch (Entidade.values()[nPai]) {
                case CLASSE_EQUIVALENCIA:
                    currentCCE.setVisible(true);
                    jSplitPane1.setRightComponent(currentCCE);
                    break;
                case DOCUMENTO:
                    currentDoc.setVisible(true);
                    jSplitPane1.setRightComponent(currentDoc);
                    break;
                case TESTE_VALIDACAO:
                    currentTesteCase.setVisible(true);
                    jSplitPane1.setRightComponent(currentTesteCase);
                    break;
                case SUITE_VALIDACAO:
                    currentSuiteTeste.setVisible(true);
                    jSplitPane1.setRightComponent(currentSuiteTeste);
                    break;
            }
        }
        if (e.getActionCommand().equals("Remover")) {
            removeNode();
        }
        if (e.getActionCommand().equals("Renomear")) {
            renomearNo();
        }
        if (e.getActionCommand().equals("CopiarComo")) {
            copiarNo();
        }
        if (e.getActionCommand().equals("Executar")) {
            currentExec.setRegistro(new SuiteServico().getByName(selNode.toString()));
            currentExec.setVisible(true);
            jSplitPane1.setRightComponent(currentExec);

        }
        if (e.getActionCommand().equals("RemoverExec")) {
            removeExecs();
        }
        if (e.getActionCommand().equals("RemoverGrupoExecs")) {
            removerGrupoExecs();
        }
        if (e.getActionCommand().equals("VisualizarAtivacoes")) {
            currentExec.visualizarAtivacoes(
                    ExecucaoTesteValidacaoDAO.getExecucaoTesteValidacao(
                    Integer.parseInt(selNode.toString().substring(20))));
        }
    }

    public void atualizarCampos(String entidade) {
        if (entidade.equalsIgnoreCase(AtualizacaoTela.entidadeClasseEquivalencia)) {
            //atualizar as telas que possuem referencias a classe de equivalencia
            currentDoc.atualizarTela();
            currentCCE.atualizarTela();
        }

        if (entidade.equalsIgnoreCase(AtualizacaoTela.entidadeDocumento)) {
            currentTesteCase.atualizarTela();
        }

        if (entidade.equalsIgnoreCase(AtualizacaoTela.entidadeTesteValidacao)) {
            currentSuiteTeste.atualizarTela();
        }

        if (entidade.equalsIgnoreCase(AtualizacaoTela.entidadeSuiteValidacao)) {
            currentExec.atualizarTela();
        }
    }

    public void addNode(String nodeName) {

        //rota eh o ancestral sem pai
        //selNode pode ser um no da arvore principal (FerramentaTeste) ou um no de uma suite.
        //Obtem-se o indice de selNode na arvore principal
        //The root is the ancestor whith a null parent
        int nPai = selNode.getRoot().getIndex(selNode);
        DefaultMutableTreeNode nodeFather;
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();

        //Se o indice obtido for zero ou positivo, selNode eh um no da arvore principal (FerramentaTeste)
        if (nPai >= 0) {
            nodeFather = (DefaultMutableTreeNode) jTree1.getModel().getChild(jTree1.getModel().getRoot(), nPai);
            //Caso contrario, selNode eh um no de suite
        } else {
            //obtem-se em nPai o indice do noh da Suite dentro da subarvore de SuiteTeste
            nPai = selNode.getParent().getIndex(selNode);

            //nPai não é mais o identificador do no de execução dentro do grupo e sim o identificador do grupo
            ExecucaoTesteValidacao exec = ExecucaoTesteValidacaoDAO.getExecucaoTesteValidacao(Integer.parseInt(nodeName.substring(20)));
            String grupoId = "Grupo Exec" + exec.getIdGrupoExec().toString();
            nodeFather = null;
            for (int i = 0; i < selNode.getChildCount(); i++) {
                if (selNode.getChildAt(i).toString().equals(grupoId)) {
                    nodeFather = (DefaultMutableTreeNode) selNode.getChildAt(i);
                    break;
                }
            }
            if (nodeFather == null) {
                //nodeFather = new DefaultMutableTreeNode(grupoId);
                nodeFather = new ExecutionTreeNode(grupoId, exec.getModoAtivacao().equals("G"));
                model.insertNodeInto(nodeFather, selNode, selNode.getChildCount());
                selNode.add(nodeFather);
            }
            renderCellNodes.getTreeCellRendererComponent(jTree1, nodeFather, rootPaneCheckingEnabled,
                    rootPaneCheckingEnabled, rootPaneCheckingEnabled, nPai, rootPaneCheckingEnabled);
        }

        int index = 0;
        //Obtem em index a posição que o novo no será inserido na árvore.
        //Posição determinada pela ordem alfabética
        while (index < nodeFather.getChildCount()) {
            if (nodeName.compareTo(((DefaultMutableTreeNode) nodeFather.getChildAt(index)).getUserObject().toString()) > 0) {
                index++;
            } else {
                break;
            }
        }

        DefaultMutableTreeNode novoNo;
        if (nodeFather instanceof ExecutionTreeNode) {
            novoNo = new ExecutionTreeNode(nodeName, ((ExecutionTreeNode) nodeFather).isGolden());
        } else {
            novoNo = new DefaultMutableTreeNode(nodeName);
        }

        nodeFather.add(novoNo);
        model.insertNodeInto(novoNo, nodeFather, index);
        if (!nodeFather.toString().contains("Grupo")) {
            tratarNo(novoNo);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new JFramePrincipal().setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FerramentaTeste");

        jSplitPane1.setDividerSize(2);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(1168, 361));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("FerramentaTeste");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("ClasseEquivalencia");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Documento");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("CasoTeste Validação");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("SuiteTeste");
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel1.setLayout(null);
        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1188, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void popularArvore() {

        //Obter os nos da arvore principal
        DefaultTreeModel treeModel = (DefaultTreeModel) jTree1.getModel();
        int nNodes = treeModel.getChildCount(treeModel.getRoot());

        //Para cada no de FerramentaTeste(arvore principal) encontrado
        for (int i = 0; i < nNodes; i++) {

            DefaultMutableTreeNode nodeFather = (DefaultMutableTreeNode) treeModel.getChild(treeModel.getRoot(), i);

            //Verifica-se de que tipo eh o no
            switch (Entidade.values()[i]) {
                //No de Classe_equivalencia
                case CLASSE_EQUIVALENCIA:
                    //List<ClasseEquivalencia> lstCE = ClasseEquivalenciaDAO.getAll();
                    List<ClasseEquivalencia> lstCE = new ClasseEquivalenciaServico().getAll();
                    //A cada Classe Equivalencia existente no bd, é adicionado uma
                    //folha na arvore de Classe de Equivalencia
                    for (ClasseEquivalencia ce : lstCE) {
                        nodeFather.add(new DefaultMutableTreeNode(ce.getNome()));
                    }
                    break;
                //No de documento
                case DOCUMENTO:
                    //List<TemplateDocumento> lstDoc = DocumentoDAO.getAll();
                    List<TemplateDocumento> lstDoc = new DocumentoServico().getAll();
                    //A cada Documento existente no bd, eh adicionado uma
                    //folha na arvore de Documeto
                    for (TemplateDocumento doc : lstDoc) {
                        nodeFather.add(new DefaultMutableTreeNode(doc.getNome()));
                    }
                    break;
                //No de caso de teste
                case TESTE_VALIDACAO:
                    //A cada caracterizacao de teste de validacao existente no bd, eh adicionado uma
                    //folha na arvore de Caso de teste
                    //List<CaracterizacaoTesteValidacao> lstTV = CaracterizacaoTstValidacaoDAO.getAll();
                    List<CaracterizacaoTesteValidacao> lstTV = new CaracterizacaoTesteValidacaoServico().getAll();
                    for (CaracterizacaoTesteValidacao tstVal : lstTV) {
                        nodeFather.add(new DefaultMutableTreeNode(tstVal.getNome()));
                    }
                    break;
                //No de suite
                case SUITE_VALIDACAO:
                    //A cada suite de validacao existente no bd, é adicionado um
                    //no na arvore de suite
                    List<SuiteTesteValidacao> lstSTV = new SuiteServico().getAll();
                    for (SuiteTesteValidacao suiteVal : lstSTV) {
                        DefaultMutableTreeNode nodeSuite = new DefaultMutableTreeNode(suiteVal.getNome());
                        nodeFather.add(nodeSuite);
                        //Cada execucao de teste de validacao relacionada a esta suite, possui um identificador de grupo
                        List<ExecucaoTesteValidacao> execs = ExecucaoTesteValidacaoDAO.getExecucoesTesteValidacaoPerGroup(suiteVal);
                        int idGrupoExec = -1;
                        DefaultMutableTreeNode nodeGrupo = null;
                        for (ExecucaoTesteValidacao exec : execs) {
                            if (idGrupoExec != exec.getIdGrupoExec()) {
                                //Cada grupo passa a ser um no de execucao da arvore suite
                                nodeGrupo = new ExecutionTreeNode("Grupo Exec" + exec.getIdGrupoExec(), exec.getModoAtivacao().equals("G"));
                                nodeSuite.add(nodeGrupo);
                            }
                            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            //cada execucao encontrada é uma folha da arvore de grupo
                            nodeGrupo.add(new ExecutionTreeNode(formatador.format(exec.getInicio()) + " " + exec.getId(), exec.getModoAtivacao().equals("G")));
                            idGrupoExec = exec.getIdGrupoExec();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        if (jTree1.getSelectionPath() != null) {
            selNode = (DefaultMutableTreeNode) jTree1.getSelectionPath().getLastPathComponent();
            //Caso seja a raiz da arvore
            if (selNode.isRoot()) {
                return;
            }
        } else {
            return;
        }

        if (evt.getClickCount() == 2) {
            tratarNo(selNode);
        }

        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            java.awt.Point location = evt.getLocationOnScreen();
            javax.swing.JPopupMenu pm = new javax.swing.JPopupMenu();
            javax.swing.JMenuItem m = null;

            int nPai = -1;
            nPai = selNode.getRoot().getIndex(selNode);

            System.out.println("indice dentro da Ferramenta Teste : " + nPai);

            if (nPai >= 0) {
                if (Entidade.values()[nPai] == Entidade.EXECUCAO) {
                    return;
                }

                //Hablitar popup de insercao
                m = new javax.swing.JMenuItem("Novo");
                m.setActionCommand("Inserir");
                m.addActionListener(this);
                pm.add(m);
            } //O nó selecionado não é folha da arvore Ferramenta Teste
            else {
                //nPai é obtido novamente
                nPai = selNode.getRoot().getIndex(selNode.getParent());

                //nPai ainda é negativo. casos que acontecem para as execucoes dentro de alguma suite
                if (nPai < 0) {
                    if (!selNode.isLeaf()) {
                        m = new javax.swing.JMenuItem("RemoverExecuções");
                        m.addActionListener(this);
                        m.setActionCommand("RemoverGrupoExecs");
                        pm.add(m);
                        pm.show(this, location.x, location.y);
                    } else {
                        m = new javax.swing.JMenuItem("Visualizar ativações");
                        m.addActionListener(this);
                        m.setActionCommand("VisualizarAtivacoes");
                        pm.add(m);
                        pm.show(this, location.x, location.y);
                    }
                    return;
                }

                m = new javax.swing.JMenuItem("Remover");
                m.addActionListener(this);
                m.setActionCommand("Remover");
                pm.add(m);

                m = new javax.swing.JMenuItem("Renomear");
                m.addActionListener(this);
                m.setActionCommand("Renomear");
                pm.add(m);

                m = new javax.swing.JMenuItem("Copiar");
                m.addActionListener(this);
                m.setActionCommand("CopiarComo");
                pm.add(m);

                if (Entidade.values()[nPai] == Entidade.SUITE_VALIDACAO) {
                    m = new javax.swing.JMenuItem("Executar");
                    m.addActionListener(this);
                    m.setActionCommand("Executar");
                    pm.add(m);

                    m = new javax.swing.JMenuItem("RemoverExecuções");
                    m.addActionListener(this);
                    m.setActionCommand("RemoverExec");
                    pm.add(m);
                }
            }
            pm.show(this, location.x, location.y);
        }
    }//GEN-LAST:event_jTree1MouseClicked

    public void disablePanels() {
        jPanel1 = new JPanel();
        jPanel1.setBounds(200, 0, PANEL_WIDTH, PANEL_HEIGHT);
        jPanel1.setVisible(true);
        jSplitPane1.setRightComponent(jPanel1);
    }

    public void addPanel(JPanel panel) {
        jSplitPane1.setRightComponent(panel);
    }

    private void tratarNo(DefaultMutableTreeNode selNode) {
        TreeNode parent = selNode.getParent();
        TreeNode root = ((DefaultMutableTreeNode) parent).getRoot();

        while (parent != root && (parent.getIndex(root) == -1)) {
            parent = parent.getParent();

            if (root.getIndex(parent) == Entidade.SUITE_VALIDACAO.ordinal()) {
                if (selNode.toString().contains("Grupo")) {
                    //selecionado um grupo de execucoes
                    //SuiteTesteValidacao suite = SuiteTesteValidacaoDAO.getSuiteTesteValidacao(selNode.getParent().toString());
                    SuiteTesteValidacao suite = new SuiteServico().getByName(selNode.getParent().toString());
                    currentExec.setRegistro(suite, selNode.toString());
                    currentExec.setVisible(true);
                    jSplitPane1.setRightComponent(currentExec);

                } else {
                    currentExec.setRegistro(selNode.toString());
                    currentExec.setVisible(true);
                    jSplitPane1.setRightComponent(currentExec);
                }
            }
        }

        parent = selNode.getParent();
        int nPai = root.getIndex(parent);

        //nPai é -1 para filhos da subarvore de suites
        if (nPai == -1) {
            return;
        }

        switch (Entidade.values()[nPai]) {
            case CLASSE_EQUIVALENCIA:
                currentCCE.setVisible(true);
                jSplitPane1.setRightComponent(currentCCE);
                currentCCE.setRegistro((String) selNode.getUserObject());
                break;
            case DOCUMENTO:
                currentDoc.setVisible(true);
                jSplitPane1.setRightComponent(currentDoc);
                currentDoc.setRegistro((String) selNode.getUserObject());
                break;
            case TESTE_VALIDACAO:
                currentTesteCase.setVisible(true);
                jSplitPane1.setRightComponent(currentTesteCase);
                currentTesteCase.setRegistro((String) selNode.getUserObject());
                break;
            case SUITE_VALIDACAO:
                currentSuiteTeste.setVisible(true);
                jSplitPane1.setRightComponent(currentSuiteTeste);
                currentSuiteTeste.setRegistro((String) selNode.getUserObject());
                break;
        }
    }

    private void removeNode() {

        TreeNode parent = selNode.getParent();
        TreeNode root = ((DefaultMutableTreeNode) parent).getRoot();

        int nPai = root.getIndex(parent);
        int retorno = 0;

        disablePanels();

        try {
            switch (Entidade.values()[nPai]) {
                case CLASSE_EQUIVALENCIA:
                    //retorno = ClasseEquivalenciaDAO.delete(selNode.getUserObject().toString());                
                    new ClasseEquivalenciaServico().delete(selNode.getUserObject().toString());
                    break;
                case DOCUMENTO:
                    // retorno = DocumentoDAO.delete(selNode.getUserObject().toString());
                    new DocumentoServico().delete(selNode.getUserObject().toString());
                    break;
                case TESTE_VALIDACAO:
                    new CaracterizacaoTesteValidacaoServico().delete(selNode.getUserObject().toString());
                    break;
                case SUITE_VALIDACAO:
                    new SuiteServico().delete(selNode.getUserObject().toString());
                    break;
                default:
                    System.out.println("FALTA IMPLEMENTAR RemoveNode");
                    break;
            }
        } catch (RollbackException e) {
            JOptionPane.showMessageDialog(this, "Erro de deleção" + "\n" + e.getMessage());
        }

        if (retorno >= 0) {
            DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
            model.removeNodeFromParent(selNode);
            atualizarCampos(Entidade.values()[nPai].toString());
        }
    }

    public void removeExecs(String golden) {
        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        TreeNode aChild;

        //remove todos os nos representando as execucoes da arvore da suite
        while (selNode.getChildCount() > 0) {
            aChild = selNode.getFirstChild();
            model.removeNodeFromParent((DefaultMutableTreeNode) aChild);
        }

        if (golden.equalsIgnoreCase("T")) {
            return;
        }

        SuiteTesteValidacao suite = new SuiteServico().getByName(selNode.getUserObject().toString());
        List<ExecucaoTesteValidacao> execs = ExecucaoTesteValidacaoDAO.getExecucoesTesteValidacao(suite);

        //Remover do bd somente a execução golden dessa suite pois, existe somente um golden por suite
        for (ExecucaoTesteValidacao exec : execs) {
            if (exec.getModoAtivacao().equalsIgnoreCase(golden)) {
                AtivacaoTesteValidacaoDAO.deleteByExecution(exec);
                ExecucaoTesteValidacaoDAO.delete(exec.getId().intValue());
            }
        }
    }

    private void removeExecs() {

        if (JOptionPane.YES_OPTION
                != JOptionPane.showConfirmDialog(this, "Deseja remover todas as execuções da suite " + selNode.getUserObject().toString(), "Remover Execuções", 2)) {
            return;
        }

        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        TreeNode aChild;
        while (selNode.getChildCount() > 0) {
            aChild = selNode.getFirstChild();
            model.removeNodeFromParent((DefaultMutableTreeNode) aChild);
        }

        SuiteTesteValidacao suite = new SuiteServico().getByName(selNode.getUserObject().toString());
        List<ExecucaoTesteValidacao> execs = ExecucaoTesteValidacaoDAO.getExecucoesTesteValidacao(suite);

        for (ExecucaoTesteValidacao exec : execs) {
            AtivacaoTesteValidacaoDAO.deleteByExecution(exec);
            ExecucaoTesteValidacaoDAO.delete(exec.getId().intValue());
        }

        //remover os arquivos.xml correspondentes as execucoes sendo removidas
        removeTestCaseFile(selNode.getUserObject().toString(), null);
    }

    private void removerGrupoExecs() {

        //So eh possivel a remocao de um grupo de execucoes caso estas nao sejam Golden       
        String execId = selNode.getFirstChild().toString().substring(selNode.getFirstChild().toString().length() - 4, selNode.getFirstChild().toString().length());
        ExecucaoTesteValidacao execucao = ExecucaoTesteValidacaoDAO.getExecucaoTesteValidacao(Integer.parseInt(execId));

        if (execucao.getModoAtivacao().equals("G")) {
            JOptionPane.showMessageDialog(this, "Este grupo é um grupo golden e não pode ser removido", "Remover Grupo de Execuções", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (JOptionPane.YES_OPTION
                != JOptionPane.showConfirmDialog(this, "Deseja remover este grupo de execuções da suite " + selNode.getUserObject().toString(), "Remover Grupo de Execuções", 2)) {
            return;
        }

        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
        TreeNode aChild;
        List<String> execucoes = new ArrayList<String>();
        while (selNode.getChildCount() > 0) {
            aChild = selNode.getFirstChild();
            String execIdentificador = selNode.getFirstChild().toString().substring(selNode.getFirstChild().toString().length() - 4, selNode.getFirstChild().toString().length());
            execucoes.add(execIdentificador);
            model.removeNodeFromParent((DefaultMutableTreeNode) aChild);
        }

        for (String idExe : execucoes) {
            AtivacaoTesteValidacaoDAO.deleteByExecution(ExecucaoTesteValidacaoDAO.getExecucaoTesteValidacao(Integer.parseInt(idExe)));
            ExecucaoTesteValidacaoDAO.delete(Integer.parseInt(idExe));
        }

        String suiteName = selNode.getParent().toString();

        model.removeNodeFromParent(selNode);

        //remover os arquivos.xml correspondentes as execucoes sendo removidas        
        removeTestCaseFile(suiteName, execucoes);

    }

    private void copiarNo() {
        String nomeNovo = (String) JOptionPane.showInputDialog(this, new JLabel("Novo nome: "), "Copiar Como", JOptionPane.YES_NO_CANCEL_OPTION);
        if (nomeNovo == null) {
            return;
        }

        TreeNode parent = selNode.getParent();
        TreeNode root = ((DefaultMutableTreeNode) parent).getRoot();

        int nPai = root.getIndex(parent);
        //int retorno = 0;
        String entidadeAtualizada = "";

        switch (Entidade.values()[nPai]) {
            case CLASSE_EQUIVALENCIA:
                //retorno = copiarClasseEquivalencia(selNode.getUserObject().toString(), nomeNovo);
                copiarClasseEquivalencia(selNode.getUserObject().toString(), nomeNovo);
                entidadeAtualizada = AtualizacaoTela.entidadeClasseEquivalencia;
                break;
            case DOCUMENTO:
                // retorno = copiarTemplateDocumento(selNode.getUserObject().toString(), nomeNovo);
                copiarTemplateDocumento(selNode.getUserObject().toString(), nomeNovo);
                entidadeAtualizada = AtualizacaoTela.entidadeDocumento;
                break;
            case TESTE_VALIDACAO:
                copiarValidacaoTeste(selNode.getUserObject().toString(), nomeNovo);
                entidadeAtualizada = AtualizacaoTela.entidadeTesteValidacao;
                break;
            case SUITE_VALIDACAO:
                copiarSuiteValidacao(selNode.getUserObject().toString(), nomeNovo);
                entidadeAtualizada = AtualizacaoTela.entidadeSuiteValidacao;
                break;
            default:
                System.out.println("FALTA IMPLEMENTAR CopiarNo");
                break;
        }

        selNode = (DefaultMutableTreeNode) parent;
        addNode(nomeNovo);
        atualizarCampos(entidadeAtualizada);

    }

    private void renomearNo() {
        //mostrar tela perguntando qual o novo nome do no
        //salvar o no com o novo nome
        String nomeNovo = (String) JOptionPane.showInputDialog(this, new JLabel("Novo nome: "), "Renomear nó", JOptionPane.YES_NO_CANCEL_OPTION);
        if (nomeNovo == null) {
            return;
        }

        TreeNode parent = selNode.getParent();
        TreeNode root = ((DefaultMutableTreeNode) parent).getRoot();

        int nPai = root.getIndex(parent);
        String entidadeAtualizada = "";

        switch (Entidade.values()[nPai]) {
            case CLASSE_EQUIVALENCIA:
                ClasseEquivalenciaServico ceServico = new ClasseEquivalenciaServico();
                if (null != ceServico.getByName(nomeNovo)) {
                    JOptionPane.showMessageDialog(this, "Classe de Equivalencia " + nomeNovo + " já existe.");
                    return;
                }
                ClasseEquivalencia ce = ceServico.getByName(selNode.getUserObject().toString());
                ce.setNome(nomeNovo);
                ceServico.save(ce);
                entidadeAtualizada = AtualizacaoTela.entidadeClasseEquivalencia;
                break;
            case DOCUMENTO:
                DocumentoServico docServico = new DocumentoServico();
                if (null != docServico.getByName(nomeNovo)) {
                    JOptionPane.showMessageDialog(this, "Documento " + nomeNovo + " já existe.");
                    return;
                }
                TemplateDocumento doc = docServico.getByName(selNode.getUserObject().toString());
                doc.setNome(nomeNovo);
                docServico.save(doc);
                entidadeAtualizada = AtualizacaoTela.entidadeDocumento;
                break;
            case TESTE_VALIDACAO:
                CaracterizacaoTesteValidacaoServico caractServico = new CaracterizacaoTesteValidacaoServico();
                if (null != caractServico.getByName(nomeNovo)) {
                    JOptionPane.showMessageDialog(this, "Teste de Validação " + nomeNovo + " já existe.");
                    return;
                }
                CaracterizacaoTesteValidacao tstVal = caractServico.getByName(selNode.getUserObject().toString());
                tstVal.setNome(nomeNovo);
                caractServico.save(tstVal);
                entidadeAtualizada = AtualizacaoTela.entidadeTesteValidacao;
                break;
            case SUITE_VALIDACAO:
                SuiteServico suiteServico = new SuiteServico();
                if (null != suiteServico.getByName(nomeNovo)) {
                    JOptionPane.showMessageDialog(this, "Suite de Teste de Validação " + nomeNovo + " já existe.");
                    return;
                }
                SuiteTesteValidacao suiteVal = suiteServico.getByName(selNode.getUserObject().toString());
                suiteVal.setNome(nomeNovo);
                suiteServico.save(suiteVal, null);
                entidadeAtualizada = AtualizacaoTela.entidadeSuiteValidacao;
                break;
            default:
                System.out.println("FALTA IMPLEMENTAR RenomearNode");
                break;
        }


        removeNode();
        selNode = (DefaultMutableTreeNode) parent;
        addNode(nomeNovo);
        atualizarCampos(entidadeAtualizada);

    }

    private void copiarClasseEquivalencia(String nomeNo, String novoNome) {

        ClasseEquivalenciaServico ceServico = new ClasseEquivalenciaServico();

        if (null != ceServico.getByName(novoNome)) {
            int iSelected = JOptionPane.showConfirmDialog(this, "Classe de Equivalencia " + novoNome + " já existe. Deseja sobrescrevê-la?", "Sobrescrever entidade", 2);
            if (iSelected != JOptionPane.YES_OPTION) {
                return;
            }
        }

        ClasseEquivalencia ce = ceServico.getByName(nomeNo);
        ClasseEquivalencia ceCopia = new ClasseEquivalencia();
        ceCopia.setAtributoCollection(ce.getAtributoCollection());
        ceCopia.setClasseEquivalenciaCollection(ce.getClasseEquivalenciaCollection());
        ceCopia.setComentario(ce.getComentario());
        ceCopia.setHeranca(ce.getHeranca());
        ceCopia.setNome(novoNome);
        ceCopia.setTipo(ce.getTipo());

        Collection<Valor> novosValores = new LinkedList<Valor>();
        Iterator it = ce.getValorCollection().iterator();
        while (it.hasNext()) {
            Valor valorSalvo = (Valor) it.next();
            Valor valor = new Valor();
            valor.setIdClasseEquivalencia(ceCopia);
            valor.setComentario(valorSalvo.getComentario());
            valor.setPositivoNegativo(valorSalvo.getPositivoNegativo());
            valor.setValor(valorSalvo.getValor());
            valor.setOrderId(valorSalvo.getOrderId());
            novosValores.add(valor);
        }


        ceCopia.setValorCollection(novosValores);
        ceServico.save(ceCopia);
    }

    private void copiarTemplateDocumento(String nomeNo, String novoNome) {

        DocumentoServico docServico = new DocumentoServico();

        if (null != docServico.getByName(novoNome)) {
            if (JOptionPane.YES_OPTION
                    != JOptionPane.showConfirmDialog(this, "Documento " + novoNome + " já existe. Deseja sobrescrevê-lo?", "Sobrescrever entidade", 2)) {
            }
        }

        TemplateDocumento doc = new DocumentoServico().getByName(nomeNo);

        TemplateDocumento docCopia = new TemplateDocumento();
        docCopia.setArquivoXsd(doc.getArquivoXsd());
        docCopia.setDirecao(doc.getDirecao());
        docCopia.setNome(novoNome);
        docCopia.setTipoFisico(doc.getTipoFisico());


        Collection<Atributo> novosAtributos = new LinkedList<Atributo>();
        Iterator itAtributo = doc.getAtributoCollection().iterator();
        while (itAtributo.hasNext()) {
            Atributo atributoLido = (Atributo) itAtributo.next();
            Atributo atributo = new Atributo();
            atributo.setIdClasseEquivalencia(atributoLido.getIdClasseEquivalencia());
            atributo.setIdTemplateDocumento(docCopia);
            atributo.setNumeroMaximoOcorrencias(atributoLido.getNumeroMaximoOcorrencias());
            atributo.setOpcional(atributoLido.getOpcional());
            atributo.setTag(atributoLido.getTag());
            atributo.setOrderId(atributoLido.getOrderId());
            novosAtributos.add(atributo);
        }
        docCopia.setAtributoCollection(novosAtributos);

        Collection<Regra> novasRegras = new LinkedList<Regra>();
        Iterator itRegras = doc.getRegraCollection().iterator();
        while (itRegras.hasNext()) {
            Regra regraLida = (Regra) itRegras.next();
            Regra regra = new Regra();
            regra.setOrderId(regraLida.getOrderId());
            regra.setComentario(regraLida.getComentario());
            regra.setEntaoAtributo(regraLida.getEntaoAtributo());
            regra.setEntaoRelacao(regraLida.getEntaoRelacao());
            regra.setEntaoValor(regraLida.getEntaoValor());
            regra.setIdTemplateDocumento(docCopia);
            regra.setSeAtributo(regraLida.getSeAtributo());
            regra.setSeRelacao(regraLida.getSeRelacao());
            regra.setSeValor(regraLida.getSeValor());
            novasRegras.add(regra);

        }
        docCopia.setRegraCollection(novasRegras);

        docServico.save(docCopia);
    }

    private void copiarValidacaoTeste(String nomeNo, String novoNome) {

        CaracterizacaoTesteValidacaoServico caractServico = new CaracterizacaoTesteValidacaoServico();

        if (null != caractServico.getByName(novoNome)) {
            if (JOptionPane.YES_OPTION
                    != JOptionPane.showConfirmDialog(this, "Teste de Validação " + novoNome + " já existe. Deseja sobrescrevê-lo?", "Sobrescrever entidade", 2)) {
                return;
            }
        }

        CaracterizacaoTesteValidacao tstVal = caractServico.getByName(nomeNo);

        CaracterizacaoTesteValidacao tstValCopia = new CaracterizacaoTesteValidacao();
        tstValCopia.setNome(novoNome);
        tstValCopia.setCasosNegativos(tstVal.getCasosNegativos());
        tstValCopia.setCasosOpcionais(tstVal.getCasosOpcionais());
        tstValCopia.setCasosPositivos(tstVal.getCasosPositivos());
        tstValCopia.setCasosRegras(tstVal.getCasosRegras());
        tstValCopia.setCasosRepeticoes(tstVal.getCasosRepeticoes());
        tstValCopia.setClasseValidacaoSaidaNegativa(tstVal.getClasseValidacaoSaidaNegativa());
        tstValCopia.setClasseValidacaoSaidaPositiva(tstVal.getClasseValidacaoSaidaPositiva());
        tstValCopia.setDocumentoEntrada(tstVal.getDocumentoEntrada());
        tstValCopia.setDocumentoSaidaPositiva(tstVal.getDocumentoSaidaPositiva());
        tstValCopia.setDocumentoSaidaNegativa(tstVal.getDocumentoSaidaNegativa());
        tstValCopia.setXsdSaidaNegativa(tstVal.getXsdSaidaNegativa());
        tstValCopia.setXsdSaidaPositiva(tstVal.getXsdSaidaPositiva());

        Collection<Especificos> novosEspecificos = new LinkedList<Especificos>();
        Iterator it = tstVal.getEspecificosCollection().iterator();
        while (it.hasNext()) {
            Especificos especificoLido = (Especificos) it.next();
            Especificos especificoCopia = new Especificos();
            especificoCopia.setOrderId(especificoLido.getOrderId());
            especificoCopia.setAtributo(especificoLido.getAtributo());
            especificoCopia.setIdCaracterizacaoTesteValidacao(tstValCopia);
            especificoCopia.setQuantidade(especificoLido.getQuantidade());
            especificoCopia.setTipo(especificoLido.getTipo());

            novosEspecificos.add(especificoCopia);
        }


        tstValCopia.setEspecificosCollection(novosEspecificos);
        caractServico.save(tstValCopia);


    }

    private void copiarSuiteValidacao(String nomeNo, String novoNome) {

        SuiteServico suiteServico = new SuiteServico();
        if (null != suiteServico.getByName(novoNome)) {
            if (JOptionPane.YES_OPTION
                    != JOptionPane.showConfirmDialog(this, "Suite de Teste de Validação " + novoNome + " já existe. Deseja sobrescrevê-la?", "Sobrescrever entidade", 2)) {
                return;
            }
        }


        List<SuiteValidacaoTesteValidacao> listSVTV = suiteServico.getAllSuiteValTesteVal(nomeNo);
        for (SuiteValidacaoTesteValidacao svtvLido : listSVTV) {
            SuiteValidacaoTesteValidacao svctstVal = new SuiteValidacaoTesteValidacao();
            //  svctstVal.setSuiteTesteValidacao(null);
            svctstVal.setCaracterizacaoTesteValidacao(svtvLido.getCaracterizacaoTesteValidacao());
            svctstVal.setOrderId(svtvLido.getOrderId());
            svctstVal.setResult(svtvLido.getResult());
            svctstVal.setTestCase(svtvLido.getTestCase());
            svctstVal.setWorkflow(svtvLido.getWorkflow());
        }

        //SuiteTesteValidacao suiteVal = SuiteTesteValidacaoDAO.getSuiteTesteValidacao(nomeNo);

        SuiteTesteValidacao suiteValCopia = new SuiteTesteValidacao();
        suiteValCopia.setNome(novoNome);

        suiteServico.save(suiteValCopia, listSVTV);

    }

    private void removeTestCaseFile(String suiteName, List<String> execucoes) {

        List<SuiteValidacaoTesteValidacao> listSuiteValCarcVal = new SuiteServico().getAllSuiteValTesteVal(suiteName);
        for (SuiteValidacaoTesteValidacao svtv : listSuiteValCarcVal) {
            String testCase = svtv.getTestCase();
            File dir = new File(svtv.getTestCase());
            //a lista de execucoes sendo nula, remove-se todas as execucoes do diretorio de testCase            
            if (execucoes == null) {
                for (File file : dir.listFiles()) {
                    file.delete();
                }
            }
            //remove-se somente os arquivos identificado pelo item em execucoes
            else {
                Iterator it = execucoes.iterator();
                while (it.hasNext()) {
                    String execId = it.next().toString();
                    for (File file : dir.listFiles()) {
                        if (file.getName().contains(execId)) {
                            file.delete();
                            break;
                        }
                    }
                }
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
