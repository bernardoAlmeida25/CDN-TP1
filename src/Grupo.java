/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe para criar novos grupos
 */
public class Grupo implements Serializable {

    private int id_criador;
    private String nome_grupo;
    private String nome_criador;
    private ArrayList<User_group> utilizadores_online;
    private String data_criacao;


    public Grupo(String nome, String nome_criador, String data_criacao) {
        this.nome_grupo = nome;
        this.nome_criador = nome_criador;
        this.data_criacao = data_criacao;
    }
    
    public Grupo(int id, String nome, String nome_criador, String data_criacao) {
        this.id_criador = id;
        this.nome_grupo = nome;
        this.nome_criador = nome_criador;
        this.data_criacao = data_criacao;
        this.utilizadores_online = new ArrayList<>();

    }

    public void add_utilizador(User_group user){
        this.utilizadores_online.add(user);
    }
    
    public ArrayList<User_group> getUtilizadores_online() {
        return utilizadores_online;
    }

    public void setUtilizadores_online(ArrayList<User_group> utilizadores_online) {
        this.utilizadores_online = utilizadores_online;
    }
   
    
   

    public int getId() {
        return id_criador;
    }

    public void setId(int id) {
        this.id_criador = id;
    }

    public String getNome_grupo() {
        return nome_grupo;
    }

    public void setNome_grupo(String nome_grupo) {
        this.nome_grupo = nome_grupo;
    }

    public String getNome_criador() {
        return nome_criador;
    }

    public void setNome_criador(String nome_criador) {
        this.nome_criador = nome_criador;
    }

    

    public String getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }
    


    @Override
    public String toString() {
        return "Grupo{" + "id=" + id_criador + ", nome_grupo=" + nome_grupo + ", nome_criador=" + nome_criador + ", data_criacao=" + data_criacao + '}';
    }

    
    

}
