package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3000);
        System.out.println("Server connesso");
        ArrayList user = new ArrayList<String>();
        int ticketGold = 100;
        int ticketPit = 100;
        int ticketParterre = 100;
        TicketGold tGold = new TicketGold(ticketGold);
        TicketParterre tPart = new TicketParterre(ticketParterre);
        TicketPit tPit = new TicketPit(ticketPit);
        
        
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connesso");
            GestoreServer gs = new GestoreServer(socket, user, tGold, tPit, tPart);
            gs.start();
        }
    }
}