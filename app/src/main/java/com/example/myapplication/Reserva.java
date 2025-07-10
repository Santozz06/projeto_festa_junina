package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Reserva implements Serializable {
    private String nomeCliente;
    private String formaPagamento;
    private ArrayList<Produto> listaProdutos;
    private double total;

    // Construtor
    public Reserva() {
        this.listaProdutos = new ArrayList<>();
    }

    // Getters e Setters
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public ArrayList<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(ArrayList<Produto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}