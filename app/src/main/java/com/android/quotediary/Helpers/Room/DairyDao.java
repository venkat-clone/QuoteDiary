package com.android.quotediary.Helpers.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DairyDao {

    @Insert
    public void insert(DairyEntity dairyEntity);

    @Query("SELECT * FROM Dairy")
    public LiveData<List<DairyEntity>> getAll();

    @Query("SELECT * FROM Dairy WHERE year=:Year")
    public List<DairyEntity> getDairyS(int Year);

    @Query("SELECT * FROM Dairy WHERE year=:Year")
    public LiveData<List<DairyEntity>> getDairySLive(int Year);

    @Query("DELETE FROM Dairy")
    void deleteDairy();

    @Insert()
    void insert(DairyEntity.Year Year);

    @Query("SELECT * FROM Year")
    public LiveData<List<DairyEntity.Year>> getYearsLive();

    @Query("SELECT * FROM Year")
    public List<DairyEntity.Year> getYears();

    @Query("DELETE FROM Year")
    void deleteYear();

    @Query("UPDATE Dairy SET content = :content WHERE year = :year AND day_in_year = :day")
    int updateDairy(int year,int day, String content);

    @Query("SELECT * FROM year WHERE year = :year")
    public DairyEntity.Year getYear(int year);

}
