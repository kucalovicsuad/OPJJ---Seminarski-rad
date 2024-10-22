/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Distributor;

import Store.*;
import Controller.Controller;
import User.UserController;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Suad's Laptop
 */
public class DistributorDetails extends javax.swing.JInternalFrame {
    private final Controller dbController = new Controller();
    private Distributor currentDistributor = null;
    private String currentUserPrivilege = null;
    
    /**
     * Creates new form ChangeLoginInfo
     *
     * @param distributorInfo
     * @param currentUserPrivilege
     * @throws java.sql.SQLException
     */
    public DistributorDetails(Object[] distributorInfo, String currentUserPrivilege) throws SQLException {
        initComponents();
        this.currentDistributor = new Distributor((int) distributorInfo[0], (String) distributorInfo[1], 
                (String) distributorInfo[2], (String) distributorInfo[3], (String) distributorInfo[4], 
                (String) distributorInfo[5], (String) distributorInfo[6]);
        this.currentUserPrivilege = currentUserPrivilege;
        startup();
        escapeKey();
    }
    
    private void startup() throws SQLException {

        idLabel.setText(Integer.toString(currentDistributor.getId_distributer()));
        distributorNameInput.setText(currentDistributor.getNaziv());
        addressInput.setText(currentDistributor.getAdresa());
        cityInput.setText(currentDistributor.getGrad());
        countryInput.setText(currentDistributor.getDrzava());
        emailInput.setText(currentDistributor.getEmail());
        phoneNumberInput.setText(currentDistributor.getKontakt_broj());

        if ("zaposlenik".equals(currentUserPrivilege) && !("admin".equals(currentUserPrivilege) || "superadmin".equals(currentUserPrivilege)
                || "vlasnik".equals(currentUserPrivilege))) {
            distributorNameInput.setEditable(false);
            addressInput.setEditable(false);
            cityInput.setEditable(false);
            countryInput.setEditable(false);
            emailInput.setEditable(false);
            phoneNumberInput.setEditable(false);
            changeBtn.setEnabled(false);
            changeBtn.setVisible(false);
            deleteBtn.setEnabled(false);
            deleteBtn.setVisible(false);
        }

        addressInput.setText(currentDistributor.getAdresa());
        cityInput.setText(currentDistributor.getGrad());
        countryInput.setText(currentDistributor.getDrzava());
        emailInput.setText(currentDistributor.getEmail());
        phoneNumberInput.setText(currentDistributor.getKontakt_broj());
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        distributorNameInput = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        addressInput = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cityInput = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        countryInput = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        emailInput = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        phoneNumberInput = new javax.swing.JTextField();
        buttonsPanel = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        changeBtn = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Detalji o distributeru");
        setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Menu Icons/info.png"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(500, 480));
        setMinimumSize(new java.awt.Dimension(500, 480));
        setPreferredSize(new java.awt.Dimension(500, 480));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(332, 340));
        jPanel1.setMinimumSize(new java.awt.Dimension(332, 340));
        jPanel1.setPreferredSize(new java.awt.Dimension(332, 340));
        jPanel1.setLayout(new java.awt.GridLayout(7, 0));

        jPanel2.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel2.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel11.setText("ID:");
        jLabel11.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel11.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel11.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel2.add(jLabel11, new java.awt.GridBagConstraints());

        idLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        idLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        idLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        idLabel.setName(""); // NOI18N
        idLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel2.add(idLabel, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel2);

        jPanel6.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel6.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel6.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel12.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel12.setText("Naziv:");
        jLabel12.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel12.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel12.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel6.add(jLabel12, new java.awt.GridBagConstraints());

        distributorNameInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        distributorNameInput.setMaximumSize(new java.awt.Dimension(200, 28));
        distributorNameInput.setMinimumSize(new java.awt.Dimension(200, 28));
        distributorNameInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel6.add(distributorNameInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel6);

        jPanel7.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel7.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel7.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel15.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel15.setText("Adresa:");
        jLabel15.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel15.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel15.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel7.add(jLabel15, new java.awt.GridBagConstraints());

        addressInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        addressInput.setMaximumSize(new java.awt.Dimension(200, 28));
        addressInput.setMinimumSize(new java.awt.Dimension(200, 28));
        addressInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel7.add(addressInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel7);

        jPanel10.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel10.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel10.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        jLabel16.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel16.setText("Grad:");
        jLabel16.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel16.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel16.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel10.add(jLabel16, new java.awt.GridBagConstraints());

        cityInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        cityInput.setMaximumSize(new java.awt.Dimension(200, 28));
        cityInput.setMinimumSize(new java.awt.Dimension(200, 28));
        cityInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel10.add(cityInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel10);

        jPanel11.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel11.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel11.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel17.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel17.setText("Država");
        jLabel17.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel17.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel17.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel11.add(jLabel17, new java.awt.GridBagConstraints());

        countryInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        countryInput.setMaximumSize(new java.awt.Dimension(200, 28));
        countryInput.setMinimumSize(new java.awt.Dimension(200, 28));
        countryInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel11.add(countryInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel11);

        jPanel12.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel12.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel12.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        jLabel18.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel18.setText("E-mail:");
        jLabel18.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel18.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel18.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel12.add(jLabel18, new java.awt.GridBagConstraints());

        emailInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        emailInput.setMaximumSize(new java.awt.Dimension(200, 28));
        emailInput.setMinimumSize(new java.awt.Dimension(200, 28));
        emailInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel12.add(emailInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel12);

        jPanel13.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel13.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel13.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jLabel19.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel19.setText("Br. telefona:");
        jLabel19.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel19.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel19.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel13.add(jLabel19, new java.awt.GridBagConstraints());

        phoneNumberInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        phoneNumberInput.setMaximumSize(new java.awt.Dimension(200, 28));
        phoneNumberInput.setMinimumSize(new java.awt.Dimension(200, 28));
        phoneNumberInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel13.add(phoneNumberInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(28, 0, 0, 0);
        getContentPane().add(jPanel1, gridBagConstraints);

        buttonsPanel.setMaximumSize(new java.awt.Dimension(360, 28));
        buttonsPanel.setMinimumSize(new java.awt.Dimension(360, 28));
        buttonsPanel.setPreferredSize(new java.awt.Dimension(360, 28));
        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        cancelBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        cancelBtn.setText("Otkaži");
        cancelBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelBtn.setMaximumSize(new java.awt.Dimension(96, 28));
        cancelBtn.setMinimumSize(new java.awt.Dimension(96, 28));
        cancelBtn.setPreferredSize(new java.awt.Dimension(96, 28));
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 16);
        buttonsPanel.add(cancelBtn, gridBagConstraints);

        deleteBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        deleteBtn.setText("Obriši");
        deleteBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBtn.setMaximumSize(new java.awt.Dimension(96, 28));
        deleteBtn.setMinimumSize(new java.awt.Dimension(96, 28));
        deleteBtn.setPreferredSize(new java.awt.Dimension(96, 28));
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 0);
        buttonsPanel.add(deleteBtn, gridBagConstraints);

        changeBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        changeBtn.setText("Promijeni");
        changeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        changeBtn.setMaximumSize(new java.awt.Dimension(96, 28));
        changeBtn.setMinimumSize(new java.awt.Dimension(96, 28));
        changeBtn.setPreferredSize(new java.awt.Dimension(96, 28));
        changeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 16);
        buttonsPanel.add(changeBtn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(32, 0, 16, 0);
        getContentPane().add(buttonsPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        int confirmation = JOptionPane.showOptionDialog(
                rootPane,
                "Jeste li sigurni da želite obrisati podatak iz baze podataka?",
                "Brisanje distributera",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Da", "Ne"},
                "Ne");

        if (confirmation == JOptionPane.YES_OPTION) {
            String sqlQuery1 = "DELETE FROM Distributer WHERE id_distributer = '" + currentDistributor.getId_distributer() + "'";
            String sqlQuery2 = "DELETE FROM Maloprodaja_Distributer WHERE id_distributer = '" + currentDistributor.getId_distributer() + "'";
            try {
                if (dbController.InsDelUpd(sqlQuery1) && dbController.InsDelUpd(sqlQuery2)) {
                    JOptionPane.showMessageDialog(rootPane, "Uspješno obrisani podaci...");
                    this.dispose();
                } else {
                    SQLException ex = new SQLException();
                    throw ex;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Greška tokom brisanja podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void changeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeBtnActionPerformed
       if (!distributorNameInput.getText().equals(currentDistributor.getNaziv())
                || !addressInput.getText().equals(currentDistributor.getAdresa())
                || !cityInput.getText().equals(currentDistributor.getGrad())
                || !countryInput.getText().equals(currentDistributor.getDrzava())
                || !emailInput.getText().equals(currentDistributor.getEmail())
                || !phoneNumberInput.getText().equals(currentDistributor.getKontakt_broj())) {

            String sqlQuery = "UPDATE Distributer SET "
                    + "naziv = '" + distributorNameInput.getText() + "', "
                    + "adresa = '" + addressInput.getText() + "', "
                    + "grad = '" + cityInput.getText() + "', "
                    + "drzava = '" + countryInput.getText() + "', "
                    + "email = '" + emailInput.getText() + "', "
                    + "kontakt_broj = '" + phoneNumberInput.getText() + "' "
                    + "WHERE id_distributer = '" + currentDistributor.getId_distributer()+ "'";

            try {
                if (dbController.InsDelUpd(sqlQuery)) {
                    JOptionPane.showMessageDialog(rootPane, "Uspješno izmijenjeni podaci...");
                } else {
                    SQLException ex = new SQLException();
                    throw ex;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Greška tokom ažuriranja podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Vrijednosti su nepromijenjene.", "Informacija", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_changeBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressInput;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton changeBtn;
    private javax.swing.JTextField cityInput;
    private javax.swing.JTextField countryInput;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField distributorNameInput;
    private javax.swing.JTextField emailInput;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField phoneNumberInput;
    // End of variables declaration//GEN-END:variables
}
