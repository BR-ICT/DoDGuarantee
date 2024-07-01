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
    .red-text {
        color: red;
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
</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>

<section class="container-w100">
    <!--<label for="txtPeriod">Transaction Page</label>-->

    <div class="right-container">
        <label for="Hello">HELLO:</label>
        <label id="username" class="red-text"></label>
    </div>

    <br>

    <div class="wrap-login100" style="width: 100%;margin-bottom: 0px;">
        <label for="vDepositNo" id="vDepositNoLab">DOD DEPOSIT NUMBER : </label>
        <input id="vDepositNo" name="vDepositNo" style="width: 150px; text-align: left">
        <button id="vCreate" class="btn btn-success" type="submit" form="" onclick="CheckDataToSave()">ค้นหา</button>
    </div>
    <div id="jsGrid"></div>
    <div class="wrap-test" style="width: 100%;margin-bottom: 0px;" id="modetransfer">  

        <br>
        <button id="vSave" class="btn btn-success" type="submit" onclick="CloseDoD()" form="" hidden="true">ปิด DOD </button>
        <button id="vCancel" class="btn btn-danger" type="submit" form="" onclick="clearpage()">ยกเลิก </button>
    </div>

</section>

<script>
    var cono = <%out.print(session.getAttribute("cono"));%>
    var divi = <%out.print(session.getAttribute("divi"));%>
    var auth = "<%out.print(session.getAttribute("auth"));%>";
    var chosentype = "<%out.print(session.getAttribute("type"));%>";
    var user = "<%out.print(session.getAttribute("user"));%>";


    $("#jsGrid").jsGrid({
        width: "100%",
        height: "auto",
        editing: false,
        sorting: true,
        paging: true,
        filtering: true,
        pageSize: 30,
        deleteConfirm: "Do you really want to delete the client?",
        heading: true,
        inserting: false,
        selecting: false,
        pageLoading: false,

        controller: {
            loadData: function (filter) {
                console.log(filter);
                var data = $.Deferred();
                $.ajax({
                    type: 'GET',
                    url: './Action',
                    dataType: 'json',
                    data: {
//                        completed: $("#vStatus").val(),
//                        type: $("#SearchvTratypes").val(),
                        cono: cono,
                        divi: divi,
                        path: "CloseDoDTable",
                        DoDno: $("#vDepositNo").val()
                    },
                    async: false,
                    timeout: 60000
                }).done(function (response) {
                    console.log(response);
                    response = $.grep(response, function (item) {
                        return(!filter.RCUSTOMER || (item.RCUSTOMER.indexOf(filter.RCUSTOMER) > -1))
                                && (!filter.RDUCKCODE || (item.RDUCKCODE.indexOf(filter.RDUCKCODE) > -1))
                                && (!filter.RDETAIL || (item.RDETAIL.indexOf(filter.RDETAIL) > -1))
                                && (!filter.RAMTBAHT || (item.RAMTBAHT.indexOf(filter.RAMTBAHT) > -1))
                                && (!filter.RSTATUS || (item.RSTATUS.indexOf(filter.RSTATUS) > -1))
                                && (!filter.RDATE || (item.RDATE.indexOf(filter.RDATE) > -1))
                                && (!filter.RUSAGE || (item.RUSAGE.indexOf(filter.RUSAGE) > -1))
//                                && (!filter.RCHOOSE || (item.RCHOOSE));
                        console.log(data.resolve(response));

                    });
                    data.resolve(response);

                    console.log(response);
                    console.log("response");
                });
                return data.promise();
            },

            insertItem: function (item) {
            },
            updateItem: function (item) {
            },
            deleteItem: function (item) {
//                console.log(item);
//                formData = {};
//                formData.company = item.RCOMPANY;
//                formData.customerid = item.RCUSTOMERID;
//                formData.path = "deleteFinanceMaster";
//                $.ajax({
//                    url: './Action',
//                    type: 'POST',
//                    dataType: 'json',
//                    data: formData,
//                    async: false
//                });
//                $("#jsGrid").jsGrid("loadData");
            }


        },
        fields: [
            {title: "รหัสลูกค้า", name: "RCUSTOMER", css: "limitext", type: "text", editing: false, align: "center", width: 50},
            {title: "รหัสประกันลูกเป็ด", name: "RDUCKCODE", css: "limitext", type: "text", editing: false, align: "center", width: 50},
            {title: "รายละเอียด", name: "RDETAIL", css: "limitext", type: "text", editing: false, align: "center", width: 50},
            {title: "จำนวน(บาท)", name: "RAMTBAHT", css: "limitext", type: "text", editing: false, align: "center", width: 50},
            {title: "สถานะ", name: "RSTATUS", css: "limitext", type: "text", editing: false, align: "center", width: 20},
            {title: "วันที่", name: "RDATE", css: "limitext", type: "text", editing: false, align: "center", width: 50},
            {title: "การใช้งาน", name: "RUSAGE", css: "limitext", type: "text", editing: false, align: "center", width: 50},
            {title: "เลือก", name: "RCHOOSE",
//                headerTemplate: function() {
//                    return $("<input>").attr("type", "checkbox").on("change", function () {
//                        var isChecked = $(this).is(":checked");
//                        $("#jsGrid").jsGrid("option", "data").forEach(function (item) {
//                            item.Selected = isChecked;
//                        });
//                        $(".jsgrid-checkbox").prop("checked", isChecked);
//                    });
//                },
                itemTemplate: function (_, item) {
                    return $("<input>").attr("type", "checkbox")
                            .addClass("jsgrid-checkbox")
                            .prop("checked", item.Selected)
                            .on("change", function () {
                                item.Selected = $(this).is(":checked");
                            });
                },
                align: "center",
                width: 30
            }
//            {title: "เวลา", name: "RTIME", css: "limitext", type: "text", editing: false, align: "center", sorting: false, width: 50}
        ]

    });

    function CheckDataToSave() {
//        console.log("custoemrselection")
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'text',
            data: {
                path: "CheckDataToSaveDoDClose",
                cono: cono,
                divi: divi,
                DoD: $("#vDepositNo").val()
            },
            async: false
        }).done(function (response) {
//            console.log("test");
//            console.log(response);
            if (response === '') {
                alert("เกิดข้อผิดพลาด");
                return;
            } else if (response === 'สำเร็จ') {
                $("#jsGrid").jsGrid("loadData");
            } else {
                alert(response);
                return;
            }
        });
    }



    function clearpage() {
        $("#vDepositNo").val('');
//        console.log("TEST");
        clearTable();
    }


    function CloseDoD() {
        var jsGridData = $("#jsGrid").jsGrid("option", "data");
//        console.log(jsGridData[0].Selected);
        for (var i = 0; i < jsGridData.length; i++) {
            var rowslected = jsGridData[i].Selected;
            if (rowslected) {
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'text',
                    data: {
                        path: "CloseDoD",
                        cono: cono,
                        divi: divi,
                        lblID: jsGridData[i].RDUCKCODE,
                        lbl160: jsGridData[i].RDATE,
                        lbl080: jsGridData[i].RAMTBAHT,
                        lbl110: jsGridData[i].RSTATUS,
                        lbl120: jsGridData[i].RDETAIL,
                        time: jsGridData[i].RTIME,
                        user: user
                    },
                    async: false
                }).done(function (response) {
//            console.log("test");
//            console.log(response);
                    if (response === '') {
                        alert("เกิดข้อผิดพลาด");
                        return;
                    } else {
                        alert(response);
                    }
                });
            }
        }
    }


    function clearTable() {
        var jsGridInstance = $("#jsGrid").jsGrid({});
        jsGridInstance.jsGrid("option", "data", []);
        jsGridInstance.jsGrid("refresh");
    }

    $("#username").text(user);

</script>  
