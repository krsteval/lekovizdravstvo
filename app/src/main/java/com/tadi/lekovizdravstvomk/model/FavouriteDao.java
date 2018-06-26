package com.tadi.lekovizdravstvomk.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Collection;
import java.util.List;

@Dao
public interface FavouriteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addFavourite(Favourite favourite);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFavourite(Collection<Favourite> favourite);

    @Query("select * from favourite")
    public List<Favourite> getAllFavourite();

    @Query("select * from favourite where id = :Id")
    public List<Favourite> getFavouriteById(String Id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavourite(Favourite drug);


    @Query("delete from favourite")
    void removeAllFavourite();
}
