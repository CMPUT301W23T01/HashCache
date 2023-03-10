package com.example.hashcache.models;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.IOException;
import java.io.InputStream;

public class ImageGenerator {

    public static Bitmap generateImage(Context context) {
        // example 64-bit binary string
        long binaryString = 0b1101100101010110001001101111101110010100100111110101101100001000L;

        // extract the bits for different parts
        int head = (int) (binaryString & 0b11);
        int eyes = (int) ((binaryString >> 2) & 0b11);
        int body = (int) ((binaryString >> 4) & 0b11);
        int ears = (int) ((binaryString >> 6) & 0b11);

        // load the image files for different parts
        Resources resources = context.getResources();
        Bitmap headImg = BitmapFactory.decodeResource(resources, getDrawableResourceId("head"));
        Bitmap eyesImg = BitmapFactory.decodeResource(resources, getDrawableResourceId("eyes"));
        Bitmap bodyImg = BitmapFactory.decodeResource(resources, getDrawableResourceId("body"));
        Bitmap earsImg = BitmapFactory.decodeResource(resources, getDrawableResourceId("ears"));

        // create a new image of appropriate size
        int width = Math.max(headImg.getWidth(), earsImg.getWidth());
        int height = headImg.getHeight() + eyesImg.getHeight() + bodyImg.getHeight();
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // draw different parts onto the final image
        Canvas canvas = new Canvas(image);
        int x = (width - headImg.getWidth()) / 2; // center the head image horizontally
        int y = 0;
        canvas.drawBitmap(headImg, x, y, null);

        x = (width - eyesImg.getWidth()) / 2; // center the eyes image horizontally
        y = headImg.getHeight() - eyesImg.getHeight() / 2;
        canvas.drawBitmap(eyesImg, x, y, null);

        x = (width - bodyImg.getWidth()) / 2; // center the body image horizontally
        y = headImg.getHeight() + eyesImg.getHeight();
        canvas.drawBitmap(bodyImg, x, y, null);

        x = 0; // align the left ear image to the left edge of the image
        y = 0; // align the left ear image to the top edge of the image
        canvas.drawBitmap(earsImg, x, y, null);

        x = width - earsImg.getWidth(); // align the right ear image to the right edge of the image
        y = 0; // align the right ear image to the top edge of the image
        canvas.drawBitmap(earsImg, x, y, null);

        return image;
    }

    private static int getDrawableResourceId(String name) {
        return Resources.getSystem().getIdentifier(name, "drawable", "android");
    }

}