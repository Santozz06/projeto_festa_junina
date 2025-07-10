package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class carrinho extends AppCompatActivity {

    private RecyclerView rvCarrinho;
    private LinearLayout emptyCartView;
    private EditText editNome;
    private Spinner spinnerPagamento;
    private Button btnConfirmar;
    private TextView textTotal;
    private ArrayList<Produto> itensCarrinho;
    private CarrinhoAdapter carrinhoAdapter;
    private double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        // Inicializar componentes
        rvCarrinho = findViewById(R.id.rvCarrinho);
        emptyCartView = findViewById(R.id.emptyCartView);
        editNome = findViewById(R.id.editQuantidade); // Observação: Este ID parece errado, deveria ser editNome
        spinnerPagamento = findViewById(R.id.spinnerAtracoes); // Observação: ID estranho para pagamento
        btnConfirmar = findViewById(R.id.btnVerCarrinho); // Observação: ID não corresponde à função
        textTotal = findViewById(R.id.textTotal);

        // Receber itens do carrinho
        itensCarrinho = (ArrayList<Produto>) getIntent().getSerializableExtra("carrinho");
        if(itensCarrinho == null) {
            itensCarrinho = new ArrayList<>();
        }

        // Configurar RecyclerView
        rvCarrinho.setLayoutManager(new LinearLayoutManager(this));
        carrinhoAdapter = new CarrinhoAdapter(itensCarrinho, position -> {
            // Remover item quando clicado
            itensCarrinho.remove(position);
            carrinhoAdapter.notifyItemRemoved(position);
            atualizarVisualizacaoCarrinho();
        });
        rvCarrinho.setAdapter(carrinhoAdapter);

        // Configurar spinner de pagamento
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pagamento_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPagamento.setAdapter(adapter);

        // Configurar botão de confirmar
        btnConfirmar.setOnClickListener(v -> confirmarCompra());

        // Atualizar visualização inicial
        atualizarVisualizacaoCarrinho();
    }

    private void atualizarVisualizacaoCarrinho() {
        if (itensCarrinho.isEmpty()) {
            rvCarrinho.setVisibility(View.GONE);
            emptyCartView.setVisibility(View.VISIBLE);
            TextView emptyText = new TextView(this);
            emptyText.setText("Seu carrinho está vazio");
            emptyText.setTextSize(18);
            emptyText.setTextColor(getResources().getColor(android.R.color.black));
            emptyCartView.addView(emptyText);
        } else {
            rvCarrinho.setVisibility(View.VISIBLE);
            emptyCartView.setVisibility(View.GONE);
            calcularTotal();
        }
    }

    private void calcularTotal() {
        total = 0.0;
        for (Produto produto : itensCarrinho) {
            total += produto.getPreco() * produto.getQuantidade();
        }
        textTotal.setText(String.format("Total: %s", formatarMoeda(total)));
    }

    private String formatarMoeda(double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    private void confirmarCompra() {
        String nome = editNome.getText().toString().trim();

        if (nome.isEmpty() || nome.length() < 3) {
            editNome.setError("Nome deve ter pelo menos 3 caracteres");
            return;
        }

        if (itensCarrinho.isEmpty()) {
            Toast.makeText(this, "Adicione itens ao carrinho primeiro", Toast.LENGTH_SHORT).show();
            return;
        }

        String formaPagamento = spinnerPagamento.getSelectedItem().toString();

        // Criar reserva
        Reserva reserva = new Reserva();
        reserva.setNomeCliente(nome);
        reserva.setFormaPagamento(formaPagamento);
        reserva.setListaProdutos(itensCarrinho);
        reserva.setTotal(total);

        // Simular confirmação
        Toast.makeText(this, "Pedido confirmado! Obrigado!", Toast.LENGTH_LONG).show();

        // Voltar para tela principal
        irtelaprincipalll(null);
    }

    public void irtelaprincipalll(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}