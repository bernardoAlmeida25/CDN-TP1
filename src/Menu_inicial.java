/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Menu_inicial extends javax.swing.JFrame {

    protected Chat_cliente c_cliente;

    public Menu_inicial() {
        initComponents();
    }
    public Menu_inicial(Chat_cliente c) {
        this.c_cliente = c;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Lb_Estado = new javax.swing.JLabel();
        jLabel_close_window = new javax.swing.JLabel();
        jLabel_minimizar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel_registo = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        username_login = new javax.swing.JTextField();
        username1 = new javax.swing.JLabel();
        Efetuar_login = new javax.swing.JButton();
        password_login = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));

        Lb_Estado.setFont(new java.awt.Font("Segoe UI Symbol", 1, 24)); // NOI18N
        Lb_Estado.setForeground(new java.awt.Color(255, 255, 255));
        Lb_Estado.setText("Chat");

        jLabel_close_window.setFont(new java.awt.Font("Segoe UI Symbol", 1, 36)); // NOI18N
        jLabel_close_window.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_close_window.setText("x");
        jLabel_close_window.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_close_window.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_close_windowMouseClicked(evt);
            }
        });

        jLabel_minimizar.setFont(new java.awt.Font("Segoe UI Symbol", 1, 36)); // NOI18N
        jLabel_minimizar.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_minimizar.setText("-");
        jLabel_minimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_minimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_minimizarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Lb_Estado)
                .addGap(160, 160, 160)
                .addComponent(jLabel_minimizar)
                .addGap(18, 18, 18)
                .addComponent(jLabel_close_window)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_close_window)
                    .addComponent(jLabel_minimizar)
                    .addComponent(Lb_Estado))
                .addGap(2, 2, 2))
        );

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel3.setBackground(new java.awt.Color(102, 0, 153));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/logo3.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Nirmala UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 255));
        jLabel2.setText("Chat");

        jLabel_registo.setFont(new java.awt.Font("Malgun Gothic", 0, 12)); // NOI18N
        jLabel_registo.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_registo.setText("Efetuar registo");
        jLabel_registo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel_registo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_registoMouseClicked(evt);
            }
        });

        username.setBackground(new java.awt.Color(255, 255, 255));
        username.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setText("Username");

        username_login.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        username_login.setForeground(new java.awt.Color(51, 51, 51));
        username_login.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        username_login.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        username_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                username_loginActionPerformed(evt);
            }
        });
        username_login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                username_loginKeyPressed(evt);
            }
        });

        username1.setBackground(new java.awt.Color(255, 255, 255));
        username1.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        username1.setForeground(new java.awt.Color(255, 255, 255));
        username1.setText("Password");

        Efetuar_login.setBackground(new java.awt.Color(51, 102, 255));
        Efetuar_login.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Efetuar_login.setForeground(new java.awt.Color(255, 255, 255));
        Efetuar_login.setText("Login");
        Efetuar_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Efetuar_loginActionPerformed(evt);
            }
        });
        Efetuar_login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Efetuar_loginKeyPressed(evt);
            }
        });

        password_login.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        password_login.setForeground(new java.awt.Color(51, 51, 51));
        password_login.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        password_login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                password_loginKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(username)
                    .addComponent(username_login, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(username1)
                    .addComponent(password_login))
                .addGap(80, 80, 80))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_registo)
                            .addComponent(Efetuar_login)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addComponent(username)
                .addGap(18, 18, 18)
                .addComponent(username_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(username1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(password_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(Efetuar_login)
                .addGap(48, 48, 48)
                .addComponent(jLabel_registo)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
 * Ao carregar no X envia mensagem ao server e fecha a window
     */
    private void jLabel_close_windowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_close_windowMouseClicked
        try {
            c_cliente.getS_comunica().comunica_com_server(new Mensagem(Mensagem.SAIR,c_cliente.getId_cliente(),true ));
            c_cliente.menu_utilizador.dispose();
            c_cliente.menu_inicial.dispose();
        } catch (RemoteException ex) {
        }
        System.exit(0);
    }//GEN-LAST:event_jLabel_close_windowMouseClicked

    /*
 * Ao carregar no - minimiza mensagem
     */
    private void jLabel_minimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_minimizarMouseClicked

        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel_minimizarMouseClicked


    private void username_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_username_loginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_username_loginActionPerformed

    /*
 * Ao carregar no botao de Login inicialmente faz uma pequena verificaçao se as variaveis estao vazias,
 * para nao spamar o server com informaçao incorreta.
 * O server irá verificar se os dados introduzidos estao corretos, caso esteja,
 * efetua Login, caso contrario nao deixa efetuar o login
     */
    private void Efetuar_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Efetuar_loginActionPerformed
        String username_log = username_login.getText();
        String password = password_login.getText();
        int id_cliente = c_cliente.getId_cliente();

        if (!username_log.isEmpty() && !password.isEmpty()) {

            try {
                c_cliente.getS_comunica().comunica_com_server(new Mensagem(Mensagem.LOGIN, id_cliente, username_log, password, "Login"));
            } catch (RemoteException ex) {
                Logger.getLogger(Menu_inicial.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, " Login Incorreto !!!");
        }

    }//GEN-LAST:event_Efetuar_loginActionPerformed

    /*
    * Ao carregar para efetuar o registo, abre a janela para preencher os campos de novo registo e fecha a atual
     */
    private void jLabel_registoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_registoMouseClicked

        this.dispose();
        this.c_cliente.setNovo_registo(new Registar());
        this.c_cliente.getNovo_registo().setAll(c_cliente);
        this.c_cliente.getNovo_registo().setVisible(true);
        this.c_cliente.getNovo_registo().pack();
        this.c_cliente.getNovo_registo().setLocationRelativeTo(null);
        


    }//GEN-LAST:event_jLabel_registoMouseClicked

    private void username_loginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_username_loginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ActionEvent actionevent = null;
            this.Efetuar_loginActionPerformed(actionevent);
        }
    }//GEN-LAST:event_username_loginKeyPressed

    private void password_loginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_password_loginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ActionEvent actionevent = null;
            this.Efetuar_loginActionPerformed(actionevent);
        }
    }//GEN-LAST:event_password_loginKeyPressed

    private void Efetuar_loginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Efetuar_loginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ActionEvent actionevent = null;
            this.Efetuar_loginActionPerformed(actionevent);
        }

    }//GEN-LAST:event_Efetuar_loginKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Efetuar_login;
    private javax.swing.JLabel Lb_Estado;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_close_window;
    private javax.swing.JLabel jLabel_minimizar;
    private javax.swing.JLabel jLabel_registo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField password_login;
    private javax.swing.JLabel username;
    private javax.swing.JLabel username1;
    private javax.swing.JTextField username_login;
    // End of variables declaration//GEN-END:variables

    public Chat_cliente getC_cliente() {
        return c_cliente;
    }

    public void setC_cliente(Chat_cliente c_cliente) {
        this.c_cliente = c_cliente;
    }

}