package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Room {


    private String token;

    private String password;

    private String name;

    private HashMap<String, Character> playerTokens;

    private Set<String> playerVotesForReset;

    private String[] board = new String[9];

    private String playerTurn;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room(String token, String password, String name){
        this.setName(name);
        this.setToken(token);
        this.setPassword(password);

        for(int i = 0; i < board.length; i++){
            board[i] = String.valueOf(i+1);
        }

        playerTokens = new HashMap<>();
        playerVotesForReset = new HashSet<>();

    }

    /**
     *
     * @param playerToken
     * @param password
     * @return {@code -1} password incorrect cant join
     *          {@code -2} 2 players playing cant join
     *          {@code 1} joined room, single player
     *          {@code 2} joined room, two players
     */
    public int join(String playerToken, String password){
        if(!password.equals(this.password))
            return -1;

        if(playerTokens.size() >= 2)
            return -2;

        if(playerTokens.isEmpty())
            playerTokens.put(playerToken, 'X');
        else
            playerTokens.put(playerToken, '0');

        playerTurn = decideFirst();

        System.out.println("Dodano użytkownika " + playerToken);
        System.out.println("Zaczyna gracz " + playerTurn);
        System.out.println("Liczba użytkowników w pokoju " + playerTokens.size());

        return this.playerTokens.size();
    }

    int getPlayerNumber(){
        return this.playerTokens.size();
    }

    void leave(String playerToken){
        playerTokens.remove(playerToken);

        if(playerTokens.isEmpty())
            hardReset();
    }

    /***
     *
     * @param playerToken player id to make a move
     * @param moveOffset move position
     * @return int:
     * -1 if player is not connected to room, -2 offset is incorrect, -3 illegal move, -4 not your turn 1 good move
     */
    int makeMove(String playerToken, int moveOffset){

        if(!playerTokens.containsKey(playerToken))
            return -1;

        if(!playerToken.equals(playerTurn))
            return -4;

        if(moveOffset < 0 || moveOffset > 9)
            return -2;

        if(board[moveOffset-1].contains("0") || board[moveOffset-1].contains("X"))
            return -3;



        board[moveOffset-1] = playerTokens.get(playerToken).toString();

        for(String signedPlayer : playerTokens.keySet()){
            if(!signedPlayer.equals(playerToken)){
                playerTurn = signedPlayer;
                break;
            }
        }
        System.out.println("Player turn: " + playerTurn);

        return 1;
    }


    /**
     *
     * @return Player token if the player won or "" if there is no winner at all
     */
    String checkWinner(){


        boolean isFull = true;
        for(String tile : board){
            if(tile.matches("[1-9]")){
                isFull = false;
                break;
            }
        }

        if(isFull)
            return "draw";

        for(Character sign : playerTokens.values()){
            String signed = sign.toString();
            if(
                (board[0].contains(signed) && board[1].contains(signed) && board[2].contains(signed)) ||
                (board[3].contains(signed) && board[4].contains(signed) && board[5].contains(signed)) ||
                (board[6].contains(signed) && board[7].contains(signed) && board[8].contains(signed)) ||

                (board[0].contains(signed) && board[4].contains(signed) && board[8].contains(signed)) ||
                (board[2].contains(signed) && board[4].contains(signed) && board[6].contains(signed)) ||

                (board[0].contains(signed) && board[3].contains(signed) && board[6].contains(signed)) ||
                (board[1].contains(signed) && board[4].contains(signed) && board[7].contains(signed)) ||
                (board[2].contains(signed) && board[5].contains(signed) && board[8].contains(signed))

            ){
                for(Map.Entry<String, Character> entry : playerTokens.entrySet()){
                    if(entry.getValue().equals(sign)){
                        playerVotesForReset.clear();
                        return entry.getKey();
                    }

                }
            }
        }
        return "";
    }

    String getBoardStatus(){
        return  "   ┌───┬───┬───┐\n     " +
                board[0] + " | " + board[1] + " | " + board[2] + " |\n"
                + "   ├───┼───┼───┤\n     " +
                board[3] + " | " + board[4] + " | " + board[5] + " |\n"
                + "   ├───┼───┼───┤\n     " +
                board[6] + " | " + board[7] + " | " + board[8] + " |\n" +
                "   └───┴───┴───┘";
    }

    // returns 1 if reset, 0 if there is no needed votes for non-hard reset

    void hardReset(){
        this.playerTurn = "";
        this.playerTokens.clear();
        this.playerVotesForReset.clear();
        for(int i = 0; i < board.length; i++){
            board[i] = String.valueOf(i+1);
        }
    }

    int reset(String playerToken){


        playerVotesForReset.add(playerToken);
        this.playerTurn = decideFirst();
        if(playerVotesForReset.size() == 2){
            for(int i = 0; i < board.length; i++){
                board[i] = String.valueOf(i+1);
            }
            return 1;
        }
        return -1;

    }

    boolean hasPlayer(String playerToken){
        return this.playerTokens.containsKey(playerToken);
    }

    boolean isYourTurn(String playerToken){
        return playerToken.equals(playerTurn);
    }

    /**
     *
     * @param playerToken
     * @return String opponent player token, or given player token if there is no opponent in room
     */
    String getOpponentToken(String playerToken){
        for(String player : playerTokens.keySet()){
            if(!player.equals(playerToken))
                return player;
        }
        return playerToken;
    }

    // returns "" if not 2 players in room
    String decideFirst(){
        Random r = new Random();
        if(playerTokens.size() == 2)
            return (String) playerTokens.keySet().toArray()[r.nextInt(2)];
        return "";
    }

    boolean canDelete(){
        return !(this.playerTokens.size() > 0);

    }



    String getPlayerTurn(){
        return this.playerTurn;
    }


}
