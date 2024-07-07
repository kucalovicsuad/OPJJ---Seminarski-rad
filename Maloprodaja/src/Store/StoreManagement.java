/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Store;

import User.User;
import User.UserController;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Suad's Laptop
 */
public class StoreManagement extends javax.swing.JInternalFrame {

    private final StoreController storeController = new StoreController();
    private final UserController userController = new UserController();
    private ArrayList<Store> stores = new ArrayList<>();
    private String currentUserId = null;
    private String currentUserPrivilege = null;
    JDesktopPane desktopPane = null;
    private boolean activated = true;

    /**
     * Creates new form About
     *
     * @param currentUserInfo
     * @param desktopPane
     * @throws java.sql.SQLException
     */
    public StoreManagement(User currentUserInfo, JDesktopPane desktopPane) throws SQLException {
        initComponents();
        this.stores = storeController.storesByUser(Integer.toString(currentUserInfo.getId_user()), searchInput.getText());
        this.currentUserId = Integer.toString(currentUserInfo.getId_user());
        this.currentUserPrivilege = currentUserInfo.getOvlasti();
        this.desktopPane = desktopPane;

        startup();
        escapeKey();
    }

    private void startup() throws SQLException {
        populateTable();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            if (i < 2 || i > 4) {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                try {
                    stores = storeController.storesByUser(currentUserId, searchInput.getText());
                    populateTable();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Greška tokom pribavljanja podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
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

    private static Object[] getRowData(DefaultTableModel model, int row) {
        int columnCount = model.getColumnCount();
        Object[] rowData = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
            rowData[i] = model.getValueAt(row, i);
        }
        return rowData;
    }

    private void populateTable() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Store store : stores) {
            String storeOwner = Integer.toString(store.getId_vlasnik()) + " - " + userController.userNameAndSurname(Integer.toString(store.getId_vlasnik()));
            String storeAdmin = Integer.toString(store.getId_admin()) + " - " + userController.userNameAndSurname(Integer.toString(store.getId_admin()));
            model.addRow(new Object[]{
                store.getId_maloprodaja(),
                store.getNaziv(),
                storeOwner,
                storeAdmin,
                store.getAdresa(),
                store.getGrad(),
                store.getDrzava(),
                store.getEmail(),
                store.getKontakt_broj()
            });
        }

        if (activated) {
            jTable1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = jTable1.getSelectedRow();
                        Object[] rowData = getRowData(model, row);
                        StoreDetails storeDetails = null;
                        try {
                            storeDetails = new StoreDetails(rowData, currentUserPrivilege);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(rootPane, "Greška tokom pribavljanja podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                        }
                        desktopPane.add(storeDetails).setVisible(true);
                        Dimension desktopSize = desktopPane.getSize();
                        Dimension jInternalFrameSize = storeDetails.getSize();
                        storeDetails.setLocation((desktopSize.width - jInternalFrameSize.width) / 2, 32);
                    }
                }
            });

            jTable1.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        int row = jTable1.getSelectedRow();
                        Object[] rowData = getRowData(model, row);
                        StoreDetails storeDetails = null;
                        try {
                            storeDetails = new StoreDetails(rowData, currentUserPrivilege);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(rootPane, "Greška tokom pribavljanja podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                        }
                        desktopPane.add(storeDetails).setVisible(true);
                        Dimension desktopSize = desktopPane.getSize();
                        Dimension jInternalFrameSize = storeDetails.getSize();
                        storeDetails.setLocation((desktopSize.width - jInternalFrameSize.width) / 2, 32);
                    }
                }
            });
        }

        activated = false;
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
        jLabel1 = new javax.swing.JLabel();
        searchInput = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Upravljanje preduzeća");
        setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Menu Icons/store.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(900, 500));
        setPreferredSize(new java.awt.Dimension(900, 500));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel1.setText("Pretraživanje maloprodajnih objekata");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        searchInput.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        searchInput.setMaximumSize(new java.awt.Dimension(250, 28));
        searchInput.setMinimumSize(new java.awt.Dimension(250, 28));
        searchInput.setPreferredSize(new java.awt.Dimension(250, 28));
        searchInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchInputKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel2.add(searchInput, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Poppins ExtraLight", 0, 12)); // NOI18N
        jLabel2.setText("U tabeli su prikazani podaci o maloprodajnim objektima, za detaljan pregled kliknite podatak dva puta...");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(32, 0, 0, 0);
        jPanel1.add(jPanel2, gridBagConstraints);

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
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Naziv", "Vlasnik", "Admin", "Adresa", "Grad", "Država", "E-mail", "Br. tel"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInputKeyReleased
        try {
            stores = storeController.storesByUser(currentUserId, searchInput.getText());
            populateTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Greška tokom pribavljanja podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_searchInputKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField searchInput;
    // End of variables declaration//GEN-END:variables
}
