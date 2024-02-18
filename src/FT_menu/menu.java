/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FT_menu;

import FT_menu.checkout.Checkout_home;
import FT_menu.desert.Desert_data;
import FT_menu.desert.Desert_home;
import FT_menu.desert.Event_desert;
import FT_menu.drink.Drink_data;
import FT_menu.drink.Drink_home;
import FT_menu.drink.Event_drink;
import FT_menu.food.Event_food;
import FT_menu.food.Food_data;
import FT_menu.food.Food_home;
import FT_menu.home.Home;
import G_Conection.Conection;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

/**
 *
 * @author mario
 */
public class menu extends javax.swing.JFrame {

    /**
     * Creates new form menu
     */
    
    private Home home;
    private Food_home home_f;
    private Drink_home home_d;
    private Desert_home home_s;
    private Checkout_home home_c;
    private Animator animator;
    private Point animatePoint;
    private Food_data itemSelected_f;
    private Drink_data itemSelected_d;
    private Desert_data itemSelected_s;
    Map<Food_data, Integer> data_f = new HashMap<>();
    Map<Drink_data, Integer> data_d = new HashMap<>();
    Map<Desert_data, Integer> data_s = new HashMap<>();
    private JButton button_add_f;
    private JButton button_add_d;
    private JButton button_add_s;
    private JButton button_del_f;
    private JButton button_del_d;
    private JButton button_del_s;
    private JButton button_cancel_c;
    private JButton button_stay_c;
    private JButton button_deliv_c;
    private JButton button_cashless_c;
    private JButton button_menu_list;
    private JButton button_cash;
    private JButton button_cashless;
    private JTextPane checkout_pane;
    private JPanel onSite_b;
    private JPanel quote_c;
    private JPanel deliv_a;
    private JPanel deliv_b;
    private JPanel quote_c1;
    private JLabel steak_menu_list;
    private int totalHarga = 0;
    private int id_transaksi = 1;
    int waktu_masak = 0;
    
    
    public menu() throws SQLException {
        try {
            initComponents();
            checkout();
            drink();
            desert();
            food();
            home();
            jScrollPane_note.setVerticalScrollBar(new ScrollBar());
            sp_item.setValue(1);
        } catch (Exception ex) {
            Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void remove(Component components) {
            if(mainPanel.isAncestorOf(components)) {
                mainPanel.remove(components);
            }
    }
    
    private void home() {
        home = new Home();
        button_menu_list = home.getButton_menu_list();
        steak_menu_list = home.getSteak_home_list();
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home);
        button_menu_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(home);
                mainPanel.setLayout(new BorderLayout());
                mainPanel.add(sp_item);
                mainPanel.add(jLabel_home);
                mainPanel.add(navbar_home);
                navbar_panel.setSize(0,0);
                mainPanel.add(button_navbar);
                mainPanel.add(navbar_panel);
                mainPanel.add(jScrollPane1);
                mainPanel.add(jScrollPane_note);
                mainPanel.add(jScrollPane_Total);
                mainPanel.add(txtcheckout);
                mainPanel.add(jLabel_checkout);
                mainPanel.add(jLabel_c_c);
                mainPanel.add(button_checkout);
                mainPanel.add(button_checkout1);
                mainPanel.add(home_f);
                mainPanel.revalidate();
                mainPanel.repaint();
                JOptionPane.showMessageDialog(mainPanel, "Untuk Menambah jumlah menu makanan, pastikan untuk\nmenambah jumlah spinner juga ya...:)", "Information",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        steak_menu_list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button_menu_list.doClick();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
    }
    
    //  ======== ABOUT FOOD ========
    
    private void food() {
        home_f = new Food_home();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home_f);
        food_Data();
        animator = PropertySetter.createAnimator(500, mainPanel, "imageLocation", animatePoint, mainPanel.getTargetLocation());
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);        
        button_add_f = home_f.getButton_add_f();
        button_add_f.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             //   System.err.println("klick"); // test respon
            try { 
                int spinnerValue = (int) sp_item.getValue();
                Food_data selectedFood = getItemSelected_f();
                    if (selectedFood != null) {
                        int currentPrice = selectedFood.getPrice_f();
                        if (data_f.containsKey(selectedFood)) {
                            int storedSpinnerValue = data_f.get(selectedFood);
                            updateExistingMenu_f(note, selectedFood, data_f.get(selectedFood), spinnerValue);
                            totalHarga += currentPrice * spinnerValue;
                            totalHarga -= currentPrice * storedSpinnerValue;
                        } else {
                            addNewMenu_f(note, selectedFood, spinnerValue);  
                            temp_database_f(selectedFood, spinnerValue, id_transaksi, waktu_masak);      // database add ====================
                            waktu_masaks();
                            // curent location db update its clash db add
                            totalHarga += currentPrice * spinnerValue;
                        }
                        Total.setText(String.valueOf("		Total : Rp " + totalHarga));
                        data_f.put(selectedFood, spinnerValue);
                        updt_database_f(selectedFood, data_f.get(selectedFood), spinnerValue);    // bug fixed DB update
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        button_del_f = home_f.getButton_del_f();
        button_del_f.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMenu_n(note, data_f, data_d, data_s);
                deleteMenu_t(Total, data_f, data_d, data_s);
                totalHarga = 0;
                sp_item.setValue(1);
                try {
                    dlete_temp_database();
                } catch (SQLException ex) {
                    Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    } 
    
    private void food_Data() {
        home_f.setEvent (new Event_food() {
            @Override
            public void itemClick (Component com, Food_data item_f) {
            //    System.out.println(item_f.getItemID_f()); // Using this sysout to print ID
                if (itemSelected_f != item_f) {
                    if (!animator.isRunning()) {
                        itemSelected_f = item_f;    // for return get
                        animatePoint = new Point(500,-200);
                        mainPanel.setImage(item_f.getImage_f());
                        mainPanel.setImageLocation(animatePoint);
                        mainPanel.setImageSize(new Dimension(180, 120));
                        mainPanel.repaint();
                        home_f.setSelected(com);
                        home_f.showItem(item_f);
                        animator.start();
                        handleSpinnerValue_f(item_f);   //function to handle spinner value with hashmap
                    }
                }
            }
        });
        /*int ID = 1;
        for (int i=0; i<1; i++) {
        home_f.addItem(new Food_data(ID++, " Nasi Goreng biasa", "Nasi goreng tanpa toping yang disajikan dengan telur orak arik.", 25000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img1.png"))));
        home_f.addItem(new Food_data(ID++, " Nasi Goreng ayam", "Nasi goreng yang digoreng dengan bumbu khas dan dicampur dengan potongan paha ayam.", 28000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img2.png"))));
        home_f.addItem(new Food_data(ID++, " Kwetiau Spesial\t", "Kwetiau yang digoreng dengan bumbu-bumbu khusus dan dicampur dengan daging ayam, udang, sayuran, dan telur.", 35000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img3.png"))));
        home_f.addItem(new Food_data(ID++, " Mie Goreng Biasa", "Mie yang digoreng dengan bumbu sederhana seperti bawang, kecap, dan rempah-rempah bercampur dengan telur orak arik", 25000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img4.png"))));
        home_f.addItem(new Food_data(ID++, " Mie Goreng Spesial", "Mie yang digoreng dengan campuran sedikit kaldu ayam dan disajikan dengan daging ayam, daging, udang, dan telur.", 35000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img5.png"))));
        home_f.addItem(new Food_data(ID++, " Tomehawk Steak", "Steak daging yang potongannya diambil dari tulang rusuk primer dan memiliki serat daging yang lezat. Disajikan utuh bersama saus beurre maitre d'resto.", 70000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img6.png"))));
        home_f.addItem(new Food_data(ID++, " Cumi Saus Mentega", "Cumi saus mentega adalah olahan cumi yang dimasak dengan saus mentega, bawang putih, saus tiram, dan rempah-rempah.", 40000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img7.png"))));
        home_f.addItem(new Food_data(ID++, " Udang Goreng Saus Mentega", "Udang goreng saus mentega, hidangan menggugah selera yang terdiri dari udang segar yang dimasak dengan saus mentega lezat.", 35000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img8.png"))));
        home_f.addItem(new Food_data(ID++, " Lobster Mentega", "Lobster kukus mentega, hidangan istimewa yang menggabungkan kelezatan daging lobster dengan kekayaan rasa saus mentega yang lembut.", 60000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img9.png"))));
        home_f.addItem(new Food_data(ID++, " Udang kukus med", "Masakan udang yang memukau, dimasak secara hati-hati dalam proses kukus. Menggunakan udang ukuran sedang dipilih untuk memastikan kekenyalan dan kelembutan dagingnya.", 30000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img10.png"))));
        home_f.addItem(new Food_data(ID++, " Tahu Saus Tiram", "Masakan tahu yang memadukan kelembutan tahu dengan cita rasa kaya dan gurih dari saus tiram.", 25000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img11.png"))));
        home_f.addItem(new Food_data(ID++, " Cah Kangkung\t", "Hidangan klasik yang memadukan kesegaran kangkung hijau dengan cita rasa istimewa untuk membawa penglaman bernostalgia.", 15000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img12.png"))));
        home_f.addItem(new Food_data(ID++, " Fu Yung Hai\t", " Fu yung hai, hidangan yang terdiri dari telur orak arik dan digoreng bersama daging ayam, udang, sayuran yang disajikan dengan saus asam manis dan sambal sebagai perpaduan cita rasa.", 36000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img13.png"))));
        home_f.addItem(new Food_data(ID++, " Sapo Tahu\t", " Tahu yang digoreng dengan tekstur renyah dan disajikan dengan saus pedas sebagai perpaduan rasa.", 32000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img14.png"))));
        home_f.addItem(new Food_data(ID++, " Ayam Cah Jamur", "Terbuat dari potongan ayam yang digoreng dengan bumbu rempah-rempah, kemudian ditumis dengan jamur segar.", 25000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img15.png"))));
        home_f.addItem(new Food_data(ID++, " Ayam Lada Hitam", "Ayam Lada Hitam merupakan perpaduan ayam yang gurih menggunakan lada hitam. Rasanya yang manis dan gurih bercampur pedas dari lada hitam menambah kenikmatan saat disantap panas-panas.", 35000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img16.png"))));
        home_f.addItem(new Food_data(ID++, " Spaghetti Carbonara", "Spaghetti Carbonara merupakan makanan khas Italia berupa spaghetti yang dimasak dengan telur, keju, dan daging. Makanan ini mempunyai cita rasa yang kaya, creamy, dan gurih dari sausnya.", 30000, "Food", new ImageIcon(getClass().getResource("/FT_menu/image/img17.png"))));
        home_f.addItem(new Food_data(ID++, " Nasi putih", "Nasi hangat yang ditanak dari beras premium", 5000, "\tFood", new ImageIcon(getClass().getResource("/FT_menu/image/img18.png"))));
        }*/        
        
        String sql = "SELECT * FROM food_inventory";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("id");
                String namaFood = rs.getString("nama");
                String deskripsi = rs.getString("deskripsi");
                int harga = rs.getInt("harga");
                String tipe = rs.getString("tipe");
                waktu_masak = rs.getInt("waktu_masak");
                //String imagePath = "/FT_menu/image/img" + ID + ".png";    // for inserting db path
                String gambar = rs.getString("gambar");
                String newText = namaFood.replaceAll("\\bNasi putih\\b", " Nasi putih\t");      // bug fixed
                
                
                home_f.addItem(new Food_data(ID, newText, deskripsi, harga, tipe, new ImageIcon(getClass().getResource(gambar))));
                
                /*String sql1 = "UPDATE food_inventory SET gambar = ? WHERE id = ?";
                try ( PreparedStatement ps1 = Conection.conection().prepareStatement(sql1)) {
                ps1.setString(1, imagePath);
                ps1.setInt(2, ID);
                ps1.executeUpdate();
                } catch (SQLException ex) {
                ex.printStackTrace();
                }*/
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void handleSpinnerValue_f(Food_data selectedFood) {
        if (data_f.containsKey(selectedFood)) {
            sp_item.setValue(data_f.get(selectedFood));
        } else {
            sp_item.setValue(1);
        }
    }
    
    public Food_data getItemSelected_f() {
        return itemSelected_f; // returning value for klick item food
    }
    
    private static void addNewMenu_f(JTextPane note, Food_data selectedFood, int spinnerValue) {  // Constructor for add item food from Food_data value to note(JTextPane)
        String item_note_f = selectedFood.getItemName_f() + "\t" + selectedFood.getBrandName_f() + "\tRp " + selectedFood.getPrice_f() + "  x" + spinnerValue + "\n";
        appendToNote(note, item_note_f);
    }
    
    private static void updateExistingMenu_f(JTextPane note, Food_data selectedFood, int oldSpinnerValue, int newSpinnerValue) {
        StyledDocument doc = note.getStyledDocument();
        int length = doc.getLength();
        String content = null;                
        try {
            content = doc.getText(0, length);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        int index = content.indexOf(selectedFood.getItemName_f());
        if (index != -1) {
            String matchingContent = content.substring(index, content.indexOf("\n", index)); // prep
            String updateContent = matchingContent.replaceAll("x" + oldSpinnerValue, "x" + newSpinnerValue);
            replaceTextInNote(note, matchingContent, updateContent);        
        }    
    } 

    private static void temp_database_f(Food_data selectedFood, int spinnerValue, int id_transaksi, int waktu_masak) throws SQLException { // DB to inserting, but deleted and update's coming
        try {
            String sql = "INSERT INTO food (id_transaksi, id_food, nama_makanan, tipe, deskripsi, harga, jumlah, total, waktu_masak) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" // bug clash on db
                            + "ON DUPLICATE KEY UPDATE id_food = VALUES(id_food), nama_makanan = VALUES(nama_makanan), tipe = VALUES(tipe), "
                            + "harga = VALUES(harga), jumlah = VALUES(jumlah), total = VALUES(total)";
            try (PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
                ps.setInt(1, id_transaksi);
                ps.setInt(2, selectedFood.getItemID_f());
                ps.setString(3, selectedFood.getItemName_f());
                ps.setString(4, selectedFood.getBrandName_f());
                ps.setString(5, selectedFood.getDescription_f());
                ps.setDouble(6, selectedFood.getPrice_f());
                ps.setInt(7, spinnerValue);
                ps.setDouble(8, selectedFood.getPrice_f() * spinnerValue);
                ps.setInt(9, waktu_masak);
                ps.execute();
                } 
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void updt_database_f(Food_data selectedFood, int storedSpinnerValue, int newSpinnerValue) {
        try {
            String sql = "UPDATE food SET jumlah = ?, total = ? WHERE id_food = ?";
            try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
                int newTotal = selectedFood.getPrice_f() * newSpinnerValue;

                ps.setInt(1, newSpinnerValue);
                ps.setDouble(2, newTotal);
                ps.setInt(3, selectedFood.getItemID_f());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int waktu_masaks() {
        int totalWaktuMasak = 0;
        String sql = "SELECT SUM(waktu_masak) AS total_waktu_masak FROM food";
        try (PreparedStatement ps = Conection.conection().prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                totalWaktuMasak = rs.getInt("total_waktu_masak");
                System.out.println("Total Waktu Masak: " + totalWaktuMasak);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalWaktuMasak;
    }


    //  ======== ABOUT FOOD ========
    
    //  ======== ABOUT DRINK ========
    
    private void drink() {
        home_d = new Drink_home();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home_d);
        drink_data();
        animator = PropertySetter.createAnimator(500, mainPanel, "imageLocation", animatePoint, mainPanel.getTargetLocation());
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        button_add_d = home_d.getButton_add_d();
        button_add_d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int spinnerValue = (int) sp_item.getValue();
                try {
                    Drink_data selectedDrink = getItemSelected_d();
                    if (selectedDrink != null) {
                        int currentPrice = selectedDrink.getPrice_d();
                        if (data_d.containsKey(selectedDrink)) {
                            int storedSpinnerValue = data_d.get(selectedDrink);
                            updateExistingMenu_d(note, selectedDrink, data_d.get(selectedDrink), spinnerValue);
                            totalHarga += currentPrice * spinnerValue;
                            totalHarga -= currentPrice * storedSpinnerValue;
                        } else {
                            addNewMenu_d(note, selectedDrink, spinnerValue);
                            temp_database_d(selectedDrink, spinnerValue, id_transaksi, waktu_masak);
                            waktu_masak_d();
                            totalHarga += currentPrice * spinnerValue;
                        }
                        Total.setText(String.valueOf("		Total : Rp " + totalHarga));
                        data_d.put(selectedDrink, spinnerValue);
                        updt_database_d(selectedDrink, data_d.get(selectedDrink), spinnerValue);    // bug
                    }
                } catch ( SQLException ex) {
                    ex.printStackTrace();
                }
                
            }
        });
        button_del_d = home_d.getButton_del_d();
        button_del_d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMenu_n(note, data_f, data_d, data_s);
                deleteMenu_t(Total, data_f, data_d, data_s);
                totalHarga = 0;
                sp_item.setValue(1);
                try {
                    dlete_temp_database();
                } catch (SQLException ex) {
                    Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void drink_data() {
        home_d.setEvent(new Event_drink() {
            @Override
            public void itemClick(Component com, Drink_data item_d) {
            //    System.out.println(item_d.getItemID_d()); // Using this sysout to print ID
                if(itemSelected_d != item_d) {
                    if(!animator.isRunning()) {
                        itemSelected_d = item_d;
                        animatePoint = new Point(500,-200);
                        mainPanel.setImage(item_d.getImage_d());
                        mainPanel.setImageLocation(animatePoint);
                        mainPanel.setImageSize(new Dimension(180, 120));
                        mainPanel.repaint();
                        home_d.setSelected(com);
                        home_d.showItem(item_d);
                        animator.start();
                        handleSpinnerValue_d(item_d);
                    }
                }
            }
        });
        /*int ID = 1;
        for (int i=0; i<1; i++) {
        home_d.addItem(new Drink_data(ID++, "Cosmopolitan cy", "koktail yang terdiri dari vodka, triple sec, cranberry juice, dan perasan jeruk lemon. Terkenal dengan warna merah kekuningan cerahnya.", 25000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img1.png"))));
        home_d.addItem(new Drink_data(ID++, "Manhattan\t", "Koktail klasik yang terbuat dari whiskey, vermouth manis, dan pahit. Biasanya dihidangkan dengan seiris lemon sebagai hiasan.", 30000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img2.png"))));
        home_d.addItem(new Drink_data(ID++, "So beach\t", "Minuman segar dan eksotis dengan campuran sari buah tropis, melibatkan alkohol rum.", 28000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img3.png"))));
        home_d.addItem(new Drink_data(ID++, "Bloody mary\t", "Koktail yang dibuat dengan vodka, jus tomat. Dihidangkan dengan es dan hiasan selada, seleri,", 22000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img4.png"))));
        home_d.addItem(new Drink_data(ID++, "Sea breeze\t", "Koktail berbasis vodka dengan campuran cranberry dan sari buah anggur. Memberikan kesegaran dan rasa asam.", 35000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img5.png"))));
        home_d.addItem(new Drink_data(ID++, "Tea\t", "Teh hangat yang dibuat dengan rasa manis sesuai selera.", 10000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img6.png"))));
        home_d.addItem(new Drink_data(ID++, "Green tea sey\t", "Varian dari teh hijau dengan tambahan mint.", 30000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img7.png"))));
        home_d.addItem(new Drink_data(ID++, "Lemon drink\t", "Minuman yang dibuat dengan perasan lemon, air, dan gula. Segar dan asam.", 20000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img8.png"))));
        home_d.addItem(new Drink_data(ID++, "Mango juice\t", "Jus buah mangga. Yang dibuat dengan mangga premium.", 22000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img9.png"))));
        home_d.addItem(new Drink_data(ID++, "Strawbery ext\t", " Koktail yang menggunakan sari stroberi sebagai bahan utama dengan sedikit tambahan rum", 23000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img10.png"))));
        home_d.addItem(new Drink_data(ID++, "Grape juice\t", "Jus buah anggur yang menyajikan rasa manis alami anggur.", 35000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img11.png"))));
        home_d.addItem(new Drink_data(ID++, "Kiwi ext\t", "Minuman yang terbuat dari sari buah kiwi sebagai bahan utama yang disajikan dengan es segar.", 25000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img12.png"))));
        home_d.addItem(new Drink_data(ID++, "Leicy sreene\t", "Minuman yang dibuat dari sari buah leci, yang memiliki rasa manis dan segar.", 38000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img13.png"))));
        home_d.addItem(new Drink_data(ID++, "Cultural sawki\t", "Campuran sari buah kiwi dan setrawbery dengan sedikit perasan lemon yang disajikan dengan es segar", 40000, "Drink", new ImageIcon(getClass().getResource("/FT_menu/image/drink/img14.png"))));
        }*/
        
        String sql = "SELECT * FROM drink_inventory";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("id");
                String namaFood = rs.getString("nama");
                String deskripsi = rs.getString("deskripsi");
                int harga = rs.getInt("harga");
                String tipe = rs.getString("tipe");
                waktu_masak = rs.getInt("waktu_masak");
                //String imagePath = "/FT_menu/image/drink/img" + ID + ".png";    // for inserting db path
                String gambar = rs.getString("gambar");

                home_d.addItem(new Drink_data(ID, namaFood, deskripsi, harga, tipe, new ImageIcon(getClass().getResource(gambar))));

                /*          String sql1 = "UPDATE drink_inventory SET gambar = ? WHERE id = ?";
                try ( PreparedStatement ps1 = Conection.conection().prepareStatement(sql1)) {
                ps1.setString(1, imagePath);
                ps1.setInt(2, ID);
                ps1.executeUpdate();
                } catch (SQLException ex) {
                ex.printStackTrace();
                }*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private void handleSpinnerValue_d(Drink_data selectedDrink) {
        if (data_d.containsKey(selectedDrink)) {
            sp_item.setValue(data_d.get(selectedDrink));
        } else {
            sp_item.setValue(1);
        }
    }
    
    public Drink_data getItemSelected_d() {
        return itemSelected_d;  // returning value for klick item drink
    }
    
    private static void addNewMenu_d(JTextPane note, Drink_data selectedDrink, int spinnerValue) {
        String item_note_d = " "+selectedDrink.getItemName_d() + "\t" + selectedDrink.getBrandName_d() + "\tRp " + selectedDrink.getPrice_d() + "  x" + spinnerValue + "\n";
        appendToNote(note, item_note_d);
    }
    
    private static void updateExistingMenu_d(JTextPane note, Drink_data selectedDrink, int oldSpinnerValue, int newSpinnerValue) {    // Constructor for add item drink from Drink_data value to note(JTextPane)
        StyledDocument doc = note.getStyledDocument();
        int length = doc.getLength();
        String content = null;
        try {
            content = doc.getText(0, length);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        int index = content.indexOf(selectedDrink.getItemName_d());
        if (index != -1) {
            String matchingContent = content.substring(index, content.indexOf("\n", index));    //caring bug here, its need 2 param
            String updateContent = matchingContent.replaceAll("x" + oldSpinnerValue, "x" + newSpinnerValue);
            replaceTextInNote(note, matchingContent, updateContent);
        }
    }
    
    private static void temp_database_d(Drink_data selectedDrink, int spinnerValue, int id_transaksi, int waktu_masak) throws SQLException {
        try {
            String sql = "INSERT INTO drink (id_transaksi, id_drink, nama_minuman, tipe, deskripsi, harga, jumlah, total, waktu_masak) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" // bug clash on db
                    + "ON DUPLICATE KEY UPDATE id_drink = VALUES(id_drink), nama_minuman = VALUES(nama_minuman), tipe = VALUES(tipe), "
                    + "harga = VALUES(harga), jumlah = VALUES(jumlah), total = VALUES(total)";
            try (PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
                ps.setInt(1, id_transaksi);
                ps.setInt(2, selectedDrink.getItemID_d());
                ps.setString(3, selectedDrink.getItemName_d());
                ps.setString(4, selectedDrink.getBrandName_d());
                ps.setString(5, selectedDrink.getDescription_d());
                ps.setDouble(6, selectedDrink.getPrice_d());
                ps.setInt(7, spinnerValue);
                ps.setDouble(8, selectedDrink.getPrice_d() * spinnerValue);
                ps.setInt(9, waktu_masak);
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void updt_database_d(Drink_data selectedDrink, int storedSpinnerValue, int newSpinnerValue) throws SQLException {
        try {
            String sql = "UPDATE drink SET jumlah = ?, total = ? WHERE id_drink = ?";
            try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
                int newTotal = selectedDrink.getPrice_d()* newSpinnerValue;

                ps.setInt(1, newSpinnerValue);
                ps.setDouble(2, newTotal);
                ps.setInt(3, selectedDrink.getItemID_d());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private int waktu_masak_d() {
        int totalWaktuMasak = 0;
        String sql = "SELECT SUM(waktu_masak) AS total_waktu_masak FROM drink";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                totalWaktuMasak = rs.getInt("total_waktu_masak");
                System.out.println("Total Waktu Masak: " + totalWaktuMasak);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalWaktuMasak;
    }
    
    //  ======== ABOUT DRINK ========
    
    // ========= ABOUT DESERT =========
    
        private void desert() {
        home_s = new Desert_home();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home_s);
        desert_data();
        animator = PropertySetter.createAnimator(500, mainPanel, "imageLocation", animatePoint, mainPanel.getTargetLocation());
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        button_add_s = home_s.getButton_add_s();
        button_add_s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int spinnerValue = (int) sp_item.getValue();
                try {
                    Desert_data selectedDesert = getItemSelected_s();
                    if (selectedDesert != null) {
                        int currentPrice = selectedDesert.getPrice_s();
                        if (data_s.containsKey(selectedDesert)) {
                            int storedSpinnerValue = data_s.get(selectedDesert);
                            updateExistingMenu_s(note, selectedDesert, data_s.get(selectedDesert), spinnerValue);
                            totalHarga += currentPrice * spinnerValue;
                            totalHarga -= currentPrice * storedSpinnerValue;
                        } else {
                            addNewMenu_s(note, selectedDesert, spinnerValue);
                            temp_database_s(selectedDesert, spinnerValue, id_transaksi, waktu_masak);
                            waktu_masak_s();
                            totalHarga += currentPrice * spinnerValue;
                        }
                        Total.setText(String.valueOf("		Total : Rp " + totalHarga));
                        data_s.put(selectedDesert, spinnerValue);
                        updt_database_s(selectedDesert, data_s.get(selectedDesert), spinnerValue);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        button_del_s = home_s.getButton_del_s();
        button_del_s.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMenu_n(note, data_f, data_d, data_s);
                deleteMenu_t(Total, data_f, data_d, data_s);
                totalHarga = 0;
                sp_item.setValue(1);
                try {
                    dlete_temp_database();
                } catch (SQLException ex) {
                    Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    
    private void desert_data() {
        home_s.setEvent(new Event_desert() {
            @Override
            public void itemClick(Component com, Desert_data item_s) {
            //    System.out.println(item_d.getItemID_d()); // Using this sysout to print ID
                if(itemSelected_s != item_s) {
                    if(!animator.isRunning()) {
                        itemSelected_s = item_s;
                        animatePoint = new Point(500,-200);
                        mainPanel.setImage(item_s.getImage_s());
                        mainPanel.setImageLocation(animatePoint);
                        mainPanel.setImageSize(new Dimension(180, 120));
                        mainPanel.repaint();
                        home_s.setSelected(com);
                        home_s.showItem(item_s);
                        animator.start();
                        handleSpinnerValue_s(item_s);
                    }
                }
            }
        });
        /*int ID = 1;
        for (int i=0; i<1; i++) {
        home_s.addItem(new Desert_data(ID++, "Cheesecake Blueberry", "Cheesecake ini memiliki perpaduan rasa yang unik antara creamy cheesecake dan asam manis blueberry.", 25000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img1.png"))));
        home_s.addItem(new Desert_data(ID++, "Chocolate Cake", "Chocolate Cake memiliki rasa yang manis dan pahit yang khas, serta tekstur yang lembut dan moist.", 23000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img2.png"))));
        home_s.addItem(new Desert_data(ID++, "Souffle Strawberry", "Souffle ini memiliki tekstur yang lembut dan mengembang, dengan rasa stroberi yang manis dan asam.", 24000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img3.png"))));
        home_s.addItem(new Desert_data(ID++, "Cheesecake Caramel", "Cheesecake caramel adalah varian cheesecake yang terbuat dari bahan-bahan dasar cheesecake biasa, seperti cream cheese, telur, gula, dan vanilla, namun ditambah dengan karamel.", 25000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img4.png"))));
        home_s.addItem(new Desert_data(ID++, "Cheesecake Butter", "Cheesecake Butter adalah varian cheesecake yang terbuat dari bahan-bahan dasar cheesecake biasa, seperti cream cheese, telur, gula, dan vanilla, namun ditambah dengan mentega.", 26000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img5.png"))));
        home_s.addItem(new Desert_data(ID++, "Cheesecake Chocolate", "Cheesecake Chocolate memiliki rasa yang manis dan creamy, dengan aroma dan rasa cokelat yang kuat.", 25000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img6.png"))));
        home_s.addItem(new Desert_data(ID++, "Vanilla Ice Cream", "Vanilla Ice Cream terbuat dari bahan-bahan dasar seperti susu, krim, gula, dan ekstrak vanila.", 18000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img7.png"))));
        home_s.addItem(new Desert_data(ID++, "Salted Caramel Ice Cream", "Salted Caramel Ice Cream adalah jenis es krim yang terbuat dari campuran susu, krim, gula, garam, dan karamel. Es krim ini memiliki rasa yang manis dan gurih, dengan rasa karamel yang kuat dan sedikit rasa asin.", 28000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img8.png"))));
        home_s.addItem(new Desert_data(ID++, "Biscuit pudding", "Biscuit pudding adalah hidangan penutup yang terbuat dari biskuit, susu, gula, dan agar-agar. Puding ini memiliki tekstur yang lembut dan creamy, dengan rasa yang manis dan gurih.", 18000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img9.png"))));
        home_s.addItem(new Desert_data(ID++, "Eszterhazy Torta", "Desert yang satu ini disajikan dengan almond meringue dan garis buttercream cokelat. Teksturnya yang lumer dan rasanya yang manis, membuat eszterhazy torta menjadi desert favorit di musim dingin.", 32000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img10.png"))));
        home_s.addItem(new Desert_data(ID++, "Heave Ship Gelato", "Perpaduann manisnya chocolate gelato, snow cream berry dan beignets membuat siapapun yang menikmatinya merasakan kenikamatan tiada tara.", 42000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img11.png"))));
        home_s.addItem(new Desert_data(ID++, "Chocolate Waffle", "Chocolate Waffle adalah varian waffle yang diperkaya dengan bubuk kakao atau cokelat, memberikan cita rasa dan aroma khas cokelat yang melekat. ", 24000, "Desert", new ImageIcon(getClass().getResource("/FT_menu/image/desert/img12.png"))));
        }*/

        String sql = "SELECT * FROM desert_inventory";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ID = rs.getInt("id");
                String namaFood = rs.getString("nama");
                String deskripsi = rs.getString("deskripsi");
                int harga = rs.getInt("harga");
                String tipe = rs.getString("tipe");
                waktu_masak = rs.getInt("waktu_masak");
                //String imagePath = "/FT_menu/image/desert/img" + ID + ".png";    // for inserting db path
                String gambar = rs.getString("gambar");

                home_s.addItem(new Desert_data(ID, namaFood, deskripsi, harga, tipe, new ImageIcon(getClass().getResource(gambar))));

                /*                String sql1 = "UPDATE desert_inventory SET gambar = ? WHERE id = ?";
                try ( PreparedStatement ps1 = Conection.conection().prepareStatement(sql1)) {
                ps1.setString(1, imagePath);
                ps1.setInt(2, ID);
                ps1.executeUpdate();
                } catch (SQLException ex) {
                ex.printStackTrace();
                }*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    private void handleSpinnerValue_s(Desert_data selectedDesert) {
        if (data_s.containsKey(selectedDesert)) {
            sp_item.setValue(data_s.get(selectedDesert));
        } else {
            sp_item.setValue(1);
        }
    }
    
    public Desert_data getItemSelected_s() {
        return itemSelected_s;
    }
    
    private static void addNewMenu_s(JTextPane note, Desert_data selectedDesert, int spinnerValue) {
        String item_note_s = " " + selectedDesert.getItemName_s() + "\t" + selectedDesert.getBrandName_s() + "\tRp " + selectedDesert.getPrice_s() + "  x" + spinnerValue + "\n";
        appendToNote(note, item_note_s);
    }
    
    private static void updateExistingMenu_s(JTextPane note, Desert_data selectedDesert, int oldSpinnerValue, int newSpinnerValue) {    // Constructor for add item drink from Drink_data value to note(JTextPane)
        StyledDocument doc = note.getStyledDocument();
        int length = doc.getLength();
        String content = null;
        try {
            content = doc.getText(0, length);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        int index = content.indexOf(selectedDesert.getItemName_s());
        if (index != -1) {
            String matchingContent = content.substring(index, content.indexOf("\n", index));    //caring bug here, its need 2 param
            String updateContent = matchingContent.replaceAll("x" + oldSpinnerValue, "x" + newSpinnerValue);
            replaceTextInNote(note, matchingContent, updateContent);
        }
        
    }
    
    private static void temp_database_s(Desert_data selectedDesert, int spinnerValue, int id_transaksi, int waktu_masak) {
        String sql = "INSERT INTO desert (id_transaksi, id_desert, nama_makanan, tipe, deskripsi, harga, jumlah, total, waktu_masak) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" // bug clash on db
                + "ON DUPLICATE KEY UPDATE id_desert = VALUES(id_desert), nama_makanan = VALUES(nama_makanan), tipe = VALUES(tipe), "
                + "harga = VALUES(harga), jumlah = VALUES(jumlah), total = VALUES(total)";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
            ps.setInt(1, id_transaksi);
            ps.setInt(2, selectedDesert.getItemID_s());
            ps.setString(3, selectedDesert.getItemName_s());
            ps.setString(4, selectedDesert.getBrandName_s());
            ps.setString(5, selectedDesert.getDescription_s());
            ps.setDouble(6, selectedDesert.getPrice_s());
            ps.setInt(7, spinnerValue);
            ps.setDouble(8, selectedDesert.getPrice_s() * spinnerValue);
            ps.setInt(9, waktu_masak);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updt_database_s(Desert_data selectedDesert, int storedSpinnerValue, int newSpinnerValue) throws SQLException {
        try {
            String sql = "UPDATE desert SET jumlah = ?, total = ? WHERE id_desert = ?";
            try (PreparedStatement ps = Conection.conection().prepareStatement(sql)) {
                int newTotal = selectedDesert.getPrice_s() * newSpinnerValue;
                
                ps.setInt(1, newSpinnerValue);
                ps.setDouble(2, newTotal);
                ps.setInt(3, selectedDesert.getItemID_s());
                ps.execute();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int waktu_masak_s() {
        int totalWaktuMasak = 0;
        String sql = "SELECT SUM(waktu_masak) AS total_waktu_masak FROM food";
        try ( PreparedStatement ps = Conection.conection().prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                totalWaktuMasak = rs.getInt("total_waktu_masak");
                System.out.println("Total Waktu Masak: " + totalWaktuMasak);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalWaktuMasak;
    }
    
    // ========= ABOUT DESERT =========
    
    // ++++++ Head Logic for Updating JTextPane content ++++++
    
    private static void appendToNote(JTextPane textPane, String text) {
        StyledDocument doc = textPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), text, null);
            
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    private static void replaceTextInNote(JTextPane textPane, String oldText, String newText) {
        StyledDocument doc = textPane.getStyledDocument();
        try {
            int start = doc.getText(0, doc.getLength()).indexOf(oldText);
            doc.remove(start, oldText.length());
            doc.insertString(start, newText, null);
            
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    private static void deleteMenu_n(JTextPane note, Map<Food_data, Integer> dataMap, Map<Drink_data, Integer> dataMap1, Map<Desert_data, Integer> dataMap2) {
        StyledDocument doc = note.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
            dataMap.clear();
            dataMap1.clear();
            dataMap2.clear();
            
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    private static void deleteMenu_t(JTextPane Total, Map<Food_data, Integer> dataMap, Map<Drink_data, Integer> dataMap1, Map<Desert_data, Integer> dataMap2) {
        StyledDocument doc = Total.getStyledDocument();
        try {
            doc.remove(0, doc.getLength());
            dataMap.clear();
            dataMap1.clear();
            dataMap2.clear();
            appendToNote(Total, "		Total : ");
            
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    private static void dlete_temp_database() throws SQLException {  // for deleting temp database
        String sql_f = "DELETE FROM food ORDER BY id_food";
        String sql_d = "DELETE FROM drink ORDER BY id_drink";
        String sql_s = "DELETE FROM desert ORDER BY id_desert";
        String sql_t = "DELETE FROM temporary_cash ORDER BY id_transaksi";
    
        try (PreparedStatement ps1 = Conection.conection().prepareStatement(sql_f);
             PreparedStatement ps2 = Conection.conection().prepareStatement(sql_d);
             PreparedStatement ps3 = Conection.conection().prepareStatement(sql_s);
             PreparedStatement ps4 = Conection.conection().prepareStatement(sql_t)) {
                ps1.execute();
                ps2.execute();
                ps3.execute();
                ps4.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void checko_cash() {      // when deal and execute for button deal
        try {
            int id_transaksi;
            String getIdQuery = "SELECT MAX(id_transaksi) FROM transaksi";

            try ( PreparedStatement getIdPS = Conection.conection().prepareStatement(getIdQuery)) { // get indeks from max of id_transaksi
                ResultSet resultSet = getIdPS.executeQuery();
                resultSet.next();
                id_transaksi = resultSet.getInt(1) + 1;
            }

            String sql = "INSERT INTO transaksi (id_transaksi, nama_makanan_or_minuman, tipe, deskripsi, harga, jumlah, total) "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM food "
                    + "UNION ALL "
                    + "SELECT ?, nama_minuman, tipe, deskripsi, harga, jumlah, total FROM drink "
                    + "UNION ALL "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM desert";
            
            String sql1 = "UPDATE transaksi SET status_pembayaran = ? WHERE id_transaksi = ?";  // it will be run by button triger
            String sql2 = "UPDATE transaksi SET metode_pembayaran = ?, status = ?, tanggal_pesanan = ?, waktu_pesanan = ?, total_harga = (SELECT SUM(total) FROM transaksi WHERE id_transaksi = ?) WHERE id_transaksi = ?";  // bug fixed
            Calendar calendar = Calendar.getInstance();
            Date tanggalNow = new Date(calendar.getTime().getTime());
            Time waktuSekarang = new Time(System.currentTimeMillis());
            try ( PreparedStatement ps = Conection.conection().prepareStatement(sql);
                    PreparedStatement ps1 = Conection.conection().prepareStatement(sql1);
                    PreparedStatement ps2 = Conection.conection().prepareStatement(sql2)) {
                ps.setInt(1, id_transaksi);
                ps.setInt(2, id_transaksi);
                ps.setInt(3, id_transaksi);
                
                ps1.setString(1, "Belum bayar");    // it will be run by button triger
                ps1.setInt(2, id_transaksi);
                
                ps2.setString(1, "Tunai");      // declare per button
                ps2.setString(2, "Onlocation");
                ps2.setDate(3, tanggalNow);
                ps2.setTime(4, waktuSekarang);
                ps2.setDouble(5, id_transaksi);
                ps2.setInt(6, id_transaksi);
                
                ps.executeUpdate();
                ps1.executeUpdate();
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
            int id_transaksi;
            String getIdQuery = "SELECT MAX(id_transaksi) FROM transaksi";

            try ( PreparedStatement getIdPS = Conection.conection().prepareStatement(getIdQuery)) { // get indeks from max of id_transaksi
                ResultSet resultSet = getIdPS.executeQuery();
                resultSet.next();
                id_transaksi = resultSet.getInt(1) + 1;
            }

            String sql = "INSERT INTO temporary_cash (id_transaksi, nama_makanan_or_minuman, tipe, deskripsi, harga, jumlah, total) "       // for ireport taken
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM food "
                    + "UNION ALL "
                    + "SELECT ?, nama_minuman, tipe, deskripsi, harga, jumlah, total FROM drink "
                    + "UNION ALL "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM desert";
            
            String sql1 = "UPDATE temporary_cash SET total_harga = (SELECT SUM(total) FROM temporary_cash WHERE id_transaksi = ?), waktu_temp = ? WHERE id_transaksi = ?";  // it will be run by button triger String sql2 = "UPDATE transaksi SET metode_pembayaran = ?, status = ? WHERE id_transaksi = ?";  // bug fixed
            Timestamp nowW = new Timestamp(System.currentTimeMillis());
            try ( PreparedStatement ps = Conection.conection().prepareStatement(sql);
                    PreparedStatement ps1 = Conection.conection().prepareStatement(sql1)) {
                ps.setInt(1, id_transaksi);
                ps.setInt(2, id_transaksi);
                ps.setInt(3, id_transaksi);
                
                ps1.setInt(1, id_transaksi);    // it will be run by button triger
                ps1.setTimestamp(2, nowW);
                ps1.setInt(3, id_transaksi);
                
                ps.executeUpdate();
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void checko_cashless() {      // when deal and execute for button deal
        try {
            int id_transaksi;
            String getIdQuery = "SELECT MAX(id_transaksi) FROM transaksi";

            try ( PreparedStatement getIdPS = Conection.conection().prepareStatement(getIdQuery)) { // get indeks from max of id_transaksi
                ResultSet resultSet = getIdPS.executeQuery();
                resultSet.next();
                id_transaksi = resultSet.getInt(1) + 1;
            }

            String sql = "INSERT INTO transaksi (id_transaksi, nama_makanan_or_minuman, tipe, deskripsi, harga, jumlah, total) "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM food "
                    + "UNION ALL "
                    + "SELECT ?, nama_minuman, tipe, deskripsi, harga, jumlah, total FROM drink "
                    + "UNION ALL "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM desert";

            String sql1 = "UPDATE transaksi SET status_pembayaran = ? WHERE id_transaksi = ?";  // it will be run by button triger
            String sql2 = "UPDATE transaksi SET metode_pembayaran = ?, status = ?, tanggal_pesanan = ?, waktu_pesanan = ?, total_harga = (SELECT SUM(total) FROM transaksi WHERE id_transaksi = ?) WHERE id_transaksi = ?";  // bug fixed
            Calendar calendar = Calendar.getInstance();
            Date tanggalNow = new Date(calendar.getTime().getTime());
            Time waktuSekarang = new Time(System.currentTimeMillis());
            try ( PreparedStatement ps = Conection.conection().prepareStatement(sql);  PreparedStatement ps1 = Conection.conection().prepareStatement(sql1);  PreparedStatement ps2 = Conection.conection().prepareStatement(sql2)) {
                ps.setInt(1, id_transaksi);
                ps.setInt(2, id_transaksi);
                ps.setInt(3, id_transaksi);

                ps1.setString(1, "Belum bayar");    // it will be run by button triger
                ps1.setInt(2, id_transaksi);

                ps2.setString(1, "Non-Tunai");      // declare per button
                ps2.setString(2, "Onlocation");
                ps2.setDate(3, tanggalNow);
                ps2.setTime(4, waktuSekarang);
                ps2.setDouble(5, id_transaksi);
                ps2.setInt(6, id_transaksi);

                ps.executeUpdate();
                ps1.executeUpdate();
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int id_transaksi;
            String getIdQuery = "SELECT MAX(id_transaksi) FROM transaksi";

            try ( PreparedStatement getIdPS = Conection.conection().prepareStatement(getIdQuery)) { // get indeks from max of id_transaksi
                ResultSet resultSet = getIdPS.executeQuery();
                resultSet.next();
                id_transaksi = resultSet.getInt(1) + 1;
            }

            String sql = "INSERT INTO temporary_cash (id_transaksi, nama_makanan_or_minuman, tipe, deskripsi, harga, jumlah, total) " // for ireport taken
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM food "
                    + "UNION ALL "
                    + "SELECT ?, nama_minuman, tipe, deskripsi, harga, jumlah, total FROM drink "
                    + "UNION ALL "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM desert";

            String sql1 = "UPDATE temporary_cash SET total_harga = (SELECT SUM(total) FROM temporary_cash WHERE id_transaksi = ?), waktu_temp = ? WHERE id_transaksi = ?";  // it will be run by button triger String sql2 = "UPDATE transaksi SET metode_pembayaran = ?, status = ? WHERE id_transaksi = ?";  // bug fixed
            Timestamp nowW = new Timestamp(System.currentTimeMillis());

            try ( PreparedStatement ps = Conection.conection().prepareStatement(sql);  PreparedStatement ps1 = Conection.conection().prepareStatement(sql1)) {
                ps.setInt(1, id_transaksi);
                ps.setInt(2, id_transaksi);
                ps.setInt(3, id_transaksi);

                ps1.setInt(1, id_transaksi);    // it will be run by button triger
                ps1.setTimestamp(2, nowW);
                ps1.setInt(3, id_transaksi);

                ps.executeUpdate();
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        private static void checko_cashless_c() {      // when deal and execute for button deal
        try {
            int id_transaksi;
            String getIdQuery = "SELECT MAX(id_transaksi) FROM transaksi";

            try ( PreparedStatement getIdPS = Conection.conection().prepareStatement(getIdQuery)) { // get indeks from max of id_transaksi
                ResultSet resultSet = getIdPS.executeQuery();
                resultSet.next();
                id_transaksi = resultSet.getInt(1) + 1;
            }

            String sql = "INSERT INTO transaksi (id_transaksi, nama_makanan_or_minuman, tipe, deskripsi, harga, jumlah, total) "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM food "
                    + "UNION ALL "
                    + "SELECT ?, nama_minuman, tipe, deskripsi, harga, jumlah, total FROM drink "
                    + "UNION ALL "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM desert";

            String sql1 = "UPDATE transaksi SET status_pembayaran = ? WHERE id_transaksi = ?";  // it will be run by button triger
            String sql2 = "UPDATE transaksi SET metode_pembayaran = ?, status = ?, tanggal_pesanan = ?, waktu_pesanan = ?, total_harga = (SELECT SUM(total) FROM transaksi WHERE id_transaksi = ?) WHERE id_transaksi = ?";  // bug fixed
            Calendar calendar = Calendar.getInstance();
            Date tanggalNow = new Date(calendar.getTime().getTime());
            Time waktuSekarang = new Time(System.currentTimeMillis());
            try ( PreparedStatement ps = Conection.conection().prepareStatement(sql);  PreparedStatement ps1 = Conection.conection().prepareStatement(sql1);  PreparedStatement ps2 = Conection.conection().prepareStatement(sql2)) {
                ps.setInt(1, id_transaksi);
                ps.setInt(2, id_transaksi);
                ps.setInt(3, id_transaksi);

                ps1.setString(1, "Belum bayar");    // it will be run by button triger
                ps1.setInt(2, id_transaksi);

                ps2.setString(1, "Non-Tunai");      // declare per button
                ps2.setString(2, "Delivery");
                ps2.setDate(3, tanggalNow);
                ps2.setTime(4, waktuSekarang);
                ps2.setDouble(5, id_transaksi);
                ps2.setInt(6, id_transaksi);

                ps.executeUpdate();
                ps1.executeUpdate();
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            int id_transaksi;
            String getIdQuery = "SELECT MAX(id_transaksi) FROM transaksi";

            try ( PreparedStatement getIdPS = Conection.conection().prepareStatement(getIdQuery)) { // get indeks from max of id_transaksi
                ResultSet resultSet = getIdPS.executeQuery();
                resultSet.next();
                id_transaksi = resultSet.getInt(1) + 1;
            }

            String sql = "INSERT INTO temporary_cash (id_transaksi, nama_makanan_or_minuman, tipe, deskripsi, harga, jumlah, total) " // for ireport taken
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM food "
                    + "UNION ALL "
                    + "SELECT ?, nama_minuman, tipe, deskripsi, harga, jumlah, total FROM drink "
                    + "UNION ALL "
                    + "SELECT ?, nama_makanan, tipe, deskripsi, harga, jumlah, total FROM desert";

            String sql1 = "UPDATE temporary_cash SET total_harga = (SELECT SUM(total) FROM temporary_cash WHERE id_transaksi = ?), waktu_temp = ? WHERE id_transaksi = ?";  // it will be run by button triger String sql2 = "UPDATE transaksi SET metode_pembayaran = ?, status = ? WHERE id_transaksi = ?";  // bug fixed
            Timestamp nowW = new Timestamp(System.currentTimeMillis());
            try ( PreparedStatement ps = Conection.conection().prepareStatement(sql);  PreparedStatement ps1 = Conection.conection().prepareStatement(sql1)) {
                ps.setInt(1, id_transaksi);
                ps.setInt(2, id_transaksi);
                ps.setInt(3, id_transaksi);

                ps1.setInt(1, id_transaksi);    // it will be run by button triger
                ps1.setTimestamp(2, nowW);
                ps1.setInt(3, id_transaksi);

                ps.executeUpdate();
                ps1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void Report_reek() {
        try {
            String report_reek = "src/Report_reek/report_reek1.jasper";          //BUG fixed
            Connection conn = Conection.conection();                    //BUG fixed
            Map data_j = new HashMap<>();                       //BUG fixed
            JasperPrint print = JasperFillManager.fillReport(report_reek, data_j, conn);        //BUG fixed
            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void data_delivery(String nama, int contact, String alamat, String desk_alamat) {

        try {
            String sql = "INSERT INTO data_delivery (nama_lengkap, contact, alamat, deskripsi_alamat) VALUES (?, ?, ?, ?)";     // BUG
            try ( PreparedStatement ps1 = Conection.conection().prepareStatement(sql)) {

                ps1.setString(1, nama);
                ps1.setInt(2, contact);
                ps1.setString(3, alamat);
                ps1.setString(4, desk_alamat);
                ps1.execute();

            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
    }

    // +++++ Head Logic for Updating JTextPane content ++++++

    // ====================== ABOUT CHECKOUT ======================
    
    private void checkout() throws Exception {
        home_c = new Checkout_home();
        home = new Home();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home_c);
        onSite_b = home_c.getOnsite_b();
        quote_c = home_c.getQuote_c();
        deliv_a = home_c.getDeliv_a();
        deliv_b = home_c.getDeliv_b();
        quote_c1 = home_c.getQuote_c1();
        button_cancel_c = home_c.getButton_cancel_c();
        button_cancel_c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(home_c);
                mainPanel.setLayout(new BorderLayout());
                mainPanel.add(sp_item);
                mainPanel.add(jLabel_home);
                mainPanel.add(navbar_home);
                mainPanel.add(button_navbar);
                mainPanel.add(navbar_panel);
                mainPanel.add(jScrollPane1);
                mainPanel.add(jScrollPane_note);
                mainPanel.add(jScrollPane_Total);
                mainPanel.add(txtcheckout);
                mainPanel.add(jLabel_checkout);
                mainPanel.add(jLabel_c_c);
                mainPanel.add(button_checkout);
                mainPanel.add(button_checkout1);
                mainPanel.add(home_f);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        button_stay_c = home_c.getButton_stay_c();
        button_stay_c.addActionListener(new ActionListener() {
            private boolean isSize = true;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSize) {
                    quote_c.setSize(0, 0);
                    onSite_b.setSize(292, 285);
                } else {
                    quote_c.setSize(290, 60);
                    onSite_b.setSize(0, 0);
                }
                isSize = !isSize;
            }
        });
        button_deliv_c = home_c.getButton_deliv_c();
        button_deliv_c.addActionListener(new ActionListener() {
            private boolean isSize = true;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSize) {
                    deliv_a.setSize(445, 569);
                    deliv_b.setSize(320, 95);
                    quote_c1.setSize(0, 0); //check //////////////////
                } else {
                    deliv_a.setSize(0, 0);
                    deliv_b.setSize(0, 0);
                    quote_c1.setSize(300, 350);
                }
                isSize = !isSize;
            }
        });
        button_cashless_c = home_c.getButton_cashless_c();      // cashless_delivery
        button_cashless_c.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!home_c.getNama_deliv().getText().isEmpty() && !home_c.getContact_deliv().getText().isEmpty() && !home_c.getAlamat_deliv().getText().isEmpty()) {

                        String nama_deliv = home_c.getNama_deliv().getText();
                        int contact = 0;
                        try {
                            contact = Integer.parseInt(home_c.getContact_deliv().getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(mainPanel, "Input pada No HP/Telp harus berupa angka.", "Information", JOptionPane.INFORMATION_MESSAGE);
                            home_c.getContact_deliv().setText("");
                            return;
                        }
                        String alamat_deliv = home_c.getAlamat_deliv().getText();
                        String alamat_desk = home_c.getAlamat_descrp().getText();

                        data_delivery(nama_deliv, contact, alamat_deliv, alamat_desk);
                        checko_cashless_c();
                        Report_reek();
                        int total_waktu_masak = (waktu_masaks() + waktu_masak_s() + waktu_masak_d());
                        JOptionPane.showMessageDialog(mainPanel, "Pesananmu akan tiba dalam " + total_waktu_masak + " mnt setelah melakukan pembayaran", "Information", JOptionPane.INFORMATION_MESSAGE);
                        dlete_temp_database();
                        deleteMenu_n(note, data_f, data_d, data_s);
                        deleteMenu_t(Total, data_f, data_d, data_s);
                        totalHarga = 0;
                        sp_item.setValue(1);
                        mainPanel.remove(home_c);
                        mainPanel.add(home);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Data pengiriman tidak boleh kosong");
                        return;
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        button_cash = home_c.getButton_cash();                  // cash on_location
        button_cash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checko_cash();
                Report_reek();
                int total_waktu_masak = (waktu_masaks()+waktu_masak_s()+waktu_masak_d());
                 JOptionPane.showMessageDialog(mainPanel, "Pesananmu akan tiba dalam " + total_waktu_masak +" mnt setelah melakukan pembayaran", "Information",JOptionPane.INFORMATION_MESSAGE);   // check
                try {  
                    deleteMenu_n(note, data_f, data_d, data_s);
                    deleteMenu_t(Total, data_f, data_d, data_s);
                    totalHarga = 0;
                    sp_item.setValue(1);
                    dlete_temp_database();
                    mainPanel.remove(home_c);
                    mainPanel.add(home);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        button_cashless = home_c.getButton_cashless();
        button_cashless.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checko_cashless();
                Report_reek();
                int total_waktu_masak = (waktu_masaks()+waktu_masak_s()+waktu_masak_d());
                 JOptionPane.showMessageDialog(mainPanel, "Pesananmu akan tiba dalam " + total_waktu_masak +" mnt setelah melakukan pembayaran", "Information",JOptionPane.INFORMATION_MESSAGE);
                try {
                    deleteMenu_n(note, data_f, data_d, data_s);
                    deleteMenu_t(Total, data_f, data_d, data_s);
                    totalHarga = 0;
                    sp_item.setValue(1);
                    dlete_temp_database();
                    mainPanel.remove(home_c);
                    mainPanel.add(home);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
              
            }
        });
    }
    
    // ====================== ABOUT CHECKOUT ======================
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel7 = new javax.swing.JLabel();
        background1 = new FT_menu.Background_menu();
        mainPanel = new FT_menu.MainPanel();
        jScrollPane_note = new javax.swing.JScrollPane();
        note = new javax.swing.JTextPane();
        jLabel_home = new javax.swing.JLabel();
        navbar_home = new FT_menu.navbar.Button_navbar();
        button_navbar = new FT_menu.navbar.Button_navbar();
        navbar_panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        button_drink = new FT_menu.navbar.Button_circle_black();
        button_snacks = new FT_menu.navbar.Button_circle_black();
        button_food = new FT_menu.navbar.Button_circle_black();
        sp_item = new G_Swing.Spinner();
        jScrollPane_Total = new javax.swing.JScrollPane();
        Total = new javax.swing.JTextPane();
        txtcheckout = new javax.swing.JLabel();
        jLabel_checkout = new javax.swing.JLabel();
        jLabel_c_c = new javax.swing.JLabel();
        button_checkout = new FT_menu.navbar.Button_circle();
        button_checkout1 = new FT_menu.navbar.Button_circle();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1650, 1020));

        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mainPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mainPanelMouseExited(evt);
            }
        });
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        note.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        note.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        note.setText("\n");
        jScrollPane_note.setViewportView(note);

        mainPanel.add(jScrollPane_note, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 300, 170));

        jLabel_home.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        jLabel_home.setForeground(new java.awt.Color(0, 102, 102));
        jLabel_home.setText("Home");
        jLabel_home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainPanel.add(jLabel_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 438, 50, 30));

        navbar_home.setForeground(new java.awt.Color(0, 51, 51));
        navbar_home.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        navbar_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                navbar_homeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navbar_homeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navbar_homeMouseExited(evt);
            }
        });
        mainPanel.add(navbar_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 60, 70));

        button_navbar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button_navbarMouseEntered(evt);
            }
        });
        mainPanel.add(button_navbar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 50, 50));

        navbar_panel.setBackground(new java.awt.Color(0, 102, 102));
        navbar_panel.setOpaque(false);
        navbar_panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                navbar_panelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                navbar_panelMouseExited(evt);
            }
        });
        navbar_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FT_menu/image/navbar/foodm.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        navbar_panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 10, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FT_menu/image/navbar/desert-nav.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        navbar_panel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 232, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FT_menu/image/navbar/drink (1).png"))); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        navbar_panel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 120, -1, -1));

        button_drink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_drinkMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button_drinkMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button_drinkMouseExited(evt);
            }
        });
        navbar_panel.add(button_drink, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 110, 92, 90));

        button_snacks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_snacksMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button_snacksMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button_snacksMouseExited(evt);
            }
        });
        navbar_panel.add(button_snacks, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 95, 90));

        button_food.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_foodMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button_foodMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button_foodMouseExited(evt);
            }
        });
        button_food.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_foodActionPerformed(evt);
            }
        });
        navbar_panel.add(button_food, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 96, 90));

        mainPanel.add(navbar_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 300, 0, 310));

        sp_item.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        sp_item.setLableText("Jumlah");
        mainPanel.add(sp_item, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 252, 65, -1));

        Total.setEditable(false);
        Total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Total.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Total.setText("\t\tTotal : ");
        jScrollPane_Total.setViewportView(Total);

        mainPanel.add(jScrollPane_Total, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 720, 300, 30));

        txtcheckout.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txtcheckout.setForeground(new java.awt.Color(0, 102, 102));
        txtcheckout.setText("Checkout");
        txtcheckout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainPanel.add(txtcheckout, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 790, 80, -1));

        jLabel_checkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FT_menu/image/cart/shopping_bag.png"))); // NOI18N
        jLabel_checkout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainPanel.add(jLabel_checkout, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 780, -1, -1));

        jLabel_c_c.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FT_menu/image/cart/c_c_s.png"))); // NOI18N
        jLabel_c_c.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mainPanel.add(jLabel_c_c, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 790, -1, -1));

        button_checkout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_checkoutMouseClicked(evt);
            }
        });
        button_checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_checkoutActionPerformed(evt);
            }
        });
        mainPanel.add(button_checkout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 770, 250, 60));
        mainPanel.add(button_checkout1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 780, 210, 40));

        jTextPane2.setEditable(false);
        jTextPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextPane2.setText("  ========================================\n  *************** WELCOME TO REEK ****************");
        jScrollPane1.setViewportView(jTextPane2);

        mainPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 504, 300, 50));

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1213, Short.MAX_VALUE)
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void navbar_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar_homeMouseClicked
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(home);
        mainPanel.revalidate();
        mainPanel.repaint();
    }//GEN-LAST:event_navbar_homeMouseClicked

    private void button_navbarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_navbarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_button_navbarMouseEntered

    private void navbar_homeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar_homeMouseEntered
        navbar_panel.setSize(160,310);
    }//GEN-LAST:event_navbar_homeMouseEntered

    private void navbar_homeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar_homeMouseExited
        navbar_panel.setSize(0,0);
    }//GEN-LAST:event_navbar_homeMouseExited

    private void mainPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_mainPanelMouseEntered

    private void navbar_panelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar_panelMouseExited
        navbar_panel.setSize(0,0);
    }//GEN-LAST:event_navbar_panelMouseExited

    private void mainPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_mainPanelMouseExited

    private void navbar_panelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_navbar_panelMouseEntered
        navbar_panel.setSize(160,310);
    }//GEN-LAST:event_navbar_panelMouseEntered

    private void button_foodMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_foodMouseEntered
        navbar_panel.setSize(160,310);
    }//GEN-LAST:event_button_foodMouseEntered

    private void button_snacksMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_snacksMouseEntered
        navbar_panel.setSize(160,310);
    }//GEN-LAST:event_button_snacksMouseEntered

    private void button_foodMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_foodMouseExited
        navbar_panel.setSize(0,0);
    }//GEN-LAST:event_button_foodMouseExited

    private void button_snacksMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_snacksMouseExited
        navbar_panel.setSize(0,0);
    }//GEN-LAST:event_button_snacksMouseExited

    private void button_foodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_foodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_foodActionPerformed

    private void button_snacksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_snacksMouseClicked
        
        remove(home_d);
        remove(home_f);
        remove(home_c);
        mainPanel.add(home_s);
        mainPanel.revalidate();
        mainPanel.repaint();
    }//GEN-LAST:event_button_snacksMouseClicked

    private void button_foodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_foodMouseClicked
        
        remove(home_s);
        remove(home_d);
        remove(home_c);
        mainPanel.add(home_f);
        mainPanel.revalidate();
        mainPanel.repaint();       
    }//GEN-LAST:event_button_foodMouseClicked

    private void button_drinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_drinkMouseClicked
        
        remove(home_f);
        remove(home_s);
        remove(home_c);
        mainPanel.add(home_d);
        mainPanel.revalidate();
        mainPanel.repaint();
    }//GEN-LAST:event_button_drinkMouseClicked

    private void button_drinkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_drinkMouseEntered
        navbar_panel.setSize(160,310);
    }//GEN-LAST:event_button_drinkMouseEntered

    private void button_drinkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_drinkMouseExited
        navbar_panel.setSize(0,0);
    }//GEN-LAST:event_button_drinkMouseExited

    private void button_square1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_square1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_square1ActionPerformed

    private void button_checkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_checkoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_checkoutActionPerformed

    private void button_checkoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_checkoutMouseClicked
        
        String data_note = note.getText().trim();
        if (data_note.isEmpty() || data_note.equals("  ========================================\n"
                + "  *************** WELCOME TO REEK ***************\n"
                + "\n"
                + "")) {
            JOptionPane.showMessageDialog(mainPanel, "Ooops kamu belum menambah menu nih, \nmaaf jadi belum bisa checkout.","Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            mainPanel.removeAll();
            mainPanel.add(home_c);
            mainPanel.revalidate();
            mainPanel.repaint();
            data_note = data_note.replaceAll("\\t", " ");
            String[] wordToReplace_n = {"Food", "Drink", "Desert"};
            for (String word : wordToReplace_n) {
                data_note = data_note.replaceAll("\\b" + word + "\\b", "\t\t" + word + "\t");
            }
            data_note = data_note.replaceAll("Udang Goreng Saus Mentega\\s*Food\\b", "Udang Goreng Saus Mentega\tFood");
            data_note = data_note.replaceAll("Nasi putih\\b", "Nasi putih\t");
            data_note = data_note.replaceAll("Tea\\b", "Tea\t");
            data_note = data_note.replaceAll("So beach\\b", "So beach\t");
            data_note = data_note.replaceAll("Kiwi ext\\b", "Kiwi ext\t");
            data_note = data_note.replaceAll("Salted Caramel Ice Cream\\s*Desert\\b", "Salted Caramel Ice Cream\tDesert");
            data_note = data_note.replaceAll("Cheesecake Chocolate\\s*Desert\\b", "Cheesecake Chocolate\tDesert");
            String data_total = Total.getText();
            checkout_pane = home_c.getCheckout_pane();
            String[] wordToreplace_t = {"Total"};
            for (String word : wordToreplace_t) {
                data_total = data_total.replaceAll("\t\t\\b" + word + "\\b", " " + word);
            }
         //   appendToNote(checkout_pane, );
            checkout_pane.setText("  ========================================\n"
                    + "  *************** REEK RESTAURANTS ***************\n"
                    + "\n"
                    + "\n " + data_note + "\n\n\n ========================================\n\n" + data_total);
        }
    }//GEN-LAST:event_button_checkoutMouseClicked

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
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new menu().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane Total;
    private FT_menu.Background_menu background1;
    private FT_menu.navbar.Button_circle button_checkout;
    private FT_menu.navbar.Button_circle button_checkout1;
    private FT_menu.navbar.Button_circle_black button_drink;
    private FT_menu.navbar.Button_circle_black button_food;
    private FT_menu.navbar.Button_navbar button_navbar;
    private FT_menu.navbar.Button_circle_black button_snacks;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_c_c;
    private javax.swing.JLabel jLabel_checkout;
    private javax.swing.JLabel jLabel_home;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane_Total;
    private javax.swing.JScrollPane jScrollPane_note;
    private javax.swing.JTextPane jTextPane2;
    private FT_menu.MainPanel mainPanel;
    private FT_menu.navbar.Button_navbar navbar_home;
    private javax.swing.JPanel navbar_panel;
    private javax.swing.JTextPane note;
    private G_Swing.Spinner sp_item;
    private javax.swing.JLabel txtcheckout;
    // End of variables declaration//GEN-END:variables
}
