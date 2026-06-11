package entity;

import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Pedido{
    long id;
    String nomeCliente;
    LocalDate dataDeEntrega;
    String statusProduto;
    String statusPagamento;
    String formaDePagamento;
    Float total;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataDeEntrega() {
        return dataDeEntrega;
    }

    public void setDataDeEntrega(LocalDate dataDeEntrega) {
        this.dataDeEntrega = dataDeEntrega;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(String statusProduto) {
        this.statusProduto = statusProduto;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}