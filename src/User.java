
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */
public class User implements Serializable{
    protected String nome_cliente;
    protected String username_cliente;
    protected String password_cliente;
    protected boolean online;

    public User(String nome_cliente, String username_cliente, String password_cliente) {
        this.nome_cliente = nome_cliente;
        this.username_cliente = username_cliente;
        this.password_cliente = password_cliente;
        this.online = false;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getUsername_cliente() {
        return username_cliente;
    }

    public void setUsername_cliente(String username_cliente) {
        this.username_cliente = username_cliente;
    }

    public String getPassword_cliente() {
        return password_cliente;
    }

    public void setPassword_cliente(String password_cliente) {
        this.password_cliente = password_cliente;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    
    
}
