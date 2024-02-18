/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FT_menu.desert;

import javax.swing.Icon;

/**
 *
 * @author mario
 */
public class Desert_data {
    
    int itemID_s;
    String itemName_s;
    String description_s;
    int price_s;
    String brandName_s;
    Icon image_s;
 
    public Desert_data() {
        
    }

    public int getItemID_s() {
        return itemID_s;
    }

    public void setItemID_s(int itemID_s) {
        this.itemID_s = itemID_s;
    }

    public String getItemName_s() {
        return itemName_s;
    }

    public void setItemName_s(String itemName_s) {
        this.itemName_s = itemName_s;
    }

    public String getDescription_s() {
        return description_s;
    }

    public void setDescription_s(String description_s) {
        this.description_s = description_s;
    }

    public int getPrice_s() {
        return price_s;
    }

    public void setPrice_s(int price_s) {
        this.price_s = price_s;
    }

    public String getBrandName_s() {
        return brandName_s;
    }

    public void setBrandName_s(String brandName_s) {
        this.brandName_s = brandName_s;
    }

    public Icon getImage_s() {
        return image_s;
    }

    public void setImage_s(Icon image) {
        this.image_s = image;
    }

    public Desert_data(int itemID_s, String itemName_s, String description_s, int price_s, String brandName_s, Icon image_s) {
        this.itemID_s = itemID_s;
        this.itemName_s = itemName_s;
        this.description_s = description_s;
        this.price_s = price_s;
        this.brandName_s = brandName_s;
        this.image_s = image_s;
    }
    
    @Override
    public int hashCode() {
        return itemID_s + itemName_s.hashCode() + brandName_s.hashCode() + price_s;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Desert_data data_d = (Desert_data) obj;
        return itemID_s == data_d.itemID_s &&
                itemName_s.equals(data_d.getItemName_s()) &&
                brandName_s.equals(data_d.getBrandName_s()) &&
                price_s == data_d.price_s;
    }
}
