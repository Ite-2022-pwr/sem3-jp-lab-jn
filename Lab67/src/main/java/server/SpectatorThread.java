package server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SpectatorThread extends Thread{


    private Socket clientSocket;

    private BufferedReader reader;

    private BufferedWriter writer;


    public SpectatorThread(Socket clientSocket) throws IOException {

        this.clientSocket = clientSocket;

        // odbieranie
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));

        // wysyłanie
        this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));

    }


    @Override
    public void run(){

        String clientMessage;

        while(this.clientSocket.isConnected()){

            try{
                clientMessage = reader.readLine();

                if(clientMessage.startsWith("currentPlayer")){
                    String roomToken = clientMessage.split(":")[1];
                    String playerToken = Main.roomService.getPlayerWhosTurn(roomToken);
                    writer.write(playerToken);
                    writer.newLine();
                    writer.flush();
                }
                else if(clientMessage.startsWith("getPlayers")){
                    String roomToken = clientMessage.split(":")[1];
                    String player1 = Main.roomService.getPlayerWhosTurn(roomToken);
                    String player2 = Main.roomService.getOponent(roomToken, player1);
                    writer.write(player1+","+player2);
                    writer.newLine();
                    writer.flush();
                }
                else if(clientMessage.startsWith("boardStatus")){
                    String roomToken = clientMessage.split(":")[1];
                    String boardStatus = Main.roomService.getBoardInfo(roomToken);
                    boardStatus = boardStatus.replaceAll("\\n", "*");

                    writer.write(boardStatus, 0, boardStatus.length());
                    writer.newLine();
                    writer.flush();
                }
                else if(clientMessage.startsWith("checkWinner")){
                    String roomToken = clientMessage.split(":")[1];
                    String winner = Main.roomService.checkWinner(roomToken);

                    writer.write(winner);
                    writer.newLine();
                    writer.flush();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


//        try {
//
//            byte[] buffer = new byte[1024];
//            int bytesRead = input.read(buffer);
//            String roomToken = new String(buffer, 0, bytesRead);
//
//            this.roomToken = roomToken;
//
//           OutputStreamWriter osw = new OutputStreamWriter(output);
//           InputStreamReader isr = new InputStreamReader(input);
//
//           String playerWhosTurn = Main.roomService.getPlayerWhosTurn(this.roomToken);
//           osw.write(playerWhosTurn, 0, playerWhosTurn.length());
//
//
//           while(true){
//
//
//
//           }





//            while(true){
//                // logika synchronizowania wysyłania statusu planszy z turami graczy
//                String playerTurn = Main.roomService.getPlayerWhosTurn(this.roomToken);
//                String board = Main.roomService.getBoardInfo(this.roomToken);
//
//                String boardStatus = "========================================================\n" +
//                        "Room: " + this.roomToken + "\n" +
//                        "Player turn: " + playerTurn + "\n" +
//                        "========================================================\n\n" + board;
//
//                osw.write(boardStatus, 0, boardStatus.length());
//
//
//                boolean isTurnChanged = Main.roomService.isYourTurn(this.roomToken, playerTurn);
//                while(!isTurnChanged){
//                    Thread.sleep(1000);
//                    isTurnChanged = Main.roomService.isYourTurn(this.roomToken, playerTurn);
//                }
//
//            }



            //clientSocket.close();

//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

    }



}
