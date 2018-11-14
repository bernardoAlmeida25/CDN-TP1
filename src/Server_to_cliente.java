

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author pedro
 */
public interface Server_to_cliente extends java.rmi.Remote{
    public void response_init_client( int d ) throws RemoteException;
    
    public void response_to_cliente( Mensagem mensagem ) throws RemoteException;
    
//    public void response_registo_client( Boolean d, int id ) throws RemoteException;
//
//    public void response_login_client(boolean login, int id, String username_cliente, String nome_cliente, ArrayList nome_user_online, ArrayList groups) throws RemoteException;
//
//    public void response_envia_mensagem(String enviou, String conteudo_msm) throws RemoteException;
//
//    public void response_novo_login(int id, int id0, String nome) throws RemoteException;

}
