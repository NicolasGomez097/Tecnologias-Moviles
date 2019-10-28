package org.moviles.persistance;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.moviles.model.Clima;


@androidx.room.Database(entities = {Clima.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private final String databaseName = "AppDatabase";

    public abstract ClimaDAO climaDAO();

    private static volatile Database INSTANCE;

    static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,Database.class, "databaseName").build();
                }
            }
        }
        return INSTANCE;
    }
}
