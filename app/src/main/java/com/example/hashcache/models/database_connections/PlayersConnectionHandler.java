package com.example.hashcache.models.database_connections;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hashcache.models.PlayerWallet;
import com.example.hashcache.models.database_connections.callbacks.BooleanCallback;
import com.example.hashcache.models.database_connections.callbacks.GetPlayerCallback;
import com.example.hashcache.models.database_connections.callbacks.GetPlayerWalletCallback;
import com.example.hashcache.models.database_connections.converters.PlayerDocumentConverter;
import com.example.hashcache.models.database_connections.values.CollectionNames;
import com.example.hashcache.models.database_connections.values.FieldNames;
import com.example.hashcache.models.ContactInfo;
import com.example.hashcache.models.Player;
import com.example.hashcache.models.PlayerPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Handles all calls to the Firebase Players database
 */
public class PlayersConnectionHandler {
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private HashMap<String, String> inAppUsernamesIds;
    private HashMap<String, Player> cachedPlayers;
    final String TAG = "Sample";
    private PlayerDocumentConverter playerDocumentConverter;
    private FireStoreHelper fireStoreHelper;
    private PlayerWalletConnectionHandler playerWalletConnectionHandler;
    private static PlayersConnectionHandler INSTANCE;

    /**
     * Creates a new instance of the class and initializes the connection to the database
     * @param inAppNamesIds a mapping of userIds to their usernames
     * @param playerDocumentConverter the instance of the PlayerDocumentConverter class
     *                                to use to convert documents into Player objects
     * @param fireStoreHelper the instance of the FireStoreHelper class to use to perform
     *                        general FireStore actions
     * @param db an instance of the Firestore database
     * @param playerWalletConnectionHandler the instance of the PlayerWalletConnectionHandler
     *                                      class to use to interact with a player's wallet collection
     */
    private PlayersConnectionHandler(HashMap<String, String> inAppNamesIds,
                                     PlayerDocumentConverter playerDocumentConverter,
                                     FireStoreHelper fireStoreHelper,
                                     FirebaseFirestore db, PlayerWalletConnectionHandler
                                     playerWalletConnectionHandler){
        this.inAppUsernamesIds = inAppNamesIds;
        this.cachedPlayers = new HashMap<>();
        this.playerDocumentConverter = playerDocumentConverter;
        this.fireStoreHelper = fireStoreHelper;
        this.playerWalletConnectionHandler = playerWalletConnectionHandler;
        this.db = db;

        collectionReference = db.collection(CollectionNames.PLAYERS.collectionName);
    }

    /**
     * Get the current INSTANCE of the PlayersConnectionHandler class
     * @return PlayersConnectionHandler.INSTANCE the current instance of the PlayersConnectionHandler class
     * @throws IllegalArgumentException if the INSTANCE hasn't been initialized yet
     */
    public static PlayersConnectionHandler getInstance(){
        if(INSTANCE == null){
            throw new IllegalArgumentException("No instance of PlayersConnectionHandler currently exists!");
        }
        
        return INSTANCE;
    }

    /**
     * Create and get the static instance of the PlayersConnectinoHandler class with its dependencies
     * @param inAppNamesIds a mapping of userIds to their usernames
     * @param playerDocumentConverter the instance of the PlayerDocumentConverter class
     *                                to use to convert documents into Player objects
     * @param fireStoreHelper the instance of the FireStoreHelper class to use to perform
     *                        general FireStore actions
     * @param db an instance of the Firestore database
     * @param playerWalletConnectionHandler the instance of the PlayerWalletConnectionHandler
     *                                      class to use to interact with a player's wallet collection
     * @return PlayersConnectionHandler.INSTANCE the current instance of the PlayersConnectionHandler class
     *
     * @throws IllegalArgumentException if the INSTANCE has already been initialized
     */
    public static PlayersConnectionHandler makeInstance(HashMap<String, String> inAppNamesIds,
                                                        PlayerDocumentConverter playerDocumentConverter,
                                                        FireStoreHelper fireStoreHelper,
                                                        FirebaseFirestore db, PlayerWalletConnectionHandler
                                                        playerWalletConnectionHandler){
        if(INSTANCE != null){
            throw new IllegalArgumentException("Instance of PlayersConnectionHandler already exists!");
        }
        INSTANCE = new PlayersConnectionHandler(inAppNamesIds, playerDocumentConverter,
                fireStoreHelper, db, playerWalletConnectionHandler);
        return INSTANCE;
    }

    /**
     * Resets the static INSTANCE to null.
     * Should only be used for test purposes
     */
    public static void resetInstance(){
        INSTANCE = null;
    }

    /**
     * Gets all of the current usernames across the app
     * @return inAppPlayerUserNames the usernames of all players in the app
     */
    public ArrayList<String> getInAppPlayerUserNames(){
        ArrayList<String> inAppUserNames = new ArrayList<>();
        for(String username : inAppUsernamesIds.keySet()){
            inAppUserNames.add(username);
        }
        return inAppUserNames;
    }

    /**
     * Returns a boolean CompletableFuture indicating if the username exists or not.
     *
     * @param username the username to use to pull the player with
     */

    public CompletableFuture<Boolean> usernameExists(String username){
        CompletableFuture<Boolean> cf = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            Query docRef = collectionReference.whereEqualTo(FieldNames.USERNAME.fieldName, username).limit(1);
            docRef.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    QuerySnapshot document = task.getResult();
                        cf.complete(!document.isEmpty());
                }
                else{
                    cf.completeExceptionally(new Exception("[usernameExists] Could not complete query"));
                }
            });
        });
        return cf;
    }
    public CompletableFuture<String> getPlayerIdByUsername(String username){
        CompletableFuture<String> cf = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            Query docRef = collectionReference.whereEqualTo(FieldNames.USERNAME.fieldName, username).limit(1);
            docRef.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    QuerySnapshot document = task.getResult();
                    if(!document.isEmpty()){
                        DocumentSnapshot doc = document.getDocuments().get(0);
                        String id = (String) doc.getData().get(FieldNames.USER_ID.fieldName);
                        cf.complete(id);
                    }
                    else{
                        cf.completeExceptionally(new Exception("Username does not exist."));
                    }
                }
                else{
                    cf.completeExceptionally(new Exception("[usernameExists] Could not complete query"));
                }
            });
        });
        return cf;
    }
    /**
     * Gets a Player with a given username from the Players database
     *
     * @param userName the username to use to pull the player with
     * @param getPlayerCallback the callback function to call with the player once it has
     *                          been found
     * @throws IllegalArgumentException if the given username does not belong to a player
     */
    public void getPlayer(String userName, GetPlayerCallback getPlayerCallback){
        final Player[] player = new Player[1];

        if(cachedPlayers.keySet().contains(userName)){
            player[0] = cachedPlayers.get(userName);
        }else {
            DocumentReference documentReference = collectionReference.document(
                    inAppUsernamesIds.get(userName)
            );

            /**
             * Gets the document from the collection with the given userId, and throws an error
             * if a document with the id cannot be found
             */
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "Document exists!");

                            /**
                             * Converts the document into a Player object, and calls the given
                             * callback function with it
                             */
                            playerDocumentConverter.getPlayerFromDocument(documentReference,
                                    new GetPlayerCallback() {
                                        @Override
                                        public void onCallback(Player player) {
                                            cachedPlayers.put(userName, player);
                                            Log.d(TAG, "FIND DONE");
                                            getPlayerCallback.onCallback(player);
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Document does not exist!");
                             throw new IllegalArgumentException("Given username does not exist!");
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());
                    }
                }
            });
        }
    }

    /**
     * Adds a player to the database
     *
     * @param player the player to add to the database
     * @param booleanCallback the callback function to call once the operation has finished. Call
     *                        with true if the operation was successful, and false otherwise
     * @throws IllegalArgumentException if the username is empty, too long, or already belongs
     * to a player
     */
    public void addPlayer(Player player, BooleanCallback booleanCallback){
        String userId = player.getUserId();
        String username = player.getUsername();
        ContactInfo contactInfo = player.getContactInfo();
        PlayerPreferences playerPreferences = player.getPlayerPreferences();

        if(username == null || username.equals("")|| username.length()>=50){
            throw new IllegalArgumentException("Username null, empty, or too long");
        }

        if(inAppUsernamesIds.keySet().contains(username)){
            throw new IllegalArgumentException("Username taken!");
        }
        /**
         * Sets the id on the document as the userId
         * @param isTrue indicates if setting the PlayerWallet was successful
         */
        setUserId(userId, new BooleanCallback() {
            /**
             * Sets the username on the document
             * @param isTrue indicates if setting the userId was successful
             */
            @Override
            public void onCallback(Boolean isTrue)
            {
                setUserName(collectionReference.document(userId),username, new BooleanCallback() {

                    /**
                     * Sets the contact information on the document
                     * @param isTrue indicates if setting the username was successful
                     */
                    @Override
                    public void onCallback(Boolean isTrue) {
                        if (isTrue) {
                            DocumentReference playerDocument = collectionReference.document(userId);
                            setContactInfo(playerDocument, contactInfo, new BooleanCallback() {
                                /**
                                 * Sets the player preferences on the document
                                 * @param isTrue indicates if setting the contact information
                                 *               was successful
                                 */
                                @Override
                                public void onCallback(Boolean isTrue) {
                                    if (isTrue) {
                                        setPlayerPreferences(playerDocument, playerPreferences, new BooleanCallback() {
                                            /**
                                             * Caches the created player and calls the given callback function with
                                             * the success of the player creation
                                             * @param isTrue indicates if setting the player preferences was successful
                                             */
                                            @Override
                                            public void onCallback(Boolean isTrue) {
                                                cachedPlayers.put(player.getUsername(), player);
                                                booleanCallback.onCallback(isTrue);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    /**
     * Updates the player preferences of an existing user
     * @param userId the id of the user to update the preferences for
     * @param playerPreferences the preferences to set for the user
     * @param booleanCallback the callback function to call once the operation has finished. Calls
     *                        with true if the operation was successful, and false otherwise
     */
    public void updatePlayerPreferences(String userId, PlayerPreferences playerPreferences,
                                        BooleanCallback booleanCallback){
        DocumentReference documentReference = collectionReference.document(userId);
        setPlayerPreferences(documentReference, playerPreferences, booleanCallback);
    }

    /**
     * Sets the player preferences of a user
     * @param playerDocument the document of the player to change the preferences on
     * @param playerPreferences the preferences to set for the user
     * @param booleanCallback the callback function to call once the operation has finished. Calls
     *                        with true if the operation was successful, and false otherwise
     *
     *
     */
    private void setPlayerPreferences(DocumentReference playerDocument, PlayerPreferences playerPreferences,
                                      BooleanCallback booleanCallback){
        fireStoreHelper.addBooleanFieldToDocument(playerDocument, FieldNames.RECORD_GEOLOCATION.fieldName,
                playerPreferences.getRecordGeolocationPreference(),
                new BooleanCallback() {
                    @Override
                    public void onCallback(Boolean isTrue) {
                        if(isTrue){
                            booleanCallback.onCallback(true);
                        }else{
                            Log.e(TAG , "Something went wrong while setting the player preferences");
                            booleanCallback.onCallback(false);
                        }
                    }
                });
    }

    /**
     * Updates the contact information of an existing user
     * @param userId the id of the user to update the preferences for
     * @param contactInfo the contact information to set for the user
     * @param booleanCallback the callback function to call once the operation has finished. Calls
     *                        with true if the operation was successful, and false otherwise
     */
    public void updateContactInfo(String userId, ContactInfo contactInfo,
                                        BooleanCallback booleanCallback){
        DocumentReference documentReference = collectionReference.document(userId);
        setContactInfo(documentReference, contactInfo, booleanCallback);
    }

    /**
     * Sets the contact information of a user
     * @param playerDocument the document of the player to change the preferences on
     * @param contactInfo the contact information to set for the user
     * @param booleanCallback the callback function to call once the operation has finished. Calls
     *                        with true if the operation was successful, and false otherwise
     *
     *
     */
    private void setContactInfo(DocumentReference playerDocument, ContactInfo contactInfo,
                                BooleanCallback booleanCallback){
        fireStoreHelper.addStringFieldToDocument(playerDocument, FieldNames.EMAIL.fieldName,
                contactInfo.getEmail(),
                new BooleanCallback() {
                    @Override
                    public void onCallback(Boolean isTrue) {
                        if(isTrue){
                            fireStoreHelper.addStringFieldToDocument(playerDocument, FieldNames.PHONE_NUMBER.fieldName,
                                    contactInfo.getPhoneNumber(), new BooleanCallback() {
                                        @Override
                                        public void onCallback(Boolean isTrue) {
                                            if(isTrue){
                                                booleanCallback.onCallback(true);
                                            }
                                        }
                                    });
                        }else{
                            Log.e(TAG, "Something went wrong while setting the contact information" +
                                    "of the player document");
                            booleanCallback.onCallback(false);
                        }
                    }
                });
    }

    /**
     * Updates a user's username
     * @param oldUsername the user's current username
     * @param newUsername the username to set for the user
     * @param booleanCallback the callback function to call once the operation has finished. Calls
     *                        with true if the operation was successful, and false otherwise
     *
     * @throws IllegalArgumentException if the current username does not exist
     */
    public void updateUserName(String oldUsername, String newUsername, BooleanCallback booleanCallback){
        if(!this.inAppUsernamesIds.containsKey(oldUsername)){
            throw new IllegalArgumentException("Old username does not exist!");
        }

        this.setUserName(this.collectionReference.document(inAppUsernamesIds.get(oldUsername)),
                newUsername, new BooleanCallback() {
                    @Override
                    public void onCallback(Boolean isTrue) {
                        if(isTrue){
                            if(cachedPlayers.containsKey(oldUsername)){
                                cachedPlayers.put(newUsername, cachedPlayers.get(oldUsername));
                                cachedPlayers.remove(oldUsername);
                            }
                            booleanCallback.onCallback(true);
                        }else{
                            Log.e(TAG, "something went wrong while updating the user's username");
                            booleanCallback.onCallback(false);
                        }
                    }
                });
    }

    /**
     * Set the username field on a player's document
     * @param playerDocument the document of the player to set the username field on
     * @param username the username to set on the document
     * @param booleanCallback the callback function to call once the operation has finished, calls
     *                        with true if successful, and false otherwise
     * @throws IllegalArgumentException if there already is a user with the username
     */
    private void setUserName(DocumentReference playerDocument, String username,
                             BooleanCallback booleanCallback){
        if(this.inAppUsernamesIds.keySet().contains(username)){
            throw new IllegalArgumentException("Given username already exists!");
        }

        this.fireStoreHelper.addStringFieldToDocument(playerDocument, FieldNames.USERNAME.fieldName,
                username, booleanCallback);
    }

    /**
     * Sets the id of a new document with the userId
     * @param userId the userId to use as the id on the document
     * @param booleanCallback the callback function to call once the operation has finished. Calls
     *                        with true if the operation was successful, and false otherwise
     * @throws IllegalArgumentException if there already is a document with the given userId
     */
    private void setUserId(String userId, BooleanCallback booleanCallback){
        HashMap<String, String> userIdData = new HashMap<>();
        userIdData.put(FieldNames.USER_ID.fieldName, userId);
        if(this.inAppUsernamesIds.containsValue(userId)){
            throw new IllegalArgumentException("There already exists a document with the given id!");
        } else{
            fireStoreHelper.setDocumentReference(collectionReference.document(userId),
                    userIdData, new BooleanCallback() {
                        @Override
                        public void onCallback(Boolean isTrue) {
                            if(isTrue){
                                booleanCallback.onCallback(true);
                            }else{
                                Log.e(TAG, "Something went wrong while setting the userId" +
                                        "on a new Playerdocument");
                                booleanCallback.onCallback(false);
                            }
                        }
                    });
        }
    }

    /**
     * Update the PlayerWallet collection by adding a new ScannableCode
     * @param userId the id of the user whose PlayerWallet must be updated
     * @param scannableCodeId the id of the scannable code to add
     * @param locationImage the image of the location where the user scanned the code
     * @param booleanCallback the callback function to call once the operation has finished. Calls
     *                        with true if it was successful, and false otherwise
     *
     * @throws IllegalArgumentException if there are no users with the given Id
     */
    public void playerScannedCodeAdded(String userId, String scannableCodeId,
                                       Image locationImage, BooleanCallback booleanCallback){
        if(!this.inAppUsernamesIds.containsValue(userId)){
            throw new IllegalArgumentException("Given userId does not exist!");
        }

        CollectionReference scannedCodeCollection = collectionReference
                .document(userId)
                .collection(CollectionNames.PLAYER_WALLET.collectionName);

        playerWalletConnectionHandler.addScannableCodeDocument(scannedCodeCollection,
                scannableCodeId, locationImage, booleanCallback);
    }

    /**
     * Update the PlayerWallet collection by deleting a ScannableCode
     * @param userId the id of the user whose PlayerWallet must be updated
     * @param scannableCodeId the id of the scannable code to delete
     * @param booleanCallback the callback function to call once the operation has finished. Calls
     *                        with true if it was successful, and false otherwise
     *
     * @throws IllegalArgumentException if there are no users with the given Id
     */
    public void playerScannedCodeDeleted(String userId, String scannableCodeId,
                                         BooleanCallback booleanCallback){
        if(!this.inAppUsernamesIds.containsValue(userId)){
            throw new IllegalArgumentException("Given userId does not exist!");
        }

        CollectionReference scannedCodeCollection = collectionReference
                .document(userId)
                .collection(CollectionNames.PLAYER_WALLET.collectionName);

        playerWalletConnectionHandler.deleteScannableCodeFromWallet(scannedCodeCollection,
                scannableCodeId, booleanCallback);
    }
}
