/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;

/**
 *
 * @author Gevaldo
 */
public class ChatCliente extends JFrame {
    
    JTextField textoParaEnviar;
    JButton botaoEnviar;
    Socket socket;
    PrintWriter escritor;
    String nome;
    String sala;
    JTextArea textoRecebido;
    Scanner leitor;
    
    private class EscutaServidor implements Runnable{

        @Override
        public void run() {
            try{
                String texto;
                while((texto = leitor.nextLine() )!= null){
                    textoRecebido.append(texto + "\n");
                }
            }catch(Exception x){
                System.out.print("EMMA ROBERTS");
            }
            
        }
        
    }
    
    public ChatCliente(String nome, String sala) throws IOException{
        super("Chat: "+ nome);
        this.nome = nome;
        this.sala = sala;
        
        Font fonte = new Font("Serif", Font.PLAIN, 26);
        textoParaEnviar = new JTextField(); 
        textoParaEnviar.setFont(fonte);
        botaoEnviar = new JButton("Enviar"); botaoEnviar.addActionListener(new EnviarListener());
        botaoEnviar.setFont(fonte);
        
        Teclado enter = new Teclado();
        textoParaEnviar.addActionListener(enter);
        
       
                
        Container envio = new JPanel();
        envio.setLayout(new BorderLayout());
        envio.add(BorderLayout.CENTER, textoParaEnviar);
        envio.add(BorderLayout.EAST, botaoEnviar);
        
        
        
        textoRecebido = new JTextArea();
        textoRecebido.setFont(fonte);
        JScrollPane scroll = new JScrollPane(textoRecebido);
        
        getContentPane().add(BorderLayout.CENTER, scroll);
        getContentPane().add(BorderLayout.SOUTH, envio);
        
        ConfigurarRede();
        
        setSize(500,600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
    }
    
    private class EnviarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            escritor.println(nome + " : " +textoParaEnviar.getText());
            escritor.flush();
            textoParaEnviar.setText("");
            textoParaEnviar.requestFocus();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private class Teclado implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            escritor.println(nome + " : " +textoParaEnviar.getText());
            escritor.flush();
            textoParaEnviar.setText("");
            textoParaEnviar.requestFocus();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
          
    public void ConfigurarRede() throws IOException{
         socket = new Socket("127.0.0.1", 6789);
         escritor = new PrintWriter(socket.getOutputStream());
         leitor = new Scanner(socket.getInputStream());
         new Thread(new EscutaServidor()).start();
    }
}
