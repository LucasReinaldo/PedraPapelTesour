package com.example.lucasreinaldo.pedrapapeltesoura;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PartidaDao {
     @Insert
     void adicionar(Partida p);

     @Query("SELECT * from partidas")
     List<Partida> listar();

     @Query("DELETE from partidas")
     void limpar();

     @Query("SELECT count(id) FROM partidas WHERE resultado = 'Vitória' ")
     int contarVitorias();

     @Query("SELECT count(id) FROM partidas WHERE resultado = 'Derrota' ")
     int contarDerrotas();

     @Query("SELECT count(id) FROM partidas WHERE resultado = 'Empate' ")
     int contarEmpates();
}


    //@Query("SELECT * FROM partida WHERE escolha_app AND escolha_usuario")
    //Partida findByName(String escolhaApp, String escolhaUsuario);

