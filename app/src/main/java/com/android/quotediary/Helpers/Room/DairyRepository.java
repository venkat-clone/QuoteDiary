package com.android.quotediary.Helpers.Room;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.quotediary.models.Dairy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DairyRepository {
    private DairyDao dairyDao;
    private LiveData<List<DairyEntity>> liveDairyList;


    public DairyRepository(Context application){

        DairyDataBase db = DairyDataBase.getDatabase(application);
        dairyDao = db.DairyDao();
        liveDairyList = dairyDao.getAll();
    }

    public LiveData<List<DairyEntity>> getAll(){
        liveDairyList = dairyDao.getAll();
        return liveDairyList;
    }
    public void insert(DairyEntity.Year year){
        DairyDataBase.databaseWriteExecutor.execute(()->{
            dairyDao.insert(new DairyEntity.Year(2020));
        });
    }

    public void insertServerResponce(List<Dairy.ServerDairy> list, MutableLiveData<Integer> responce){
//        DairyDataBase.databaseWriteExecutor.execute(()->{
//            for (Dairy.ServerDairy dairy:list) {
//                    dairyDao.insert(new DairyEntity(dairy.getDay(),dairy.getId(),dairy.getYear(), dairy.getContent()));
//            }
//            responce.setValue(200);
//        });
        DairyDataBase.databaseWriteExecutor.execute(
                new Runnable(){
                public void run(){
            for (Dairy.ServerDairy dairy:list) {
                dairyDao.insert(new DairyEntity(dairy.getDay(),dairy.getId(),dairy.getYear(), dairy.getContent()));
                if(!dairyDao.hasYear(dairy.getYear())) dairyDao.insert(new DairyEntity.Year(dairy.getYear()));
            }
            return;
        }
                });
        responce.setValue(200);

    }



    public void CleanDB(){
        DairyDataBase.databaseWriteExecutor.execute(()->{
            dairyDao.deleteYear();
            dairyDao.deleteDairy();
        });
    }

    public void insert(Dairy dairy){
        DairyEntity dairyEntity = new DairyEntity(dairy.getDay(), dairy.getYear(), dairy.getContent());
        DairyDataBase.databaseWriteExecutor.execute(() -> {
            try{
                dairyDao.insert(new DairyEntity.Year(dairy.getYear()));
            }catch (Exception e){

            }
            dairyDao.insert(dairyEntity);
        });
    }

    public void getYears(MutableLiveData<List<Dairy.Year>> yearlist){
        DairyDataBase.databaseWriteExecutor.execute(()->{
            List<DairyEntity.Year> years  = dairyDao.getYears();
            List<Dairy.Year> yearList = new ArrayList<>();
            Dairy.ServerDairy today = Dairy.getToday();
            boolean found = false;
            for (DairyEntity.Year year: years) {
                List<DairyEntity> DNList = dairyDao.getDairyS(year.getYear());
                List<Dairy.ServerDairy> dairies = new ArrayList<>();
                for (DairyEntity dairy:DNList) {
                    if(today.getDay()==dairy.getDay() && today.getYear()==dairy.getYear()) { found = true; }
                    dairies.add(new Dairy.ServerDairy(dairy.getId_(),dairy.getContent(), dairy.getYear(), dairy.getDay()));

                }
                if(today.getYear()==year.getYear() && !found) {
                    today.unsaved = true;
                    today.isToday = true;
                    dairies.add(today);
                    found = true;
//                    try{
//                        dairyDao.insert(new DairyEntity(today.getDay(),today.getYear(), today.getContent()));
//                    }catch (Exception e){
//
//                    }
                }
                Collections.reverse(dairies);
                yearList.add(new Dairy.Year(year.getYear(), dairies));
            }
            if(!found){
                today.isToday = true;
                today.unsaved = true;
                List<Dairy.ServerDairy> tod = new ArrayList<>();tod.add(today);
                Dairy.Year yr = new Dairy.Year(today.getYear(),tod);yr.selected=true;
                yearList.add(yr);
                try{
                    dairyDao.insert(new DairyEntity.Year(today.getYear()));
//                    dairyDao.insert(new DairyEntity(today.getDay(),today.getYear(), today.getContent()));
                }catch (Exception e){
//
                }
            }
            Collections.sort(yearList, new Comparator<Dairy.Year>() {
                @Override
                public int compare(Dairy.Year lhs, Dairy.Year rhs) {
                    return lhs.getYear()-(rhs.getYear());
                }
            });
            yearlist.postValue(yearList);
        });
    }


    public void insert(DairyEntity Dairy) {
        DairyDataBase.databaseWriteExecutor.execute(() -> {
            dairyDao.insert(Dairy);
        });
    }

    public void update(Dairy.ServerDairy dairy, MutableLiveData<Boolean> dbResponse){
        DairyDataBase.databaseWriteExecutor.execute(()->{

            try{
                DairyEntity dairyEntity = new DairyEntity(dairy.getDay(),dairy.getId(),dairy.getYear(), dairy.getContent());

                if(dairy.unsaved)
                    dairyDao.insert(dairyEntity);
                else
                    dairyDao.updateDairy(dairy.getYear(),dairy.getDay(), dairy.getContent());
            }catch (Exception e){
                e.printStackTrace();
            }


        });
    }

}
