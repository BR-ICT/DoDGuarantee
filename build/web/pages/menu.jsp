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
    .right-container {
        text-align: right;
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
    .red-text {
        color: red;
    }
    .modal {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        padding-top: 100px; /* Location of the box */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    }
</style>

<script src="./assets/daterangepicker/datetimeui.js"></script>

<section class="container-w100">

    <div class="right-container">
        <label for="Hello">HELLO:</label>
        <label id="username" class="red-text"></label>

    </div>

    <div id="jsGrid"></div>
    <div id="myModal" class="modal">

        <!-- Modal content -->
        <div class="modal-content">
            <label for="chosencustomer">CHOSEN CUSTOMER:</label>
            <label id="chosencustomer"></label>
            <div id="jsGrid2"></div>
        </div>

    </div>

</section>

<script>


    var cono = <%out.print(session.getAttribute("cono"));%>
    var divi = <%out.print(session.getAttribute("divi"));%>
    var auth = "<%out.print(session.getAttribute("auth"));%>";
    var chosentype = "<%out.print(session.getAttribute("type"));%>";
    var user = "<%out.print(session.getAttribute("user"));%>";
    var selecteditem;
    var mode = "new";
    var modal = document.getElementById("myModal");




    var NumberField = jsGrid.NumberField;

    var MyDateField = function (config) {
        jsGrid.Field.call(this, config);
    };
    MyDateField.prototype = new jsGrid.Field({
        sorter: function (date1, date2) {
            return new Date(date1) - new Date(date2);
        },
        itemTemplate: function (value) {
            var date = new Date(value);
            var day = date.getDate();
            var month = date.getMonth() + 1; // Adding 1 because months are zero-based
            var year = date.getFullYear();

            var formattedDate = ('0' + day).slice(-2) + '-' + ('0' + month).slice(-2) + '-' + year;
            return formattedDate
        },
        insertTemplate: function (value) {
            return this._insertPicker = $("<input>").datepicker({dateFormat: 'dd-mm-yy'}).datepicker({defaultDate: new Date()});
        },
        editTemplate: function (value) {
            return this._editPicker = $("<input>").datepicker({dateFormat: 'dd-mm-yy'}).datepicker("setDate", new Date(value));
        },
        insertValue: function () {
            return this._insertPicker.datepicker("getDate");
        },
        editValue: function () {
            return this._editPicker.datepicker("getDate");
        }
    });
    jsGrid.fields.myDateField = MyDateField;


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
        pageSize: 30,
        deleteConfirm: "Do you really want to delete the client?",
        heading: true,
        inserting: false,
        selecting: true,
        pageLoading: false,
        rowClick: function (args) {
            selecteditem = args.item;
            console.log(selecteditem);
            $("#chosencustomer").text(selecteditem.RNAME);
            $("#jsGrid2").jsGrid("loadData");
//            {
//                document.getElementById('bottomsection').scrollIntoView({behavior: 'smooth'})
//            }
            modal.style.display = "block";
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
//                        completed: $("#vStatus").val(),
//                        type: $("#SearchvTratypes").val(),
                        cono: cono,
                        divi: divi,
                        path: "getMenu"

                    },
                    async: false,
                    timeout: 60000
                }).done(function (response) {
                    console.log(response);
                    response = $.grep(response, function (item) {
                        return(!filter.RFARMER || (item.RFARMER.indexOf(filter.RFARMER) > -1))
                                && (!filter.RNAME || (item.RNAME.indexOf(filter.RNAME) > -1))
                                && (!filter.RCN || (item.RCN.indexOf(filter.RCN) > -1))
                                && (!filter.RREQAMT || (item.RREQAMT.indexOf(filter.RREQAMT) > -1))
                                && (!filter.RCVAMT || (item.RCVAMT.indexOf(filter.RCVAMT) > -1))
                                && (!filter.RBALANCE || (item.RBALANCE.indexOf(filter.RBALANCE) > -1));
                        console.log(data.resolve(response));

                    });
                    data.resolve(response);

                    console.log(response);
                });
                return data.promise();
            },

            insertItem: function (item) {
            },
            updateItem: function (item) {
            },
            deleteItem: function (item) {
            }
        },
        fields: [
            {title: "FARMER", name: "RFARMER", css: "limitext", type: "text", editing: false, align: "left", width: 50},
            {title: "NAME", name: "RNAME", css: "limitext", type: "text", editing: false, align: "left", width: 100},
            {title: "CN", name: "RCN", css: "limitext", type: "text", editing: false, align: "center", width: 10},
            {title: "REQAMT", name: "RREQAMT", css: "limitext", type: "text", editing: false, align: "right", width: 50},
            {title: "RCVAMT", name: "RCVAMT", css: "limitext", type: "text", editing: false, align: "right", width: 50},
            {title: "BALANCE", name: "RBALANCE", css: "limitext", type: "text", editing: false, align: "right", width: 50}
        ]

    });

    $("#jsGrid2").jsGrid({
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
        selecting: true,
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
                        cono: cono,
                        divi: divi,
                        path: "getMenuDetail",
                        selecteditem: selecteditem.RFARMER,
                        Name: selecteditem.RNAME
                    },
                    async: false,
                    timeout: 60000
                }).done(function (response) {
                    console.log(response);
                    response = $.grep(response, function (item) {
                        return(!filter.RFARMER || (item.RFARMER.indexOf(filter.RFARMER) > -1))
                                && (!filter.RNAME || (item.RNAME.indexOf(filter.RNAME) > -1))
                                && (!filter.RCN || (item.RCN.indexOf(filter.RCN) > -1))
                                && (!filter.RREQAMT || (item.RREQAMT.indexOf(filter.RREQAMT) > -1))
                                && (!filter.RCVAMT || (item.RCVAMT.indexOf(filter.RCVAMT) > -1))
                                && (!filter.RBALANCE || (item.RBALANCE.indexOf(filter.RBALANCE) > -1));
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
            }
        },
        fields: [
            {title: "CustomerID", name: "RFARMER", css: "limitext", type: "text", editing: false, align: "left", width: 50},
            {title: "DOD no.", name: "RDODNO", css: "limitext", type: "text", editing: false, align: "left", width: 100},
            {title: "Status", name: "RSTATUS", css: "limitext", type: "text", editing: false, align: "center", width: 10},
            {title: "House", name: "RHOUSE", css: "limitext", type: "text", editing: false, align: "right", width: 50},
            {title: "DOD Qantity", name: "RQTY", css: "limitext", type: "text", editing: false, align: "right", width: 50},
            {title: "Price(Baht)", name: "RPRICE", css: "limitext", type: "text", editing: false, align: "right", width: 50},
            {title: "Req Amount(Baht)", name: "RAMTBAHT", css: "limitext", type: "text", editing: false, align: "right", width: 50}
        ]

    });

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
    $("#jsGrid").jsGrid("loadData");
    $("#username").text(user);


</script>  
