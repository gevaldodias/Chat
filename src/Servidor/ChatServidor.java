/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.List;
//import java.awt.List;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Gevaldo
 */
public class ChatServidor {
    Socket socket;
    Hashtable<InetAddress, PrintWriter> cliente;
    Hashtable<String,Hashtable> salas;
    String[] nomesSalas = {"Redes I", "Redes II", "SD"};
    Scanner leitor;
        
    public ChatServidor() throws IOException{
        cliente = new Hashtable<>();
        salas = new Hashtable<>();
        salas.put("Redes I", cliente);
        salas.put("Redes II", cliente);
        salas.put("SD", cliente);
        
        ServerSocket server = new ServerSocket(6789);        
        while(true){
           socket = server.accept();
           new Thread(new EscutaCliente(socket)).start();
           PrintWriter p = new PrintWriter(socket.getOutputStream());
           for(String elemento : salas.keySet()){
                   if(elemento.equals("Redes I")){
                       System.out.println(elemento);                       
                       cliente.put(socket.getInetAddress(),p);
                       System.out.print(cliente.toString());
                       break;
                    }else if (elemento.equals("Redes II")){
                        System.out.println(elemento);
                        cliente.put(socket.getInetAddress(), p);
                        System.out.print(cliente.toString());
                        break;                        
                    } else if(elemento.equals("SD")){
                        System.out.println(elemento);
                        cliente.put(socket.getInetAddress(), p);
                        System.out.print(cliente.toString());
                        break;
                    }//fim do else if de Redes I
           }//fim do for que busca a sala para adiconar os clientes
        }//fim do while true
    }//fim do construtor ChatServidor
    
    private void EncaminharParaTodos(String texto){    
        for(String sala : salas.keySet()){
            if(sala.toString().equals("Redes I")){
                for(Object ip : salas.get(sala).keySet()){              
                    try{
                        System.out.println(sala.toString()); 
                        if(!ip.toString().equals(socket.getInetAddress().toString())){// envia para todos para todos da sala Redes II, exceto quem enviou
                            cliente.get(ip).println(texto);
                            cliente.get(ip).flush();
                        }//fim do if que envia para todos da sala Redes II
                    }catch(Exception e){}
                }
                break;
            }else if(sala.toString().equals("Redes II")){
                for(Object ip : salas.get(sala).keySet()){              
                    try{
                        System.out.println(sala.toString()); 
                        if(!ip.toString().equals(socket.getInetAddress().toString())){// envia para todos para todos da sala Redes II, exceto quem enviou
                            cliente.get(ip).println(texto);
                            cliente.get(ip).flush();
                        }//fim do if que envia para todos da sala Redes II
                    }catch(Exception e){}
                }
                break;
            }else if(sala.toString().equals("SD")){
                for(Object ip : salas.get(sala).keySet()){              
                    try{
                        System.out.println(sala.toString()); 
                        if(!ip.toString().equals(socket.getInetAddress().toString())){// envia para todos para todos da sala Redes II, exceto quem enviou
                            cliente.get(ip).println(texto);
                            cliente.get(ip).flush();
                        }//fim do if que envia para todos da sala Redes II
                    }catch(Exception e){}
                }
                break;
            }
        }//fim do for que busca as salas que existem
    }//fim do metodo escuta cliente
    
    private class EscutaCliente implements Runnable {
        
        
        public EscutaCliente(Socket socket) throws IOException{
             leitor = new Scanner (socket.getInputStream());
        }

        @Override
        public void run() {
            try{
                String texto;
                while((texto = leitor.nextLine()) != null){
                    System.out.println(texto);
                    EncaminharParaTodos(texto);
                }
            }catch(Exception x){}
         
        }
        
    }
}
