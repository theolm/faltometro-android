package com.ufrgs.faltometro.widget;

import android.app.Activity;
import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ufrgs.faltometro.R;

/**
 * @author Alan Wink
 */
public class WidgetUpdate {
    private static final String TAG = "WidgetUpdate";

    public static void update(Activity activity){
        update(activity.getApplication());
    }

    public static void update(Application app){
        Log.d(TAG, "update() called with: " + "activity = [" + app + "]");
        int ids[] = AppWidgetManager.getInstance(app).getAppWidgetIds(new ComponentName(app, WidgetProvider.class));
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(app);
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widget_list_view);
    }
}
