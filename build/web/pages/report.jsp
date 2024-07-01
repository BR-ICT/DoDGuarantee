<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    if (session.getAttribute("cono") == null) {
//        response.sendRedirect("login.jsp");
    }
%>

<style>

    td.limitext{
        white-space: nowrap;
        width: 100px;
        overflow: hidden;
        text-overflow:ellipsis;
        /*text-overflow: ellipsis !important;*/
    }

    button {
        outline: none !important;
        border: hidden;
        background: springgreen;
    }

    .ui-widget *, .ui-widget input, .ui-widget select, .ui-widget button {
        font-family: 'Helvetica Neue Light', 'Open Sans', Helvetica;
        font-size: 14px;
        font-weight: 300 !important;
    }

    .details-form-field input,
    .details-form-field select {
        width: 250px;
        float: right;
    }
    table, th, td {
        border:1px solid black;
    }

    .details-form-field {
        margin: 25px 0;
    }

    .details-form-field:first-child {
        margin-top: 10px;
    }

    .details-form-field:last-child {
        margin-bottom: 10px;
    }

    .details-form-field button {
        display: block;
        width: 100px;
        margin: 0 auto;
    }

    input.error, select.error {
        border: 1px solid #ff9999;
        background: #ffeeee;
    }

    label.error {
        float: right;
        margin-left: 100px;
        font-size: .8em;
        color: #ff6666;
    }

    .form-control{
        display:block;
        width:100%;
        height:27px;
        padding:2px 0px;
        font-size:14px;
        line-height:1.42857143;
        color:#555;
        background-color:#fff;
        background-image:none;
        border:1px solid #ccc;
        border-radius:4px;
        -webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);
        box-shadow:inset 0 1px 1px rgba(0,0,0,.075);
        -webkit-transition:border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
        -o-transition:border-color ease-in-out .15s,box-shadow ease-in-out .15s;
        transition:border-color ease-in-out .15s,box-shadow ease-in-out .15s

    }

    .form-control:focus{
        border-color:#66afe9;
        outline:0;
        -webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
        box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
    }

    .form-control::-moz-placeholder{
        color:#999;
        opacity:1
    }

    .form-control:-ms-input-placeholder{
        color:#999
    }

    .form-control::-webkit-input-placeholder{
        color:#999
    }

    .form-control::-ms-expand{
        background-color:transparent;
        border:0
    }

    .form-control[disabled],.form-control[readonly],fieldset[disabled] .form-control{
        background-color:#eee;
        opacity:1
    }

    .form-control[disabled],fieldset[disabled] .form-control{
        cursor:not-allowed
    }

    td.limitext{
        white-space: nowrap;
        width: 100px;
        overflow: hidden;
        text-overflow:ellipsis;
        /*text-overflow: ellipsis !important;*/
    }

    .wrap-test {
        width: 100%;
        background: white;
        border-radius: 2px;
        overflow: hidden;
        padding: 10px 10px 10px 10px;

        box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -moz-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -webkit-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -o-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -ms-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
    }

    .wrap-login100 {
        width: 100%;
        background: pink;
        border-radius: 2px;
        overflow: hidden;
        padding: 10px 10px 10px 10px;

        box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -moz-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -webkit-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -o-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
        -ms-box-shadow: 0 5px 10px 0px rgba(0, 0, 0, 0.1);
    }

    td.lvgb{
        background-color: #bdd4ff !important;
    }

    td.lvgb-yello{
        background-color: #fff3bd !important;
    }

    td.lvgb-green{
        background-color: #72fc9e !important;
    }

    .container-w100{
        width: 100%;
        padding-left: 10px;
        padding-right: 10px;

    }

    .jsgrid-table {
        border-collapse: separate;
    }

    .jsgrid-grid-body td, .jsgrid-grid-header td, .jsgrid-grid-header th {
        border-left: 0;
        border-top: 0;
    }
    .switch {
        position: relative;
        display: inline-block;
        width: 60px;
        height: 34px;
    }

    .switch input {
        opacity: 0;
        width: 0;
        height: 0;
    }

    .slider {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        -webkit-transition: .4s;
        transition: .4s;
    }

    .slider:before {
        position: absolute;
        content: "";
        height: 26px;
        width: 26px;
        left: 4px;
        bottom: 4px;
        background-color: white;
        -webkit-transition: .4s;
        transition: .4s;
    }

    input:checked + .slider {
        background-color: #2196F3;
    }

    input:focus + .slider {
        box-shadow: 0 0 1px #2196F3;
    }

    input:checked + .slider:before {
        -webkit-transform: translateX(26px);
        -ms-transform: translateX(26px);
        transform: translateX(26px);
    }

    /* Rounded sliders */
    .slider.round {
        border-radius: 34px;
    }

    .slider.round:before {
        border-radius: 100%;
    }

    .hasDatepicker {
        width: 100px;
        text-align: center;
    }
    .ui-datepicker * {
        font-family: 'Helvetica Neue Light', 'Open Sans', Helvetica;
        font-size: 14px;
        font-weight: 300 !important;
    }
    .right-container {
        text-align: right;
    }
    /* Solid border */
    .solid-border {
        border: 2px solid black;
    }

    /* Dotted border */
    .dotted-border {
        border: 2px dotted black;
    }

    /* Dashed border */
    .dashed-border {
        border: 2px dashed black;
    }

    /* Double border */
    .double-border {
        border: 3px double black;
    }

    /* Groove border */
    .groove-border {
        border: 4px groove black;
    }

    /* Ridge border */
    .ridge-border {
        border: 4px ridge black;
    }

    /* Inset border */
    .inset-border {
        border: 4px inset black;
    }

    /* Outset border */
    .outset-border {
        border: 4px outset black;
    }
    .no-border {
        border: none;
        border-collapse: collapse;
    }
    .red-text {
        color: red;
    }
    .chosen-container.chosen-with-drop .chosen-drop {
    position: relative;
}
</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>
<link href="./assets/chosen/chosen.min.css" rel="stylesheet" />
<script src="./assets/chosen/chosen.jquery.min.js" type="text/javascript"></script>
<section class="container-w100">
    <!--<label for="txtPeriod">Transaction Page</label>-->



    <br>
    <div class="wrap-test" style="width: 100%;margin-bottom: 0px;" id="modetransfer">
        <table  style="width:70%;"  class="no-border" >
            <tr  class="no-border" >
                <td  class="no-border" > <label class="red-text">Pls. Entry your parameter !</label></td>
                <td  class="no-border" ></td>
                <td  class="no-border" > <label for="Hello">HELLO:</label>
                    <label id="username" class="red-text"></label></td>
            </tr>
            <tr  class="no-border" >
                <td  class="no-border"  >
                    <label class="red-text">** </label>
                    <label>เลขที่ใบมัดจำลูกเป็ด : </label>
                </td>   
                <td  class="no-border"> <label style="text-align: left;"></label></td>

            </tr>
            <tr  class="no-border" >
                <td  class="no-border" style="width: 390px" >
                    <select name="DDLReport" id="DDLReport" onchange="ReportTypeChange()" style="width:300px;margin-right: 5px;margin-bottom: 5px;">
                        <!--<option value="">- เลือก -</option>-->
                        <option selected="selected" value="DodReport_M3t">DOD Guarantee By Supplier</option>
                        <option value="DodReportALL_M3t">DOD Guarantee By Date</option>
                        <option value="DODReportsumdod">DOD Guarantee Receive and Return</option>
                    </select>
                    <!--<input type="submit" name="btnsearchmenu" value="ค้นหา" id="btnsearchmenu" style="color:Blue;font-weight:bold;width:58px;">-->
                </td>
                <td  class="no-border" >
                </td>
            </tr>
            <tr  class="no-border" >
                <td  class="no-border" style="width: 500px" >
                    <label id="DDLTdNolab"> Supplier : </label>
                    <select style="width: 400px" name="DDLTdNo"  id="DDLTdNo"></select>
                </td>
                <td  class="no-border" >

                </td>

            </tr>
            <tr  class="no-border" >
                <td  class="no-border" style="width: 310px">
                    <label> Date :  </label>
                    <input id="vStartDateReport" name="vStartDateReport"  "width: 125px">
                    <!--<input style="width: 125px">-->
                    <label> - </label>
                    <input id="vEndDateReport" name="vEndDateReport"  "width: 125px">
                    <!--<input style="width: 125px">-->
                    <input type="submit" name="btnRunRP" value="RUN !!" id="btnRunRP" style="color:Blue;font-weight:bold;" onclick="reportcall()" >
                </td>
                <td  class="no-border" >

                </td>

            </tr>
        </table>
        <section id="reportreport">
            <div class='centerDiv' id="vSection"></div>
        </section>

    </div>



</section>

<script>
    var cono = <%out.print(session.getAttribute("cono"));%>
    var divi = <%out.print(session.getAttribute("divi"));%>
    var auth = "<%out.print(session.getAttribute("auth"));%>";
    var chosentype = "<%out.print(session.getAttribute("type"));%>";
    var user = "<%out.print(session.getAttribute("user"));%>";
    $("#username").text(user);

    function reportcall() {
        if ($("#DDLReport").val() === "DodReport_M3t") {
            var supplier = encodeURIComponent($("#DDLTdNo").val());
            var FromDate = encodeURIComponent($("#vStartDateReport").val());
            var ToDate = encodeURIComponent($("#vEndDateReport").val());
            var url = "Report?supplier=" + supplier + "&FromDate=" + FromDate + "&ToDate=" + ToDate + "&report=DodReport_M3t";
            let myWindow = window.open(
                    url,
                    "_blank"
                    );
        } else if ($("#DDLReport").val() === "DodReportALL_M3t") {
            var FromDate = encodeURIComponent($("#vStartDateReport").val());
            var ToDate = encodeURIComponent($("#vEndDateReport").val());
            var url = "Report?FromDate=" + FromDate + "&ToDate=" + ToDate + "&report=DodReportALL_M3t";
            let myWindow = window.open(
                    url,
                    "_blank"
                    );
        } else if ($("#DDLReport").val() === "DODReportsumdod") {
            var supplier = encodeURIComponent($("#DDLTdNo").val());
            var FromDate = encodeURIComponent($("#vStartDateReport").val());
            var ToDate = encodeURIComponent($("#vEndDateReport").val());
            var url = "Report?FromDate=" + FromDate + "&ToDate=" + ToDate + "&report=DODReportsumdod";
            let myWindow = window.open(
                    url,
                    "_blank"
                    );
        }
    }
    ;

    function getDoDlistPrint() {
//Get data of customer
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getDoDlistReport",
                cono: cono,
                divi: divi
            },
            async: true
        }).done(function (response) {
            console.log(response);
//            $('#customerselection').empty();
            $.each(response, function (i, obj) {
                var div_data = "<option value=" + obj.DoDCode + ">" + obj.DoDlist + "</option>";
                $(div_data).appendTo('#DDLTdNo');
            });
            $('#DDLTdNo').chosen();
        });
    }
    function ReportTypeChange() {
        $('#DDLTdNo').chosen("destroy");
        var reporttype = $("#DDLReport").val();
//        console.log(reporttype);
        if (reporttype === "DodReport_M3t") {
            $("#DDLTdNolab").show();
            $("#DDLTdNo").show();
            $('#DDLTdNo').chosen();
        } else {
            $("#DDLTdNolab").hide();
            $("#DDLTdNo").hide();
        }
    }
    getDoDlistPrint();
    $("#vStartDateReport").datepicker({dateFormat: 'dd-mm-yy'}).datepicker("setDate", new Date($.now()));
    $("#vEndDateReport").datepicker({dateFormat: 'dd-mm-yy'}).datepicker("setDate", new Date($.now()));
</script>  
