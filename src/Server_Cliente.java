
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
public class Server_Cliente implements Serializable{

    protected Server_to_cliente tm;
    protected int id;
    protected String nome_cliente;
    protected String username_cliente;
    protected String password_cliente;
    

    Server_Cliente(Server_to_cliente tm) {
        this.tm = tm;
    }

    Server_Cliente(Server_to_cliente r, int id_cliente) {
         this.tm = r;
         this.id = id_cliente;
    }

    public Server_to_cliente getTm() {
        return tm;
    }

    public void setTm(Server_to_cliente tm) {
        this.tm = tm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Server_Cliente{"+ id + ", nome_cliente=" + nome_cliente + ", username_cliente=" + username_cliente + ", password_cliente=" + password_cliente + '}';
    }
    
    

}
