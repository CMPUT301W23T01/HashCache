/*
    https://github.com/yuriy-budiyev/code-scanner

 */
package com.example.hashcache.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.hashcache.R;
import com.example.hashcache.controllers.hashInfo.HashController;
import com.example.hashcache.controllers.hashInfo.HashExceptions;
import com.example.hashcache.models.database_connections.PlayersConnectionHandler;
import com.example.hashcache.store.AppStore;
import com.google.zxing.Result;

import java.util.function.Function;

/**

 QRScanActivity
 An activity that uses the camera to scan QR codes. Displays a camera view and scans any QR codes that are detected.
 When a QR code is successfully scanned, a toast is displayed with the text that was encoded in the code.
 */
public class QRScanActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    /**
     * Called when the activity is created.
     *
     * Initializes the activity by setting up the camera view and scanner, and adding a callback function for when a
     * QR code is successfully scanned.
     *
     * @param savedInstanceState saved state of the activity, if it was previously closed.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scan_activity);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            /**
             * Called when a QR code is successfully decoded.
             *
             * Displays a toast with the text that was encoded in the QR code.
             *
             * @param result the result of decoding the QR code
             */
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(QRScanActivity.this, "Caching...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        HashController.addScannableCode(result.getText()).thenAccept(scannableCode -> {
                            System.out.println("Have added QR code!!");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(QRScanActivity.this, "Added QR code!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent(QRScanActivity.this, NewMonsterActivity.class);
                            startActivity(intent);
                        }).exceptionally(new Function<Throwable, Void>() {
                            @Override
                            public Void apply(Throwable throwable) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(throwable instanceof HashExceptions.AlreadyHasCodeEx){
                                            Toast.makeText(QRScanActivity.this, "ERROR: You already have QR code on your wallet!", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(QRScanActivity.this, "ERROR: There was an error adding QR code!", Toast.LENGTH_SHORT).show();
                                        }
                                        Intent intent = new Intent(QRScanActivity.this, AppHome.class);
                                        startActivity(intent);
                                    }
                                });
                                System.out.println("Could not add QR code" + throwable.getMessage());
                                return null;
                            }
                        });

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the camera view is clicked.
             *
             * Starts the scanner preview.
             *
             * @param view the view that was clicked (the camera view)
             */
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }
    /**
     * Called when the activity is resumed.
     *
     * Starts the scanner preview.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }
    /**
     * Called when the activity is paused.
     *
     * Releases the resources used by the scanner.
     */
    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
