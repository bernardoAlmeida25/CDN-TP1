
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author pedro
 */
public class Chat_cliente implements Server_to_cliente {

    //comunica com server
    protected Cliente_to_server s_comunica;

    //cliente
    protected int id_cliente;
    protected String nome_cliente;
    protected String user_name;
    //cliente

    //forms
    protected Menu_inicial menu_inicial;
    protected Utilizador_menu menu_utilizador;
    protected Registar novo_registo;
    //forms

    private final String url_ip = "localhost";
    private final String door = "2001";

    public Chat_cliente() {
        this.menu_inicial = null;
        this.menu_utilizador = null;
        this.novo_registo = null;
        this.id_cliente = 0;
        this.nome_cliente = "";
        this.user_name = "";
        start_user();
    }

    private void start_user() {
        try {
            System.out.println("Start USER");
            UnicastRemoteObject.exportObject(this);

            String serverName = "rmi://" + url_ip + ":" + door + "/ChatServer";

            System.out.println("Looking up Chat at: " + serverName);

            try {
                s_comunica = (Cliente_to_server) Naming.lookup(serverName);

            } catch (MalformedURLException | NotBoundException | RemoteException e) {
                System.out.println("" + e);
            }

            s_comunica.init_client(this);
            System.out.println("We have been registered!");

        } catch (NullPointerException | RemoteException re) {
            System.out.println("" + re);
            JOptionPane.showMessageDialog(null,
                    "Chat Server Offline",
                    "Chat Offline",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Start_Menu() {
        this.menu_inicial = new Menu_inicial();
        this.menu_utilizador = new Utilizador_menu();
        this.menu_inicial.setC_cliente(this);
        this.menu_inicial.setVisible(true);
        this.menu_inicial.pack();
        this.menu_inicial.setLocationRelativeTo(null);
    }

    /*
    **
    ** METODOS DE RESPOSTA DO SERVER
     */
    @Override
    public synchronized void response_init_client(int d) throws RemoteException {
        this.id_cliente = d;
        Start_Menu();
    }

    @Override
    public synchronized void response_to_cliente(Mensagem mensagem) throws RemoteException {
        int tipo_msm = mensagem.getTipo_msm();
        Server_Cliente sc;
        try {
            switch (tipo_msm) {
                case Mensagem.LOGIN:
                    if (mensagem.isBool()) {
                        System.err.println("Login com Sucesso!!");

                        this.id_cliente = mensagem.getId_comunica();
                        this.user_name = mensagem.getEnviou();
                        this.nome_cliente = mensagem.getRecebeu();

                        this.menu_inicial.setVisible(false);
                        this.menu_utilizador.setVisible(true);
                        this.menu_utilizador.pack();
                        this.menu_utilizador.setLocationRelativeTo(null);
                        this.menu_utilizador.chat_menu(this, nome_cliente);
                        atualiza_chat_menu(mensagem.getArray_utilizadores(), mensagem.getGrupos_criados());
//            this.menu_inicial.dispose();
                    } else {

                        System.err.println("Login incorreto");
//            JOptionPane.showMessageDialog(this.menu_inicial,
//                    "Username ou palavra passe estao incorretos",
//                    "Erro de Login",
//                    JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case Mensagem.REGISTO:
                    if (mensagem.isBool()) {
                        System.err.println("Registo com Sucesso!!");
                        this.id_cliente = mensagem.getId_comunica();

                        this.novo_registo.setVisible(false);

                        this.menu_inicial.setVisible(true);
                        this.menu_inicial.pack();
                        this.menu_inicial.setLocationRelativeTo(null);

                    } else {

                        System.err.println("Registo falhou!");
//            JOptionPane.showMessageDialog(novo_registo, "Nome do grupo vazio");
                    }
                    break;

                case Mensagem.ATUALIZA_USERS_LOGIN:
                    menu_utilizador.L_utilizadores.add(mensagem.getUser_login());
                    break;

                case Mensagem.ATUALIZA_GRUPOS:
                    /* atualiza tabela com os grupos criados */
                    boolean exists = false;
                    for (int i = 0; i < menu_utilizador.L_grupos.countItems(); i++) {
                        if (menu_utilizador.L_grupos.getItem(i).equals(mensagem.getUser_login())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        menu_utilizador.L_grupos.add(mensagem.getUser_login());
                    }
                    break;

                case Mensagem.GRUPO:
                    if (mensagem.isBool()) {
                        System.err.println("grupo com Sucesso!");
                    } else {
                        //por modal a dizer erro ao criar
                        System.err.println("Registo falhou!");
                    }
                    break;

                case Mensagem.MENSAGEM_CHAT_PRIVATE:
                    /* gere forma de mostrar mensagens */
                    String gere_msm = "";

                    if (menu_utilizador.L_utilizadores.getSelectedIndex() != -1) {
                        if (menu_utilizador.L_utilizadores.getItem(menu_utilizador.L_utilizadores.getSelectedIndex()).equals(mensagem.getRecebeu())) {
                            gere_msm = "[" + mensagem.getRecebeu() + " > Me] : " + mensagem.getConteudo_msm() + "\n";
                        
                        } else if (mensagem.getEnviou().equals(nome_cliente)) {
                            gere_msm = "[" + mensagem.getEnviou()+ " >" +  mensagem.getRecebeu() + " ] :" + mensagem.getConteudo_msm() + "\n";
                        
                        } else {
                            gere_msm = "[" + mensagem.getRecebeu() + " > Me] : " + mensagem.getConteudo_msm() + "\n";
                        }
                    } else {
                        gere_msm = "[" + mensagem.getEnviou() + " > Me] : " + mensagem.getConteudo_msm() + "\n";
                    }
                    menu_utilizador.TA_Mensagem.append(gere_msm);

//                    mensagens.add(new Mensagens(msg.getEnviou(), msg.getRecebeu(), msm_padeiro));
//                    ficheiros.Guarda_Objeto_Ficheiro(mensagens, nome_ficheiro);
                    break;

                case Mensagem.GRUPO_MSM:
                    try {
                        if (menu_utilizador.L_grupos.getSelectedItem().equals(mensagem.getRecebeu())) {
                            if (nome_cliente.equals(mensagem.getEnviou())) {
                                gere_msm = "[ Eu ] : " + mensagem.getConteudo_msm() + "\n";
                        
                        } else {
                            gere_msm = "[ " + mensagem.getEnviou()+ " ] :" + mensagem.getConteudo_msm() + "\n";
                        
                        }
                            menu_utilizador.TA_Mensagem.append(gere_msm + "\n");
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;

                case Mensagem.SAIR:
                    menu_utilizador.L_utilizadores.remove(mensagem.getUser_login());
                    menu_utilizador.TA_Mensagem.append("--> " + mensagem.getUser_login() + " saiu do Chat\n");
                    break;

                case Mensagem.ATUALIZA_GRUPOS_REMOVIDO:
                    menu_utilizador.L_grupos.remove(mensagem.getUser_login());
                    menu_utilizador.TA_Mensagem.append("--> " + mensagem.getUser_login() + " Grupo foi removido\n");
                    menu_utilizador.Lb_remover.setVisible(false);
                    menu_utilizador.Lb_sair.setVisible(false);
                    break;
                case Mensagem.ATUALIZA_USER_EXIT_GROUP:
                    
                    try {
                        if (menu_utilizador.L_grupos.getSelectedItem().equals(mensagem.getEnviou())) {
                            if(mensagem.getConteudo_msm() != null){
                                if( mensagem.getConteudo_msm().equals(user_name)){
                                    menu_utilizador.TA_Mensagem.append("Saida do grupo com SUCESSO" + "\n");
                                    menu_utilizador.L_grupos.deselect(menu_utilizador.L_grupos.getSelectedIndex());
                                    menu_utilizador.Lb_remover.setVisible(false);
                                    menu_utilizador.Lb_sair.setVisible(false);
                                }
                            }
                            menu_utilizador.TA_Mensagem.append(mensagem.getRecebeu()+ " Saiu do Grupo" + "\n");
                        }else{
                            menu_utilizador.Lb_remover.setVisible(false);
                            menu_utilizador.Lb_sair.setVisible(false);
                        }
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    break;

                default:
                    System.out.println("Opção Inválida USER");
            }
        } catch (Exception ex) {
            System.err.println("Exception SocketClient run()");
        }
    }

//    @Override
//    public synchronized void response_registo_client(Boolean d, int id) throws RemoteException {
//        if (d) {
//            System.err.println("Registo com Sucesso!!");
//            this.id_cliente = id;
//
//            this.novo_registo = null;
//            this.menu_inicial.setVisible(true);
//            this.menu_inicial.pack();
//            this.menu_inicial.setLocationRelativeTo(null);
//
//        } else {
//
//            System.err.println("Registo falhou!");
////            JOptionPane.showMessageDialog(novo_registo,
////                    "Registo Falhou",
////                    "Erro de Registo",
////                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    @Override
//    public synchronized void response_login_client(boolean login, int id, String username_cliente, String nome_cliente, ArrayList nome_user_online, ArrayList groups) throws RemoteException {
//        if (login) {
//            System.err.println("Login com Sucesso!!");
//
//            this.id_cliente = id;
//            this.user_name = username_cliente;
//            this.nome_cliente = nome_cliente;
//
////            this.menu_inicial.dispose();
//            this.menu_utilizador.setVisible(true);
//            this.menu_utilizador.pack();
//            this.menu_utilizador.setLocationRelativeTo(null);
//            this.menu_utilizador.chat_menu(this, nome_cliente);
//            atualiza_chat_menu(nome_user_online, groups);
////            this.menu_inicial.dispose();
//        } else {
//
//            System.err.println("Login incorreto");
////            JOptionPane.showMessageDialog(this.menu_inicial,
////                    "Username ou palavra passe estao incorretos",
////                    "Erro de Login",
////                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    @Override
//    public synchronized void response_envia_mensagem(String enviou, String conteudo_msm) throws RemoteException {
//        /* gere forma de mostrar mensagens */
//        System.err.println(conteudo_msm);
////        String msm_padeiro;
////        if (msg.getAux().equals(frame_MenuInicial.getUsername_utilizador())) {
////            if (frame_MenuInicial.getMenu_utilizador().L_utilizadores.getSelectedIndex() != -1) {
////                if (frame_MenuInicial.getMenu_utilizador().L_utilizadores.getItem(frame_MenuInicial.getMenu_utilizador().L_utilizadores.getSelectedIndex()).equals(msg.getEnviou())) {
////                    msm_padeiro = "[" + msg.getEnviou() + " > Me] : " + msg.getConteudo_msm() + "\n";
////
//        menu_utilizador.TA_Mensagem.append(conteudo_msm);
////                } else {
////                    msm_padeiro = "[" + msg.getEnviou() + " > Me] : " + msg.getConteudo_msm() + "\n";
////
////                }
////            } else {
////                msm_padeiro = "[" + msg.getEnviou() + " > Me] : " + msg.getConteudo_msm() + "\n";
////
////            }
////        } else {
////            msm_padeiro = "[" + msg.getEnviou() + " > " + msg.getRecebeu() + "] : " + msg.getConteudo_msm() + "\n";
////            frame_MenuInicial.getMenu_utilizador().TA_Mensagem.append(msm_padeiro);
////        }
////        mensagens.add(new Mensagens(msg.getEnviou(), msg.getRecebeu(), msm_padeiro));
////        ficheiros.Guarda_Objeto_Ficheiro(mensagens, nome_ficheiro);
//    }
//
//    @Override
//    public synchronized void response_novo_login(int id, int id0, String nome) throws RemoteException {
//        menu_utilizador.L_utilizadores.add(nome);
//    }

    /*
    **
    ** METODOS DE RESPOSTA DO SERVER
     */
    public void atualiza_chat_menu(ArrayList user_on, ArrayList groups) {
        Iterator<String> itr_ut = user_on.iterator();

        while (itr_ut.hasNext()) {
            String ut_u = itr_ut.next();

            menu_utilizador.L_utilizadores.add(ut_u);

        }

        Iterator<String> itr_gr = groups.iterator();

        while (itr_gr.hasNext()) {
            String ut_g = itr_gr.next();

            menu_utilizador.L_grupos.add(ut_g);

        }
    }

    public Cliente_to_server getS_comunica() {
        return s_comunica;
    }

    public void setS_comunica(Cliente_to_server s_comunica) {
        this.s_comunica = s_comunica;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Utilizador_menu getMenu_utilizador() {
        return menu_utilizador;
    }

    public void setMenu_utilizador(Utilizador_menu menu_utilizador) {
        this.menu_utilizador = menu_utilizador;
    }

    public Registar getNovo_registo() {
        return novo_registo;
    }

    public void setNovo_registo(Registar novo_registo) {
        this.novo_registo = novo_registo;
    }

    public Menu_inicial getMenu_inicial() {
        return menu_inicial;
    }

    public void setMenu_inicial(Menu_inicial menu_inicial) {
        this.menu_inicial = menu_inicial;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "Chat_cliente{" + "s_comunica=" + s_comunica + ", start=" + menu_inicial + ", id_cliente=" + id_cliente + '}';
    }

}
