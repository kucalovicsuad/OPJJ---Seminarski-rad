/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package CashRegister;

import Item.Item;
import Item.ItemController;
import Receipt.Receipt;
import Receipt.ReceiptController;
import Receipt.ReceiptItem;
import Store.StoreController;
import User.UserController;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Suad's Laptop
 */
public class CashRegister extends javax.swing.JInternalFrame {

    private final StoreController storeController = new StoreController();
    private final UserController userController = new UserController();
    private final ItemController itemController = new ItemController();
    private final ReceiptController receiptController = new ReceiptController();

    private final String currentUserId;
    private final String currentStoreId;
    private boolean editQuantity = false;
    private boolean addQuantity = true;
    JTextField itemNameInputTextField;

    private ArrayList<ReceiptItem> currentReceiptItems = new ArrayList<>();

    public CashRegister(String currentUserId, String currentStoreId) throws SQLException {
        initComponents();
        this.currentUserId = currentUserId;
        this.currentStoreId = currentStoreId;

        startup();
        customListners();
        escapeKey();
        F1Key();
        F2Key();
        F3Key();
        F10Key();
        F12Key();
    }

    public final void startup() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        model.setRowCount(0);

        LocalDate date = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d. MMM. yyyy.");
        String currentDate = date.format(format);

        storeNameLabel.setText(storeController.storeName(currentStoreId));
        userNameLabel.setText(userController.userNameAndSurname(currentUserId));
        currentDateLabel.setText(currentDate);

        totalPriceLabel.setText("0.00 KM");
        basePriceLabel.setText("0.00 KM");
        taxPriceLabel.setText("0.00 KM");
        discountPriceLabel.setText("0.00 KM");
        extraDiscountInput.setText("");
        totalDiscountValueLabel.setText("0.00 KM");

        totalDiscountLabel.setEnabled(false);
        totalDiscountLabel.setVisible(false);
        totalDiscountValueLabel.setEnabled(false);
        totalDiscountValueLabel.setVisible(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            if (i != 1) {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            barcodeInput.requestFocusInWindow();
        });
    }

    public final void customListners() {
        this.itemNameInputTextField = (JTextField) itemNameInput.getEditor().getEditorComponent();
        itemNameInput.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

        itemNameInputTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_DOWN && e.getKeyCode() != KeyEvent.VK_UP) {
                    try {
                        String input = itemNameInputTextField.getText();
                        filter(input);
                        itemNameInput.setPopupVisible(true);
                        itemNameInputTextField.setText(input);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Greška u pribavljanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        itemFoundByName();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Greška u pribavljanju podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        itemNameInputTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    itemNameInputTextField.setText("");
                    barcodeInput.requestFocus();
                }
            }
        });

        JComponent editor = itemQuantityInput.getEditor();
        JFormattedTextField spinnerTextField = ((JSpinner.DefaultEditor) editor).getTextField();

        spinnerTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (barcodeInput.getText().isEmpty()) {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                        if (jTable1.getSelectedRow() >= 0) {
                            int confirmation = JOptionPane.showOptionDialog(
                                    rootPane,
                                    "Da li želite promijeniti količinu odabranog artikla?",
                                    "Izmjena količine",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    new String[]{"Da", "Ne"},
                                    "Da");

                            if (confirmation == JOptionPane.YES_OPTION) {
                                int barcode = (int) model.getValueAt(jTable1.getSelectedRow(), 0);
                                barcodeInput.setText(Integer.toString(barcode));
                                editQuantity = true;
                                jTable1.clearSelection();
                            } else {
                                readyForNextItem();
                            }
                        } else {
                            int confirmation = JOptionPane.showOptionDialog(
                                    rootPane,
                                    "Da li želite promijeniti količinu zadnjeg dodanog artikla?",
                                    "Izmjena količine",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    new String[]{"Da", "Ne"},
                                    "Da");

                            if (confirmation == JOptionPane.YES_OPTION) {
                                if (jTable1.getRowCount() > 0) {
                                    int barcode = (int) model.getValueAt(jTable1.getRowCount() - 1, 0);
                                    barcodeInput.setText(Integer.toString(barcode));
                                    editQuantity = true;
                                }
                            } else {
                                readyForNextItem();
                            }
                        }
                        
                        barcodeInput.dispatchEvent(new KeyEvent(barcodeInput, KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_ENTER, '\n'));
                    } else {
                        addQuantity = true;
                        barcodeInput.dispatchEvent(new KeyEvent(barcodeInput, KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_ENTER, '\n'));
                    }
                }
            }
        });
    }

    public void filter(String input) throws SQLException {
        itemNameInput.removeAllItems();

        ArrayList<Item> items = itemController.getAllItems(currentStoreId, input);

        for (Item item : items) {
            itemNameInput.addItem(item.getNaziv());
        }
        itemNameInput.setSelectedItem(null);
        itemNameInput.setPopupVisible(false);
    }

    private void escapeKey() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTable1.getRowCount() > 0) {
                    int confirmation = JOptionPane.showOptionDialog(
                            rootPane,
                            "Da li ste sigurni da želite zatvoriti kasu dok je račun aktivan?",
                            "Aktivan račun",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{"Da", "Ne"},
                            "Ne");

                    if (confirmation == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        };

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "disposeForm");
        actionMap.put("disposeForm", escapeAction);
    }

    private void F1Key() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readyForNextItem();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1Key");
        actionMap.put("F1Key", escapeAction);
    }

    private void F2Key() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readyForNextItem();
                itemNameInputTextField.requestFocus();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2Key");
        actionMap.put("F2Key", escapeAction);
    }

    private void F3Key() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemQuantityInput.requestFocus();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "F3Key");
        actionMap.put("F3Key", escapeAction);
    }

    private void F10Key() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBtn.doClick();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "F10Key");
        actionMap.put("F10Key", escapeAction);
    }

    private void F12Key() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishBtn.doClick();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "F12Key");
        actionMap.put("F12Key", escapeAction);
    }

    private void itemFoundByName() throws SQLException {
        itemNameInput.setPopupVisible(false);

        if (itemController.doesItemWithNameExist(itemNameInputTextField.getText(), Integer.toString((int) itemQuantityInput.getValue()))) {
            populateTable(itemController.getItemByName(itemNameInputTextField.getText(), currentStoreId, "1"));
        } else {
            JOptionPane.showMessageDialog(rootPane, "Artikal nije na stanju.", "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int itemAlreadyInTable(int itemId) {
        for (int row = 0; row < jTable1.getRowCount(); row++) {
            if ((int) jTable1.getValueAt(row, 0) == itemId) {
                return row;
            }
        }
        return -1;
    }

    private void populateTable(Item addedItem) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int rowIndex = itemAlreadyInTable(addedItem.getId_artikal());

        if (rowIndex >= 0) {
            int newQuantity = (int) model.getValueAt(rowIndex, 2);

            if (editQuantity) {
                newQuantity = (int) itemQuantityInput.getValue();
                editQuantity = false;
                addQuantity = false;
            }
            
            if (addQuantity) {
                newQuantity = newQuantity + (int) itemQuantityInput.getValue();
            } else {
                addQuantity = true;
            }

            model.setValueAt(newQuantity, rowIndex, 2);

            double newPrice = addedItem.getCijena_prodaje() - (addedItem.getPopust() / 100) * addedItem.getCijena_prodaje();
            model.setValueAt(roundToTwoDecimalPlaces(newPrice * newQuantity), rowIndex, 5);

        } else {
            model.addRow(new Object[]{
                addedItem.getId_artikal(),
                addedItem.getNaziv(),
                (int) itemQuantityInput.getValue(),
                roundToTwoDecimalPlaces(addedItem.getCijena_prodaje()),
                roundToTwoDecimalPlaces(addedItem.getPopust()),
                roundToTwoDecimalPlaces((int) itemQuantityInput.getValue() * (addedItem.getCijena_prodaje() - (addedItem.getPopust() / 100) * addedItem.getCijena_prodaje()))
            });
        }

        updatePrices();
        readyForNextItem();
    }

    private void readyForNextItem() {
        jTable1.clearSelection();
        barcodeInput.requestFocus();
        barcodeInput.setText("");
        itemNameInputTextField.setText("");
        itemQuantityInput.setValue(1);
    }

    private void updatePrices() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        double extraDiscount = 0;

        if (!extraDiscountInput.getText().isEmpty() && Double.parseDouble(extraDiscountInput.getText()) != 0) {
            extraDiscount = Double.parseDouble(extraDiscountInput.getText());

            if (extraDiscount < 0 || extraDiscount > 100) {
                JOptionPane.showMessageDialog(rootPane, "Vrijednost popusta nije validna.", "Greška", JOptionPane.ERROR_MESSAGE);
                extraDiscountInput.setText("");
                extraDiscount = 0;
            }
        } else {
            extraDiscountInput.setText("");
            totalDiscountLabel.setEnabled(false);
            totalDiscountLabel.setVisible(false);
            totalDiscountValueLabel.setEnabled(false);
            totalDiscountValueLabel.setVisible(false);
        }

        double totalPrice = 0;
        for (int row = 0; row < jTable1.getRowCount(); row++) {
            totalPrice += Double.valueOf((String) model.getValueAt(row, 5));
        }

        double discountPrice = 0;
        for (int row = 0; row < jTable1.getRowCount(); row++) {
            discountPrice += (int) model.getValueAt(row, 2) * Double.parseDouble((String) model.getValueAt(row, 3));
        }

        discountPrice -= totalPrice;
        double totalDiscountPrice = totalPrice * (extraDiscount / 100);
        totalPrice -= totalDiscountPrice;

        double basePrice = totalPrice * 0.83;
        double taxPrice = totalPrice - basePrice;
        totalDiscountPrice += discountPrice;

        String totalPriceString = roundToTwoDecimalPlaces(totalPrice);
        String basePriceString = roundToTwoDecimalPlaces(basePrice);
        String taxPriceString = roundToTwoDecimalPlaces(taxPrice);
        String discountPriceString = roundToTwoDecimalPlaces(discountPrice);
        String totalDiscountPriceString = roundToTwoDecimalPlaces(totalDiscountPrice);

        totalPriceLabel.setText(totalPriceString + " KM");
        basePriceLabel.setText(basePriceString + " KM");
        taxPriceLabel.setText(taxPriceString + " KM");
        discountPriceLabel.setText(discountPriceString + " KM");

        if (extraDiscount > 0) {
            totalDiscountLabel.setEnabled(true);
            totalDiscountLabel.setVisible(true);
            totalDiscountValueLabel.setEnabled(true);
            totalDiscountValueLabel.setVisible(true);

            totalDiscountValueLabel.setText(totalDiscountPriceString + " KM");
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

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        itemNameInput = new javax.swing.JComboBox<>();
        removeItemBtn = new javax.swing.JButton();
        addItemBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        finishBtn = new javax.swing.JButton();
        barcodeInput = new javax.swing.JTextField();
        itemQuantityInput = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        currentDateLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        storeNameLabel = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 3), new java.awt.Dimension(0, 3), new java.awt.Dimension(32767, 3));
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        totalPriceLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        taxPriceLabel = new javax.swing.JLabel();
        discountPriceLabel = new javax.swing.JLabel();
        extraDiscountInput = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        totalDiscountLabel = new javax.swing.JLabel();
        totalDiscountValueLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        basePriceLabel = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Kasa");
        setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Menu Icons/users.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(1200, 900));
        setPreferredSize(new java.awt.Dimension(1200, 900));
        try {
            setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        jLabel1.setText("ID/Barkod artikla (F1)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        jLabel2.setText("Naziv artikla (F2)");
        jPanel4.add(jLabel2, new java.awt.GridBagConstraints());

        itemNameInput.setEditable(true);
        itemNameInput.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        itemNameInput.setMaximumSize(new java.awt.Dimension(250, 28));
        itemNameInput.setMinimumSize(new java.awt.Dimension(250, 28));
        itemNameInput.setPreferredSize(new java.awt.Dimension(250, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 32);
        jPanel4.add(itemNameInput, gridBagConstraints);

        removeItemBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        removeItemBtn.setText("Ukloni artikal");
        removeItemBtn.setMaximumSize(new java.awt.Dimension(132, 28));
        removeItemBtn.setMinimumSize(new java.awt.Dimension(132, 28));
        removeItemBtn.setPreferredSize(new java.awt.Dimension(132, 28));
        removeItemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeItemBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 16, 16);
        jPanel4.add(removeItemBtn, gridBagConstraints);

        addItemBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        addItemBtn.setText("Dodaj artikal");
        addItemBtn.setMaximumSize(new java.awt.Dimension(132, 28));
        addItemBtn.setMinimumSize(new java.awt.Dimension(132, 28));
        addItemBtn.setPreferredSize(new java.awt.Dimension(132, 28));
        addItemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel4.add(addItemBtn, gridBagConstraints);

        resetBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        resetBtn.setText("Poništi račun");
        resetBtn.setMaximumSize(new java.awt.Dimension(132, 28));
        resetBtn.setMinimumSize(new java.awt.Dimension(132, 28));
        resetBtn.setPreferredSize(new java.awt.Dimension(132, 28));
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 16, 16);
        jPanel4.add(resetBtn, gridBagConstraints);

        finishBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        finishBtn.setText("Završi račun");
        finishBtn.setMaximumSize(new java.awt.Dimension(132, 28));
        finishBtn.setMinimumSize(new java.awt.Dimension(132, 28));
        finishBtn.setPreferredSize(new java.awt.Dimension(132, 28));
        finishBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel4.add(finishBtn, gridBagConstraints);

        barcodeInput.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        barcodeInput.setMaximumSize(new java.awt.Dimension(250, 28));
        barcodeInput.setMinimumSize(new java.awt.Dimension(250, 28));
        barcodeInput.setPreferredSize(new java.awt.Dimension(250, 28));
        barcodeInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                barcodeInputKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 32);
        jPanel4.add(barcodeInput, gridBagConstraints);

        itemQuantityInput.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        itemQuantityInput.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        itemQuantityInput.setMaximumSize(new java.awt.Dimension(81, 28));
        itemQuantityInput.setMinimumSize(new java.awt.Dimension(81, 28));
        itemQuantityInput.setPreferredSize(new java.awt.Dimension(81, 28));
        itemQuantityInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                itemQuantityInputFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel4.add(itemQuantityInput, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        jLabel11.setText("Količina (F3)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 0, 0, 0);
        jPanel3.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        getContentPane().add(jPanel3, gridBagConstraints);

        jScrollPane1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        jTable1.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID/Barkod", "Artikal", "Količina", "Cijena", "Popust %", "Ukupna cijena"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMaxWidth(75);
            jTable1.getColumnModel().getColumn(1).setMinWidth(250);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        getContentPane().add(jScrollPane1, gridBagConstraints);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0);
        flowLayout2.setAlignOnBaseline(true);
        jPanel6.setLayout(flowLayout2);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel17.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel17.setText("Datum:");
        jLabel17.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel17.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel17.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 4, 0);
        jPanel5.add(jLabel17, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel18.setText("Operater:");
        jLabel18.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel18.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel18.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 16, 0);
        jPanel5.add(jLabel18, gridBagConstraints);

        currentDateLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        currentDateLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        currentDateLabel.setText("1. Jan 2002.");
        currentDateLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        currentDateLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        currentDateLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel5.add(currentDateLabel, gridBagConstraints);

        userNameLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        userNameLabel.setText("Suad Kucalović");
        userNameLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        userNameLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        userNameLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 16, 0);
        jPanel5.add(userNameLabel, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel25.setText("Maloprodaja:");
        jLabel25.setMaximumSize(new java.awt.Dimension(96, 28));
        jLabel25.setMinimumSize(new java.awt.Dimension(96, 28));
        jLabel25.setPreferredSize(new java.awt.Dimension(96, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(160, 16, 4, 0);
        jPanel5.add(jLabel25, gridBagConstraints);

        storeNameLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        storeNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        storeNameLabel.setText("Maloprodaja d.o.o.");
        storeNameLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        storeNameLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        storeNameLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(160, 0, 4, 0);
        jPanel5.add(storeNameLabel, gridBagConstraints);

        jPanel6.add(jPanel5);

        jPanel1.add(jPanel6);
        jPanel1.add(filler1);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0);
        flowLayout1.setAlignOnBaseline(true);
        jPanel7.setLayout(flowLayout1);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel5.setFont(new java.awt.Font("Poppins Medium", 0, 18)); // NOI18N
        jLabel5.setText("Ukupna cijena:");
        jLabel5.setMaximumSize(new java.awt.Dimension(150, 28));
        jLabel5.setMinimumSize(new java.awt.Dimension(150, 28));
        jLabel5.setPreferredSize(new java.awt.Dimension(150, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(64, 0, 4, 0);
        jPanel2.add(jLabel5, gridBagConstraints);

        totalPriceLabel.setFont(new java.awt.Font("Poppins Medium", 0, 18)); // NOI18N
        totalPriceLabel.setForeground(new java.awt.Color(0, 153, 51));
        totalPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalPriceLabel.setText("0.00 KM");
        totalPriceLabel.setMaximumSize(new java.awt.Dimension(120, 28));
        totalPriceLabel.setMinimumSize(new java.awt.Dimension(120, 28));
        totalPriceLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(64, 0, 4, 0);
        jPanel2.add(totalPriceLabel, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel6.setText("Iznos PDV:");
        jLabel6.setMaximumSize(new java.awt.Dimension(150, 28));
        jLabel6.setMinimumSize(new java.awt.Dimension(150, 28));
        jLabel6.setPreferredSize(new java.awt.Dimension(150, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel7.setText("Popust:");
        jLabel7.setMaximumSize(new java.awt.Dimension(150, 28));
        jLabel7.setMinimumSize(new java.awt.Dimension(150, 28));
        jLabel7.setPreferredSize(new java.awt.Dimension(150, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel8.setText("Dodatni popust:");
        jLabel8.setMaximumSize(new java.awt.Dimension(150, 28));
        jLabel8.setMinimumSize(new java.awt.Dimension(150, 28));
        jLabel8.setPreferredSize(new java.awt.Dimension(150, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(jLabel8, gridBagConstraints);

        taxPriceLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        taxPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        taxPriceLabel.setText("0.00 KM");
        taxPriceLabel.setMaximumSize(new java.awt.Dimension(120, 28));
        taxPriceLabel.setMinimumSize(new java.awt.Dimension(120, 28));
        taxPriceLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(taxPriceLabel, gridBagConstraints);

        discountPriceLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        discountPriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        discountPriceLabel.setText("0.00 KM");
        discountPriceLabel.setMaximumSize(new java.awt.Dimension(120, 28));
        discountPriceLabel.setMinimumSize(new java.awt.Dimension(120, 28));
        discountPriceLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(discountPriceLabel, gridBagConstraints);

        extraDiscountInput.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        extraDiscountInput.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        extraDiscountInput.setMaximumSize(new java.awt.Dimension(120, 28));
        extraDiscountInput.setMinimumSize(new java.awt.Dimension(120, 28));
        extraDiscountInput.setPreferredSize(new java.awt.Dimension(120, 28));
        extraDiscountInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                extraDiscountInputKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(extraDiscountInput, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("%");
        jLabel10.setMaximumSize(new java.awt.Dimension(28, 28));
        jLabel10.setMinimumSize(new java.awt.Dimension(28, 28));
        jLabel10.setPreferredSize(new java.awt.Dimension(28, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(jLabel10, gridBagConstraints);

        totalDiscountLabel.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        totalDiscountLabel.setText("Ukupan popust:");
        totalDiscountLabel.setMaximumSize(new java.awt.Dimension(150, 28));
        totalDiscountLabel.setMinimumSize(new java.awt.Dimension(150, 28));
        totalDiscountLabel.setPreferredSize(new java.awt.Dimension(150, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(totalDiscountLabel, gridBagConstraints);

        totalDiscountValueLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        totalDiscountValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalDiscountValueLabel.setText("0.00 KM");
        totalDiscountValueLabel.setMaximumSize(new java.awt.Dimension(120, 28));
        totalDiscountValueLabel.setMinimumSize(new java.awt.Dimension(120, 28));
        totalDiscountValueLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(totalDiscountValueLabel, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel13.setText("Osnovica:");
        jLabel13.setMaximumSize(new java.awt.Dimension(150, 28));
        jLabel13.setMinimumSize(new java.awt.Dimension(150, 28));
        jLabel13.setPreferredSize(new java.awt.Dimension(150, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(jLabel13, gridBagConstraints);

        basePriceLabel.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        basePriceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        basePriceLabel.setText("0.00 KM");
        basePriceLabel.setMaximumSize(new java.awt.Dimension(120, 28));
        basePriceLabel.setMinimumSize(new java.awt.Dimension(120, 28));
        basePriceLabel.setPreferredSize(new java.awt.Dimension(120, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 4, 0);
        jPanel2.add(basePriceLabel, gridBagConstraints);

        jPanel7.add(jPanel2);

        jPanel1.add(jPanel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 64);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void barcodeInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_barcodeInputKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (itemController.doesItemWithIdExist(barcodeInput.getText(), Integer.toString((int) itemQuantityInput.getValue()))) {
                    try {
                        populateTable(itemController.getItemById(barcodeInput.getText(), currentStoreId, Integer.toString((int) itemQuantityInput.getValue())));
                    } catch (SQLException ex) {
                        throw ex;
                    }
                } else {
                    SQLException ex = new SQLException();
                    throw ex;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Artikal nije na stanju.", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_barcodeInputKeyReleased

    private void addItemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemBtnActionPerformed
        if (barcodeInput.getText().isEmpty() && itemNameInputTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Molimo odaberite artikal.", "Greška", JOptionPane.ERROR_MESSAGE);
            readyForNextItem();
        } else if (itemNameInputTextField.getText().isEmpty()) {
            barcodeInput.dispatchEvent(new KeyEvent(barcodeInput, KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_ENTER, '\n'));
        } else {
            itemNameInputTextField.dispatchEvent(new KeyEvent(itemNameInputTextField, KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_ENTER, '\n'));
        }
    }//GEN-LAST:event_addItemBtnActionPerformed

    private void removeItemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeItemBtnActionPerformed
        if (jTable1.getSelectedRow() >= 0) {
            int confirmation = JOptionPane.showOptionDialog(
                    rootPane,
                    "Jeste li sigurni da želite ukloniti artikal?",
                    "Uklanjanje artikla",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Da", "Ne"},
                    "Da");

            if (confirmation == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                model.removeRow(jTable1.getSelectedRow());
                updatePrices();

                JOptionPane.showMessageDialog(rootPane, "Uspješno uklonjen artikal.", "Obavijest", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Molimo odaberite artikal koji želite ukloniti.", "Greška", JOptionPane.ERROR_MESSAGE);
        }

        readyForNextItem();
    }//GEN-LAST:event_removeItemBtnActionPerformed

    private void extraDiscountInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_extraDiscountInputKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int confirmation = JOptionPane.showOptionDialog(
                    rootPane,
                    "Jeste li sigurni da želite dodati dodatni popust?",
                    "Dodatni popust",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Da", "Ne"},
                    "Ne");
            if (confirmation == JOptionPane.YES_OPTION) {
                updatePrices();
                readyForNextItem();
            }
        }
    }//GEN-LAST:event_extraDiscountInputKeyReleased

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_F3) {
            itemQuantityInput.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            removeItemBtn.doClick();
        }
    }//GEN-LAST:event_jTable1KeyReleased

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        evt.consume();
    }//GEN-LAST:event_jTable1KeyPressed

    private void itemQuantityInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_itemQuantityInputFocusGained
        if (jTable1.getSelectedRowCount() > 0) {
            itemQuantityInput.setValue(jTable1.getValueAt(jTable1.getSelectedRow(), 2));
        }
    }//GEN-LAST:event_itemQuantityInputFocusGained

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        if (jTable1.getRowCount() > 0) {
            int confirmation = JOptionPane.showOptionDialog(
                    rootPane,
                    "Jeste li sigurni da želite poništiti trenutni račun?",
                    "Poništavanje računa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Da", "Ne"},
                    "Ne");

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    startup();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Greška u poništavanju računa.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            readyForNextItem();
        }
    }//GEN-LAST:event_resetBtnActionPerformed

    private void finishBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishBtnActionPerformed
        if (jTable1.getRowCount() > 0) {
            int confirmation = JOptionPane.showOptionDialog(
                    rootPane,
                    "Jeste li sigurni da želite završiti s trenutnim računom?",
                    "Završavanje računa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Da", "Ne"},
                    "Da");

            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                    Receipt currentReceipt = new Receipt();
                    currentReceiptItems.removeAll(currentReceiptItems);

                    for (int i = 0; i < jTable1.getRowCount(); i++) {
                        ReceiptItem itemOnReceipt = new ReceiptItem();

                        itemOnReceipt.setId_artikal((int) model.getValueAt(i, 0));
                        itemOnReceipt.setKolicina((int) model.getValueAt(i, 2));
                        itemOnReceipt.setPopust(Double.parseDouble((String) model.getValueAt(i, 4)));
                        itemOnReceipt.setUkupna_cijena(Double.parseDouble((String) model.getValueAt(i, 5)));

                        currentReceiptItems.add(itemOnReceipt);
                    }

                    LocalTime time = LocalTime.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                    String currentTime = time.format(format);

                    currentReceipt.setId_maloprodaja(Integer.parseInt(currentStoreId));
                    currentReceipt.setId_zaposlenik(Integer.parseInt(currentUserId));
                    currentReceipt.setItems(currentReceiptItems);
                    currentReceipt.setDatum(currentDateLabel.getText());
                    currentReceipt.setVrijeme(currentTime);
                    currentReceipt.setUkupna_cijena(Double.parseDouble(totalPriceLabel.getText().split("\\s+", 2)[0]));
                    
                    System.out.println("Ovdje je uredu");

                    if (!totalDiscountValueLabel.getText().equals("0.00 KM")) {
                        currentReceipt.setUkupni_popust(Double.parseDouble(totalDiscountValueLabel.getText().split("\\s+", 2)[0]));
                    } else {
                        currentReceipt.setUkupni_popust(Double.parseDouble(discountPriceLabel.getText().split("\\s+", 2)[0]));
                    }

                    currentReceipt.setPdv(Double.parseDouble(taxPriceLabel.getText().split("\\s+", 2)[0]));

                    if (receiptController.addNewReceipt(currentReceipt)) {
                        JOptionPane.showMessageDialog(rootPane, "Račun je uspješno završen.", "Obavijest", JOptionPane.INFORMATION_MESSAGE);
                        startup();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Greška u završavanju računa.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Račun je prazan.", "Greška", JOptionPane.ERROR_MESSAGE);
            readyForNextItem();
        }
    }//GEN-LAST:event_finishBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemBtn;
    private javax.swing.JTextField barcodeInput;
    private javax.swing.JLabel basePriceLabel;
    private javax.swing.JLabel currentDateLabel;
    private javax.swing.JLabel discountPriceLabel;
    private javax.swing.JTextField extraDiscountInput;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton finishBtn;
    private javax.swing.JComboBox<String> itemNameInput;
    private javax.swing.JSpinner itemQuantityInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton removeItemBtn;
    private javax.swing.JButton resetBtn;
    private javax.swing.JLabel storeNameLabel;
    private javax.swing.JLabel taxPriceLabel;
    private javax.swing.JLabel totalDiscountLabel;
    private javax.swing.JLabel totalDiscountValueLabel;
    private javax.swing.JLabel totalPriceLabel;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
