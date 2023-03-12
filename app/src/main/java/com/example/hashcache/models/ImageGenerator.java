package com.example.hashcache.models;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class ImageGenerator {

    public static Bitmap generateImage(Resources resources, int boxWidth, int boxHeight, int headX, int headY, int eyesX, int eyesY, int bodyX, int bodyY, int mouthX, int mouthY) {
        // example 64-bit binary string
        long binaryString = 0b1101100101010110001001101111101110010100100111110101101100001000L;

        // extract the bits for different parts
        int head = 1; // set default head to 1
        int eyes = (int) ((binaryString >> 2) & 0b11); // extract 2 bits for eyes
        int body = (int) ((binaryString >> 4) & 0b11); // extract 2 bits for body
        int mouth = (int) ((binaryString >> 6) & 0b11); // extract 2 bits for mouth

        // set head based on the remaining bits
        long remainingBits = binaryString >> 8; // discard the first 8 bits
        switch ((int) (remainingBits % 3)) {
            case 0:
                head = 1;
                break;
            case 1:
                head = 2;
                break;
            case 2:
                head = 3;
                break;
        }

        // load the image files for different parts
        Bitmap headImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "head"));
        Bitmap eyesImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "eyes"+2));
        Bitmap bodyImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "body"+2));
        Bitmap mouthImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "mouth"+2));

        // create a new image of appropriate size
        Bitmap image = Bitmap.createBitmap(boxWidth, boxHeight, Bitmap.Config.ARGB_8888);

        // draw different parts onto the final image
        Canvas canvas = new Canvas(image);

        // draw head
        canvas.drawBitmap(headImg, headX, headY, null);

        // draw eyes
        canvas.drawBitmap(eyesImg, eyesX, eyesY, null);

        // draw body
        canvas.drawBitmap(bodyImg, bodyX, bodyY, null);

        // draw mouth
        canvas.drawBitmap(mouthImg, mouthX, mouthY, null);

        return image;
    }

    private static int getDrawableResourceId(Resources resources, String name) {
        return resources.getIdentifier(name, "drawable", "com.example.hashcache");
    }

}
