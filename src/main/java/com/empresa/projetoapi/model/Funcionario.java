package com.empresa.projetoapi.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Funcionario {

    private int id;
    private String nome;
    private String sobrenome;
    private int idade;
    private String email;
    private String cargo;
    private int empresaId; 

    public Funcionario(int id, String nome, String sobrenome, int idade, String email, String cargo, int empresaId) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.email = email;
        this.cargo = cargo;
        this.empresaId = empresaId; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }
}

