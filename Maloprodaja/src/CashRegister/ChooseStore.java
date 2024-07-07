/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package CashRegister;

import Store.Store;
import Store.StoreController;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Suad's Laptop
 */
public class ChooseStore extends javax.swing.JInternalFrame {

    private final String currentUserId;
    private String currentStoreId;
    private final String currentUserPrivilege;
    private final JDesktopPane desktopPane;
    private final StoreController storeController = new StoreController();
    private final RegisterController registerController = new RegisterController();

    /**
     * Creates new form Deposit
     *
     * @param currentUserId
     * @param currentStoreId
     * @param currentUserPrivilege
     * @param desktopPane
     * @throws java.sql.SQLException
     */
    public ChooseStore(String currentUserId, String currentStoreId, String currentUserPrivilege, JDesktopPane desktopPane) throws SQLException {
        this.currentUserId = currentUserId;
        this.currentStoreId = currentStoreId;
        this.currentUserPrivilege = currentUserPrivilege;
        this.desktopPane = desktopPane;

        startup();
        escapeKey();
    }

    private void startup() throws SQLException {
        if (currentUserPrivilege.equals("zaposlenik")) {
            openRegister();
        } else {
            initComponents();
            ArrayList<Store> stores = storeController.storesByUser(currentUserId, "");

            int i = 0;
            int index = 0;
            for (Store store : stores) {
                if (Integer.toString(store.getId_maloprodaja()).equals(currentStoreId)) {
                    index = i;
                }
                storeInput.addItem(Integer.toString(store.getId_maloprodaja()) + " - " + storeController.storeName(Integer.toString(store.getId_maloprodaja())));
                i++;
            }

            storeInput.setSelectedIndex(index);
        }
    }

    private void openRegister() throws SQLException {
        if (registerController.isOpen(currentStoreId)) {
            System.out.println(registerController.wasLeftOpen(currentStoreId, currentUserId));
            if (registerController.wasLeftOpen(currentStoreId, currentUserId)) {
                JOptionPane.showMessageDialog(rootPane, "Prethodna kasa nije zaključena, automatski je zaključena sada. Molimo otvorite novu kasu", "Greška", JOptionPane.ERROR_MESSAGE);
                OpenRegister openRegister = null;
                try {
                    openRegister = new OpenRegister(currentUserId, currentUserPrivilege, currentStoreId, desktopPane);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Greška u pribavljanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
                desktopPane.add(openRegister).setVisible(true);
                Dimension desktopSize = desktopPane.getSize();
                Dimension jInternalFrameSize = openRegister.getSize();
                openRegister.setLocation((desktopSize.width - jInternalFrameSize.width) / 2, 32);
                cancelBtn.doClick();
            } else {
                CashRegister cashRegister = null;
                try {
                    cashRegister = new CashRegister(currentUserId, currentStoreId);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Greška u pribavljanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
                desktopPane.add(cashRegister).setVisible(true);
                Dimension desktopSize = desktopPane.getSize();
                Dimension jInternalFrameSize = cashRegister.getSize();
                cashRegister.setLocation((desktopSize.width - jInternalFrameSize.width) / 2, 32);
                cancelBtn.doClick();
            }
        } else {
            int confirmation = JOptionPane.showOptionDialog(
                    rootPane,
                    "Potrebno je prvo otvoriti kasu, želite li otvoriti kasu sada?",
                    "Kasa nije otvorena",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Da", "Ne"},
                    "Da");

            if (confirmation == JOptionPane.YES_OPTION) {
                OpenRegister openRegister = null;
                try {
                    openRegister = new OpenRegister(currentUserId, currentUserPrivilege, currentStoreId, desktopPane);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Greška u pribavljanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
                desktopPane.add(openRegister).setVisible(true);
                Dimension desktopSize = desktopPane.getSize();
                Dimension jInternalFrameSize = openRegister.getSize();
                openRegister.setLocation((desktopSize.width - jInternalFrameSize.width) / 2, 32);
                cancelBtn.doClick();
            }
        }
    }

    private void escapeKey() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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

        jPanel14 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        chooseBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        storeInput = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setTitle("Kasa");
        setMaximumSize(new java.awt.Dimension(400, 280));
        setMinimumSize(new java.awt.Dimension(400, 280));
        setPreferredSize(new java.awt.Dimension(400, 280));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel14.setMaximumSize(new java.awt.Dimension(400, 28));
        jPanel14.setMinimumSize(new java.awt.Dimension(400, 28));
        jPanel14.setPreferredSize(new java.awt.Dimension(400, 28));
        jPanel14.setLayout(new java.awt.GridBagLayout());

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
        jPanel14.add(cancelBtn, gridBagConstraints);

        chooseBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        chooseBtn.setText("Odaberi");
        chooseBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chooseBtn.setMaximumSize(new java.awt.Dimension(96, 28));
        chooseBtn.setMinimumSize(new java.awt.Dimension(96, 28));
        chooseBtn.setPreferredSize(new java.awt.Dimension(96, 28));
        chooseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 0);
        jPanel14.add(chooseBtn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 16, 0);
        getContentPane().add(jPanel14, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel2.setText("Odaberite maloprodajni objekat");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        storeInput.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        storeInput.setMaximumSize(new java.awt.Dimension(150, 28));
        storeInput.setMinimumSize(new java.awt.Dimension(150, 28));
        storeInput.setPreferredSize(new java.awt.Dimension(150, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 16, 0);
        getContentPane().add(storeInput, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void chooseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseBtnActionPerformed
        String newStoreId = (String) storeInput.getSelectedItem();
        currentStoreId = newStoreId.substring(0, newStoreId.indexOf(" - "));
        try {
            openRegister();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Greška u pribavljanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_chooseBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton chooseBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JComboBox<String> storeInput;
    // End of variables declaration//GEN-END:variables
}
