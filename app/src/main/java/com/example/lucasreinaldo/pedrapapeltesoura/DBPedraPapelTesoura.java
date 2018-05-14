package com.example.lucasreinaldo.pedrapapeltesoura;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Partida.class}, version = 1, exportSchema = false)
public abstract class DBPedraPapelTesoura extends RoomDatabase {

    private static DBPedraPapelTesoura instancia;

    public static DBPedraPapelTesoura obterInstanciaUnica(
            Context context) {
        synchronized (DBPedraPapelTesoura.class) {
            if (instancia == null)
                instancia = Room.databaseBuilder(
                        context.getApplicationContext(),
                        DBPedraPapelTesoura.class,"banco").build();

            return instancia;
        }
    }

    public abstract PartidaDao partidas();

}
