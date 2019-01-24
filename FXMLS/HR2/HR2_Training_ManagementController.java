/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.HR2;

import FXMLS.HR2.ClassFiles.HR2_CoursesClass;
import FXMLS.HR2.ClassFiles.HR2_LM_CourseOutlineModal;
import FXMLS.HR2.ClassFiles.HR2_TrainingReq_Class;
import FXMLS.HR2.ClassFiles.HR4_Jobs_Class;
import Model.HR2_TM_Training_Requisition;
import Synapse.Components.Modal.Modal;
import Synapse.Form;
import Synapse.Model;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author EdenRamoneda
 */
public class HR2_Training_ManagementController implements Initializable {

    @FXML
    private TableView<HR2_TrainingReq_Class> tbl_training_req;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_req_dept;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_req_jp;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_req_date_req;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTrainingRequests();
        ForColumns();
    }

    public void loadTrainingRequests() {

        try {
            HR2_TM_Training_Requisition tr = new HR2_TM_Training_Requisition();
            List training_req = tr.join(Model.JOIN.INNER, "aerolink.tbl_hr4_department", "id", "dept", "=", "dept_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr4_jobs", "job_id", "j", "=", "job_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr2_request_status", "req_status_id", "rs", "=", "req_status_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr4_employee_profiles", "employee_code", "ep", "=", "requested_by")
                    .where(new Object[][]{{"aerolink.tbl_hr2_training_requisition.isDeleted", "<>", "1"}})
                    .orderBy("j.title", Model.Sort.ASC)
                    .get("aerolink.tbl_hr2_training_requisition.tr_id,dept.dept_name, j.title, aerolink.tbl_hr2_training_requisition.training_title,aerolink.tbl_hr2_training_requisition.no_of_participants,"
                            + "aerolink.tbl_hr2_training_requisition.total_hours,aerolink.tbl_hr2_training_requisition.from_day, "
                            + "aerolink.tbl_hr2_training_requisition.to_day, aerolink.tbl_hr2_training_requisition.reason,rs.req_status,concat(ep.firstname, ' ',ep.middlename, ' ',ep.lastname) as requested_by,"
                            + "aerolink.tbl_hr2_training_requisition.date_requested");

            DisplayTrainingReq(training_req);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public void DisplayTrainingReq(List a) {
        ObservableList<HR2_TrainingReq_Class> t_requests = FXCollections.observableArrayList();
        t_requests.clear();
        try {

            for (Object d : a) {
                HashMap hm1 = (HashMap) d;

                t_requests.add(
                        new HR2_TrainingReq_Class(
                                String.valueOf(hm1.get("tr_id")),
                                String.valueOf(hm1.get("dept_name")),
                                String.valueOf(hm1.get("title")),
                                String.valueOf(hm1.get("training_title")),
                                String.valueOf(hm1.get("no_of_participants")),
                                String.valueOf(hm1.get("total_hours")),
                                String.valueOf(hm1.get("from_day")),
                                String.valueOf(hm1.get("to_day")),
                                String.valueOf(hm1.get("reason")),
                                String.valueOf(hm1.get("req_status")),
                                String.valueOf(hm1.get("requested_by")),
                                String.valueOf(hm1.get("date_requested"))
                        ));
            }

            tbl_training_req.setItems(t_requests);

        } catch (Exception e) {
            System.out.println(e);
        }
        System.err.println(tbl_training_req.getItems().size());
        tbl_training_req.getSelectionModel().selectFirst();
    }

    public void ForColumns() {
        col_req_dept.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().dept_name);
        col_req_jp.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().title);
        col_req_date_req.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().date_requested);
        TableColumn<HR2_TrainingReq_Class, Void> MB = new TableColumn("Action");

        Callback<TableColumn<HR2_TrainingReq_Class, Void>, TableCell<HR2_TrainingReq_Class, Void>> cellFactory
                = new Callback<TableColumn<HR2_TrainingReq_Class, Void>, TableCell<HR2_TrainingReq_Class, Void>>() {
            @Override
            public TableCell<HR2_TrainingReq_Class, Void> call(final TableColumn<HR2_TrainingReq_Class, Void> param) {

                final TableCell<HR2_TrainingReq_Class, Void> cell = new TableCell<HR2_TrainingReq_Class, Void>() {
                    private final Button more_btn = new Button("More");

                    {
                        try {
                            more_btn.setOnAction(e
                                    -> {

                                HR2_TrainingReq_Class tr1 = (HR2_TrainingReq_Class) getTableRow().getItem();
                                Modal lq = Modal.getInstance(new Form("/FXMLS/HR2/Modals/LM_CourseOutline.fxml").getParent());
                                lq.open();
                            });
                            more_btn.setStyle("-fx-text-fill: #fff; -fx-background-color:#00cc66");
                            more_btn.setCursor(javafx.scene.Cursor.HAND);
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                    }

                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(more_btn);
                        }
                    }
                };
                return cell;
            }

        };

        MB.setCellFactory(cellFactory);
        tbl_training_req.getColumns().add(MB);
    }

}