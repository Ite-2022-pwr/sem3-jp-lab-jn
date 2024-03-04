package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.UUID;

public class RoomService extends UnicastRemoteObject implements RoomServiceInterface {

    private static HashMap<String, Room> rooms = new HashMap<>();

    public RoomService() throws RemoteException {
        super();


    }

    @Override
    public String createRoom(String password, String roomName) throws RemoteException {

        String token = UUID.randomUUID() + "@" + roomName;

        if(rooms.containsKey(token))
            return "Can't create new room with token " + token +". Room with that token already exists! Try again";

        rooms.put(token, new Room(token, password, roomName));
        return "Created room named " + roomName + ". You can connect to it via token " + token;
    }

    /**
     *
     * @param password
     * @return {@code -2} 2 players playing cant join
     *          {@code -1} password incorrect cant join
     *          {@code 0} cant find room with given token
     *          {@code 1} joined room, single player
     *          {@code 2} joined room, two players
     * @throws RemoteException
     */
    @Override
    public int joinRoom(String playerToken, String roomToken, String password) throws RemoteException {

        if(!rooms.containsKey(roomToken))
            return 0;

        Room room = rooms.get(roomToken);
        return room.join(playerToken, password);

    }

    @Override
    public int leaveRoom(String playerToken, String roomToken) throws RemoteException {

        if(!rooms.containsKey(roomToken))
            return 0;

        Room room = rooms.get(roomToken);

        if(!room.hasPlayer(playerToken))
            return -1;

        room.leave(playerToken);

        return 1;

    }

    @Override
    public int resetRoom(String playerToken, String roomToken) throws RemoteException {
        if(!rooms.containsKey(roomToken))
            return 0;

        Room room = rooms.get(roomToken);

        if(!room.hasPlayer(playerToken))
            return -1;

        return room.reset(playerToken);
    }

    @Override
    public int deleteRoom(String roomToken, String password) throws RemoteException {

        if(!rooms.containsKey(roomToken))
            return -1;

        Room room = rooms.get(roomToken);

        if(room.canDelete()){
            rooms.remove(roomToken);
            return 1;
        }

        return 0;
    }

    @Override
    public String listRooms(){
        StringBuilder output = new StringBuilder();
        for(Room room : rooms.values()){
            output.append("Room ").append(room.getName()).append(" | token: ").append(room.getToken()).append(" Players: ").append(room.getPlayerNumber()).append("/2 ").append("\n");
        }
        return output.toString();
    }

    @Override
    public int checkPlayersInRoom(String token){
        return rooms.get(token).getPlayerNumber();
    }

    @Override
    public String getPlayerWhosTurn(String roomToken){
        return rooms.get(roomToken).getPlayerTurn();
    }

    @Override
    public boolean hasRoomWithToken(String roomToken){
        return rooms.get(roomToken) != null;
    }

    @Override
    public String checkWinner(String roomToken){
        return rooms.get(roomToken).checkWinner();
    }

    @Override
    public boolean isYourTurn(String roomToken, String playerToken){
        return rooms.get(roomToken).isYourTurn(playerToken);
    }

    @Override
    public String getOponent(String roomToken, String playerToken){
        return rooms.get(roomToken).getOpponentToken(playerToken);
    }

    /***
     *
     * @param playerToken player id to make a move
     * @param moveOffset move position
     * @return int:
     * -1 if player is not connected to room, -2 offset is incorrect, -3 illegal move, -4 not your turn 1 good move
     */
    @Override
    public int makeMove(String playerToken, String roomToken, int moveOffset){

        int status = -5;


        if(rooms.get(roomToken).hasPlayer(playerToken)){
            return rooms.get(roomToken).makeMove(playerToken, moveOffset);
        }

        return status;
    }

    public String getBoardInfo(String roomToken){
        return rooms.get(roomToken).getBoardStatus();
    }





}
