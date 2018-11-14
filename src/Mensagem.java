
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */
public class Mensagem implements Serializable{
    public static final int LOGIN = 0, REGISTO = 1, ATUALIZA_USERS_LOGIN = 2,MENSAGEM_CHAT_PRIVATE = 3, GRUPO = 4,ATUALIZA_GRUPOS = 5,GRUPO_MSM = 6, GRUPO_JUNTAR = 7,SAIR = 8,REMOVER_GRUPO = 9,ATUALIZA_GRUPOS_REMOVIDO = 10, SAIR_GRUPO = 11, ATUALIZA_USER_EXIT_GROUP = 12,FICHEIRO_ENV = 13,PED_ENVIAR_FICHEIRO = 14;
    protected int tipo_msm;
    protected int id_comunica;
    protected boolean bool;
   //variaveis utilizadas no protocolo
    protected String enviou, recebeu, conteudo_msm, aux,aux2,user_login;
    protected ArrayList<String> array_utilizadores; //lista de utilizadores para saber utiladores on
    protected ArrayList<String> grupos_criados; //lista de grupos para saber grupos
    
    //variavel yes or not

    
    //variavel yes or not
    public Mensagem(int tipo_msm, int id, boolean bool){
        this.tipo_msm = tipo_msm;
        this.bool = bool;
        this.id_comunica = id;
    }
    // tipo msms, id_cliente, username, nome, password
    public Mensagem(int REGISTO, int id_cliente, String username_var, String nome_var, String repetir_password_var) {
        this.tipo_msm = REGISTO;
        this.id_comunica = id_cliente;
        this.enviou = username_var;
        this.recebeu = "SERVER";
        this.conteudo_msm = nome_var;
        this.aux = repetir_password_var;
        
    }
    
    public Mensagem(int REGISTO, int id_cliente, String username_var, String quem_recebe, String msm, String aux) {
        this.tipo_msm = REGISTO;
        this.id_comunica = id_cliente;
        this.enviou = username_var;
        this.recebeu = quem_recebe;
        this.conteudo_msm = msm;
        this.aux = aux;
        
    }

    public Mensagem(int LOGIN, int id, String username_cliente, String nome_cliente, boolean login) {
        this.tipo_msm = LOGIN;
        this.id_comunica = id;
        this.enviou = username_cliente;
        this.recebeu = nome_cliente;
        this.bool = login;
    }

    public Mensagem(int LOGIN, int id, String username_cliente, String nome_cliente, boolean login, ArrayList nome_user_online, ArrayList arrayList) {
        this.tipo_msm = LOGIN;
        this.id_comunica = id;
        this.enviou = username_cliente;
        this.recebeu = nome_cliente;
        this.bool = login;
        this.array_utilizadores = nome_user_online;
        this.grupos_criados = arrayList;
    }

    public Mensagem(int ATUALIZA_USERS_LOGIN, String nome) {
        this.tipo_msm = ATUALIZA_USERS_LOGIN;
        this.user_login = nome;
    }

    public Mensagem(int GRUPO_MSM, String enviou, String nome_grupo, String conteudo_msm) {
        this.tipo_msm = GRUPO_MSM;
        this.enviou = enviou;
        this.recebeu = nome_grupo;
        this.conteudo_msm = conteudo_msm;
    }

    public int getTipo_msm() {
        return tipo_msm;
    }

    public void setTipo_msm(int tipo_msm) {
        this.tipo_msm = tipo_msm;
    }

    public int getId_comunica() {
        return id_comunica;
    }

    public void setId_comunica(int id_comunica) {
        this.id_comunica = id_comunica;
    }

    public String getEnviou() {
        return enviou;
    }

    public void setEnviou(String enviou) {
        this.enviou = enviou;
    }

    public String getRecebeu() {
        return recebeu;
    }

    public void setRecebeu(String recebeu) {
        this.recebeu = recebeu;
    }

    public String getConteudo_msm() {
        return conteudo_msm;
    }

    public void setConteudo_msm(String conteudo_msm) {
        this.conteudo_msm = conteudo_msm;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    public String getAux2() {
        return aux2;
    }

    public void setAux2(String aux2) {
        this.aux2 = aux2;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public ArrayList<String> getArray_utilizadores() {
        return array_utilizadores;
    }

    public void setArray_utilizadores(ArrayList<String> array_utilizadores) {
        this.array_utilizadores = array_utilizadores;
    }

    public ArrayList<String> getGrupos_criados() {
        return grupos_criados;
    }

    public void setGrupos_criados(ArrayList<String> grupos_criados) {
        this.grupos_criados = grupos_criados;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "tipo_msm=" + tipo_msm + ", id_comunica=" + id_comunica + ", bool=" + bool + ", enviou=" + enviou + ", recebeu=" + recebeu + ", conteudo_msm=" + conteudo_msm + ", aux=" + aux + ", aux2=" + aux2 + ", user_login=" + user_login + ", array_utilizadores=" + array_utilizadores + ", grupos_criados=" + grupos_criados + '}';
    }
    
    
    
    
    
}
