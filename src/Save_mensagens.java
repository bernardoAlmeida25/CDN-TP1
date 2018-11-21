/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe para guardar mensagens
 */
public class Save_mensagens implements Serializable {

    protected String user_enviou, user_recebeu, conteudo_msm,data;

    public Save_mensagens(String user_enviou, String user_recebeu, String conteudo_msm) {
        this.user_enviou = user_enviou;
        this.user_recebeu = user_recebeu;
        this.conteudo_msm = conteudo_msm;
        get_atual_date();
    }

    public String getUser_enviou() {
        return user_enviou;
    }

    public void setUser_enviou(String user_enviou) {
        this.user_enviou = user_enviou;
    }

    public String getUser_recebeu() {
        return user_recebeu;
    }

    public void setUser_recebeu(String user_recebeu) {
        this.user_recebeu = user_recebeu;
    }

    public String getConteudo_msm() {
        return conteudo_msm;
    }

    public void setConteudo_msm(String conteudo_msm) {
        this.conteudo_msm = conteudo_msm;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Mensagens{" + "user_enviou=" + user_enviou + ", user_recebeu=" + user_recebeu + ", conteudo_msm=" + conteudo_msm + '}';
    }

    private void get_atual_date() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	this.data = ""+dateFormat.format(date); //data_atual
    }

}
