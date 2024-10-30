package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class GestoreServer extends Thread {
    Socket socket;
    ArrayList user;
    TicketGold ticketGold;
    TicketParterre ticketParterre;
    TicketPit ticketPit;

    GestoreServer(Socket socket, ArrayList<String> user, TicketGold ticketGold, TicketPit ticketPit,
            TicketParterre ticketParterre) {
        this.socket = socket;
        this.user = user;
        this.ticketGold = ticketGold;
        this.ticketPit = ticketPit;
        this.ticketParterre = ticketParterre;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            boolean presente = false;
            String messaggioClient;
            String username = "";

            do {
                presente = false;
                username = in.readLine();

                if (user.contains(username)) {
                    out.writeBytes("n" + "\n");
                    presente = true;
                } else {
                    user.add(username);
                    out.writeBytes("s" + "\n");
                }

            } while (presente);

            do {
                messaggioClient = in.readLine();

                if (messaggioClient.equals("n")) {
                    out.writeBytes("s\n");
                    String s = in.readLine();
                    if (s.equals("gold")) {
                        System.out.println("Gold: " + ticketGold.getTicket());
                        out.writeBytes(ticketGold.getTicket() + "\n");
                    } else if (s.equals("pit")) {
                        System.out.println("Pit: " +ticketPit.getTicket());
                        out.writeBytes(ticketPit.getTicket() + "\n");
                    } else if (s.equals("parterre")) {
                        System.out.println("Parterre: " +ticketParterre.getTicket());
                        out.writeBytes(ticketParterre.getTicket() + "\n");
                    }
                }

                else if (messaggioClient.equals("buy")) {
                    out.writeBytes("i\n");
                    String s = in.readLine(); //lettura lato
                    if (s.equals("gold")) {
                        out.writeBytes("a\n"); //accettata
                        String t = in.readLine();
                        if (ticketGold.getTicket() - Integer.parseInt(t) >= 0) {
                            ticketGold.setTicket(ticketGold.getTicket() - Integer.parseInt(t));
                            System.out.println("Rimanenti Gold: " +ticketGold.getTicket());
                            out.writeBytes("OK\n");
                        } else {
                            out.writeBytes("KO\n");
                        }
                    } else if (s.equals("pit")) {
                        out.writeBytes("a\n"); //accettata
                        String t = in.readLine();
                        if (ticketPit.getTicket() - Integer.parseInt(t) >= 0) {
                            ticketPit.setTicket(ticketPit.getTicket() - Integer.parseInt(t));
                            System.out.println("Rimanenti Pit: " +ticketPit.getTicket());
                            out.writeBytes("OK\n");
                        } else {
                            out.writeBytes("KO\n");
                        }
                    } else if (s.equals("parterre")) {
                        out.writeBytes("a\n"); //accettata
                        String t = in.readLine();
                        if (ticketParterre.getTicket() - Integer.parseInt(t) >= 0) {
                            ticketParterre.setTicket(ticketParterre.getTicket() - Integer.parseInt(t));
                            System.out.println("Rimanenti Parterre: " +ticketParterre.getTicket());
                            out.writeBytes("OK\n");
                        } else {
                            out.writeBytes("KO\n");
                        }
                    }
                    else{
                        out.writeBytes("KO\n");
                    }

                } else if (messaggioClient.equals("quit")) {
                    System.out.println("client disconnesso");
                }

            } while (!messaggioClient.equals("quit"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
