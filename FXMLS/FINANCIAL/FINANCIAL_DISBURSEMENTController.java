/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.FINANCIAL;

import FXMLS.FINANCIAL.CLASSFILES.Disbursement_Voucher_classfile;
import FXMLS.FINANCIAL.CLASSFILES.Disbursement_request_classfile;
import FXMLS.FINANCIAL.STATIC.CLASFILES.Finance_CreateVoucher;
import Model.Financial.Financial_budget_request;
import Model.Financial.Financial_disbursement_request_model;
import Model.Financial.Financial_disbursement_voucher;
import Synapse.Components.Modal.Modal;
import Synapse.Form;
import Synapse.Session;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Gilbert
 */
public class FINANCIAL_DISBURSEMENTController implements Initializable {

    @FXML
    private TableView<Disbursement_request_classfile> disbursement_tbl;
    @FXML
    private Label daterequest_label;
    @FXML
    private Label department_label;
    @FXML
    private Label claimant_label;
    @FXML
    private TextArea description_txtarea;
    @FXML
    private Label amount_label;
    @FXML
    private JFXButton dv_btn;
    @FXML
    private Label reqno_label;
    @FXML
    private Label prioritylvl_label;
    @FXML
    private Label status_label;
    @FXML
    private Label dr_id_label;
    @FXML
    private TableView<Disbursement_Voucher_classfile> dv_tbl;
    @FXML
    private JFXTextField srch_name_txt;
    @FXML
    private DatePicker srch_date;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem Voucher_menuItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // srch_date.setOnAction(e ->srchDate());
        //srch_name_txt.setOnKeyTyped(e -> srch());
        
        loadtable_disbursementrequest();
        AddtableColumn_disbursementrequest();
        
        AddtableColumn_disbursementVoucher();
        loadDisbursementVoucher();
        
        //contexmenu
        /*disbursement_tbl.setOnMouseClicked(value -> {
            if (!disbursement_tbl.isDisabled() || !disbursement_tbl.getItems().isEmpty()) {
                if (value.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(disbursement_tbl, value.getX(), value.getY());
                }

                if (value.getButton() == MouseButton.PRIMARY) {
                    if (value.getClickCount() == 2) {
                       viewingVoucher();
                    }
                }
            }
        });*/
        
         disbursement_tbl.setContextMenu(contextMenu);
         Voucher_menuItem.setOnAction(value -> viewingVoucher());
        
        
    }    
   
    public void viewingVoucher() {
        
        Finance_CreateVoucher.id = disbursement_tbl.getSelectionModel().getSelectedItem().drID.getValue();
        Finance_CreateVoucher.RequestNo = disbursement_tbl.getSelectionModel().getSelectedItem().drReqno.getValue();
        Finance_CreateVoucher.DateRequest = disbursement_tbl.getSelectionModel().getSelectedItem().drDatereq.getValue();
        Finance_CreateVoucher.Requestor = disbursement_tbl.getSelectionModel().getSelectedItem().drReqname.getValue();
        Finance_CreateVoucher.Department = disbursement_tbl.getSelectionModel().getSelectedItem().drDepartment.getValue();
        Finance_CreateVoucher.Description = disbursement_tbl.getSelectionModel().getSelectedItem().drDescription.getValue();
        Finance_CreateVoucher.PriorityLevel = disbursement_tbl.getSelectionModel().getSelectedItem().drprioritylvl.getValue();
        Finance_CreateVoucher.Amount = disbursement_tbl.getSelectionModel().getSelectedItem().drAmount.getValue();
        Finance_CreateVoucher.Status = disbursement_tbl.getSelectionModel().getSelectedItem().drdisbursementStatus.getValue();

        Modal md = Modal.getInstance(new Form("/FXMLS/FINANCIAL/CALLER/DISBURSEMENT_VOUCHER.fxml").getParent());
        md.open();
        //md.getF().getStage().setOnCloseRequest(event -> this.AddtableColumn_disbursementrequest());

    }
    
    /*
     public void srchDate(){
        Financial_disbursement_voucher fvm1 = new Financial_disbursement_voucher(); 

    //String SearchText = searchno.getText().equals("") ? "[a-z]" : searchno.getText();
        
        try {

            List listrec = fvm1.where(new Object[][]{
                {"dv_date_released","like", "%" + srch_date.getValue() + "%"}
            }).get();

            //VoucherRecord(listrec);
     
        } catch (Exception e) {
            System.out.println(e);
        }
    }*/
    /*
    
    public void srch(){
           dv_tbl.getItems().clear();
        Financial_disbursement_voucher fvm = new Financial_disbursement_voucher(); 

    String SearchText = srch_name_txt.getText().equals("") ? "[a-z]" : srch_name_txt.getText();
        
        try {

            List listrec = fvm.where(new Object[][]{
                {"dv_claimant","like", "%" + SearchText + "%"}
            }).get();

            VoucherRecord(listrec);
     
        } catch (Exception e) {
            System.out.println(e);
        }
          dv_tbl.setItems(dvc);
    }
    */
    public void AddtableColumn_disbursementVoucher(){
        dv_tbl.getItems().clear();
        dv_tbl.getColumns().removeAll(dv_tbl.getColumns());
        
        TableColumn<Disbursement_Voucher_classfile, String> dreq = new TableColumn<>("Date Request");
        TableColumn<Disbursement_Voucher_classfile, String> de = new TableColumn<>("Department");
        TableColumn<Disbursement_Voucher_classfile, String> des = new TableColumn<>("Description");
        TableColumn<Disbursement_Voucher_classfile, String> c = new TableColumn<>("Claimant");
        TableColumn<Disbursement_Voucher_classfile, String> a = new TableColumn<>("Amount");
        TableColumn<Disbursement_Voucher_classfile, String> rb = new TableColumn<>("Received By.");
        TableColumn<Disbursement_Voucher_classfile, String> drel = new TableColumn<>("Date Released");
       
        dreq.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_Voucher_classfile, String> param) -> param.getValue().dvdreq);
        de.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_Voucher_classfile, String> param) -> param.getValue().dvdep);
        des.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_Voucher_classfile, String> param) -> param.getValue().dvdes);
        c.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_Voucher_classfile, String> param) -> param.getValue().dvclaimant);
        a.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_Voucher_classfile, String> param) -> param.getValue().dvamount);
        rb.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_Voucher_classfile, String> param) -> param.getValue().dvreceivedby);
        drel.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_Voucher_classfile, String> param) -> param.getValue().dvdatereleased);
        
        dv_tbl.getColumns().addAll(dreq,de,des,c,a,rb,drel);
    
    }
    
    ObservableList<Disbursement_Voucher_classfile> dvc = FXCollections.observableArrayList();
    Financial_disbursement_voucher fdv = new Financial_disbursement_voucher();
    public void loadDisbursementVoucher(){
         CompletableFuture.supplyAsync(() -> {

            while (Session.CurrentRoute.equals("id_disbursement")) {
                
                try {
                    fdv.get("CHECKSUM_AGG(BINARY_CHECKSUM(*)) as chk").stream().forEach(e -> {
                        DummyCount = Long.parseLong(((HashMap) e).get("chk").toString());
                    });

                    if (DummyCount != GlobalCount) {

                        dv_tbl.getItems();
                            List b = fdv.get("budget_date_req as budget_date_req",
                                    "dv_department as dv_department",
                                    "dv_description as dv_description",
                                    "dv_claimant as dv_claimant",
                                    "dv_amount as dv_amount",
                                    "dv_received_by as dv_received_by",
                                    "CONCAT(dv_disbursed_status, ' - ', dv_date_released) as dv_date_released");
                            VoucherRecord(b);
                     {
                         
                    }
                    dv_tbl.setItems(dvc);
                        GlobalCount = DummyCount;
                    }
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FINANCIAL_BUDGET_MANAGEMENTController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return 0;
        }, Session.SessionThreads);

     
    }
    
    private void VoucherRecord(List voucher){
         dv_tbl.getItems().clear();

        try {
             
              for(Object d : voucher)
            {
                HashMap hm = (HashMap) d; 
                //exquisite casting
                hm.get("budget_date_req");
                hm.get("dv_department");
                hm.get("dv_description");
                hm.get("dv_claimant");
                hm.get("dv_amount");
                hm.get("dv_received_by");
                hm.get("dv_date_released");
                
               dvc.add(new Disbursement_Voucher_classfile(
                            String.valueOf(hm.get("budget_date_req")),
                            String.valueOf(hm.get("dv_department")),
                            String.valueOf(hm.get("dv_description")),
                            String.valueOf(hm.get("dv_claimant")),
                            String.valueOf(hm.get("dv_amount")),
                            String.valueOf(hm.get("dv_received_by")),
                            String.valueOf(hm.get("dv_date_released"))
                ) );   
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        dv_tbl.setItems(dvc);

     
    }
    
  
    
    
     public void AddtableColumn_disbursementrequest(){
        
        disbursement_tbl.getItems().clear();
        disbursement_tbl.getColumns().removeAll(disbursement_tbl.getColumns());
        
        
        TableColumn<Disbursement_request_classfile, String> drreqno = new TableColumn<>("Request No");
        TableColumn<Disbursement_request_classfile, String> drdatereq = new TableColumn<>("Date Request");
        TableColumn<Disbursement_request_classfile, String> drrequestor = new TableColumn<>("Requestor");
        TableColumn<Disbursement_request_classfile, String> drdep = new TableColumn<>("Department");
        TableColumn<Disbursement_request_classfile, String> drdes = new TableColumn<>("Description");
        TableColumn<Disbursement_request_classfile, String> dramnt = new TableColumn<>("Amount");
        TableColumn<Disbursement_request_classfile, String> drstat = new TableColumn<>("Status");
       
        
        drreqno.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_request_classfile, String> param) -> param.getValue().drReqno);
        drdatereq.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_request_classfile, String> param) -> param.getValue().drDatereq);
        drrequestor.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_request_classfile, String> param) -> param.getValue().drReqname);
        drdep.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_request_classfile, String> param) -> param.getValue().drDepartment);
        drdes.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_request_classfile, String> param) -> param.getValue().drDescription);
        dramnt.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_request_classfile, String> param) -> param.getValue().drAmount);
        drstat.setCellValueFactory((TableColumn.CellDataFeatures<Disbursement_request_classfile, String> param) -> param.getValue().drdisbursementStatus);
        
        disbursement_tbl.getColumns().addAll(drreqno,drdatereq,drrequestor,drdep,drdes,dramnt,drstat);
    
    }
    
    
     long DummyCount = 0;
     long GlobalCount = 0;
     int Global_Count = 0;
     ExecutorService e = Executors.newFixedThreadPool(1);   
    ObservableList<Disbursement_request_classfile> brc = FXCollections.observableArrayList();
    Financial_disbursement_request_model drm = new Financial_disbursement_request_model();
    
    
    public void loadtable_disbursementrequest(){
        
          CompletableFuture.supplyAsync(() -> {

            while (Session.CurrentRoute.equals("id_disbursement")) {
                
                try {
                    drm.get("CHECKSUM_AGG(BINARY_CHECKSUM(*)) as chk").stream().forEach(e -> {
                        DummyCount = Long.parseLong(((HashMap) e).get("chk").toString());
                    });

                    if (DummyCount != GlobalCount) {

                        disbursement_tbl.getItems();
                            List b = drm.where(new Object[][]{
                             {"dr_status","=","Pending"}
                    }).get();
                            request(b);
                            
                     {
                         
                    }
                    disbursement_tbl.setItems(brc);
                        GlobalCount = DummyCount;
                    }
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FINANCIAL_BUDGET_MANAGEMENTController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return 0;
        }, Session.SessionThreads);

     }
     
     private void request(List req){
         
        disbursement_tbl.getItems().clear();
        ObservableList<Disbursement_request_classfile> fdrc = FXCollections.observableArrayList();

        try {
             
              for(Object d : req)
            {
                HashMap hm = (HashMap) d; 
                //exquisite casting
                 hm.get("dr_id");
                hm.get("dr_requestno");
                hm.get("dr_daterequest");
                hm.get("dr_department");
                hm.get("dr_requestor");
                hm.get("dr_description");
                hm.get("dr_prioritylvl");
                hm.get("dr_amount");
                hm.get("dr_status");
                
               brc.add(new Disbursement_request_classfile(
                            String.valueOf(hm.get("dr_id")),
                            String.valueOf(hm.get("dr_requestno")),
                            String.valueOf(hm.get("dr_daterequest")),
                            String.valueOf(hm.get("dr_requestor")),
                            String.valueOf(hm.get("dr_department")),
                            String.valueOf(hm.get("dr_description")),
                            String.valueOf(hm.get("dr_prioritylvl")),
                            String.valueOf(hm.get("dr_amount")),
                            String.valueOf(hm.get("dr_status"))
                ) );   
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        disbursement_tbl.setItems(fdrc);

     }
}
