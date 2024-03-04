package client;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

public class UI {

    public static enum FrameName {
        MAIN_MENU, JOIN_ROOM, CREATE_ROOM, DELETE_ROOM, ROOM_MAIN, LIST_ROOMS, SPECTATOR_MENU
    }

    public FrameName renderFrameByName(FrameName frameName, Scanner scan) throws IOException, InterruptedException {
        switch (frameName){
            case MAIN_MENU -> {return this.mainMenuUI(scan);}
            case LIST_ROOMS -> {return this.listRoomsUI(scan);}
            case CREATE_ROOM -> {return this.createRoomUI(scan);}
            case JOIN_ROOM -> {return this.joinRoomUI(scan);}
            case ROOM_MAIN -> {return this.roomMainUI(scan);}
            case SPECTATOR_MENU -> {return  this.spectatorMenuUI(scan);}
            case DELETE_ROOM -> {return this.deleteRoomUI(scan);}

        }

        return FrameName.MAIN_MENU;
    }

    private FrameName mainMenuUI(Scanner scan){
        this.clearConsole();


        System.out.println("========================================================");
        System.out.println("Player: " + Main.playerToken);
        System.out.println("========================================================");
        System.out.println();
        System.out.println("1. Create Room");
        System.out.println("2. Join Room");
        System.out.println("3. List Rooms");
        System.out.println("4. Spectator mode");
        System.out.println("5. Delete room");
        System.out.println();
        System.out.println("Type 'exit' to end the program");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();
        int parsedOption = 0;

        try{
            parsedOption = this.validateOption(choice, new int[]{1, 2, 3, 4,5});
            switch (parsedOption) {
                case 1 -> {return FrameName.CREATE_ROOM;}
                case 2 -> {return FrameName.JOIN_ROOM;}
                case 3 -> {return FrameName.LIST_ROOMS;}
                case 4 -> {return FrameName.SPECTATOR_MENU;}
                case 5 -> {return FrameName.DELETE_ROOM;}
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return FrameName.MAIN_MENU;
    }

    private FrameName listRoomsUI(Scanner scan) throws RemoteException {
        this.clearConsole();


        System.out.println("========================================================");
        System.out.println("Player: " + Main.playerToken);
        System.out.println("========================================================");
        System.out.println();
        System.out.println("1. Create Room");
        System.out.println("2. Join Room");
        System.out.println("3. List Rooms");
        System.out.println("4. Spectator mode");
        System.out.println("5. Delete Room");
        System.out.println();
        System.out.println("Type 'exit' to end the program");

        System.out.println("Rooms: ");
        System.out.println("--------------");
        System.out.println(Main.roomService.listRooms());
        System.out.println();

        System.out.print("Choose an option: ");
        String choice = scan.nextLine();
        int parsedOption = 0;

        try{
            parsedOption = this.validateOption(choice, new int[]{1, 2, 3, 4, 5});
            switch (parsedOption) {
                case 1 -> {return FrameName.CREATE_ROOM;}
                case 2 -> {return FrameName.JOIN_ROOM;}
                case 3 -> {return FrameName.LIST_ROOMS;}
                case 4 -> {return FrameName.SPECTATOR_MENU;}
                case 5 -> {return FrameName.DELETE_ROOM;}
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return FrameName.MAIN_MENU;
    }

    private FrameName createRoomUI(Scanner scan) throws RemoteException {
        this.clearConsole();


        System.out.println("========================================================");
        System.out.println("Player: " + Main.playerToken);
        System.out.println("========================================================");
        System.out.println();
        System.out.println("Provide a room name: ");
        String name = scan.nextLine();
        System.out.println("Provide password: ");
        String password = scan.nextLine();

        System.out.println(Main.roomService.createRoom(password, name));
        System.out.println();
        System.out.println("1. Main menu");
        System.out.println("2. Join Room");
        System.out.println("3. List Rooms");
        System.out.println("4. Spectator mode");
        System.out.println("5. Delete Room");
        System.out.println();
        System.out.println("Type 'exit' to end the program");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();
        int parsedOption = 0;

        try{
            parsedOption = this.validateOption(choice, new int[]{1, 2, 3, 4, 5});
            switch (parsedOption) {
                case 1 -> {return FrameName.MAIN_MENU;}
                case 2 -> {return FrameName.JOIN_ROOM;}
                case 3 -> {return FrameName.LIST_ROOMS;}
                case 4 -> {return FrameName.SPECTATOR_MENU;}
                case 5 -> {return FrameName.DELETE_ROOM;}
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return FrameName.MAIN_MENU;
    }

    private FrameName joinRoomUI(Scanner scan) throws RemoteException, InterruptedException {
        this.clearConsole();


        System.out.println("========================================================");
        System.out.println("Player: " + Main.playerToken);
        System.out.println("========================================================");
        System.out.println();
        System.out.println("Rooms: ");
        System.out.println("--------------");
        System.out.println(Main.roomService.listRooms());
        System.out.println();
        System.out.println("Provide a room token: ");
        String token = scan.nextLine();
        System.out.println("Provide password: ");
        String password = scan.nextLine();

        System.out.println(Main.roomService);
        int joinStatus = Main.roomService.joinRoom(Main.playerToken, token, password);

        if(joinStatus > 0){
            Main.connectedRoomToken = token;
        }

        switch (joinStatus){
            case -2 -> {return this.roomErrorUISpecial(scan, "There are currently 2 players, you can only spectate", token);}
            case -1 -> {return this.roomErrorUISpecial(scan, "Invalid password to room", token);}
            case 0 -> {return this.roomErrorUISpecial(scan, "Couldn't find a room with given token", token);}
            case 1 -> {return this.roomSoloUISpecial(scan);}
        }



        return FrameName.ROOM_MAIN;
    }



    private FrameName roomSoloUISpecial(Scanner scan) throws RemoteException, InterruptedException {
        this.clearConsole();
        System.out.println("========================================================");
        System.out.println("Room: " + Main.connectedRoomToken);
        System.out.println("Player: " + Main.playerToken);
        System.out.println("========================================================");
        System.out.println();
        System.out.println("Waiting for other player to join..");

        int iteration = 0;
        int playerNumber = 1;
        while(true){

            playerNumber = Main.roomService.checkPlayersInRoom(Main.connectedRoomToken);
            if(playerNumber >= 2){
                return FrameName.ROOM_MAIN;
            }

            Thread.sleep(1000);
            if(++iteration % 15 == 0){
                System.out.println("Do you wish to continue waiting? <Y><N>");
                String choice = scan.nextLine();
                if(choice.contains("N"))
                    return FrameName.MAIN_MENU;
            }
        }
    }

    private FrameName roomErrorUISpecial(Scanner scan, String message, String room) throws RemoteException{
        this.clearConsole();
        System.out.println("========================================================");
        System.out.println("Player: " + Main.playerToken);
        System.out.println("========================================================");
        System.out.println();
        System.out.println("An error ocurred when trying to connect to room " + room);
        System.out.println(message);
        System.out.println();
        System.out.println("1. Main menu");
        System.out.println();
        System.out.println("Type 'exit' to end the program");
        System.out.print("Choose an option: ");
        String choice = scan.nextLine();
        int parsedOption = 0;

        parsedOption = this.validateOption(choice, new int[]{1, 2, 3, 4});
        switch (parsedOption) {
            case 1 -> {return FrameName.MAIN_MENU;}
        }

        return FrameName.MAIN_MENU;
    }

    private FrameName spectatorMenuUI(Scanner scan) throws RemoteException {
        this.clearConsole();
        System.out.println("========================================================");
        System.out.println("Entered a spectator mode");
        System.out.println("You can connect and view games between 2 players in chosen room");
        System.out.println("Rooms: ");
        System.out.println("--------------");
        System.out.println(Main.roomService.listRooms());
        String roomToken = scan.nextLine();

        boolean roomExists = Main.roomService.hasRoomWithToken(roomToken);

        if(!roomExists){
            System.out.println();
            System.out.println("There is no room with that token");
            System.out.println("1. Continue");
            System.out.println("Type anything else to go to the main menu");
            String choice1 = scan.nextLine();

            if(choice1.equals("1"))
                return FrameName.SPECTATOR_MENU;
            else
                return FrameName.MAIN_MENU;
        }

        int playerNumber = Main.roomService.checkPlayersInRoom(roomToken);

        if(playerNumber != 2){
            System.out.println();
            System.out.println("You must select a room with 2 players to spectate a game");
            System.out.println("1. Continue");
            System.out.println("Type anything else to go to the main menu");
            String choice1 = scan.nextLine();

            if(choice1.equals("1"))
                return FrameName.SPECTATOR_MENU;
            else
                return FrameName.MAIN_MENU;
        }

        try{

            while(true){
                this.clearConsole();
                String[] players = Main.sendSocketMessage("getPlayers", roomToken).split(",");
                String currentPlayer = Main.sendSocketMessage("currentPlayer", roomToken);
                String boardStatus = Main.sendSocketMessage("boardStatus", roomToken);


                System.out.println("=====================================================");
                System.out.println("Room: " + roomToken);
                System.out.println("Player1: " + players[0]);
                System.out.println("Player2:" + players[1]);
                System.out.println("Current turn: " + currentPlayer);
                System.out.println("=====================================================");
                System.out.println();
                System.out.println(boardStatus.replaceAll("\\*", "\n"));

                String winner = Main.sendSocketMessage("checkWinner", roomToken);

                if(!winner.equals("")) {
                    System.out.println();
                    System.out.println("-----------------------------------------------");

                    if (winner.equals(players[0])) {
                        System.out.println("Player 1 won");
                    } else if (winner.equals("draw")) {
                        System.out.println("It's a draw");
                    } else {
                        System.out.println("Player 2 won");
                    }

                    System.out.println("1. Main menu");
                    System.out.println("Type anything else to exit a program");
                    System.out.println("-----------------------------------------------");
                    Scanner scan1 = new Scanner(System.in);
                    String choice1 = scan1.nextLine();

                    if(choice1.contains("1")){
                        return FrameName.MAIN_MENU;
                    }
                    else{
                        System.exit(0);
                    }
                }


                String turn = currentPlayer;
                int iteration = 0;
                while(turn.equals(currentPlayer)){
                    if(++iteration % 100 == 0){
                        System.out.println("Do you wish to continue waiting? <Y><N>");
                        if(scan.nextLine().contains("N"))
                            return FrameName.MAIN_MENU;
                    }
                    Thread.sleep(1000);
                    turn = Main.sendSocketMessage("currentPlayer", roomToken);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return FrameName.MAIN_MENU;
    }
    private FrameName roomMainUI(Scanner scan) throws RemoteException, InterruptedException {
        this.clearConsole();

        String oponentName = Main.roomService.getOponent(Main.connectedRoomToken, Main.playerToken);
        boolean isYourTurn = Main.roomService.isYourTurn(Main.connectedRoomToken, Main.playerToken);
        String board = Main.roomService.getBoardInfo(Main.connectedRoomToken);

        System.out.println("========================================================");
        System.out.println("Room: " + Main.connectedRoomToken);
        System.out.println("Player: " + Main.playerToken);
        System.out.println("Opponent: " + oponentName);
        System.out.println("========================================================");
        System.out.println();
        System.out.println(board);
        System.out.println();
        if(isYourTurn){
            System.out.println("Type number in which you want to enter a move");
            int choice = scan.nextInt();
            int moveStatus = Main.roomService.makeMove(Main.playerToken, Main.connectedRoomToken, choice);

            while(moveStatus < 0){
                switch(moveStatus){
                    case -5 -> {System.out.println("Couldn't find a room ");}
                    case -4 -> {System.out.println("It's not your turn to make a move");}
                    case -3 -> {System.out.println("You made a illegal move, try changing the number");}
                    case -2 -> {System.out.println("Provided incorrect number to make a move");}
                    case -1 -> {System.out.println("You are not connected to the room");}
                }

                Thread.sleep(1000);
                System.out.println();
                System.out.println("Type number in which you want to enter a move");
                choice = scan.nextInt();
                moveStatus = Main.roomService.makeMove(Main.playerToken, Main.connectedRoomToken, choice);
            }


        }
        else{
            System.out.println("Waiting for the opponent to make a move...");
            int iteration = 0;
            String winner = "";
            while(!isYourTurn){
                Thread.sleep(1000);
                isYourTurn = Main.roomService.isYourTurn(Main.connectedRoomToken, Main.playerToken);
                winner = Main.roomService.checkWinner(Main.connectedRoomToken);
                if(!winner.equals(""))
                    break;
                if(++iteration % 100 == 0){
                    System.out.println("Do you wish to continue waiting? <Y><N>");
                    String choice2 = scan.nextLine();
                    if(choice2.contains("N"))
                        return FrameName.MAIN_MENU;
                }
            }
        }

        String winner = Main.roomService.checkWinner(Main.connectedRoomToken);
        if(!winner.equals("")){
            System.out.println();
            System.out.println("-----------------------------------------------");

            if(winner.equals(Main.playerToken)){
                System.out.println("Congratulations, you won!!!!!");
            }
            else if(winner.equals("draw")){
                System.out.println("It's a draw!");
            }
            else{
                System.out.println("You lost");
            }

            System.out.println("1. Main menu");
            System.out.println("2. Reset board and play again");
            System.out.println("Type anything else to exit a program");
            System.out.println("-----------------------------------------------");
            Scanner scan1 = new Scanner(System.in);
            String choice1 = scan1.nextLine();

            if(choice1.contains("1")){
                Main.roomService.leaveRoom(Main.playerToken, Main.connectedRoomToken);
                Main.connectedRoomToken = "";
                return FrameName.MAIN_MENU;
            }
            else if(choice1.contains("2")){
                // reset boardu
                // pętla czekania i sprawdzenia czy poszły dwa resety
                int status = Main.roomService.resetRoom(Main.playerToken, Main.connectedRoomToken);
                System.out.println();
                System.out.println("You voted for a reset, waiting for the opponent to make a decision");

                int iteration = 0;
                int playerNumber = 2;
                while(status != 1){
                    Thread.sleep(1000);
                    status = Main.roomService.resetRoom(Main.playerToken, Main.connectedRoomToken);
                    playerNumber = Main.roomService.checkPlayersInRoom(Main.connectedRoomToken);

                    if(playerNumber < 2){
                        System.out.println("Your opponent has left the room");
                        System.out.println("1. Main menu");
                        System.out.println("Type anything else to exit a program");
                        String choice2 = scan1.nextLine();
                        if(choice2.contains("1")){
                            Main.roomService.leaveRoom(Main.playerToken, Main.connectedRoomToken);
                            Main.connectedRoomToken = "";
                            return FrameName.MAIN_MENU;
                        }
                        else{
                            System.exit(0);
                        }
                    }
                    if(++iteration % 100 == 0){
                        System.out.println("Do you wish to continue waiting? <Y><N>");
                        scan.nextLine();
                        if(scan.nextLine().contains("N"))
                            return FrameName.MAIN_MENU;
                    }
                }
            }
            else{
                System.exit(0);
            }
        }


        return FrameName.ROOM_MAIN;
    }


    private FrameName deleteRoomUI(Scanner scan) throws RemoteException{
        this.clearConsole();


        System.out.println("========================================================");
        System.out.println("Player: " + Main.playerToken);
        System.out.println("========================================================");
        System.out.println();
        System.out.println("Rooms: ");
        System.out.println("--------------");
        System.out.println(Main.roomService.listRooms());
        System.out.println();
        System.out.println("Provide a room token to delete: ");
        String token = scan.nextLine();
        System.out.println("Provide password: ");
        String password = scan.nextLine();


        int deleteStatus = Main.roomService.deleteRoom(token, password);


        System.out.println();
        if(deleteStatus == 0)
            System.out.println("Couldn't find a room with given token");
        else if(deleteStatus == -1)
            System.out.println("Can't delete a room with players in it");
        else
            System.out.println("Room deleted");


        System.out.println();
        System.out.println("1. Main menu");
        System.out.println("Type anything else to exit a program");
        System.out.println("-----------------------------------------------");
        Scanner scan1 = new Scanner(System.in);
        String choice1 = scan1.nextLine();

        if(choice1.contains("1")){
            return FrameName.MAIN_MENU;
        }
        else{
            System.exit(0);
        }

        return FrameName.ROOM_MAIN;

    }





    public void clearConsole()
    {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            }
            else{
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e) {
            for (int i = 0; i < 50; ++i) System.out.println();
        }

    }

    private int validateOption(String chosenOption, int[] options) throws IllegalArgumentException{

        if(chosenOption.equals("exit") || chosenOption.equals("'exit'")){
            System.exit(0);
        }
        else{
            try{
                int parsedOption = Integer.parseInt(chosenOption);
                boolean isValidOption = false;

                for (int option : options) {
                    if (option == parsedOption) {
                        isValidOption = true;
                        break;
                    }
                }

                if(!isValidOption){
                    throw new IllegalArgumentException("Chosen option is illegal");
                }

                return parsedOption;

            }
            catch (NumberFormatException e){
                throw new IllegalArgumentException("Chosen option is not a integer or string 'exit'");
            }
        }

        return -1;
    }


}
