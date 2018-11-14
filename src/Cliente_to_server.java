

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.RemoteException;

/**
 *
 * @author pedro
 */
public interface Cliente_to_server extends java.rmi.Remote{
    public void init_client( Server_to_cliente r ) throws RemoteException;
    
    public void comunica_com_server( Mensagem mensagem ) throws RemoteException;
    
    

//    public void envia_mensagem(Mensagem mensagem) throws RemoteException;
//
//    public void efetua_registo(Mensagem mensagem) throws RemoteException;
//
//    public void efetua_login(Mensagem mensagem) throws RemoteException;
}
