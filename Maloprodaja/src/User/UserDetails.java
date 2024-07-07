/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package User;

import Controller.Controller;
import Store.Store;
import Store.StoreController;
import com.toedter.calendar.IDateEditor;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author Suad's Laptop
 */
public class UserDetails extends javax.swing.JInternalFrame {

    private final Controller dbController = new Controller();
    private final UserController userController = new UserController();
    private final StoreController storeController = new StoreController();
    private User userDetails = null;
    private String currentUserId = null;
    private String currentUserPrivilege = null;

    /**
     * Creates new form ChangeLoginInfo
     *
     * @param userId
     * @param currentUserPrivilege
     * @param currentUserId
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     */
    public UserDetails(String userId, String currentUserPrivilege, String currentUserId) throws SQLException, ParseException {
        initComponents();
        this.currentUserPrivilege = currentUserPrivilege;
        this.currentUserId = currentUserId;
        userController.userDetails(userId);
        this.userDetails = userController.getUser();
        startup();
        escapeKey();
    }

    private void startup() throws SQLException, ParseException {
        Calendar cal = Calendar.getInstance();

        userWorkDateInput.setMaxSelectableDate(cal.getTime());

        cal.add(Calendar.YEAR, -18);
        Date maxDate = cal.getTime();

        userBirthDateInput.setMaxSelectableDate(maxDate);

        IDateEditor dateEditor = userBirthDateInput.getDateEditor();
        if (dateEditor instanceof JTextFieldDateEditor txtFld) {
            txtFld.addPropertyChangeListener("foreground", (PropertyChangeEvent event) -> {
                if (Color.BLACK.equals(event.getNewValue())) {
                    txtFld.setForeground(new Color(230, 230, 230));
                }
            });
        }

        dateEditor = userWorkDateInput.getDateEditor();
        if (dateEditor instanceof JTextFieldDateEditor txtFld) {
            txtFld.addPropertyChangeListener("foreground", (PropertyChangeEvent event) -> {
                if (Color.BLACK.equals(event.getNewValue())) {
                    txtFld.setForeground(new Color(230, 230, 230));
                }
            });
        }

        userBirthDateInput.setDateFormatString("d. MMM. yyyy.");
        userWorkDateInput.setDateFormatString("d. MMM. yyyy.");

        int index = 0;

        switch (userDetails.getOvlasti()) {
            case "superadmin" ->
                index = 0;
            case "admin" ->
                index = 1;
            case "vlasnik" ->
                index = 2;
            case "zaposlenik" ->
                index = 3;
        }

        ArrayList<Store> stores = storeController.storesByUser(currentUserId, "");

        int i = 0;
        int storeIndex = 0;
        for (Store store : stores) {
            if (userDetails.getId_maloprodaja() == store.getId_maloprodaja()) {
                storeIndex = i;
            }
            userStoreInput.addItem(Integer.toString(store.getId_maloprodaja()) + " - " + storeController.storeName(Integer.toString(store.getId_maloprodaja())));
            i++;
        }

        userPrivilegeInput.addItem("Superadministrator");
        userPrivilegeInput.addItem("Administrator");
        userPrivilegeInput.addItem("Vlasnik");
        userPrivilegeInput.addItem("Zaposlenik");
        
        userIdLabel.setText(Integer.toString(userDetails.getId_user()));
        userNameInput.setText(userDetails.getIme());
        userSurnameInput.setText(userDetails.getPrezime());
        userUsernameInput.setText(userDetails.getUsername());
        userPrivilegeInput.setSelectedIndex(index);
        userEmailInput.setText(userDetails.getEmail());
        userPhoneNumberInput.setText(userDetails.getKontakt_broj());
        userBirthDateInput.setDate(new SimpleDateFormat("d. MMM. yyyy.").parse(userDetails.getDatum_rodjenja()));
        userWorkDateInput.setDate(new SimpleDateFormat("d. MMM. yyyy.").parse(userDetails.getDatum_zaposlenja()));
        userStoreInput.setSelectedIndex(storeIndex);
        userPayInput.setText(Double.toString(userDetails.getSatnica()));
        userPositionInput.setText(userDetails.getPozicija());
        userJobDescriptionInput.setText(userDetails.getOpis_posla());
        
        if (!"superadmin".equals(currentUserPrivilege)) {
            if (!userPrivilegeInput.getSelectedItem().equals("Superadministrator")) {
                userPrivilegeInput.removeItemAt(0);
            }
        }

        if (("zaposlenik".equals(currentUserPrivilege) && !("admin".equals(currentUserPrivilege) || "superadmin".equals(currentUserPrivilege)
                || "vlasnik".equals(currentUserPrivilege))) || (!"superadmin".equals(currentUserPrivilege) && userPrivilegeInput.getSelectedItem().equals("Superadministrator"))) {
            userNameInput.setEnabled(false);
            userSurnameInput.setEnabled(false);
            userUsernameInput.setEnabled(false);
            userPrivilegeInput.setEnabled(false);
            userEmailInput.setEnabled(false);
            userPhoneNumberInput.setEnabled(false);
            userBirthDateInput.setEnabled(false);
            userWorkDateInput.setEnabled(false);
            userStoreInput.setEnabled(false);
            userPayInput.setEnabled(false);
            paycheckPanel.setVisible(false);
            userPositionInput.setEnabled(false);
            userJobDescriptionInput.setEnabled(false);
            changeBtn.setEnabled(false);
            changeBtn.setVisible(false);
            resetPassBtn.setEnabled(false);
            resetPassBtn.setVisible(false);
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
                dispose();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "disposeForm");
        actionMap.put("disposeForm", escapeAction);
    }

    private boolean datesOkay(Date date1, Date date2) {
        Calendar minDate = new GregorianCalendar();
        minDate.setTime(date1);

        Calendar maxDate = new GregorianCalendar();
        maxDate.setTime(date2);

        // Add 18 years to the comparison date
        maxDate.add(Calendar.YEAR, -18);

        return minDate.before(maxDate);
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
        userIdLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        userNameInput = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        userSurnameInput = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        userBirthDateInput = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        userUsernameInput = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        userPrivilegeInput = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        userEmailInput = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        userPhoneNumberInput = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        cancelBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        changeBtn = new javax.swing.JButton();
        resetPassBtn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        userStoreInput = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        userWorkDateInput = new com.toedter.calendar.JDateChooser();
        paycheckPanel = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        userPayInput = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        userPositionInput = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userJobDescriptionInput = new javax.swing.JTextArea();

        setClosable(true);
        setIconifiable(true);
        setTitle("Detalji o zaposleniku");
        setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Menu Icons/info.png"))); // NOI18N
        setMaximumSize(new java.awt.Dimension(800, 520));
        setMinimumSize(new java.awt.Dimension(800, 520));
        setPreferredSize(new java.awt.Dimension(800, 520));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setMaximumSize(new java.awt.Dimension(332, 380));
        jPanel1.setMinimumSize(new java.awt.Dimension(332, 380));
        jPanel1.setPreferredSize(new java.awt.Dimension(332, 380));
        jPanel1.setLayout(new java.awt.GridLayout(8, 0));

        jPanel2.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel2.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel11.setText("ID:");
        jLabel11.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel11.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel2.add(jLabel11, new java.awt.GridBagConstraints());

        userIdLabel.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userIdLabel.setMaximumSize(new java.awt.Dimension(200, 28));
        userIdLabel.setMinimumSize(new java.awt.Dimension(200, 28));
        userIdLabel.setName(""); // NOI18N
        userIdLabel.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel2.add(userIdLabel, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel2);

        jPanel6.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel6.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel6.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel12.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel12.setText("Ime:");
        jLabel12.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel12.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel6.add(jLabel12, new java.awt.GridBagConstraints());

        userNameInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userNameInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userNameInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userNameInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel6.add(userNameInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel6);

        jPanel7.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel7.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel7.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        jLabel15.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel15.setText("Prezime:");
        jLabel15.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel15.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel15.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel7.add(jLabel15, new java.awt.GridBagConstraints());

        userSurnameInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userSurnameInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userSurnameInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userSurnameInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel7.add(userSurnameInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel7);

        jPanel10.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel10.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel10.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel10.setLayout(new java.awt.GridBagLayout());

        jLabel16.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel16.setText("Datum rođenja:");
        jLabel16.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel16.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel16.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel10.add(jLabel16, new java.awt.GridBagConstraints());

        userBirthDateInput.setForeground(new java.awt.Color(255, 255, 255));
        userBirthDateInput.setFont(new java.awt.Font("Poppins ExtraLight", 0, 12)); // NOI18N
        userBirthDateInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userBirthDateInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userBirthDateInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel10.add(userBirthDateInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel10);

        jPanel11.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel11.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel11.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel17.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel17.setText("Korisničko ime:");
        jLabel17.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel17.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel17.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel11.add(jLabel17, new java.awt.GridBagConstraints());

        userUsernameInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userUsernameInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userUsernameInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userUsernameInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel11.add(userUsernameInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel11);

        jPanel9.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel9.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel9.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel14.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel14.setText("Ovlasti:");
        jLabel14.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel14.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel14.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel9.add(jLabel14, new java.awt.GridBagConstraints());

        userPrivilegeInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userPrivilegeInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userPrivilegeInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userPrivilegeInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel9.add(userPrivilegeInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel9);

        jPanel12.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel12.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel12.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        jLabel18.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel18.setText("E-mail:");
        jLabel18.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel18.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel18.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel12.add(jLabel18, new java.awt.GridBagConstraints());

        userEmailInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userEmailInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userEmailInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userEmailInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel12.add(userEmailInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel12);

        jPanel13.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel13.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel13.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel13.setLayout(new java.awt.GridBagLayout());

        jLabel19.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel19.setText("Br. telefona:");
        jLabel19.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel19.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel19.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel13.add(jLabel19, new java.awt.GridBagConstraints());

        userPhoneNumberInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userPhoneNumberInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userPhoneNumberInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userPhoneNumberInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel13.add(userPhoneNumberInput, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 16);
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel14.setMaximumSize(new java.awt.Dimension(520, 28));
        jPanel14.setMinimumSize(new java.awt.Dimension(520, 28));
        jPanel14.setPreferredSize(new java.awt.Dimension(520, 28));
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
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 0);
        jPanel14.add(deleteBtn, gridBagConstraints);

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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 16);
        jPanel14.add(changeBtn, gridBagConstraints);

        resetPassBtn.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        resetPassBtn.setText("Resetuj");
        resetPassBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetPassBtn.setMaximumSize(new java.awt.Dimension(96, 28));
        resetPassBtn.setMinimumSize(new java.awt.Dimension(96, 28));
        resetPassBtn.setPreferredSize(new java.awt.Dimension(96, 28));
        resetPassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetPassBtnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 0, 16);
        jPanel14.add(resetPassBtn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(28, 0, 0, 0);
        getContentPane().add(jPanel14, gridBagConstraints);

        jPanel4.setMaximumSize(new java.awt.Dimension(332, 380));
        jPanel4.setMinimumSize(new java.awt.Dimension(332, 380));
        jPanel4.setPreferredSize(new java.awt.Dimension(332, 380));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        jPanel3.setMaximumSize(new java.awt.Dimension(332, 200));
        jPanel3.setMinimumSize(new java.awt.Dimension(332, 200));
        jPanel3.setPreferredSize(new java.awt.Dimension(332, 180));
        jPanel3.setLayout(new java.awt.GridLayout(4, 0));

        jPanel8.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel8.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel8.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel13.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel13.setText("Maloprodaja:");
        jLabel13.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel13.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel13.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel8.add(jLabel13, new java.awt.GridBagConstraints());

        userStoreInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userStoreInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userStoreInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userStoreInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel8.add(userStoreInput, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel8);

        jPanel15.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel15.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel15.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        jLabel20.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel20.setText("Datum zaposlenja:");
        jLabel20.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel20.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel15.add(jLabel20, new java.awt.GridBagConstraints());

        userWorkDateInput.setFont(new java.awt.Font("Poppins ExtraLight", 0, 12)); // NOI18N
        userWorkDateInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userWorkDateInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userWorkDateInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel15.add(userWorkDateInput, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel15);

        paycheckPanel.setMaximumSize(new java.awt.Dimension(250, 28));
        paycheckPanel.setMinimumSize(new java.awt.Dimension(250, 28));
        paycheckPanel.setPreferredSize(new java.awt.Dimension(250, 28));
        paycheckPanel.setLayout(new java.awt.GridBagLayout());

        jLabel23.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel23.setText("Satnica:");
        jLabel23.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel23.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel23.setPreferredSize(new java.awt.Dimension(120, 28));
        paycheckPanel.add(jLabel23, new java.awt.GridBagConstraints());

        userPayInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userPayInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userPayInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userPayInput.setPreferredSize(new java.awt.Dimension(200, 28));
        paycheckPanel.add(userPayInput, new java.awt.GridBagConstraints());

        jPanel3.add(paycheckPanel);

        jPanel16.setMaximumSize(new java.awt.Dimension(250, 28));
        jPanel16.setMinimumSize(new java.awt.Dimension(250, 28));
        jPanel16.setPreferredSize(new java.awt.Dimension(250, 28));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        jLabel21.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel21.setText("Pozicija:");
        jLabel21.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel21.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel21.setPreferredSize(new java.awt.Dimension(120, 28));
        jPanel16.add(jLabel21, new java.awt.GridBagConstraints());

        userPositionInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userPositionInput.setMaximumSize(new java.awt.Dimension(200, 28));
        userPositionInput.setMinimumSize(new java.awt.Dimension(200, 28));
        userPositionInput.setPreferredSize(new java.awt.Dimension(200, 28));
        jPanel16.add(userPositionInput, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel16);

        jPanel4.add(jPanel3);

        jPanel17.setMaximumSize(new java.awt.Dimension(250, 160));
        jPanel17.setMinimumSize(new java.awt.Dimension(250, 160));
        jPanel17.setPreferredSize(new java.awt.Dimension(250, 160));
        jPanel17.setLayout(new java.awt.GridBagLayout());

        jLabel22.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        jLabel22.setText("Opis posla:");
        jLabel22.setMaximumSize(new java.awt.Dimension(120, 28));
        jLabel22.setMinimumSize(new java.awt.Dimension(120, 28));
        jLabel22.setPreferredSize(new java.awt.Dimension(120, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel17.add(jLabel22, gridBagConstraints);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(200, 175));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 175));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 175));

        userJobDescriptionInput.setColumns(14);
        userJobDescriptionInput.setFont(new java.awt.Font("Poppins Light", 0, 12)); // NOI18N
        userJobDescriptionInput.setLineWrap(true);
        userJobDescriptionInput.setRows(5);
        userJobDescriptionInput.setTabSize(4);
        userJobDescriptionInput.setWrapStyleWord(true);
        userJobDescriptionInput.setMaximumSize(new java.awt.Dimension(200, 175));
        userJobDescriptionInput.setMinimumSize(new java.awt.Dimension(200, 175));
        userJobDescriptionInput.setPreferredSize(new java.awt.Dimension(200, 173));
        jScrollPane1.setViewportView(userJobDescriptionInput);

        jPanel17.add(jScrollPane1, new java.awt.GridBagConstraints());

        jPanel4.add(jPanel17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 0, 0);
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        if (currentUserPrivilege.equals("superadmin") || currentUserPrivilege.equals("admin") || currentUserPrivilege.equals("vlasnik")) {
            int confirmation = JOptionPane.showOptionDialog(
                    rootPane,
                    "Jeste li sigurni da želite obrisati zaposlenika iz baze podataka?",
                    "Brisanje zaposlenika",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Da", "Ne"},
                    "Ne");

            if (confirmation == JOptionPane.YES_OPTION) {
                String sqlQuery = "DELETE FROM Zaposlenik WHERE id_zaposlenik = '" + userIdLabel.getText() + "'";

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
        } else {
            JOptionPane.showMessageDialog(rootPane, "Samo ovlašteni korisnici mogu koristiti ovu opciju.", "Neovlaštena opcija", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void changeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeBtnActionPerformed
        if (currentUserPrivilege.equals("superadmin") || currentUserPrivilege.equals("admin") || currentUserPrivilege.equals("vlasnik")) {
            String newStoreId = (String) userStoreInput.getSelectedItem();

            String userPrivilege = null;

            switch (userPrivilegeInput.getSelectedIndex()) {
                case 0 ->
                    userPrivilege = "superadmin";
                case 1 ->
                    userPrivilege = "admin";
                case 2 ->
                    userPrivilege = "vlasnik";
                case 3 ->
                    userPrivilege = "zaposlenik";
            }

            if (!userNameInput.getText().equals(userDetails.getIme())
                    || !userSurnameInput.getText().equals(userDetails.getPrezime())
                    || !userUsernameInput.getText().equals(userDetails.getUsername())
                    || !userEmailInput.getText().equals(userDetails.getEmail())
                    || !userPhoneNumberInput.getText().equals(userDetails.getKontakt_broj())
                    || !userDetails.getDatum_rodjenja().equals(new SimpleDateFormat("d. MMM. yyyy.").format(userBirthDateInput.getDate()))
                    || !userDetails.getDatum_zaposlenja().equals(new SimpleDateFormat("d. MMM. yyyy.").format(userWorkDateInput.getDate()))
                    || Double.parseDouble(userPayInput.getText()) != userDetails.getSatnica()
                    || !userPositionInput.getText().equals(userDetails.getPozicija())
                    || !userJobDescriptionInput.getText().equals(userDetails.getOpis_posla())
                    || !Integer.toString(userDetails.getId_maloprodaja()).equals(newStoreId.substring(0, newStoreId.indexOf(" - ")))
                    || !userDetails.getOvlasti().equals(userPrivilege)) {

                if (datesOkay(userBirthDateInput.getDate(), userWorkDateInput.getDate())) {
                    String sqlQuery = "UPDATE Zaposlenik SET "
                            + "id_maloprodaja = '" + newStoreId.substring(0, newStoreId.indexOf(" - ")) + "', "
                            + "ime = '" + userNameInput.getText() + "', "
                            + "prezime = '" + userSurnameInput.getText() + "', "
                            + "datum_rodjenja = '" + new SimpleDateFormat("d. MMM. yyyy.").format(userBirthDateInput.getDate()) + "', "
                            + "datum_zaposlenja = '" + new SimpleDateFormat("d. MMM. yyyy.").format(userWorkDateInput.getDate()) + "', "
                            + "email = '" + userEmailInput.getText() + "', "
                            + "kontakt_broj = '" + userPhoneNumberInput.getText() + "', "
                            + "username = '" + userUsernameInput.getText() + "', "
                            + "ovlasti = '" + userPrivilege + "', "
                            + "pozicija = '" + userPositionInput.getText() + "', "
                            + "opis_posla = '" + userJobDescriptionInput.getText() + "', "
                            + "satnica = '" + userPayInput.getText() + "' "
                            + "WHERE id_zaposlenik = '" + userIdLabel.getText() + "'";

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
                    JOptionPane.showMessageDialog(rootPane, "Datum zaposlenja ne može biti prije 18. godine zaposlenika.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Vrijednosti su nepromijenjene.", "Informacija", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Samo ovlašteni korisnici mogu koristiti ovu opciju.", "Neovlaštena opcija", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_changeBtnActionPerformed

    private void resetPassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPassBtnActionPerformed
        if (currentUserPrivilege.equals("superadmin") || currentUserPrivilege.equals("admin") || currentUserPrivilege.equals("vlasnik")) {
            int confirmation = JOptionPane.showOptionDialog(
                    rootPane,
                    "Jeste li sigurni da želite resetovati lozinku zaposlenika iz baze podataka??",
                    "Resetovanje lozinke",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Da", "Ne"}, // Custom button labels
                    "Ne");

            if (confirmation == JOptionPane.YES_OPTION) {
                String newUserPassword = userController.randomPassword();

                String sqlQuery = "UPDATE Zaposlenik SET password = '" + newUserPassword + "' WHERE id_zaposlenik = '" + userIdLabel.getText() + "'";

                try {
                    if (dbController.InsDelUpd(sqlQuery)) {
                        JPanel messagePanel = new JPanel();

                        messagePanel.add(new JLabel("Lozinka uspješno resetovana... Nova lozinka je: "));
                        messagePanel.add(new JTextField(newUserPassword));

                        JOptionPane.showMessageDialog(rootPane, messagePanel);
                    } else {
                        SQLException ex = new SQLException();
                        throw ex;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Greška tokom ažuriranja podataka.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Samo ovlašteni korisnici mogu koristiti ovu opciju.", "Neovlaštena opcija", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_resetPassBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton changeBtn;
    private javax.swing.JButton deleteBtn;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel paycheckPanel;
    private javax.swing.JButton resetPassBtn;
    private com.toedter.calendar.JDateChooser userBirthDateInput;
    private javax.swing.JTextField userEmailInput;
    private javax.swing.JLabel userIdLabel;
    private javax.swing.JTextArea userJobDescriptionInput;
    private javax.swing.JTextField userNameInput;
    private javax.swing.JTextField userPayInput;
    private javax.swing.JTextField userPhoneNumberInput;
    private javax.swing.JTextField userPositionInput;
    private javax.swing.JComboBox<String> userPrivilegeInput;
    private javax.swing.JComboBox<String> userStoreInput;
    private javax.swing.JTextField userSurnameInput;
    private javax.swing.JTextField userUsernameInput;
    private com.toedter.calendar.JDateChooser userWorkDateInput;
    // End of variables declaration//GEN-END:variables
}
