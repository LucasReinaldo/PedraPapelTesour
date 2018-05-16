package com.example.lucasreinaldo.pedrapapeltesoura;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EstatisticaJogada extends AppCompatActivity {

    private Handler handlerThreadPrincipal;
    private Executor executorThreadDoBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica_jogada);

        handlerThreadPrincipal = new Handler(Looper.getMainLooper());
        executorThreadDoBanco = Executors.newSingleThreadExecutor();


        findViewById(R.id.botaoLimpar).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rodarNaThreadDoBanco(new Runnable() {
                            @Override
                            public void run() {
                                DBPedraPapelTesoura banco = DBPedraPapelTesoura
                                        .obterInstanciaUnica(EstatisticaJogada.this);
                                PartidaDao partida = banco.partidas();
                                partida.limpar();

                                atualizar();
                            }
                        });
                    }
                });

        atualizar();
    }


    void atualizar() {
        rodarNaThreadDoBanco(new Runnable() {
            @Override
            public void run() {
                DBPedraPapelTesoura banco = DBPedraPapelTesoura
                        .obterInstanciaUnica(EstatisticaJogada.this);
                PartidaDao partidas = banco.partidas();

                final List<Partida> lista = partidas.listar();

                rodarNaThreadPrincipal(new Runnable() {
                    @Override
                    public void run() {
                        ListView listaMovimentacoes = findViewById(R.id.listaMovimentacoes);

                        ArrayAdapter<Partida> adaptador = new ArrayAdapter<>(
                                EstatisticaJogada.this,
                                android.R.layout.simple_list_item_1,
                                lista);
                        listaMovimentacoes.setAdapter(adaptador);
                    }
                });

            }
        });
    }

    void rodarNaThreadPrincipal(Runnable acao) {
        handlerThreadPrincipal.post(acao);
    }

    void rodarNaThreadDoBanco(Runnable acao) {
        executorThreadDoBanco.execute(acao);
    }

}