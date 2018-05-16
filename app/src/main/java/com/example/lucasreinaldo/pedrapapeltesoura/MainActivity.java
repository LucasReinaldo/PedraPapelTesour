package com.example.lucasreinaldo.pedrapapeltesoura;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handlerThreadPrincipal = new Handler(Looper.getMainLooper());
        executorThreadDoBanco = Executors.newSingleThreadExecutor();
    }

     private Handler handlerThreadPrincipal;
     private Executor executorThreadDoBanco;

     public void selecionarPedra(View view){
        this.opcaoSelecionada("pedra");
     }

     public void selecionarPapel(View view){
         this.opcaoSelecionada("papel");
     }

     public void selecionarTesoura(View view){
         this.opcaoSelecionada("tesoura");
     }

        // intent para chamar a tela na qual irá constar as estatísticas
        //como jogadas e demais...

     public void estatisticaBotao (View button){
         Intent estatisticaIntent = new Intent(MainActivity.this, EstatisticaJogada.class);
         startActivityForResult(estatisticaIntent, 0);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        atualizar();
    }

    public void opcaoSelecionada(final String escolhaUsuario){

        /* poderia usar a escolha do usuario com numeros inteiros, porem decidi fazer um array
           de Strings para ficar mais intuito a forma como o codigo esta sendo criado ficando
           mais facil o entendimento.
           coloquei o this.find... como boa pratica.
         */

        ImageView imagemResutado = this.findViewById(R.id.imageResultado);
        final TextView textoResultado = this.findViewById(R.id.textResultado);
//        TextView textoVitoria = this.findViewById(R.id.Vitoria);
//        TextView textoDerrota = this.findViewById(R.id.Derrota);
//        TextView textoEmpate = this.findViewById(R.id.Empate);

        final String[] opcoes = {"pedra", "papel", "tesoura"};
        final int numero = new Random().nextInt(3);
        final String escolhaApp = opcoes[numero];

        // o switch foi feito para o app escolher uma imagem dentro de drawable, se tornando assim
        // a escolha do app.

        switch (escolhaApp){
            case "pedra" : imagemResutado.setImageResource(R.drawable.pedra);
                break;
            case  "papel" : imagemResutado.setImageResource(R.drawable.papel);
                break;
            case  "tesoura" : imagemResutado.setImageResource(R.drawable.tesoura);
                break;
        }

         final Partida p = new Partida();
         p.escolhaUsuario = escolhaUsuario.toString();
         p.escolhaApp = escolhaApp.toString();

        /* estarei utilizando o ifelse para verificar as condicoes de ganhador e perdedor
           no if estou verificando as condicoes do app ser ganhador
           no ifelse as do usuario e o que sobra sao os empates
         */

        if (
                (escolhaApp == "pedra" && escolhaUsuario == "tesoura") ||
                (escolhaApp == "papel" && escolhaUsuario == "pedra") ||
                (escolhaApp == "tesoura" && escolhaUsuario == "papel")

                ){
            textoResultado.setText("Você Perdeu! :(");
            p.resultado = "Derrota";

        }else if (
                (escolhaUsuario == "pedra" && escolhaApp == "tesoura") ||
                (escolhaUsuario == "papel" && escolhaApp == "pedra") ||
                (escolhaUsuario == "tesoura" && escolhaApp == "papel")

                ){
            textoResultado.setText("Você Ganhou! :)");
            p.resultado = "Vitória";

        }else {
            textoResultado.setText("Empatou, tente outra vez!");
            p.resultado = "Empate";
        }

         rodarNaThreadDoBanco(new Runnable() {
             @Override
             public void run() {
                 DBPedraPapelTesoura banco = DBPedraPapelTesoura
                         .obterInstanciaUnica(MainActivity.this);
                 PartidaDao partida = banco.partidas();
                 partida.adicionar(p);

                 atualizar();

             }
         });
     }

    void atualizar() {
        rodarNaThreadDoBanco(new Runnable() {
            @Override
            public void run() {
                DBPedraPapelTesoura banco = DBPedraPapelTesoura
                        .obterInstanciaUnica(MainActivity.this);
                PartidaDao partidas = banco.partidas();
                final int vitorias = partidas.contarVitorias();
                final int derrotas = partidas.contarDerrotas();
                final int empates = partidas.contarEmpates();

                rodarNaThreadPrincipal(new Runnable() {
                    @Override
                    public void run() {
                        TextView textoVitoria = findViewById(R.id.Vitoria);
                        textoVitoria.setText("" + vitorias);
                        TextView textoDerrota = findViewById(R.id.Derrota);
                        textoDerrota.setText("" + derrotas);
                        TextView textoEmpate = findViewById(R.id.Empate);
                        textoEmpate.setText("" + empates);
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
