/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author mario
 */
public class Waktu extends JLabel {

    public Waktu() {
        // Mulai timer untuk memperbarui label setiap detik
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDateTime();
            }
        });
        timer.start();
        
        // Memastikan label diisi dengan waktu saat inisialisasi
        updateDateTime();
    }


    private void updateDateTime() {
        // Mendapatkan waktu dan tanggal saat ini
        Date now = new Date();

        // Membuat format untuk menampilkan tanggal dan waktu
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        // Mengatur teks label dengan tanggal dan waktu yang diformat
        setText(dateFormat.format(now) + "      " + "Time : " + timeFormat.format(now));
    }
}