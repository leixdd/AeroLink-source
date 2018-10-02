/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.HR4;

/**
 *
 * @author Lei
 */
public class HR4_Employee extends Synapse.Model {

    public HR4_Employee() {
        this.initTable("tbl_hr4_employees");
    }

    public HR4_Employee(String trigger) {

        switch (trigger) {
            case "profile":
                this.initTable("tbl_hr4_employee_profiles");
                break;
            case "familyBackground":
                this.initTable("tbl_hr4_employee_FB");
                break;
            case "EducAttain":
                this.initTable("tbl_hr4_employee_educAttain");
                break;
            case "TSM":
                this.initTable("tbl_hr4_employee_trainingSeminars");
                break;
            case "job":
                this.initTable("tbl_hr4_employee_jobs");
                break;
            default:
                break;
        }
    }

}