
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
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
                    } else {

                        System.err.println("Login incorreto");
                        this.menu_inicial.Lb_error_login.setVisible(true);
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
                        this.novo_registo.Lb_error.setVisible(true);
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
                        if (menu_utilizador.L_utilizadores.getItem(menu_utilizador.L_utilizadores.getSelectedIndex()).equals(mensagem.getRecebeu()) ) {
                            gere_msm = "[" + mensagem.getRecebeu() + " > Me] : " + mensagem.getConteudo_msm() + "\n";

                        } else if (  mensagem.getEnviou().equals(nome_cliente)) {
                            gere_msm = "[" + mensagem.getEnviou() + " >" +  mensagem.getRecebeu() + " ] : " + mensagem.getConteudo_msm() + "\n";

                        } 
//                        else {
//                            gere_msm = "[" + mensagem.getEnviou() + " > aMe] : " + mensagem.getConteudo_msm() + "\n";
//                        }
                    } else if (mensagem.getRecebeu().equals("GERAL")) {
                        gere_msm = "[" + mensagem.getEnviou() + " > Enviou-lhe uma mensagem ] : " + mensagem.getConteudo_msm() + "\n";
                    }
                    menu_utilizador.TA_Mensagem.append("\n"+gere_msm);

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
                    menu_utilizador.TA_Mensagem.append("\n--> " + mensagem.getUser_login() + " saiu do Chat\n");
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
                case Mensagem.ENVIA_FILE:
                    /* pergunta se quer aceitar receber um ficheiro e onde guardar*/
                    if (JOptionPane.showConfirmDialog(menu_utilizador, ("Aceita o ficheiro '" + mensagem.getConteudo_msm()+ "' de " + mensagem.getEnviou() + " ?")) == 0) {

                        JFileChooser jf = new JFileChooser();
                        jf.setSelectedFile(new File(mensagem.getConteudo_msm()));
                        int returnVal = jf.showSaveDialog(menu_utilizador);

                        String saveTo = jf.getSelectedFile().getPath();
                        if (saveTo != null && returnVal == JFileChooser.APPROVE_OPTION) {
                            try (FileOutputStream fos = new FileOutputStream(saveTo)) {
                                fos.write(mensagem.getFile());
                                fos.close();
                            }
                            menu_utilizador.TA_Mensagem.append("\n "+mensagem.getConteudo_msm()+ " guardado com sucesso" + "\n");
                        } else {
                            menu_utilizador.TA_Mensagem.append("\n Erro ao guardar o Ficheiro "+mensagem.getConteudo_msm() + "\n");
                        }
                    } else {
                        menu_utilizador.TA_Mensagem.append("\n Nao aceitas-te o ficheiro de "+mensagem.getEnviou() + "\n");
                    }
                    break;
                
                case Mensagem.ATUALAZIAR_GRUPOS:
                    atualiza_user_group( mensagem.getGrupo());
                    break;
                    
                case Mensagem.UTILIZADORES_REGISTADOS:
                    Iterator<String> itr_ut = mensagem.getArray_utilizadores().iterator();
                    while (itr_ut.hasNext()) {
                        String ut_u = itr_ut.next();
                        menu_utilizador.L_utilizadores.add(ut_u);
                    }
                    
                    break;
                    
                case Mensagem.ATUALIZAR_MENSAGENS_OFFLINE:

                    Iterator<Save_mensagens> itr_ut_m = mensagem.getMensagens_offline().iterator();
                    while (itr_ut_m.hasNext()) {
                        Save_mensagens ut_u_m = itr_ut_m.next();
                        String gere_msm_offline = ut_u_m.getData()+" | [" + ut_u_m.getUser_enviou() + " > Me] : " + ut_u_m.getConteudo_msm()+ "\n";
                        menu_utilizador.TA_Mensagem.append("\n"+gere_msm_offline);
                    }
                    break;
                    
                case Mensagem.MENSAGEM_GERAL:
                    /* gere forma de mostrar mensagens */
                    String gere_msm_geral = "";

                    gere_msm_geral = "[" + mensagem.getEnviou() + " > Enviou-lhe uma mensagem ] : " + mensagem.getConteudo_msm() + "\n";

                    menu_utilizador.TA_Mensagem.append("\n"+gere_msm_geral);
                    break;

                default:
                    System.out.println("Opção Inválida USER");
            }
        } catch (Exception ex) {
            System.err.println("Exception SocketClient run()");
        }
    }

    /*
    **
    ** METODOS DE RESPOSTA DO SERVER
     */
    public void atualiza_chat_menu(ArrayList user_on, ArrayList groups) {
        

        if(user_on != null){
            Iterator<String> itr_ut = user_on.iterator();
            while (itr_ut.hasNext()) {
                String ut_u = itr_ut.next();

                menu_utilizador.L_utilizadores.add(ut_u);

            }
        }

        if(groups != null){
            Iterator<Grupo> itr_gr = groups.iterator();

            while (itr_gr.hasNext()) {
                Grupo ut_g = itr_gr.next();

                menu_utilizador.L_grupos.add(ut_g.getNome_grupo());

            }
        }
    }
    
    private void atualiza_user_group(Grupo grupo_criado) {
        if(grupo_criado != null){
            Iterator<User_group> itr_gr = grupo_criado.getUtilizadores_online().iterator();

            while (itr_gr.hasNext()) {
                User_group ut_g = itr_gr.next();
                if(!ut_g.getUser_name().equals(user_name)){
                    menu_utilizador.L_utilizadores_grupo.add(ut_g.getNome_user());
                }
            }
            menu_utilizador.jLabel_chefe_grupo.setText(grupo_criado.getNome_criador());
            menu_utilizador.jLabel7_grupo_name.setText(grupo_criado.getNome_grupo());
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
