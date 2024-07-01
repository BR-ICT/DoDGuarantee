/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.report;

import com.br.connection.ConnectDB2;
import com.br.connection.ConnectSQLServer;
import static com.br.data.Select.getHowtoread;
import static com.br.utility.Constant.dbname;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Wattana
 */
public class Report extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");

        String report = request.getParameter("report");
        System.out.println("report: " + report);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        JasperDesign JPDnew;
        JasperReport jasperNew;
        Connection conn = null;
        Connection conn2 = null;
        Connection conn3 = null;
        Connection conn4 = null;

        switch (report) {

            case "DodReport_M3t":
//                JPDnew;
                try {
                String testOrNot = "";
                if (dbname.equals("BRLDTABK01")) {
                    testOrNot = "_test";
                } else {
                    testOrNot = "";
                }
                String path2 = getServletContext().getRealPath("/jaspers/");

                JPDnew = JRXmlLoader.load(path2 + "DodReport_M3t" + testOrNot + ".jrxml");
                jasperNew = JasperCompileManager.compileReport(JPDnew);
                File reportFile = new File(getServletContext().getRealPath("jaspers/DodReport_M3t" + testOrNot + ".jasper"));

                conn2 = ConnectDB2.ConnectionDB();
                String Fromdate = request.getParameter("FromDate");
                Fromdate = Fromdate.substring(6, 10) + Fromdate.substring(3, 5) + Fromdate.substring(0, 2);
                String Todate = request.getParameter("ToDate");
                Todate = Todate.substring(6, 10) + Todate.substring(3, 5) + Todate.substring(0, 2);
                Map parameters2 = new HashMap();
                parameters2.put("supplier", request.getParameter("supplier"));
                parameters2.put("FromDate", Integer.parseInt(Fromdate));
                parameters2.put("ToDate", Integer.parseInt(Todate));
                JasperPrint jasp = JasperFillManager.fillReport(jasperNew, parameters2, conn2);
                ServletOutputStream ouputStream2 = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasp, ouputStream2);
                ouputStream2.flush();
                ouputStream2.close();

            } catch (JRException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "DodReportALL_M3t":

//                JPDnew;
                try {
                String testOrNot = "";
                if (dbname.equals("BRLDTABK01")) {
                    testOrNot = "_test";
                } else {
                    testOrNot = "";
                }
                String path2 = getServletContext().getRealPath("/jaspers/");

                JPDnew = JRXmlLoader.load(path2 + "DodReportALL_M3t" + testOrNot + ".jrxml");
                jasperNew = JasperCompileManager.compileReport(JPDnew);
                File reportFile = new File(getServletContext().getRealPath("jaspers/DodReportALL_M3t" + testOrNot + ".jasper"));

                conn2 = ConnectDB2.ConnectionDB();
                String Fromdate = request.getParameter("FromDate");
                Fromdate = Fromdate.substring(6, 10) + Fromdate.substring(3, 5) + Fromdate.substring(0, 2);
                String Todate = request.getParameter("ToDate");
                Todate = Todate.substring(6, 10) + Todate.substring(3, 5) + Todate.substring(0, 2);
                Map parameters2 = new HashMap();
//                parameters2.put("supplier", request.getParameter("supplier"));
                parameters2.put("FromDate", Integer.parseInt(Fromdate));
                parameters2.put("ToDate", Integer.parseInt(Todate));

                JasperPrint jasp = JasperFillManager.fillReport(jasperNew, parameters2, conn2);
                ServletOutputStream ouputStream2 = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasp, ouputStream2);
                ouputStream2.flush();
                ouputStream2.close();

            } catch (JRException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "DODReportsumdod":
                
//                JPDnew;
                try {
                String testOrNot = "";
                if (dbname.equals("BRLDTABK01")) {
                    testOrNot = "_test";
                } else {
                    testOrNot = "";
                }
                String path2 = getServletContext().getRealPath("/jaspers/");

                JPDnew = JRXmlLoader.load(path2 + "DODReportsumdod" + testOrNot + ".jrxml");
                jasperNew = JasperCompileManager.compileReport(JPDnew);
                File reportFile = new File(getServletContext().getRealPath("jaspers/DODReportsumdod." + testOrNot + "jasper"));

                conn2 = ConnectDB2.ConnectionDB();
                String Fromdate = request.getParameter("FromDate");
                Fromdate = Fromdate.substring(6, 10) + Fromdate.substring(3, 5) + Fromdate.substring(0, 2);
                String Todate = request.getParameter("ToDate");
                Todate = Todate.substring(6, 10) + Todate.substring(3, 5) + Todate.substring(0, 2);
                Map parameters2 = new HashMap();
//                parameters2.put("supplier", request.getParameter("supplier"));
                parameters2.put("FromDate", Integer.parseInt(Fromdate));
                parameters2.put("ToDate", Integer.parseInt(Todate));

                JasperPrint jasp = JasperFillManager.fillReport(jasperNew, parameters2, conn2);
                ServletOutputStream ouputStream2 = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasp, ouputStream2);
                ouputStream2.flush();
                ouputStream2.close();

            } catch (JRException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "DODStatementM3":

//                JPDnew;
                try {
                String testOrNot = "";
                if (dbname.equals("BRLDTABK01")) {
                    testOrNot = "_test";
                } else {
                    testOrNot = "";
                }
                String path2 = getServletContext().getRealPath("/jaspers/");
                String imgurl = path2 + "icon.png";
                JPDnew = JRXmlLoader.load(path2 + "DODStatementM3" + testOrNot + ".jrxml");
                jasperNew = JasperCompileManager.compileReport(JPDnew);
                File reportFile = new File(getServletContext().getRealPath("jaspers/DODStatementM3" + testOrNot + ".jasper"));
                String[] infor = (request.getParameter("OrderNo")).split("_");
                String cono = request.getParameter("cono");
                String divi = request.getParameter("divi");
                String suppliercode = infor[0];
                String Date = infor[1];
                String Status = infor[2];
                String amount = infor[3];
                if (amount.contains("-")) {
                    amount = amount.substring(1);
                }
                String howtoread = getHowtoread(amount);
                conn2 = ConnectDB2.ConnectionDB();
//                String Date = request.getParameter("FromDate");
//                Date = Date.substring(6, 10) + Date.substring(3, 5) + Date.substring(0, 2);
//                String Todate = request.getParameter("ToDate");
//                Todate = Todate.substring(6, 10) + Todate.substring(3, 5) + Todate.substring(0, 2);
                Map parameters2 = new HashMap();
//                parameters2.put("supplier", request.getParameter("supplier"));
                parameters2.put("imageurl", imgurl);
                parameters2.put("Date", Integer.parseInt(Date));
                parameters2.put("type", Integer.parseInt(Status));
                parameters2.put("Customerno", suppliercode);
                parameters2.put("HowToRead", howtoread);

                JasperPrint jasp = JasperFillManager.fillReport(jasperNew, parameters2, conn2);
                ServletOutputStream ouputStream2 = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasp, ouputStream2);
                ouputStream2.flush();
                ouputStream2.close();

            } catch (JRException ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

            default:
                break;

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
