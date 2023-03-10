package com.example.hashcache.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.io.InputStream;

public class ImageGenerator {

    public static Bitmap generateImage(InputStream headInputStream, InputStream eyesInputStream, InputStream bodyInputStream, InputStream earsInputStream, long binaryString) throws IOException {
        // extract the bits for different parts
        int head = (int) (binaryString & 0b11);
        int eyes = (int) ((binaryString >> 2) & 0b11);
        int body = (int) ((binaryString >> 4) & 0b11);
        int ears = (int) ((binaryString >> 6) & 0b11);

        // load the image files for different parts
        Bitmap headBitmap = BitmapFactory.decodeStream(headInputStream);
        Bitmap eyesBitmap = BitmapFactory.decodeStream(eyesInputStream);
        Bitmap bodyBitmap = BitmapFactory.decodeStream(bodyInputStream);
        Bitmap earsBitmap = BitmapFactory.decodeStream(earsInputStream);

        // create a new image of appropriate size
        int width = Math.max(headBitmap.getWidth(), earsBitmap.getWidth());
        int height = headBitmap.getHeight() + eyesBitmap.getHeight() + bodyBitmap.getHeight();
        Bitmap imageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imageBitmap);

        // draw different parts onto the final image
        int x = (width - headBitmap.getWidth()) / 2; // center the head image horizontally
        int y = 0;
        canvas.drawBitmap(headBitmap, x, y, new Paint());

        x = (width - eyesBitmap.getWidth()) / 2; // center the eyes image horizontally
        y = headBitmap.getHeight() - eyesBitmap.getHeight() / 2;
        canvas.drawBitmap(eyesBitmap, x, y, new Paint());

        x = (width - bodyBitmap.getWidth()) / 2; // center the body image horizontally
        y = headBitmap.getHeight() + eyesBitmap.getHeight();
        canvas.drawBitmap(bodyBitmap, x, y, new Paint());

        x = 0; // align the left ear image to the left edge of the image
        y = 0; // align the left ear image to the top edge of the image
        canvas.drawBitmap(earsBitmap, x, y, new Paint());

        x = width - earsBitmap.getWidth(); // align the right ear image to the right edge of the image
        y = 0; // align the right ear image to the top edge of the image
        canvas.drawBitmap(earsBitmap, x, y, new Paint());

        return imageBitmap;
    }
}