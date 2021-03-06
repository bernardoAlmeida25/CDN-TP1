
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
    public static final int LOGIN = 0, REGISTO = 1, ATUALIZA_USERS_LOGIN = 2,MENSAGEM_CHAT_PRIVATE = 3, GRUPO = 4,ATUALIZA_GRUPOS = 5,GRUPO_MSM = 6, GRUPO_JUNTAR = 7,SAIR = 8,REMOVER_GRUPO = 9,ATUALIZA_GRUPOS_REMOVIDO = 10, SAIR_GRUPO = 11, ATUALIZA_USER_EXIT_GROUP = 12,ENVIA_FILE = 13,ATUALAZIAR_GRUPOS = 14,UTILIZADORES_REGISTADOS = 15,CHANGE_USER_CHAT=16,ATUALIZAR_MENSAGENS_OFFLINE = 17,MENSAGEM_GERAL = 18;
    protected int tipo_msm,tipo_msm_file;
    protected int id_comunica;
    protected boolean bool;
    protected byte[] file;
    protected Grupo grupo;


   //variaveis utilizadas no protocolo
    protected String enviou, recebeu, conteudo_msm, aux,aux2,user_login;
    protected ArrayList<String> array_utilizadores; //lista de utilizadores para saber utiladores on
    protected ArrayList<Grupo> grupos_criados; //lista de grupos para saber grupos
    //protected ArrayList<Save_mensagens> m;
    
    //variavel yes or not
    private ArrayList<Save_mensagens> mensagens_offline;

    
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
    
    // tipo msms, id_cliente, quem_enviou, quem_recebe, nome_ficheiro, aux, file
    public Mensagem(int REGISTO, int id_cliente, String nome_user, String nome_quem_recebe, String nome_ficheiro, int aux,byte[] file_to_send) {
        this.tipo_msm = REGISTO;
        this.id_comunica = id_cliente;
        this.enviou = nome_user;
        this.recebeu = nome_quem_recebe;
        this.conteudo_msm = nome_ficheiro;
        this.tipo_msm_file = aux;
        this.file = file_to_send;
        
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

    public Mensagem(int LOGIN, int id, String username_cliente, String nome_cliente, boolean login, ArrayList nome_user_online, ArrayList groups) {
        this.tipo_msm = LOGIN;
        this.id_comunica = id;
        this.enviou = username_cliente;
        this.recebeu = nome_cliente;
        this.bool = login;
        this.array_utilizadores = nome_user_online;
        this.grupos_criados = groups;
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

    public Mensagem(int ATUALAZIAR_GRUPOS, int id, String username_cliente, String nome_cliente, Grupo add_util_grupo) {
        this.tipo_msm = ATUALAZIAR_GRUPOS;
        this.id_comunica = id;
        this.enviou = username_cliente;
        this.recebeu = nome_cliente;
        this.grupo = add_util_grupo;
    }


    public Mensagem(int UTILIZADORES_REGISTADOS, int id, ArrayList<Save_mensagens> mensagens) {
        this.tipo_msm = UTILIZADORES_REGISTADOS;
        this.id_comunica = id;
        this.mensagens_offline = mensagens;
    }

    public Mensagem(int UTILIZADORES_REGISTADOS, int id, ArrayList<String> nome_registados, String a) {
        this.tipo_msm = UTILIZADORES_REGISTADOS;
        this.id_comunica = id;
        this.array_utilizadores = nome_registados;
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

    public ArrayList<Grupo> getGrupos_criados() {
        return grupos_criados;
    }

    public void setGrupos_criados(ArrayList<Grupo> grupos_criados) {
        this.grupos_criados = grupos_criados;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }
    
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getTipo_msm_file() {
        return tipo_msm_file;
    }

    public void setTipo_msm_file(int tipo_msm_file) {
        this.tipo_msm_file = tipo_msm_file;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public ArrayList<Save_mensagens> getMensagens_offline() {
        return mensagens_offline;
    }

    public void setMensagens_offline(ArrayList<Save_mensagens> mensagens_offline) {
        this.mensagens_offline = mensagens_offline;
    }

    
    @Override
    public String toString() {
        return "Mensagem{" + "tipo_msm=" + tipo_msm + ", id_comunica=" + id_comunica + ", bool=" + bool + ", enviou=" + enviou + ", recebeu=" + recebeu + ", conteudo_msm=" + conteudo_msm + ", aux=" + aux + ", aux2=" + aux2 + ", user_login=" + user_login + ", array_utilizadores=" + array_utilizadores + ", grupos_criados=" + grupos_criados + '}';
    }
    
    
    
    
    
}
