package com.devbruto.treetorrah.Model;

import java.io.Serializable;

public class Registro implements Serializable {

    private int Id;
    private int Ano;
    private String Estado;
    private int ArvoresCortadas;

    @Override
    public String toString() {
        return Integer.toString(Ano);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAno() {
        return Ano;
    }

    public void setAno(int ano) {
        Ano = ano;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public int getArvoresCortadas() {
        return ArvoresCortadas;
    }

    public void setArvoresCortadas(int arvoresCortadas) {
        ArvoresCortadas = arvoresCortadas;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int volume) {
        Volume = volume;
    }

    public int getArvoresRespostas() {
        return ArvoresRespostas;
    }

    public void setArvoresRespostas(int arvoresRespostas) {
        ArvoresRespostas = arvoresRespostas;
    }

    public float getValorMulta() {
        return ValorMulta;
    }

    public void setValorMulta(float valorMulta) {
        ValorMulta = valorMulta;
    }

    private int Volume;
    private int ArvoresRespostas;
    private float ValorMulta;
}