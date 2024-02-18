/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Admin;

import FT_menu.ScrollBar;
import G_Conection.Conection;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

/**
 *
 * @author mario
 */
public class menu_configuration extends javax.swing.JFrame {

    /**
     * Creates new form menu_configuration
     */
    
    private admin_reek adm_r;
    private DocumentListener myDoc;
    JFileChooser chooser = new JFileChooser();
    private BufferedImage originalImage;
    File filenya;
    
    public menu_configuration() {
        initComponents();
        control_text();
        background_mnu_conf.addBlurComponent(jPanel1);
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        jScrollPane2.setVerticalScrollBar(new ScrollBar());
        jScrollPane3.setVerticalScrollBar(new ScrollBar());
        jScrollPane4.setVerticalScrollBar(new ScrollBar());
        jScrollPane4.setHorizontalScrollBarPolicy(jScrollPane4.HORIZONTAL_SCROLLBAR_NEVER);
        Panel_update.setVisible(false);
        panel_white_update.setVisible(false);
        Panel_deleted.setVisible(false);
        panel_w_dleted.setVisible(false);
    }
    
    int admin_id = 0;
    int harga = 0;
    int waktu_msak = 0;
    String Tipe;
    String nama_mkanan;
    String deskripsi;
    String path;
    int admin_id_updt;
    String tipe_updt;
    String kolom_updt;
    String value_updt;
    int id_updt;
    int id_dlete;
    String Tipe_dlete;
    

    
    private void saveImage(BufferedImage image, String path) {  /// for adding pict /// for adding pict /// for adding pict
        try {
            File ouputfile = new File(path);
            ImageIO.write(image, "png", ouputfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showTable_food() {
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("No");
        tb.addColumn("Nama Makanan  ");
        tb.addColumn("Tipe");
        tb.addColumn("Deskripsi");
        tb.addColumn("Harga");
        tb.addColumn("Waktu masak");
        tb.addColumn("Path gambar");
        Table_food.setModel(tb);

        String sql = "SELECT * FROM food_inventory";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tb.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("tipe"),
                    rs.getString("deskripsi"),
                    rs.getInt("harga"),
                    rs.getString("waktu_masak"),
                    rs.getString("gambar"),});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void showImage(BufferedImage image, JLabel label) {
        Image dimg = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        label.setIcon(imageIcon);
    }
    
    public void showTable_drink() {
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("No");
        tb.addColumn("Nama Makanan  ");
        tb.addColumn("Tipe");
        tb.addColumn("Deskripsi");
        tb.addColumn("Harga");
        tb.addColumn("Waktu masak");
        tb.addColumn("Path gambar");
        Table_drink.setModel(tb);

        String sql = "SELECT * FROM drink_inventory";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tb.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("tipe"),
                    rs.getString("deskripsi"),
                    rs.getInt("harga"),
                    rs.getString("waktu_masak"),
                    rs.getString("gambar"),});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void showTable_desert() {
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("No");
        tb.addColumn("Nama Makanan  ");
        tb.addColumn("Tipe");
        tb.addColumn("Deskripsi");
        tb.addColumn("Harga");
        tb.addColumn("Waktu masak");
        tb.addColumn("Path gambar");
        Table_desert.setModel(tb);

        String sql = "SELECT * FROM desert_inventory";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tb.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("tipe"),
                    rs.getString("deskripsi"),
                    rs.getInt("harga"),
                    rs.getString("waktu_masak"),
                    rs.getString("gambar"),});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void control_text() {
        StyledDocument doc = TextPane_deskripsi.getStyledDocument();
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkLengthAndInsertNewline(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });
        doc.addDocumentListener(myDoc);
    }

    private void checkLengthAndInsertNewline(DocumentEvent e) {
        int maxLength = 25;
        Document doc = e.getDocument();
        int length = doc.getLength();

        if (length == maxLength) {
            //doc.removeDocumentListener(myDoc);

            // Masukkan newline
            SwingUtilities.invokeLater(() -> {
                try {
                    doc.insertString(length, "\n", null);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                } finally {
                    doc.addDocumentListener(myDoc);
                }
            });
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background_mnu_conf = new Admin.Background_admin();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_food = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_desert = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_drink = new javax.swing.JTable();
        square_white1 = new Admin.Square_white();
        button_refresh = new javax.swing.JButton();
        button_view = new FT_menu.navbar.Button_circle();
        button_cancel = new FT_menu.navbar.Button_circle();
        button_add = new javax.swing.JButton();
        button_update = new javax.swing.JButton();
        button_deleted = new javax.swing.JButton();
        button_circle_black1 = new FT_menu.navbar.Button_circle_black();
        button_circle_black2 = new FT_menu.navbar.Button_circle_black();
        button_search = new javax.swing.JButton();
        Panel_add = new Admin.Square();
        panel_add_w = new Admin.Square_white_more();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ComboBox_tipe = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TextPane_deskripsi = new javax.swing.JTextPane();
        button_upload = new javax.swing.JButton();
        LB_Preview = new javax.swing.JLabel();
        txtWaktu_msak = new javax.swing.JTextField();
        button_TAMBAH_DATA = new javax.swing.JButton();
        txtHarga = new javax.swing.JTextField();
        txtAdmin_id = new javax.swing.JTextField();
        txtPath = new javax.swing.JTextField();
        txtNama_mkanan1 = new javax.swing.JTextField();
        Panel_update = new Admin.Square();
        panel_white_update = new Admin.Square_white_more();
        ComboBox_tpe_update = new javax.swing.JComboBox<>();
        Button_preview_updt = new javax.swing.JButton();
        txt_adminID_update = new javax.swing.JTextField();
        txtID_update = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ComboBox_klom_update = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtValue_update = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Button_update = new javax.swing.JButton();
        txtPreview = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        Panel_deleted = new Admin.Square();
        panel_w_dleted = new Admin.Square_white();
        jButton1 = new javax.swing.JButton();
        txtID_dlete = new javax.swing.JTextField();
        ComboBox_dlete = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1650, 1020));

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));
        jPanel1.setForeground(new java.awt.Color(0, 153, 204));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Table_food.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Table_food);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 63, 491, 600));

        Table_desert.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(Table_desert);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1064, 63, 503, 600));

        Table_drink.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(Table_drink);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(541, 63, 503, 600));

        square_white1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button_refresh.setText("REFRESH");
        button_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_refreshMouseClicked(evt);
            }
        });
        square_white1.add(button_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 158, 210, -1));

        button_view.setText("VIEW");
        button_view.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        button_view.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_viewMouseClicked(evt);
            }
        });
        square_white1.add(button_view, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 110, 40));

        button_cancel.setForeground(new java.awt.Color(0, 51, 51));
        button_cancel.setText("CANCEL");
        button_cancel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        button_cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_cancelMouseClicked(evt);
            }
        });
        square_white1.add(button_cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 110, 40));

        button_add.setText("ADD");
        button_add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_addMouseClicked(evt);
            }
        });
        square_white1.add(button_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 210, -1));

        button_update.setText("UPDATE");
        button_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_updateMouseClicked(evt);
            }
        });
        square_white1.add(button_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 45, 210, -1));

        button_deleted.setText("DELETE");
        button_deleted.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_deleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_deletedMouseClicked(evt);
            }
        });
        square_white1.add(button_deleted, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 82, 210, -1));
        square_white1.add(button_circle_black1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 110, 60));
        square_white1.add(button_circle_black2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 110, 60));

        button_search.setText("SEARCH");
        button_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        square_white1.add(button_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 210, -1));

        jPanel1.add(square_white1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 691, 420, 190));

        Panel_add.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_add_w.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 1, 15)); // NOI18N
        jLabel1.setText("Tipe");
        panel_add_w.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));

        jLabel2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel2.setText("Admin ID");
        panel_add_w.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel3.setText("Nama Makanan");
        panel_add_w.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel4.setText("Deskripsi");
        panel_add_w.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 70, -1));

        jLabel5.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel5.setText("Harga");
        panel_add_w.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, -1, -1));

        ComboBox_tipe.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ComboBox_tipe.setForeground(new java.awt.Color(0, 51, 51));
        ComboBox_tipe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Drink", "Desert" }));
        ComboBox_tipe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_add_w.add(ComboBox_tipe, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 200, -1));

        jLabel6.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel6.setText("Waktu Masak");
        panel_add_w.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 30, -1, -1));

        jScrollPane4.setViewportView(TextPane_deskripsi);

        panel_add_w.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 230, 100));

        button_upload.setBackground(new java.awt.Color(204, 255, 255));
        button_upload.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        button_upload.setText("Upload Gambar");
        button_upload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_upload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_uploadMouseClicked(evt);
            }
        });
        button_upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_uploadActionPerformed(evt);
            }
        });
        panel_add_w.add(button_upload, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 140, 120, -1));

        LB_Preview.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Preview", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10))); // NOI18N
        panel_add_w.add(LB_Preview, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 40, 150, 130));
        panel_add_w.add(txtWaktu_msak, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 30, 50, -1));

        button_TAMBAH_DATA.setBackground(new java.awt.Color(0, 153, 255));
        button_TAMBAH_DATA.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        button_TAMBAH_DATA.setText("TAMBAHKAN DATA");
        button_TAMBAH_DATA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_TAMBAH_DATA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_TAMBAH_DATAMouseClicked(evt);
            }
        });
        panel_add_w.add(button_TAMBAH_DATA, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, 150, -1));
        panel_add_w.add(txtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 120, -1));

        txtAdmin_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdmin_idActionPerformed(evt);
            }
        });
        panel_add_w.add(txtAdmin_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 200, -1));

        txtPath.setEditable(false);
        panel_add_w.add(txtPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 100, 120, -1));

        txtNama_mkanan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNama_mkanan1ActionPerformed(evt);
            }
        });
        panel_add_w.add(txtNama_mkanan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 200, -1));

        Panel_add.add(panel_add_w, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1000, 180));

        jPanel1.add(Panel_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 681, 1020, 200));

        panel_white_update.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ComboBox_tpe_update.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        ComboBox_tpe_update.setForeground(new java.awt.Color(0, 51, 51));
        ComboBox_tpe_update.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Drink", "Desert" }));
        ComboBox_tpe_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_white_update.add(ComboBox_tpe_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 179, -1));

        Button_preview_updt.setBackground(new java.awt.Color(204, 255, 0));
        Button_preview_updt.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        Button_preview_updt.setText("PREVIEW");
        Button_preview_updt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Button_preview_updt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_preview_updtMouseClicked(evt);
            }
        });
        panel_white_update.add(Button_preview_updt, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 170, -1));
        panel_white_update.add(txt_adminID_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 70, -1));
        panel_white_update.add(txtID_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 180, -1));

        jLabel8.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel8.setText("ID");
        panel_white_update.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, -1, -1));

        jLabel9.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel9.setText("Kolom");
        panel_white_update.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, -1, -1));

        jLabel10.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel10.setText("Value");
        panel_white_update.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, -1, -1));

        ComboBox_klom_update.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        ComboBox_klom_update.setForeground(new java.awt.Color(0, 51, 51));
        ComboBox_klom_update.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "nama", "tipe", "deskripsi", "harga", "waktu_masak", "gambar" }));
        ComboBox_klom_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_white_update.add(ComboBox_klom_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 180, -1));

        jLabel11.setFont(new java.awt.Font("Serif", 1, 19)); // NOI18N
        jLabel11.setText("Check");
        panel_white_update.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));
        panel_white_update.add(txtValue_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 180, -1));

        jLabel12.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel12.setText("Tipe");
        panel_white_update.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, -1, -1));

        Button_update.setBackground(new java.awt.Color(51, 153, 0));
        Button_update.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        Button_update.setText("UPDATE");
        Button_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Button_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_updateMouseClicked(evt);
            }
        });
        panel_white_update.add(Button_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, 170, -1));

        txtPreview.setEditable(false);
        txtPreview.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        panel_white_update.add(txtPreview, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 630, 40));

        jLabel13.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel13.setText("Admin ID");
        panel_white_update.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        javax.swing.GroupLayout Panel_updateLayout = new javax.swing.GroupLayout(Panel_update);
        Panel_update.setLayout(Panel_updateLayout);
        Panel_updateLayout.setHorizontalGroup(
            Panel_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_updateLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(panel_white_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_updateLayout.setVerticalGroup(
            Panel_updateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_updateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_white_update, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(Panel_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(521, 681, 990, -1));

        Panel_deleted.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_w_dleted.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(255, 102, 102));
        jButton1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton1.setText("DELETE");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        panel_w_dleted.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 140, -1));

        txtID_dlete.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        txtID_dlete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtID_dleteActionPerformed(evt);
            }
        });
        panel_w_dleted.add(txtID_dlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 120, 28));

        ComboBox_dlete.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        ComboBox_dlete.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Food", "Drink", "Desert" }));
        ComboBox_dlete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panel_w_dleted.add(ComboBox_dlete, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 200, -1));

        jLabel7.setFont(new java.awt.Font("Serif", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID");
        panel_w_dleted.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 30, 20));

        jLabel14.setFont(new java.awt.Font("Serif", 1, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tipe");
        panel_w_dleted.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 50, 30, 20));

        Panel_deleted.add(panel_w_dleted, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 850, 160));

        jPanel1.add(Panel_deleted, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 690, 870, 180));

        javax.swing.GroupLayout background_mnu_confLayout = new javax.swing.GroupLayout(background_mnu_conf);
        background_mnu_conf.setLayout(background_mnu_confLayout);
        background_mnu_confLayout.setHorizontalGroup(
            background_mnu_confLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background_mnu_confLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );
        background_mnu_confLayout.setVerticalGroup(
            background_mnu_confLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background_mnu_confLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background_mnu_conf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background_mnu_conf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button_viewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_viewMouseClicked
        /*        showTable_food();    //Test
        showTable_drink();
        showTable_desert();*/
    }//GEN-LAST:event_button_viewMouseClicked

    private void button_cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_cancelMouseClicked
        adm_r = new admin_reek();
        this.setVisible(false);
        adm_r.setVisible(true);
    }//GEN-LAST:event_button_cancelMouseClicked

    private void button_uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_uploadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_uploadActionPerformed

    private void button_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_addMouseClicked
        Panel_add.setVisible(true);
        panel_add_w.setVisible(true);
        Panel_update.setSize(0, 200);
        panel_white_update.setSize(0, 178);
        Panel_update.setVisible(false);
        panel_white_update.setVisible(false);
        Panel_deleted.setVisible(false);
        panel_w_dleted.setVisible(false);
        Panel_add.setSize(1020, 210);
        panel_add_w.setSize(1000, 190);
    }//GEN-LAST:event_button_addMouseClicked

    private void button_uploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_uploadMouseClicked

        if (!txtAdmin_id.getText().isEmpty() && !txtAdmin_id.getText().isEmpty() && !TextPane_deskripsi.getText().isEmpty() && !txtWaktu_msak.getText().isEmpty()) {
            try {
                admin_id = Integer.parseInt(txtAdmin_id.getText());
                harga = Integer.parseInt(txtHarga.getText());
                waktu_msak = Integer.parseInt(txtWaktu_msak.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Data Admin ID, Harga, Waktu Masak harus menggunakan angka", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Tipe = (String) ComboBox_tipe.getSelectedItem();
            nama_mkanan = txtNama_mkanan1.getText();
            deskripsi = TextPane_deskripsi.getText();
            System.out.println(Tipe);       // Test
        } else {
            JOptionPane.showMessageDialog(this, "Data tidak boleh kosong", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if ("Food".equals(Tipe)) {
            int buka_dialog = chooser.showOpenDialog(menu_configuration.this);
            if (buka_dialog == JFileChooser.APPROVE_OPTION) {
                filenya = chooser.getSelectedFile();
                try {
                    String projectDirectory = System.getProperty("user.dir");   /// for adding pict

                    originalImage = ImageIO.read(new File(filenya.getPath()));

                    String nfile = filenya.getName();   /// for adding pict
                    String paths = projectDirectory + "/src/FT_menu/image/" + nfile;    /// for adding pict // bisa di custom path
                    saveImage(originalImage, paths);    /// for adding pict
                    filenya = new File(paths);

                    txtPath.setText("/FT_menu/image/" + nfile);
                    showImage(originalImage, LB_Preview);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if ("Drink".equals(Tipe)) {
            int buka_dialog = chooser.showOpenDialog(menu_configuration.this);
            if (buka_dialog == JFileChooser.APPROVE_OPTION) {
                filenya = chooser.getSelectedFile();
                try {
                    String projectDirectory = System.getProperty("user.dir");   /// for adding pict

                    originalImage = ImageIO.read(new File(filenya.getPath()));

                    String nfile = filenya.getName();   /// for adding pict
                    String paths = projectDirectory + "/src/FT_menu/image/drink/" + nfile;      /// for adding pict // bisa di custom path
                    saveImage(originalImage, paths);    /// for adding pict
                    filenya = new File(paths);

                    txtPath.setText("/FT_menu/image/drink" + nfile);
                    showImage(originalImage, LB_Preview);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if ("Desert".equals(Tipe)) {
            int buka_dialog = chooser.showOpenDialog(menu_configuration.this);
            if (buka_dialog == JFileChooser.APPROVE_OPTION) {
                filenya = chooser.getSelectedFile();
                try {
                    String projectDirectory = System.getProperty("user.dir");   /// for adding pict

                    originalImage = ImageIO.read(new File(filenya.getPath()));

                    String nfile = filenya.getName();   /// for adding pict
                    String paths = projectDirectory + "/src/FT_menu/image/desert/" + nfile;      /// for adding pict // bisa di custom path
                    saveImage(originalImage, paths);    /// for adding pict
                    filenya = new File(paths);

                    txtPath.setText("/FT_menu/image/desert" + nfile);
                    showImage(originalImage, LB_Preview);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        path = txtPath.getText();
       // System.out.println(path);   //test
    }//GEN-LAST:event_button_uploadMouseClicked

    private void button_TAMBAH_DATAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_TAMBAH_DATAMouseClicked
       
        if (!txtAdmin_id.getText().isEmpty() && !txtAdmin_id.getText().isEmpty() && !TextPane_deskripsi.getText().isEmpty() && !txtWaktu_msak.getText().isEmpty()) {
            if ("Food".equals(Tipe)) {
                String sql = "INSERT INTO food_inventory (admin_id, nama, tipe, deskripsi, harga, waktu_masak, gambar) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
                    ps.setInt(1, admin_id);
                    ps.setString(2, nama_mkanan);
                    ps.setString(3, Tipe);
                    ps.setString(4, deskripsi);
                    ps.setDouble(5, harga);
                    ps.setInt(6, waktu_msak);
                    ps.setString(7, path);
                    ps.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if ("Drink".equals(Tipe)) {
                String sql = "INSERT INTO drink_inventory (admin_id, nama, tipe, deskripsi, harga, waktu_masak, gambar) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
                    ps.setInt(1, admin_id);
                    ps.setString(2, nama_mkanan);
                    ps.setString(3, Tipe);
                    ps.setString(4, deskripsi);
                    ps.setDouble(5, harga);
                    ps.setInt(6, waktu_msak);
                    ps.setString(7, path);
                    ps.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if ("Desert".equals(Tipe)) {
                String sql = "INSERT INTO desert_inventory (admin_id, nama, tipe, deskripsi, harga, waktu_masak, gambar) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
                    ps.setInt(1, admin_id);
                    ps.setString(2, nama_mkanan);
                    ps.setString(3, Tipe);
                    ps.setString(4, deskripsi);
                    ps.setDouble(5, harga);
                    ps.setInt(6, waktu_msak);
                    ps.setString(7, path);
                    ps.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lengkapi data terlebih dahulu... :)", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        txtAdmin_id.setText("");
        txtHarga.setText("");
        txtNama_mkanan1.setText("");
        txtWaktu_msak.setText("");
        txtPath.setText("");
        TextPane_deskripsi.setText("");
        txtAdmin_id.requestFocus();
        JOptionPane.showMessageDialog(this, "Penambahan Data Berhasil.");
    }//GEN-LAST:event_button_TAMBAH_DATAMouseClicked

    private void button_deletedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_deletedMouseClicked
        Panel_deleted.setVisible(true);
        panel_w_dleted.setVisible(true);
        Panel_add.setSize(0, 210);
        panel_add_w.setSize(0, 200);
        Panel_update.setSize(0, 200);
        panel_white_update.setSize(0,178);
        Panel_add.setVisible(false);
        panel_add_w.setVisible(false);
        Panel_update.setVisible(false);
        panel_white_update.setVisible(false);
        Panel_deleted.setSize(870, 180);
        panel_w_dleted.setSize(850, 160);
    }//GEN-LAST:event_button_deletedMouseClicked

    private void txtAdmin_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdmin_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdmin_idActionPerformed

    private void txtNama_mkanan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNama_mkanan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNama_mkanan1ActionPerformed

    private void Button_preview_updtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_preview_updtMouseClicked
        if (!txt_adminID_update.getText().isEmpty() && !txtID_update.getText().isEmpty()) {
            try {
                admin_id_updt = Integer.parseInt(txt_adminID_update.getText());
                id_updt = Integer.parseInt(txtID_update.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Data Admin ID dan ID menggunakan angka", "Information", JOptionPane.INFORMATION_MESSAGE);
                txtID_update.setText("");
                txt_adminID_update.setText("");
                return;
            }
            value_updt = txtValue_update.getText();
            tipe_updt = (String) ComboBox_tpe_update.getSelectedItem();
            kolom_updt = (String) ComboBox_klom_update.getSelectedItem();
        } else {
            JOptionPane.showMessageDialog(this, "Lengkapi data terlebih dahulu.... :)", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String tableName = ""; 
        if ("Food".equals(tipe_updt)) {
            tableName = "food_inventory";
        } else if ("Drink".equals(tipe_updt)) {
            tableName = "drink_inventory";
        } else if ("Desert".equals(tipe_updt)) {
            tableName = "desert_inventory";
        }
   
        String sql = "SELECT " + kolom_updt + " FROM " + tableName + " WHERE ID = ?";
        try ( PreparedStatement ps = Conection.conection().prepareCall(sql)) {
            ps.setInt(1, id_updt);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nilaiKolom = rs.getString(1);
                    txtPreview.setText(kolom_updt + " : " + nilaiKolom);
                } else {
                    JOptionPane.showMessageDialog(this, "Data tidak ditemukan", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_Button_preview_updtMouseClicked

    private void Button_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_updateMouseClicked
        if (!txt_adminID_update.getText().isEmpty() && !txtValue_update.getText().isEmpty() && !txtID_update.getText().isEmpty()) {
            try {
                admin_id_updt = Integer.parseInt(txt_adminID_update.getText());
                id_updt = Integer.parseInt(txtID_update.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Data Admin ID dan ID menggunakan angka", "Information", JOptionPane.INFORMATION_MESSAGE);
                txtID_update.setText("");
                txt_adminID_update.setText("");
                return;
            }
            value_updt = txtValue_update.getText();
            tipe_updt = (String) ComboBox_tpe_update.getSelectedItem();
            kolom_updt = (String) ComboBox_klom_update.getSelectedItem();
        } else {
            JOptionPane.showMessageDialog(this, "Lengkapi data terlebih dahulu.... :)", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String tableName = "";
        if ("Food".equals(tipe_updt)) {
            tableName = "food_inventory";
        } else if ("Drink".equals(tipe_updt)) {
            tableName = "drink_inventory";
        } else if ("Deser".equals(tipe_updt)) {
            tableName = "desert_inventory";
        }

        String sql = "UPDATE " + tableName + " SET " + kolom_updt + " = ? WHERE ID = ?";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ps.setString(1, value_updt);
            ps.setInt(2, id_updt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        txt_adminID_update.setText("");
        txtID_update.setText("");
        txtPreview.setText("");
        txtValue_update.setText("");
        txt_adminID_update.requestFocus();
        JOptionPane.showMessageDialog(this, "Update Data Berhasil.");
    }//GEN-LAST:event_Button_updateMouseClicked

    private void button_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_updateMouseClicked
        Panel_update.setVisible(true);
        panel_white_update.setVisible(true);
        Panel_add.setSize(0, 210);
        panel_add_w.setSize(0, 200);
        Panel_add.setVisible(false);
        panel_add_w.setVisible(false);
        Panel_deleted.setVisible(false);
        panel_w_dleted.setVisible(false);
        Panel_update.setSize(1018, 200);
        panel_white_update.setSize(978, 178);
    }//GEN-LAST:event_button_updateMouseClicked

    private void button_refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_refreshMouseClicked
        showTable_food();
        showTable_drink();
        showTable_desert();
    }//GEN-LAST:event_button_refreshMouseClicked

    private void txtID_dleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtID_dleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtID_dleteActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        if (!txtID_dlete.getText().isEmpty()) {
            id_dlete = Integer.parseInt(txtID_dlete.getText());
            Tipe_dlete = (String) ComboBox_dlete.getSelectedItem();
        } else {
            JOptionPane.showMessageDialog(this, "Lengkapi Data terlebih dahulu.");
            return;
        }
        
        String tableName = "";
        if ("Food".equals(Tipe_dlete)) {
            tableName = "food_inventory";
        } else if ("Drink".equals(Tipe_dlete)) {
            tableName = "drink_inventory";
        } else if ("Desert".equals(Tipe_dlete)) {
            tableName = "desert_inventory";
        }
        
        String sql = "DELETE FROM " + tableName + " WHERE ID = ?";
        try (PreparedStatement ps = Conection.conection().prepareStatement(sql)) {  
            ps.setInt(1, id_dlete);
            JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus menu dengan ID "+ id_dlete + " ?");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        String sql1 = "ALTER TABLE " + tableName + " AUTO_INCREMENT = ?";
        try (PreparedStatement ps1 = Conection.conection().prepareStatement(sql1)){
            ps1.setInt(1, id_dlete - 1);
            ps1.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        txtID_dlete.setText("");
        JOptionPane.showMessageDialog(this, "Penghapusan Data Berhasil.");
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menu_configuration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu_configuration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu_configuration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu_configuration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu_configuration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_preview_updt;
    private javax.swing.JButton Button_update;
    private javax.swing.JComboBox<String> ComboBox_dlete;
    private javax.swing.JComboBox<String> ComboBox_klom_update;
    private javax.swing.JComboBox<String> ComboBox_tipe;
    private javax.swing.JComboBox<String> ComboBox_tpe_update;
    private javax.swing.JLabel LB_Preview;
    private Admin.Square Panel_add;
    private Admin.Square Panel_deleted;
    private Admin.Square Panel_update;
    private javax.swing.JTable Table_desert;
    private javax.swing.JTable Table_drink;
    private javax.swing.JTable Table_food;
    private javax.swing.JTextPane TextPane_deskripsi;
    private Admin.Background_admin background_mnu_conf;
    private javax.swing.JButton button_TAMBAH_DATA;
    private javax.swing.JButton button_add;
    private FT_menu.navbar.Button_circle button_cancel;
    private FT_menu.navbar.Button_circle_black button_circle_black1;
    private FT_menu.navbar.Button_circle_black button_circle_black2;
    private javax.swing.JButton button_deleted;
    private javax.swing.JButton button_refresh;
    private javax.swing.JButton button_search;
    private javax.swing.JButton button_update;
    private javax.swing.JButton button_upload;
    private FT_menu.navbar.Button_circle button_view;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private Admin.Square_white_more panel_add_w;
    private Admin.Square_white panel_w_dleted;
    private Admin.Square_white_more panel_white_update;
    private Admin.Square_white square_white1;
    private javax.swing.JTextField txtAdmin_id;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtID_dlete;
    private javax.swing.JTextField txtID_update;
    private javax.swing.JTextField txtNama_mkanan1;
    private javax.swing.JTextField txtPath;
    private javax.swing.JTextField txtPreview;
    private javax.swing.JTextField txtValue_update;
    private javax.swing.JTextField txtWaktu_msak;
    private javax.swing.JTextField txt_adminID_update;
    // End of variables declaration//GEN-END:variables
}
