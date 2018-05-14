package com.example.lucasreinaldo.pedrapapeltesoura;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

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
         startActivity(estatisticaIntent);
     }


     public void opcaoSelecionada( String escolhaUsuario){

        /* poderia usar a escolha do usuario com numeros inteiros, porem decidi fazer um array
           de Strings para ficar mais intuito a forma como o codigo esta sendo criado ficando
           mais facil o entendimento.
           coloquei o this.find... como boa pratica.
         */

        ImageView imagemResutado = this.findViewById(R.id.imageResultado);
        TextView textoResultado = this.findViewById(R.id.textResultado);
        TextView textoVitoria = this.findViewById(R.id.Vitoria);
        TextView textoDerrota = this.findViewById(R.id.Derrota);
        TextView textoEmpate = this.findViewById(R.id.Empate);

        String[] opcoes = {"pedra", "papel", "tesoura"};
        int numero = new Random().nextInt(3);
        String escolhaApp = opcoes[numero];

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


        }else if (
                (escolhaUsuario == "pedra" && escolhaApp == "tesoura") ||
                (escolhaUsuario == "papel" && escolhaApp == "pedra") ||
                (escolhaUsuario == "tesoura" && escolhaApp == "papel")

                ){
            textoResultado.setText("Você Ganhou! :)");


        }else {
            textoResultado.setText("Empatou, tente mais uma vez!");

        }

     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handlerThreadPrincipal = new Handler(Looper.getMainLooper());
        executorThreadDoBanco = Executors.newSingleThreadExecutor();

        // Fiz essa parte para que sempre que clicar em um botao para jogar
        // o banco de dados vai salvar a informacao
        // Não cheguei a testar ainda, mas acredito qeu a lógica seja essa,
        // pra gente nao colocar um botao de salvar como no exemplo do prof.
        findViewById(R.id.imageTesoura).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(
                                MainActivity.this, EstatisticaJogada.class), 0);
                    }
                });
        findViewById(R.id.imagePedra).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(
                                MainActivity.this, EstatisticaJogada.class), 0);
                    }
                });
        findViewById(R.id.imagePapel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(
                                MainActivity.this, EstatisticaJogada.class), 0);
                    }
                });

        findViewById(R.id.botaoLimpar).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rodarNaThreadDoBanco(new Runnable() {
                            @Override
                            public void run() {
                                DBPedraPapelTesoura banco = DBPedraPapelTesoura
                                        .obterInstanciaUnica(MainActivity.this);
                                PartidaDao partida = banco.partidas();
                                partida.limpar();

                                atualizar();
                            }
                        });
                    }
                });

        atualizar();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        atualizar();
    }

    void atualizar() {
        rodarNaThreadDoBanco(new Runnable() {
            @Override
            public void run() {
                DBPedraPapelTesoura banco = DBPedraPapelTesoura
                        .obterInstanciaUnica(MainActivity.this);
                PartidaDao partidas = banco.partidas();

                final List<Partida> lista = partidas.listar();

                rodarNaThreadPrincipal(new Runnable() {
                    @Override
                    public void run() {
                        TextView textoVitoria = findViewById(R.id.Vitoria);
                        TextView textoEmpate = findViewById(R.id.Empate);
                        TextView textoDerrota = findViewById(R.id.Derrota);
                        ListView listaMovimentacoes = findViewById(R.id.listaMovimentacoes);

                        ArrayAdapter<Partida> adaptador = new ArrayAdapter<>(
                                MainActivity.this,
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
