package com.example.hashcache.controllers;

import com.example.hashcache.context.Context;
import com.example.hashcache.models.database.Database;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class PerformLoginCommand {
    public static CompletableFuture<Boolean> performLogin(String deviceId, AddUserCommand addUserCommand){
        CompletableFuture<Boolean> cf = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            Database.getInstance().getUsernameForDevice(deviceId)
                    .thenAccept(username -> {
                        if(username!=null){
                            addUserCommand.addUser(username, Database.getInstance(), Context.get())
                                    .thenAccept(nullValue -> {
                                        cf.complete(true);
                                    })
                                    .exceptionally(new Function<Throwable, Void>() {
                                        @Override
                                        public Void apply(Throwable throwable) {
                                            cf.completeExceptionally(throwable);
                                            return null;
                                        }
                                    });
                        }else{
                            cf.complete(false);
                        }
                    });
        });
        return cf;
    }
}
