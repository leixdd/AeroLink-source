/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.Administrative;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Onodera-Chan
 */
public class AddfilesController implements Initializable {

    private Connection con = DBconnection.con();
    private PreparedStatement pst;
    private ResultSet rs;
    private String directory = "";
    private String directory1 = "src/FXMLS/Administrative/RetrievedFiles/";
    @FXML
    private JFXButton btntoarch;
    @FXML
    private JFXButton btntoarch1;
    @FXML
    private TextField adddocname;
    @FXML
    private JFXComboBox<String> addbox;
    ObservableList<String> addbox1 = FXCollections.observableArrayList("Contracts","Permit");
    @FXML
    private JFXTextField addpathtxt;
    @FXML
    private AnchorPane anchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addbox.setItems(addbox1);
    }    
     
    @FXML
    public void insertvalue(){
   
        try{
            String query = "insert into aerolink.admin_document_file values(?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, adddocname.getText());
            pst.setString(2, addbox.getSelectionModel().getSelectedItem());
               
            File f = new File(directory+addpathtxt.getText());
            FileInputStream fs = new FileInputStream(f);
            pst.setBinaryStream(3, fs);
            pst.setString(4, "Retrieved");
            java.util.Date d = new java.util.Date();
            long t = d.getTime();
            java.sql.Time st = new java.sql.Time(t);
            pst.setTime(5, st);
          
            java.sql.Date date = new java.sql.Date(t);
            pst.setDate(6, date);
            
            pst.execute();
            AlertBox.display("Alert", "Document is now Ready");
            AlertBox.close(btntoarch);
        }catch(Exception ex){
            AlertBox.display("Alert", "Data Cannot be Empty");
        }try{
            String sql = "select * from aerolink.admin_document_file where [Document Name]=  '"+adddocname.getText()+"'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            File fn = new File(directory1+adddocname.getText()+"-"+addbox.getSelectionModel().getSelectedItem()+".pdf");
            FileOutputStream out = new FileOutputStream(fn);
            
            while(rs.next()){
                 InputStream input = rs.getBinaryStream("Document File");
                 byte[] buffer = new byte[1024];
                 while(input.read(buffer) > 0){
                     out.write(buffer);
                 }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        
    }
    
    @FXML
    public void opendialog(){
            FileChooser fc = new FileChooser();
            FileChooser.ExtensionFilter exf1 = new FileChooser.ExtensionFilter("PDF Files", "*.pdf");
            fc.getExtensionFilters().add(exf1);
            File f = fc.showOpenDialog(null);
               
            if(f != null){
              
                addpathtxt.setText(""+f);
            }
            else{
               AlertBox.display("Alert", "No File Selected");
            }
    }
    
    @FXML
    public void cancel(){
        AlertBox.close(btntoarch1);
        AlertBox.display("Alert", "Adding Files has been Canceled");
    }
    
}
