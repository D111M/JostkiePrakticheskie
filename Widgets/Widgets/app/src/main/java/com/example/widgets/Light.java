package com.example.widgets;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class Light extends BroadcastReceiver {
    FlashClass flashClass;
    private boolean isLight = false;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_view);
        this.context = context.getApplicationContext();
        flashClass = new FlashClass(this.context);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.context);
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);

        Log.d("Light", String.valueOf(isLight));

        if (isLight)
        {
            views.setTextViewText(R.id.widgetFlash, "LightOff");
            flashClass.LightOff();
            isLight = false;
        }
        else
        {
            views.setTextViewText(R.id.widgetFlash, "LightOn");
            flashClass.LightOn();
            isLight = true;
        }
        appWidgetManager.updateAppWidget(thisWidget, views);
    }
}
