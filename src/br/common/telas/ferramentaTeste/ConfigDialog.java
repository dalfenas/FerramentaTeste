/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.common.telas.ferramentaTeste;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class ConfigDialog extends JDialog { //implements ActionListener {
    private JPanel myPanel = null;
    private boolean answer = false;
    public boolean getAnswer() { 
        return ((ConfigPanel)myPanel).getAnswer();
    }
   
    //public ConfigDialog(JFrame frame, boolean modal, String myMessage) {
    public ConfigDialog(JFrame frame, boolean modal) {
        super(frame, modal);
        myPanel = new ConfigPanel(this);
        getContentPane().add(myPanel);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
    
}
