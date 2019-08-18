package com.gogaworm.easyjlpt.dataexplorer;

import com.gogaworm.easyjlpt.dataexplorer.data.Kanji;
import com.gogaworm.easyjlpt.dataexplorer.data.KanjiLesson;
import com.gogaworm.easyjlpt.dataexplorer.data.Word;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class DataFileConverter {
    public static void main(String... args) throws IOException {
        String filename = "e://work/other/EasyJlpt/app/src/main/assets/data/N2/kanji/Nihongo Sou Matome/lesson_211.json";

        DataFileConverter dataFileConverter = new DataFileConverter();
        KanjiLesson kanjiLesson = dataFileConverter.loadJsonFromFile(filename);
        dataFileConverter.updateLesson(kanjiLesson);
        dataFileConverter.saveJsonToFile("e://work/other/EasyJlpt/app/src/main/assets/data/N2/kanji/Nihongo Sou Matome/lesson_211_1.json", kanjiLesson);
    }

    private KanjiLesson loadJsonFromFile(String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            return gson.fromJson(bufferedReader, KanjiLesson.class);
        }
    }

    private void saveJsonToFile(String fileName, KanjiLesson kanjiLesson) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(fileName)) {
            writer.write(gson.toJson(kanjiLesson));
        }
    }

    private void updateLesson(KanjiLesson kanjiLesson) {
        for (Kanji kanji : kanjiLesson.kanji) {
            if (kanji.id == null) {
                kanji.id = getNextId();
            }
            for (Word word : kanji.words) {
                if (word.id == null) {
                    word.id = getNextId();
                }
            }
        }
    }

    public static String getNextId() {
        return getAlphaNumericString(10) + "##";
    }

    private static String getAlphaNumericString(int maxLength) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(maxLength);

        for (int i = 0; i < maxLength; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
