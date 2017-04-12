package com.gogaworm.easyjlpt.utils;

import android.content.Context;
import com.gogaworm.easyjlpt.R;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 05.04.2017.
 *
 * @author ikarpova
 */
public class English2KanaConverter {
    private Map<String, String> mapping = new HashMap<>();

    public English2KanaConverter() {
    }

    public void initMapping(Context context) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.mapping);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String str;
        while ((str = reader.readLine()) != null){
            String[] parts = str.split(" ");
            mapping.put(parts[0].trim(), parts[1].trim());
        }
    }

    public String convert(String text) {
        StringBuilder buffer = new StringBuilder();
        String pattern = "";
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (ch >= 'A' && ch <= 'Z') {
                pattern = pattern + ch;
                String replace = mapping.get(pattern);
                if (replace != null) {
                    buffer.append(replace);
                    pattern = "";
                }
            } else {
                buffer.append(ch);
            }
        }
        if (pattern.length() != 0) {
            buffer.append(pattern);
        }
        return buffer.toString();
    }
}
