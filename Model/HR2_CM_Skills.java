/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import static Synapse.Model.setColumns;
import static Synapse.Model.setTable;
import java.util.List;

/**
 * 
 * @author Eden Ramoneda
 */
public class HR2_CM_Skills extends Synapse.Model{
        
         public HR2_CM_Skills()
            {
                   setColumns("skill_id","skill","skill_description","isDeleted");
                    this.initTable("tbl_hr2_skillset");
            }

    public List where() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
