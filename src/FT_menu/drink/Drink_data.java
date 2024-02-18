/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FT_menu.drink;

import javax.swing.Icon;

/**
 *
 * @author mario
 */
public class Drink_data {
    
    int itemID_d;
    String itemName_d;
    String description_d;
    int price_d;
    String brandName_d;
    Icon image_d;
 
    public Drink_data() {
        
    }

    public int getItemID_d() {
        return itemID_d;
    }

    public void setItemID_d(int itemID_d) {
        this.itemID_d = itemID_d;
    }

    public String getItemName_d() {
        return itemName_d;
    }

    public void setItemName_d(String itemName_d) {
        this.itemName_d = itemName_d;
    }

    public String getDescription_d() {
        return description_d;
    }

    public void setDescription_d(String description_d) {
        this.description_d = description_d;
    }

    public int getPrice_d() {
        return price_d;
    }

    public void setPrice_d(int price_d) {
        this.price_d = price_d;
    }

    public String getBrandName_d() {
        return brandName_d;
    }

    public void setBrandName_d(String brandName_d) {
        this.brandName_d = brandName_d;
    }

    public Icon getImage_d() {
        return image_d;
    }

    public void setImage_d(Icon image) {
        this.image_d = image;
    }

    public Drink_data(int itemID_d, String itemName_d, String description_d, int price_d, String brandName_d, Icon image_d) {
        this.itemID_d = itemID_d;
        this.itemName_d = itemName_d;
        this.description_d = description_d;
        this.price_d = price_d;
        this.brandName_d = brandName_d;
        this.image_d = image_d;
    }
    
    @Override
    public int hashCode() {
        return itemID_d + itemName_d.hashCode() + brandName_d.hashCode() + price_d;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Drink_data data_d = (Drink_data) obj;
        return itemID_d == data_d.itemID_d &&
                itemName_d.equals(data_d.getItemName_d()) &&
                brandName_d.equals(data_d.getBrandName_d()) &&
                price_d == data_d.price_d;
    }
}
