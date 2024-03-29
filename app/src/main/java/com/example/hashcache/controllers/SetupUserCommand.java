package com.example.hashcache.controllers;

import android.util.Log;

import com.example.hashcache.appContext.AppContext;
import com.example.hashcache.models.database.DatabasePort;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A controller to handle setting up a user
 */
public class SetupUserCommand {
    /**
     * Sets up the user with the given username after the user has been logged in or created.
     *
     * @param userName the username of the user to set up
     * @param db the interface to use to conenct to the firestore collection
     * @param appContext the current context of the app
     * @return cf the CompletableFuture that will complete exceptionally if there was a problem
     */
    public CompletableFuture<Void> setupUser(String userName, DatabasePort db,
                           AppContext appContext) {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        db.getIdByUsername(userName).thenAccept(userId -> {
            db.getPlayer(userId).thenAccept(player -> {

                AddLoginCommand.addLogin(userName, appContext, db)
                        .thenAccept(nullValue -> {
                            appContext.setCurrentPlayer(player);
                            appContext.setupListeners();
                            cf.complete(null);
                        })
                        .exceptionally(new Function<Throwable, Void>() {
                            @Override
                            public Void apply(Throwable throwable) {
                                cf.completeExceptionally(throwable);
                                return null;
                            }
                        });
            }).exceptionally(new Function<Throwable, Void>() {
                @Override
                public Void apply(Throwable throwable) {
                    Log.d("ERROR", "Could not get player" + userName);
                    Log.d("Reason", throwable.getMessage());
                    cf.completeExceptionally(new Exception("Could not get player."));
                    return null;
                }
            });
        });

        return cf;
    }
}
