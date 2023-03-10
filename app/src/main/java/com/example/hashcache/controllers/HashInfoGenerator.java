package com.example.hashcache.controllers;


import android.content.res.Resources;

import com.example.hashcache.models.HashInfo;
import com.example.hashcache.models.ScannableCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class HashInfoGenerator {

    public static class NameGenerator{

        static ArrayList<String> names;
        /**
         * Generates the monsters name.
         * @param hashValue contains the hash associated to the qr code
         * @return a string that represents the monsters name
         */
        public static String generateName(long hashValue, Resources resources){
            //generate the name from the hash information
            long hashCode = hashValue;

            return generateFunnyName(hashCode).concat(generateRealName(hashCode));
        }

        /**
         * Uses bits 0-11 from the bitmap to generate a funny name
         * @param bitmap An integer storing information to generate unique funny name
         * @return The monsters funny name
         */
        private static String generateFunnyName(long bitmap) {
            String[] firstPrefix = {"Go", "Ta", "Blo", "Fi"};
            String[] firstSuffix = {"rpus", "trox", "bulon", "mp"};
            String[] secondPrefix = {"Cro", "Aa", "Xe", "Di"};
            String[] secondSuffix = {"rg", "bol", "vex", "zzle"};
            //String[] endings = {"First", "Second", "Third", "Fourth"};
            String[] endings = {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Ruler"};

            // Add the first and second name to the funny name
            String funnyName = firstPrefix[getBits(bitmap,0,2)] + firstSuffix[getBits(bitmap,2,2)];
            funnyName += " " + secondPrefix[getBits(bitmap,4,2)] + secondSuffix[getBits(bitmap,6,2)];
            funnyName += " the " + endings[getBits(bitmap, 8, 3)];

            return funnyName;
        }

        /**
         * Generates the real name by getting an index from a file associate to the values of the 12-21 bits
         * @param bitmap An integer storing info to generate real name
         * @return The monsters reals name
         */
        private static String generateRealName(long bitmap) {
            int idx = getBits(bitmap, 12, 10);
            return names.get(idx);
        }

        /**
         * Reads from a csv file the first 1024 names then stores them in a ArrayList
         */
        private static void getNames(Resources resources) {
            String filename = "assets/names.csv";
            names = new ArrayList<String>();
            String temp;

            try {
                InputStream is = resources.getAssets().open(filename);
                Scanner scan = new Scanner(is);

                // Set the delim
                scan.useDelimiter("\n");
                scan.next();
                for (int i = 0; i < 1024 && scan.hasNext(); i++) {
                    temp = scan.next().split(",")[1];
                    names.add(temp.substring(1, temp.length() - 1));
                }
                // Close the file scanner
                scan.close();
            }  catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        /** Gets the desired bits from num1
         * @param num Number to get bits from
         * @param start Index of starting bit
         * @param n Number of bits
         * @return The value of the n bits starting from start
         */
        private static int getBits(long num, int start, int n) {
            return (int)((num >> start) & ((1 << n) - 1));
        }

    }

    public static class ImageGenerator{
        protected static String generateImage(Object hashValue){
            //generate the image from the hash information

            return null;
        }
    }

    public static class ScoreGenerator{
        protected static int generateScore(Object hashValue){
            //generate the score from the hash information

            return -1;
        }
    }

    public HashInfo createScannableCodeHashInfo(String codeContents){
        //somehow get hash value

        /** Uncomment once implementation finalized
        return new HashInfo(ImageGenerator.generateImage(hasValue),
                NameGenerator.generateName(hashValue), ScoreGenerator.generateScore(hashValue));
        */

        return null;
    }
}
