package com.android.quotediary.ui.quotes;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.aware.WifiAwareManager;
import android.widget.RemoteViews;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.R;
import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.sharedPreferenceServices;
import com.android.quotediary.ui.TextAppWidget1;

import java.io.File;

public class QuotesViewModel extends AndroidViewModel {
    MutableLiveData<DataModelOther.FontStyle> selected;
    MutableLiveData<String> selectedQuote;
    MutableLiveData<Float>  textSize;
    public QuotesViewModel(Application application) {
        super(application);
        selectedQuote = new MutableLiveData<>("");
        selected = new MutableLiveData<>(new DataModelOther.FontStyle());
        textSize = new MutableLiveData<Float>(32f);
    }

    public MutableLiveData<DataModelOther.FontStyle> getSelected() {
        return selected;
    }

    public void setSelected(MutableLiveData<DataModelOther.FontStyle> selected) {
        this.selected = selected;
    }

    public MutableLiveData<String> getSelectedQuote() {
        return selectedQuote;
    }

    public void setSelectedQuote(MutableLiveData<String> selectedQuote) {
        this.selectedQuote = selectedQuote;
    }
//    public void setQuoteData(Context context){
//        DataModelOther.finalQuote finalQuote = new DataModelOther.finalQuote();
//        finalQuote.setQuote(selectedQuote.getValue());
//        finalQuote.setTextSize(textSize.getValue());
//        finalQuote.setStyleID(selected.getValue().getId());
//        sharedPreferenceServices.putTextStyle(finalQuote,context);
//    }

    public void updateQuotes(Context context){
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        AppWidgetProvider appWidgetProvider = new AppWidgetProvider();
        int[] ids = AppWidgetManager.getInstance(context)
                    .getAppWidgetIds(new ComponentName(context, TextAppWidget1.class));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.text_app_widget1);

        File file = new File(context.getCacheDir(), "image.png");

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);


        views.setImageViewBitmap(R.id.myimage, bitmap);

        widgetManager.updateAppWidget(ids,views);


    }
}