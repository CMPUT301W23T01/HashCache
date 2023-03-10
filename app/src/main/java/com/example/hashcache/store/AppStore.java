package com.example.hashcache.store;

import com.example.hashcache.models.Player;
import com.example.hashcache.models.ScannableCode;
import com.example.hashcache.models.database.Database;
import com.example.hashcache.models.database_connections.callbacks.BooleanCallback;
import com.example.hashcache.models.database_connections.callbacks.GetPlayerCallback;

import java.util.Observable;

/**
 * Represents a store for holding global state information in the app
 */
public class AppStore extends Observable {
    private static AppStore instance;

    private AppStore() {

    }

    boolean isLoggedIn;
    private Player currentPlayer;
    private Player selectedPlayer;
    private ScannableCode currentScannableCode = new ScannableCode();
    private ScannableCode lowestScannableCode = new ScannableCode();
    private ScannableCode highestScannableCode = new ScannableCode();

    /**
     * Gets the singleton instance of the AppStore
     *
     * @return instance The singleton instance of the AppStore
     */
    public static AppStore get() {
        if (instance == null) {
            synchronized (AppStore.class) {
                if (instance == null) {
                    instance = new AppStore();
                }

            }
        }
        return instance;
    }


    public void setSelectedPlayer(Player player){
        selectedPlayer = player;
    }

    public Player getSelectedPlayer(){
        return selectedPlayer;
    }
    /**
     * Sets the current player in the AppStore
     *
     * @param player The player to set as the current player
     */
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    /**
     * Gets the current player in the AppStore
     *
     * @return currentPlayer The current player in the AppStore
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the selected scannable code
     * 
     * @param currentScannableCode the scananbleCode to set as selected
     */
    public void setCurrentScannableCode(ScannableCode currentScannableCode) {
        this.currentScannableCode = currentScannableCode;
    }

    private void setHighestScannableCode(ScannableCode scanCode) {
        this.highestScannableCode = scanCode;
    }

    public ScannableCode getHighestScannableCode() {
        return this.highestScannableCode;
    }

    private void setLowestScannableCode(ScannableCode scanCode) {
        this.lowestScannableCode = scanCode;
    }

    public ScannableCode getLowestScannableCode() {
        return this.lowestScannableCode;
    }

    public ScannableCode getCurrentScannableCode() {
        return currentScannableCode;
    }

    public void setupListeners() {
        String userId = getCurrentPlayer().getUserId();
        Database.getInstance().onPlayerDataChanged(userId, new GetPlayerCallback() {
            @Override
            public void onCallback(Player player) {
                System.out.println(String.format("Player data for %s has changed", currentPlayer.getUsername()));
                setCurrentPlayer(player);
                setChanged();
                notifyObservers();
            }
        });

        Database.getInstance().onPlayerWalletChanged(userId, new BooleanCallback() {
            @Override
            public void onCallback(Boolean isTrue) {
                System.out.println(String.format("Player wallet for %s has changed", currentPlayer.getUsername()));
                Database.getInstance().getPlayer(userId).thenAccept(playa -> {
                    if (playa != null) {
                        setCurrentPlayer(playa);
                        setChanged();
                        notifyObservers();
                        Database.getInstance()
                                .getPlayerWalletTotalScore(playa.getPlayerWallet().getScannedCodeIds())
                                .thenAccept(totalScore -> {
                                    getCurrentPlayer().setTotalScore(totalScore.longValue());
                                    setChanged();
                                    notifyObservers();
                                });
                        Database.getInstance().getPlayerWalletLowScore(playa.getPlayerWallet().getScannedCodeIds())
                                .thenAccept(scanCode -> {
                                    setLowestScannableCode(scanCode);
                                    setChanged();
                                    notifyObservers();
                                });
                        Database.getInstance().getPlayerWalletTopScore(playa.getPlayerWallet().getScannedCodeIds())
                                .thenAccept(scanCode -> {
                                    setHighestScannableCode(scanCode);
                                    setChanged();
                                    notifyObservers();
                                });

                    }
                });
            }
        });

    }

}
