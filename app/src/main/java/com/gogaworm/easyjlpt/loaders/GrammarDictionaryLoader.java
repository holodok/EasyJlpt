package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Grammar;
import com.gogaworm.easyjlpt.data.GrammarSearchResult;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.gogaworm.easyjlpt.loaders.GrammarLoader.parseMeanings;

/**
 * Created on 15.10.2017.
 *
 * @author ikarpova
 */
public class GrammarDictionaryLoader extends JlptDataLoader<GrammarSearchResult> {
    GrammarDictionaryLoader(Context context, JlptSection section) {
        super(context, section, JlptLevel.ALL);
    }

    @Override
    protected List<GrammarSearchResult> load() throws IOException, JSONException {
        LinkedList<GrammarSearchResult> results = new LinkedList<>();
        //loadFromFile(results, section, level, Type.);
/* //todo: load from all files
        List<String> fileList = new ArrayList<>();
        for (JlptLevel level : new JlptLevel[]{JlptLevel.N1, JlptLevel.N2, JlptLevel.N3, JlptLevel.N4, JlptLevel.N5}) {
            try {
                String path = section.name().toLowerCase() + '_' + level;
                String[] files = getContext().getAssets().list(path);
                if (files == null || files.length == 0) continue;
                for (String file : files) {
                    if (file.startsWith("lesson_")) {
                        fileList.add(path + '/' + file.replace(".json", ""));
                    }
                }
            } catch (IOException ex) {
                Log.e("GrammarDictionaryLoader", "Can't load file list.", ex);
            }
        }
*/
        return results;
    }

    @Override
    protected void parseJson(List<GrammarSearchResult> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray jsonGrammarList = root.optJSONArray("grammar");
        if (jsonGrammarList != null) {
            for (int i = 0; i < jsonGrammarList.length(); i++) {
                JSONObject jsonGrammar = jsonGrammarList.getJSONObject(i);
                String item = jsonGrammar.getString("item");
                Grammar.Meaning[] meanings = parseMeanings(jsonGrammar.getJSONArray("meanings"));
                for (Grammar.Meaning meaning : meanings) {
                    //results.add(new GrammarSearchResult(0, item, meaning));
                }
            }
        }
    }
}
