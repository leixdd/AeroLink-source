/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.HR2.ClassFiles;

import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author EdenRamoneda
 */
public class HR2_TM_ViewTrainingInfo_Modal {
    //shared data when the button clicked in training status table 
    public static String t_id, tr_id, department, job_position, participants, employee_code, trainor, from_day, to_day, status_id, status;

    public static void init_Trainings(String tid, String trid, String d,
            String jp, String p, String ep, String t, String fd, String td, String si, String s) {
        t_id = tid;
        tr_id = trid;
        department = d;
        job_position = jp;
        participants = p;
        employee_code = ep;
        trainor = t;
        from_day = fd;
        to_day = td;
        status_id = si;
        status = s;
    }
    
    
  /*  public SimpleStringProperty training_id,job_position,training_title,trainor,
            start_date,end_date,start_time,end_time,type,location,vehicle,budget_cost;

    public HR2_TM_ViewTrainingInfo_Modal(String training_id, String job_position, 
            String training_title, String trainor, String start_date,String end_date,
            String start_time, String end_time, String type,
            String location, String vehicle, String budget_cost) {
        this.training_id = new SimpleStringProperty(training_id);
        this.job_position = new SimpleStringProperty(job_position);
        this.training_title = new SimpleStringProperty(training_title);
        this.trainor = new SimpleStringProperty(trainor);
        this.start_date = new SimpleStringProperty(start_date);
        this.end_date = new SimpleStringProperty(end_date);
        this.start_time = new SimpleStringProperty(start_time);
        this.end_time = new SimpleStringProperty(end_time);
        this.type = new SimpleStringProperty(type);
        this.location = new SimpleStringProperty(location);
        this.vehicle = new SimpleStringProperty(vehicle);
        this.budget_cost = new SimpleStringProperty(budget_cost);
    }
    
   */
    
}
