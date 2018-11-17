/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class ServerImpl implements Remote, Cliente_to_server {

    private int id_cliente;
    private static ServerImpl rmi;
    protected ArrayList<Server_Cliente> clientes;
    protected ArrayList<Grupo> grupos;
    //contem todos os utilizadores registados
    protected ArrayList<User> user;
    protected Thread thread = null;

    public ServerImpl() {
        this.id_cliente = 0;
        this.clientes = new ArrayList<>();
        this.grupos = new ArrayList<>();
        this.user = new ArrayList<>();
        this.user.add(new User("aaaa", "aaaa", "aaaa"));
        this.user.add(new User("bbbb", "bbbb", "bbbb"));
        this.user.add(new User("cccc", "cccc", "cccc"));
    }

    public static void main(String[] args) {
        System.setProperty("java.security.policy", "file:/policy.all");
        // We need to set the security manager to the RMISecurityManager
        System.setSecurityManager(new RMISecurityManager());

        try {
            rmi = new ServerImpl();

            LocateRegistry.createRegistry(2001);
            System.out.println("Registry created");

            UnicastRemoteObject.exportObject(((Cliente_to_server) rmi));

            Naming.rebind("//localhost:2001/ChatServer", rmi);

            System.out.println("Bindings Finished");

            System.out.println("Waiting for Client requests");
        } catch (java.rmi.UnknownHostException uhe) {
            System.out.println("The host computer name you have specified does not match your real computer name.");
        } catch (RemoteException re) {
            System.out.println("Error starting service");
            System.out.println("" + re);
        } catch (MalformedURLException mURLe) {
            System.out.println("Internal error" + mURLe);
        }
    }  // main

    /*
    *COMUNICA COM cliente
    **/
    @Override
    public synchronized void init_client(Server_to_cliente r) throws RemoteException {
        System.out.println("Client requesting a connection");

        Server_Cliente tt;

        // Insert two lines of code that complete the implementation of this method.
        // 1. Create a new TimeTicker object
        // 2. Start this new object running.
        this.id_cliente++;
        tt = new Server_Cliente(r, id_cliente);
        clientes.add(tt);

//        tt.start();
        tt.getTm().response_init_client(this.id_cliente);

        System.out.println("Chat Started");
    }
    
    @Override
    public synchronized void comunica_com_server(Mensagem mensagem) throws RemoteException {
        int tipo_msm = mensagem.getTipo_msm();
        Server_Cliente sc;
        
        try {
            switch (tipo_msm) {
                case Mensagem.LOGIN:
                    sc = procura_cliente_ID(mensagem.getId_comunica());
                    boolean login = false;
                    try {
                        User cliente_user = retorna_user_dados(mensagem.getEnviou());
                        if (cliente_user != null && sc != null) {
//                            if (sc != null) {
//                                if (sc.getId() == mensagem.getId_comunica() && sc.getUsername_cliente().equals(mensagem.getEnviou()) && sc.getPassword_cliente().equals(mensagem.getConteudo_msm())) {
                                sc.setNome_cliente(cliente_user.getNome_cliente());
                                sc.setPassword_cliente(cliente_user.getPassword_cliente());
                                sc.setUsername_cliente(cliente_user.getUsername_cliente());
                                login = true;
//                                }
//                            }
                        }
                        if (sc != null) {
                            sc.getTm().response_to_cliente(new Mensagem(Mensagem.LOGIN, sc.getId(), sc.getUsername_cliente(), sc.getNome_cliente(), login, nome_user_online(sc.getId()), new ArrayList()));
                            if (login) {
                                envia_novo_login_other_users(sc.getId(), sc.getNome_cliente());
                            }
                        }

                    } catch (NullPointerException e) {
                        sc.getTm().response_to_cliente(new Mensagem(Mensagem.LOGIN, sc.getId(), "", "", login, new ArrayList(), new ArrayList()));
                    }
                    break;
                    
                case Mensagem.REGISTO:
                    sc = procura_cliente_ID(mensagem.getId_comunica());
                    boolean user_validation = false;
                    if (sc != null) {
                        if (verifica_existe_user_name(mensagem.getEnviou())) {
                            this.user.add(new User(mensagem.getConteudo_msm(), mensagem.getEnviou(), mensagem.getAux()));
                            user_validation = true;
                        }

                        sc.getTm().response_to_cliente(new Mensagem(Mensagem.REGISTO, sc.getId(), user_validation));
                    }
                    break;
                case Mensagem.MENSAGEM_CHAT_PRIVATE:
                    
                    sc = procura_cliente_nome(mensagem.getRecebeu());
                    System.out.println(mensagem.toString());
                    if(sc != null){
                        //                                     (int REGISTO, int id_cliente, String nome_quem enviou, String quem_recebe, String msm, String aux)
                        sc.getTm().response_to_cliente(new Mensagem(Mensagem.MENSAGEM_CHAT_PRIVATE, sc.getId(), sc.getNome_cliente(),mensagem.getEnviou(),mensagem.getConteudo_msm(),""));
                        Server_Cliente me = procura_cliente_ID(mensagem.getId_comunica());
                        me.getTm().response_to_cliente(new Mensagem(Mensagem.MENSAGEM_CHAT_PRIVATE, me.getId(), me.getNome_cliente(), sc.getNome_cliente(), mensagem.getConteudo_msm(),""));
                    }
                    break;
                
                case Mensagem.GRUPO:
                    //int REGISTO, int id_cliente, String username_var, String quem_recebe, String msm, String aux
                    //Mensagem.GRUPO,cliente.getId_cliente(), utilizador_nome, "Server", nome_novo_grupo, "" + dateFormat.format(date)
                    //Adiciona um novo grupo
                    String nome_grupo = mensagem.getConteudo_msm();
                    sc = procura_cliente_ID(mensagem.getId_comunica());

                    if (sc != null) { //Verifica se o utilizador que está a criar o grupo existe
                        if (Verifica_grupo(nome_grupo, sc, mensagem.getAux())) { //Verifica se o grupo criado é válido
                            Atualiza_InformationAll(Mensagem.ATUALIZA_GRUPOS, sc.getNome_cliente(), nome_grupo); //Atualiza a lista de grupos de todos os clientes
                        } else {
                            //erro ao criar grupo
                            sc.getTm().response_to_cliente(new Mensagem(Mensagem.GRUPO, sc.getId(), false));
                        }
                    }

                    break;
                case Mensagem.GRUPO_JUNTAR:
                    sc = procura_cliente_ID(mensagem.getId_comunica());
                    if (!mensagem.getRecebeu().isEmpty() && sc != null) { //Verifica se o utilizador e o grupo exsitem
                        Grupo add_util_grupo = procura_grupoNome(mensagem.getRecebeu());

                        if (add_util_grupo != null) { //Verifica se grupo existe
                            Verifica_SeUser_ExisteOtherGroups_and_Remove( sc.getUsername_cliente()); //Verifica se o utilizador já pertence ao grupo
                            add_util_grupo.add_utilizador(new User_group(sc.getNome_cliente(), sc.getUsername_cliente())); //Adiciona o utilizador a um array de usernames
                        }
                        sc.getTm().response_to_cliente(new Mensagem(Mensagem.ATUALAZIAR_GRUPOS, sc.getId(), sc.getUsername_cliente(), sc.getNome_cliente(), add_util_grupo));
                    }
                    break;
                    
                case Mensagem.GRUPO_MSM:
                    //Envia uma mensagem para todos os membros do respetivo grupo através do multicast
                    sc = procura_cliente_ID(mensagem.getId_comunica());
                    if (mensagem.getEnviou() != null && sc != null) { //verifica se o cliente que enviou a mensagem existe e se o grupo existe
                        Grupo procura_grupo = procura_grupoNomeUser(mensagem.getRecebeu());
                        if (procura_grupo != null) { //verifica se o utilizador existe naquele grupo  
                            send_to_all_member_group(procura_grupo,mensagem.getEnviou(), mensagem.getConteudo_msm());
                        }
                    }
                    break;
                    
                case Mensagem.SAIR:
                    //int tipo_msm, int id, boolean bool
                    sc = procura_cliente_ID(mensagem.getId_comunica());
                    String nome_cliente_saiu = "";
                    if (sc != null) {
                        nome_cliente_saiu = sc.getNome_cliente();
                        if (!mensagem.isBool()) {
                            Verifica_SeUser_ExisteOtherGroups_and_Remove( sc.getUsername_cliente());
                        }
                        
                        remover_user_do_server(sc);
                        
                    }
                    /* avisa todos os utilizadores online para atualizar lista user e remove a thread do user que saiu */
                    //server_frame.getServer_multiCast().sendMulticast(Message.SAIR_GRUPO, msg.getAux(), msg.getEnviou(), msg.getConteudo_msm(),msg.getRecebeu());
                    Atualiza_informacaoAll_exit_user(Mensagem.SAIR, nome_cliente_saiu);
//                    remover_Thread(ID);
                    break;
                case Mensagem.REMOVER_GRUPO:
                    sc = procura_cliente_ID(mensagem.getId_comunica());
                    if (sc != null) {
                        Grupo remove_grupo = procura_grupoNome(mensagem.getEnviou()); // procura grupo
                        if (remove_grupo != null) { //verifica se grupo existe
                           
                            if (remove_grupo.getUtilizadores_online().size() == 1) { //Verifica se o utilizador existe no grupo e é o ultimo elemento
                                //Remove o grupo e atualiza a lista de grupos, e envia mensagem a alertar os utilizadores do grupo
                                String nome_grupo_to_remove = remove_grupo.getNome_grupo();
                                remove_grupo(remove_grupo);
                                
                                Atualiza_informacaoAll_remove_grupo(Mensagem.ATUALIZA_GRUPOS_REMOVIDO, nome_grupo_to_remove);
                                sc.getTm().response_to_cliente(new Mensagem(Mensagem.ATUALIZA_USER_EXIT_GROUP, nome_grupo_to_remove, sc.getUsername_cliente(), sc.getUsername_cliente()));
                            }
                        }
                        
                    }
                    break;
                case Mensagem.SAIR_GRUPO:
                    sc = procura_cliente_ID(mensagem.getId_comunica());
                    if(sc != null){
                         Grupo user_exit_group = procura_grupoNome(mensagem.getEnviou());
                         if(user_exit_group != null){
                             String nome_grupo_to_remove = user_exit_group.getNome_grupo();
                             if(user_exit_group.getUtilizadores_online().size() == 1){
                                  remove_grupo(user_exit_group);
                                  Atualiza_informacaoAll_remove_grupo(Mensagem.ATUALIZA_GRUPOS_REMOVIDO, nome_grupo_to_remove);
                             }else{
                                 remove_user_group(user_exit_group, sc.getUsername_cliente());
                                 atualiza_user_exit_group(Mensagem.ATUALIZA_USER_EXIT_GROUP, user_exit_group, sc.getNome_cliente());
                                 sc.getTm().response_to_cliente(new Mensagem(Mensagem.ATUALIZA_USER_EXIT_GROUP, nome_grupo_to_remove, sc.getUsername_cliente(), sc.getUsername_cliente()));
                             }
                         }
                    }
                    break;
                
                case Mensagem.ENVIA_FILE:
                    // recebe pedido de envio de ficheiro
                    sc = procura_cliente_ID(mensagem.getId_comunica()); //quem enviou pedido para enviar ficheiro
                    if (sc != null) {
                        if(mensagem.getTipo_msm_file() == Mensagem.MENSAGEM_CHAT_PRIVATE){
                            Server_Cliente quem_recebe_file = procura_cliente_nome(mensagem.getRecebeu()); // quem vai receber o ficheiro
                            quem_recebe_file.getTm().response_to_cliente(new Mensagem(Mensagem.ENVIA_FILE, sc.getId(),sc.getNome_cliente(), quem_recebe_file.getUsername_cliente(), mensagem.getConteudo_msm(),mensagem.getTipo_msm_file(),mensagem.getFile()));
                        }else if(mensagem.getTipo_msm_file() == Mensagem.GRUPO_MSM){
                            Grupo procura_grupo = procura_grupoNomeUser(mensagem.getRecebeu());
                            if (procura_grupo != null) { //verifica se o utilizador existe naquele grupo  
                                send_file_to_all_member_group(procura_grupo, sc.getNome_cliente(), mensagem);
                            }
                        }
                    }
                    break;
 
                default:
                    System.out.println("Opção Inválida");
            }
        } catch (RemoteException ex) {
            System.err.println("Exception SocketClient run()"+ex);
        }
        
    }

    /*
    *COMUNICA COM USER
    **/
    
    private User retorna_user_dados(String username){
        
        Iterator<User> itr_ut = user.iterator();

        while (itr_ut.hasNext()) {
            User ut_u = itr_ut.next();
            if (ut_u.getUsername_cliente().equals( username)) {
                return ut_u;
            }
        }
        //nao existe user igual
        return null;
    }
    
    private void envia_novo_login_other_users(int id, String nome) {

        Iterator<Server_Cliente> itr_ut = clientes.iterator();

        while (itr_ut.hasNext()) {
            Server_Cliente ut_u = itr_ut.next();
            if (ut_u.getId() != id && !ut_u.getUsername_cliente().isEmpty()) {
                try {
                    ut_u.getTm().response_to_cliente( new Mensagem (Mensagem.ATUALIZA_USERS_LOGIN, nome));
                } catch (RemoteException ex) {
                    Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    private ArrayList nome_user_online(int id_user){
        ArrayList<String> user_on = new ArrayList<>();
        Iterator<Server_Cliente> itr_ut = clientes.iterator();

        while (itr_ut.hasNext()) {
            Server_Cliente ut_u = itr_ut.next();
            if (ut_u.getId() != id_user && ut_u.getUsername_cliente() != null) {
                user_on.add(ut_u.getNome_cliente());
            }
        }
        //nao existe igual
        return user_on;
    }
    private boolean verifica_existe_user_name(String user_name) {
        Iterator<User> itr_ut = user.iterator();

        while (itr_ut.hasNext()) {
            User ut_u = itr_ut.next();
            if (ut_u.getUsername_cliente() != null && ut_u.getUsername_cliente().equals(user_name)) {
                return false; //ja existe este username
            }
        }
        //nao existe igual
        return true;
    }

    private Server_Cliente procura_cliente_ID(int id_cliente) {
        Iterator<Server_Cliente> itr_ut = clientes.iterator();

        while (itr_ut.hasNext()) {
            Server_Cliente ut_u = itr_ut.next();
            if (ut_u.getId() == id_cliente) {
                return ut_u;
            }
        }
        return null;
    }
    
    private Server_Cliente procura_cliente_username(String username) {
       Iterator<Server_Cliente> itr_ut = clientes.iterator();

        while (itr_ut.hasNext()) {
            Server_Cliente ut_u = itr_ut.next();
            if (ut_u.getUsername_cliente().equals(username)) {
                return ut_u;
            }
        }
        return null;
    }

    private Server_Cliente procura_cliente_nome(String recebe_msm) {
        Iterator<Server_Cliente> itr_ut = clientes.iterator();

        while (itr_ut.hasNext()) {
            Server_Cliente ut_u = itr_ut.next();
            if (ut_u.getNome_cliente().equals(recebe_msm)) {
                return ut_u;
            }
        }
        return null;
    }
    
    //Verifica se o grupo que recebe é válido
    private boolean Verifica_grupo(String nome_grupo, Server_Cliente criador, String data) {
        Boolean bool = false;
        if (nome_grupo.trim().length() > 4) {

            Iterator<Grupo> itr = grupos.iterator();
            bool = true;

            while (itr.hasNext() && bool) {
                Grupo ut = itr.next();
                if (ut.getNome_grupo().equals(nome_grupo)) {
                    bool = false;
                }
            }
        }
        if (bool) {
            grupos.add(new Grupo(criador.getId(), nome_grupo, criador.getUsername_cliente(), data));
        }

        return bool;
    }

    private void Atualiza_InformationAll(int ATUALIZA_GRUPOS, String nome_cliente, String nome_grupo) {
        Iterator<Server_Cliente> itr_ut = clientes.iterator();

        while (itr_ut.hasNext()) {
            Server_Cliente ut_u = itr_ut.next();
            if (!ut_u.getUsername_cliente().isEmpty()) {
                try {
                    ut_u.getTm().response_to_cliente(new Mensagem(ATUALIZA_GRUPOS, nome_grupo));
                } catch (RemoteException ex) {
                    Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    private Grupo procura_grupoNome(String nome_grupo) {
        Iterator<Grupo> itr_ut = grupos.iterator();
        
        while (itr_ut.hasNext()) {
            Grupo ut_u = itr_ut.next();
            if (ut_u.getNome_grupo().equals(nome_grupo)) {
                return ut_u;
            }
        }
        return null;
    }

    private void Verifica_SeUser_ExisteOtherGroups_and_Remove(String username_cliente) {
        Iterator<Grupo> itr_ut = grupos.iterator();

        while (itr_ut.hasNext()) {
            Grupo ut_u = itr_ut.next();

            ArrayList<User_group> array_grupo = ut_u.getUtilizadores_online();
            ArrayList<User_group> copy_grupo = new ArrayList<>();//resolver concorrencia se arrays
            copy_grupo.addAll(array_grupo);
            
            
            Iterator<User_group> user_array = copy_grupo.iterator();

            while (user_array.hasNext()) {
                User_group user_grupo = user_array.next();

                if (user_grupo.getUser_name().equals(username_cliente)) {

                    array_grupo.remove(user_grupo);
                }

            }

        }
    }

    private Grupo procura_grupoNomeUser(String recebeu) {
        Iterator<Grupo> itr_ut = grupos.iterator();

        while (itr_ut.hasNext()) {
            Grupo ut_u = itr_ut.next();

            if (ut_u.getNome_grupo().equals(recebeu)) {
                return ut_u;
            }
        }

        return null;
    }

    private void send_to_all_member_group(Grupo procura_grupo, String enviou, String conteudo_msm) {
        Iterator<User_group> itr_ut = procura_grupo.getUtilizadores_online().iterator();
        Server_Cliente cliente;
        while (itr_ut.hasNext()) {
            User_group ut_u = itr_ut.next();
            cliente = procura_cliente_username(ut_u.getUser_name());
            try {
                cliente.getTm().response_to_cliente(new Mensagem(Mensagem.GRUPO_MSM, enviou,procura_grupo.getNome_grupo(),conteudo_msm));
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void remover_user_do_server(Server_Cliente user_remove) {
        try {
            clientes.remove(user_remove);
        } catch (Exception e) {
        }
    }

    private void Atualiza_informacaoAll_exit_user(int SAIR, String nome_cliente_saiu) {
        Iterator<Server_Cliente> itr_ut = clientes.iterator();

        while (itr_ut.hasNext()) {
            Server_Cliente ut_u = itr_ut.next();

            try {
                ut_u.getTm().response_to_cliente(new Mensagem(SAIR, nome_cliente_saiu));
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void remove_grupo(Grupo procura_grupo) {

        this.grupos.remove(procura_grupo);

    }

    private void Atualiza_informacaoAll_remove_grupo(int ATUALIZA_GRUPOS_REMOVIDO, String nome_grupo_to_remove) {
        Iterator<Server_Cliente> itr_ut = clientes.iterator();

        while (itr_ut.hasNext()) {
            Server_Cliente ut_u = itr_ut.next();

            try {
                ut_u.getTm().response_to_cliente(new Mensagem(ATUALIZA_GRUPOS_REMOVIDO, nome_grupo_to_remove));
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void remove_user_group(Grupo user_exit_group, String aux) {
        
        ArrayList<User_group> ug = user_exit_group.getUtilizadores_online();
        Iterator<User_group> itr_ut = ug.iterator();
        boolean bool = true;
        while (itr_ut.hasNext() && bool){
            User_group ut_u = itr_ut.next();
            if(ut_u.getUser_name().equals(aux)){
                ug.remove(ut_u);
                bool = false;
            }
        }
    }

    private void atualiza_user_exit_group(int ATUALIZA_USER_EXIT_GROUP, Grupo user_exit_group, String nome_cliente) {
        Iterator<User_group> itr_ut = user_exit_group.getUtilizadores_online().iterator();

        while (itr_ut.hasNext()) {
            User_group ut_u = itr_ut.next();
            Server_Cliente username = procura_cliente_username(ut_u.getUser_name());
            
            try {
                username.getTm().response_to_cliente(new Mensagem(ATUALIZA_USER_EXIT_GROUP, user_exit_group.getNome_grupo(), nome_cliente, "Saiu do Grupo"));
            } catch (RemoteException ex) {
                Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void send_file_to_all_member_group(Grupo procura_grupo, String quem_enviou, Mensagem mensagem) {
        Iterator<User_group> itr_ut = procura_grupo.getUtilizadores_online().iterator();
        Server_Cliente cliente;
        while (itr_ut.hasNext()) {
            User_group ut_u = itr_ut.next();
            cliente = procura_cliente_username(ut_u.getUser_name());
            if( !cliente.getNome_cliente().equals(quem_enviou)){
                try {
                    cliente.getTm().response_to_cliente(new Mensagem(Mensagem.ENVIA_FILE, 0,quem_enviou, cliente.getUsername_cliente(), mensagem.getConteudo_msm(),mensagem.getTipo_msm_file(),mensagem.getFile()));
                } catch (RemoteException ex) {
                    Logger.getLogger(ServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
    

}
