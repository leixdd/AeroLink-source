/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Lei
 */
public class HR4_Designation extends Synapse.Model{

    public HR4_Designation() {
        setColumns("id", "designation");
        this.initTable("tbl_hr4_job_designations");
    }
    
    
}
