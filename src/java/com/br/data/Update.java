/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.data;

import com.br.connection.ConnectDB2;
import com.br.connection.ConnectSQLServer;
import static com.br.utility.Constant.dbname;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Wattana
 */
public class Update {


    public static String updateorder(String ordernum, String transtype, String date, String description, String amount, String total, String howtoread, String username, String type, String car, String cono, String divi, String supplier) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";
        String year1 = date.substring(6, 10);
        String month = date.substring(3, 5);
        String day = date.substring(0, 2);
        String date2 = year1 + month + day;
        double total3 = 0;
        double amount2 = Double.parseDouble(amount);
        total = total.replace(",", "");
        double total2 = Double.parseDouble(total);

        String modeoperator = "";
        if (transtype.equals("2")) {
            modeoperator = "-";
            total3 = amount2 - total2;

        } else {
            total3 = amount2 + total2;
        }
        String realtotal = Double.toString(total3);
        System.out.println("username\n" + username);
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();

//                query1 = "UPDATE " + dbname + ".TSG_TRANLS\n"
//                        + "SET TRATGTY = '" + transtype + "',TRATGDT = '" + date2 + "',TRATAMT = '" + howtoread + "',TRASAMT = (\n"
//                        + "SELECT SUM(TRAGAMT) AS SUMOFAMOUNT\n"
//                        + "FROM " + dbname + ".TSG_TRANLS A\n"
//                        + "WHERE TRAPSTS = 1\n"
//                        + "AND TRATGDT <= \n"
//                        + "(SELECT TRATGDT \n"
//                        + "FROM " + dbname + ".TSG_TRANLS A\n"
//                        + "WHERE TRATGNO = " + ordernum + "\n"
//                        + "))+ (" + modeoperator + amount + "),TRADESC='" + description + "',TRAGAMT='" + modeoperator + amount + "',TRATYPE ='" + type + "',TRACUSER = '" + username + "',TRACDATE = CURRENT date,TRACTIME = CURRENT time\n"
//                        + "WHERE TRATGNO = '" + ordernum + "'\n"
//                        + "AND TRACONO =" + cono + " AND TRADIVI =" + divi;
                query1 = "UPDATE " + dbname + ".TSG_TRANLS\n"
                        + "SET TRATGTY = '1',TRATGDT = '" + date2 + "',TRATAMT = '" + howtoread + "',TRASAMT = (SELECT SUM(TRAGAMT) AS SUMOFAMOUNT\n"
                        + "FROM " + dbname + ".TSG_TRANLS A\n"
                        + "WHERE TRAPSTS = 1\n"
                        + "AND TRATGDT <= \n"
                        + "(SELECT TRATGDT\n"
                        + "FROM " + dbname + ".TSG_TRANLS A\n"
                        + "WHERE TRATGNO = " + ordernum + "\n"
                        + "AND TRACONO =" + cono + " AND TRADIVI =" + divi + "\n"
                        + "AND TRATYPE =" + type + "\n"
                        + ")\n"
                        + "AND TRASUNO = (SELECT TRASUNO\n"
                        + "FROM " + dbname + ".TSG_TRANLS A\n"
                        + "WHERE TRATGNO = " + ordernum + "\n"
                        + "AND TRACONO =" + cono + " AND TRADIVI =" + divi + "\n"
                        + "AND TRATYPE =" + type + "\n"
                        + ")\n"
                        + "GROUP BY TRASUNO)+ (" + modeoperator + amount + "),TRADESC='" + description + "',TRAGAMT='" + modeoperator + amount + "',\n"
                        + "TRATYPE ='" + type + "',TRACUSER = '" + username + "',TRACDATE = CURRENT date,TRACTIME = CURRENT time\n"
                        + "WHERE TRATGNO = '" + ordernum + "'\n"
                        + "AND TRACONO =" + cono + " AND TRADIVI =" + divi + "\n"
                        + "AND TRATYPE =" + type;

                System.out.println("importstartdata\n" + query1);
                stmt.execute(query1);

                respond = "UPDATE successfully!";

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return respond;
    }

    public static String updateMasterData(String addr, String startdate, String total, String car) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";

        
//        startdate
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();

                query1 = "UPDATE " + dbname + ".TSG_OWNERLS A\n"
                        + "SET OWNADDR = '" + addr + "'\n"
                        + ",OWNTGDT='" + startdate + "'\n"
                        + ",OWNGAMT = '" + total + "'\n"
                        + "WHERE OWNCAR = '" + car + "'";

                stmt.execute(query1);

                respond = "generate successfully! new num is";

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return respond;
    }

    public static String cancelhistory(String transnum, String username) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";
        double num = Double.parseDouble(transnum);

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();

                query1 = "UPDATE " + dbname + ".TSG_TRANLS\n"
                        + "SET TRAPSTS = 9,TRACUSER = '" + username + "'\n"
                        + ",TRACDATE = CURRENT Date\n"
                        + ",TRACTIME = CURRENT time\n"
                        + "WHERE TRATGNO =" + transnum;

                stmt.execute(query1);

                respond = "The number has been Canceled!";

            } else {
                System.out.println("Server can't connect.");
            }

        } catch (SQLException sqle) {
            throw sqle;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return respond;
    }

}
