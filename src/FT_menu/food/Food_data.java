/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FT_menu.food;

import javax.swing.Icon;

/**
 *
 * @author mario
 */
public class Food_data {
    
    int itemID_f;
    String itemName_f;
    String description_f;
    int price_f;
    String brandName_f;
    Icon image_f;
    boolean moved;
 
    public Food_data() {
        
    }

    public int getItemID_f() {
        return itemID_f;
    }

    public void setItemID_f(int itemID_f) {
        this.itemID_f = itemID_f;
    }

    public String getItemName_f() {
        return itemName_f;
    }

    public void setItemName_f(String itemName_f) {
        this.itemName_f = itemName_f;
    }

    public String getDescription_f() {
        return description_f;
    }

    public void setDescription_f(String description_f) {
        this.description_f = description_f;
    }

    public int getPrice_f() {
        return price_f;
    }

    public void setPrice_f(int price_f) {
        this.price_f = price_f;
    }

    public String getBrandName_f() {
        return brandName_f;
    }

    public void setBrandName_f(String brandName_f) {
        this.brandName_f = brandName_f;
    }
    

    public Icon getImage_f() {
        return image_f;
    }

    public void setImage_f(Icon image_f) {
        this.image_f = image_f;
    }  

    public Food_data(int itemID_f, String itemName_f, String description_f, int price_f, String brandName_f, Icon image_f) {
        this.itemID_f = itemID_f;
        this.itemName_f = itemName_f;
        this.description_f = description_f;
        this.price_f = price_f;
        this.brandName_f = brandName_f;
        this.image_f = image_f;
    }
    
    @Override
    public int hashCode() {
        return itemID_f + itemName_f.hashCode() + brandName_f.hashCode() + price_f;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Food_data data_f = (Food_data) obj;
        return itemID_f == data_f.itemID_f &&
               itemName_f.equals(data_f.itemName_f) &&
               brandName_f.equals(data_f.brandName_f) &&
               price_f == data_f.price_f;                  
    }
}