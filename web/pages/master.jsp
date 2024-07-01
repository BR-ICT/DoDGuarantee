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
         z-index: 10;
    }
    .wrap-formyellow {
        width: 100%;
        background: #fffccc;
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


    .hasDatepicker {
        width: 100px;
        text-align: center;
    }
    .ui-datepicker * {
        font-family: 'Helvetica Neue Light', 'Open Sans', Helvetica;
        font-size: 14px;
        font-weight: 300 !important;
    }

    .button-right {
        float: right;

    }
    .red-text {
        color: red;
    }
    .right-container {
        text-align: right;
    }
    .customerselection_chosen{
        z-index: 0;
    }
</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>
<link href="./assets/chosen/chosen.min.css" rel="stylesheet" />
<script src="./assets/chosen/chosen.jquery.min.js" type="text/javascript"></script>
<section class="container-w100">
    <div class="right-container">
        <label for="Hello">HELLO:</label>
        <label id="username" class="red-text"></label>
    </div>
    
    <!--<div class='exampleSearch'>-->
<!--    <select  placeholder="Choose some technologies..." id="search">
        <option value="">--เลือก--</option>
        <option value="1">test</option>
        <option value="2">อาม้า</option>
    </select>
    <select data-placeholder="Choose a country..." style="width:350px;" class="chosen-select">
         <option value="">--เลือก--</option>
        <option value="1">test</option>
        <option value="2">อาม่า</option>
         </select>-->
    <!--</div>-->
    <div class="wrap-login100" style="width: 100%;margin-bottom: 0px;">
        <!--<button id="vApprove" class="btn btn-success button-right" type="submit" form="" style="margin: 0px 0px 0px 5px;" >APPROVE</button>-->
        <button id="vbtnFW" class="btn btn-success button-right" type="submit" form="" style="margin: 0px 0px 0px 4px;">AUTO FW</button>
        
        <button id="vAdddataApprove" class="btn btn-success button-right" type="submit" form="" >Current mode:ADD DATA</button>

       
 <label for="vCustomer" id="vCustomerlab">Customer:</label>
        <select  class=""  style="width: 350px" name="type"  id="approverselection" onchange="approverchange();">    
        </select>
        <select  class=""  style="width: 350px" name="type"  id="AutoFWselection" onchange="autoFWchange();">    
        </select>
        
        <select  class=""  style="width: 350px" name="type"  id="customerselection" onchange="customerchange()">
        </select>
        <button id="vrefresh" class="btn btn-success" type="submit" form="" style="margin: 0px 0px 0px 4px;">Refresh</button>

    <div class="wrap-formyellow" style="width: 100%;margin-bottom: 0px;">
        <label for="OrderNo" id="OrderNolab">No.</label>
        <label id="OrderNo" name ="OrderNo"></label>
        <p id="linebreak1"></p>
        <label for="vID">ID:</label>
        <label id="vID" name ="vID" ></label>
        <br>
        <label for="vName">ชื่อ:</label>
        <label id="vName" name ="vName" ></label>
        <br>
        <label for="Address">ที่อยู่:</label>
        <label id="vAddress" name ="vAddress" ></label>
        <br>
        <label for="vFarmer">Farmer:</label>
        <input id="vFarmer" name="Farmer" style="margin: 0px 0px 0px 10px;width: 150px; text-align: left" disabled="">
        <br>
        <label for="vNo">House:</label>
        <input id="vNo" name="No" style="margin: 0px 0px 0px 16px;width: 150px; text-align: left" disabled="" maxlength="2">
        <br>
        <label for="vQTY" >DOD Quantity(PCS):</label>
        <input type="number" id="vQTY" name="QTY" style="margin: 0px 0px 0px 16px;width: 150px; text-align: right" disabled="" min="1" max="99" onchange="amtcalculate()">
        <br>
        <label for="vPrices">Unit Price(Baht):</label>
        <input type="number" id="vPrices" name="Price" style="margin: 0px 0px 0px 40px;width: 150px; text-align: right" disabled="" onchange="amtcalculate()" >
        <br>
        <label for="vAmount">Setting Amt(Baht):</label>
        <input id="vAmount" name="Amount" style="margin: 0px 0px 0px 24px;width: 150px; text-align: right" disabled="">
        <label id="vRemainlab" style="margin: 0px 0px 0px 24px;" >Remaining (Baht):</label>
        <input id="vRemain" name="Remain" style="width: 150px; text-align: right">
        <br>
        <label for="vReason">Reasons:</label>   
        <input id="vReason" name="Reason" value="เปิดเล้าใหม่" style="margin: 0px 0px 0px 16px;width: 200px; text-align: left" disabled="" maxlength="30">
        <br>
        <label for="vFWTarget" id="vFWTargetlab">Forward remaining amt to a new DOD quarantee number:</label>   
        <input id="vFWTarget" name="vFWTarget" style="width: 150px; text-align: left">
        <br>
        <button id="vApproveMaster" class="btn btn-success" type="submit" form="" style="margin: 0px 0px 0px 4px;">ยอมรับ</button>
         <button id="btnSave" class="btn btn-danger" type="submit" onclick="CheckDataToSave()" form="" hidden="true">บันทึก </button>
        <button id="vCancel" class="btn btn-success" type="submit" form="" >ยกเลิก </button>
    </div>
            </div>
       

    <!--<div id="jsGrid"></div>-->

</section>
<script>


            var cono = <%out.print(session.getAttribute("cono"));%>
            var divi = <%out.print(session.getAttribute("divi"));%>
            var auth = "<%out.print(session.getAttribute("auth"));%>";
            var chosentype = "<%out.print(session.getAttribute("type"));%>";
            var user = "<%out.print(session.getAttribute("user"));%>";
            var mode = "add";
            var customermode = "normal";
            var firstboot = false;
            var firstboot2 = false;

            $(document).ready(function () {
//                $('#customerselection').selectize();
//                $('#search').selectize();
//                $(".select").chosen(); 
                
            });

//btnadd_Click is add default mode





//Get detail of the customer    
//btnSearchAdd_Click Function
            function customerchange() {
//        console.log("custoemrselection")
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "getCustomerMasterDetail",
                        customer: $("#customerselection").val()
                    },
                    async: false
                }).done(function (response) {
//            console.log("test");
                    console.log(response);
                    $.each(response, function (i, obj) {
//                $("#vID").val(obj.ID);
                        $("#vID").text(obj.ID);
                        $("#vName").text(obj.Name);
                        $("#vAddress").text(obj.Address);
                        $("#Purpose").val(obj.Purpose);
                        $("#vFarmer").prop("disabled", false);
                        $("#vNo").prop("disabled", false);
                        $("#vQTY").prop("disabled", false);
                        $("#vPrices").prop("disabled", false);
                        $("#vReason").prop("disabled", false);
                        $("#vFarmer").val(obj.ID);
                        $("#vReason").val("เปิดเล้าใหม่");
                        ;
                    });
                });
            }

            function getCustomerlist() {
//Get data of customer
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "getCustomer"
                    },
                    async: true
                }).done(function (response) {
                    console.log(response);
                    $('#customerselection').empty();
                    $.each(response, function (i, obj) {
//                console.log(obj);
                        var div_data = "<option value=" + obj.customercode + ">" + obj.customerlist + "</option>";
                        $(div_data).appendTo('#customerselection');
                         
                        
//                        $(div_data).appendTo('#customerselection').chosen();
//                $(div_data).appendTo('#customerlist');
                    });
                     $("#customerselection").chosen("destroy");
                    $("#customerselection").chosen();
//        document.getElementById('customerselection').change();
                    customerchange();
                });
            }

            function amtcalculate() {
//        console.log("TEST");
                var QTY = $("#vQTY").val();
                var Prices = $("#vPrices").val();
                console.log(Prices);
                console.log(QTY);
                var amount;
                if (QTY !== "" && Prices !== "") {
                    amount = QTY * Prices;
                    console.log(amount);
                    $("#vAmount").val(amount);
                }
            }
            //btnSave_Click Function
            function CheckDataToSave() {
                if (customermode === "normal") {
                    var Farmer = $("#vFarmer").val();
                    if (Farmer === "") {
                        alert('กรุณาตรวจสอบข้อมูล Farmer');
                        return;
                    }
                    var Number = $("#vNo").val();
                    if (Number === "") {
                        alert('กรุณาตรวจสอบข้อมูล No.');
                        return;
                    }
                    var QTY = $("#vQTY").val();
                    if (QTY === "") {
                        alert('กรุณาตรวจสอบข้อมูล DOD Quantity (PCS) ');
                        return;
                    }
                    var Prices = $("#vPrices").val();
                    if (Prices === "") {
                        alert('คุณลืมเลือก Unit Price');
                        return;
                    }
                    if (QTY.length > 2) {
                        alert('DOD Quantity(PCS) ใช้ไม่เกิน 2 หลัก');
                        return;
                    }
                    savetheorder();
                } else if (customermode === "FW") {
                    var FWTarget = $("#vFWTarget").val;
                    if (FWTarget === "") {
                        alert('คุณลืมใส่หมายเลข DOD');
                        return;
                    }
                    var Farmer = $("#vFarmer").val();
                    if (Farmer === "") {
                        alert('กรุณาตรวจสอบข้อมูล Farmer');
                        return;
                    }
                    var Number = $("#vNo").val();
                    if (Number === "") {
                        alert('กรุณาตรวจสอบข้อมูล No.');
                        return;
                    }
                    var QTY = $("#vQTY").val();
                    if (QTY === "") {
                        alert('กรุณาตรวจสอบข้อมูล DOD Quantity (PCS) ');
                        return;
                    }
                    var Prices = $("#vPrices").val();
                    if (Prices === "") {
                        alert('คุณลืมเลือก Unit Price');
                        return;
                    }
                    if (QTY.length > 2) {
                        alert('DOD Quantity(PCS) ใช้ไม่เกิน 2 หลัก');
                        return;
                    }
                    FWtheorder();
                }

            }
            function savetheorder() {
//        console.log("custoemrselection")
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'text',
                    data: {
                        path: "savetheorder",
                        cono: cono,
                        divi: divi,
                        customer: $("#customerselection").val(),
                        farmer: $("#vFarmer").val(),
                        number: $("#vNo").val(),
                        qty: $("#vQTY").val(),
                        price: $("#vPrices").val(),
                        amount: $("#vAmount").val(),
                        user: user,
                        reason: $("#vReason").val()
                    },
                    async: false
                }).done(function (response) {
//            console.log("test");
//            console.log(response);
                    if (response === '') {
                        alert("เกิดข้อผิดพลาด");
                    } else {
                        alert(response);
                        cleardetail();
                        getCustomerlist();
                    }



                });
            }

            function FWtheorder() {
//        console.log("custoemrselection")
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'text',
                    data: {
                        path: "FWtheorder",
                        cono: cono,
                        divi: divi,
                        customer: $("#AutoFWselection").val(),
                        farmer: $("#vFarmer").val(),
                        number: $("#vNo").val(),
                        qty: $("#vQTY").val(),
                        price: $("#vPrices").val(),
                        amount: $("#vAmount").val(),
                        user: user,
                        reason: $("#vReason").val(),
                        remaining: $("#vRemain").val(),
                        FWTarget: $("#vFWTarget").val()
                    },
                    async: false
                }).done(function (response) {
                    if (response === '') {
                        alert("เกิดข้อผิดพลาด กรุณาที่รายการใหม่");
                    } else {
                        alert(response);
                        cleardetail();
                        getautofwlist();
                    }

                });
            }




            $("#vApproveMaster").click(function () {
                approve();
            });

            $("#vbtnFW").click(function () {
                customermodechange();
                autoFWchange();
            });



            function  customermodechange() {
                $("#AutoFWselection").chosen("destroy");
                $("#customerselection").chosen("destroy");
                if (customermode === "normal") {
                    $("#vbtnFW").hide();
                    $("#linebreak1").show();
                    $("#vCustomerlab").text("FARMER DOD GUARANTEE NUMBER : ");
                    $("#customerselection").hide();
                    $("#AutoFWselection").show();
                    $("#vRemainlab").show();
                    $("#vRemain").show();
                    $("#OrderNo").show();
                    $("#OrderNolab").show();
                    $("#vFWTargetlab").show();
                    $("#vFWTarget").show();
                    customermode = "FW";
                    $("#AutoFWselection").chosen();
                } else if (customermode === "FW") {
                     $("#AutoFWselection").hide();
                    $("#linebreak1").hide();
                    $("#vbtnFW").show();
                    $("#vCustomerlab").text("Customer:");
                    $("#customerselection").show();
                    $("#AutoFWselection").hide();
                    $("#vRemainlab").hide();
                    $("#vRemain").hide();
                    $("#OrderNo").hide();
                    $("#OrderNolab").hide();
                    $("#vFWTargetlab").hide();
                    $("#vFWTarget").hide();
                    customermode = "normal";
                      $("#customerselection").chosen();
                }
            }

            $("#vCancel").click(function () {
                customermode = "FW";
                customermodechange();
                mode = "approve";
                modechange();
            });


            $("#vrefresh").click(function () {
                if (mode === "add" && customermode === "normal") {
                    getCustomerlist();
                    $("#customerselection").chosen("destroy");
                    $("#customerselection").chosen();
                } else if (mode === "add" && customermode === "FW") {
                    getautofwlist();
                    $('#AutoFWselection').chosen("destroy");
                    $('#AutoFWselection').chosen();
                } else if (mode === "approve") {
                    getApproverlist();
                    $('#approverselection').chosen("destroy");
                    $('#approverselection').chosen();
//            cleardetail();

                }
            });




            function approve() {
//        console.log("custoemrselection")
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'text',
                    data: {
                        path: "approvemaster",
                        cono: cono,
                        divi: divi,
                        user: user,
                        number: $("#approverselection").val()
                    },
                    async: false
                }).done(function (response) {
//            console.log("test");
//            console.log(response);

                    alert(response);
                    cleardetail();
                    getApproverlist();
//            autofwchange();
//            document.getElementById("vrefresh").click();
                });
            }
            getCustomerlist();
            getApproverlist();
            getautofwlist();
            $("#username").text(user + "(" + auth + ")");



            function getApproverlist() {
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "getApprove",
                        cono: cono,
                        divi: divi
                    },
                    async: true
                }).done(function (response) {
                    console.log("getapprove");
                    console.log(response);
                    $('#approverselection').empty();
                    $.each(response, function (i, obj) {
//                console.log(obj);
                        var div_data = "<option value=" + obj.approvecode + ">" + obj.approvelist + "</option>";
                        $(div_data).appendTo('#approverselection');
//            $(div_data).appendTo('#customerlist');
                    });
//        document.getElementById('customerselection').change();
                    if (firstboot) {
//                console.log('FirstBoot');
                        approverchange();
//                firstboot = true;
                    } else {
                        firstboot = true;
                    }

                });
            }
            function cleardetail() {
                if (customermode === "normal") {
                    $("#vID").text("");
                    $("#vName").text("");
                    $("#vAddress").text("");
                    $("#Purpose").val("");
                    $("#vNo").val("");
                    $("#vQTY").val("");
                    $("#vPrices").val("");
                    $("#vAmount").val("");
                    $("#vFarmer").prop("disabled", true);
                    $("#vNo").prop("disabled", true);
                    $("#vQTY").prop("disabled", true);
                    $("#vPrices").prop("disabled", true);
                    $("#vReason").prop("disabled", true);
                    $("#vFarmer").val("");
                    $("#vReason").val("เปิดเล้าใหม่");
                } else if (customermode === "FW") {
                    $("#vFWTarget").val("");
                }

            }

            $("#vAdddataApprove").click(function () {
                customermode = "FW";
                customermodechange();
                modechange();
            });

            function modechange() {
                 $("#customerselection").chosen("destroy");
                 $("#approverselection").chosen("destroy");
                 
                if (mode === "add") {
//            console.log("approve");
                    mode = "approve";
                    $("#vAdddataApprove").text("Current mode:APPROVE");
                    $("#btnSave").hide();
//            $("#vCancel").show();
                    $("#customerselection").hide();
                    $("#approverselection").show();
                    $("#vCustomerlab").show();
                    $("#vbtnFW").hide();
                    $("#vApproveMaster").show();
                    $("#AutoFWselection").hide();
                    
                    cleardetail();
                    approverchange();
                    $("#approverselection").chosen();
                } else if (mode === "approve") {
                    console.log("add");
                    mode = "add";
                    $("#vAdddataApprove").text("Current mode:ADD DATA");
                    $("#btnSave").show();
//            $("#vCancel").show();
                    $("#approverselection").hide();
                    $("#customerselection").show();
                    $("#vCustomerlab").show();
                    $("#vbtnFW").show();
                    $("#vApproveMaster").hide();
                    
                    cleardetail();
                    customerchange();
                     $("#customerselection").chosen();
                }
            }





            function getautofwlist() {
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "getautoFWlist",
                        cono: cono,
                        divi: divi
                    },
                    async: true
                }).done(function (response) {
//            console.log("getapprove");
                    console.log(response);
                    $('#getautoFWlist').empty();
                    $.each(response, function (i, obj) {
//                console.log(obj);
                        var div_data = "<option value=" + obj.approvecode + ">" + obj.approvelist + "</option>";
                        $(div_data).appendTo('#AutoFWselection');
//            $(div_data).appendTo('#customerlist');
                    });
                    if (firstboot2) {
//                approverchange();
                    } else {
                        firstboot2 = true;
                    }

                })
            }
            ;



            function approverchange() {
//        console.log("custoemrselection")
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "getApproverMasterDetail",
                        approver: $("#approverselection").val(),
                        cono: cono,
                        divi: divi
                    },
                    async: false
                }).done(function (response) {
//            console.log("test");
                    console.log(response);
                    if (response.length === 0) {
                        $("#vApproveMaster").prop("disabled", true);
                    } else {
                        $("#vApproveMaster").prop("disabled", false);
                    }
                    $.each(response, function (i, obj) {
//              $("#vID").text(obj.ID);
                        $("#vID").text(obj.ID);
                        $("#vFarmer").val(obj.ID);
                        $("#vName").text(obj.Name);
                        $("#vAddress").text(obj.Address);
                        $("#vNo").val(obj.Number);
                        $("#vQTY").val(obj.QTY);
                        $("#vPrices").val(obj.Price);
                        $("#vAmount").val(obj.Amount);
                        $("#vReason").val(obj.Reason);
                        $("#vFarmer").prop("disabled", true);
                        $("#vNo").prop("disabled", true);
                        $("#vQTY").prop("disabled", true);
                        $("#vPrices").prop("disabled", true);
                        $("#vReason").prop("disabled", true);
//                $("#vFarmer").val(obj.ID);
//                $("#vReason").val("เปิดเล้าใหม่");
                        ;
                    });
//                    $("#approverselection").chosen();
                });
            }
            if (auth === "US") {
                $("#vApproveMaster").prop("disabled", true);
            }



            function autoFWchange() {
//        console.log("custoemrselection")
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        path: "getAutoFWdetail",
                        approver: $("#AutoFWselection").val(),
                        cono: cono,
                        divi: divi

                    },
                    async: false
                }).done(function (response) {
//            console.log("test");
                    console.log(response);
                    if (response.length === 0) {
                        $("#vApproveMaster").prop("disabled", true);
                    } else {
                        $("#vApproveMaster").prop("disabled", false);
                    }
                    $.each(response, function (i, obj) {
//              $("#vID").text(obj.ID);
                        $("#vID").text(obj.ID);
                        $("#OrderNo").text(obj.Ordernum);
                        $("#vName").text(obj.name);
                        $("#vAddress").val(obj.address);
                        $("#vFarmer").val(obj.farmer);
                        $("#vNo").val(obj.house);
//                $("#vNo").val(obj.Number);
                        $("#vQTY").val(obj.QTY);
                        $("#vPrices").val(obj.Price);
                        $("#vAmount").val(obj.AMT);
//                $("#vReason").val(obj.Reason);
                        $("#vRemain").val(obj.Remaining);
                        $("#vReason").val("Auto FW - From " + obj.Ordernum);

//                $("#vFarmer").prop("disabled", true);
//                $("#vNo").prop("disabled", true);
//                $("#vQTY").prop("disabled", true);
//                $("#vPrices").prop("disabled", true);
//                $("#vReason").prop("disabled", true);
//                $("#vFarmer").val(obj.ID);
//                $("#vReason").val("เปิดเล้าใหม่");
                        ;
                    });
                });
            }



//    $("#vSavebtnSave").hide();
//    $("#vCancel").hide();
//    $("#customerselection").hide();
//    $("#vCustomerlab").hide();
            $("#approverselection").hide();
            $("#vApproveMaster").hide();
            $("#AutoFWselection").hide();
            $("#vRemain").hide();
            $("#vRemainlab").hide();
            $("#OrderNo").hide();
            $("#OrderNolab").hide();
            $("#vFWTargetlab").hide();
            $("#vFWTarget").hide();
            $("#linebreak1").hide();

//    testbr
//    id = ""



</script>  
