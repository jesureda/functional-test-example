[#macro changeto sec]	
	[=(((sec / 60)/60)%24)?string("00")]:[=((sec / 60)%60)?string("00")]:[=(sec % 60)?string("00")]
[/#macro]
[#macro changetoms sec]	
	[=(((sec/1000) / 60) / 24)?string("00")]:[=((sec/1000) / 60)?string("00")]:[=((sec/1000) % 60)?string("00")].[=(sec)?string("00")]
[/#macro]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Cilantrum Email</title>
        <style type="text/css">
            body {
                font-family: Arial;
            }
            .head{
                background-color: rgb(222,222,222);
            }
            .odd{
                background-color: rgb(239,239,239);
            }
            tr,td,th{
                font-size: 12px;
            }
            table{
                border-collapse: collapse;
            }
            .datos{
                margin-bottom: 20px;
            }
            .datos td,.datos tr,.datos th{
                padding: 5px;
            }
            .textfont {
                font-weight: normal;
                font-size: 12px;
                color: #000000;
                font-family: verdana, arial, helvetica, sans-serif
            }
            .title {
                background: rgb(0,66,84);
                color: white !important;
            }
            .underline {
                content: '';
                background: rgb(3,101,124);
                height: 12px;
            }
            .product {
                font-size: 24px;
                padding: 5px 10px;
            }
            .text-failed{
                color: red;
            }
            .text-passed{
                color: green;
            }
            .text-blocked{
                color: blue;
            }
            .failed {
                background-color: rgb(255,100,100);
            }
            .passed {
                background-color: rgb(88,245,153);
            }
            .blocked {
                background-color: rgb(98,162,240);
            }
            .graph-global{
                background-color: rgb(0,66,84);
                color: white;
            }
            .graph{
                width: 100%;
                color: white;
                margin-bottom: 1px;
            }
            .graph .title{
                border-right: 10px solid rgb(3,101,124);
            }
            .graph td{
                padding: 5px;
                height: 30px;
            }
            .filler{
                background: rgb(240,240,240);
            }
            .header{
                border-bottom: 2px solid rgb(255,192,51);
                padding-bottom: 5px;
            }
        </style>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body>
        <!-- Tabla Fondo -->
        <table class='textfont' width='100%' border='0' cellspacing='0' cellpadding='0'>
            <!-- Cabecera del informe -->
            <tr>
                <td>
                    <table cellspacing='0' cellpadding='0' width='100%' align='center'
                        border='0'>
                        <tbody>
                            <tr class='title'>
                                <td class='product' width='80%'>Ejecuciones MOT</td>
                                <td class='subheader' align='right' >[=report_date]</td>
                                <td width='2%'></td>
                            </tr>
                            <tr class='underline'>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>

            <!-- Contenido del informe -->
            <tr>
                <td valign="top">
                    <table cellspacing='0' cellpadding='0' width='100%' align='center'>
                        <tbody>
                            <tr>
                                <td><h3 class='header' style='margin:5px 0 5px 0'>Test Suites summary</h3></td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            <!-- Tabla de acumulados -->
            <tr>
                <td>
                    <table class='textfont' cellspacing='0' cellpadding='0' width='100%'
                        align='center' border='0'>
                        <tbody>
                            <tr>
                                <td colspan='4' class='owner'>
                                    <table class='datos' cellspacing='0' cellpadding='0'
                                        width='100%' align='center' border='0'>
                                        <tr class='head textfont'>
                                            <th class='chl' style='width: 50%' align='left'>Suite name</th>
                                            <th class='chl text-passed'
                                                style='width: 10%; text-align: center'>Passed</th>
                                            <th class='chl text-failed'
                                                style='width: 10%; text-align: center'>Failed</th>
                                            <th class='chl text-blocked'
                                                style='width: 10%; text-align: center'>Blocked</th>
                                            <th class='chl'
                                                style='width: 10%; text-align: center'>Total</th>
                                            <th class='chl'
                                                style='width: 10%; text-align: center'>Duration</th>
                                        </tr>
                                        [#assign row=0]
                                        [#list suites as suite]
                                        	<tr class='textfont [#if (row%2)!=0]odd[/#if]'>
	                                            <td align='left'>[=suite.name]</td>
	                                            <td align='center'>[=suite.passed]</td>
	                                            <td align='center'>[=suite.failed]</td>
	                                            <td align='center'>[=suite.blocked]</td>
	                                            <td align='center'>[=suite.totals]</td>
	                                            <td align='center'>[@changeto sec=suite.duration/]</td>
                                        	</tr>
                                        	[#assign row = row +1]
                                        [/#list]
                                        <tr class='head textfont'>
                                            <th align='left'>Totals</th>
                                            <th align='center' class='text-passed'>[=total_passed]</th>
                                            <th align='center' class='text-failed'>[=total_failed]</th>
                                            <th align='center' class='text-blocked'>[=total_blocked]</th>
                                            <th align='center'>[=totals]</th>
                                            <th align='center'>[@changeto sec=total_duration/]</th>
                                        </tr>
                                    </table>
                                    <table cellspacing='0' cellpadding='0'
                                        width='100%' align='center' border='0'>
                                        <tr>
                                            <td valign="top">
                                                <table cellspacing='0' cellpadding='0' width='100%' align='center'>
                                                    <tbody>
                                                        <tr>
                                                            <td><h3 class='header' style='margin:0 0 5px 0'>Global Status</h3></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <table class='graph' cellspacing='0' cellpadding='0'
                                                    width='100%' align='center' border='0'>
                                                    <tr>
                                                        <td class='title' width='15%' align='center'>Passed [=percentpassed]%</td>
                                                        <td class='passed' width='[=percentpassedcalculated]%'></td>
                                                        <td class='filler' width='[=percentnotpassedcalculated]%'></td>
                                                    </tr>
                                                </table>
                                                <table class='graph' cellspacing='0' cellpadding='0'
                                                    width='100%' align='center' border='0'>
                                                    <tr>
                                                        <td class='title' width='15%' align='center'>Failed [=percentfailed]%</td>
                                                        <td class='failed' width='[=percentfailedcalculated]%'></td>
                                                        <td class='filler' width='[=percentnotfailedcalculated]%'></td>
                                                    </tr>
                                                </table>
                                                <table class='graph' cellspacing='0' cellpadding='0'
                                                    width='100%' align='center' border='0'>
                                                    <tr>
                                                        <td class='title' width='15%' align='center'>Blocked [=percentblocked]%</td>
                                                        <td class='blocked' width='[=percentblockedcalculated]%'></td>
                                                        <td class='filler' width='[=percentnotblockedcalculated]%'></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellspacing='0' cellpadding='0' width='100%' align='center' border='0' style='margin-top: 25px'>
	                    <tr>
	                    	<td><img src='https://www.santander.com/csgs/StaticBS?blobcol=urldata&blobheadername1=content-type&blobheadername2=Content-Disposition&blobheadername3=appID&blobheadervalue1=image%2Fpng&blobheadervalue2=inline%3Bfilename%3D30%5C162%5CLogo_Santander_Corp_%28167x46px%29.png&blobheadervalue3=santander.wc.CFWCSancomQP01&blobkey=id&blobtable=MungoBlobs&blobwhere=1278762146098&ssbinary=true' alt="Esto es un logo"/></td>
	                    </tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>