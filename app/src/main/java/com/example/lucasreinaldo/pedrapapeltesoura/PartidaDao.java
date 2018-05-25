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

     @Query("SELECT count(id) FROM partidas WHERE resultado = 'Desse um banhu' ")
     int contarVitorias();

     @Query("SELECT count(id) FROM partidas WHERE resultado = 'Ti arrombassi' ")
     int contarDerrotas();

     @Query("SELECT count(id) FROM partidas WHERE resultado = 'Mofassi' ")
     int contarEmpates();

     @Query("SELECT count(id) FROM partidas WHERE resultado = 'Desse um banhu' or  'Ti arrombassi' or 'Mofassi' ")
     int contarJogadas();
}


//@Query("SELECT * FROM partida WHERE escolha_app AND escolha_usuario")
//Partida findByName(String escolhaApp, String escolhaUsuario);
