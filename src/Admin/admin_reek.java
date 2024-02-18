/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Admin;
import FT_menu.ScrollBar;
import G_Conection.Conection;
import TUBES_PCD.Tubes_pcd;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author mario
 */
public class admin_reek extends javax.swing.JFrame {

    /**
     * Creates new form admin_reek
     */
    private Timer timer;
    private menu_configuration m_conf;
    private Tubes_pcd pcd;
    
    public admin_reek() {
        initComponents();
        background_admin1.addBlurComponent(Panel_adma);
        background_admin1.addBlurComponent(Panel_admb);
        jScrollPane1.setVerticalScrollBar(new ScrollBar());
        jScrollPane_a.setVerticalScrollBar(new ScrollBar());
        jScrollPane_b.setVerticalScrollBar(new ScrollBar());
        LB_table.setText("");
    }
    
    private void TextPane_a() throws SQLException {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT * FROM transaksi";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int no = rs.getInt("id_transaksi");
                String nama = rs.getString("nama_makanan_or_minuman");
                String tipe = rs.getString("tipe");
                String jumlah = rs.getString("jumlah");
                int harga = rs.getInt("harga");
                int total = rs.getInt("total");
                String status_p = rs.getString("status_pembayaran");
                String metode_p = rs.getString("metode_pembayaran");
                String status = rs.getString("status");
                Date tanggal = rs.getDate("tanggal_pesanan");
                Time waktu_pesanan = rs.getTime("waktu_pesanan");
                int total_harga = rs.getInt("total_harga");

                result.append("\n No\t: ").append(no)
                        .append("\n Nama\t\t: ").append(nama)
                        .append("\n Tipe\t\t: ").append(tipe)
                        .append("\n Jumlah\t\t: ").append(jumlah)
                        .append("\n Harga\t\t: ").append(harga)
                        .append("\n Total\t\t: ").append(total)
                        .append("\n Status Pembayaran\t: ").append(status_p)
                        .append("\n Metode Pembayaran\t: ").append(metode_p)
                        .append("\n Status\t\t: ").append(status)
                        .append("\n Tanggal Pesanan\t: ").append(tanggal)
                        .append("\n Waktu Pesanan\t: ").append(waktu_pesanan)
                        .append("\n Total Harga\t\t: ").append(total_harga)
                        .append("\n");
                jTextPane_a.setText(result.toString());
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void Textpane_b() {
                StringBuilder result = new StringBuilder();
        String sql = "SELECT id_transaksi, tipe, SUM(jumlah) AS total_jumlah, harga, status_pembayaran, metode_pembayaran, status, tanggal_pesanan, waktu_pesanan, total_harga FROM transaksi GROUP BY id_transaksi";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int no = rs.getInt("id_transaksi");
                String tipe = rs.getString("tipe");
                String jumlah = rs.getString("total_jumlah");
                int harga = rs.getInt("harga");
                String status_p = rs.getString("status_pembayaran");
                String metode_p = rs.getString("metode_pembayaran");
                String status = rs.getString("status");
                Date tanggal = rs.getDate("tanggal_pesanan");
                Time waktu_pesanan = rs.getTime("waktu_pesanan");
                int total_harga = rs.getInt("total_harga");

                result.append("\n No\t: ").append(no)
                        .append("\n Tipe\t\t: ").append(tipe)
                        .append("\n Jumlah\t\t: ").append(jumlah)
                        .append("\n Harga\t\t: ").append(harga)
                        .append("\n Status Pembayaran\t: ").append(status_p)
                        .append("\n Metode Pembayaran\t: ").append(metode_p)
                        .append("\n Status\t\t: ").append(status)
                        .append("\n Tanggal Pesanan\t: ").append(tanggal)
                        .append("\n Waktu Pesanan\t: ").append(waktu_pesanan)
                        .append("\n Total Harga\t\t: ").append(total_harga)
                        .append("\n");
                jTextPane_b.setText(result.toString());
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showTable() {
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("No");
        tb.addColumn("Nama F&B  ");
        tb.addColumn("Tipe");
        tb.addColumn("Harga");
        tb.addColumn("Jumlah");
        tb.addColumn("Total");
        tb.addColumn("Pembayaran");
        tb.addColumn("Status");
        tb.addColumn("Tanggal pesanan");
        tb.addColumn("Waktu pesanan");
        tb.addColumn("Total harga");
        jTable1.setModel(tb);
        
        String sql = "SELECT * FROM transaksi";
        try (PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tb.addRow(new Object[]{
                rs.getInt("id_transaksi"),
                rs.getString("nama_makanan_or_minuman"),
                rs.getString("tipe"),
                rs.getInt("harga"),
                rs.getInt("jumlah"),
                rs.getString("total"),
                rs.getString("status_pembayaran"),
                rs.getString("status"),
                rs.getDate("tanggal_pesanan"),
                rs.getTime("waktu_pesanan"),
                rs.getInt("total_harga"),
                });
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
         
    }

    private void startSync() {
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                showTable();
                TextPane_a();
                Textpane_b();

                System.out.println("Syncron started");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        timer.start();
    }

    private void stopSync() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            System.out.println("Syncron stoped");
        }
    }
    
    private void clearTable() {
        DefaultTableModel tb = (DefaultTableModel) jTable1.getModel();
        int rowCount = tb.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tb.removeRow(i);
        }
    }
    
    private static void Report_adm() {
        try {
            String report_reek = "src/Admin/Report_adm/report_adm.jasper";        
            Connection conn = Conection.conection();                    
            Map data_j = new HashMap<>();                      
            JasperPrint print = JasperFillManager.fillReport(report_reek, data_j, conn); 
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
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

        background_admin1 = new Admin.Background_admin();
        Panel_admb = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        LB_table = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane_a = new javax.swing.JScrollPane();
        jTextPane_a = new javax.swing.JTextPane();
        jScrollPane_b = new javax.swing.JScrollPane();
        jTextPane_b = new javax.swing.JTextPane();
        jButton2 = new javax.swing.JButton();
        Panel_adma = new javax.swing.JPanel();
        square2 = new Admin.Square();
        waktu2 = new Admin.Waktu();
        button_menu_conf = new FT_menu.navbar.Button_circle();
        button_start = new FT_menu.navbar.Button_circle();
        button_stop = new FT_menu.navbar.Button_circle();
        square1 = new Admin.Square();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel_admb.setBackground(new java.awt.Color(0, 153, 153));
        Panel_admb.setOpaque(false);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setEnabled(false);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        LB_table.setFont(new java.awt.Font("Serif", 1, 20)); // NOI18N
        LB_table.setForeground(new java.awt.Color(255, 0, 0));
        LB_table.setText("jLabel2");

        jButton1.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jButton1.setText("PRINT");

        jTextPane_a.setEditable(false);
        jTextPane_a.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane_a.setViewportView(jTextPane_a);

        jTextPane_b.setEditable(false);
        jTextPane_b.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane_b.setViewportView(jTextPane_b);

        jButton2.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jButton2.setText("DELETE");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Panel_admbLayout = new javax.swing.GroupLayout(Panel_admb);
        Panel_admb.setLayout(Panel_admbLayout);
        Panel_admbLayout.setHorizontalGroup(
            Panel_admbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_admbLayout.createSequentialGroup()
                .addGroup(Panel_admbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_admbLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
                        .addGap(34, 34, 34))
                    .addGroup(Panel_admbLayout.createSequentialGroup()
                        .addGroup(Panel_admbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_admbLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addGroup(Panel_admbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(Panel_admbLayout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(LB_table, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(Panel_admbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane_a, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                    .addComponent(jScrollPane_b))
                .addGap(32, 32, 32))
        );
        Panel_admbLayout.setVerticalGroup(
            Panel_admbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_admbLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane_a, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane_b, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_admbLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(LB_table)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        Panel_adma.setBackground(new java.awt.Color(0, 153, 153));
        Panel_adma.setOpaque(false);
        Panel_adma.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        square2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        waktu2.setForeground(new java.awt.Color(255, 255, 255));
        waktu2.setFont(new java.awt.Font("Serif", 1, 22)); // NOI18N
        square2.add(waktu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 50));

        Panel_adma.add(square2, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 78, 368, 90));

        button_menu_conf.setText("MENU CONFIGURATION");
        button_menu_conf.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        button_menu_conf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_menu_confMouseClicked(evt);
            }
        });
        button_menu_conf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_menu_confActionPerformed(evt);
            }
        });
        Panel_adma.add(button_menu_conf, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 600, 270, 90));

        button_start.setForeground(new java.awt.Color(0, 51, 51));
        button_start.setText("START");
        button_start.setFont(new java.awt.Font("Serif", 1, 25)); // NOI18N
        button_start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_startMouseClicked(evt);
            }
        });
        Panel_adma.add(button_start, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 270, 90));

        button_stop.setText("STOP");
        button_stop.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        button_stop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_stopMouseClicked(evt);
            }
        });
        Panel_adma.add(button_stop, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, 270, 90));

        jButton3.setText("TUBES PCD");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout square1Layout = new javax.swing.GroupLayout(square1);
        square1.setLayout(square1Layout);
        square1Layout.setHorizontalGroup(
            square1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, square1Layout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(44, 44, 44))
        );
        square1Layout.setVerticalGroup(
            square1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, square1Layout.createSequentialGroup()
                .addContainerGap(525, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(32, 32, 32))
        );

        Panel_adma.add(square1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 360, 580));

        javax.swing.GroupLayout background_admin1Layout = new javax.swing.GroupLayout(background_admin1);
        background_admin1.setLayout(background_admin1Layout);
        background_admin1Layout.setHorizontalGroup(
            background_admin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background_admin1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(Panel_adma, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Panel_admb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(37, 37, 37))
        );
        background_admin1Layout.setVerticalGroup(
            background_admin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background_admin1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(background_admin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(background_admin1Layout.createSequentialGroup()
                        .addComponent(Panel_adma, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
                        .addGap(77, 77, 77))
                    .addGroup(background_admin1Layout.createSequentialGroup()
                        .addComponent(Panel_admb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(50, 50, 50))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background_admin1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background_admin1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button_startMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_startMouseClicked
       startSync();
        JOptionPane.showMessageDialog(this, "Selamat bekerja admin.");
        LB_table.setText("SYNCRON is Runing . . . . . ");
    }//GEN-LAST:event_button_startMouseClicked

    private void button_stopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_stopMouseClicked
        stopSync();
        LB_table.setText("");
        clearTable();
        jTextPane_a.setText("");
        jTextPane_b.setText("");
        JOptionPane.showMessageDialog(this, "Hari yang menyenangkan");
    }//GEN-LAST:event_button_stopMouseClicked

    private void button_menu_confMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_menu_confMouseClicked
        m_conf = new menu_configuration();
        this.setVisible(false);
        m_conf.showTable_food();
        m_conf.showTable_drink();
        m_conf.showTable_desert();
        m_conf.setVisible(true);
    }//GEN-LAST:event_button_menu_confMouseClicked

    private void button_menu_confActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_menu_confActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_menu_confActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        pcd = new Tubes_pcd();
        this.setVisible(false);
        pcd.setVisible(true);
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        
    }//GEN-LAST:event_jButton2MouseClicked

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
            java.util.logging.Logger.getLogger(admin_reek.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin_reek.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin_reek.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin_reek.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin_reek().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LB_table;
    private javax.swing.JPanel Panel_adma;
    private javax.swing.JPanel Panel_admb;
    private Admin.Background_admin background_admin1;
    private FT_menu.navbar.Button_circle button_menu_conf;
    private FT_menu.navbar.Button_circle button_start;
    private FT_menu.navbar.Button_circle button_stop;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane_a;
    private javax.swing.JScrollPane jScrollPane_b;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane_a;
    private javax.swing.JTextPane jTextPane_b;
    private Admin.Square square1;
    private Admin.Square square2;
    private Admin.Waktu waktu2;
    // End of variables declaration//GEN-END:variables
}
