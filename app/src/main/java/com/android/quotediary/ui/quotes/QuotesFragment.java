package com.android.quotediary.ui.quotes;

import static com.android.quotediary.DataBindingAdapter.Setfont;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class QuotesFragment extends Fragment {

    private FragmentQuotesBinding binding;
    QuotesViewModel mViewModel;
    FontAdapter fontAdapter;
    public List<DataModelOther.FontStyle> fontStyleList;
    public boolean isEmpty = true;
    QuotesAdapter quotesAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(QuotesViewModel.class);
        binding = FragmentQuotesBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new ClickHandler());
        binding.setViewmdel(mViewModel);

        init();
        Observers();
        return binding.getRoot();
    }


    void init() {
        mViewModel.getQuotes(requireContext());
        quotesAdapter = new QuotesAdapter(getContext(), new ArrayList<>(), mViewModel);
        binding.quoteRecycler.setAdapter(quotesAdapter);

    }


    void Observers() {
        mViewModel.serverResponce.observe(getViewLifecycleOwner(), new Observer<List<DataModelOther.Quote>>() {
            @Override
            public void onChanged(List<DataModelOther.Quote> quotes) {
                if(quotes!=null) quotesAdapter.Update(quotes);
            }
        });
        binding.slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                binding.selectedQuote.setTextSize(value);
                mViewModel.textSize.setValue(value);
            }
        });
        mViewModel.selected.observe(getViewLifecycleOwner(), new Observer<DataModelOther.FontStyle>() {
            @Override
            public void onChanged(DataModelOther.FontStyle fontStyle) {
                if (fontStyle != null) {
                    Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/" + fontStyle.getId() + ".ttf");
                    binding.selectedQuote.setTypeface(typeface);
                }
            }
        });
//        mViewModel.selectedpos.observe(getViewLifecycleOwner(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                if(integer!=null){
//                    if(integer>-1) binding.quoteRecycler.scrollToPosition(integer);
//                }
//            }
//        });
        mViewModel.getSelectedQuote().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    if(isEmpty) {
                        Animation animation = AnimationUtils.loadAnimation(requireContext(),R.anim.top_to_bottom);
                        binding.cardlayout.startAnimation(animation);
                        isEmpty = false;
                    }
                    isEmpty = s.equals("");
                    if (fontAdapter == null) {
                        fontStyleList = new ArrayList<>();
                        get_fonts();
                        fontAdapter = new FontAdapter(fontStyleList, getContext(), mViewModel);
                        binding.fontRecycler.setAdapter(fontAdapter);

                    }
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(requireContext());
                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},120);
                        return;
                    }
                    binding.maincard.setBackground(wallpaperManager.getDrawable().getCurrent());
                    if(mViewModel.selectedpos.getValue()!=null && mViewModel.selectedpos.getValue()>-1)binding.quoteRecycler.scrollToPosition(mViewModel.selectedpos.getValue());
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

        public void White(View view){
            binding.selectedQuote.setTextColor(requireContext().getResources().getColor(R.color.white));
//            binding.selectedQuote.
        }
        public void Black(View view){
            binding.selectedQuote.setTextColor(requireContext().getResources().getColor(R.color.black));
        }


        public void onNext(View view){
//            Toast.makeText(context,"onUpdate",Toast.LENGTH_SHORT).show();

            binding.textlayout.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(binding.textlayout.getDrawingCache());

            File f = new File(requireContext().getCacheDir(),"image.png");

            try {
                if(!f.exists())
                    f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos  = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            mViewModel.setQuoteData(requireContext());
            mViewModel.updateQuotes(requireContext());




//            Intent intent = new Intent(requireActivity(), TextAppWidget1.class);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
////            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
////            // since it seems the onUpdate() is only fired on that:
//            int[] ids = AppWidgetManager.getInstance(requireActivity())
//                    .getAppWidgetIds(new ComponentName(requireActivity(), TextAppWidget1.class));
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
//            requireActivity().sendBroadcast(intent);



            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.app_name)
                    .setMessage("Add This Quote from Home Screen \n Home Screen > Widgets > Quote Dairy")

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

                    .show();




        }

        public void CloseSelected(View view){
            Animation animation = AnimationUtils.loadAnimation(requireContext(),R.anim.bottom_to_top);
            binding.cardlayout.startAnimation(animation);
            mViewModel.selectedQuote.setValue("");
            isEmpty = true;
        }



    }








    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}