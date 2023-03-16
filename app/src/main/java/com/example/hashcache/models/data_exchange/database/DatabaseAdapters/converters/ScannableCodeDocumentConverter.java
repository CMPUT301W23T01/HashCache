package com.example.hashcache.models.data_exchange.database.DatabaseAdapters.converters;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hashcache.models.Comment;
import com.example.hashcache.models.HashInfo;
import com.example.hashcache.models.ScannableCode;
import com.example.hashcache.models.data_exchange.database.DatabaseAdapters.callbacks.GetCommentsCallback;
import com.example.hashcache.models.data_exchange.database.values.CollectionNames;
import com.example.hashcache.models.data_exchange.database.values.FieldNames;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ScannableCodeDocumentConverter {

    /**
     * Get a scannableCode from a document reference
     * @param documentReference the document reference to get the scannableCode object from
     * @return cf the CompleteableFuture with the ScannableCode object
     */
    public static CompletableFuture<ScannableCode> getScannableCodeFromDocument(DocumentReference documentReference){
        String[] scannableCodeId = new String[1];
        String[] codeLocationId  = new String[1];
        String[] generatedName = new String[1];
        int[] generatedScore = new int[1];

        CompletableFuture<ScannableCode> cf = new CompletableFuture<>();

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        try{
                            scannableCodeId[0] = document.getId();
                            codeLocationId[0] = (String) document.getData().get(FieldNames.CODE_LOCATION_ID.fieldName);
                            generatedName[0] = (String) document.getData().get(FieldNames.GENERATED_NAME.fieldName);
                            generatedScore[0] = Integer.parseInt((String) document.getData()
                                    .get(FieldNames.GENERATED_SCORE.fieldName));

                            getAllComments(documentReference.collection(CollectionNames.COMMENTS.collectionName))
                                    .thenAccept(comments -> {
                                        cf.complete(new ScannableCode(scannableCodeId[0],
                                                codeLocationId[0], new HashInfo(null, generatedName[0],
                                                generatedScore[0]), comments));
                                    }).exceptionally(new Function<Throwable, Void>() {
                                        @Override
                                        public Void apply(Throwable throwable) {
                                            cf.completeExceptionally(throwable);
                                            return null;
                                        }
                                    });

                            //TODO: Store the image once we figure out how to do it
                        }catch (NullPointerException e){
                            cf.completeExceptionally(new Exception("Scannable Code missing fields!"));
                        }
                    } else {
                        cf.completeExceptionally(new Exception("No such scannable code exists!"));
                    }
                } else {
                    cf.completeExceptionally(task.getException());
                }
            }
        });

        return  cf;
    }

    /**
     * Gets all the comments on a scannable code document
     * @param collectionReference the reference to the comments collection
     * @return cf the CompleteableFuture with an arraylist of comments
     */
    private static CompletableFuture<ArrayList<Comment>> getAllComments(CollectionReference collectionReference){
        ArrayList<Comment> comments = new ArrayList<>();
        CompletableFuture<ArrayList<Comment>> cf = new CompletableFuture<>();

        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()>0){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    comments.add(new Comment((String) document.getData().get(FieldNames.COMMENT_BODY.fieldName),
                                            (String) document.getData().get(FieldNames.COMMENTATOR_ID.fieldName)));
                                }
                            }

                            cf.complete(comments);
                        } else {
                            cf.completeExceptionally(task.getException());
                        }
                    }
                });
        return cf;
    }
}