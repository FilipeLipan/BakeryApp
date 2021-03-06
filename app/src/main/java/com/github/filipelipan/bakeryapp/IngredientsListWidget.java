package com.github.filipelipan.bakeryapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.github.filipelipan.bakeryapp.data.model.Step;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsListWidget extends AppWidgetProvider {

	static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
								int appWidgetId) {

//		CharSequence widgetText = context.getString(R.string.appwidget_text);
		// Construct the RemoteViews object
		RemoteViews views = getGardenGridRemoteView(context);
//		views.setTextViewText(R.id.appwidget_text, widgetText);

		// Instruct the widget manager to update the widget
		appWidgetManager.updateAppWidget(appWidgetId, views);

	}

	/**
	 * Creates and returns the RemoteViews to be displayed in the GridView mode widget
	 *
	 * @param context The context
	 * @return The RemoteViews for the GridView mode widget
	 */
	private static RemoteViews getGardenGridRemoteView(Context context) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
		// Set the GridWidgetService intent to act as the adapter for the GridView
		Intent intent = new Intent(context, WidgetListService.class);
		views.setRemoteAdapter(R.id.widget_list_view, intent);
		// Set the PlantDetailActivity intent to launch when clicked
		Intent appIntent = new Intent(context, MainActivity.class);
		PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		views.setPendingIntentTemplate(R.id.widget_list_view, appPendingIntent);
		// Handle empty gardens
		views.setEmptyView(R.id.widget_list_view, R.id.empty_view);
		return views;
	}

	public static void updateRecipeListWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// There may be multiple widgets active, so update all of them
		for (int appWidgetId : appWidgetIds) {
			updateAppWidget(context, appWidgetManager, appWidgetId);
		}
	}

	@Override
	public void onEnabled(Context context) {
		// Enter relevant functionality for when the first widget is created
	}

	@Override
	public void onDisabled(Context context) {
		// Enter relevant functionality for when the last widget is disabled
	}
}

