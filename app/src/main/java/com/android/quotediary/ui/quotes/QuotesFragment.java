package com.android.quotediary.ui.quotes;

import static com.android.quotediary.DataBindingAdapter.Setfont;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.quotediary.databinding.FragmentQuotesBinding;
import com.android.quotediary.models.DataModelOther;
import com.google.android.material.slider.Slider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        fontStyleList = new ArrayList<>();
        get_fonts();
        fontAdapter = new FontAdapter(fontStyleList,getContext(),mViewModel);
        binding.fontRecycler.setAdapter(fontAdapter);
        Observers();
        binding.slider.setValue(50);
        binding.slider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                binding.selectedQuote.setTextSize(value);
            }
        });
        return binding.getRoot();
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
                DataModelOther.FontStyle fontStyle = new DataModelOther.FontStyle(obj.getString("file"), obj.getString("name"),false );
                fontStyleList.add(fontStyle);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}