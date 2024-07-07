/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Receipt;

import Item.ItemController;
import Store.StoreController;
import User.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Suad's Laptop
 */
public class ReceiptDetails extends javax.swing.JInternalFrame {

    private final Receipt currentReceipt;
    private final String currentUserPrivilege;

    private final ReceiptController receiptController = new ReceiptController();
    private final StoreController storeController = new StoreController();
    private final UserController userController = new UserController();
    private final ItemController itemController = new ItemController();

    /**
     * Creates new form About
     *
     * @param receiptId
     * @param currentUserPrivilege
     * @throws java.sql.SQLException
     */
    public ReceiptDetails(String receiptId, String currentUserPrivilege) throws SQLException {
        initComponents();
        this.currentReceipt = receiptController.getReceipt(receiptId);
        this.currentUserPrivilege = currentUserPrivilege;

        startup();
        escapeKey();
    }

    public final void startup() throws SQLException {
        receiptIdLabel.setText(Integer.toString(currentReceipt.getId_racun()));
        receiptStoreLabel.setText(storeController.storeName(Integer.toString(currentReceipt.getId_maloprodaja())));
        receiptUserLabel.setText(userController.userNameAndSurname(Integer.toString(currentReceipt.getId_zaposlenik())));
        receiptDateLabel.setText(currentReceipt.getDatum());
        receiptTimeLabel.setText(currentReceipt.getVrijeme());
        receiptTotalPriceLabel.setText(roundToTwoDecimalPlaces(currentReceipt.getUkupna_cijena()) + " KM");
        receiptTotalDiscountLabel.setText(roundToTwoDecimalPlaces(currentReceipt.getUkupni_popust()) + " KM");
        receiptTaxPriceLabel.setText(roundToTwoDecimalPlaces(currentReceipt.getPdv()) + " KM");

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            if (i != 1) {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        populateTable();
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

    private void populateTable() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (ReceiptItem item : currentReceipt.getItems()) {
            model.addRow(new Object[]{
                item.getId_artikal(),
                itemController.getItemName(Integer.toString(item.getId_artikal())),
                item.getKolicina(),
                roundToTwoDecimalPlaces(item.getUkupna_cijena() / (item.getKolicina() * (1 - item.getPopust() / 100))),
                item.getPopust(),
                item.getUkupna_cijena()
            });
        }
    }

    private String roundToTwoDecimalPlaces(Double value) {

        value = Math.round(value * 100) / 100.0;
        String newValue = Double.toString(value);
        int periodIndex = newValue.indexOf('.');

        if (periodIndex != -1 && periodIndex + 2 == newValue.length()) {
            newValue += "0";
        }

        return newValue;
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        receiptTaxPriceLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        receiptIdLabel = new javax.swing.JLabel();
        receiptDateLabel = new javax.swing.JLabel();
        receiptUserLabel = new javax.swing.JLabel();
        receiptStoreLabel = new javax.swing.JLabel();
        receiptTotalDiscountLabel = new javax.swing.JLabel();
        receiptTotalPriceLabel = new javax.swing.JLabel();
        receiptTimeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonsPanel = new javax.swing.JPanel();
        closeBtn = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Detalji o računu");
        setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Menu Icons/users.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(900, 500));
        setPreferredSize(new java.awt.Dimension(900, 500));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel3.setText("ID:");
        jLabel3.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel3.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel3.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        jPanel1.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel4.setText("Maloprodaja:");
        jLabel4.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel4.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel4.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel5.setText("Operater:");
        jLabel5.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel5.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel5.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel6.setText("Datum:");
        jLabel6.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel6.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel6.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel1.add(jLabel6, gridBagConstraints);

        receiptTaxPriceLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        receiptTaxPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        receiptTaxPriceLabel.setText("1.50 KM");
        receiptTaxPriceLabel.setMaximumSize(new java.awt.Dimension(100, 28));
        receiptTaxPriceLabel.setMinimumSize(new java.awt.Dimension(100, 28));
        receiptTaxPriceLabel.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel1.add(receiptTaxPriceLabel, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel8.setText("Ukupna cijena:");
        jLabel8.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel8.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel8.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel9.setText("Ukupni popust:");
        jLabel9.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel9.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel9.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
        jPanel1.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel10.setText("PDV:");
        jLabel10.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel10.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel10.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 16, 0);
        jPanel1.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel11.setText("Vrijeme:");
        jLabel11.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel11.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel11.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(16, 8, 0, 0);
        jPanel1.add(jLabel11, gridBagConstraints);

        receiptIdLabel.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        receiptIdLabel.setText("1272");
        receiptIdLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        receiptIdLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        receiptIdLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        jPanel1.add(receiptIdLabel, gridBagConstraints);

        receiptDateLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        receiptDateLabel.setText("20. nov. 2002.");
        receiptDateLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        receiptDateLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        receiptDateLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel1.add(receiptDateLabel, gridBagConstraints);

        receiptUserLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        receiptUserLabel.setText("Suad Kucalović");
        receiptUserLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        receiptUserLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        receiptUserLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel1.add(receiptUserLabel, gridBagConstraints);

        receiptStoreLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        receiptStoreLabel.setText("Maloprodaja d.o.o.");
        receiptStoreLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        receiptStoreLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        receiptStoreLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel1.add(receiptStoreLabel, gridBagConstraints);

        receiptTotalDiscountLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        receiptTotalDiscountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        receiptTotalDiscountLabel.setText("3.69 KM");
        receiptTotalDiscountLabel.setMaximumSize(new java.awt.Dimension(100, 28));
        receiptTotalDiscountLabel.setMinimumSize(new java.awt.Dimension(100, 28));
        receiptTotalDiscountLabel.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        jPanel1.add(receiptTotalDiscountLabel, gridBagConstraints);

        receiptTotalPriceLabel.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        receiptTotalPriceLabel.setForeground(new java.awt.Color(0, 153, 51));
        receiptTotalPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        receiptTotalPriceLabel.setText("369.00 KM");
        receiptTotalPriceLabel.setMaximumSize(new java.awt.Dimension(100, 28));
        receiptTotalPriceLabel.setMinimumSize(new java.awt.Dimension(100, 28));
        receiptTotalPriceLabel.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel1.add(receiptTotalPriceLabel, gridBagConstraints);

        receiptTimeLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        receiptTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        receiptTimeLabel.setText("3:14");
        receiptTimeLabel.setToolTipText("");
        receiptTimeLabel.setMaximumSize(new java.awt.Dimension(100, 28));
        receiptTimeLabel.setMinimumSize(new java.awt.Dimension(100, 28));
        receiptTimeLabel.setPreferredSize(new java.awt.Dimension(100, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        jPanel1.add(receiptTimeLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel1, gridBagConstraints);

        jScrollPane1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jTable1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Naziv", "Količina", "Cijena", "Popust %", "Ukupna cijena"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.setRowHeight(28);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowGrid(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(1).setMinWidth(250);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        buttonsPanel.setMaximumSize(new java.awt.Dimension(450, 28));
        buttonsPanel.setMinimumSize(new java.awt.Dimension(450, 28));
        buttonsPanel.setPreferredSize(new java.awt.Dimension(450, 28));
        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        closeBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        closeBtn.setText("Zatvori");
        closeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeBtn.setMaximumSize(new java.awt.Dimension(120, 28));
        closeBtn.setMinimumSize(new java.awt.Dimension(120, 28));
        closeBtn.setPreferredSize(new java.awt.Dimension(120, 28));
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 16);
        buttonsPanel.add(closeBtn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 16, 0);
        getContentPane().add(buttonsPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton closeBtn;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel receiptDateLabel;
    private javax.swing.JLabel receiptIdLabel;
    private javax.swing.JLabel receiptStoreLabel;
    private javax.swing.JLabel receiptTaxPriceLabel;
    private javax.swing.JLabel receiptTimeLabel;
    private javax.swing.JLabel receiptTotalDiscountLabel;
    private javax.swing.JLabel receiptTotalPriceLabel;
    private javax.swing.JLabel receiptUserLabel;
    // End of variables declaration//GEN-END:variables
}
