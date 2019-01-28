/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.HR2;

import FXMLS.HR2.ClassFiles.CM_SkillReq_ModalClass;
import FXMLS.HR2.ClassFiles.CM_Skill_RequisitionClass;
import FXMLS.HR2.ClassFiles.HR2_CoursesClass;
import FXMLS.HR2.ClassFiles.HR2_LM_CourseOutlineModal;
import FXMLS.HR2.ClassFiles.HR2_TM_ViewTrainingInfo_Modal;
import FXMLS.HR2.ClassFiles.HR2_TrainingReq_Class;
import FXMLS.HR2.ClassFiles.HR4_Jobs_Class;
import Model.HR2_TM_Training_Requisition;
import Model.HR4_Departments;
import Model.HR4_Jobs;
import Synapse.Components.Modal.Modal;
import Synapse.Form;
import Synapse.Model;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private TableView<HR2_TrainingReq_Class> tbl_training_mngmt;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_tm_dept;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_tm_jp;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_tm_p;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_tm_from;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_tm_to;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_tm_pn_process;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_req_status;
    @FXML
    private ContextMenu CMenu;
    @FXML
    private MenuItem MI_more;
    @FXML
    private MenuItem MI_archive;
    @FXML
    private TableView<?> tbl_req_facility;
    @FXML
    private TableColumn<?, ?> col_req_facility;
    @FXML
    private TableColumn<?, ?> col_req_facilityImg;
    @FXML
    private JFXTextField txt_search_facilities;
    @FXML
    private TableView<?> tbl_req_vehicle;
    @FXML
    private TableColumn<?, ?> col_req_vehicleType;
    @FXML
    private TableColumn<?, ?> col_req_vehicleModel;
    @FXML
    private JFXTextField txt_search_vehicles;
    @FXML
    private Label lbl_training_req_notif;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_tm_trainor;
    @FXML
    private TableView<HR2_TrainingReq_Class> tbl_history_of_trainings;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_hs_dept;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_hs_jp;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_hs_p;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_hs_trainor;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_hs_from;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_hs_to;
    @FXML
    private TableColumn<HR2_TrainingReq_Class, String> col_hs_status;
    @FXML
    private JFXComboBox cbox_hs_dept;
    @FXML
    private JFXComboBox cbox_hs_jp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTrainingRequests();
        ForColumns();
        loadTrainingMngmt();
        loadHistoryOfTraining();
        int d = tbl_training_req.getItems().size();
        lbl_training_req_notif.setText(String.valueOf(d));
        DisplayDataInCB();
    }

    public void DisplayDataInCB() {
        HR4_Departments dept = new HR4_Departments();
        HR4_Jobs jobs = new HR4_Jobs();

        try {
            List c = dept.get();
            for (Object d : c) {
                HashMap hm1 = (HashMap) d;
                //RS
                //  cbox_department.getItems().add("DEPT" + hm1.get("id") + " - " + hm1.get("dept_name"));
                cbox_hs_dept.getItems().add(hm1.get("dept_name"));

            }
            List d = jobs.get();
            //"concat(substring(title,0,2), job_id) as job_id, title"
            for (Object e : d) {
                HashMap hm2 = (HashMap) e;
                //RS
                cbox_hs_jp.getItems().add(hm2.get("title"));
            }
            }catch (Exception e) {
            System.out.println(e);
        }

        }
        //for training mngmt.
    public void loadTrainingMngmt() {

        try {
            HR2_TM_Training_Requisition tr = new HR2_TM_Training_Requisition();
            List training_req = tr.join(Model.JOIN.INNER, "aerolink.tbl_hr4_department", "id", "dept", "=", "dept_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr4_jobs", "job_id", "j", "=", "job_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr2_request_status", "req_status_id", "rs", "=", "req_status_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr2_trainingInfo", "tr_id", "ti", "=", "tr_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr4_employee_profiles", "employee_code", "=", "ti", "trainor", true)
                    .where(new Object[][]{
                {"rs.req_status_id", "<>", "3"},
                {"aerolink.tbl_hr2_training_requisition.isDeleted", "<>", "1"}})
                    .orderBy("aerolink.tbl_hr2_training_requisition.date_requested", Model.Sort.ASC)
                    .get("aerolink.tbl_hr2_training_requisition.tr_id,dept.dept_name,j.title,training_title,no_of_participants,"
                            + "concat(aerolink.tbl_hr4_employee_profiles.firstname,' ',aerolink.tbl_hr4_employee_profiles.middlename,' ',aerolink.tbl_hr4_employee_profiles.lastname) as trainor,"
                            + "from_day, to_day, rs.req_status_id, rs.req_status");

            DisplayTrainingM(training_req);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public void loadHistoryOfTraining() {

        try {
            HR2_TM_Training_Requisition tr = new HR2_TM_Training_Requisition();
            List training_req_archive = tr.join(Model.JOIN.INNER, "aerolink.tbl_hr4_department", "id", "dept", "=", "dept_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr4_jobs", "job_id", "j", "=", "job_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr2_request_status", "req_status_id", "rs", "=", "req_status_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr2_trainingInfo", "tr_id", "ti", "=", "tr_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr4_employee_profiles", "employee_code", "=", "ti", "trainor", true)
                    .where(new Object[][]{{"ti.isDeleted", "<>", "0"}})
                    .orderBy("aerolink.tbl_hr2_training_requisition.date_requested", Model.Sort.ASC)
                    .get("aerolink.tbl_hr2_training_requisition.tr_id,dept.dept_name,j.title,training_title,no_of_participants,"
                            + "concat(aerolink.tbl_hr4_employee_profiles.firstname,' ',aerolink.tbl_hr4_employee_profiles.middlename,' ',aerolink.tbl_hr4_employee_profiles.lastname) as trainor,"
                            + "from_day, to_day, rs.req_status_id, rs.req_status");

            DisplayHistoryOfTraining(training_req_archive);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public void DisplayHistoryOfTraining(List m) {
        ObservableList<HR2_TrainingReq_Class> t_requests = FXCollections.observableArrayList();
        t_requests.clear();
        try {

            for (Object d : m) {
                HashMap hm1 = (HashMap) d;
                System.out.println("TRAINING MANAGEMENT" + d);
                t_requests.add(
                        new HR2_TrainingReq_Class(
                                String.valueOf(hm1.get("tr_id")),
                                String.valueOf(hm1.get("dept_name")),
                                String.valueOf(hm1.get("title")),
                                String.valueOf(hm1.get("training_title")),
                                String.valueOf(hm1.get("no_of_participants")),
                                String.valueOf(hm1.get("trainor")),
                                String.valueOf(hm1.get("from_day")),
                                String.valueOf(hm1.get("to_day")),
                                String.valueOf(hm1.get("reason")),
                                String.valueOf(hm1.get("req_status_id")),
                                String.valueOf(hm1.get("req_status")),
                                String.valueOf(hm1.get("requested_by")),
                                String.valueOf(hm1.get("date_requested"))
                        ));
            }

            tbl_history_of_trainings.setItems(t_requests);

        } catch (Exception e) {
            System.out.println(e);
        }
        System.err.println(tbl_history_of_trainings.getItems().size());
        tbl_history_of_trainings.getSelectionModel().selectFirst();
    }

    public void DisplayTrainingM(List m) {
        ObservableList<HR2_TrainingReq_Class> t_requests = FXCollections.observableArrayList();
        t_requests.clear();
        try {

            for (Object d : m) {
                HashMap hm1 = (HashMap) d;
                System.out.println("TRAINING MANAGEMENT" + d);
                t_requests.add(
                        new HR2_TrainingReq_Class(
                                String.valueOf(hm1.get("tr_id")),
                                String.valueOf(hm1.get("dept_name")),
                                String.valueOf(hm1.get("title")),
                                String.valueOf(hm1.get("training_title")),
                                String.valueOf(hm1.get("no_of_participants")),
                                String.valueOf(hm1.get("trainor")),
                                String.valueOf(hm1.get("from_day")),
                                String.valueOf(hm1.get("to_day")),
                                String.valueOf(hm1.get("reason")),
                                String.valueOf(hm1.get("req_status_id")),
                                String.valueOf(hm1.get("req_status")),
                                String.valueOf(hm1.get("requested_by")),
                                String.valueOf(hm1.get("date_requested"))
                        ));
            }

            tbl_training_mngmt.setItems(t_requests);

        } catch (Exception e) {
            System.out.println(e);
        }
        System.err.println(tbl_training_mngmt.getItems().size());
        tbl_training_mngmt.getSelectionModel().selectFirst();
    }

    //for tbl_training_req
    public void loadTrainingRequests() {

        try {
            HR2_TM_Training_Requisition tr = new HR2_TM_Training_Requisition();
            List training_req = tr.join(Model.JOIN.INNER, "aerolink.tbl_hr4_department", "id", "dept", "=", "dept_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr4_jobs", "job_id", "j", "=", "job_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr2_request_status", "req_status_id", "rs", "=", "req_status_id")
                    .join(Model.JOIN.INNER, "aerolink.tbl_hr4_employee_profiles", "employee_code", "ep", "=", "requested_by")
                    .where(new Object[][]{
                {"rs.req_status_id", "=", "3"},
                {"aerolink.tbl_hr2_training_requisition.isDeleted", "<>", "1"}
            })
                    .orderBy("aerolink.tbl_hr2_training_requisition.date_requested", Model.Sort.ASC)
                    .get("aerolink.tbl_hr2_training_requisition.tr_id,dept.dept_name, j.title, aerolink.tbl_hr2_training_requisition.training_title,aerolink.tbl_hr2_training_requisition.no_of_participants,"
                            + "aerolink.tbl_hr2_training_requisition.total_hours,aerolink.tbl_hr2_training_requisition.from_day, "
                            + "aerolink.tbl_hr2_training_requisition.to_day, aerolink.tbl_hr2_training_requisition.reason,rs.req_status,concat(ep.firstname, ' ',ep.middlename, ' ',ep.lastname) as requested_by,"
                            + "aerolink.tbl_hr2_training_requisition.date_requested,rs.req_status");

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
                                String.valueOf(hm1.get("req_status_id")),
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
        //for tbl_mngmt
        col_tm_dept.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().dept_name);
        col_tm_jp.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().title);
        col_tm_p.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().no_of_participants);
        col_tm_trainor.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().total_hours);
        col_tm_from.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().from_day);
        col_tm_to.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().to_day);
        col_tm_pn_process.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().request_status);
        //for tbl_history_of_trainings
        col_hs_dept.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().dept_name);
        col_hs_jp.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().title);
        col_hs_p.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().no_of_participants);
        col_hs_trainor.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().total_hours);
        col_hs_from.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().from_day);
        col_hs_to.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().to_day);
        col_hs_status.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().request_status);
        //for tbl_req
        col_req_dept.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().dept_name);
        col_req_jp.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().title);
        col_req_date_req.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().date_requested);
        col_req_status.setCellValueFactory((TableColumn.CellDataFeatures<HR2_TrainingReq_Class, String> param) -> param.getValue().request_status);
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

    @FXML
    public void ContextMenu(MouseEvent event) {

        if (event.getButton() == MouseButton.SECONDARY) {
            CMenu.show(tbl_training_mngmt, event.getX(), event.getSceneY());
            MI_more.setOnAction(e
                    -> {

                HR2_TM_ViewTrainingInfo_Modal.init_Trainings(
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().tr_id.getValue(),
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().dept_name.getValue(),
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().title.getValue(),
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().no_of_participants.getValue(),
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().total_hours.getValue(),
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().from_day.getValue(),
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().to_day.getValue(),
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().request_status_id.getValue(),
                        tbl_training_mngmt.getSelectionModel().getSelectedItem().request_status.getValue()
                );
                Modal moreDetails = Modal.getInstance(new Form("/FXMLS/HR2/Modals/TM_ViewTraining.fxml").getParent());
                moreDetails.open();
            }
            );
            MI_archive.setOnAction(e -> {

            });
        }

    }

}
