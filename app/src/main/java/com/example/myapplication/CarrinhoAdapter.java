package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder> {

    private ArrayList<Produto> itens;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CarrinhoAdapter(ArrayList<Produto> itens, OnItemClickListener listener) {
        this.itens = itens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_carrinho, parent, false);
        return new CarrinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {
        Produto produto = itens.get(position);
        holder.textProduto.setText(String.format(Locale.getDefault(),
                "%s x%d - %s",
                produto.getNome(),
                produto.getQuantidade(),
                formatarMoeda(produto.getPreco() * produto.getQuantidade())));

        holder.btnRemover.setOnClickListener(v -> listener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    private String formatarMoeda(double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        TextView textProduto;
        ImageButton btnRemover;

        public CarrinhoViewHolder(@NonNull View itemView) {
            super(itemView);
            textProduto = itemView.findViewById(R.id.textProdutoCarrinho);
            btnRemover = itemView.findViewById(R.id.btnRemover);
        }
    }
}