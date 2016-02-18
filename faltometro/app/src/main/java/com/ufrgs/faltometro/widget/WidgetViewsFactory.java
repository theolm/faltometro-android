package com.ufrgs.faltometro.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.utils.Tags;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.List;

/**
 * @author Alan Wink
 */
public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetViewsFactory";

    private Context mContext;
    private int appWidgetId;

    private DatabaseHandler mDatabase;
    private List<DisciplineVo> mDisciplineList;

    public WidgetViewsFactory(Context context, Intent intent) {
        Log.d(TAG, "WidgetViewsFactory() called with: " + "context = [" + context + "], intent = [" + intent + "]");
        mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        mDatabase = new DatabaseHandler(mContext);
        mDisciplineList = mDatabase.getAllDisciplines();

    }

    @Override
    public void onCreate() {
        //no-op
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged() called with: " + "");
        mDisciplineList = mDatabase.getAllDisciplines(); //TODO: Check if this is right
    }

    @Override
    public void onDestroy() {
        //no-op
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount() called with: " + "");
        Log.d(TAG, "getCount() returned: " + mDisciplineList.size());
        return mDisciplineList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt() called with: " + "position = [" + position + "]");
        DisciplineVo discipline = mDisciplineList.get(position);
        RemoteViews remoteView = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        remoteView.setInt(R.id.widget_item_layout, "setBackgroundColor", Color.parseColor(discipline.cor));
        remoteView.setTextViewText(R.id.widget_item_name, discipline.name);
        remoteView.setTextViewText(R.id.widget_item_ratio, discipline.totalFaults + "/" + discipline.maxFaults);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d(TAG, "getViewTypeCount() called with: " + "");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId() called with: " + "position = [" + position + "]");
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
