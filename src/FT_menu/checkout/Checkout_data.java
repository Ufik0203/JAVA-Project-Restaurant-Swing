/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FT_menu.checkout;

/**
 *
 * @author mario
 */
public class Checkout_data {

    public Checkout_data(String nama_c, String noHp_c, String alamat_c, String descAlamat_c) {
        this.nama_c = nama_c;
        this.noHp_c = noHp_c;
        this.alamat_c = alamat_c;
        this.descAlamat_c = descAlamat_c;
    }
    
    String nama_c;
    String noHp_c;
    String alamat_c;
    String descAlamat_c;

    public String getNama_c() {
        return nama_c;
    }

    public void setNama_c(String nama_c) {
        this.nama_c = nama_c;
    }

    public String getNoHp_c() {
        return noHp_c;
    }

    public void setNoHp_c(String noHp_c) {
        this.noHp_c = noHp_c;
    }

    public String getAlamat_c() {
        return alamat_c;
    }

    public void setAlamat_c(String alamat_c) {
        this.alamat_c = alamat_c;
    }

    public String getDescAlamat_c() {
        return descAlamat_c;
    }

    public void setDescAlamat_c(String descAlamat_c) {
        this.descAlamat_c = descAlamat_c;
    }
    
}
