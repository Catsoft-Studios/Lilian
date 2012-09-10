
package lilian;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

class ViewController extends JFrame
{
    /** VARIABLES **/
    
    DomainController domain;
    
    JButton clean;
    JTextField input;
    TextArea terminal;
    JButton askButton;
    
    /** CONSTRUCTOR **/
    
    public ViewController() 
    {
        this.domain = new DomainController();
        
        this.clean = new JButton("Clean");
        this.input = new JTextField();
        this.askButton = new JButton("Ask!");
        this.terminal = new TextArea("> ", 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        
        this.ConfigureElements();
        
        this.setTitle("Lilian 4.0 - PreAlpha");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
    }
    
    /** CONFIGURATION **/
    
    private void ConfigureElements()
    {
        // Set elements info:
        this.input.setText("");
        this.input.setColumns(10);
        this.input.requestFocusInWindow();
        
        this.clean.addActionListener(new CleanButton());
        
        this.terminal.setEditable(false);
        
        // event listeners:
        this.askButton.addActionListener(new AskButton());
        
        // define layout:
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(this.clean, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 4;
        panel.add(this.terminal, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(this.input, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(this.askButton, gbc);
        
        this.add(panel);
    }
    
    /** ACTION LISTENERS **/
    
    private class AskButton implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            String text_in  = input.getText();
            String text_out = domain.Ask(text_in);
            
            String output = domain.process + "\n> Final answer is:\n\n";
            terminal.setText(output+"    "+text_out+"\n\n");
            input.requestFocusInWindow();
        }
    }
    
    private class CleanButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            terminal.setText("> ");
            input.requestFocusInWindow();
        }
    }
    
}
