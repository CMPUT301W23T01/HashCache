package com.example.hashcache.tests.stubs;

import com.example.hashcache.database_connections.callbacks.GetPlayerCallback;
import com.example.hashcache.models.Player;

import java.util.ArrayList;

public class PlayersConnectionHandlerStub {
    public PlayersConnectionHandlerStub(ArrayList<String> inAppPlayerUserNames){}
    public Player getPlayer(String userName, GetPlayerCallback getPlayerCallback){
        return new Player(userName);
    }
    public void addPlayer(String username){}
}
