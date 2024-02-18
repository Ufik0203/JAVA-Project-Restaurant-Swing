package Test;

public class pdata {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // Metode untuk memperbarui nilai data berdasarkan JTextField
    public void updateDataFromTextField(javax.swing.JTextField textField) {
        setData(textField.getText());
    }
}
