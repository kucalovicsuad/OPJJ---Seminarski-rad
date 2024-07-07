/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Login;

import User.UserController;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Dashboard.MainWindow;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 *
 * @author Suad's Laptop
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        escapeKey();
    }
    
    private void escapeKey() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBtn.doClick();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "disposeForm");
        actionMap.put("disposeForm", escapeAction);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        usernameInput = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        passwordInput = new javax.swing.JPasswordField();
        jPanel5 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        passForgotten = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Prijava korisnika");
        setMinimumSize(new java.awt.Dimension(400, 400));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel7.setMaximumSize(new java.awt.Dimension(400, 380));
        jPanel7.setMinimumSize(new java.awt.Dimension(400, 380));
        jPanel7.setPreferredSize(new java.awt.Dimension(400, 380));
        jPanel7.setLayout(new java.awt.GridLayout(2, 0));

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 0, 16)); // NOI18N
        jLabel1.setText("Molimo prijavite se!");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jPanel7.add(jPanel1);

        jPanel2.setLayout(new java.awt.GridLayout(4, 1));

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel5.setText("Korisničko ime:");
        jLabel5.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel5.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel5.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 16);
        jPanel3.add(jLabel5, gridBagConstraints);

        usernameInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        usernameInput.setMaximumSize(new java.awt.Dimension(150, 28));
        usernameInput.setMinimumSize(new java.awt.Dimension(150, 28));
        usernameInput.setPreferredSize(new java.awt.Dimension(150, 28));
        usernameInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameInputKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        jPanel3.add(usernameInput, gridBagConstraints);

        jPanel2.add(jPanel3);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel6.setText("Lozinka:");
        jLabel6.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel6.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel6.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 16);
        jPanel4.add(jLabel6, gridBagConstraints);

        passwordInput.setMaximumSize(new java.awt.Dimension(150, 28));
        passwordInput.setMinimumSize(new java.awt.Dimension(150, 28));
        passwordInput.setPreferredSize(new java.awt.Dimension(150, 28));
        passwordInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordInputKeyPressed(evt);
            }
        });
        jPanel4.add(passwordInput, new java.awt.GridBagConstraints());

        jPanel2.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        cancelBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cancelBtn.setText("Otkaži");
        cancelBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelBtn.setMaximumSize(new java.awt.Dimension(81, 28));
        cancelBtn.setPreferredSize(new java.awt.Dimension(81, 28));
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 16);
        jPanel5.add(cancelBtn, gridBagConstraints);

        loginBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        loginBtn.setText("Prijavi se");
        loginBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginBtn.setMaximumSize(new java.awt.Dimension(81, 28));
        loginBtn.setMinimumSize(new java.awt.Dimension(81, 28));
        loginBtn.setPreferredSize(new java.awt.Dimension(81, 28));
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 0, 0);
        jPanel5.add(loginBtn, gridBagConstraints);

        jPanel2.add(jPanel5);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        passForgotten.setFont(new java.awt.Font("Poppins Light", 2, 12)); // NOI18N
        passForgotten.setText("Zaboravili ste lozinku?");
        passForgotten.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        passForgotten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                passForgottenMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 0);
        jPanel6.add(passForgotten, gridBagConstraints);

        jPanel2.add(jPanel6);

        jPanel7.add(jPanel2);

        getContentPane().add(jPanel7, new java.awt.GridBagConstraints());

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void passForgottenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_passForgottenMouseClicked
        ForgottenPass passwordFrame = null;
        try {
            passwordFrame = new ForgottenPass();
            passwordFrame.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Greška prilikom pokretanja!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
        this.setVisible(false);
    }//GEN-LAST:event_passForgottenMouseClicked

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        User.UserController userController = new UserController();

        String username = String.valueOf(usernameInput.getText());
        String password = String.valueOf(passwordInput.getPassword());

        try {
            if (userController.isLoggedIn(username, password)) {
                MainWindow mainFrame = new MainWindow(userController.getUser());
                mainFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Uneseni su neispravni podaci!", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Greška prilikom prijave!", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_loginBtnActionPerformed

    private void passwordInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loginBtn.doClick();
        }
    }//GEN-LAST:event_passwordInputKeyPressed

    private void usernameInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameInputKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            loginBtn.doClick();
        }
    }//GEN-LAST:event_usernameInputKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatDarkLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JButton loginBtn;
    private javax.swing.JLabel passForgotten;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JTextField usernameInput;
    // End of variables declaration//GEN-END:variables
}
