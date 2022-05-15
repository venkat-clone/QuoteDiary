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
            Dairy today = Dairy.getToday();
            boolean found = false;
            for (DairyEntity.Year year: years) {
                List<DairyEntity> DNList = dairyDao.getDairyS(year.getYear());
                List<Dairy> dairies = new ArrayList<>();
                for (DairyEntity dairy:DNList) {
                    dairies.add(new Dairy(dairy.getContent(), dairy.getYear(), dairy.getDay()));
                    if(today.getDay()==dairy.getDay() && today.getYear()==dairy.getYear()) found=true;
                }
                if(today.getYear()==year.getYear() && !found) {
                    dairies.add(today);
                    found = true;
                    try{
                        dairyDao.insert(new DairyEntity(today.getDay(),today.getYear(), today.getContent()));
                    }catch (Exception e){

                    }
                }
                yearList.add(new Dairy.Year(year.getYear(), dairies));
            }
            if(!found){
                today.isToday = true;
                List<Dairy> tod = new ArrayList<>();tod.add(today);
                Dairy.Year yr = new Dairy.Year(today.getYear(),tod);yr.selected=true;
                yearList.add(yr);
                try{
                    dairyDao.insert(new DairyEntity.Year(today.getYear()));
                    dairyDao.insert(new DairyEntity(today.getDay(),today.getYear(), today.getContent()));
                }catch (Exception e){

                }
            }
            yearlist.postValue(yearList);
        });
    }


    public void insert(DairyEntity Dairy) {
        DairyDataBase.databaseWriteExecutor.execute(() -> {
            dairyDao.insert(Dairy);
        });
    }

    public void update(Dairy dairy, MutableLiveData<Boolean> dbResponse){
        DairyDataBase.databaseWriteExecutor.execute(()->{

            try{
                dairyDao.updateDairy(dairy.getYear(),dairy.getDay(), dairy.getContent());
                dbResponse.setValue(true);
            }catch (Exception e){
                e.printStackTrace();
            }


        });
    }

}
