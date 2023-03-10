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

    public static Bitmap generateImage(Resources resources, int boxWidth, int boxHeight) {
        // example 64-bit binary string
        long binaryString = 0b1101100101010110001001101111101110010100100111110101101100001000L;

        // extract the bits for different parts
        int head = (int) (binaryString & 0b11);
        int eyes = (int) ((binaryString >> 2) & 0b11);
        int body = (int) ((binaryString >> 4) & 0b11);
        int ears = (int) ((binaryString >> 6) & 0b11);

        // load the image files for different parts
        Bitmap headImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "head"));
        Bitmap eyesImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "eyes"));
        Bitmap bodyImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "body"));
        Bitmap earsImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "ears"));

        // resize the bitmaps to reduce their size
        int width = boxWidth / 5; // set the width to 1/5 of the box width
        headImg = Bitmap.createScaledBitmap(headImg, width, width, false);
        eyesImg = Bitmap.createScaledBitmap(eyesImg, width, width, false);
        bodyImg = Bitmap.createScaledBitmap(bodyImg, width, width, false);
        earsImg = Bitmap.createScaledBitmap(earsImg, width, width, false);

        // create a new image of appropriate size
        int height = headImg.getHeight() + eyesImg.getHeight() + bodyImg.getHeight();
        Bitmap image = Bitmap.createBitmap(boxWidth, boxHeight, Bitmap.Config.ARGB_8888);

        // draw different parts onto the final image
        Canvas canvas = new Canvas(image);
        int x = (boxWidth - width) / 2; // center the head image horizontally
        int y = 0;
        canvas.drawBitmap(headImg, x, y, null);

        x = (boxWidth - width) / 2; // center the eyes image horizontally
        y = headImg.getHeight() - width / 2;
        canvas.drawBitmap(eyesImg, x, y, null);

        x = (boxWidth - width) / 2; // center the body image horizontally
        y = headImg.getHeight() + eyesImg.getHeight();
        canvas.drawBitmap(bodyImg, x, y, null);

        x = (boxWidth - 2 * width) / 2; // center the left ear image horizontally
        y = 0; // align the left ear image to the top edge of the image
        canvas.drawBitmap(earsImg, x, y, null);

        x = (boxWidth + width) / 2; // center the right ear image horizontally
        y = 0; // align the right ear image to the top edge of the image
        canvas.drawBitmap(earsImg, x, y, null);

        return image;
    }

    private static int getDrawableResourceId(Resources resources, String name) {
        return resources.getIdentifier(name, "drawable", "com.example.hashcache");
    }


}