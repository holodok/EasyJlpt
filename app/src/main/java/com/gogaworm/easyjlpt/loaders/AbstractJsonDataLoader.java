package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public abstract class AbstractJsonDataLoader <T> extends AsyncTaskLoader<List<T>> {
    protected List<T> data;
    public String errorMessage;

    public AbstractJsonDataLoader(Context context) {
        super(context);
    }

    @Override
    public List<T> loadInBackground() {
        errorMessage = null;
        try {
            return load();
        } catch (Exception ex) {
            Log.e(getContext().getResources().getString(R.string.app_name), ex.getLocalizedMessage(), ex);
            errorMessage = ex.getLocalizedMessage();
        }
        return null;
    }

    protected abstract List<T> load() throws IOException, JSONException;

    protected void loadFromFile(List<T> results, JlptSection section, JlptLevel level, Type type, int id) throws IOException, JSONException {
        String fileName = section.name().toLowerCase() + '_' + level.name();
        switch (type) {
            case SECTION:
                fileName += "/sections";
                break;
            case LESSON:
                fileName += "/lesson_" + id;
                break;
            case EXAM:
                fileName += "/exam_" + id;
                break;
        }
        String json = loadJSONFromAsset(getContext(), fileName);
        parseJson(results, json);
    }

    protected abstract void parseJson(List<T> results, String json) throws JSONException;

    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(List<T> data) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (data != null) {
                onReleaseResources(data);
            }
        }
        List<T> oldApps = this.data;
        this.data = data;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(data);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (data != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(data);
        }

        if (takeContentChanged() || data == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(List<T> data) {
        super.onCanceled(data);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(data);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (data != null) {
            onReleaseResources(data);
            data = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<T> data) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
        if (data != null) {
            data.clear();
        }
    }

    protected  String loadJSONFromAsset(Context context, String fileName) throws IOException {
        StringBuilder text = new StringBuilder();
        InputStream is = context.getAssets().open(fileName + ".json");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
            }
            bufferedReader.close();
        } finally {
            is.close();
        }
        return text.toString();
    }
}
