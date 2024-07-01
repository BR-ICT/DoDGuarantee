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
</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>

<section class="container-w100">
    <!--<label for="txtPeriod">Transaction Page</label>-->



    <br>

    <!--<div class="wrap-login100" style="width: 100%;margin-bottom: 0px;">-->

    <!--        <table>
                <tr>
                <label for="vType" id="vType">เลขที่ใบมัดจำลูกเป็ด :</label> 
                <label for="vCustomer" id="vCustomerlab">Customer:</label>
                <select  class=""  style="width: 75px" name="typechooser"  id="typechooser" onchange="customerchange()">
                    <option value="">--เลือก--</option>
                    <option value="1">รับเงินมัดจำ</option>
                    <option value="2">คืนเงินมัดจำ</option>
                </select>
                </tr>
    
                <tr>
    
    
                </tr>
                <tr>
    
                </tr>
            </table>
            <label for="vDepositNo" id="vDepositNoLab">DOD DEPOSIT NUMBER : </label>
            <input id="vDepositNo" name="vDepositNo" style="width: 150px; text-align: left">
            <button id="vCreate" class="btn btn-success" type="submit" form="" >ค้นหา</button>-->
    <!--</div>-->
    <!--<div id="jsGrid"></div>-->
    <div class="wrap-test" style="width: 100%;margin-bottom: 0px;" id="modetransfer">
        <table  style="width:70%;"  class="no-border" >
            <tr  class="no-border" >
                <td  class="no-border" > <label class="red-text">Pls. Entry your parameter !</label></td>
                <td  class="no-border" ></td>
                <td  class="no-border" > 
                    <label for="Hello">HELLO:</label>
                    <label id="username" class="red-text"></label>
                </td>
            </tr>
            <tr  class="no-border" >
                <td  class="no-border"  style="width: 470px">
                    <label class="red-text">** </label>
                    <label>เลขที่ใบมัดจำลูกเป็ด : </label>
                </td>
                <!--<td  class="no-border"style="width: 350px"> <label style="text-align: left;">วันที่ทำรายการ :</label></td>-->
                <!--<td  class="no-border" style="width: 350px"><label> ประเภทเงินมัดจำ :  </label>-->
                </td>
            </tr>
            <tr  class="no-border" >
                <td  class="no-border" >
                    <select style="width: 450px" name="DDLTdNo"  id="DDLTdNo"></select>
                </td>
                <!--<td  class="no-border" >-->
                <!--<select style="width: 300px" name="DDLdate"  id="DDLdate"></select>-->
                <!--</td>-->
                <td  class="no-border" > 
                    <!--                    <select  class=""  style="width: 250px" name="typechooser"  id="typechooser" onchange="customerchange()">
                                            <option value="">--เลือก--</option>
                                            <option value="1">รับเงินมัดจำ</option>
                                            <option value="2">คืนเงินมัดจำ</option>
                                        </select>-->
                    <input type="submit" name="btnRunRP" value="RUN !!" id="btnRunRP" onclick="RunReport()" style="color:Blue;font-weight:bold;">
                </td>
            </tr>
        </table>


    </div>



</section>

<script>
    var cono = <%out.print(session.getAttribute("cono"));%>
    var divi = <%out.print(session.getAttribute("divi"));%>
    var auth = "<%out.print(session.getAttribute("auth"));%>";
    var chosentype = "<%out.print(session.getAttribute("type"));%>";
    var user = "<%out.print(session.getAttribute("user"));%>";
    $("#username").text(user);






    function RunReport() {
        console.log($("#DDLTdNo").val());
        var SelectedOrder = encodeURIComponent($("#DDLTdNo").val());
        var url = "Report?OrderNo=" + SelectedOrder + "&report=DODStatementM3" + "&cono=" + encodeURIComponent(cono) + "&divi=" + encodeURIComponent(divi);
        let myWindow = window.open(url, "_blank");
        var timer = setInterval(function () {
            if (myWindow.closed) {
                clearInterval(timer);
                // alert("closed");
                if (window.confirm(`Submit Confirm?`)) {
                    $.ajax({
                        url: './Action',
                        type: 'GET',
                        dataType: 'text',
                        data: {
                            path: "changeStatusDoD",
                            Supplier: $("#DDLTdNo").val(),
                            cono: cono,
                            divi: divi
                        },
                        async: false,
                        timeout: 60000
                    }).done(function (response) {
//                        var timer4 = setInterval(function () {
//                        var ordernum = $("#DDLTdNo").val();
//                            console.log(response);
                        alert(response);
//                        Reset();
//                        }, 1000);
                        getDoDlistPrint();
                    });
//                    console.log("GOT IT");
                }

            }
        }, 1000);
    }


    function getDoDlistPrint() {
//Get data of customer
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getCustomerPrintlist",
                cono: cono,
                divi: divi
            },
            async: true
        }).done(function (response) {
            console.log(response);
            $('#DDLTdNo').empty();
            $.each(response, function (i, obj) {
//                console.log(obj);
                var div_data = "<option value=" + obj.value + ">" + obj.listname + "</option>";
                $(div_data).appendTo('#DDLTdNo');
//                $(div_data).appendTo('#customerlist');
            });
//        document.getElementById('customerselection').change();

        });
    }



//    function getDateDoDlistPrint() {
////Get data of customer
//        $.ajax({
//            url: './Action',
//            type: 'GET',
//            dataType: 'json',
//            data: {
//                path: "getDateDoDlistPrint",
//                cono: cono,
//                divi: divi
//            },
//            async: true
//        }).done(function (response) {
//            console.log(response);
////            $('#customerselection').empty();
//            $.each(response, function (i, obj) {
////                console.log(obj);
//                var div_data = "<option value=" + obj.value + ">" + obj.listname + "</option>";
//                $(div_data).appendTo('#DDLdate');
////                $(div_data).appendTo('#customerlist');
//            });
////        document.getElementById('customerselection').change();
////            customerchange();
//        });
//    }
    getDoDlistPrint();
//    getDateDoDlistPrint();
</script>  
