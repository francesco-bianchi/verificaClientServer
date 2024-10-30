package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", 3000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        boolean presente = false;
        String messaggio="";
        String messServer;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("inserire il proprio username:");
            String username = sc.nextLine();
            out.writeBytes(username + "\n");
            String apprUsername = in.readLine();

            if(apprUsername.equals("n")){
                System.out.println("username non disponibile");
                presente = true;
            }
            else{
                System.out.println("username disponibile");
                presente = false;
            }
        } while (presente);

        do {
            System.out.println("-----Menu-----");
            System.out.println("Disponibilità (disp)");
            System.out.println("Acquista (acq)");
            System.out.println("Uscire (usc)");

            messaggio = sc.nextLine();
            if(messaggio.equals("disp")){
                out.writeBytes("n\n");
                messServer = in.readLine();
                System.out.println("Inserire lato stadio(gold, pit, parterre)");
                String s = sc.nextLine();
                out.writeBytes(s + "\n");
                messServer = in.readLine();
                System.out.println(messServer);
            }
            else if(messaggio.equals("acq")){
                out.writeBytes("buy\n");
                messServer = in.readLine();
                if(messServer.equals("i")){
                    System.out.println("Inserire lato stadio (gold, pit, parterre)");
                    String s = sc.nextLine();
                    out.writeBytes(s + "\n"); //scrittura lato stadio

                    String mess2 = in.readLine();
                    if(mess2.equals("a")){
                        System.out.println("Inserire la quantità di biglietti");
                        String t = sc.nextLine();
                        out.writeBytes(t + "\n");
                        String messBiglietti = in.readLine();
                        if(messBiglietti.equals("OK")){
                            System.out.println("Biglietti acquistati");
                        }
                        else if(messBiglietti.equals("KO")){
                            System.out.println("Biglietti non disponibili");
                        }
                    }
                    
                }
            }
            else if(messaggio.equals("usc")){
                out.writeBytes("quit\n");
                
            }
            else{
                System.out.println("Scegli un opzione");
            }
            
        } while (!messaggio.equals("usc"));
        
    }
}