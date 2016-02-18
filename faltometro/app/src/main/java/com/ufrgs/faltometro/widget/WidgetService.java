package com.ufrgs.faltometro.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * @author Alan Wink
 */
public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetViewsFactory(this.getApplicationContext(),intent);
    }
}
