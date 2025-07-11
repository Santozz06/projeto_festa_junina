package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    private final ArrayList<Produto> lista;
    private final OnItemRemovidoListener listener;
    public interface OnItemRemovidoListener {
        void onItemRemovido();
    }

    public CarrinhoAdapter(ArrayList<Produto> lista, OnItemRemovidoListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarrinhoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoAdapter.ViewHolder holder, int position) {
        Produto produto = lista.get(position);
        holder.textView.setText(produto.getNome() + " x" + produto.getQuantidade() + " = R$" + String.format("%.2f", produto.getSubtotal()));

        holder.btnRemover.setOnClickListener(v -> {
            lista.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            notifyItemRangeChanged(holder.getAdapterPosition(), lista.size());

            if (listener != null) {
                listener.onItemRemovido();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton btnRemover;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textItemProduto);
            btnRemover = v.findViewById(R.id.btnRemover);
        }
    }
}
