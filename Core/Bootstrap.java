package Core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Config.*;
import Synapse.Database;
import Synapse.Form;
import Synapse.Session;
import Synapse.iDB;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.StageStyle;

/**
 *
 * @author Lei
 */
public class Bootstrap {
    
    public static void build(){
        
        //Building routes
        Session.links =  RouteConfig.links;
        //Building Database Configs
        
        Session.Database = DatabaseConfig.DATABASE;
        Session.Ip = DatabaseConfig.IP;
        Session.Password = DatabaseConfig.PASSWORD;
        Session.Port = DatabaseConfig.PORT;
        Session.User = DatabaseConfig.USER;
        Session.schema = DatabaseConfig.schema;
        Session.provider = DatabaseConfig.PROVIDER;
        
        //Start app
        new Form("/FXMLS/SplashScreen.fxml").open(StageStyle.UNDECORATED);
        
        
    }
}
