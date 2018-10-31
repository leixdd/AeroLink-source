/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.HR2.Modals;

import FXMLS.HR2.ClassFiles.HR2_AssessmentClass;
import FXMLS.HR2.ClassFiles.HR2_EvaluationClass;
import FXMLS.HR2.ClassFiles.HR2_LMClass_For_AddQuestion_Modal;
import FXMLS.HR2.ClassFiles.HR2_LM_EditQuestion_for_Modal;
import FXMLS.HR2.ClassFiles.HR2_Training_InfoClass;
import Model.HR2_Assessment;
import Model.HR2_Evaluation;
import Synapse.Components.Modal.Modal;
import Synapse.Form;
import Synapse.Model;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.awt.Checkbox;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Eden Ramoneda
 */
public class HR2_View_ChoicesController implements Initializable {

    
    
    @FXML
    private TableView<HR2_EvaluationClass> tbl_choices;
    
    private TableColumn<HR2_EvaluationClass, Boolean> addButton = new TableColumn("Correct Answer");
    
    private TableColumn<HR2_EvaluationClass, String> col_choice = new TableColumn("Choice");
    
    private TableColumn<HR2_EvaluationClass, String> col_choice_description = new TableColumn("Choice Description");
    
    @FXML
    private JFXTextArea txt_question;
    ToggleGroup d = new ToggleGroup();
    @FXML
    private JFXButton btn_edit;
    @FXML
    private Label lbl_c;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbl_c.setText(HR2_LMClass_For_AddQuestion_Modal.lm_course_title);
        txt_question.setText(HR2_LMClass_For_AddQuestion_Modal.question);
        txt_question.setStyle("-fx-text-fill: #000; -fx-border-color: #1f1f14");
        loadData();
        DisplayDataInJTable();
    }

    public void loadData() {

        HR2_Evaluation c = new HR2_Evaluation();
        List choices = c.join(Model.JOIN.INNER, "aerolink.tbl_hr2_assessment", "question_id", "a", "=", "question_id")
                .where(new Object[][]{{"a.question", "=", txt_question.getText()}})
                .get("a.question,a.choice_id,aerolink.tbl_hr2_evaluation.choice,aerolink.tbl_hr2_evaluation.choice_description,aerolink.tbl_hr2_evaluation.ischecked");
        // .get("a.question,a.choice_id,aerolink.tbl_hr2_evaluation.choice,aerolink.tbl_hr2_evaluation.choice_description,IIF (aerolink.tbl_hr2_evaluation.ischecked = 1, '", d.selectToggle(value).toString(), "'' )as ischecked");
        Data(choices);

    }

    public void Data(List b) {
        ObservableList<HR2_EvaluationClass> obj = FXCollections.observableArrayList();
        obj.clear();
        try {
            for (Object d : b) {

                HashMap hm = (HashMap) d;
                System.out.println(hm);
                
                HR2_LM_EditQuestion_for_Modal.choice_description.put(String.valueOf(hm.get("choice")), String.valueOf(hm.get("choice_description")));

                obj.add(
                        new HR2_EvaluationClass(
                                String.valueOf(hm.get("question")),
                                String.valueOf(hm.get("choice_id")),
                                String.valueOf(hm.get("choice")),
                                String.valueOf(hm.get("choice_description")),
                                String.valueOf(hm.get("ischecked"))));
                
            
            }
            tbl_choices.setItems(obj);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void DisplayDataInJTable() {

        col_choice.setCellValueFactory(param -> param.getValue().choice);
        col_choice_description.setCellValueFactory(param -> param.getValue().choice_description);

        addButton.setCellValueFactory(value -> value.getValue().ischecked);
        addButton.setCellFactory(CheckBoxTableCell.forTableColumn(addButton));
//        addButton.setCellFactory(new Callback<TableColumn<HR2_EvaluationClass, Boolean>, TableCell<HR2_EvaluationClass, Boolean>>() {
//            @Override
//            public TableCell<HR2_EvaluationClass, Boolean> call(TableColumn<HR2_EvaluationClass, Boolean> param) {
//                final TableCell<HR2_EvaluationClass, Boolean> cell = new TableCell<HR2_EvaluationClass, Boolean>() {
//                    private final RadioButton rbtn = new RadioButton();
//
//                    {
//                        try {
//                            rbtn.setToggleGroup(d);
//                            System.out.println(param.getCellData(0));
//                            rbtn.setCursor(javafx.scene.Cursor.HAND);
//                        } catch (Exception ex) {
//                            System.out.println(ex);
//                        }
//                    }
//
//                    public void updateItem(Boolean item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(rbtn);
//                        }
//                    }
//
//                };
//                return cell;
//            }
//        });

        tbl_choices.getColumns().addAll(addButton, col_choice, col_choice_description);
    }

    @FXML
    public void EditQuestionModal() {
        HR2_LM_EditQuestion_for_Modal.init_Question(lbl_c.getText(),
                txt_question.getText());
        Modal updateQ = Modal.getInstance(new Form("/FXMLS/HR2/Modals/LM_EditQuestion.fxml").getParent());
        updateQ.open();

    }
}
