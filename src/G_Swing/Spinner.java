/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package G_Swing;

import G_Swing.SpinnerUI;
import javax.swing.JSpinner;

/**
 *
 * @author mario
 */
public class Spinner extends JSpinner {
    
    public void setLableText(String text) {
        SpinnerUI.Editor editor = (SpinnerUI.Editor)getEditor();
        editor.setLabelText(text);
    }

    public String getLableText() {
        SpinnerUI.Editor editor = (SpinnerUI.Editor)getEditor();
        return editor.getLabelText();
    }
    
    public Spinner() {
       
       setOpaque(false);
       setUI (new SpinnerUI());   
    }
}
