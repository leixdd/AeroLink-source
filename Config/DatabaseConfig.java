/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.sql.Connection;

/**
 *
 * @author Eden Ramoneda
 */
public class DatabaseConfig {

    public static String PROVIDER = "mssql";
    public static String schema = "aerolink"; //will be change if you were using mssql
    public static String HttpURL = "http://127.0.0.1:8423/api/";
    public static String key = "$2a$10$0S0Q9s6rrlAnabpLXoaWyuG5lgdQGMciIdSgxMzSULZW0e9vaCDke";

    public static Boolean offline = true;
   
    

    public static String IP = "139.99.62.2"; //NS558185\AEROLINKSQL
    public static String PORT = "8420";//1443
    public static String DATABASE = "AeroLink";
    public static String USER = "sa";
    public static String PASSWORD = "PYWETgMDWTaS834fo9ijGkENpl43yrFtXpcJ9ovB0$yPS38";
 

    //if offline mode
   /*
    public static String IP = "127.0.0.1"; //NS558185\AEROLINKSQL
    public static String PORT = "1433";//1443
    public static String DATABASE = "AeroLink";
    public static String USER = "finance";//core
    public static String PASSWORD = "finance";//core     */
    
    
    
}
