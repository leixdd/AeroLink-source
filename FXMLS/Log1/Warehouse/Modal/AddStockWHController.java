
package FXMLS.Log1.Warehouse.Modal;

//import FXMLS.Log1.ClassFiles.Log1_fullInventoryList;
import FXMLS.Log1.ClassFiles.Log1_WarehouseItemsClassfiles;
import FXMLS.Log1.util.AlertMaker;
import Model.Log1.Log1_WarehouseActivityLogModel;
import Model.Log1.Log1_WarehouseItemModel;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddStockWHController implements Initializable {

    @FXML
    private Label stock_txt;
    @FXML
    private Label status_txt;
    @FXML
    private Label itemLoc_txt;
    @FXML
    private Button addStock_btn;
    @FXML
    private Label date_txt;
    @FXML
    private Label time_txt;
    @FXML
    private Label critQty_txt;
    @FXML
    private TextField valueAdded_txt;
    @FXML
    private TextField addedby_txt;
    @FXML
    private Label itemName_txt;
    @FXML
    private Label ItemID_txt;
    @FXML
    private Button cancel_btn;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayCurrentDate();
        cancel_btn.setOnMouseClicked(e-> close());
        addStock_btn.setOnMouseClicked(e->addStock());
        
        valueAdded_txt.setOnKeyTyped(value -> {
            System.err.println(value.getCharacter());
            if (!Character.isDigit(value.getCharacter().charAt(0))) {
                value.consume();
            }
        });
    }    
    public void displayCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
//      get current date time with Date()
        Date date = new Date();
        date_txt.setText(dateFormat.format(date));
        
        Calendar cal = Calendar.getInstance();
        time_txt.setText(timeFormat.format(cal.getTime()));
    }
    public void clearFields(){
        valueAdded_txt.setText("");
        itemName_txt.setText("");
        stock_txt.setText("");
        status_txt.setText("");
        itemLoc_txt.setText("");
        critQty_txt.setText("");
    }

    public void close() {
        clearFields();
        Stage stage = (Stage) ItemID_txt.getScene().getWindow();
        stage.close();
    }
    
    
    
    public void populateFields(Log1_WarehouseItemsClassfiles item) {
        itemName_txt.setText(item.getItemName());
        stock_txt.setText(item.getItemStock());
        status_txt.setText(item.getItemStatus());
        itemLoc_txt.setText(item.getItemLocation());
        critQty_txt.setText(item.getItemCriticalLevel());
        ItemID_txt.setText(item.getItemID());
    }
    
    
    public void addStock(){
        String input = valueAdded_txt.getText();
        
        Boolean flag = input.isEmpty();
        
        
        int currentStock = Integer.parseInt(stock_txt.getText());
        int addedStock=Integer.parseInt(valueAdded_txt.getText());
            
        int newCurrentStock = Integer.valueOf(currentStock+addedStock);
        String forDB = String.valueOf(currentStock+addedStock);
        
//        StockQuantityPlusEnterAmount_txt.setText(answer);
//        int w=Integer.parseInt(StockQuantityPlusEnterAmount_txt.getText());
        int z =Integer.parseInt(critQty_txt.getText());
        
            if(newCurrentStock <= z){
                status_txt.setText("Need to Reorder!");
            }else{
                status_txt.setText("Good in Stock");
            }
            
        Log1_WarehouseItemModel itemDB = new Log1_WarehouseItemModel();  
        Log1_WarehouseActivityLogModel actLogDB = new Log1_WarehouseActivityLogModel();
        
        if(!flag){
            try{
                String [][] actlog_data ={
                    {"ActivityUser", addedby_txt.getText()},
                    {"ActivityItem",itemName_txt.getText()},
                    {"ActivityItemStock", stock_txt.getText()},
                    {"ActivityAction", "Added"},
                    {"ActivityValue", valueAdded_txt.getText()},
                    {"ActivityItemStockRemaining",forDB},
                    {"ActivityPurpose", "Simple Add"},
                    {"ActivityDate", date_txt.getText()},
                    {"ActivityTime",time_txt.getText()},
                };
                if(actLogDB.insert(actlog_data)){
                    AlertMaker.showSimpleAlert(null,"Operation Success!");
                    valueAdded_txt.setText("");
                    itemDB.update(new Object [][]{
                        {"ItemStock", forDB},
                        {"ItemStatus", status_txt.getText()},
                    }).where(new Object [][]{
                        {"ItemID","=",ItemID_txt.getText()}
                    }).executeUpdate();
                    clearFields();
                    close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            AlertMaker.showErrorMessage("", "Please, enter a value.");
        }
        
    }

}
