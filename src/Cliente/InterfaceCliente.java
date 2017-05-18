/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import javax.swing.*;

/**
 *
 * @author Gevaldo
 */
public class InterfaceCliente extends JFrame{
    
    JButton enviar;
    JTextField nome, ip;
    
    
    
    public InterfaceCliente(){
        super("Janela");
        
        
        
        
        
        
        setSize(200,600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(null);
    }
    
}
