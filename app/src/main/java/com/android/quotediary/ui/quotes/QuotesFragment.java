package com.android.quotediary.ui.quotes;

import static com.android.quotediary.DataBindingAdapter.Setfont;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.quotediary.R;
import com.android.quotediary.databinding.FragmentQuotesBinding;
import com.android.quotediary.models.DataModelOther;
import com.android.quotediary.sharedPreferenceServices;
import com.android.quotediary.ui.TextAppWidget1;
import com.google.android.material.slider.Slider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class QuotesFragment extends Fragment {

    private FragmentQuotesBinding binding;
    QuotesViewModel mViewModel;
    FontAdapter fontAdapter;
    public List<DataModelOther.FontStyle> fontStyleList;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(QuotesViewModel.class);
        binding = FragmentQuotesBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new ClickHandler());
        binding.setViewmdel(mViewModel);

        Observers();
        init();
        return binding.getRoot();
    }


    void init(){
        List<String> lIst = new ArrayList<>();
        lIst.add("Hay NO 1");
        lIst.add("Hay NO 2");
        lIst.add("hay NO 3");
        lIst.add("hay NO 4");
        QuotesAdapter quotesAdapter = new QuotesAdapter(getContext(),lIst,mViewModel);
        binding.quoteRecycler.setAdapter(quotesAdapter);
        binding.slider.addOnChangeListener(new Slider.OnChangeListener() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                            binding.selectedQuote.setTextSize(value);
                            mViewModel.textSize.setValue(value);
                        }
        });
    }


    void Observers(){
        mViewModel.selected.observe(getViewLifecycleOwner(), new Observer<DataModelOther.FontStyle>() {
            @Override
            public void onChanged(DataModelOther.FontStyle fontStyle) {
                if(fontStyle!=null){
                    Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/"+ fontStyle.getId()+".ttf");
                    binding.selectedQuote.setTypeface(typeface);
                }
            }
        });
        mViewModel.getSelectedQuote().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){

                    if(fontAdapter==null) {
                        fontStyleList = new ArrayList<>();
                        get_fonts();
                        fontAdapter = new FontAdapter(fontStyleList, getContext(), mViewModel);
                        binding.fontRecycler.setAdapter(fontAdapter);
                    }
                    binding.slider.setValue(50);
                }
            }
        });

    }

    public void get_fonts(){
        String json;
        try {
            InputStream is = requireActivity().getAssets().open("Fonts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("fonts");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj= jsonArray.getJSONObject(i);
                DataModelOther.FontStyle fontStyle = new DataModelOther.FontStyle(obj.getString("file"), obj.getString("name") );
                fontStyleList.add(fontStyle);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public class ClickHandler{



        public void onNext(View view){
//            Toast.makeText(context,"onUpdate",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireActivity(), TextAppWidget1.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
//            // since it seems the onUpdate() is only fired on that:
            int[] ids = AppWidgetManager.getInstance(requireActivity())
                    .getAppWidgetIds(new ComponentName(requireActivity(), TextAppWidget1.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            requireActivity().sendBroadcast(intent);
//            RemoteViews views = new RemoteViews(requireActivity().getPackageName(), R.layout.text_app_widget1);
//            Calendar calendar  = Calendar.getInstance();
//            views.setTextViewText(R.id.appwidget_text, calendar.getTime().getMinutes()+"");
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(requireActivity());
//            appWidgetManager.updateAppWidget(ids, views);
//            appWidgetManager.partiallyUpdateAppWidget(ids, views);
//            getActivity().getSystemService()


            mViewModel.setQuoteData(requireContext());
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.app_name)
                    .setMessage("Add This Quote from Home Screen \n Home Screen > Widgets > Quote Dairy")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Go To Home Screen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startMain);
                        }
                    })
                    .setNegativeButton("Cancle",null)

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .show();




        }
    }








    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}