package com.tadi.lekovizdravstvomk.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Drug.class, Favourite.class}, version = 3, exportSchema = false)
public abstract class MonitoringDatabase extends RoomDatabase {

    private static MonitoringDatabase INSTANCE;

    public abstract DrugDao receptionDao();
    public abstract FavouriteDao favouriteDao();

    public static MonitoringDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, MonitoringDatabase.class, "lekovizdravstvodatabase")
                            //Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the exercise, allow queries on the main thread.
                            // Don't do this on a real app!
                            .allowMainThreadQueries()
                            // recreate the database if necessary
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
