package com.example.lucasreinaldo.pedrapapeltesoura;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

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

        findViewById(R.id.imageTesoura).setOnClickListener(

                new View.OnClickListener() {

                        rodarNaThreadDoBanco(new Runnable() {
                            @Override
                            public void run() {
                                DBPedraPapelTesoura banco = DBPedraPapelTesoura
                                        .obterInstanciaUnica(EstatisticaJogada.this);
                                PartidaDao partidas = banco.partidas();

                                finish();
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