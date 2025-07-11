package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class carrinho extends AppCompatActivity {

    private RecyclerView rvCarrinho;
    private CarrinhoAdapter adapter;
    private TextView textTotal;
    private EditText editNome;
    private Spinner spinnerPagamento;
    private Button btnConfirmar;
    private ArrayList<Produto> carrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        rvCarrinho = findViewById(R.id.rvCarrinho);
        textTotal = findViewById(R.id.textTotal);
        editNome = findViewById(R.id.editQuantidade);
        spinnerPagamento = findViewById(R.id.spinnerAtracoes);
        btnConfirmar = findViewById(R.id.btnVerCarrinho);
        carrinho = (ArrayList<Produto>) getIntent().getSerializableExtra("carrinho");

        // Se vazio, mostrar view de vazio
        if (carrinho == null || carrinho.isEmpty()) {
            rvCarrinho.setVisibility(View.GONE);
            findViewById(R.id.emptyCartView).setVisibility(View.VISIBLE);
        } else {
            rvCarrinho.setVisibility(View.VISIBLE);
            findViewById(R.id.emptyCartView).setVisibility(View.GONE);
            adapter = new CarrinhoAdapter(carrinho, this::atualizarTotal);
            rvCarrinho.setLayoutManager(new LinearLayoutManager(this));
            rvCarrinho.setAdapter(adapter);
            atualizarTotal();
        }

        btnConfirmar.setOnClickListener(v -> confirmarCompra());
    }

    private void atualizarTotal() {
        double total = 0;
        for (Produto p : carrinho) {
            total += p.getSubtotal();
        }
        textTotal.setText(String.format(Locale.getDefault(), "Total: R$ %.2f", total));

        if (carrinho.isEmpty()) {
            rvCarrinho.setVisibility(View.GONE);
            findViewById(R.id.emptyCartView).setVisibility(View.VISIBLE);
        }
    }


    private void confirmarCompra() {
        if (carrinho == null || carrinho.isEmpty()) {
            Toast.makeText(this, "Adicione itens ao carrinho antes de reservar", Toast.LENGTH_SHORT).show();
            return;
        }

        String nome = editNome.getText().toString().trim();
        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!nome.matches("^[a-zA-ZÀ-ÿ\\s]{3,100}$")) {
            Toast.makeText(this, "Verifique se preencheu corretamente seu nome", Toast.LENGTH_SHORT).show();
            return;
        }

        String pagamento = spinnerPagamento.getSelectedItem().toString();
        if (pagamento.isEmpty()) {
            Toast.makeText(this, "Selecione uma forma de pagamento", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Pedido Reservado!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void irtelaprincipalll(View view) {
        finish();
    }
}
