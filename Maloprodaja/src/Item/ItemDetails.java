/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Item;

import Distributor.*;
import Controller.Controller;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
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
public class ItemDetails extends javax.swing.JInternalFrame {

    private final Controller dbController = new Controller();
    private final DistributorController distributorController = new DistributorController();
    private final ItemController itemController = new ItemController();
    
    private Item currentItem = null;
    private String currentUserPrivilege = null;

    /**
     * Creates new form ChangeLoginInfo
     *
     * @param itemId
     * @param storeId
     * @param currentUserPrivilege
     * @throws java.sql.SQLException
     */
    public ItemDetails(String itemId, String storeId, String currentUserPrivilege) throws SQLException {
        initComponents();
        this.currentItem = itemController.getItemById(itemId, storeId, "-1");
        this.currentUserPrivilege = currentUserPrivilege;
        startup();
        escapeKey();
    }

    private void startup() throws SQLException {

        idLabel.setText(Integer.toString(currentItem.getId_artikal()));
        itemNameInput.setText(currentItem.getNaziv());
        distributorNameInput.setText(Integer.toString(currentItem.getId_artikal()) + " - " + distributorController.distributorName(Integer.toString(currentItem.getId_artikal())));
        amountInput.setText(Integer.toString(currentItem.getKolicina()));
        discountInput.setText(Double.toString(currentItem.getPopust()));
        purchasePriceinput.setText(Double.toString(currentItem.getCijena_nabavke()));
        sellingPriceInput.setText(Double.toString(currentItem.getCijena_prodaje()));
        distributorNameInput.setEditable(false);

        if ("zaposlenik".equals(currentUserPrivilege) && !("admin".equals(currentUserPrivilege) || "superadmin".equals(currentUserPrivilege)
                || "vlasnik".equals(currentUserPrivilege))) {
            itemNameInput.setEditable(false);
            distributorNameInput.setEditable(false);
            amountInput.setEditable(false);
            discountInput.setEditable(false);
            purchasePriceinput.setEditable(false);
            sellingPriceInput.setEditable(false);
            changeBtn.setEnabled(false);
            changeBtn.setVisible(false);
            deleteBtn.setEnabled(false);
            deleteBtn.setVisible(false);
        }
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
        itemNameInput = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        distributorNameInput = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        amountInput = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        discountInput = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        purchasePriceinput = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        sellingPriceInput = new javax.swing.JTextField();
        buttonsPanel = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        changeBtn = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Detalji o artiklu");
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
        jLabel11.setMaximumSize(new java.awt.Dimension(110, 28));
        jLabel11.setMinimumSize(new java.awt.Dimension(110, 28));
        jLabel11.setPreferredSize(new java.awt.Dimension(110, 28));
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
        jLabel12.setMaximumSize(new java.awt.Dimension(110, 28));
        jLabel12.setMinimumSize(new java.awt.Dimension(110, 28));
        jLabel12.setPreferredSize(new java.awt.Dimension(110, 28));
        jPanel6.add(jLabel12, new java.awt.GridBagConstraints());

        itemNameInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        itemNameInput.setMaximumSize(new java.awt.Dimension(200, 28));
        itemNameInput.setMinimumSize(new java.awt.Dimension(200, 28));
        itemNameInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel6.add(itemNameInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel6);

        jPanel7.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel7.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel7.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel15.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel15.setText("Distributer:");
        jLabel15.setMaximumSize(new java.awt.Dimension(110, 28));
        jLabel15.setMinimumSize(new java.awt.Dimension(110, 28));
        jLabel15.setPreferredSize(new java.awt.Dimension(110, 28));
        jPanel7.add(jLabel15, new java.awt.GridBagConstraints());

        distributorNameInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        distributorNameInput.setFocusable(false);
        distributorNameInput.setMaximumSize(new java.awt.Dimension(200, 28));
        distributorNameInput.setMinimumSize(new java.awt.Dimension(200, 28));
        distributorNameInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel7.add(distributorNameInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel7);

        jPanel10.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel10.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel10.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        jLabel16.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel16.setText("Količina:");
        jLabel16.setMaximumSize(new java.awt.Dimension(110, 28));
        jLabel16.setMinimumSize(new java.awt.Dimension(110, 28));
        jLabel16.setPreferredSize(new java.awt.Dimension(110, 28));
        jPanel10.add(jLabel16, new java.awt.GridBagConstraints());

        amountInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        amountInput.setMaximumSize(new java.awt.Dimension(200, 28));
        amountInput.setMinimumSize(new java.awt.Dimension(200, 28));
        amountInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel10.add(amountInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel10);

        jPanel11.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel11.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel11.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel17.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel17.setText("Popust:");
        jLabel17.setMaximumSize(new java.awt.Dimension(110, 28));
        jLabel17.setMinimumSize(new java.awt.Dimension(110, 28));
        jLabel17.setPreferredSize(new java.awt.Dimension(110, 28));
        jPanel11.add(jLabel17, new java.awt.GridBagConstraints());

        discountInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        discountInput.setMaximumSize(new java.awt.Dimension(200, 28));
        discountInput.setMinimumSize(new java.awt.Dimension(200, 28));
        discountInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel11.add(discountInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel11);

        jPanel12.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel12.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel12.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        jLabel18.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel18.setText("Nabavna cijena:");
        jLabel18.setMaximumSize(new java.awt.Dimension(110, 28));
        jLabel18.setMinimumSize(new java.awt.Dimension(110, 28));
        jLabel18.setPreferredSize(new java.awt.Dimension(110, 28));
        jPanel12.add(jLabel18, new java.awt.GridBagConstraints());

        purchasePriceinput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        purchasePriceinput.setMaximumSize(new java.awt.Dimension(200, 28));
        purchasePriceinput.setMinimumSize(new java.awt.Dimension(200, 28));
        purchasePriceinput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel12.add(purchasePriceinput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel12);

        jPanel13.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel13.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel13.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jLabel19.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel19.setText("Prodajna cijena:");
        jLabel19.setMaximumSize(new java.awt.Dimension(110, 28));
        jLabel19.setMinimumSize(new java.awt.Dimension(110, 28));
        jLabel19.setPreferredSize(new java.awt.Dimension(110, 28));
        jPanel13.add(jLabel19, new java.awt.GridBagConstraints());

        sellingPriceInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        sellingPriceInput.setMaximumSize(new java.awt.Dimension(200, 28));
        sellingPriceInput.setMinimumSize(new java.awt.Dimension(200, 28));
        sellingPriceInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel13.add(sellingPriceInput, new java.awt.GridBagConstraints());

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
            String sqlQuery1 = "DELETE FROM Artikal WHERE id_artikal = '" + currentItem.getId_artikal() + "'";
            try {
                if (dbController.InsDelUpd(sqlQuery1)) {
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
        if (!itemNameInput.getText().equals(currentItem.getNaziv())
                || !amountInput.getText().equals(currentItem.getKolicina())
                || !discountInput.getText().equals(currentItem.getPopust())
                || !purchasePriceinput.getText().equals(currentItem.getCijena_nabavke())
                || !sellingPriceInput.getText().equals(currentItem.getCijena_prodaje())) {
            
            String newDistributorName = distributorNameInput.getText();

            String sqlQuery = "UPDATE Artikal SET "
                    + "naziv = '" + distributorNameInput.getText() + "', "
                    + "kolicina = '" + amountInput.getText() + "', "
                    + "popust = '" + discountInput.getText() + "', "
                    + "cijena_nabavke = '" + purchasePriceinput.getText() + "', "
                    + "cijena_prodaje = '" + sellingPriceInput.getText() + "' "
                    + "WHERE id_artikal = '" + currentItem.getId_artikal()+ "'";

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
    private javax.swing.JTextField amountInput;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton changeBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField discountInput;
    private javax.swing.JTextField distributorNameInput;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField itemNameInput;
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
    private javax.swing.JTextField purchasePriceinput;
    private javax.swing.JTextField sellingPriceInput;
    // End of variables declaration//GEN-END:variables
}
