package com.example.hashcache.models.database;

import android.location.Location;
import android.util.Pair;

import com.example.hashcache.models.CodeMetadata;
import com.example.hashcache.models.Comment;
import com.example.hashcache.models.ContactInfo;
import com.example.hashcache.models.Player;
import com.example.hashcache.models.PlayerPreferences;
import com.example.hashcache.models.PlayerWallet;
import com.example.hashcache.models.ScannableCode;
import com.example.hashcache.models.database.DatabaseAdapters.callbacks.BooleanCallback;
import com.example.hashcache.models.database.DatabaseAdapters.callbacks.GetPlayerCallback;
import com.example.hashcache.models.database.DatabaseAdapters.callbacks.GetScannableCodeCallback;
import com.firebase.geofire.GeoLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import kotlin.Triple;

public interface DatabasePort {

    CompletableFuture<Boolean> usernameExists(String username);

    CompletableFuture<String> getIdByUsername(String username);

    CompletableFuture<Void> createPlayer(String username);

    CompletableFuture<Player> getPlayer(String userId);
    CompletableFuture<HashMap<String, String>> getPlayers();

    CompletableFuture<Long> getTotalScore(String userId);

    CompletableFuture<Void> addComment(String scannableCodeId, Comment comment);

    CompletableFuture<Void> updatePlayerPreferences(String userId, PlayerPreferences playerPreferences);

    CompletableFuture<String> addScannableCode(ScannableCode scannableCode);
    CompletableFuture<Void> addScannableCodeToPlayerWallet(String userId, String scannableCodeId);
    CompletableFuture<Boolean> scannableCodeExistsOnPlayerWallet(String userId, String scannableCodeId);
    CompletableFuture<Boolean> scannableCodeExists(String scannableCodeId);

    CompletableFuture<Boolean> removeScannableCodeFromWallet(String userId, String scannableCodeId);
    CompletableFuture<ScannableCode> getPlayerWalletTopScore(ArrayList<String> scannableCodeIds);
    CompletableFuture<ScannableCode> getPlayerWalletLowScore(ArrayList<String> scannableCodeIds);
    CompletableFuture<Long> getPlayerWalletTotalScore(ArrayList<String> scannableCodeIds);
    CompletableFuture<ArrayList<ScannableCode>> getScannableCodesByIdInList(ArrayList<String> scannableCodeIds);
    CompletableFuture<ScannableCode> getScannableCodeById(String scannableCodeId);
    CompletableFuture<Boolean> updateContactInfo(ContactInfo contactInfo, String userId);
    CompletableFuture<Pair<String, String>> getUsernameById(String userId);
    CompletableFuture<Integer> getNumPlayersWithScannableCode(String scannableCodeId);
    CompletableFuture<Void> addLoginRecord(String username);

    CompletableFuture<Void> addScannableCodeMetadata(CodeMetadata codeMetadata);
    CompletableFuture<ArrayList<CodeMetadata>> getCodeMetadataWithinRadius(GeoLocation location, double radiusMeters);
    CompletableFuture<ArrayList<ScannableCode>> getScannableCodesWithinRadius(GeoLocation location, double radiusMeters);
    CompletableFuture<String> getUsernameForDevice();

    CompletableFuture<Boolean> codeMetadataEntryExists(String userId, String scannableCodeId);
    CompletableFuture<Void> updatePlayerCodeMetadataImage(String userId, String scannableCodeId, String image);
    CompletableFuture<CodeMetadata> getPlayerCodeMetadataById(String userId, String scannableCodeId);
    CompletableFuture<ArrayList<CodeMetadata>> getCodeMetadataById(String scannableCodeId);
    CompletableFuture<Void> deleteLogin();

    void resetInstances();
    CompletableFuture<ArrayList<Pair<String, String>>> getUsernamesByIds(ArrayList<String> userIds);
    void onPlayerDataChanged(String userId, GetPlayerCallback callback);
    void onPlayerWalletChanged(String playerId, BooleanCallback callback);
    void onScannableCodeCommentsChanged(String scannableCodeId, GetScannableCodeCallback callback);
    CompletableFuture<ArrayList<Triple<String, Long, String>>> getTopUsers(String filter);
    CompletableFuture<Boolean> updatePlayerScores(String userId, PlayerWallet playerWallet);
    CompletableFuture<String> getTopMonsterName(String userId);
    CompletableFuture<ArrayList<Pair<String, ScannableCode>>> getScannableCodesWithinRadiusSorted(Location location);

    CompletableFuture<Boolean> removeScannableCodeMetadata(String scannableCodeId, String userId);
}
