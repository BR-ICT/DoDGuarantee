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
    .red-text {
        color: red;
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
</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>
<link href="./assets/chosen/chosen.min.css" rel="stylesheet" />
<script src="./assets/chosen/chosen.jquery.min.js" type="text/javascript"></script>
<section class="container-w100">

    <!--<label for="txtPeriod">Transaction Page</label>-->

    <table  style="width:100%;"  class="no-border" >
        <tr  class="no-border" >
            <td  class="no-border"  style="width:65%;" >&nbsp;</td>
            <td  class="no-border" ></td>
            <td  class="no-border" > <label for="Hello">HELLO:</label>
                <label id="username" class="red-text"></label></td>
        </tr>
    </table>
    <div class="right-container">

    </div>

    <br>
    <!--------------------------------------------------------------------------------------------------------------------------------------->
    <div class="wrap-login100" style="width: 100%;margin-bottom: 0px;">
        <table style="width:50%"  class="no-border">
            <tr  class="no-border">
                <td  class="no-border"> 
                    <label for="vCustomer" id="vCustomerlab">ชื่อลูกค้า: </label>
                    <select  class=""  style="width: 350px" name="type"   onchange="dodchange()" id="customerselection" ></select>
                </td>
                <td  class="no-border"> 
                    <label for="vType" id="vType">ประเภทเงินมัดจำ :</label> 
                    <select  class=""  style="width: 100px" name="typechooser"  id="typechooser"  onchange="typechange()">
                        <!--<option value="">--เลือก--</option>-->
                        <option value="1" selected="">รับเงินมัดจำ</option>
                        <option value="2">คืนเงินมัดจำ</option>
                    </select>
                </td>

        </table>

    
    <div class="wrap-test" style="width: 100%;margin-bottom: 0px;" id="modetransfer">  
        <table style="width:75%"  class="double-border">
            <tr>
                <td style="width: 34%"><span  style="font-size: large;" >ชื่อลูกค้า : </span>
                    <span id="lblName" style="color:Blue;font-size: large"></span>
                </td >
                <td style="width: 33%"><span  style="font-size: large">เลขที่(NO.): </span>
                    <span id="lblTRATGNO" style="color:#F20000;font-size:Large;font-weight:bold;"></span>
                </td>
                <td style="width: 33%"></td>
            </tr>
            <tr>
                <td><span  style="font-size: large">ที่อยู่ :</span>
                    <span id="lblAddress" style="color:Blue;font-size: large"></span></td>
                <td><span  style="font-size: large">รหัสลูกค้า :</span>
                    <span id="lblSUNO" style="color:Blue;font-size: large"></span>
                </td>
                <td><span  style="font-size: large">เล้าที่ :</span>
                    <span id="lblHOUSE" style="color:Blue;font-size: large"></span>
                </td>

            </tr>
            <tr>
                <td><span  style="font-size: large">จำนวนลูกเป็ด :</span>
                    <span id="lblQTY" style="color:Blue;font-size: large"></span>
                </td>
                <td><span  style="font-size: large">จำนวนเงินมัดจำทั้งหมด :</span>
                    <span id="lblOwnGAMT" style="color:Blue;font-size: large"></span>
                </td>
                <td><span  style="font-size: large"> จำนวนเงินมัดจำสะสม :</span>
                    <span id="lblSUMTRAGAMT" style="color:Blue;font-size: large"></span>
                </td>
            </tr>
        </table>
        <table style="width:75%"  class="double-border">
            <tr>
                <th  style="text-align: center;" style="width: 40%">
                    <span style="font-size: large;" >
                        รายละเอียดการรับ/คืนเงินมัดจำ
                    </span></th>
                <th style="width: 30%">
                    <span style="font-size: large;" >
                        รับเงินมัดจำ
                    </span></th>
                <th style="width: 30%">
                    <span style="font-size: large;" >
                        คืนเงินมัดจำ
                    </span></th>
                <!--                <td>รับเงินมัดจำ</td>
                                <td>คืนเงินมัดจำ</td>-->
            </tr>
            <tr>
                <th> <input name="txtDescription" type="text" value="รับเงินมัดจำ DOD" id="txtDescription" style="color:Red;height:20px;width:325px;"> </th>
                <th> <input name="txtRecript" type="text" value="0.00" id="txtRecript" style="color:Red;width:150px;"> 
                    <!--<span id="lblRecript" style="color:#CC3300;font-size:Large;font-weight:bold;">0.00</span>-->
                </th>
                <th> <input name="txtReturn" type="text" value="0.00" id="txtReturn" style="color:Red;width:150px;">
                    <!--<span id="lblreturn" style="color:#CC3300;font-size:Large;font-weight:bold;">0.00</span>-->
                </th>
            </tr>
            <tr>
                <th><span id="Label13" style="font-size: large">จำนวน(AMOUNT) : </span>
                    <span id="lblAMT" style="color:Blue;font-size: large">ศูนย์บาทถ้วน</span></th>
                <th>บาท(BATH)</th>
                <th>บาท(BATH)</th>
            </tr>
            <tr>
                <th><span id="Label15" style="font-size: large">จัดทำโดย : </span>
                    <span id="lblsessionname" style="color:Blue;font-size: large"></span>
                </th>
                <th></th>
                <th></th>
            </tr>
        </table>
        <br>
        <button id="vSave" class="btn btn-danger" type="submit" onclick="SaveCreateDODorder()" form="" hidden="true">บันทึก </button>
        <button id="vCancel" class="btn btn-success" type="submit" form="" onclick="cleardetail()">ยกเลิก </button>
    </div>
        </div>
    <div id="jsGrid"></div>
</section>

<script>
    var cono = <%out.print(session.getAttribute("cono"));%>
    var divi = <%out.print(session.getAttribute("divi"));%>
    var auth = "<%out.print(session.getAttribute("auth"));%>";
    var chosentype = "<%out.print(session.getAttribute("type"));%>";
    var user = "<%out.print(session.getAttribute("user"));%>";
    getCustomerCreateDoDlist();
    $("#username").text(user);
    $("#lblsessionname").text(user);
    function getCustomerCreateDoDlist() {
//Get data of customer
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getCustomerCreateDoDlist",
                cono: cono,
                divi: divi
            },
            async: true
        }).done(function (response) {
            console.log(response);
            $('#customerselection').empty();
            $.each(response, function (i, obj) {
//                console.log(obj);
                var div_data = "<option value=" + obj.dodcode + ">" + obj.dodlist + "</option>";
                $(div_data).appendTo('#customerselection');
//                $(div_data).appendTo('#customerlist');
            });
            $('#customerselection').chosen();
//        document.getElementById('customerselection').change();
            dodchange();
        });
    }

    function dodchange() {
//        console.log("custoemrselection")
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "dodCreateChange",
                DoD: $("#customerselection").val(),
                cono: cono,
                divi: divi
            },
            async: false
        }).done(function (response) {
//            console.log("test");
            console.log(response);
            $.each(response, function (i, obj) {
//                $("#vID").val(obj.ID);
                $("#lblSUNO").text(obj.lblSUNO);
                $("#lblName").text(obj.lblName);
                $("#lblAddress").text(obj.lblAddress);
                $("#lblOwnGAMT").text(obj.lblOwnGAMT);
                $("#lblQTY").text(obj.lblQTY);
                $("#lblHOUSE").text(obj.lblHOUSE);
                $("#lblTRATGNO").text(obj.lblTRATGNO);
                $("#lblSUMTRAGAMT").text(obj.lblSUMTRAGAMT);
            });
        });
    }
    function typechange() {
        var typeno = $("#typechooser").val();
        $("#txtRecript").val('0.00');
//        $("#lblRecript").text('0.00');
        $("#txtReturn").val('0.00');
//        $("#lblreturn").text('0.00');
        $("#lblAMT").text('ศูนย์บาทถ้วน');

        if (typeno === "1") {
            $("#txtDescription").val("รับเงินมัดจำ DOD");
            $("#txtRecript").show();
//            $("#lblRecript").show();
//            $("#lblreturn").hide();
            $("#txtReturn").hide();
            $("#txtRecript").val("0.00");
//            $("#lblRecript").text("0.00");
        } else if (typeno === "2") {
            $("#txtDescription").val("คืนเงินมัดจำ DOD");
//            $("#lblRecript").hide();
            $("#txtRecript").hide();
//            $("#lblreturn").show();
            $("#txtReturn").show();
            $("#txtReturn").val("0.00");
//            $("#lblreturn").text("0.00");
//    'lblSUMTRAGAMT.Text = SumAMT
//            btnCreate.Visible = False
//            Dim mne As New MoneyExt()
        }
    }

//    $("#lblreturn").hide();
    $("#txtReturn").hide();

    $("#txtRecript").change(function () {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'text',
            data: {
                path: "getHowtoread",
                amount: $("#txtRecript").val()
            },
            async: false
        }).done(function (response) {
            console.log(response);
            $("#lblAMT").text(response);
        });
    });


    $("#txtReturn").change(function () {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'text',
            data: {
                path: "getHowtoread",
                amount: $("#txtReturn").val()
            },
            async: false
        }).done(function (response) {
            console.log(response);
            $("#lblAMT").text(response);
        });
    });

    function SaveCreateDODorder() {
        var lblSUNO = $("#lblSUNO").text();
        var lblTRATGNO = $("#lblTRATGNO").text();
        var lblHOUSE = $("#lblHOUSE").text();
        var lblQTY = $("#lblQTY").text();
        var type = $("#typechooser").val();
        var txtRecript = $("#txtRecript").val();
        var txtReturn = $("#txtReturn").val();
        if (type === "1" && txtRecript === "") {
            alert("โปรดใส่จำนวนรับเงินมัดจำ");
            return;
        } else if (type === "2" && txtReturn === "") {
            alert("โปรดใส่จำนวนคืนเงินมัดจำ");
            return;
        }
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'text',
            data: {
                path: "SaveCreateDODorder",
                cono: cono,
                divi: divi,
                customer: $("#customerselection").val(),
                lblSUNO: lblSUNO,
                lblTRATGNO: lblTRATGNO,
                lblHOUSE: lblHOUSE,
                lblQTY: lblQTY,
                txtRecript: txtRecript,
                txtReturn: txtReturn,
                txtDescription: $("#txtDescription").val(),
                type: type,
                user: user
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
//                getCustomerlist();
            }
        });
    }

    function cleardetail() {
        $("#typechooser").val("1");
        typechange();
    }

</script>  
