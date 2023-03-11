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

    public static Bitmap generateImage(Resources resources, int boxWidth, int boxHeight) {
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
        Bitmap headImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "head.png"));
        if (headImg == null) {
            Log.e("ImageGenerator", "headImg is null");
        }
        Bitmap eyesImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "eyes"+1+".png"));
        if (eyesImg == null) {
            Log.e("ImageGenerator", "eyesImg is null");
        }
        Bitmap bodyImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "body"+1+".png"));
        if (bodyImg == null) {
            Log.e("ImageGenerator", "bodyImg is null");
        }
        Bitmap mouthImg = BitmapFactory.decodeResource(resources, getDrawableResourceId(resources, "mouth"+1+".png"));
        if (mouthImg == null) {
            Log.e("ImageGenerator", "mouthImg is null");
        }

        // resize the bitmaps to reduce their size
        int width = boxWidth / 5; // set the width to 1/5 of the box width
        headImg = Bitmap.createScaledBitmap(headImg, width, width, false);
        eyesImg = Bitmap.createScaledBitmap(eyesImg, width, width, false);
        bodyImg = Bitmap.createScaledBitmap(bodyImg, width, width, false);
        mouthImg = Bitmap.createScaledBitmap(mouthImg, width, width, false);

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

        x = (boxWidth - 2 * width) / 2; // center the mouth image horizontally
        y = headImg.getHeight() + eyesImg.getHeight() + bodyImg.getHeight();
        canvas.drawBitmap(mouthImg, x, y, null);

        return image;
    }

    private static int getDrawableResourceId(Resources resources, String name) {
        return resources.getIdentifier(name, "drawable", "com.example.hashcache");
    }


}