/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FXMLS.HR3.ClassFiles;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author my
 */
public class HR3_status_overtime {
    public SimpleStringProperty employee_code;
    public SimpleStringProperty date;
    public SimpleStringProperty overtime_hours;
    public SimpleStringProperty reason;
    public SimpleStringProperty status;
    

    public HR3_status_overtime(  String b, String c, String d, String e, String f) {
        
       
        this.employee_code = new SimpleStringProperty(b);
        this.date = new SimpleStringProperty(c);
        this.overtime_hours = new SimpleStringProperty(d);
        this.reason = new SimpleStringProperty(e);
        this.status = new SimpleStringProperty(f);
        
    }
}
