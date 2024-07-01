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
        background: orchid;
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

</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>

<section class="container-w100">

    <label for="txtPeriod">History</label>
    <div class="wrap-login100" style="width: 100%;margin-bottom: 0px;">


        <label for="txtPeriod">ประเภท</label>
        <select style="width: 75px" name="invoiceround"  id="vTransfertype">
            <option value="1" selected="selected">รับเงิน</option>
            <option value="2">คืนเงิน</option>
        </select>
        <label for="txtPeriod">TYPE</label>
        <select style="width: 150px" name="invoiceround"  id="vTratype">
            <option value="1" >รับเงินคืนประกัน L/D</option>
            <option value="2">รับเงินคืนประกัน FEED</option>
            <option value="3" selected="selected">รับเงินคืนประกัน DUCK</option>
        </select>
        <!--        <label for="txtPeriod">Supplier</label>
                <select style="width: 200px" name="supplierchooser"  id="vSupplier">
                    <option value="" selected="selected">Invoice Round!</option>
                </select>-->

        <input id="vSupplier" name="supplierchooser" list="supplierlist" style="width: 200px;">
        <datalist id="supplierlist">
            <option value="">Select supplier!</option>
            <option value="">testtest</option>
        </datalist>


        <label for="txtPeriod">Car:</label>
        <input id="vCar" name="Car" list="carlist" style="width: 150px;">
        <datalist id="carlist">
            <option value="">Select car</option>
        </datalist>
      

        <button id="vSearch" class="btn btn-success" type="submit" form="">Search</button> 
       
    </div>
    <div id="jsGrid"></div>
</section>

<script>
    var cono = <%out.print(session.getAttribute("cono"));%>
    var divi = <%out.print(session.getAttribute("divi"));%>
    var chosentype = "<%out.print(session.getAttribute("type"));%>";




    var NumberField = jsGrid.NumberField;

    function DecimalField(config) {
        NumberField.call(this, config);
    }

    DecimalField.prototype = new NumberField({

        step: 0.01,

        filterValue: function () {
            return this.filterControl.val() ? parseFloat(this.filterControl.val()) : undefined;
        },

        insertValue: function () {
            return this.insertControl.val() ? parseFloat(this.insertControl.val()) : undefined;
        },

        editValue: function () {
            return this.editControl.val() ? parseFloat(this.editControl.val()) : undefined;
        },

        _createTextBox: function () {
            return NumberField.prototype._createTextBox.call(this)
                    .attr("step", this.step);
        }
    });

    jsGrid.fields.decimal = jsGrid.DecimalField = DecimalField;


    $("#jsGrid").jsGrid({
        width: "100%",
        height: "auto",
        editing: false,
        sorting: true,
        paging: true,
        filtering: true,
        pageSize: 25,
        deleteConfirm: "Reject this order?",
        heading: true,
        inserting: false,
        selecting: false,
        pageLoading: false,

        onRefreshed: function (args) {
            var items = args.grid.option("data");
            var total = {TOTAL: "Total", "RENDDATE": 0, IsTotal: true};
            var qty = 0;

            items.forEach(function (item) {
                qty += parseFloat(item.RENDDATE);
            });

            total.RENDDATE = parseFloat(qty).toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');

            console.log(total);
            args.grid._content.append("<tr><td colspan=\"1\" class=\"jsgrid-cell jsgrid-align-right\" style=\"text-align: center\"><strong>Total</strong></td>"
                    + "<td colspan=\"1\" class=\"jsgrid-cell jsgrid-align-right\"><strong></strong></td>"
                    + "<td colspan=\"4\" class=\"jsgrid-cell jsgrid-align-right\"><strong></strong></td>"
                    + "<td colspan=\"1\" class=\"jsgrid-cell jsgrid-align-right\"><strong>" + qty.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,') + "</strong></td>"
                    + "<td colspan=\"7\" class=\"jsgrid-cell jsgrid-align-right\"><strong></strong></td>"

//                    + "<td colspan=\"2\" class=\"jsgrid-cell jsgrid-align-right\"><strong></strong></td>"
//                    + "<td colspan=\"2\" class=\"jsgrid-cell jsgrid-align-right\"><strong></strong></td>"
//                    + "<td colspan=\"1\" class=\"jsgrid-cell jsgrid-align-right\"><strong>" + net_qty.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,') + "</strong></td>"
//                    + "<td colspan=\"1\" class=\"jsgrid-cell jsgrid-align-right\"><strong>" + net_kgs.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,') + "</strong></td>"

                    + "</td>");

        },
        controller: {
            loadData: function (filter) {
                console.log(filter);
                var data = $.Deferred();
                $.ajax({
                    type: 'GET',
                    url: './Action',
                    dataType: 'json',
                    data: {
                        path: "getHistory",
                        type: $("#vTransfertype").val(),
                        car: $("#vCar").val(),
                        supplier: $("#vSupplier").val(),
                        cono:cono,
                        divi:divi
                    },
                    async: false,
                    timeout: 60000
                }).done(function (response) {
                    console.log("response: " + response);
                    response = $.grep(response, function (item) {
                        return(!filter.RCOMPANY || (item.RCOMPANY.indexOf(filter.RCOMPANY) > -1))
                                && (!filter.RCUSTOMERID || (item.RCUSTOMERID.indexOf(filter.RCUSTOMERID) > -1))
                                && (!filter.RCUSTOMERNAME || (item.RCUSTOMERNAME.indexOf(filter.RCUSTOMERNAME) > -1))
                                && (!filter.RSTARTDATE || (item.RSTARTDATE.indexOf(filter.RSTARTDATE) > -1))
                                && (!filter.RDESCRIPTION || (item.RDESCRIPTION.indexOf(filter.RDESCRIPTION) > -1))
                                && (!filter.RENDDATE || (item.RENDDATE.indexOf(filter.RENDDATE) > -1))
                                && (!filter.RBILLDATE || (item.RBILLDATE.indexOf(filter.RBILLDATE) > -1))
                                && (!filter.RPAYDATE || (item.RPAYDATE.indexOf(filter.RPAYDATE) > -1))
                                && (!filter.RROUND || (item.RROUND.indexOf(filter.RROUND) > -1))
                                && (!filter.RSTATUS || (item.RSTATUS.indexOf(filter.RSTATUS) > -1))
                                && (!filter.RSTATUS2 || (item.RSTATUS2.indexOf(filter.RSTATUS2) > -1))
                                && (!filter.RSTATUS3 || (item.RSTATUS3.indexOf(filter.RSTATUS3) > -1))
                                && (!filter.RCAR || (item.RCAR.indexOf(filter.RCAR) > -1))
                                && (!filter.RCUSER || (item.RCUSER.indexOf(filter.RCUSER) > -1))
                                && (!filter.RVOUCHER || (item.RVOUCHER.indexOf(filter.RVOUCHER) > -1))
                                && (!filter.RCDATE || (item.RCDATE.indexOf(filter.RCDATE) > -1))
                                && (!filter.RCTIME || (item.RCDATE.indexOf(filter.RCTIME) > -1))
                                && (!filter.RCARNAME || (item.RCARNAME.indexOf(filter.RCARNAME) > -1))
                                && (!filter.RSUPPLIERNAME || (item.RSUPPLIERNAME.indexOf(filter.RSUPPLIERNAME) > -1));
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
//                alert(item.RDTOTA_KGS);
                console.log(item);
                formData = {};
                formData.company = item.RCOMPANY;
                formData.customerid = item.RCUSTOMERID;
                formData.billdate = item.RBILLDATE;
                formData.paydate = item.RPAYDATE;
                formData.startdate = item.RSTARTDATE;
                formData.enddate = item.RENDDATE;
                formData.path = "updateHeaderFinance";
                $.ajax({
                    url: './Action',
                    type: 'POST',
                    dataType: 'json',
                    data: formData,
                    async: false
                });
                $("#jsGrid").jsGrid("loadData");
            },
            deleteItem: function (item) {
                console.log(item);
                formData = {};
                formData.company = item.RCUSTOMERID;
                formData.username = "<%out.print(session.getAttribute("user"));%>";
                formData.status = item.RSTATUS3;
                formData.path = "rejecthistory";
                $.ajax({
                    url: './Action',
                    type: 'GET',
                    dataType: 'text',
                    data: formData,
                    async: false
                }).done(function (response) {
                    alert(response);
                    console.log(response);
                });
                $("#jsGrid").jsGrid("loadData");
            }

        },
        fields: [
            {type: "control", width: 50
                , itemTemplate: function (_, item) {
                    if (item.IsTotal)
                        return "";
                    return jsGrid.fields.control.prototype.itemTemplate.apply(this, arguments);
                }},
            {title: "ประเภท", name: "RCOMPANY", css: "limitext", type: "text", editing: false, align: "center", width: 50},
            {title: "ลำดับ", name: "RCUSTOMERID", css: "limitext", type: "text", editing: false, align: "left", width: 75},
            {title: "วันที่", name: "RCUSTOMERNAME", css: "limitext", type: "text", editing: false, align: "left", width: 100},
            {title: "รหัสSupplier", name: "RSTARTDATE", css: "limitext", type: "text", editing: false, align: "right", width: 75},
            {title: "ชื่อ Supplier", name: "RSUPPLIERNAME", css: "limitext", type: "text", editing: false, align: "right", width: 200},
            {title: "รถ", name: "RCAR", css: "limitext", type: "text", editing: false, align: "right", width: 100},
            {title: "ชื่อ คนขับรถ", name: "RCARNAME", css: "limitext", type: "text", editing: false, align: "right", width: 300},
            {title: "คำอธิบาย", name: "RDESCRIPTION", css: "limitext", type: "text", editing: false, align: "right", width: 150},
            {title: "จำนวน", name: "RENDDATE", css: "limitext", type: "text", editing: false, align: "right", width: 75
                , itemTemplate: function (value) {
                    return  parseFloat(value).toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
                }},
            {title: "จำนวนสะสมจากยอดก่อนรวมกัน", name: "RBILLDATE", css: "limitext", type: "text", editing: false, align: "right", width: 75
                , itemTemplate: function (value) {
                    return  parseFloat(value).toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
                }},
            {title: "จำนวนเงินเป็นคำอ่านภาษาไทย", name: "RPAYDATE", css: "limitext", type: "text", editing: false, align: "right", width: 100},
            {title: "สร้างโดย", name: "RROUND", css: "limitext", type: "text", editing: false, align: "right", width: 100},
            {title: "ประเภทรายการ", name: "RSTATUS", css: "limitext", type: "text", editing: false, align: "right", width: 150},
            {title: "สถานะ", name: "RSTATUS2", css: "limitext", type: "text", editing: false, align: "right", width: 50},
            {title: "สถานะปริ้น", name: "RSTATUS3", css: "limitext", type: "text", editing: false, align: "right", width: 100},
            {title: "User ที่ใช้รายการล่าสุด", name: "RCUSER", css: "limitext", type: "text", editing: false, align: "right", width: 100},
            {title: "Voucher", name: "RVOUCHER", css: "limitext", type: "text", editing: false, align: "right", width: 120},
            {title: "วันมีการเปลี่ยนแปลงของรายการล่าสุด", name: "RCDATE", css: "limitext", type: "text", editing: false, align: "right", width: 100},
            {title: "เวลาของการสร้างรายการล่าสุด", name: "RCTIME", css: "limitext", type: "text", editing: false, align: "right", width: 100}
        ]});

    $("#vTest").click(function () {
        var selectedRowData = args.item;
        var RCOMPANY = selectedRowData.RCOMPANY;
        console.log(RCOMPANY);
    });



    $("#vSearch").click(function () {
        console.log($("#vSupplier").val());
        console.log($("#vAmount").val());
        $("#jsGrid").jsGrid("loadData");
    }
    );
    $("#vGenerate").click(function () {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'text',
            data: {
                path: "importStartData",
                type: $("#vTransfertype").val(),
                invround: $("#vSupplier").val(),
                year: $("#vCar").val(),
                amount: $("#vAmount").val(),
                date: $("#vDate").val(),
                username: "<%out.print(session.getAttribute("user"));%>",
                tratype: $("#vTratype").val(),
                tragsts: $("#vTragsts").val(),
                trapst: $("#vTrapst").val()
            },
            async: false,
            timeout: 60000
        }).done(function (response) {
            alert(response);
            console.log(response);
        })
    });

    $("#vTratype").change(function () {

        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getLS",
                type: $("#vTratype").val(),
                cono:cono,
                divi:divi
            },
            async: false
        }).done(function (response) {
            console.log("GET LS");
            console.log(response);

            console.log(div_data);
        });
        $('#vCar').val('')
    })



    
    
    $("#vSupplier").change(function () {
        $.ajax({
            url: './Action',
            type: 'GET',
            dataType: 'json',
            data: {
                path: "getCarHistory",
                supplier: $("#vSupplier").val(),
                cono:cono,
                divi:divi
            },
            async: false
        }).done(function (response) {
            console.log(response);
            warehouse = response;
            $('#carlist').empty().append('<option value="" selected="selected">Select car!</option>');
            $("<option> All Car </option>").appendTo('#carlist');
            $.each(response, function (i, obj) {
                var div_data = "<option>" + obj.vYear + "</option>";
                $(div_data).appendTo('#carlist');

            });

        });
    })
    $.ajax({
        url: './Action',
        type: 'GET',
        dataType: 'json',
        data: {
            path: "getLS",
            type: $("#vTratype").val(),
            cono:cono,
            divi:divi
        },
        async: false
    }).done(function (response) {
        console.log(response);
        month = response;

        $('#supplierlist').empty().append('<option value="" selected="selected">Select Year!</option>');
        $("<option> All Supplier </option>").appendTo('#supplierlist');
        $.each(response, function (i, obj) {
            var div_data = "<option>" + obj.vLS + "</option>";
            $(div_data).appendTo('#supplierlist');
        });
    });


    $("#vTratype").val(chosentype);
    $("#vTratype").prop("disabled", true);
    $("#jsGrid").jsGrid("loadData");
</script>  
