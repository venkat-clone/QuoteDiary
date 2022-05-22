package com.android.quotediary.ui.quotes;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.aware.WifiAwareManager;
import android.widget.RemoteViews;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.quotediary.R;
import com.android.quotediary.Reterofit.Repository.UserRepository;
import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.sharedPreferenceServices;
import com.android.quotediary.ui.TextAppWidget1;

import java.io.File;
import java.util.List;

public class QuotesViewModel extends AndroidViewModel {
    MutableLiveData<DataModelOther.FontStyle> selected;
    MutableLiveData<String> selectedQuote;
    MutableLiveData<List<DataModelOther.Quote>> serverResponce;
    MutableLiveData<Float>  textSize;
    UserRepository userRepository ;
    MutableLiveData<Integer> selectedpos;
    MutableLiveData<Boolean> isLoading;
    int Page =0;
    public QuotesViewModel(Application application) {
        super(application);
        selectedQuote = new MutableLiveData<>("");
        selected = new MutableLiveData<>(new DataModelOther.FontStyle());
        textSize = new MutableLiveData<Float>(32f);
        serverResponce = new MutableLiveData<>();
        userRepository = new UserRepository();
        selectedpos  = new MutableLiveData<>(-1);
        isLoading = new MutableLiveData<>(true);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(MutableLiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    public void getQuotes(Context context){
        if(Page >=0) userRepository.getQuotes(context,serverResponce,++Page);
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
        int[] ids = AppWidgetManager.getInstance(context)
                    .getAppWidgetIds(new ComponentName(context, TextAppWidget1.class));
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.text_app_widget1);
//
//        File file = new File(context.getFilesDir(), "image.png");
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
//
//
//        views.setImageViewBitmap(R.id.myimage, bitmap);

//        widgetManager.updateAppWidget(ids,views);
        Intent intent = new Intent(context, TextAppWidget1.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
//            // since it seems the onUpdate() is only fired on that:

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(intent);



    }
}