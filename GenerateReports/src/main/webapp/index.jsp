<%-- 
    Document   : home
    Created on : Sep 26, 2017, 2:21:02 AM
    Author     : ravi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src='dwr/interface/TcAction.js'></script>
        <script src='dwr/engine.js'></script>
        <script src='dwr/util.js'></script>  
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <script>
            bodyonload();
            function bodyonload()
            {
//                 document.getElementById("moduleTC").value="Select Module TC";

                TcAction.getAllModule(function getAllModule(map) {
                    dwr.util.removeAllOptions("module");
                    dwr.util.addOptions("module", map)
                });


            }
            function fill()
            {
                document.getElementById('module').selectedIndex = 0;
                document.getElementById('ModuleTC').selectedIndex = 0;
                $(function () {

                    var val = $('#tc_id').val();

                    if (val.length > 1) {  // check length
                        TcAction.getAllTc_id(val, function (data) {
                            $('#tc_id').autocomplete({source: data});
                        });
                    } else {
                        $('#tc_id').autocomplete({source: []});  // clean
                    }

                });
            }
            function getTCdetails()
            {
                var val = $('#tc_id').val();
                if (val === "")
                {
                    var newval = document.getElementById('ModuleTC').value;
                    var moduleis = document.getElementById('module').value;
                    if (newval === "Select Test Case" && moduleis !== "Select Module")
                    {
                        TcAction.getModuleDetails(moduleis, {callback: function (data) {
                                document.getElementById("Details").innerHTML = data;
                            }
                        });
                    }
                    else
                    {
                        TcAction.getDetails(newval, {callback: function (data) {
                                document.getElementById("Details").innerHTML = data;
                            }
                        });
                    }

                }
                else
                {
                    TcAction.getDetails(val, {callback: function (data) {
                            document.getElementById("Details").innerHTML = data;

                        }
                    });
                }
            }
            function getTCDesc()
            {
                var val = $('#tc_id').val();
                if (val === "")
                {
                    var newval = document.getElementById('ModuleTC').value;
                    var moduleis = document.getElementById('module').value;
                    if (newval === "Select Test Case" && moduleis !== "Select Module")
                    {
                        TcAction.getModuleDetails(moduleis, {callback: function (data) {
                                document.getElementById("Details").innerHTML = data;
                            }
                        });
                    }
                    else
                    {
                        TcAction.getDesc(newval, {callback: function (data) {
                                document.getElementById("Details").innerHTML = data;

                            }
                        });
                    }

                }
                else
                {
                    TcAction.getDesc(val, {callback: function (data) {
                            document.getElementById("Details").innerHTML = data;

                        }
                    });
                }
            }
            function blankText()
            {
                document.getElementById("tc_id").value = "";
                document.getElementById("Details").innerHTML = "";
                var val = document.getElementById("module").value;
                if (val !== "Select")
                {
                    TcAction.getModuleTC(val, function getModuleTC(map) {
                        dwr.util.removeAllOptions("ModuleTC");
                        dwr.util.addOptions("ModuleTC", map)
                    });
                }
            }
            function blankText1()
            {
                document.getElementById("tc_id").value = "";
            }
            function runtc()
            {
                var val=document.getElementById("module").value;
                TcAction.runModule(val, function runModule(){
                	
                });
            }

        </script>
        <div>
            <form>
                <table>
                    <tr>
                        <td><input type="text" id="tc_id" placeholder="Search by TC_ID" onkeyup="fill()" style="width: 400px;"/></td><td> OR<td> 
                        <td><select id="module" onchange="blankText()"/></td>
                        <td><select id="ModuleTC" onchange="blankText1()"><option>Select Test Case</option></select></td>
                        <td> <input type="button" id="result_tc" value="Get Status" onclick="getTCdetails()"/></td>
                        <td> <input type="button" id="desc_tc" value="Get Description" onclick="getTCDesc()"/></td>
                        <td> <input type="button" id="desc_tc" value="Run TestCase" onclick="runtc()"/></td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="Details" style="width: 400px; height: 200px"></div> 



    </body>
</html>
