package com.example.lucasreinaldo.pedrapapeltesoura;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "partidas")
public class Partida {
    @PrimaryKey (autoGenerate = true)
    public int id;

    @ColumnInfo(name = "escolha_app")
    public String escolhaApp;

    @ColumnInfo(name = "escolha_usuario")
    public String escolhaUsuario;

    @ColumnInfo(name = "resultado")
    public String resultado;


}
