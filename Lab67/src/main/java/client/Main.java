package client;

import server.RoomServiceInterface;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Function;

public class Main {



    static String playerToken;

    static Socket spectatorSocket;


    static RoomServiceInterface roomService;

    static String connectedRoomToken = "";

    static String host;

    static int port;


    public static void main(String[] args) {
        try{

            Main.host = "127.0.0.1";
            Main.port = 10001;

            Main.spectatorSocket = new Socket(Main.host, Main.port+1);
            Registry registry = LocateRegistry.getRegistry(host, port);
            roomService = (RoomServiceInterface) registry.lookup("RoomService");


            Scanner scanner = new Scanner(System.in);
            UI UIHandler = new UI();

            


            System.out.print("Enter your player name: ");
            playerToken = UUID.randomUUID() + "@" + scanner.nextLine();

            System.out.println();

            UI.FrameName frame = UI.FrameName.MAIN_MENU;
            while (true) {
                frame = UIHandler.renderFrameByName(frame, scanner);
            }

        }
        catch(Exception e){
            System.out.println(" Client failed " + e);
            e.printStackTrace();
        }
    }

    static String sendSocketMessage(String message, String roomToken) throws IOException {

        // odbieranie
        BufferedReader reader = new BufferedReader(new InputStreamReader(Main.spectatorSocket.getInputStream(), StandardCharsets.UTF_8));
        // wysyłanie
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Main.spectatorSocket.getOutputStream(), StandardCharsets.UTF_8));

        writer.write(message+":"+roomToken);
        writer.newLine();
        writer.flush();

        return reader.readLine();

    }


}
