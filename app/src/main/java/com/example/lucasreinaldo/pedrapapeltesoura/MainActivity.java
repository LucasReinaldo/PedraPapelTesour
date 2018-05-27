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


    public void selecionarTarrafa(View view){
        this.opcaoSelecionada("tarrafa");
    }

    public void selecionarMane(View view){
        this.opcaoSelecionada("mane");
    }

    public void selecionarJerere(View view){
        this.opcaoSelecionada("jerere");
    }

    public void selecionarSiri(View view){
        this.opcaoSelecionada("siri");
    }

    public void selecionarTainha(View view){
        this.opcaoSelecionada("tainha");
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
        final TextView textoEscolha = this.findViewById (R.id.textoEscolha);
//        TextView textoVitoria = this.findViewById(R.id.Vitoria);
//        TextView textoDerrota = this.findViewById(R.id.Derrota);
//        TextView textoEmpate = this.findViewById(R.id.Empate);

        final String[] opcoes = { "tarrafa", "mane", "jerere", "siri", "tainha"};
        final int numero = new Random().nextInt(5);
        final String escolhaApp = opcoes[numero];

        // o switch foi feito para o app escolher uma imagem dentro de drawable, se tornando assim
        // a escolha do app.

        switch (escolhaApp){

            case  "tarrafa" : imagemResutado.setImageResource(R.drawable.tarrafa);
                break;
            case  "mane" : imagemResutado.setImageResource(R.drawable.mane);
                break;
            case  "jerere" : imagemResutado.setImageResource(R.drawable.jerere);
                break;
            case  "siri" : imagemResutado.setImageResource(R.drawable.siri);
                break;
            case  "tainha" : imagemResutado.setImageResource(R.drawable.tainha);
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

                (escolhaApp == "mane" && escolhaUsuario == "tarrafa")
                ) {
            textoEscolha.setText ("O Mané joga a Tarrafa!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";

        }else if (

                (escolhaApp == "mane" && escolhaUsuario == "jerere")

                ){ textoEscolha.setText ("O Mané joga o Jereré!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";


        }else if (

                (escolhaApp == "siri" && escolhaUsuario == "mane")

                ){ textoEscolha.setText ("O Siri garra no Mané!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";


        }else if (

                (escolhaApp == "tainha" && escolhaUsuario == "mane")

                ){ textoEscolha.setText ("A Tainha dixpara Mané!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";


        }else if (

                (escolhaApp == "tarrafa" && escolhaUsuario == "tainha")

                ){ textoEscolha.setText ("A Tarrafa pesca a Tainha!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";


        }else if (

                (escolhaApp == "jerere" && escolhaUsuario == "siri")

                ){ textoEscolha.setText ("O Jereré pesca o Siri!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";

        }else if (

                (escolhaApp == "tarrafa" && escolhaUsuario == "siri")

                ){ textoEscolha.setText ("A Tarrafa pesca o Siri!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";

        }else if (

                (escolhaApp == "jerere" && escolhaUsuario == "tarrafa")

                ){ textoEscolha.setText ("O Jereré escangalha a Tarrafa!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";

        }else if (

                (escolhaApp == "tainha" && escolhaUsuario == "jerere")

                ){ textoEscolha.setText ("A Tainha dixpara do jereré!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";

        }else if (

                (escolhaApp == "siri" && escolhaUsuario == "tainha")

                ){ textoEscolha.setText ("O Siri garra na Tainha!");
            textoResultado.setText ("Coisa Medonha!!! Você Perdeu! :)");
            p.resultado = "Ti arrombassi";


        }else if (

                (escolhaUsuario == "mane" && escolhaApp == "tarrafa")
                ) {
            textoEscolha.setText ("O Mané joga a Tarrafa!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";

        }else if (

                (escolhaUsuario == "mane" && escolhaApp == "jerere")

                ){ textoEscolha.setText ("O Mané joga o Jereré!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";


        }else if (

                (escolhaUsuario == "siri" && escolhaApp == "mane")

                ){ textoEscolha.setText ("O Siri garra no Mané!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";


        }else if (

                (escolhaUsuario == "tainha" && escolhaApp == "mane")

                ){ textoEscolha.setText ("A Tainha dixpara do Mané!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";


        }else if (

                (escolhaUsuario == "tarrafa" && escolhaApp == "tainha")

                ){ textoEscolha.setText ("A Tarrafa pesca a Tainha!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";


        }else if (

                (escolhaUsuario == "jerere" && escolhaApp == "siri")

                ){ textoEscolha.setText ("O Jereré pesca o Siri!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";

        }else if (

                (escolhaUsuario == "tarrafa" && escolhaApp == "siri")

                ){ textoEscolha.setText ("A Tarrafa pesca o Siri!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";

        }else if (

                (escolhaUsuario == "jerere" && escolhaApp == "tarrafa")

                ){ textoEscolha.setText ("O Jereré escangalha a Tarrafa!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";

        }else if (

                (escolhaUsuario == "tainha" && escolhaApp == "jerere")

                ){ textoEscolha.setText ("A Tainha dixpara do jereré!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";

        }else if (

                (escolhaUsuario == "siri" && escolhaApp == "tainha")

                ){ textoEscolha.setText ("O Siri garra na Tainha!");
            textoResultado.setText ("Dazum Banhu!!! Você Ganhou! :)");
            p.resultado = "Desse um banhu";



        }else {
            textoEscolha.setText("Mofas ca pomba na balaia!!!");
            textoResultado.setText("Empatou, tente outra vez!");
            p.resultado = "Mofassi";
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
                final int jogadas = 51 - partidas.contarJogadas ();

                rodarNaThreadPrincipal(new Runnable() {
                    @Override
                    public void run() {
                        TextView textoJogadas = findViewById (R.id.Jogadas);
                        textoJogadas.setText("Jogadas:  " + jogadas);
                        TextView textoVitoria = findViewById(R.id.Vitoria);
                        textoVitoria.setText("Vitorias:  " + vitorias);
                        TextView textoDerrota = findViewById(R.id.Derrota);
                        textoDerrota.setText("Derrotas:  " + derrotas);
                        TextView textoEmpate = findViewById(R.id.Empate);
                        textoEmpate.setText("Empates:  " + empates);

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
