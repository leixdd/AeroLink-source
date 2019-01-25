/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.Core2.Change;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author JPEG
 */
public class SPviewReportController implements Initializable {

    @FXML
    private AnchorPane SProotPane;
    @FXML
    private JFXButton SPviewM;
    @FXML
    private Label branchCount1;
    @FXML
    private Label branchCount11;
    @FXML
    private Label branchCount12;
    @FXML
    private Label branchCount13;
    @FXML
    private Label branchCount111;
    @FXML
    private Label branchCount121;
    @FXML
    private Label branchCount14;
    @FXML
    private Label branchCount112;
    @FXML
    private Label branchCount122;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void viewM(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXMLS/Core2/ServiceProvider.fxml"));
        SProotPane.getChildren().setAll(pane);
    }

    @FXML
    private void viewDashboard(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXMLS/Core2/Dashboard.fxml"));
        SProotPane.getChildren().setAll(pane);
    }

}
