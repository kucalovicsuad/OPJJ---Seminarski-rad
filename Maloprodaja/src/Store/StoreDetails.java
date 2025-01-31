/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Store;

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
public class StoreDetails extends javax.swing.JInternalFrame {

    private final Controller dbController = new Controller();
    private final StoreController storeController = new StoreController();
    private final UserController userController = new UserController();
    private final Map<String, String> storeOwnerAndAdmin;
    private final ArrayList<Map<String, String>> allUsers;
    private Store currentStore = null;
    private String currentUserPrivilege = null;

    /**
     * Creates new form ChangeLoginInfo
     *
     * @param storeInfo
     * @param currentUserPrivilege
     * @throws java.sql.SQLException
     */
    public StoreDetails(Object[] storeInfo, String currentUserPrivilege) throws SQLException {
        initComponents();
        this.storeOwnerAndAdmin = storeController.storeOwnerAndAdmin(Integer.toString((Integer) storeInfo[0]));
        this.allUsers = userController.getAllUsers();
        this.currentStore = new Store((int) storeInfo[0], Integer.parseInt(storeOwnerAndAdmin.get("id_vlasnik")), Integer.parseInt(storeOwnerAndAdmin.get("id_admin")),
                (String) storeInfo[1], (String) storeInfo[4], (String) storeInfo[5], (String) storeInfo[6], (String) storeInfo[7], (String) storeInfo[8]);
        this.currentUserPrivilege = currentUserPrivilege;
        startup();
        escapeKey();
    }

    private void startup() throws SQLException {

        storeIdLabel.setText(Integer.toString(currentStore.getId_maloprodaja()));
        storeEmployeesLabel.setText(storeController.numOfEmployees(Integer.toString(currentStore.getId_maloprodaja())));
        storeNameInput.setText(currentStore.getNaziv());

        ownerInput.addItem(storeOwnerAndAdmin.get("id_vlasnik") + " - " + storeOwnerAndAdmin.get("vlasnikName"));
        adminInput.addItem(storeOwnerAndAdmin.get("id_admin") + " - " + storeOwnerAndAdmin.get("adminName"));

        for (Map<String, String> user : allUsers) {
            if (!storeOwnerAndAdmin.get("id_vlasnik").equals(user.get("id_zaposlenik")) && "vlasnik".equals(user.get("ovlasti"))) {
                ownerInput.addItem(user.get("id_zaposlenik") + " - " + user.get("imeIPrezime"));
            }

            if (!storeOwnerAndAdmin.get("id_admin").equals(user.get("id_zaposlenik")) && !"vlasnik".equals(user.get("ovlasti"))) {
                adminInput.addItem(user.get("id_zaposlenik") + " - " + user.get("imeIPrezime"));
            }
        }

        if ("zaposlenik".equals(currentUserPrivilege) && !("admin".equals(currentUserPrivilege) || "superadmin".equals(currentUserPrivilege)
                || "vlasnik".equals(currentUserPrivilege))) {
            storeNameInput.setEnabled(false);
            ownerInput.setEnabled(false);
            adminInput.setEnabled(false);
            addressInput.setEnabled(false);
            cityInput.setEnabled(false);
            countryInput.setEnabled(false);
            emailInput.setEnabled(false);
            phoneNumberInput.setEnabled(false);
            changeBtn.setEnabled(false);
            changeBtn.setVisible(false);
            deleteBtn.setEnabled(false);
            deleteBtn.setVisible(false);
        }

        addressInput.setText(currentStore.getAdresa());
        cityInput.setText(currentStore.getGrad());
        countryInput.setText(currentStore.getDrzava());
        emailInput.setText(currentStore.getEmail());
        phoneNumberInput.setText(currentStore.getKontakt_broj());
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
        storeIdLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        storeEmployeesLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        storeNameInput = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        ownerInput = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        adminInput = new javax.swing.JComboBox<>();
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
        setTitle("Detalji o maloprodajnom objektu");
        setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Menu Icons/info.png"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(500, 600));
        setMinimumSize(new java.awt.Dimension(500, 600));
        setPreferredSize(new java.awt.Dimension(500, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(332, 460));
        jPanel1.setMinimumSize(new java.awt.Dimension(332, 460));
        jPanel1.setPreferredSize(new java.awt.Dimension(332, 460));
        jPanel1.setLayout(new java.awt.GridLayout(10, 1, 0, 16));

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

        storeIdLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        storeIdLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        storeIdLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        storeIdLabel.setName(""); // NOI18N
        storeIdLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel2.add(storeIdLabel, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel2);

        jPanel3.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel3.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel3.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel20.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel20.setText("Zaposlenika:");
        jLabel20.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel20.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel20.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel3.add(jLabel20, new java.awt.GridBagConstraints());

        storeEmployeesLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        storeEmployeesLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        storeEmployeesLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        storeEmployeesLabel.setName(""); // NOI18N
        storeEmployeesLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel3.add(storeEmployeesLabel, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel3);

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

        storeNameInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        storeNameInput.setMaximumSize(new java.awt.Dimension(200, 28));
        storeNameInput.setMinimumSize(new java.awt.Dimension(200, 28));
        storeNameInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel6.add(storeNameInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel6);

        jPanel8.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel8.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel8.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel13.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel13.setText("Vlasnik:");
        jLabel13.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel13.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel13.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel8.add(jLabel13, new java.awt.GridBagConstraints());

        ownerInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        ownerInput.setMaximumSize(new java.awt.Dimension(200, 28));
        ownerInput.setMinimumSize(new java.awt.Dimension(200, 28));
        ownerInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel8.add(ownerInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel8);

        jPanel9.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel9.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel9.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel14.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel14.setText("Admin:");
        jLabel14.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel14.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel14.setPreferredSize(new java.awt.Dimension(96, 28));
        jPanel9.add(jLabel14, new java.awt.GridBagConstraints());

        adminInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        adminInput.setMaximumSize(new java.awt.Dimension(200, 28));
        adminInput.setMinimumSize(new java.awt.Dimension(200, 28));
        adminInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel9.add(adminInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel9);

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
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
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
        gridBagConstraints.insets = new java.awt.Insets(28, 0, 0, 0);
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
                "Brisanje maloprodajnog objekta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Da", "Ne"}, // Custom button labels
                "Ne");

        if (confirmation == JOptionPane.YES_OPTION) {
            String sqlQuery = "DELETE FROM Maloprodaja WHERE id_maloprodaja = '" + currentStore.getId_maloprodaja() + "'";

            try {
                if (dbController.InsDelUpd(sqlQuery)) {
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
        if (!storeNameInput.getText().equals(currentStore.getNaziv())
                || !addressInput.getText().equals(currentStore.getAdresa())
                || !cityInput.getText().equals(currentStore.getGrad())
                || !countryInput.getText().equals(currentStore.getDrzava())
                || !emailInput.getText().equals(currentStore.getEmail())
                || !phoneNumberInput.getText().equals(currentStore.getKontakt_broj())
                || ownerInput.getSelectedIndex() != 0 || adminInput.getSelectedIndex() != 0) {

            String newOwnerId = (String) ownerInput.getSelectedItem();
            String newAdminId = (String) adminInput.getSelectedItem();

            String sqlQuery = "UPDATE Maloprodaja SET "
                    + "id_vlasnik = '" + newOwnerId.substring(0, newOwnerId.indexOf(" - ")) + "', "
                    + "id_admin = '" + newAdminId.substring(0, newAdminId.indexOf(" - ")) + "', "
                    + "naziv = '" + storeNameInput.getText() + "', "
                    + "adresa = '" + addressInput.getText() + "', "
                    + "grad = '" + cityInput.getText() + "', "
                    + "drzava = '" + countryInput.getText() + "', "
                    + "email = '" + emailInput.getText() + "', "
                    + "kontakt_broj = '" + phoneNumberInput.getText() + "' "
                    + "WHERE id_maloprodaja = '" + currentStore.getId_maloprodaja() + "'";

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
    private javax.swing.JComboBox<String> adminInput;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton changeBtn;
    private javax.swing.JTextField cityInput;
    private javax.swing.JTextField countryInput;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField emailInput;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JComboBox<String> ownerInput;
    private javax.swing.JTextField phoneNumberInput;
    private javax.swing.JLabel storeEmployeesLabel;
    private javax.swing.JLabel storeIdLabel;
    private javax.swing.JTextField storeNameInput;
    // End of variables declaration//GEN-END:variables
}
