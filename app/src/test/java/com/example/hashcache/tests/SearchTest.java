package com.example.hashcache.tests;

import com.example.hashcache.models.Player;
import com.example.hashcache.models.PlayerList;
import com.example.hashcache.models.database_connections.CodeLocationConnectionHandler;
import com.example.hashcache.models.database_connections.FireStoreHelper;
import com.example.hashcache.models.database_connections.PlayersConnectionHandler;
import com.example.hashcache.models.database_connections.callbacks.BooleanCallback;
import com.example.hashcache.models.database_connections.converters.CodeLocationDocumentConverter;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class SearchTest {

    private String newPlayerUsername;
    private Player mockPlayer;
    private ArrayList<String> names;
    private PlayersConnectionHandler mockPlayerConnectionHandler;
    private BooleanCallback mockBooleanCallback;

    @BeforeEach
    void resetMocks(){
        newPlayerUsername = "Stubby";
        mockPlayer = new Player(newPlayerUsername);
        names = new ArrayList<>();
        mockPlayerConnectionHandler = Mockito.mock(PlayersConnectionHandler.class);
        mockBooleanCallback = Mockito.mock(BooleanCallback.class);
        PlayerList.resetInstance();
    }

    @Test
    public void testLevenshtein() {
        PlayerList playerList = PlayerList.getInstance(mockPlayerConnectionHandler);
        Integer distance = playerList.computeLevenshteinDistance("benyam", "ephrem");

        assert distance == 5;

        distance = playerList.computeLevenshteinDistance("hashcache", "hashcache");

        assert distance == 0;

        distance = playerList.computeLevenshteinDistance("a", "Ryan");

        assert distance == 3;
    }

    // TODO add a test for searching
    @Test
    public void testSearch() {
    }

    // TODO add a test for using the filter. When more stuff with the leaderboard is setup
    @Test
    public void testFilter() {

    }
}
