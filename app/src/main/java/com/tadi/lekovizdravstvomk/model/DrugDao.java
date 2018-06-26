package com.tadi.lekovizdravstvomk.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Dao
public interface DrugDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addDrug(Drug drug);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDrug(Collection<Drug> drugs);

    @Query("select * from drugs")
    public List<Drug> getAllDrugs();

    @Query("select * from drugs where isFavovite=1")
    public List<Drug> getAllFavoritesDrugs();

    @Query("select * from drugs where id = :Id")
    public List<Drug> getDrugsById(String Id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDrug(Drug drug);

    @Query("UPDATE drugs SET isFavovite=1 where id=:Id")
    void updateDrug(int Id);

    @Query("delete from drugs")
    void removeAllDrug();
}
