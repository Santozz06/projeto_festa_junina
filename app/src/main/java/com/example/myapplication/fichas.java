package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class fichas extends AppCompatActivity {

    private Spinner spinnerProdutos;
    private EditText editQuantidade;
    private Button btnAdicionar;
    private ArrayList<Produto> carrinho = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fichas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar componentes
        spinnerProdutos = findViewById(R.id.spinnerAtracoes);
        editQuantidade = findViewById(R.id.editQuantidade);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        // Configurar o spinner com os produtos
        configurarSpinnerProdutos();

        // Configurar o botão de adicionar ao carrinho
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarAoCarrinho();
            }
        });
    }

    private void configurarSpinnerProdutos() {
        // Criar lista de produtos (conforme documentação)
        ArrayList<String> produtos = new ArrayList<>();
        produtos.add("Pé de moleque - R$5.00");
        produtos.add("Pipoca - R$3.00");
        produtos.add("Cachorro-quente - R$5.00");
        produtos.add("Refrigerante - R$5.00");
        produtos.add("Quentão - R$4.00");
        // Adicione outros produtos conforme a documentação

        // Configurar o adapter para o spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                produtos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProdutos.setAdapter(adapter);
    }

    private void adicionarAoCarrinho() {
        try {
            // Validar quantidade
            String quantidadeStr = editQuantidade.getText().toString();
            if (quantidadeStr.isEmpty()) {
                Toast.makeText(this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantidade = Integer.parseInt(quantidadeStr);
            if (quantidade <= 0) {
                Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obter produto selecionado
            String produtoSelecionado = spinnerProdutos.getSelectedItem().toString();

            // Extrair nome e preço do produto
            String[] partes = produtoSelecionado.split(" - R\\$");
            String nomeProduto = partes[0];
            double preco = Double.parseDouble(partes[1]);

            // Criar objeto Produto e adicionar ao carrinho
            Produto produto = new Produto(nomeProduto, preco, quantidade);
            carrinho.add(produto);

            // Feedback para o usuário
            Toast.makeText(this, "Produto adicionado ao carrinho!", Toast.LENGTH_SHORT).show();

            // Limpar campo de quantidade
            editQuantidade.setText("");

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao adicionar produto", Toast.LENGTH_SHORT).show();
        }
    }

    public void irtelaprincipal(View view) {
        Intent i = new Intent(fichas.this, MainActivity.class);
        startActivity(i);
    }

    public void ircarrinho(View view) {
        // Passar o carrinho para a activity do carrinho
        Intent i = new Intent(fichas.this, carrinho.class);
        i.putExtra("carrinho", carrinho);
        startActivity(i);
    }
}