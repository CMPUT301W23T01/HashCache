package com.example.hashcache.controllers;

import com.example.hashcache.context.Context;
import com.example.hashcache.models.database.Database;
import com.example.hashcache.models.database.DatabasePort;

import org.checkerframework.checker.units.qual.C;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class LogoutCommand {
    public static CompletableFuture<Void> logout(DatabasePort db){
        CompletableFuture<Void> cf = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            db.deleteLogin()
                    .thenAccept(nullValue -> {
                        cf.complete(nullValue);
                    })
                    .exceptionally(new Function<Throwable, Void>() {
                        @Override
                        public Void apply(Throwable throwable) {
                            cf.completeExceptionally(throwable);
                            return null;
                        }
                    });
        });
        return cf;
    }
}