/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.HR2.ClassFiles;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author EdenRamoneda
 */
public class CM_Skill_RequisitionClass {

    public SimpleStringProperty sr_id, dept_name, title, req_status_id, req_status;

    public CM_Skill_RequisitionClass(String sr_id, String dept_name, String title, String req_status_id, String req_status) {
        this.sr_id = new SimpleStringProperty(sr_id);
        this.dept_name = new SimpleStringProperty(dept_name);
        this.title = new SimpleStringProperty(title);
        this.req_status_id = new SimpleStringProperty(req_status_id);
        this.req_status = new SimpleStringProperty(req_status);
    }

}
