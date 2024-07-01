/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.data;

import MForms.Utils.MNEHelper;
import MForms.Utils.MNEProtocol;
import MvxAPI.MvxSockJ;
import com.br.connection.ConnectDB2;
import com.br.connection.ConnectSQLServer;
import static com.br.utility.Constant.dbM3Name;
import static com.br.utility.Constant.dbname;
import java.awt.event.WindowEvent;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;

/**
 *
 * @author Wattana
 */
public class Select {

    public static String mneLogOnUrl = "https://bkrmvxm3.bangkokranch.com:21008/mne/servlet/MvxMCSvt"; //PRD
//    public static String mneLogOnUrl = "https://bkrmvxm3.bangkokranch.com:22008/mne/servlet/MvxMCSvt";   // TST 
    static MvxSockJ sock;
    private static String appServer;
    private static int appPort;
    private static String m3id;
    private static String m3pw;
    String chkpms300 = "no";

    public static JSONArray getCompany() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT CCCONO,CCDIVI,CCCONM,'\"'|| TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' || TRIM(CCCONM) || '\"' AS COMPANY\n"
                        + "FROM M3FDBPRD.CMNDIV\n"
                        + "WHERE CCDIVI != ''\n"
                        + "ORDER BY CCCONO";
                System.out.println("SelectCompany\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("CCCONO", mRes.getString(1).trim());
                    mMap.put("CCDIVI", mRes.getString(2).trim());
                    mMap.put("CCCONM", mRes.getString(3).trim());
                    mMap.put("COMPANY", mRes.getString(4).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static Boolean checkAuth(String user) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT CTL_REM AS CHECK_AUTH\n"
                        + "FROM " + dbname + ".APPCTL1\n"
                        + "WHERE CTL_CODE = 'DOD'\n"
                        + "AND CTL_REM = 'AP'\n"
                        + "AND CTL_UID = '" + user + "'";
                System.out.println("checkAuth\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    return true;

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

        return false;

    }

    public static JSONArray getTransHeader(String user, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = " SELECT TRATGNO||' | '||TRACAR || OWNADDR\n"
                        + "FROM " + dbname + ".TSG_TRANLS\n"
                        + "JOIN " + dbname + ".TSG_OWNERLS\n"
                        + "ON OWNCAR = TRACAR\n"
                        + "WHERE (TRAPSTS = '0' OR \n"
                        + "TRAPSTS = '3')\n"
                        + "AND TRAUSER = '" + user + "'\n"
                        + "AND OWNCONO =" + cono + "\n"
                        + "AND OWNDIVI =" + divi + "\n"
                        + "ORDER BY TRATGNO";
                System.out.println("getTransHeader\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vTransactionnum", mRes.getString(1).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getLS(String type, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DISTINCT  A.OWNSUNO, IDTLNO||': '||OWNNAME\n"
                        + "FROM M3FDBPRD.CIDMAS c\n"
                        + "JOIN " + dbname + ".TSG_OWNERLS A\n"
                        + "ON A.OWNSUNO = C.IDSUNO\n"
                        + "WHERE IDCONO = '10'\n"
                        + "AND IDSTAT = '20'\n"
                        + "AND IDTFNO = 'DELIVERY'\n"
                        + "AND OWNCONO =" + cono + " \n"
                        + "AND OWNDIVI =" + divi + "\n"
                        + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                        + "AND A.OWNTYPE =" + type;
                System.out.println("getLS\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vLSdata", mRes.getString(1).trim());
                    mMap.put("vLS", mRes.getString(2).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getLSandName() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DISTINCT OWNERNO,OWNERNO||' : '||IDSUNM\n"
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
                        + "WHERE DCCONO = '10'\n"
                        + ") AS b\n"
                        + "ON b.DCFWNO = a.OWNERNO";
                System.out.println("getLSandName\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vLSdata", mRes.getString(1).trim());
                    mMap.put("vLS", mRes.getString(2).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getcarMaster(String LS) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String[] LSnew = LS.split(":");
        String useLS = LSnew[0];

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT OWNERNO,DCTRCA || ':' || DCTX40\n"
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
                        + "WHERE DCCONO = '10'\n"
                        + "--AND DCFWNO = 'LS00000143'\n"
                        + ") AS b\n"
                        + "ON b.DCFWNO = a.OWNERNO\n"
                        + "WHERE OWNERNO = '" + useLS + "'\n"
                        + "ORDER BY IDSUNO, OWNERNO, DCTRCA";
                System.out.println("getcarMaster\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vCardata", mRes.getString(2).trim());
                    mMap.put("vCar", mRes.getString(2).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getOrder(String order, String cono, String divi, String Status) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String[] orderlist = order.split(":");
        String orderuse = orderlist[0];

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT A.OWNSUNO,A.OWNCAR||' : '|| OWNADDR\n"
                        + "FROM M3FDBPRD.CIDMAS c\n"
                        + "JOIN " + dbname + ".TSG_OWNERLS A\n"
                        + "ON A.OWNSUNO = C.IDSUNO\n"
                        + "JOIN M3FDBPRD.DCARRI B\n"
                        + "ON B.DCTRCA = OWNCAR\n"
                        + "WHERE IDCONO = '10'\n"
                        + "AND IDSTAT = '20'\n"
                        + "AND IDTFNO = 'DELIVERY'\n"
                        + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                        + "AND OWNGSTS = " + Status + "\n"
                        + "AND IDTLNO = '" + orderuse + "'\n"
                        + "AND OWNCONO =" + cono + "\n"
                        + "AND OWNDIVI =" + divi;
                System.out.println("getOrder\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vOrderdata", mRes.getString(1).trim());
                    mMap.put("vDriver", mRes.getString(2).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static String GetDateDecmalCurrenttime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    public static String GetDateDecmalwithsecond() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.ENGLISH);
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    public static String Gettimenow() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("HHmmss", Locale.ENGLISH);
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    public static String get_SemiColonValue0(String TextFieldto) {
        String TextFieldtos[] = TextFieldto.split(":");
        return TextFieldtos[0]; // GET COST CENTER
    }

    public static JSONArray CheckDuplicateSupInvoice(String sup, String inv, String srn) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String query = "SELECT COUNT(*) AS CHK\n"
                        + "FROM brldta0100.pur_eprhead\n"
                        + "WHERE EPRH_SUNO = '" + sup + "'\n"
                        + "AND EPRH_INVSU = '" + inv + "'\n"
                        + "AND EPRH_PHNO != '" + srn + "'";
                System.out.println("CheckDuplicateSupInvoice\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
                if (mRes.next()) {
                    if (mRes.getInt("CHK") > 0) {
                        Map<String, Object> mMap = new HashMap<>();
                        mMap.put("check", "true");
                        mJSonArr.put(mMap);
                    } else {
                        Map<String, Object> mMap = new HashMap<>();
                        mMap.put("check", "false");
                        mJSonArr.put(mMap);
                    }

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

        return mJSonArr;

    }

    public static JSONArray getDriverCar(String car, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String[] newCar = car.split(":");
        String useCar = newCar[0].trim();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT OWNCAR,OWNNAME,DCTX15,DCTX40\n"
                        + "FROM " + dbname + ".TSG_OWNERLS A\n"
                        + "LEFT JOIN\n"
                        + "(SELECT DCTRCA,DCTX15,DCTX40\n"
                        + "FROM M3FDBPRD.DCARRI) AS D\n"
                        + "ON A.OWNCAR = D.DCTRCA\n"
                        + "WHERE OWNCAR = '" + useCar + "'\n"
                        + "AND OWNCONO =" + cono + "\n"
                        + "AND OWNDIVI =" + divi;
                System.out.println("get Driver Car\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vCar", mRes.getString(1).trim());
                    mMap.put("vCarLicense", mRes.getString(3).trim());
                    mMap.put("vDriver", mRes.getString(4).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static String getHowtoread(String amountin) throws Exception {

        String mJSonArr = "";
        Connection conn = ConnectDB2.ConnectionDB();
        DecimalFormat df = new DecimalFormat("#.00");
        String bathTxt, n, bathTH = "";
        Double amount;
        bathTxt = amountin;
        amount = Double.parseDouble(amountin);
        bathTxt = df.format(amount);
        String[] num = {"ศูนย์", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด", "เก้า", "สิบ"};
        String[] number = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] rank = {"", "สิบ", "ร้อย", "พัน", "หมื่น", "แสน", "ล้าน"};
        String[] temp = bathTxt.split("[.]");
        String intVal = temp[0];
        String deciVal = temp[1];
        if (Double.parseDouble(bathTxt) == 0) {
            bathTH = "ศูนย์กิโลกรัม";
        } else {
            for (int i = 0; i < intVal.length(); i++) {
                n = intVal.substring(i, i + 1);
                if (Integer.parseInt(n) != 0) {
                    if ((i == (intVal.length() - 1)) && (n.indexOf("1") > -1) && intVal.length() > 1) {
                        bathTH += "เอ็ด";
                    } else if ((i == (intVal.length() - 2)) && (n.indexOf("2") > -1)) {
                        bathTH += "ยี่";
                    } else if ((i == (intVal.length() - 2)) && (n.indexOf("1") > -1)) {
                        bathTH += "";
                    } else {
                        bathTH += num[Integer.parseInt(n)];
                    }
                    bathTH += rank[(intVal.length() - i) - 1];
                } else if (i == 0) {
                    bathTH += num[Integer.parseInt(n)];
                }
            }

            if (deciVal.length() > 0 && Integer.parseInt(deciVal) != 0) {
                bathTH += "จุด";
                for (int i = 0; i < deciVal.length(); i++) {
                    System.out.print(deciVal.substring(0 + i, 1 + i));
                    for (int j = 0; j < 10; j++) {
                        if (deciVal.substring(0 + i, 1 + i).contains(number[j])) {
                            bathTH += num[j];
                        }
                    }
                }
            }

        }
        bathTH += "บาทถ้วน";
        return bathTH;
    }

    public static String getLeftAmount(String car, String convert, String date, String cono, String divi) throws Exception {

        String mJSonArr = "";
        Connection conn = ConnectDB2.ConnectionDB();
        String[] car2 = car.split(":");
        String caruse = car2[0];
        String day = date.substring(0, 2);
        String month = date.substring(3, 5);
        String year1 = date.substring(6, 10);
        String date2 = year1 + month + day;
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT COALESCE(A.WHATLEFT, 0)\n"
                        + "FROM\n"
                        + "(SELECT A.TRACAR,SUM(A.TRAGAMT) AS WHATLEFT,TRACONO,TRADIVI\n"
                        + "FROM " + dbname + ".TSG_TRANLS A\n"
                        + "WHERE TRAPSTS = 1\n"
                        + "AND TRATGDT <= '" + date2 + "' \n"
                        + "GROUP BY TRACAR,TRACONO,TRADIVI) AS A\n"
                        + "RIGHT JOIN\n"
                        + "(SELECT * FROM \n"
                        + "" + dbname + ".TSG_OWNERLS B) AS B\n"
                        + "ON A.TRACAR = B.OWNCAR\n"
                        + "AND A.TRACONO = B.OWNCONO\n"
                        + "AND A.TRADIVI = B.OWNDIVI\n"
                        + "WHERE B.OWNCAR = '" + caruse + "'\n"
                        + "AND B.OWNCONO =" + cono + "\n"
                        + "AND B.OWNDIVI =" + divi;
                System.out.println("get Left Amount\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    if (convert.equals("1")) {
                        String numericString = mRes.getString(1).trim();
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        Number number = numberFormat.parse(numericString);

                        // Format the number with commas
                        DecimalFormat decimalFormat = new DecimalFormat("#,###");
                        String formattedString = decimalFormat.format(number);
                        mJSonArr = formattedString;
                    } else if (convert.equals("0")) {
                        mJSonArr = mRes.getString(1).trim();
                    }
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

        return mJSonArr;

    }

    public static String ImportNewMaster() throws Exception {

        String mJSonArr = "";
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT COALESCE(MAX(TRATGNO) + 1,10000001)\n"
                        + "FROM " + dbname + ".TSG_TRANLS B";
                System.out.println("geTransactionNum\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    mJSonArr = mRes.getString(1).trim();

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

        return mJSonArr;

    }

    public static JSONArray Company() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT CCCONO,CCDIVI,CCCONM,'\"'|| TRIM(CCCONO) || ' : ' || TRIM(CCDIVI) || ' : ' || TRIM(CCCONM) || '\"' AS COMPANY\n"
                        + "FROM M3FDBPRD.CMNDIV\n"
                        + "WHERE CCDIVI != ''\n"
                        + "ORDER BY CCCONO";
                System.out.println("SelectCompany\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("CCCONO", mRes.getString(1).trim());
                    mMap.put("CCDIVI", mRes.getString(2).trim());
                    mMap.put("CCCONM", mRes.getString(3).trim());
                    mMap.put("COMPANY", mRes.getString(4).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getMenu(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT K.DOD030,K.OKCUNM,count(L.sum04) AS countDOD04,K.Reqamt, K.Rcvamt ,K.Reqamt- K.Rcvamt as sumamt\n"
                        + "FROM (\n"
                        + "SELECT CC.*,coalesce(E.amt8 ,0) AS Rcvamt \n"
                        + "FROM ( \n"
                        + "SELECT  AA.DOD010,AA.DOD020,AA.DOD030,AA.OKCUNM,coalesce(B.sumdod08 ,0) AS Reqamt \n"
                        + "FROM\n"
                        + "(\n"
                        + "SELECT DISTINCT  DOD010,DOD020, DOD030,OKCUNM,OKCUNO \n"
                        + "FROM " + dbname + ".BMSDOD A\n"
                        + "left JOIN  M3FDBPRD.Ocusma ON OKCUNO = DOD030 \n"
                        + ") AA LEFT JOIN  \n"
                        + "(SELECT  DOD010,DOD020,sum(DOD080)AS sumdod08,DOD030\n"
                        + "FROM " + dbname + ".BMSDOD \n"
                        + "WHERE DOD110 = 10 and DOD210 = 20 \n"
                        + "GROUP BY DOD030, DOD010,DOD020 ) B  ON B.DOD030 = AA.OKCUNO  \n"
                        + ") CC LEFT JOIN\n"
                        + "( SELECT  DOD010,DOD020,sum(DOD080) AS amt8,DOD030 \n"
                        + "FROM " + dbname + ".BMSDOD  \n"
                        + "WHERE DOD110  = 10 AND DOD110 = 40 and DOD210 = 20 \n"
                        + "GROUP BY DOD030,DOD010,DOD020 )E ON E.DOD030 = CC.DOD030 \n"
                        + "ORDER by OKCUNM  ) K LEFT JOIN\n"
                        + "(\n"
                        + "SELECT DISTINCT DOD010,DOD020,DoD040 AS sum04,DOD030\n"
                        + "FROM " + dbname + ".BMSDOD  ) L\n"
                        + "ON L.DOD030 = K.DOD030 \n"
                        + "WHERE K.DOD010 =" + cono + " AND\n"
                        + " K.DOD020 =" + divi + "\n"
                        + "GROUP BY K.DOD030,K.OKCUNM,K.Reqamt, K.Rcvamt,K.DOD010,k.DOD020";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }
                    mMap.put("RFARMER", value1);
                    String value2 = mRes.getString(2);
                    if (value2 != null) {
                        value2 = value2.trim();
                    } else {
                        value2 = "";
                    }
                    mMap.put("RNAME", value2);
                    String value3 = mRes.getString(3);
                    if (value3 != null) {
                        value3 = value3.trim();
                    } else {
                        value3 = "";
                    }
                    mMap.put("RCN", value3);
                    String value4 = mRes.getString(4);
                    if (value4 != null) {
                        value4 = value4.trim();
                    } else {
                        value4 = "";
                    }
                    mMap.put("RREQAMT", value4);
                    String value5 = mRes.getString(5);
                    if (value5 != null) {
                        value5 = value5.trim();
                    } else {
                        value5 = "";
                    }
                    mMap.put("RCVAMT", value5);
                    String value6 = mRes.getString(6);
                    if (value6 != null) {
                        value6 = value6.trim();
                    } else {
                        value6 = "";
                    }
                    mMap.put("RBALANCE", value6);
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getMenuDetail(String cono, String divi, String selectedFarmer, String Name) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "Select Distinct DOD040,DOD210,DOD060,DOD070,DOD075,DOD080\n"
                        + "FROM " + dbname + ".BMSDOD \n"
                        + "WHERE DOD110 = '10' \n"
                        + "AND DOD030 =  '" + selectedFarmer + "' \n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'\n"
                        + "Order by DOD040 ASC";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    String value2 = mRes.getString(2);
                    String value3 = mRes.getString(3);
                    String value4 = mRes.getString(4);
                    String value5 = mRes.getString(5);
                    String value6 = mRes.getString(6);
                    String Status = "";
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }

                    if (value2 != null) {
                        value2 = value2.trim();
                    } else {
                        value2 = "";
                    }

                    if (value3 != null) {
                        value3 = value3.trim();
                    } else {
                        value3 = "";
                    }

                    if (value4 != null) {
                        value4 = value4.trim();
                    } else {
                        value4 = "";
                    }

                    if (value5 != null) {
                        value5 = value5.trim();
                    } else {
                        value5 = "";
                    }

                    if (value6 != null) {
                        value6 = value6.trim();
                    } else {
                        value6 = "";
                    }

                    if (value2.equals("20")) {
                        Status = "ใช้งาน";
                    } else if (value2.equals("90")) {
                        Status = "ยกเลิก";
                    } else if (value2.equals("10")) {
                        Status = "รออนุมัติ";
                    }

                    mMap.put("RFARMER", selectedFarmer);
                    mMap.put("RDODNO", value1);
                    mMap.put("RSTATUS", Status);
                    mMap.put("RHOUSE", value3);
                    mMap.put("RQTY", value4);
                    mMap.put("RPRICE", value5);
                    mMap.put("RAMTBAHT", value6);
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray CloseDoDTable(String cono, String divi, String DoDno) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DOD030, DOD040, DOD080, DOD120, DOD160,DOD110,DOD210,DOD140\n"
                        + "FROM " + dbname + ".BMSDOD \n"
                        + "WHERE DOD040 = '" + DoDno + "' Order by DOD160,DOD210";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes2 = stmt.executeQuery(query);

                while (mRes2.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes2.getString(1);
                    String Status = "";
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }

                    String value2 = mRes2.getString(2);
                    if (value2 != null) {
                        value2 = value2.trim();
                    } else {
                        value2 = "";
                    }

                    String value3 = mRes2.getString(3);
                    if (value3 != null) {
                        value3 = value3.trim();
                    } else {
                        value3 = "";
                    }

                    String value4 = mRes2.getString(4);
                    if (value4 != null) {
                        value4 = value4.trim();
                    } else {
                        value4 = "";
                    }

                    String value5 = mRes2.getString(5);
                    if (value5 != null) {
                        value5 = value5.trim();
                    } else {
                        value5 = "";
                    }

                    String value6 = mRes2.getString(6);
                    if (value6 != null) {
                        value6 = value6.trim();
                    } else {
                        value6 = "";
                    }
                    String value7 = mRes2.getString(7);
                    if (value7.equals("20")) {
                        Status = "ใช้งาน";
                    } else if (value7.equals("90")) {
                        Status = "ยกเลิก";
                    } else if (value7.equals("10")) {
                        Status = "รออนุมัติ";
                    }
                    mMap.put("RCUSTOMER", value1);
                    mMap.put("RDUCKCODE", value2);
                    mMap.put("RDETAIL", value4);
                    mMap.put("RAMTBAHT", value3);
                    mMap.put("RSTATUS", value6);
                    mMap.put("RDATE", value5);
                    mMap.put("RUSAGE", Status);
                    mMap.put("RTIME", mRes2.getString(8));
//                    mMap.put("RCHOOSE", false);
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static String savetheorder(String cono, String divi,
            String Customer, String farmer, String Number,
            String qty, String price, String amount, String reason, String user) throws Exception {

//        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String currentdate = GetDateDecmalCurrenttime();
        String currentdatewithtime = GetDateDecmalwithsecond();
        String hoursminute = Gettimenow();
        String currentmonth = currentdate.substring(4, 6);
        String year2digit = currentdate.substring(2, 4);
        String year = currentdate.substring(0, 4);
        String day = currentdate.substring(6, 8);
        String MonthCode = "";
        String thisshouldbenull = "null";
        boolean check = true;
        String alerttext = "";
        switch (currentmonth) {
            case "01":
                MonthCode = "A";
                break;
            case "02":
                MonthCode = "B";
                break;
            case "03":
                MonthCode = "C";
                break;
            case "04":
                MonthCode = "D";
                break;
            case "05":
                MonthCode = "E";
                break;
            case "06":
                MonthCode = "F";
                break;
            case "07":
                MonthCode = "G";
                break;
            case "08":
                MonthCode = "H";
                break;
            case "09":
                MonthCode = "I";
                break;
            case "10":
                MonthCode = "J";
            case "11":
                MonthCode = "K";
                break;
            case "12":
                MonthCode = "L";
                break;
        }
        String returnno = "";
        String value1 = "";
        String id = year2digit + MonthCode + day;
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = " SELECT LPAD(COALESCE(MAX(TRIM(SUBSTR(DOD040,7,5)))+1,'001'),3,'0') AS MAXNUM\n"
                        + " FROM " + dbname + ".BMSDOD  \n"
                        + "  WHERE DOD050 = '" + currentdate + "'";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
//                Map<String, Object> mMap = new HashMap<>();
                while (mRes.next()) {
//                    Map<String, Object> mMap = new HashMap<>();
                    value1 = mRes.getString(1);
                }
                returnno = id + value1;

                Statement stmt2 = conn.createStatement();
                String query2 = " SELECT DOD030,DOD060,DOD050 \n"
                        + " FROM " + dbname + ".BMSDOD WHERE DOD030 ='" + Customer + "'\n"
                        + " AND DOD060 = '" + Number + "'";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes2 = stmt2.executeQuery(query2);
//                Map<String, Object> mMap = new HashMap<>();
                while (mRes2.next()) {
//                    Map<String, Object> mMap = new HashMap<>();
                    thisshouldbenull = mRes2.getString(1);
                }
                if (!thisshouldbenull.equals("null")) {
                    alerttext = "This customer already have this No.";
                    check = false;
                }
                if (check) {
                    Statement stmt3 = conn.createStatement();
                    String query3 = "Insert into " + dbname + ".BMSDOD(DOD010, DOD020,\n"
                            + "DOD030, DOD040, DOD050, DOD060, DOD070, DOD075,\n"
                            + "DOD080, DOD090, DOD100, DOD110, DOD115, DOD120,\n"
                            + "DOD130, DOD140, DOD150, DOD160, DOD170, DOD180,\n"
                            + "DOD190, DOD200, DOD210, DOD220, DOD230, DOD240)\n"
                            + "VALUES('" + cono + "', '" + divi + "',\n"
                            + "'" + farmer + "', '" + returnno + "', '" + currentdate + "',\n"
                            + "'" + Number + "', '" + price + "', '" + qty + "',\n"
                            + "'" + amount + "', '" + currentdate + "', NOW(), '10', '', '" + reason + "',\n"
                            + "'" + user + "', NOW(), '0', '" + currentdate + "', '" + hoursminute + "', '" + currentdate + "',\n"
                            + "'0', '" + user + "', '10', '0', '0', '')";
//                    System.out.println("Get Menu\n" + query);
//                ResultSet mRes3 = stmt3.executeQuery(query3);
//                Map<String, Object> mMap = new HashMap<>();
                    stmt3.execute(query3);
                    alerttext = "บันทึกข้อมูลเรียบร้อย" + returnno;
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

        return alerttext;

    }

    public static String SaveCreateDODorder(String cono, String divi,
            String customer, String lblSUNO, String lblTRATGNO,
            String lblHOUSE, String lblQTY, String txtRecript,
            String txtReturn, String txtDescription, String type, String user) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String currentdate = GetDateDecmalCurrenttime();
        String hoursminute = Gettimenow();

        String alerttext = "";
        String price = "";
        String guanrateedate = "";
        String approvdate = "";
        String approveby = "";
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DOD030,DOD040\n"
                        + ",DOD050,DOD140,DOD130\n"
                        + ",OKCUNM,( OKCUA1 || OKCUA2 || OKCUA3  )AS sumadd\n"
                        + ",DOD060,DOD080,DOD070,DOD060,DOD075 \n"
                        + "FROM " + dbname + ".BMSDOD  \n"
                        + "LEFT JOIN M3FDBPRD.OCUSMA \n"
                        + "ON DOD030= okcuno \n"
                        + "and DOD010 = OKcono  \n"
                        + "WHERE DOD210 = '20' \n"
                        + "and DOD040= '" + customer + "'\n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'";
//                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);
//                Map<String, Object> mMap = new HashMap<>();
                while (mRes.next()) {
                    price = mRes.getString("DOD075");
                    guanrateedate = mRes.getString("DOD050");
                    approvdate = mRes.getString("DOD140");
                    approveby = mRes.getString("DOD130");
                }
                Statement stmt3 = conn.createStatement();
                String query3 = "";
                if (type.equals("1")) {
                    query3 = "insert into " + dbname + ".BMSDOD(DOD010, DOD020, DOD030,\n"
                            + "DOD040, DOD050, DOD060,\n"
                            + "DOD070, DOD075, DOD080,\n"
                            + "DOD090, DOD100, DOD110,\n"
                            + "DOD115, DOD120, DOD130,\n"
                            + "DOD140, DOD150, DOD160,\n"
                            + "DOD170, DOD180, DOD190,\n"
                            + "DOD200, DOD210, DOD220,\n"
                            + "DOD230, DOD240)\n"
                            + " values(  '" + cono + "','" + divi + "',\n"
                            + " '" + lblSUNO + "',\n"
                            + " '" + lblTRATGNO + " ' ,\n"
                            + " '" + guanrateedate + " ',\n"
                            + " '" + lblHOUSE + "',\n"
                            + " '" + lblQTY + "',\n"
                            + " '" + price + "',\n"
                            + " '-" + txtRecript + "',\n"
                            + " '" + currentdate + " ',\n"
                            + " NOW(),\n"
                            + " '20',\n"
                            + " '',\n"
                            + " '" + txtDescription + "',\n"
                            + " '" + approveby + "',\n"
                            + " NOW(),\n"
                            + " '0',\n"
                            + " '" + currentdate + " ',\n"
                            + " '" + hoursminute + " ',\n"
                            + " '" + currentdate + " ',\n"
                            + " '0',\n"
                            + " '" + user + "',\n"
                            + " '20',\n"
                            + " '0', \n"
                            + " '0', \n"
                            + " '' )";
                } else if (type.equals("2")) {
                    query3 = "insert into " + dbname + ".BMSDOD(DOD010, DOD020, DOD030,\n"
                            + "DOD040, DOD050, DOD060,\n"
                            + "DOD070, DOD075, DOD080,\n"
                            + "DOD090, DOD100, DOD110,\n"
                            + "DOD115, DOD120, DOD130,\n"
                            + "DOD140, DOD150, DOD160,\n"
                            + "DOD170, DOD180, DOD190,\n"
                            + "DOD200, DOD210, DOD220,\n"
                            + "DOD230, DOD240)\n"
                            + " values(  '" + cono + "','" + divi + "',\n"
                            + " '" + lblSUNO + "',\n"
                            + " '" + lblTRATGNO + " ' ,\n"
                            + " '" + guanrateedate + " ',\n"
                            + " '" + lblHOUSE + "',\n"
                            + " '" + lblQTY + "',\n"
                            + " '" + price + "',\n"
                            + " '" + txtReturn + "',\n"
                            + " '" + currentdate + " ',\n"
                            + " NOW(),\n"
                            + " '30',\n"
                            + " '',\n"
                            + " '" + txtDescription + "',\n"
                            + " '" + approveby + "',\n"
                            + " NOW(),\n"
                            + " '0',\n"
                            + " '" + currentdate + " ',\n"
                            + " '" + hoursminute + " ',\n"
                            + " '" + currentdate + " ',\n"
                            + " '0',\n"
                            + " '" + user + "',\n"
                            + " '20',\n"
                            + " '0', \n"
                            + " '0', \n"
                            + " '' )";
                }
                stmt3.execute(query3);
                alerttext = "บันทึกข้อมูลเรียบร้อย";
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

        return alerttext;

    }

    public static String FWtheorder(String cono, String divi,
            String Customer, String farmer, String Number,
            String qty, String price, String amount, String reason,
            String user, String remaining, String FWTarget) throws Exception {

//        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String currentdate = GetDateDecmalCurrenttime();
        String currentdatewithtime = GetDateDecmalwithsecond();
        String hoursminute = Gettimenow();

        String year2digit = currentdate.substring(2, 4);
        String year = currentdate.substring(0, 4);
        String day = currentdate.substring(6, 8);
        String MonthCode = "";
        String thisshouldbenull = "null";
        String thisshouldbenull2 = "null";
        boolean check = true;
        boolean check2 = true;
        String alerttext = "";

        String returnno = "";
        String value1 = "";
        String id = year2digit + MonthCode + day;

        String DOD040 = "";
        String DOD030 = "";
        String DOD050 = "";
        String DOD060 = "";
        String DOD070 = "";
        String DOD075 = "";
        String DOD130 = "";
        String DOD150 = "";
        String ReasonsFW = "Auto FW + to " + FWTarget;
        try {
            if (conn != null) {
                //First Step to CHECK DOD
                Statement stmt1 = conn.createStatement();
                String query1 = "SELECT DOD040,DOD030,DOD040,DOD050, DOD060, DOD070,\n"
                        + "DOD075, DOD080, DOD090, DOD100, DOD110, DOD115,\n"
                        + "DOD120, DOD130, DOD140, DOD150, DOD160, DOD170,\n"
                        + "DOD180, DOD190, DOD200,DOD210\n"
                        + "FROM " + dbname + ".BMSDOD \n"
                        + "WHERE  DOD040 = '" + FWTarget + "'\n"
                        + "and DOD210 = '20'";
//                System.out.println("Get Menu\n" + query);
                ResultSet mRes1 = stmt1.executeQuery(query1);
//                Map<String, Object> mMap = new HashMap<>();
                while (mRes1.next()) {
//                    Map<String, Object> mMap = new HashMap<>();
                    thisshouldbenull = mRes1.getString(1);
                    DOD040 = mRes1.getString(1);
                    DOD030 = mRes1.getString(2);
                    DOD050 = mRes1.getString(4);
                    DOD060 = mRes1.getString(5);
                    DOD070 = mRes1.getString(6);
                    DOD075 = mRes1.getString(7);
                    DOD130 = mRes1.getString(14);
                    DOD150 = mRes1.getString(16);
                }
                if (thisshouldbenull.equals("null")) {
                    alerttext = "ไม่มีหมายเลข DOD นี้ ";
                    check = false;
                }
                if (check && check2) {
                    Statement stmt2 = conn.createStatement();
                    String query2 = "Insert into " + dbname + ".BMSDOD(DOD010, DOD020,\n"
                            + "DOD030, DOD040, DOD050, DOD060, DOD070, DOD075,\n"
                            + "DOD080, DOD090, DOD100, DOD110, DOD115, DOD120,\n"
                            + "DOD130, DOD140, DOD150, DOD160, DOD170, DOD180,\n"
                            + "DOD190, DOD200, DOD210, DOD220, DOD230, DOD240)\n"
                            + "VALUES('" + cono + "', '" + divi + "',\n"
                            + "'" + DOD030 + "', '" + DOD040 + "', '" + DOD050 + "',\n"
                            + "'" + DOD060 + "', '" + DOD070 + "', '" + DOD075 + "',\n"
                            + "'-" + remaining + "', '" + currentdate + "', NOW(), '40', '" + Customer + "', '" + reason + "',\n"
                            + "'" + DOD130 + "', NOW(), '" + DOD150 + "', '" + currentdate + "', '" + hoursminute + "', '" + currentdate + "',\n"
                            + "'0', '" + user + "', '20', '0', '0', '')";
//                    System.out.println("Get Menu\n" + query);
//                ResultSet mRes3 = stmt3.executeQuery(query3);
//                Map<String, Object> mMap = new HashMap<>();
                    stmt2.execute(query2);
                }
                Statement stmt3 = conn.createStatement();
                String query3 = "SELECT DOD040,DOD030,DOD040,\n"
                        + "DOD050, DOD060, DOD070, DOD075, DOD080,\n"
                        + " DOD090, DOD100, DOD110, DOD115, DOD120,\n"
                        + " DOD130, DOD140, DOD150, DOD160, DOD170,\n"
                        + " DOD180, DOD190, DOD200\n"
                        + " FROM " + dbname + ".BMSDOD\n"
                        + " WHERE  DOD040 = '" + Customer + "'";
//                System.out.println("Get Menu\n" + query);
                ResultSet mRes3 = stmt3.executeQuery(query3);
//                Map<String, Object> mMap = new HashMap<>();
                while (mRes3.next()) {
//                    Map<String, Object> mMap = new HashMap<>();
                    thisshouldbenull2 = mRes3.getString(1);
                    DOD040 = mRes3.getString(1);
                    DOD030 = mRes3.getString(2);
                    DOD050 = mRes3.getString(4);
                    DOD060 = mRes3.getString(5);
                    DOD070 = mRes3.getString(6);
                    DOD075 = mRes3.getString(7);
                    DOD130 = mRes3.getString(14);
                    DOD150 = mRes3.getString(16);
                }
                if (thisshouldbenull2.equals("null")) {
                    alerttext = "เกิดข้อผิดพลาด กรุณาที่รายการใหม่";
                    check = false;
                }
                if (check && check2) {
                    Statement stmt4 = conn.createStatement();
                    String query4 = "Insert into " + dbname + ".BMSDOD(DOD010, DOD020,\n"
                            + "DOD030, DOD040, DOD050, DOD060, DOD070, DOD075,\n"
                            + "DOD080, DOD090, DOD100, DOD110, DOD115, DOD120,\n"
                            + "DOD130, DOD140, DOD150, DOD160, DOD170, DOD180,\n"
                            + "DOD190, DOD200, DOD210, DOD220, DOD230, DOD240)\n"
                            + "VALUES('" + cono + "', '" + divi + "',\n"
                            + "'" + DOD030 + "', '" + DOD040 + "', '" + DOD050 + "',\n"
                            + "'" + DOD060 + "', '" + DOD070 + "', '" + DOD075 + "',\n"
                            + "'" + remaining + "', '" + currentdate + "', NOW(), '41', '" + FWTarget + "', '" + ReasonsFW + "',\n"
                            + "'" + user + "', NOW(), '" + DOD150 + "', '" + currentdate + "', '" + hoursminute + "', '" + currentdate + "',\n"
                            + "'0', '" + user + "', '90', '0', '0', '')";
//                    System.out.println("Get Menu\n" + query);
//                ResultSet mRes3 = stmt3.executeQuery(query3);
//                Map<String, Object> mMap = new HashMap<>();
                    stmt4.execute(query4);
                    alerttext = "บันทึกข้อมูลเรียบร้อย";
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

        return alerttext;

    }

    public static String CheckDataToSaveDoDClose(String cono, String divi,
            String DoD) throws Exception {

//        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String alert = "สำเร็จ";
        boolean check = false;
        try {
            if (conn != null) {
                //First Step to CHECK DOD
                Statement stmt1 = conn.createStatement();
                String query1 = "SELECT DOD040 FROM " + dbname + ".BMSDOD \n"
                        + "WHERE  DOD040= '" + DoD + "'\n"
                        + "AND DOD010 = '" + cono + "' \n"
                        + "AND DOD020 = '" + divi + "'";
//                System.out.println("Get Menu\n" + query);
                ResultSet mRes1 = stmt1.executeQuery(query1);
//                Map<String, Object> mMap = new HashMap<>();
                while (mRes1.next()) {
                    check = true;
                }
                if (!check) {
                    alert = "ตรวจสอบเลขที่ DOD อีกครั้ง";
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

        return alert;

    }

    public static String changeStatusDoD(String cono,
            String divi,
            String Supplier) throws Exception {

//        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        
        String[] infor = (Supplier).split("_");
        String suppliercode = infor[0];
        String Date = infor[1];
        String Status = infor[2];
        String amount = infor[3];
        boolean check = false;
        String alert = suppliercode + " of " + Date + " has been submitted";
        try {
            if (conn != null) {
                //First Step to CHECK DOD
                Statement stmt1 = conn.createStatement();
                String query1 = "UPDATE " + dbname + ".BMSDOD\n"
                        + "SET DOD150 = '1'\n"
                        + "WHERE DOD040= '" + suppliercode + "' AND DOD160 = '" + Date + "'\n"
                        + "AND DOD110= '" + Status + "'";
//                System.out.println("Get Menu\n" + query);
                stmt1.execute(query1);
//                Map<String, Object> mMap = new HashMap<>();

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

        return alert;

    }

    public static String CloseDoD(String cono, String divi,
            String lblID, String lbl160, String lbl080, String lbl110, String lbl120, String time, String user) throws Exception {

//        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String alert = "ปิด DOD เรียบร้อย ";
        String currentdate = GetDateDecmalCurrenttime();
        try {
            if (conn != null) {
                //First Step to CHECK DOD
                Statement stmt1 = conn.createStatement();
                String query1 = " update " + dbname + ".BMSDOD \n"
                        + "set DOD180  = '" + currentdate + "',\n"
                        + "DOD200  = '" + user + "',\n"
                        + "DOD190  = '1',\n"
                        + "DOD210  = '90'\n"
                        + "where DOD040 ='" + lblID + "' and DOD160 ='" + lbl160 + "' and DOD110 = '" + lbl110 + "'\n"
                        + "and DOD140 = '" + time + "'";
//                System.out.println("Get Menu\n" + query);
                stmt1.execute(query1);
//                Map<String, Object> mMap = new HashMap<>();

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

        return alert;

    }

    public static JSONArray getCustomer() throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT OKCUNO,OKCUNO || ' ' ||OKCUNM as customer \n"
                        + "FROM M3FDBPRD.Ocusma\n"
                        + "WHERE okcucl BETWEEN '14' AND '26' \n"
                        + "AND okstat = '20' \n"
                        + "ORDER BY OKCUNO";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }
                    mMap.put("customercode", value1);
                    String value2 = mRes.getString(2);
                    if (value2 != null) {
                        value2 = value2.trim();
                    } else {
                        value2 = "";
                    }
                    mMap.put("customerlist", value2);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getCustomerCreateDoDlist(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DISTINCT DOD040 , DOD040 || ' ' ||okcunm AS NAME \n"
                        + "FROM " + dbname + ".BMSDOD \n"
                        + "LEFT JOIN M3FDBPRD.OCUSMA \n"
                        + "ON DOD030= okcuno\n"
                        + "WHERE DOD110=10 \n"
                        + "AND DOD210 = 20 \n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'\n"
                        + "ORDER BY DOD040 DESC";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    String value2 = mRes.getString(2);
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }

                    mMap.put("dodcode", value1);
                    mMap.put("dodlist", value2);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getCustomerPrintlist(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT  Distinct  DOD040 ||' - '||TRIM(OKCUNM) ||' - '||SUBSTRING(DOD160,1,4)||'/'||SUBSTRING(DOD160,5,2)||'/'||SUBSTRING(DOD160,7,2)||' - '||\n"
                        + "CASE WHEN DOD110 = '20'\n"
                        + "THEN 'รับเงินมัดจำ'\n"
                        + "WHEN DOD110 = '30'\n"
                        + "THEN 'คืนเงินมัดจำ'\n"
                        + "END AS FULLORDER\n"
                        + ",DOD040 ||'_'||DOD160||'_'||DOD110||'_'||DOD080 AS Value\n"
                        + "FROM " + dbname + ".BMSDOD\n"
                        + "LEFT JOIN M3FDBPRD.OCUSMA \n"
                        + "ON DOD030= okcuno\n"
                        + "and DOD010 = OKcono \n"
                        + "WHERE DOD150 = '0' \n"
                        + "AND DOD210 = '20'\n"
                        + "AND DOD110 BETWEEN '20'AND '30'\n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'\n"
                        + "--ORDER BY DOD040 DESC";

                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
//                    String value1 = mRes.getString(1);
//                    String value2 = mRes.getString(2);

                    mMap.put("listname", mRes.getString(1));
                    mMap.put("value", mRes.getString(2));
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getDoDlistReport(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DISTINCT DOD030,dod030 ||' '|| okcunm as name\n"
                        + "FROM " + dbname + ".BMSDOD \n"
                        + "LEFT JOIN M3FDBPRD.OCUSMA \n"
                        + "ON DOD030 = okcuno \n"
                        + "WHERE DOD210 = 20 \n"
                        + "AND DOD010 ='" + cono + "'\n"
                        + "AND DOD020 ='" + divi + "'\n"
                        + "order by DOD030 DESC";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    String value2 = mRes.getString(2);
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }
                    if (value2 != null) {
                        value2 = value2.trim();
                    } else {
                        value2 = "";
                    }
                    mMap.put("DoDCode", value1);
                    mMap.put("DoDlist", value2);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getDateDoDlistPrint(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT  Distinct  DOD040 ||' - '||TRIM(OKCUNM) ||' - '||SUBSTRING(DOD160,1,4)||'/'||SUBSTRING(DOD160,5,2)||'/'||SUBSTRING(DOD160,7,2)||' - '||\n"
                        + "CASE WHEN DOD110 = '20'\n"
                        + "THEN 'รับเงินมัดจำ'\n"
                        + "WHEN DOD110 = '30'\n"
                        + "THEN 'คืนเงินมัดจำ'\n"
                        + "END AS FULLORDER\n"
                        + ",DOD040 ||' - '||DOD160 AS Value\n"
                        + "FROM " + dbname + ".BMSDOD\n"
                        + "LEFT JOIN M3FDBPRD.OCUSMA \n"
                        + "ON DOD030= okcuno\n"
                        + "and DOD010 = OKcono \n"
                        + "WHERE DOD150 = '0' \n"
                        + "AND DOD210 = '20'\n"
                        + "AND DOD110 BETWEEN '20'AND '30'\n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'\n"
                        + "--ORDER BY DOD040 DESC";

                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }
                    mMap.put("DoDlist", value1);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getDoDlistPrintDate(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DISTINCT DOD040\n"
                        + "FROM " + dbname + ".BMSDOD\n"
                        + "LEFT JOIN M3FDBPRD.OCUSMA \n"
                        + "ON DOD030= okcuno\n"
                        + "WHERE DOD110=10 \n"
                        + "AND DOD210 = 20 \n"
                        + "AND DOD010 =" + cono + "\n"
                        + "AND DOD020 =" + divi + "\n"
                        + "ORDER BY DOD040 DESC";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }
                    mMap.put("DoDlist", value1);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getApprove(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DOD040 , DOD040 || ' ' ||okcunm AS FULL\n"
                        + " FROM " + dbname + ".BMSDOD\n"
                        + "LEFT JOIN M3FDBPRD.OCUSMA ON DOD030= okcuno\n"
                        + "WHERE DOD210 = 10 \n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'"
                        + "AND okcuno IS NOT null ORDER BY DOD040 DESC";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }
                    mMap.put("approvecode", value1);
                    String value2 = mRes.getString(2);
                    if (value2 != null) {
                        value2 = value2.trim();
                    } else {
                        value2 = "";
                    }
                    mMap.put("approvelist", value2);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getCustomerMasterDetail(String Customer) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT  OKCUNO,OKCUNM , OKCUA1,OKCUA2,OKCUA3,OKCUA4 FROM M3FDBPRD.Ocusma \n"
                        + "WHERE  okcucl BETWEEN  '14' AND '26'\n"
                        + "AND okstat = '20' \n"
                        + "AND OKCUNO = '" + Customer + "'";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    String fulladdress = mRes.getString(3).trim() + mRes.getString(4).trim() + mRes.getString(5).trim();
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("ID", mRes.getString(1));
                    mMap.put("Name", mRes.getString(2));
                    mMap.put("Address", fulladdress);

                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getApproverMasterDetail(String cono, String divi, String Customer) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT DOD030,DOD210,DOD040,DOD060,DOD070,DOD075,DOD080,DOD120,okcunm\n"
                        + ",(TRIM(OKCUA1) ||' '||TRIM(OKCUA2)||' '|| TRIM(OKCUA3)||' ' || TRIM(OKCUA4) )AS sumadd \n"
                        + " FROM " + dbname + ".BMSDOD \n"
                        + " LEFT JOIN M3FDBPRD.Ocusma\n"
                        + " ON OKCUNO = DOD030\n"
                        + " AND okcono = DOD010   \n"
                        + " WHERE DOD040  = '" + Customer + "'\n"
                        + " AND  DOD210 = '10'\n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'";
//                String query = "SELECT DOD030,DOD210,DOD040,DOD060,DOD070,DOD075,DOD080,DOD120,okcunm,( OKCUA1 || OKCUA2 || OKCUA3 || OKCUA4 )AS sumadd \n"
//                        + " FROM " + dbname + ".BMSDOD \n"
//                        + " LEFT JOIN M3FDBPRD.Ocusma\n"
//                        + " ON OKCUNO = DOD030\n"
//                        + " AND okcono = DOD010   \n"
//                        + " WHERE DOD040  = '" + Customer + "'  AND  DOD210 = '10'";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
//                    String fulladdress = mRes.getString(3).trim() + mRes.getString(4).trim() + mRes.getString(5).trim();
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("ID", mRes.getString(1));
                    mMap.put("Name", mRes.getString(9));
                    mMap.put("Address", mRes.getString(10));
                    mMap.put("Number", mRes.getString(4));
                    mMap.put("Price", mRes.getString(5));
                    mMap.put("QTY", mRes.getString(6));
                    mMap.put("Amount", mRes.getString(7));
                    mMap.put("Reason", mRes.getString(8));
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getAutoFWdetail(String Customer, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String Remaining = "0";
        String name = "";
        String name2;
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT SUM(DOD080) * -1 as  amt \n"
                        + "FROM " + dbname + ".BMSDOD  \n"
                        + "WHERE DOD110 IN(20,40,30)\n"
                        + "AND  DOD210 = 20 \n"
                        + "AND DOD040  = '" + Customer + "'\n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'";
//                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Remaining = mRes.getString(1);
//                    String fulladdress = mRes.getString(3).trim() + mRes.getString(4).trim() + mRes.getString(5).trim();

                }
                Statement stmt2 = conn.createStatement();
                String query2 = "SELECT DOD030,DOD210,DOD040,DOD060,DOD070\n"
                        + ",DOD075,DOD080,DOD120,okcunm,( TRIM(OKCUA1) ||' '||TRIM(OKCUA2) ||' '|| TRIM(OKCUA3)||' '|| TRIM(OKCUA4) ) AS sumadd\n"
                        + "FROM " + dbname + ".BMSDOD \n"
                        + "LEFT JOIN M3FDBPRD.Ocusma\n"
                        + "ON OKCUNO = DOD030 \n"
                        + "AND okcono = DOD010 \n"
                        + "WHERE DOD040  = '" + Customer + "'"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'";
//                System.out.println("Get Menu\n" + query);
                ResultSet mRes2 = stmt2.executeQuery(query2);

                while (mRes2.next()) {
//                    String fulladdress = mRes.getString(3).trim() + mRes.getString(4).trim() + mRes.getString(5).trim();
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("ID", mRes2.getString(1));
                    mMap.put("Ordernum", mRes2.getString(3));
                    mMap.put("name", mRes2.getString(9));
                    mMap.put("address", mRes2.getString(10));
                    mMap.put("farmer", mRes2.getString(1));
                    mMap.put("house", mRes2.getString(4));
                    mMap.put("QTY", mRes2.getString(6));
                    mMap.put("Price", mRes2.getString(5));
                    mMap.put("AMT", mRes2.getString(7));
                    mMap.put("Remaining", Remaining);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray dodCreateChange(String cono, String divi, String Customer) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String Amount = "0";
//        String name = "";
//        String name2;
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT COALESCE(sum(dod080)*-1,0) AS amt \n"
                        + "FROM " + dbname + ".BMSDOD\n"
                        + "WHERE DOD110 IN(20,30,40) \n"
                        + "and DOD210 = '20'\n"
                        + "AND DOD040 = '" + Customer + "'";
//                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Amount = mRes.getString(1);
//                    String fulladdress = mRes.getString(3).trim() + mRes.getString(4).trim() + mRes.getString(5).trim();

                }
                Statement stmt2 = conn.createStatement();
                String query2 = "SELECT DOD160,DOD030,DOD040\n"
                        + ",OKCUNM,( OKCUA1 || OKCUA2 || OKCUA3  ) AS sumadd,DOD060,DOD080\n"
                        + ",DOD070,DOD060,DOD075 \n"
                        + "FROM " + dbname + ".BMSDOD \n"
                        + "LEFT JOIN M3FDBPRD.OCUSMA \n"
                        + "ON DOD030= okcuno\n"
                        + "and DOD010 = OKcono \n"
                        + "WHERE DOD110='10' \n"
                        + "and DOD210 = '20'\n"
                        + "and DOD040= '" + Customer + "'";
//                System.out.println("Get Menu\n" + query);
                ResultSet mRes2 = stmt2.executeQuery(query2);

                while (mRes2.next()) {
//                    String fulladdress = mRes.getString(3).trim() + mRes.getString(4).trim() + mRes.getString(5).trim();
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("lblSUNO", mRes2.getString("DOD030"));
                    mMap.put("lblName", mRes2.getString("OKCUNM"));
                    mMap.put("lblAddress", mRes2.getString("sumadd"));
                    mMap.put("lblOwnGAMT", mRes2.getString("DOD080"));
                    mMap.put("lblQTY", mRes2.getString("DOD070"));
                    mMap.put("lblHOUSE", mRes2.getString("DOD060"));
                    mMap.put("lblTRATGNO", mRes2.getString("DOD040"));
                    mMap.put("lblSUMTRAGAMT", Amount);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static JSONArray getautoFWlist(String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = " SELECT distinct DOD040 , DOD040 || ' ' ||TRIM(okcunm) ||' '||SUBSTRING(DOD060,2)  AS FULLFW \n"
                        + " FROM  " + dbname + ".BMSDOD \n"
                        + " LEFT JOIN M3FDBPRD.OCUSMA \n"
                        + " ON DOD030= okcuno \n"
                        + " where DOD210 = 20 \n"
                        + " And DOD110 = 10 \n"
                        + "AND DOD010 = '" + cono + "'\n"
                        + "AND DOD020 = '" + divi + "'\n"
                        + " Order by DOD040 ASC";
                System.out.println("Get Menu\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    String value1 = mRes.getString(1);
                    if (value1 != null) {
                        value1 = value1.trim();
                    } else {
                        value1 = "";
                    }
                    mMap.put("approvecode", value1);
                    String value2 = mRes.getString(2);
                    if (value2 != null) {
                        value2 = value2.trim();
                    } else {
                        value2 = "";
                    }
                    mMap.put("approvelist", value2);
                    mJSonArr.put(mMap);
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

        return mJSonArr;

    }

    public static String dodCreateChange(String transnum, String type) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
//                Statement stmt2 = conn.createStatement();
                if (type.equals("1")) {
                    query1 = "UPDATE " + dbname + ".TSG_OWNERLS A\n"
                            + "SET A.OWNGSTS = (SELECT CASE \n"
                            + "	WHEN SUM(TRAGAMT) >= B.OWNGAMT THEN '1'\n"
                            + "	ELSE '0'\n"
                            + "END\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS B\n"
                            + "ON A.TRACAR = B.OWNCAR\n"
                            + "WHERE TRAPSTS = '1'\n"
                            + "AND TRACAR = '" + transnum + "'\n"
                            + "GROUP BY TRACAR,B.OWNGAMT)\n"
                            + "WHERE A.OWNCAR = '" + transnum + "'";
                }
                //incase if bug change 0 to B.OWNGAMT
                if (type.equals("2")) {
                    query1 = "UPDATE " + dbname + ".TSG_OWNERLS A\n"
                            + "SET A.OWNGSTS = (SELECT CASE \n"
                            + "	WHEN SUM(TRAGAMT) <= 0 THEN '9'\n"
                            + "	ELSE '0'\n"
                            + "END\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS B\n"
                            + "ON A.TRACAR = B.OWNCAR\n"
                            + "WHERE TRAPSTS = '1'\n"
                            + "AND TRACAR = '" + transnum + "'\n"
                            + "GROUP BY TRACAR,B.OWNGAMT)\n"
                            + "WHERE A.OWNCAR = '" + transnum + "'";
                }

                stmt.execute(query1);

                respond = "successfully updated";

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

    public static JSONArray getMaster(String completed, String type, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        String query = "";
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                if (completed.equals("0")) {
                    query = "SELECT DISTINCT  A.OWNSUNO,OWNNAME,IDTLNO,OWNCAR,OWNADDR,\n"
                            + "CASE\n"
                            + "WHEN OWNTYPE = 1 THEN 'รับเงินคืนประกัน L/D'\n"
                            + "WHEN OWNTYPE = 2 THEN 'รับเงินคืนประกัน FEED'\n"
                            + "WHEN OWNTYPE = 3 THEN 'รับเงินคืนประกัน DUCK'\n"
                            + "END AS TYPE\n"
                            + ",OWNTGDT,OWNGAMT,COALESCE(B.Amount,0) AS Amount,\n"
                            + "CASE \n"
                            + "WHEN OWNGSTS = '0' THEN 'INCOMPLETE'\n"
                            + "WHEN OWNGSTS = '1' THEN 'COMPLETE'\n"
                            + "WHEN OWNGSTS = '2' THEN 'TRANSFERED'\n"
                            + "WHEN OWNGSTS = '9' THEN 'CANCELED'\n"
                            + "END AS STATUS\n"
                            + "FROM M3FDBPRD.CIDMAS c\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS A\n"
                            + "ON A.OWNSUNO = C.IDSUNO\n"
                            + "LEFT JOIN\n"
                            + "(SELECT A.TRACAR,SUM(A.TRAGAMT) AS Amount,TRACONO,TRADIVI\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "WHERE TRAPSTS = 1\n"
                            + "GROUP BY TRACAR,TRACONO,TRADIVI) AS B\n"
                            + "ON B.TRACAR = A.OWNCAR\n"
                            + "AND B.TRACONO = OWNCONO\n"
                            + "AND B.TRADIVI = OWNDIVI\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                            + "AND OWNTYPE =" + type + "\n"
                            + "AND OWNCONO =" + cono + " \n"
                            + "AND OWNDIVI = " + divi + " \n"
                            + "ORDER BY A.OWNSUNO,IDTLNO,OWNCAR";
                } else if (completed.equals("1")) {
                    query = "SELECT DISTINCT  A.OWNSUNO,OWNNAME,IDTLNO,OWNCAR,OWNADDR,\n"
                            + "CASE\n"
                            + "WHEN OWNTYPE = 1 THEN 'รับเงินคืนประกัน L/D'\n"
                            + "WHEN OWNTYPE = 2 THEN 'รับเงินคืนประกัน FEED'\n"
                            + "WHEN OWNTYPE = 3 THEN 'รับเงินคืนประกัน DUCK'\n"
                            + "END AS TYPE\n"
                            + ",OWNTGDT,OWNGAMT,COALESCE(B.Amount,0) AS Amount,\n"
                            + "CASE \n"
                            + "WHEN OWNGSTS = '0' THEN 'INCOMPLETE'\n"
                            + "WHEN OWNGSTS = '1' THEN 'COMPLETE'\n"
                            + "WHEN OWNGSTS = '2' THEN 'TRANSFERED'\n"
                            + "WHEN OWNGSTS = '9' THEN 'CANCELED'\n"
                            + "END AS STATUS\n"
                            + "FROM M3FDBPRD.CIDMAS c\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS A\n"
                            + "ON A.OWNSUNO = C.IDSUNO\n"
                            + "LEFT JOIN\n"
                            + "(SELECT A.TRACAR,SUM(A.TRAGAMT) AS Amount,TRACONO,TRADIVI\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "WHERE TRAPSTS = 1\n"
                            + "GROUP BY TRACAR,TRACONO,TRADIVI) AS B\n"
                            + "ON B.TRACAR = A.OWNCAR\n"
                            + "AND B.TRACONO = OWNCONO\n"
                            + "AND B.TRADIVI = OWNDIVI\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                            + "AND OWNGSTS = 0\n"
                            + "AND OWNCONO =" + cono + " \n"
                            + "AND OWNDIVI = " + divi + " \n"
                            + "AND OWNTYPE =" + type + "\n"
                            + "ORDER BY A.OWNSUNO,IDTLNO,OWNCAR";
                } else if (completed.equals("2")) {
                    query = "SELECT DISTINCT  A.OWNSUNO,OWNNAME,IDTLNO,OWNCAR,OWNADDR,\n"
                            + "CASE\n"
                            + "WHEN OWNTYPE = 1 THEN 'รับเงินคืนประกัน L/D'\n"
                            + "WHEN OWNTYPE = 2 THEN 'รับเงินคืนประกัน FEED'\n"
                            + "WHEN OWNTYPE = 3 THEN 'รับเงินคืนประกัน DUCK'\n"
                            + "END AS TYPE\n"
                            + ",OWNTGDT,OWNGAMT,COALESCE(B.Amount,0) AS Amount,\n"
                            + "CASE \n"
                            + "WHEN OWNGSTS = '0' THEN 'INCOMPLETE'\n"
                            + "WHEN OWNGSTS = '1' THEN 'COMPLETE'\n"
                            + "WHEN OWNGSTS = '2' THEN 'TRANSFERED'\n"
                            + "WHEN OWNGSTS = '9' THEN 'CANCELED'\n"
                            + "END AS STATUS\n"
                            + "FROM M3FDBPRD.CIDMAS c\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS A\n"
                            + "ON A.OWNSUNO = C.IDSUNO\n"
                            + "LEFT JOIN\n"
                            + "(SELECT A.TRACAR,SUM(A.TRAGAMT) AS Amount,TRACONO,TRADIVI\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "WHERE TRAPSTS = 1\n"
                            + "GROUP BY TRACAR,TRACONO,TRADIVI) AS B\n"
                            + "ON B.TRACAR = A.OWNCAR\n"
                            + "AND B.TRACONO = OWNCONO\n"
                            + "AND B.TRADIVI = OWNDIVI\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                            + "AND OWNGSTS = 1\n"
                            + "AND OWNTYPE =" + type + "\n"
                            + "AND OWNCONO =" + cono + " \n"
                            + "AND OWNDIVI = " + divi + " \n"
                            + "ORDER BY A.OWNSUNO,IDTLNO,OWNCAR";
                } else if (completed.equals("3")) {
                    query = "SELECT DISTINCT  A.OWNSUNO,OWNNAME,IDTLNO,OWNCAR,OWNADDR,\n"
                            + "CASE\n"
                            + "WHEN OWNTYPE = 1 THEN 'รับเงินคืนประกัน L/D'\n"
                            + "WHEN OWNTYPE = 2 THEN 'รับเงินคืนประกัน FEED'\n"
                            + "WHEN OWNTYPE = 3 THEN 'รับเงินคืนประกัน DUCK'\n"
                            + "END AS TYPE\n"
                            + ",OWNTGDT,OWNGAMT,COALESCE(B.Amount,0) AS Amount,\n"
                            + "CASE \n"
                            + "WHEN OWNGSTS = '0' THEN 'INCOMPLETE'\n"
                            + "WHEN OWNGSTS = '1' THEN 'COMPLETE'\n"
                            + "WHEN OWNGSTS = '2' THEN 'TRANSFERED'\n"
                            + "WHEN OWNGSTS = '9' THEN 'CANCELED'\n"
                            + "END AS STATUS\n"
                            + "FROM M3FDBPRD.CIDMAS c\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS A\n"
                            + "ON A.OWNSUNO = C.IDSUNO\n"
                            + "LEFT JOIN\n"
                            + "(SELECT A.TRACAR,SUM(A.TRAGAMT) AS Amount,TRACONO,TRADIVI\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "WHERE TRAPSTS = 1\n"
                            + "GROUP BY TRACAR,TRACONO,TRADIVI) AS B\n"
                            + "ON B.TRACAR = A.OWNCAR\n"
                            + "AND B.TRACONO = OWNCONO\n"
                            + "AND B.TRADIVI = OWNDIVI\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                            + "AND OWNGSTS = 2\n"
                            + "AND OWNTYPE =" + type + "\n"
                            + "AND OWNCONO =" + cono + " \n"
                            + "AND OWNDIVI = " + divi + " \n"
                            + "ORDER BY A.OWNSUNO,IDTLNO,OWNCAR";
                } else if (completed.equals("4")) {
                    query = "SELECT DISTINCT  A.OWNSUNO,OWNNAME,IDTLNO,OWNCAR,OWNADDR,\n"
                            + "CASE\n"
                            + "WHEN OWNTYPE = 1 THEN 'รับเงินคืนประกัน L/D'\n"
                            + "WHEN OWNTYPE = 2 THEN 'รับเงินคืนประกัน FEED'\n"
                            + "WHEN OWNTYPE = 3 THEN 'รับเงินคืนประกัน DUCK'\n"
                            + "END AS TYPE\n"
                            + ",OWNTGDT,OWNGAMT,COALESCE(B.Amount,0) AS Amount,\n"
                            + "CASE \n"
                            + "WHEN OWNGSTS = '0' THEN 'INCOMPLETE'\n"
                            + "WHEN OWNGSTS = '1' THEN 'COMPLETE'\n"
                            + "WHEN OWNGSTS = '2' THEN 'TRANSFERED'\n"
                            + "WHEN OWNGSTS = '9' THEN 'CANCELED'\n"
                            + "END AS STATUS\n"
                            + "FROM M3FDBPRD.CIDMAS c\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS A\n"
                            + "ON A.OWNSUNO = C.IDSUNO\n"
                            + "LEFT JOIN\n"
                            + "(SELECT A.TRACAR,SUM(A.TRAGAMT) AS Amount,TRACONO,TRADIVI\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "WHERE TRAPSTS = 1\n"
                            + "GROUP BY TRACAR,TRACONO,TRADIVI) AS B\n"
                            + "ON B.TRACAR = A.OWNCAR\n"
                            + "AND B.TRACONO = OWNCONO\n"
                            + "AND B.TRADIVI = OWNDIVI\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                            + "AND OWNGSTS = 9\n"
                            + "AND OWNTYPE =" + type + "\n"
                            + "AND OWNCONO =" + cono + " \n"
                            + "AND OWNDIVI = " + divi + " \n"
                            + "ORDER BY A.OWNSUNO,IDTLNO,OWNCAR";
                }
                System.out.println(query);
                ResultSet mRes = stmt.executeQuery(query);
                while (mRes.next()) {
                    String value1 = mRes.getString(1);
                    if (value1 != null) {
                        value1 = value1.trim();
                    }
                    String value2 = mRes.getString(2);
                    if (value2 != null) {
                        value2 = value2.trim();
                    }
                    String value3 = mRes.getString(3);
                    if (value3 != null) {
                        value3 = value3.trim();
                    }
                    String value4 = mRes.getString(4);
                    if (value4 != null) {
                        value4 = value4.trim();
                    }
                    String value5 = mRes.getString(5);
                    if (value5 != null) {
                        value5 = value5.trim();
                    }
                    String value6 = mRes.getString(6);
                    if (value6 != null) {
                        value6 = value6.trim();
                    }
                    String value7 = mRes.getString(7);
                    if (value7 != null) {
                        value7 = value7.trim();
                    }
                    String value8 = mRes.getString(8);
                    if (value8 != null) {
                        value8 = value8.trim();
                    }
                    String value9 = mRes.getString(9);
                    if (value9 != null) {
                        value9 = value9.trim();
                    }
                    String value10 = mRes.getString(10);
                    if (value10 != null) {
                        value10 = value10.trim();
                    }
//                    String value11 = mRes.getString(11);
//                    if (value11 != null) {
//                        value11 = value11.trim();
//                    }
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("RCOMPANY", value1);
                    mMap.put("RCUSTOMERID", value2);
                    mMap.put("RLSCODE", value3);
                    mMap.put("RTKCODE", value4);
                    mMap.put("RDESCRIPTION", value5);
                    mMap.put("RTYPE", value6);
                    value7 = value7.substring(4, 6) + "-" + value7.substring(6, 8) + "-" + value7.substring(0, 4);
                    mMap.put("RDATE", value7);
                    mMap.put("RTOTAL", value8);
                    mMap.put("RSUM", value9);
                    mMap.put("ROUNDSTATUS", value10);
//                    mMap.put("ROUNDCASEPAY", value9);
//                    mMap.put("ROUNDPAYSPECIAL", value11);
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getHistory(String type, String car, String supplier, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String query2 = "";
        String carname = car;
        String suppliername = supplier;
        if (!supplier.equals("")) {
            String[] supplierlist = supplier.split(":");
            suppliername = supplierlist[0];
        }
        if (!car.equals("")) {
            String[] carlist = car.split(":");
            carname = carlist[0];
        }
        try {
            Statement stmt2 = conn.createStatement();
            if (conn != null) {
                if ("All Supplier".equals(suppliername) || "".equals(suppliername)) {
                    query2 = "SELECT \n"
                            + "CASE WHEN TRATGTY = '1' THEN 'รับเงิน'\n"
                            + "WHEN TRATGTY = '2' THEN 'คืนเงิน'  END AS tratype\n"
                            + ",TRATGNO,TRATGDT,TRASUNO,TRADESC,TRAGAMT,TRASAMT,TRATAMT,TRAUSER,\n"
                            + "CASE WHEN TRATYPE = '1' THEN 'รับเงินคืนประกัน L/D'\n"
                            + "WHEN TRATYPE = '2' THEN 'รับเงินคืนประกัน FEED'\n"
                            + "WHEN TRATYPE = '3' THEN 'รับเงินคืนประกัน DUCK'\n"
                            + "END AS TYPE,TRAGSTS,\n"
                            + "CASE \n"
                            + "	WHEN TRAPSTS = '0' THEN 'NO PRINT'\n"
                            + "WHEN TRAPSTS = '1' THEN 'PRINTED'\n"
                            + "WHEN TRAPSTS = '3' THEN 'REJECTED'\n"
                            + "WHEN TRAPSTS = '9' THEN 'CANCELLED'\n"
                            + "	END\n"
                            + ",TRACAR,TRACUSER,TRAVOUCHER,TRACDATE,TRACTIME,DCTX40,suppliername\n"
                            + "FROM \n"
                            + dbname + ".TSG_TRANLS A\n"
                            + " LEFT JOIN\n"
                            + "(SELECT DCTRCA,DCTX40,DCFWNO\n"
                            + "FROM " + dbM3Name + ".DCARRI) AS E\n"
                            + "ON TRACAR = E.DCTRCA\n"
                            + "LEFT JOIN\n"
                            + "(SELECT DISTINCT  IDSUNO,A.OWNSUNO, OWNNAME AS suppliername,OWNCONO,OWNDIVI\n"
                            + "FROM " + dbM3Name + ".CIDMAS c\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS A\n"
                            + "ON A.OWNSUNO = C.IDSUNO\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                            + "AND A.OWNTYPE = 3) AS B\n"
                            + "ON A.TRASUNO = B.IDSUNO\n"
                            + "AND B.OWNCONO = A.TRACONO\n"
                            + "AND B.OWNDIVI = A.TRADIVI\n"
                            + "WHERE TRATGTY = '" + type + "'\n"
                            + "AND  B.OWNCONO =" + cono + "\n"
                            + "AND B.OWNDIVI =" + divi;
                    System.out.println(query2);

                } else if (!"".equals(carname) && "All Car".equals(carname)) {
                    query2 = "SELECT \n"
                            + "CASE WHEN TRATGTY = '1' THEN 'รับเงิน'\n"
                            + "WHEN TRATGTY = '2' THEN 'คืนเงิน'  END AS tratype\n"
                            + ",TRATGNO,TRATGDT,TRASUNO,TRADESC,TRAGAMT,TRASAMT,TRATAMT,TRAUSER,\n"
                            + "CASE WHEN TRATYPE = '1' THEN 'รับเงินคืนประกัน L/D'\n"
                            + "WHEN TRATYPE = '2' THEN 'รับเงินคืนประกัน FEED'\n"
                            + "WHEN TRATYPE = '3' THEN 'รับเงินคืนประกัน DUCK'\n"
                            + "END AS TYPE,TRAGSTS,\n"
                            + "CASE \n"
                            + "	WHEN TRAPSTS = '0' THEN 'NO PRINT'\n"
                            + "WHEN TRAPSTS = '1' THEN 'PRINTED'\n"
                            + "WHEN TRAPSTS = '3' THEN 'REJECTED'\n"
                            + "WHEN TRAPSTS = '9' THEN 'CANCELLED'\n"
                            + "	END\n"
                            + ",TRACAR,TRACUSER,TRAVOUCHER,TRACDATE,TRACTIME,DCTX40,suppliername\n"
                            + "FROM \n"
                            + "BRLDTA0100.TSG_TRANLS A\n"
                            + " LEFT JOIN\n"
                            + "(SELECT DCTRCA,DCTX40,DCFWNO\n"
                            + "FROM M3FDBPRD.DCARRI) AS E\n"
                            + "ON TRACAR = E.DCTRCA\n"
                            + "LEFT JOIN\n"
                            + "(SELECT DISTINCT  IDTLNO,IDSUNO,A.OWNSUNO, OWNNAME AS suppliername,OWNCONO,OWNDIVI\n"
                            + "FROM M3FDBPRD.CIDMAS c\n"
                            + "JOIN BRLDTA0100.TSG_OWNERLS A\n"
                            + "ON A.OWNSUNO = C.IDSUNO\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                            + "AND A.OWNTYPE = 3) AS B\n"
                            + "ON A.TRASUNO = B.IDSUNO\n"
                            + "AND B.OWNCONO = A.TRACONO\n"
                            + "AND B.OWNDIVI = A.TRADIVI\n"
                            + "WHERE TRATGTY = '" + type + "'\n"
                            + "AND B. IDTLNO = '" + suppliername + "'\n"
                            + "AND  B.OWNCONO =" + cono + "\n"
                            + "AND B.OWNDIVI =" + divi;
                } else {
                    query2 = "SELECT \n"
                            + "CASE WHEN TRATGTY = '1' THEN 'รับเงิน'\n"
                            + "WHEN TRATGTY = '2' THEN 'คืนเงิน'  END AS tratype\n"
                            + ",TRATGNO,TRATGDT,TRASUNO,TRADESC,TRAGAMT,TRASAMT,TRATAMT,TRAUSER,\n"
                            + "CASE WHEN TRATYPE = '1' THEN 'รับเงินคืนประกัน L/D'\n"
                            + "WHEN TRATYPE = '2' THEN 'รับเงินคืนประกัน FEED'\n"
                            + "WHEN TRATYPE = '3' THEN 'รับเงินคืนประกัน DUCK'\n"
                            + "END AS TYPE,TRAGSTS,\n"
                            + "CASE \n"
                            + "	WHEN TRAPSTS = '0' THEN 'NO PRINT'\n"
                            + "WHEN TRAPSTS = '1' THEN 'PRINTED'\n"
                            + "WHEN TRAPSTS = '3' THEN 'REJECTED'\n"
                            + "WHEN TRAPSTS = '9' THEN 'CANCELLED'\n"
                            + "	END\n"
                            + ",TRACAR,TRACUSER,TRAVOUCHER,TRACDATE,TRACTIME,DCTX40,suppliername\n"
                            + "FROM \n"
                            + "BRLDTA0100.TSG_TRANLS A\n"
                            + " LEFT JOIN\n"
                            + "(SELECT DCTRCA,DCTX40,DCFWNO\n"
                            + "FROM M3FDBPRD.DCARRI) AS E\n"
                            + "ON TRACAR = E.DCTRCA\n"
                            + "LEFT JOIN\n"
                            + "(SELECT DISTINCT  IDSUNO,A.OWNSUNO, OWNNAME AS suppliername,OWNCONO,OWNDIVI\n"
                            + "FROM M3FDBPRD.CIDMAS c\n"
                            + "JOIN BRLDTA0100.TSG_OWNERLS A\n"
                            + "ON A.OWNSUNO = C.IDSUNO\n"
                            + "WHERE IDCONO = '10'\n"
                            + "AND IDSTAT = '20'\n"
                            + "AND IDTFNO = 'DELIVERY'\n"
                            + "AND SUBSTRING(IDTLNO,0,3) = 'LS'\n"
                            + "AND A.OWNTYPE = 3) AS B\n"
                            + "ON A.TRASUNO = B.IDSUNO\n"
                            + "AND B.OWNCONO = A.TRACONO\n"
                            + "AND B.OWNDIVI = A.TRADIVI\n"
                            + "WHERE TRATGTY = '" + type + "'\n"
                            + "AND TRACAR = '" + carname + "'\n"
                            + "AND  B.OWNCONO =" + cono + "\n"
                            + "AND B.OWNDIVI =" + divi;
                }
                ResultSet mRes = stmt2.executeQuery(query2);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();

                    mMap.put("RCOMPANY", mRes.getString(1));
                    mMap.put("RCUSTOMERID", mRes.getString(2));
                    mMap.put("RCUSTOMERNAME", mRes.getString(3).substring(6, 8) + "-" + mRes.getString(3).substring(4, 6) + "-" + mRes.getString(3).substring(0, 4));
                    mMap.put("RSTARTDATE", mRes.getString(4));
                    mMap.put("RDESCRIPTION", mRes.getString(5));
                    mMap.put("RENDDATE", mRes.getString(6));
                    mMap.put("RBILLDATE", mRes.getString(7));
                    mMap.put("RPAYDATE", mRes.getString(8));
                    mMap.put("RROUND", mRes.getString(9));
                    mMap.put("RSTATUS", mRes.getString(10));
                    mMap.put("RSTATUS2", mRes.getString(11));
                    mMap.put("RSTATUS3", mRes.getString(12));
                    mMap.put("RCAR", mRes.getString(13));
                    mMap.put("RCUSER", mRes.getString(14));
                    mMap.put("RVOUCHER", mRes.getString(15));
                    mMap.put("RCDATE", mRes.getString(16));
                    mMap.put("RCTIME", mRes.getString(17));
                    mMap.put("RCARNAME", mRes.getString(18));
                    mMap.put("RSUPPLIERNAME", mRes.getString(19));
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static String changeprintstatus(String transnum, String username) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";
        double num = Double.parseDouble(transnum);

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();

                query1 = "UPDATE " + dbname + ".TSG_TRANLS\n"
                        + "SET TRAPSTS = 1\n"
                        + ",TRACUSER = '" + username + "'\n"
                        + ",TRACDATE = CURRENT date\n"
                        + ",TRACTIME = CURRENT time\n"
                        + "WHERE TRATGNO =" + transnum;
                System.out.println("Check if data exist or not\n" + query1);

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

    public static String approvemaster(String username, String runningnum, String cono, String divi) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "Problem has been occured";
//        double num = Double.parseDouble(transnum);
        String currentdate = GetDateDecmalCurrenttime();
        String currenttime = Gettimenow();
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();

                query1 = "UPDATE " + dbname + ".BMSDOD \n"
                        + " SET DOD210  =  '20',\n"
                        + "  DOD130  =  '" + username + "',\n"
                        + "  DOD090 = '" + currentdate + "',\n"
                        + "  DOD100 = NOW(),\n"
                        + "  DOD140 = NOW(),\n"
                        + "  DOD160 = '" + currentdate + "',\n"
                        + "  DOD170 = '" + currenttime + "',\n"
                        + "  DOD180 = '" + currentdate + "'\n"
                        + "  WHERE DOD040 = '" + runningnum + "'\n"
                        + " AND DOD010 = '" + cono + "' AND DOD020 ='" + divi + "'";
                System.out.println("Check if data exist or not\n" + query1);

                stmt.execute(query1);

                respond = "ยอมรับเรียบร้อย";

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

    public static String changestatusifsameamount(String transnum, String type) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
//                Statement stmt2 = conn.createStatement();
                if (type.equals("1")) {
                    query1 = "UPDATE " + dbname + ".TSG_OWNERLS A\n"
                            + "SET A.OWNGSTS = (SELECT CASE \n"
                            + "	WHEN SUM(TRAGAMT) >= B.OWNGAMT THEN '1'\n"
                            + "	ELSE '0'\n"
                            + "END\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS B\n"
                            + "ON A.TRACAR = B.OWNCAR\n"
                            + "WHERE TRAPSTS = '1'\n"
                            + "AND TRACAR = '" + transnum + "'\n"
                            + "GROUP BY TRACAR,B.OWNGAMT)\n"
                            + "WHERE A.OWNCAR = '" + transnum + "'";
                }
                //incase if bug change 0 to B.OWNGAMT
                if (type.equals("2")) {
                    query1 = "UPDATE " + dbname + ".TSG_OWNERLS A\n"
                            + "SET A.OWNGSTS = (SELECT CASE \n"
                            + "	WHEN SUM(TRAGAMT) <= 0 THEN '9'\n"
                            + "	ELSE '0'\n"
                            + "END\n"
                            + "FROM " + dbname + ".TSG_TRANLS A\n"
                            + "JOIN " + dbname + ".TSG_OWNERLS B\n"
                            + "ON A.TRACAR = B.OWNCAR\n"
                            + "WHERE TRAPSTS = '1'\n"
                            + "AND TRACAR = '" + transnum + "'\n"
                            + "GROUP BY TRACAR,B.OWNGAMT)\n"
                            + "WHERE A.OWNCAR = '" + transnum + "'";
                }

                stmt.execute(query1);

                respond = "successfully updated";

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

    public static String rejecthistory(String transnum, String username, String status) throws Exception {

        Connection conn = ConnectDB2.ConnectionDB();
        String query1 = "";
        String respond = "";
        Integer check = 1;
//        double num = Double.parseDouble(transnum);

        try {
            if (conn != null) {
                if (status.equals("CANCELLED")) {
                    check = 0;
                    respond = "You cannot reject cancelled order";
                }
                if (status.equals("REJECTED")) {
                    check = 0;
                    respond = "Order is already rejected";
                }
                if (check == 1) {
                    Statement stmt = conn.createStatement();
                    Statement stmt2 = conn.createStatement();
                    query1 = "UPDATE " + dbname + ".TSG_TRANLS\n"
                            + "SET TRAPSTS = 3,TRACUSER = '" + username + "'\n"
                            + ",TRACDATE = CURRENT Date \n"
                            + ",TRACTIME = CURRENT time \n"
                            + "WHERE TRATGNO =" + transnum;

                    stmt.execute(query1);
                    respond = "The order" + transnum + "has been rejected!";
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

//---------------------------------------------------------------------------------
    public static JSONArray getCarHistory(String supplier, String cono, String divi) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();
        String[] suppliersplited = supplier.split(":");
        String supplier1 = suppliersplited[0];
        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT OWNCAR||':'||E.DCTX40\n"
                        + "FROM " + dbname + ".TSG_OWNERLS\n"
                        + " LEFT JOIN\n"
                        + "(SELECT DCTRCA,DCTX40,DCFWNO\n"
                        + "FROM M3FDBPRD.DCARRI) AS E\n"
                        + "ON OWNCAR = E.DCTRCA\n"
                        + "WHERE E.DCFWNO = '" + supplier1 + "'\n"
                        + "AND OWNCONO =" + cono + "\n"
                        + "AND OWNDIVI =" + divi + "\n"
                        + "ORDER BY OWNCAR ";
                System.out.println("getPeriod\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vYear", mRes.getString(1).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getTKtype(String type) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
                String query = "SELECT OWNSUNO,OWNCAR||'| '||OWNADDR\n"
                        + "FROM " + dbname + ".TSG_OWNERLS\n"
                        + "WHERE OWNTYPE = '" + type + "' ";
                System.out.println("getOrder\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vLSdata", mRes.getString(1).trim());
                    mMap.put("vLS", mRes.getString(2).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

    public static JSONArray getTransferdata(String cono, String divi, String invround) throws Exception {

        JSONArray mJSonArr = new JSONArray();
        Connection conn = ConnectDB2.ConnectionDB();

        try {
            if (conn != null) {

                Statement stmt = conn.createStatement();
//                String query = "SELECT  A.TRATGTY,TRATGNO,TRATGDT,TRASUNO,TRADESC,TRAGAMT,TRASAMT,TRATAMT,TRAUSER,TRAGSTS,TRAPSTS,A.TRACAR\n"
//                        + ",B.OWNNAME,COALESCE(C.SUMOFAMOUNT,0),DCTX15,DCTX40\n"
//                        + "FROM\n"
//                        + "(SELECT *\n"
//                        + "FROM " + dbname + ".TSG_TRANLS A) AS A\n"
//                        + "RIGHT JOIN\n"
//                        + "(SELECT * FROM \n"
//                        + dbname + ".TSG_OWNERLS B) AS B\n"
//                        + "ON A.TRACAR = B.OWNCAR\n"
//                        + "LEFT JOIN \n"
//                        + "(\n"
//                        + "SELECT TRACAR,SUM(TRAGAMT) AS SUMOFAMOUNT\n"
//                        + "FROM " + dbname + ".TSG_TRANLS A\n"
//                        + "WHERE TRAPSTS = 1\n"
//                        + "AND TRATGDT <= \n"
//                        + "(SELECT TRATGDT \n"
//                        + "FROM " + dbname + ".TSG_TRANLS A\n"
//                        + "WHERE TRATGNO = " + invround + ")\n"
//                        + "GROUP BY TRACAR \n"
//                        + ") AS C\n"
//                        + "ON A.TRACAR = C.TRACAR\n"
//                        + "LEFT JOIN\n"
//                        + "(SELECT DCTRCA,DCTX15,DCTX40\n"
//                        + "FROM M3FDBPRD.DCARRI) AS D\n"
//                        + "ON A.TRACAR = D.DCTRCA\n"
//                        + "WHERE A.TRATGNO = '" + invround + "'";

                String query = "SELECT  A.TRATGTY,TRATGNO,TRATGDT,TRASUNO,TRADESC,TRAGAMT,TRASAMT,TRATAMT,TRAUSER,TRAGSTS,TRAPSTS,A.TRACAR\n"
                        + ",B.OWNNAME,COALESCE(C.SUMOFAMOUNT,0),DCTX15,DCTX40,TRIM(E.IDTLNO)||': '||OWNNAME,TRIM(B.OWNCAR)||' : '|| OWNADDR\n"
                        + "FROM\n"
                        + "(SELECT *\n"
                        + "FROM " + dbname + ".TSG_TRANLS A) AS A\n"
                        + "RIGHT JOIN\n"
                        + "(SELECT * FROM \n"
                        + "" + dbname + ".TSG_OWNERLS B) AS B\n"
                        + "ON A.TRACAR = B.OWNCAR\n"
                        + "AND A.TRACONO = B.OWNCONO\n"
                        + "AND A.TRADIVI = B.OWNDIVI\n"
                        + "LEFT JOIN \n"
                        + "(\n"
                        + "SELECT TRACAR,SUM(TRAGAMT) AS SUMOFAMOUNT\n"
                        + "FROM " + dbname + ".TSG_TRANLS A\n"
                        + "WHERE TRAPSTS = 1\n"
                        + "AND TRATGDT <= \n"
                        + "(SELECT TRATGDT\n"
                        + "FROM " + dbname + ".TSG_TRANLS A\n"
                        + "WHERE TRATGNO = '" + invround + "')\n"
                        + "GROUP BY TRACAR \n"
                        + ") AS C\n"
                        + "ON A.TRACAR = C.TRACAR\n"
                        + "LEFT JOIN\n"
                        + "(SELECT DCTRCA,DCTX15,DCTX40\n"
                        + "FROM M3FDBPRD.DCARRI) AS D\n"
                        + "ON A.TRACAR = D.DCTRCA\n"
                        + "LEFT JOIN\n"
                        + "M3FDBPRD.CIDMAS E\n"
                        + "ON B.OWNSUNO = E.IDSUNO\n"
                        + "AND E.IDCONO = A.TRACONO\n"
                        + "WHERE A.TRATGNO = '" + invround + "'\n"
                        + "AND TRACONO = " + cono + "\n"
                        + "AND TRADIVI = " + divi;

                System.out.println("getPeriod\n" + query);
                ResultSet mRes = stmt.executeQuery(query);

                while (mRes.next()) {
                    String year = mRes.getString(3).trim().substring(0, 4);
                    String month = mRes.getString(3).trim().substring(4, 6);
                    String day = mRes.getString(3).trim().substring(6, 8);
                    Map<String, Object> mMap = new HashMap<>();
                    mMap.put("vTrantype", mRes.getString(1).trim());
                    mMap.put("vTransactionnum2", mRes.getString(2).trim());
                    mMap.put("vDate", day + "-" + month + "-" + year);
                    mMap.put("vLSdata", mRes.getString(17).trim());
                    mMap.put("vDriverdata", mRes.getString(18).trim());
                    mMap.put("vDescription", mRes.getString(5).trim());
                    String amount = mRes.getString(6).trim();
                    amount = amount.replace("-", "");
                    mMap.put("vAmount", amount);
                    mMap.put("vHowtoread", mRes.getString(8).trim());
                    mMap.put("vUser", mRes.getString(9).trim());
                    mMap.put("vCarform", mRes.getString(12).trim());
                    String numericString = mRes.getString(14).trim();
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    Number number = numberFormat.parse(numericString);

                    // Format the number with commas
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    String formattedString = decimalFormat.format(number);
                    mMap.put("vAmountleft", formattedString);
                    mMap.put("vCarLicense", mRes.getString(15).trim());
                    mMap.put("vCustomer", mRes.getString(16).trim());
                    mJSonArr.put(mMap);

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

        return mJSonArr;

    }

}
