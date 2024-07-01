/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.data;

import com.br.connection.ConnectDB2;
import com.br.connection.ConnectSQLServer;
import static com.br.utility.Constant.dbname;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Wattana
 */
public class Insert {

    public static String importStartData(String transtype, String date, String description, String amount, String total, String howtoread, String username, String type, String car, String cono, String divi) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";
        String year1 = date.substring(6, 10);
        String month = date.substring(3, 5);
        String day = date.substring(0, 2);
        String date2 = year1 + month + day;
        String last2digityear1 = year1.substring(2, 4);
        double amount2 = Double.parseDouble(amount);
        String modeoperator = "+";
        total = total.replace(",", "");
        double total2 = Double.parseDouble(total);
        double total3 = 0;
        String realtotal = Double.toString(total3);
        System.out.println("username\n" + username);
        if (transtype.equals("2")) {
            modeoperator = "-";
            total3 = amount2 - total2;
        } else {
            total3 = amount2 + total2;
        }
        String newnum = null;
        String query2 = "";

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                Statement stmt2 = conn.createStatement();

                query2 = "SELECT CASE WHEN CAST(MAX(B.TRATGNO) AS DECIMAL(10,0)) > 0 THEN ('" + last2digityear1 + type + "'||MAX(SUBSTRING(B.TRATGNO,4,5) )) +1 ELSE'" + last2digityear1 + type + "'||'00001' END AS maxnum\n"
                        + "FROM " + dbname + ".TSG_TRANLS B\n"
                        + "WHERE SUBSTRING(TRATGNO,1,2) =" + last2digityear1 + "\n"
                        + "AND TRACONO =" + cono + "\n"
                        + "AND TRADIVI =" + divi;
                System.out.println("importStartData Part1\n" + query2);
                query1 = "INSERT INTO " + dbname + ".TSG_TRANLS(TRATGTY,TRATGNO,TRATGDT,TRASUNO,TRADESC,TRAGAMT,TRASAMT,TRATAMT,TRAUSER,TRATYPE,TRAGSTS,TRAPSTS,TRACAR,TRACDATE,TRACTIME,TRACUSER,TRACONO,TRADIVI)\n"
                        + "SELECT  '" + transtype + "',(SELECT CASE WHEN CAST(MAX(B.TRATGNO) AS DECIMAL(10,0)) > 0 THEN ('" + last2digityear1 + type + "'||MAX(SUBSTRING(B.TRATGNO,4,5) ))+1 ELSE'" + last2digityear1 + type + "'||'00001' END AS maxnum\n"
                        + "FROM " + dbname + ".TSG_TRANLS B\n"
                        + "WHERE SUBSTRING(TRATGNO,1,2) =" + last2digityear1 + "),'" + date2 + "',A.OWNSUNO,'" + description + "',\n"
                        + modeoperator + amount + ",\n"
                        + "(SELECT COALESCE(A.WHATLEFT, 0)\n"
                        + "FROM\n"
                        + "(SELECT A.TRACAR,SUM(A.TRAGAMT) AS WHATLEFT\n"
                        + "FROM " + dbname + ".TSG_TRANLS A\n"
                        + "WHERE TRAPSTS = 1\n"
                        + "AND A.TRATGDT <= " + date2 + "\n"
                        + "GROUP BY TRACAR) AS A\n"
                        + "RIGHT JOIN\n"
                        + "(SELECT * FROM \n"
                        + dbname + ".TSG_OWNERLS B) AS B\n"
                        + "ON A.TRACAR = B.OWNCAR\n"
                        + "WHERE B.OWNCAR = '" + car + "')" + modeoperator + " " + amount + ",'" + howtoread + "'  ,'" + username + "'  ,'" + type + "'  ,'0','0',A.OWNCAR,CURRENT DATE,CURRENT TIME,'" + username + "'," + cono + "," + divi + "  \n"
                        + "FROM " + dbname + ".TSG_OWNERLS A\n"
                        + "WHERE A.OWNCAR = '" + car + "'\n"
                        + "GROUP BY A.OWNGAMT,A.OWNFLAG,A.OWNCAR,A.OWNSUNO,A.OWNGSTS,A.OWNTGDT";

                System.out.println("importstartdata Part2\n" + query1);
                ResultSet mRes = stmt2.executeQuery(query2);
                if (mRes.next()) {
                    newnum = mRes.getString("maxnum");
                }
                stmt.execute(query1);

                respond = "generate successfully! new num is  " + newnum;

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

    public static String GetNewTransactionNum(String LS, String Car, String Type, String Amount, String date, String cono, String divi) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";
        String querycheck = "";
        String[] LSnew = LS.split(":");
        String LSuse = LSnew[0];
        String[] Carnew = Car.split(":");
        String Caruse = Carnew[0].trim();
        String year1 = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        String date2 = year1 + month + day;
        String check = "0";

        String newnum = null;
        String query2 = "";

        try {
            if (conn != null) {
                Statement stmtcheck = conn.createStatement();
                querycheck = "SELECT DISTINCT OWNCAR FROM \n"
                        + dbname + ".TSG_OWNERLS\n"
                        + "WHERE OWNCAR = '" + Caruse + "'\n"
                        + "AND OWNCONO =" + cono + "\n"
                        + "AND OWNDIVI =" + divi + "\n"
                        + "ORDER BY OWNCAR";
                ResultSet mRes3 = stmtcheck.executeQuery(querycheck);
                System.out.println("Check if data exist or not\n" + query1);
                while (mRes3.next()) {
                    if (Caruse.equals(mRes3.getString(1).trim())) {
                        respond = "Data already Existed!";
                        check = "1";
                    }
                }
                Statement stmt = conn.createStatement();
                if (check.equals("0")) {
                    query1 = "INSERT INTO " + dbname + ".TSG_OWNERLS (OWNSUNO,OWNNAME,OWNADDR,OWNGAMT,OWNTYPE,OWNGSTS,OWNTGDT,OWNFLAG,OWNCAR,OWNCONO,OWNDIVI)\n"
                            + "SELECT IDSUNO,IDSUNM,DCTX40,'" + Amount + "','" + Type + "','0','" + date2 + "','0',DCTRCA," + cono + "," + divi + "\n"
                            + "FROM \n"
                            + "(SELECT IDCONO, IDSUNO, IDSUNM, IDTLNO AS OWNERNO\n"
                            + "FROM M3FDBPRD.CIDMAS c\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS') AS a\n"
                            + "LEFT JOIN \n"
                            + "(SELECT DCTRCA, DCTX40, DCTX15, DCFWNO\n"
                            + "FROM M3FDBPRD.DCARRI\n"
                            + "WHERE DCCONO = '" + cono + "'\n"
                            + ") AS b\n"
                            + "ON b.DCFWNO = a.OWNERNO\n"
                            + "WHERE DCTRCA = '" + Caruse + "'\n"
                            + "ORDER BY IDSUNO, OWNERNO, DCTRCA";

                    System.out.println("GetNewTransactionNum\n" + query1);
                    stmt.execute(query1);
                    respond = Car + "From" + LS + " has been imported successfully!";
                }

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
