package com.android.quotediary.ui.quotes;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

//        list.add(new DataModelOther.FontStyle("01"));
//        list.add(new DataModelOther.FontStyle("02"));
//        list.add(new DataModelOther.FontStyle("03"));
//        list.add(new DataModelOther.FontStyle("04"));
//        list.add(new DataModelOther.FontStyle("05"));
//        list.add(new DataModelOther.FontStyle("06"));
//        list.add(new DataModelOther.FontStyle("07"));
//        list.add(new DataModelOther.FontStyle("08"));
        get_fonts();
        fontAdapter = new FontAdapter(fontStyleList,getContext(),mViewModel);
        binding.fontRecycler.setAdapter(fontAdapter);
        Observers();
        return binding.getRoot();
    }

    void Observers(){
        mViewModel.selected.observe(getViewLifecycleOwner(), new Observer<DataModelOther.FontStyle>() {
            @Override
            public void onChanged(DataModelOther.FontStyle fontStyle) {
                if(fontStyle!=null){

                }
            }
        });
    }

    public void get_fonts(){
        String json;
        try {
            InputStream is = getActivity().getAssets().open("Fonts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read();
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
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