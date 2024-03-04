package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static Registry reg;
    public static int port = 10001;

    static RoomService roomService;


    public static void main(String[] args) {

        parseTerminalParams(args);

        try{
            roomService = new RoomService();
            reg = LocateRegistry.createRegistry(port);
            reg.rebind("RoomService", roomService);
            roomService.createRoom("123", "test");

            System.out.println("Tic-Tac-Toe Server is ready on port " + port);

            ServerSocket serverSocket = new ServerSocket(port+1);



            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                
                Thread clientThread = new SpectatorThread(clientSocket);
                clientThread.start();
            }






        }
        catch(Exception e){
            System.out.println("Server failed " + e.getMessage());
        }



    }


    private static void parseTerminalParams(String[] args){

        if(args.length == 0)
            return;

        if(args[0].equals("--help")){
            System.out.println("""
                    usage: <name_of_jar>.jar [-p port]
                            [port]: port number on which server will be hosted, default is 10001
                    """);
            System.exit(0);
        }
        else if(args[0].startsWith("p")){
            String portString = args[1];

            try{
                port = Integer.parseInt(portString);
            }
            catch(NumberFormatException e){
                System.out.println("Cannot convert given port to integer");
                System.exit(-1);
            }
        }

    }
}
