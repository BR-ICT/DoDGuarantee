/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.api;

import com.br.data.Delete;
import com.br.data.Insert;
import com.br.data.Select;
import com.br.data.Update;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wattana
 */
public class Action extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String path = request.getParameter("path");
        // String path = request.getServletPath();
        System.out.println("path: " + path);

        switch (path) {
            case "getCompany":
                out.print(Select.getCompany());
                out.flush();
                break;
            case "getMenu":
                out.print(Select.getMenu(request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getMenuDetail":
                out.print(Select.getMenuDetail(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("selecteditem"),
                        request.getParameter("Name")
                ));
                out.flush();
                break;
            case "CloseDoDTable":
                out.print(Select.CloseDoDTable(request.getParameter("cono"),
                        request.getParameter("divi"), request.getParameter("DoDno")));
                out.flush();
                break;
            case "savetheorder":
                out.print(Select.savetheorder(
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("customer"),
                        request.getParameter("farmer"),
                        request.getParameter("number"),
                        request.getParameter("qty"),
                        request.getParameter("price"),
                        request.getParameter("amount"),
                        request.getParameter("reason"),
                        request.getParameter("user")));
                out.flush();
                break;
            case "SaveCreateDODorder":
                out.print(Select.SaveCreateDODorder(
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("customer"),
                        request.getParameter("lblSUNO"),
                        request.getParameter("lblTRATGNO"),
                        request.getParameter("lblHOUSE"),
                        request.getParameter("lblQTY"),
                        request.getParameter("txtRecript"),
                        request.getParameter("txtReturn"),
                        request.getParameter("txtDescription"),
                        request.getParameter("type"),
                        request.getParameter("user")));
                out.flush();
                break;
            case "FWtheorder":
                out.print(Select.FWtheorder(
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("customer"),
                        request.getParameter("farmer"),
                        request.getParameter("number"),
                        request.getParameter("qty"),
                        request.getParameter("price"),
                        request.getParameter("amount"),
                        request.getParameter("reason"),
                        request.getParameter("user"),
                        request.getParameter("remaining"),
                        request.getParameter("FWTarget")));
                out.flush();
                break;
            case "CheckDataToSaveDoDClose":
                out.print(Select.CheckDataToSaveDoDClose(
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("DoD")));
                out.flush();
                break;
            case "CloseDoD":
                out.print(Select.CloseDoD(
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("lblID"),
                        request.getParameter("lbl160"),
                        request.getParameter("lbl080"),
                        request.getParameter("lbl110"),
                        request.getParameter("lbl120"),
                        request.getParameter("time"),
                        request.getParameter("user")));
                out.flush();
                break;
            case "changeStatusDoD":
                out.print(Select.changeStatusDoD(
                        request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("Supplier")));
                out.flush();
                break;
            case "getCustomer":
                out.print(Select.getCustomer());
                out.flush();
                break;
            case "getApprove":
                out.print(Select.getApprove(
                        request.getParameter("cono"),
                        request.getParameter("divi")));
                out.flush();
                break;
            case "getCustomerCreateDoDlist":
                out.print(Select.getCustomerCreateDoDlist(request.getParameter("cono"),
                         request.getParameter("divi")));
                out.flush();
                break;
            case "getCustomerPrintlist":
                out.print(Select.getCustomerPrintlist(request.getParameter("cono"),
                         request.getParameter("divi")));
                out.flush();
                break;
            case "getDateDoDlistPrint":
                out.print(Select.getDateDoDlistPrint(request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getDoDlistReport":
                out.print(Select.getDoDlistReport(request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getDoDlistPrintDate":
                out.print(Select.getDoDlistPrintDate(request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getCustomerMasterDetail":
                out.print(Select.getCustomerMasterDetail(request.getParameter("customer")));
                out.flush();
                break;
            case "getApproverMasterDetail":
                out.print(Select.getApproverMasterDetail(request.getParameter("cono"),
                        request.getParameter("divi"), request.getParameter("approver")));
                out.flush();
                break;
            case "getAutoFWdetail":
                out.print(Select.getAutoFWdetail(request.getParameter("approver"),
                        request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "dodCreateChange":
                out.print(Select.dodCreateChange(request.getParameter("cono"),
                        request.getParameter("divi"),
                        request.getParameter("DoD")));
                out.flush();
                break;
            case "getautoFWlist":
                out.print(Select.getautoFWlist(request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "importStartData":
                out.print(Insert.importStartData(request.getParameter("transtype"), request.getParameter("date"), request.getParameter("description"), request.getParameter("amount"), request.getParameter("total"), request.getParameter("howtoread"), request.getParameter("username"), request.getParameter("type"), request.getParameter("car"), request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "changeprintstatus":
                out.print(Select.changeprintstatus(request.getParameter("transnum"), request.getParameter("username1")));
                out.flush();
                break;
            case "approvemaster":
                out.print(Select.approvemaster(request.getParameter("user"), request.getParameter("number"), request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "changestatusifsameamount":
                out.print(Select.changestatusifsameamount(request.getParameter("car"), request.getParameter("type")));
                out.flush();
                break;
            case "rejecthistory":
                out.print(Select.rejecthistory(request.getParameter("company"), request.getParameter("username"), request.getParameter("status")));
                out.flush();
            case "cancelhistory":
                out.print(Update.cancelhistory(request.getParameter("transnum"), request.getParameter("username1")));
                out.flush();
            case "updateorder":
                out.print(Update.updateorder(request.getParameter("ordernum"),
                        request.getParameter("transtype"), request.getParameter("date"),
                        request.getParameter("description"), request.getParameter("amount"),
                        request.getParameter("total"), request.getParameter("howtoread"),
                        request.getParameter("username"), request.getParameter("type"),
                        request.getParameter("car"), request.getParameter("cono"),
                        request.getParameter("divi"), request.getParameter("supplier")));
                out.flush();
                break;
            case "getHistory":
                out.print(Select.getHistory(request.getParameter("type"), request.getParameter("car"), request.getParameter("supplier"), request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getLSandName":
                out.print(Select.getLSandName());
                out.flush();
                break;
            case "getTransHeader":
                out.print(Select.getTransHeader(request.getParameter("username1"), request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getcarMaster":
                out.print(Select.getcarMaster(request.getParameter("LS")));
                out.flush();
                break;
            case "updateMasterData":
                out.print(Update.updateMasterData(request.getParameter("description"), request.getParameter("startdate"), request.getParameter("total"), request.getParameter("car")));
                out.flush();
                break;
            case "getOrder":
                out.print(Select.getOrder(request.getParameter("order"), request.getParameter("cono"), request.getParameter("divi"), request.getParameter("status")));
                out.flush();
                break;

            case "CheckDuplicateSupInvoice":
                out.print(Select.CheckDuplicateSupInvoice(request.getParameter("sup"),
                        request.getParameter("inv"),
                        request.getParameter("srn")
                ));
                out.flush();
                break;
            case "getLS":
                out.print(Select.getLS(request.getParameter("type"), request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getTKtype":
                out.print(Select.getTKtype(request.getParameter("type")));
                out.flush();
                break;
            case "getDriverCar":
                out.print(Select.getDriverCar(request.getParameter("car"), request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getLeftAmount":
                out.print(Select.getLeftAmount(request.getParameter("car"), request.getParameter("convert"), request.getParameter("date"), request.getParameter("cono"), request.getParameter("divi")));
                out.flush();
                break;
            case "getHowtoread":
                out.print(Select.getHowtoread(request.getParameter("amount")));
                out.flush();
                break;
            case "ImportNewMaster":
                out.print(Select.ImportNewMaster());
                out.flush();
                break;
            case "GetNewTransactionNum":
                out.print(Insert.GetNewTransactionNum(request.getParameter("LS"),
                        request.getParameter("Car"),
                        request.getParameter("Type"),
                        request.getParameter("Amount"),
                        request.getParameter("date"),
                        request.getParameter("cono"),
                        request.getParameter("divi")));
                out.flush();
                break;

            case "getCarHistory":
                out.print(Select.getCarHistory(request.getParameter("supplier"),
                        request.getParameter("cono"),
                        request.getParameter("divi")));
                out.flush();
                break;
            case "getTransferdata":
                out.print(Select.getTransferdata(request.getParameter("cono"), request.getParameter("divi"), request.getParameter("invround")));
                out.flush();
                break;

            // Insert
            // Update
            // Delete
//            case "submitPO":
//                out.print();
//                out.flush();
//                break;
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
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Action.class.getName()).log(Level.SEVERE, null, ex);
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
