[#macro changeto sec]	
	[=(((sec / 60)/60)%24)?string("00")]:[=((sec / 60)%60)?string("00")]:[=(sec % 60)?string("00")]
[/#macro]
[#macro changetoms sec]	
	[=((((sec/1000) / 60)/60) % 24)?string("00")]:[=(((sec/1000) / 60)%60)?string("00")]:[=((sec/1000) % 60)?string("00")].[=(sec)?string("00")]
[/#macro]
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv='content-type' content='text/html; charset=UTF-8' />
		<meta name="author" content="rbarbosat">
        <script>[=jquery]</script>
        <script>[=chartjs]</script>
        <script>[=chartjsplugin]</script>
        <style type='text/css'>
            [=css]
        </style>
	</head>
	<body>
        <section id="header">
            <span class='logo'>
                <img class='logo' alt="LOGO Santander" src='https://www.santander.com/csgs/StaticBS?blobcol=urldata&blobheadername1=content-type&blobheadername2=Content-Disposition&blobheadername3=appID&blobheadervalue1=image%2Fpng&blobheadervalue2=inline%3Bfilename%3D30%5C162%5CLogo_Santander_Corp_%28167x46px%29.png&blobheadervalue3=santander.wc.CFWCSancomQP01&blobkey=id&blobtable=MungoBlobs&blobwhere=1278762146098&ssbinary=true'/>
            </span>
        </section>
		<div class='grid'>
            <section id='sidebar'>
                <div>
                    <div id='sidebar-title' data-shadow-text="Text-Shadow">
                        <p>Cilantrum Reports</p>        
                    </div>
                    <ul id='sidebar-menu'>
                        <li>
                            <a href='#content'>
                                <span class='menu-text'><i class='global-icon'></i>Global summary</span>
                            </a>
                        </li>
                        <li>
                            <a href='#global-graphs'>
                                <span class='menu-text'><i class='project-summary-icon'></i>Test Suite summary</span>
                            </a>
                        </li>
                        <li id='sidebar-submenu' class='element-with-triangle'>
                            <a href='#[=suites[0].name]'>
                                <span class='menu-text'><i class='project-icon'></i>Execution detail</span>
                            </a>
                            <ul>
                            	[#list suites as suite]
                            		<li class='submenu-suite'>
	                            		<a href='#[=suite.name]'>
	                            			<span class='menu-text' title='[=suite.name]'>[=suite.name]</span>
	                            		</a>
                            			<span class='plus'></span>
                            			<ul>
		                            		[#list suite.tests as test]
		                            		<li class='submenu-test'>
	                                            <a href='#[=test.name]' title='[=test.name]'>
	                                                <span class='text-[=test.status?lower_case] menu-text-test'>
	                                                    [=test.name]
	                                                </span>
	                                            </a>
	                                        </li>
		                            		[/#list]
	                            		</ul>
	                            	</li>
                            	[/#list]
                            </ul>
                        </li>
                    </ul>
                </div>
            </section>
            <section id="content">
            	<div id="go-to-beginning">
            		<a href="#header">^</a>
            	</div>
                <section id="content-body">
                	<div id="global-info">
                		<div id="summary">
                    		<h1>Global Information</h1>
                    		<table style='width:100%;height:100%'>
	                    		<tr>
	                    			<td style='width:50%'>
	                    				<div class="test-info">
				                            <b>Executed in:</b> [=computer]
				                        </div>
	                    			</td>
	                    			<td style='width:50%'>
	                    				<div class="test-info">
				                            <b>Operative system:</b> [=so]
				                        </div>
	                    			</td>
	                    		</tr>
	                    		<tr>
	                    			<td style='width:50%'>
	                    				<div class="test-info">
	                    					<b>Started at:</b> &nbsp; [=starting_date?number_to_time?string["dd-MM-yyyy HH:mm:ss"]]
				                        </div>
	                    			</td>
	                    			<td style='width:50%'>
	                    				<div class="test-info">
	                    					<b>Total duration:</b> [@changeto sec=total_duration/]
				                        </div>
	                    			</td>
	                    		</tr>
                    			[#if parallel=="true"]
	                    		<tr>
	                    			<td style='width:50%'>
	                    				<div class="test-info">
	                    					<b>Real duration:</b> [@changeto sec=realDuration/]
				                        </div>
	                    			</td>
	                    			<td style='width:50%'>
	                    				<div class="test-info">
	                    					<b>Time saved:</b> [@changeto sec=saved_time/]
				                        </div>
	                    			</td>
	                    		</tr>
                    			[/#if]
	                    		<tr>
	                    			<td style='width:50%'>
	                    				<div class="test-info">
	                    					<b>Finished at:</b> [=ending_date?number_to_time?string["dd-MM-yyyy HH:mm:ss"]]
				                        </div>
	                    			</td>
	                    			<td style='width:50%'>
	                    			</td>
	                    		</tr>
	                    	</table>
                    	</div>
                	</div>
                    <div id='global-summary'>
                        <div id='global-content-grid'>
                            <div id='global-results'>
                                <h1>Global Summary</h1>
                                <table id='summary-table' class='table'>
                                    <thead>
                                        <tr>
                                            <th width='50%'>Suite</th>
                                            <th width='10%'>Tests</th>
                                            <th class='text-passed' width='10%'>Passed</th>
                                            <th class='text-failed' width='10%'>Failed</th>
                                            <th class='text-blocked' width='10%'>Blocked</th>
                                            <th width='20%'>Execution time (s)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
	                                    [#list suites as suite]
	                                        <tr>
	                                            <td title='[=suite.name]' class='menu-text-test'>[=suite.name]</td>
	                                            <td>[=suite.totals]</td>
	                                            <td>[=suite.passed]</td>
	                                            <td>[=suite.failed]</td>
	                                            <td>[=suite.blocked]</td>
	                                            <td>[@changeto sec=suite.duration/]</td>
	                                        </tr>
	                                    [/#list]
                                    </tbody>
                                    <tfoot>
                                        <tr class='totals'>
                                            <th>Total</th>
                                            <th>[=total]</th>
                                            <th>[=total_passed]</th>
                                            <th>[=total_failed]</th>
                                            <th>[=total_blocked]</th>
                                            <th>[@changeto sec=suite_total_time/]</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <div id='global-chart'>
                                <canvas id='summary-chart' class='charts'></canvas>
                            </div>
                        </div>
                    </div>
                    <div id='global-graphs'>
                        <div>
                            <h1>Time spent in each suite</h1>
                        <canvas id='time-per-suite' style="min-height: 90%;min-width: 97%;max-height: 90%;max-width: 97%;"></canvas>
                        </div>
                        <div>
                            <h1>Status of tests in each suite</h1>
                            <canvas id='status-per-suite' style="min-height: 90%;min-width: 97%;max-height: 90%;max-width: 97%;"></canvas>
                        </div>
                    </div>
                    [#list suites as suite]
	                    <div class='test-suite-detail' id='[=suite.name]'>
	                        <h1>[=suite.name]</h1>
	                        <table class='table'>
	                            <thead>
	                                <tr>
	                                    <th width='55%'><span>TestName</span></th>
	                                    <th width='15%'><span>Start</span></th>
	                                    <th width='15%'><span>Finish</span></th>
	                                    <th width='15%'><span>Execution time</span></th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            [#list suite.tests as test]
	                            	<tr class='tests-list'>
	                                    <td class='test-item [=test.status?lower_case]'><a href='#[=test.name]' title='[=test.name]'>[=test.name]</a></td>
	                                    <td class='date'>[=test.start_date?number_to_time?string["dd-MM-yyyy HH:mm:ss"]]</td>
	                                    <td class='date'>[=test.end_date?number_to_time?string["dd-MM-yyyy HH:mm:ss"]]</td>
	                                    <td>[@changetoms sec=test.duration/]</td>
	                                </tr>
	                            [/#list]
	                            </tbody>
	                        </table>
	                    </div>
	                    [#list suite.tests as test]
		                    <div class='test-execution-detail' id='[=test.name]'>
		                        <div class='test-status-container'>
		                            <span class='test-status text-[=test.status?lower_case]'>[=test.status?upper_case]</span>
		                        </div><h1>[=test.name]</h1>
		                        [#if test.model?has_content]
		                        	<div class='test-info'>
		                    			Model: [=test.model]
		                    		</div>
		                    	[/#if]
		                    	[#if test.version?has_content]
		                        	<div class='test-info'>
		                    			Version: [=test.version]
		                    		</div>
		                    	[/#if]
		                    	[#if test.platform?has_content]
		                        	<div class='test-info'>
		                    			Platform: [=test.platform]
		                    		</div>
		                    	[/#if]
		                        <div class="test-info">
		                            Started at: &nbsp; [=test.start_date?number_to_time?string["dd-MM-yyyy HH:mm:ss"]]
		                        </div>
		                        <div class="test-info">
		                            Finished at: [=test.end_date?number_to_time?string["dd-MM-yyyy HH:mm:ss"]]
		                        </div>
		                        <div class="test-info">
		                            Duration: &nbsp; &nbsp; [@changetoms sec=test.duration/]
		                        </div>
		                        <div class="test-info">
		                            Description: [=test.description]
		                        </div>
		                        [#if test.highLevelFailure??]
		                        	<div class='test-info text-failed'>
		                    			Error category: [=test.highLevelFailure]
		                    		</div>
		                    	[/#if]
		                        [#if test.lowLevelFailure??]
		                    		<div class='test-info text-failed'>
		                    			Detail of the failure: [=test.lowLevelFailure]
		                    		</div>
		                    	[/#if]
		                    	[#if test.retriedAttempts > 0]
		                    		<div class='test-info text-failed'>
		                    			This test case was retried '[=test.retriedAttempts]' time/s
		                    		</div>
		                        <div class='test-exec-message-title'>Retried tests</div>
		                        	
		                        	<table class='table'>
			                            <thead>
			                                <tr>
			                                    <th width='10%'>Display/hide</th>
			                                    <th width='10%'><span>Retry attempt</span></th>
			                                    <th width='40%'><span>Error category</span></th>
			                                    <th width='40%'><span>Error detail</span></th>
			                                </tr>
			                            </thead>
			                            <tbody>
			                        		[#list test.retriedTests as retriedTest]
			                        	    	<tr class='tests-list'>
				                            		<td><span class='plus-retry'></span></td>
				                                    <td class='date'>Retry [=retriedTest?index+1]</td>
				                                    <td class='date'>[#if retriedTest.highLevelFailure??][=retriedTest.highLevelFailure][/#if]</td>
				                                    <td class='date'>[#if retriedTest.lowLevelFailure??][=retriedTest.lowLevelFailure][/#if]</td>
				                                </tr>
				                                [#if retriedTest.steps??]
					                                <tr id="[=test.name]-[=retriedTest?index+1]" class="hide">
					                                	<td colspan="4">
					                                	 	<div class='test-exec-message-title'>Last steps</div>
					                                		[#list retriedTest.steps as step]
									                        	[#if step.status??]
									                        		[#if step.status == "IMG"]
								                            			<p style='text-align:center;margin:0px;padding:5px'>
								                            				Screenshot of the step:
								                            			</p>
								                            			<p style='text-align:center;margin:0px;padding:0px;font-size:0.7em'>
								                            				Click to enlarge the image
								                            			</p>
								                            			<img class='screenshot' src='data:image/png;base64, [=step.description]'/>
							                            			[#else]
										                        		<ul>
											                        		<li class='test-message'>
											                        			<span class='steps text-[=step.status?lower_case]'>
											                        				Step.[=step.contador] - 
											                        			</span>
											                        			[#if step.startMillis !=0 || step.startMillis??]
											                        				[[=step.startMillis?number_to_time?string["dd-MM-yyyy HH:mm:ss"]]]-[=step.description]
											                        			[/#if]
											                        		</li>
										                        		</ul>
									                        		[/#if]
									                        	[/#if]
									                        [/#list]
									                        [#if retriedTest.stacktrace??]
		                        								<span class='plus-stacktrace'></span>
										                        <div class='test-exec-message-title'>Exception</div>
										                        <div class='stacktrace hide'>
										                        	[=retriedTest.stacktrace]
										                        </div>
									                        [/#if]
					                                	</td>
					                                </tr>
				                                [/#if]
				                            [/#list]
			                            </tbody>
			                        </table>
		                    	[/#if]
		                        <div class='test-exec-message-title'>Execution steps</div>
		                        <div class='messages'>
		                        [#list test.steps as step]
		                        	[#if step.status??]
		                        		[#if step.status == "IMG"]
	                            			<p style='text-align:center;margin:0px;padding:5px'>
	                            				Screenshot of the step:
	                            			</p>
	                            			<p style='text-align:center;margin:0px;padding:0px;font-size:0.7em'>
	                            				Click to enlarge the image
	                            			</p>
	                            			<img class='screenshot' src='data:image/png;base64, [=step.description]'/>
                            			[#else]
			                        		<ul>
				                        		<li class='test-message'>
				                        			<span class='steps text-[=step.status?lower_case]'>
				                        				Step.[=step.contador] - 
				                        			</span>
				                        			[#if step.startMillis??]
				                        				[[=step.startMillis?number_to_time?string["dd-MM-yyyy HH:mm:ss"]]]-[=step.description]
				                        			[/#if]
				                        		</li>
			                        		</ul>
		                        		[/#if]
		                        	[/#if]
		                        [/#list]
		                        </div>
		                        [#if test.stacktrace??]
		                        	<span class='plus-stacktrace'></span>
			                        <div class='test-exec-message-title'>Exception</div>
			                        <div class='stacktrace hide'>
			                        	[=test.stacktrace]
			                        </div>
		                        [/#if]
		                    </div>
	                    [/#list]
                    [/#list]
                </section>
            </section>
        </div>
	</body>
</html>
<script type="text/javascript">
    $(document).ready(function () {
    
	    var offsetTop = $("#status-per-suite").offset().top;
	
			$(window).scroll(function(){
			  var scrollTop = $(window).scrollTop();
			  if(scrollTop > offsetTop){
			    $("#go-to-beginning").fadeIn(200);
			  }else if(scrollTop < offsetTop){
			    $("#go-to-beginning").fadeOut(200);
			  }
		});
		
		$('.plus-retry').attr('data-content','+');
		
		 $('.plus-retry').click(function(){
            var str = $(this).attr('data-content');
            if(str=='+'){
                $(this).attr('data-content','-');
                $(this).parent().parent().next().show(400);
            }else{
                $(this).attr('data-content','+');
                $(this).parent().parent().next().hide(400);
            }
        });
        
        $('.plus-stacktrace').attr('data-content','+');
		
		 $('.plus-stacktrace').click(function(){
            var str = $(this).attr('data-content');
            if(str=='+'){
                $(this).attr('data-content','-');
                $(this).next().next().show(400);
            }else{
                $(this).attr('data-content','+');
                $(this).next().next().hide(400);
            }
        });
		
		
        $('.plus').attr('data-content','+');
        
        $('.plus').click(function(){
            var str = $(this).attr('data-content');
            if(str=='+'){
                $(this).attr('data-content','-');
                $(this).siblings('ul').slideDown(300);
            }else{
                $(this).attr('data-content','+');
                $(this).siblings('ul').slideUp(300);
            }
        });
        
        $('.plus').hover(function(){
            $(this).siblings("a").addClass('border-on-li');
        });
        
        $('.plus').mouseout(function(){
            $(this).siblings("a").removeClass('border-on-li');
        });
        
        
        var x = false;  
        $('.screenshot').click( 
          function () {  
              if(!x){   
                $(this).animate({'width':'100%'}, 500, 'linear',function(){});  
                 $(this).prev().html('Click again to reduce the image');
                x=true;  
              }else {   
                $(this).animate({'width':'10%'}, 500, 'linear',function(){});  
                 $(this).prev().html('Click to enlarge the image');
                x=false; 
              }  
            });
    });
    
    new Chart(document.getElementById('summary-chart'), {  
        type: 'doughnut',  
        data: {  
          labels: ['Passed', 'Failed', 'Blocked'],
          datasets: [{  
            backgroundColor: ['rgb(54, 255, 115)', '#FF2200', 'rgb(25, 75, 255)'],  
            borderWidth: 0,  
            data: [[=total_passed], [=total_failed], [=total_blocked]]  
          }]  
        },  
        options: {  
          aspectRatio: 1,  
          responsive: true,  
          cutoutPercentage: 0,  
          animation: {  
            duration: 1500,  
            easing: 'easeOutSine',  
          },
          centerText: {
            display: true,  
            text: 'Tests'  
          },
          pieceLabel: {
            overlap: true,
            fontColor: ['black','white','white'],
            position: 'inside',
            render: 'percentage',
            precision: 2
          } 
        }  
    });
    
    new Chart(document.getElementById('status-per-suite'), {  
        type: 'bar',  
        data: {  
          labels: [[#list suites as suite]'[=suite.name]'[#if !(suite?is_last)],[/#if][/#list]],  
          datasets: [{  
              label: 'Passed',  
              data: [[#list suites as suite]'[=suite.passed]'[#if !(suite?is_last)],[/#if][/#list]],  
              fill: false,  
              backgroundColor: 'rgba(0, 255, 50, 0.6)',   
              borderWidth: 1,
              datalabels: {
                align: 'end',
                anchor: 'start'
             }
            },  
            {  
              label: 'Failed',  
              data: [[#list suites as suite]'[=suite.failed]'[#if !(suite?is_last)],[/#if][/#list]],  
              fill: false,  
              backgroundColor: 'rgba(255, 20, 0, 0.7)',   
              borderWidth: 1,
              datalabels: {
                align: 'center',
                anchor: 'center'
             }  
            },  
            { 
              label: 'Blocked',  
              data: [[#list suites as suite]'[=suite.blocked]'[#if !(suite?is_last)],[/#if][/#list]],  
              fill: false,  
              backgroundColor: 'rgba(0,150,255,0.7)',  
              borderWidth: 1,
              datalabels: {
                align: 'end',
                anchor: 'start'
             }  
            }  
          ],  
        },  
        options: {
          events: [],
          scales: {
            xAxes: [{
                stacked: true,
                gridLines: {
                  color: "#CCC"
                },
                ticks: {  
                    fontSize: 9,
                }  
            }],
            yAxes: [{
                stacked: true,
                gridLines: {
                  color: "#CCC"
                },
                ticks: {  
                    beginAtZero: true,
                }  
            }]  
          },animation: {
              onComplete: function() {
                var chartInstance = this.chart,
                  ctx = chartInstance.ctx;

                ctx.font = Chart.helpers.fontString(12, "bold", Chart.defaults.global.defaultFontFamily);
                ctx.textAlign = 'center';
                ctx.textBaseline = 'center';

                this.data.datasets.forEach(function(dataset, i) {
                  var meta = chartInstance.controller.getDatasetMeta(i);
                  meta.data.forEach(function(bar, index) {
                    //var data = dataset.data[index];
                    if(i==2 || i==0){    
                        ctx.fillStyle = 'black';
                    }else{
                        ctx.fillStyle = '#DDD';
                    }
                    var data = dataset.data[index];
                    if (data > 0) {
                      ctx.fillText(data, bar._model.x, bar._model.base - (bar._model.base - bar._model.y) / 2 - 5);
                    }
                  });
                });
              }
            }
        }  
      });
    
    var yAxesticks = [];
    new Chart(document.getElementById('time-per-suite'), { 
        type: 'line', 
        data: { 
          labels: ['',[#list suites as suite]'[=suite.name]'[#if !(suite?is_last)],[/#if][/#list],''], 
          datasets: [{ 
            data: [null, [#list suites as suite]'[=suite.duration?string("0")]',[/#list]null], 
            label: 'Time in seconds', 
            borderColor: 'rgb(75, 192, 192)', 
            backgroundColor: 'rgba(0,250,250,0.4)',
            fill: true,
            pointRadius: 5,
            borderJoinStyle: "round",
            lineTension: 0.09
          }], 
        }, 
        options: {
          aspectRatio: 2,  
          responsive: true,
          maintainAspectRatio: false,
          title: { 
            display: true, 
            text: 'Execution time through each Test Suite' 
          },
            scales:{
                xAxes: [{
                 ticks: {
                    autoSkip: false,
                    maxRotation: 10,
                    minRotation: 10,
                    fontSize: 10
                  },
                  gridLines: {
                    color: "#CCC",
                    offsetGridLines : true
                  }
                }],
                yAxes: [{
                    gridLines: {
                        color: "#CCC"
                    },
                    scaleLabel: {
                      display: true,
                      labelString: 'Seconds'
                    },
                    ticks : {
                        beginAtZero : true,
                        callback : function(value,index,values){
                            yAxesticks = values;
                            return value;
                        },
                        max: [=total_duration_margin?string("0")]
                    }
                }]
            },
          legend: {
            labels: {
               fontSize:14,
              filter: function(label) {
                if (label.text === 'Time in seconds') return true;
              }
            }
          }
        }
      });
</script>
