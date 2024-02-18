/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package TUBES_PCD;

import FT_menu.ScrollBar;
import java.awt.Color;
import java.awt.Graphics;
import org.opencv.core.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;



/**
 *
 * @author mario
 */
public class Tubes_pcd extends javax.swing.JFrame {

    /**
     * Creates new form TUBES_PCD
     */
    public Tubes_pcd() {
        initComponents();
        background_tubesPCD1.addBlurComponent(PanelBack);
        System.loadLibrary("opencv_java480");
        jScrollPane1.setVerticalScrollBar(new ScrollBar());

    }

    JFileChooser chooser = new JFileChooser("");
    File filenya;
    private BufferedImage originalImage;
    Tesseract tr = new Tesseract();
    
    private BufferedImage toGrayScale(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayscaleImage.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();

        return grayscaleImage;
    }
    
    private BufferedImage performEdgeDetection(BufferedImage inputImage) {
        Deteksi_tepi detector = new Deteksi_tepi();
        detector.setLowThreshold(0.5f);
        detector.setHighThreshold(1f);
        detector.setSourceImage(inputImage);
        detector.process();

        return detector.getEdgesImage();
    }
    
    private BufferedImage binarizeImage(BufferedImage edgeImage) {
        // Implementasi binarisasi gambar di sini
        int width = edgeImage.getWidth();
        int height = edgeImage.getHeight();
        BufferedImage binaryImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        // Contoh: Menggunakan metode binerisasi sederhana (misalnya, ambang batas)
        int threshold = 128;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(edgeImage.getRGB(x, y));
                int grayscale = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                if (grayscale > threshold) {
                    binaryImage.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    binaryImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        // Kembalikan gambar hasil binarisasi
        return binaryImage;
    }
       
    private int[][] labelObjects(BufferedImage image) {
        // Implementasi label objek di sini
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] labeledImage = new int[width][height];
        int label = 1;

        // Contoh: Menggunakan metode penggambaran (connected component labeling)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isBlack(image.getRGB(x, y))) {
                    int connectedLabel = getConnectedLabel(labeledImage, x, y);
                    if (connectedLabel == 0) {
                        labeledImage[x][y] = label++;
                    } else {
                        labeledImage[x][y] = connectedLabel;
                    }
                }
            }
        }

        // Kembalikan hasil label objek
        return labeledImage;
    }

    private int getConnectedLabel(int[][] labeledImage, int x, int y) {
        return 0;
    }

    private int calculateObjectArea(int[][] labeledImage, int objectId) {
        // Implementasi perhitungan area objek di sini
        int width = labeledImage.length;
        int height = labeledImage[0].length;
        int area = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (labeledImage[x][y] == objectId) {
                    area++;
                }
            }
        }

        // Kembalikan area objek
        return area;
    }

    private Point calculateObjectCenter(int[][] labeledImage, int objectId) {
        // Implementasi perhitungan pusat objek di sini
        int width = labeledImage.length;
        int height = labeledImage[0].length;
        int sumX = 0, sumY = 0, count = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (labeledImage[x][y] == objectId) {
                    sumX += x;
                    sumY += y;
                    count++;
                }
            }
        }

        // Hitung koordinat pusat dan kembalikan
        int centerX = sumX / count;
        int centerY = sumY / count;
        return new Point(centerX, centerY);
    }

    private boolean isBlack(int rgb) {
        // Metode untuk memeriksa apakah warna piksel hitam
        return (rgb & 0xFF) == 0;
    }

    private BufferedImage iconToBufferedImage(ImageIcon icon) {
        // Metode untuk mengonversi ImageIcon menjadi BufferedImage
        Image img = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }
    
    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    private void showImage(BufferedImage image, JLabel label) {
        Image dimg = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        label.setIcon(imageIcon);
    }
    
    private static BufferedImage applyCartoonFilter(BufferedImage originalImage) {
        // Aplikasikan filter kartun dengan mengatur ambang batas
        int threshold = 70; // Nilai ambang batas dapat disesuaikan
        BufferedImage cartoonImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                int rgb = originalImage.getRGB(x, y);

                // Ambil nilai komponen warna
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Aplikasikan filter kartun dengan mengatur ambang batas
                int newRed = (red < threshold) ? 0 : 255;
                int newGreen = (green < threshold) ? 0 : 255;
                int newBlue = (blue < threshold) ? 0 : 255;

                // Set nilai piksel pada gambar hasil
                int cartoonPixel = (alpha << 24) | (newRed << 16) | (newGreen << 8) | newBlue;
                cartoonImage.setRGB(x, y, cartoonPixel);
            }
        }

        return cartoonImage;
    }
    
    private void saveImage(BufferedImage image, String path) {  /// for adding pict /// for adding pict /// for adding pict
        try {
            File ouputfile = new File(path);
            ImageIO.write(image, "png", ouputfile);
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

        background_tubesPCD1 = new TUBES_PCD.Background_tubesPCD();
        PanelBack = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        Output_obj_img = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        Input_img = new javax.swing.JLabel();
        dir = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        input_grayscale = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        Input_edge_blur = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        Input_obj_img = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelBack.setBackground(new java.awt.Color(0, 153, 153));
        PanelBack.setOpaque(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OCR From Images", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setText("Proses");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jPanel5.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 470, -1));

        Output_obj_img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.add(Output_obj_img, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 470, 290));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input Citra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Upload");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        Input_img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        dir.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(Input_img, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(dir, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                        .addGap(7, 7, 7)))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(Input_img, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(dir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filter Gray Scale", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Proses");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        input_grayscale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                    .addComponent(input_grayscale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(input_grayscale, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filter Edge Detect + Add Heavyblur", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("Proses");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        Input_edge_blur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Input_edge_blur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(Input_edge_blur, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filter Kartun", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setText("Proses");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 470, -1));

        Input_obj_img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.add(Input_obj_img, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 470, 290));

        jTextPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportView(jTextPane1);

        jButton6.setText("Add Text");
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Delete");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBackLayout = new javax.swing.GroupLayout(PanelBack);
        PanelBack.setLayout(PanelBackLayout);
        PanelBackLayout.setHorizontalGroup(
            PanelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelBackLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelBackLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))
                    .addGroup(PanelBackLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(PanelBackLayout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                .addGap(71, 71, 71)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32))))
        );
        PanelBackLayout.setVerticalGroup(
            PanelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBackLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PanelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(PanelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelBackLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(PanelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelBackLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(PanelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout background_tubesPCD1Layout = new javax.swing.GroupLayout(background_tubesPCD1);
        background_tubesPCD1.setLayout(background_tubesPCD1Layout);
        background_tubesPCD1Layout.setHorizontalGroup(
            background_tubesPCD1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background_tubesPCD1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(PanelBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );
        background_tubesPCD1Layout.setVerticalGroup(
            background_tubesPCD1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background_tubesPCD1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(PanelBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background_tubesPCD1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background_tubesPCD1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
 // Memilih gambar dari file
        int buka_dialog = chooser.showOpenDialog(Tubes_pcd.this);
        if (buka_dialog == JFileChooser.APPROVE_OPTION) {
            filenya = chooser.getSelectedFile();
            try {
                String projectDirectory = System.getProperty("user.dir");   /// for adding pict
                
                originalImage = ImageIO.read(new File(filenya.getPath()));
                
                String nfile = filenya.getName();   /// for adding pict
                String paths = projectDirectory + "/src/TUBES_PCD/"+ nfile;    /// for adding pict // bisa di custom path
                saveImage(originalImage, paths);    /// for adding pict
                filenya = new File(paths);
                
                dir.setText(filenya.getPath());
                showImage(originalImage, Input_img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        if (filenya != null) {
            BufferedImage grayscaleImage = toGrayScale(originalImage);
            showImage(grayscaleImage, input_grayscale);
        } else {
            JOptionPane.showMessageDialog(this, "Masukkan gambar terlebih dahulu");
        }
    }//GEN-LAST:event_jButton2MouseClicked
    
    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        if (filenya != null) {
            BufferedImage edgeBlurImage = performEdgeDetection(originalImage);
            showImage(edgeBlurImage, Input_edge_blur);
        } else {
            JOptionPane.showMessageDialog(this, "Masukkan gambar terlebih dahulu");
            jButton1.requestFocus();
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        BufferedImage buff = applyCartoonFilter(originalImage);
        showImage(buff, Input_obj_img);
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        if (filenya != null) {
            try {
                BufferedImage grayScaleImage = toGrayScale(originalImage);
                tr.setDatapath("D:\\Kuliah\\Semester 3\\JAVA\\Tess4J-3.4.8-src\\Tess4J");
                String result = tr.doOCR(grayScaleImage);
            //    System.out.println(result);
                jTextPane1.setText(" " + result);
                showImage(originalImage, Output_obj_img);
            } catch (TesseractException ex) {
                Logger.getLogger(Tubes_pcd.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Masukkan gambar terlebih dahulu");
            jButton1.requestFocus();
        }
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (filenya != null) {
            Input_img.setIcon(null);
            input_grayscale.setIcon(null);
            Input_edge_blur.setIcon(null);
            Input_obj_img.setIcon(null);
            Output_obj_img.setIcon(null);
            filenya = null;
            dir.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Masukkan gambar terlebih dahulu");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (filenya != null) {
            if (filenya.exists()) {
                filenya = null;
                Input_img.setIcon(null);
                input_grayscale.setIcon(null);
                Input_edge_blur.setIcon(null);
                Input_obj_img.setIcon(null);
                Output_obj_img.setIcon(null);
                dir.setText("");
                jTextPane1.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Masukkan gambar terlebih dahulu");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(Tubes_pcd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tubes_pcd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tubes_pcd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tubes_pcd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tubes_pcd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Input_edge_blur;
    private javax.swing.JLabel Input_img;
    private javax.swing.JLabel Input_obj_img;
    private javax.swing.JLabel Output_obj_img;
    private javax.swing.JPanel PanelBack;
    private TUBES_PCD.Background_tubesPCD background_tubesPCD1;
    private javax.swing.JTextField dir;
    private javax.swing.JLabel input_grayscale;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
