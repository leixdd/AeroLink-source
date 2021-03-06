/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.HR2.Modals;

import FXMLS.HR2.ClassFiles.HR2_CM_Skills_Class_for_Modal;
import FXMLS.HR2.ClassFiles.HR2_TM_ViewTrainingInfo_Modal;
import FXMLS.HR2.ClassFiles.HR2_Training_InfoClass;
import FXMLS.HR2.ClassFiles.TM_ViewParticipantsClass;
import Model.Financial.Financial_budget_request;
import Model.HR2_RequestStatus;
import Model.HR2_TM_TrainingInfo;
import Model.HR2_TM_Training_Requisition;
import Model.HR2_Temp_Employee_Profiles;
import Model.HR2_Temp_Facilities;
import Model.HR2_Temp_Vehicles;
import Model.HR2_Type_of_Training;
import Model.HR4_Jobs;
import Synapse.Components.Modal.Modal;
import Synapse.Form;
import Synapse.Model;
import Synapse.STORED_PROC;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author EdenRamoneda
 */
public class TM_ViewTrainingController implements Initializable {

    private JFXTextField txt_edit_training_title;
    @FXML
    private JFXComboBox cbox_edit_trainor;
    @FXML
    private JFXComboBox cbox_edit_type;
    @FXML
    private JFXComboBox cbox_edit_v;
    @FXML
    private JFXComboBox cbox_edit_title;
    @FXML
    private JFXButton btn_view_participants;
    @FXML
    private JFXComboBox cbox_edit_dept;
    @FXML
    private JFXTextField txt_date_requested;
    @FXML
    private JFXTextField txt_from_day;
    @FXML
    private JFXTextField txt_to_day;
    @FXML
    private JFXButton btn_submit;
    @FXML
    private JFXTextField txt_training_title;
    @FXML
    private JFXTextField txt_hrs;
    @FXML
    private JFXTextArea txt_reason;
    @FXML
    private JFXTextField txt_req_by;
    @FXML
    private JFXTimePicker txt_from_time;
    @FXML
    private JFXTimePicker txt_to_time;
    @FXML
    private JFXComboBox cbox_edit_venue;
    @FXML
    private JFXTextField txt_participants;
    @FXML
    private JFXComboBox cbox_edit_status;
    @FXML
    private JFXComboBox cbox_edit_budget;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbox_edit_title.getItems().add(HR2_TM_ViewTrainingInfo_Modal.job_position);
        cbox_edit_title.getSelectionModel().selectFirst();
        cbox_edit_trainor.getItems().add(HR2_TM_ViewTrainingInfo_Modal.employee_code + " - " + HR2_TM_ViewTrainingInfo_Modal.trainor);
        cbox_edit_trainor.getSelectionModel().selectFirst();
        txt_from_day.setText(HR2_TM_ViewTrainingInfo_Modal.from_day);
        txt_to_day.setText(HR2_TM_ViewTrainingInfo_Modal.to_day);
        // txt_from_day.setValue(LocalDate.parse(HR2_TM_ViewTrainingInfo_Modal.from_day));
        // txt_to_day.setValue(LocalDate.parse(HR2_TM_ViewTrainingInfo_Modal.to_day));
        txt_participants.setText(HR2_TM_ViewTrainingInfo_Modal.participants);
        /* cbox_edit_status.getItems().add("S00" + HR2_TM_ViewTrainingInfo_Modal.status_id + " - " + HR2_TM_ViewTrainingInfo_Modal.status);
        cbox_edit_status.getSelectionModel().selectFirst();*/
        loadDataInComboBoxes();
        loadData();
    }

    @FXML
    public void viewParticipants() {
        TM_ViewParticipantsClass.getJobPosition(cbox_edit_title.getSelectionModel().getSelectedItem().toString());
        Modal viewParticipants = Modal.getInstance(new Form("/FXMLS/HR2/Modals/TM_ViewParticipants.fxml").getParent());
        viewParticipants.open();
    }

    public void loadDataInComboBoxes() {

        HR2_Temp_Employee_Profiles trainors = new HR2_Temp_Employee_Profiles();
        HR2_Type_of_Training type_of_training = new HR2_Type_of_Training();
        HR2_Temp_Vehicles vehicles = new HR2_Temp_Vehicles();
        HR2_RequestStatus rst = new HR2_RequestStatus();
        HR2_Temp_Facilities facilities = new HR2_Temp_Facilities();
        Financial_budget_request fbr = new Financial_budget_request();
        List set_trainors = trainors.get();

        for (Object e : set_trainors) {
            HashMap hm2 = (HashMap) e;
            //RS
            cbox_edit_trainor.getItems().add(hm2.get("employee_code") + " - " + hm2.get("firstname") + " " + hm2.get("middlename") + " " + hm2.get("lastname"));
        }

        List set_type_of_training = type_of_training.get();

        for (Object f : set_type_of_training) {
            HashMap hm3 = (HashMap) f;
            //RS
            cbox_edit_type.getItems().add("TM" + hm3.get("type_of_training_id") + " - " + hm3.get("type_of_training"));
        }

        List set_venue = facilities.get();

        for (Object venue : set_venue) {
            HashMap hmVenue = (HashMap) venue;
            //RS
            cbox_edit_venue.getItems().add("F00" + hmVenue.get("FacilityID") + " - " + hmVenue.get("FacilityName"));
        }

        List set_vehicles = vehicles.get();

        for (Object g : set_vehicles) {
            HashMap hm4 = (HashMap) g;
            //RS
            cbox_edit_v.getItems().add("V" + hm4.get("VehicleID") + " - " + hm4.get("VehicleModel"));
        }
        List req_status = rst.get();

        for (Object h : req_status) {
            HashMap hm5 = (HashMap) h;
            //RS
            cbox_edit_status.getItems().add("S00" + hm5.get("req_status_id") + " - " + hm5.get("req_status"));
        }
        List budget = fbr.where(new Object[][]{
            {"budget_department", "=", "Human Resource - Training Management"},
            {"budget_status", "=", "Approved"}
        }).get();

        for (Object training_budget : budget) {
            HashMap hm6 = (HashMap) training_budget;
            cbox_edit_budget.getItems().add("B00" + hm6.get("budget_id") + " - " + hm6.get("budget_description"));
        }
    }

    public void loadData() {

        HR2_TM_Training_Requisition tmtrc = new HR2_TM_Training_Requisition();

        List training_data = tmtrc.join(Model.JOIN.INNER, "aerolink.tbl_hr2_trainingInfo", "tr_id", "ti", "=", "tr_id")
                .join(Model.JOIN.INNER, "aerolink.tbl_log1_AssetFacility", "FacilityID", "=", "ti", "facility_id", true)
                .join(Model.JOIN.INNER, "aerolink.tbl_log1_AssetVehicles", "VehicleID", "=", "ti", "vehicle_id", true)
                .join(Model.JOIN.INNER, "aerolink.tbl_financials_request_budget", "budget_id", "=", "ti", "budget_id", true)
                .join(Model.JOIN.INNER, "aerolink.tbl_hr4_employee_profiles", "employee_code", "emp", "=", "requested_by")
                .join(Model.JOIN.INNER, "aerolink.tbl_eis_requisition", "request_id", "rs", "=", "request_id")
                .join(Model.JOIN.INNER, "aerolink.tbl_eis_request_status", "req_status_id", "=", "rs", "request_status", true)
                .join(Model.JOIN.INNER, "aerolink.tbl_hr2_type_of_training", "type_of_training_id", "=", "ti", "type_of_training_id", true)
                .where(new Object[][]{{"aerolink.tbl_hr2_training_requisition.tr_id", "=", HR2_TM_ViewTrainingInfo_Modal.tr_id}})
                .get("aerolink.tbl_hr2_training_requisition.tr_id,aerolink.tbl_hr2_training_requisition.training_title,aerolink.tbl_hr2_training_requisition.total_hours, reason, concat('S00',aerolink.tbl_eis_request_status.req_status_id,' - ',aerolink.tbl_eis_request_status.req_status)as request_status,"
                        + "concat(emp.employee_code,' - ',emp.firstname,' ',emp.middlename,' ',emp.lastname) as requested_by,"
                        + "date_requested,concat(emp.employee_code,' - ',emp.firstname,' ',emp.middlename,' ',emp.lastname) as trainor, ti.start_time, ti.end_time,"
                        + "CONCAT('TM',aerolink.tbl_hr2_type_of_training.type_of_training_id,' - ',aerolink.tbl_hr2_type_of_training.type_of_training) as type_of_training,"
                        + "concat('F00',aerolink.tbl_log1_AssetFacility.FacilityID,' - ',aerolink.tbl_log1_AssetFacility.FacilityName)as facility,"
                        + "concat('V',aerolink.tbl_log1_AssetVehicles.VehicleID,' - ',aerolink.tbl_log1_AssetVehicles.VehicleModel)as vehicle,"
                        + "concat('B00',aerolink.tbl_financials_request_budget.budget_id,' - ',aerolink.tbl_financials_request_budget.budget_description)as budget_cost"
                );
        Data(training_data);

    }

    public void Data(List b) {
        b.stream().forEach(row -> {
            txt_date_requested.setText(((HashMap) row).get("date_requested").toString());
            txt_training_title.setText(((HashMap) row).get("training_title").toString());
            txt_hrs.setText(((HashMap) row).get("total_hours").toString());
            txt_reason.setText(((HashMap) row).get("reason").toString());
            cbox_edit_status.setValue(((HashMap) row).get("request_status").toString());
            txt_req_by.setText(((HashMap) row).get("requested_by").toString());
            cbox_edit_trainor.setValue(((HashMap) row).get("trainor").toString());
            txt_from_time.setValue(LocalTime.parse(((HashMap) row).get("start_time").toString()));
            txt_to_time.setValue(LocalTime.parse(((HashMap) row).get("end_time").toString()));
            cbox_edit_type.setValue(((HashMap) row).get("type_of_training").toString());
            cbox_edit_venue.setValue(((HashMap) row).get("facility").toString());
            cbox_edit_v.setValue(((HashMap) row).get("vehicle").toString());
            cbox_edit_budget.setValue(((HashMap) row).get("budget_cost").toString());
            cbox_edit_status.setValue(((HashMap) row).get("request_status").toString());

        });
    }

    String rq_id = "";

    @FXML
    public void UpdateData() {
        try {
            Alert update = new Alert(Alert.AlertType.CONFIRMATION);
            update.setContentText("Are you sure you want to update this data?");
            Optional<ButtonType> rs = update.showAndWait();

            if (rs.get() == ButtonType.OK) {
                HR2_TM_Training_Requisition tmtr = new HR2_TM_Training_Requisition();
                List<HashMap> s = tmtr.where(new Object[][]{{"tr_id", "=", HR2_TM_ViewTrainingInfo_Modal.tr_id}}).get();

                s.stream().forEach((HashMap hash) -> {
                    rq_id = hash.get("request_id").toString();
                });
                STORED_PROC.executeCall("EIS_updateRequestStatus", new Object[][]{
                    {"request_id", rq_id},
                    {"request_status", cbox_edit_status.getSelectionModel().getSelectedItem().toString().substring(3).split(" - ")[0]} //1 kapag approved, 2 kapag denied, 3 on process
                });
                HR2_TM_TrainingInfo tm = new HR2_TM_TrainingInfo();
                Boolean a = tm.where(new Object[][]{
                    {"t_id", "=", HR2_TM_ViewTrainingInfo_Modal.t_id}
                }).update(new Object[][]{
                    {"trainor", cbox_edit_trainor.getSelectionModel().getSelectedItem().toString().split(" - ")[0]},
                    {"start_time", txt_from_time.getValue().toString()},
                    {"end_time", txt_to_time.getValue().toString()},
                    {"type_of_training_id", cbox_edit_type.getSelectionModel().getSelectedItem().toString().substring(2).split(" - ")[0]},
                    {"facility_id", cbox_edit_venue.getSelectionModel().getSelectedItem().toString().substring(3).split(" - ")[0]},
                    {"vehicle_id", cbox_edit_v.getSelectionModel().getSelectedItem().toString().substring(1).split(" - ")[0]},
                    {"budget_id", cbox_edit_budget.getSelectionModel().getSelectedItem().toString().substring(3).split(" - ")[0]}
                }).executeUpdate();
                Alert dropnotif = new Alert(Alert.AlertType.INFORMATION);
                dropnotif.setContentText(cbox_edit_title.getSelectionModel().getSelectedItem().toString() + " Successfully Updated");
                dropnotif.showAndWait();
                //loadData();
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
