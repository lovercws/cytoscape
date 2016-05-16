<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网络图</title>
<script type="text/javascript" src="js/cytoscape.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css" />
<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js"
	charset="UTF-8"></script>
<script type="text/javascript">
	Ext.onReady(function() {
		var cy = cytoscape({
			container : document.querySelector('#cy'),
			boxSelectionEnabled : false,
			autounselectify : true,
			elements : {
				nodes : [ {
					data : {
						id : 'j',
						name : 'Jerry'
					}
				}, {
					data : {
						id : 'e',
						name : 'Elaine'
					}
				}, {
					data : {
						id : 'k',
						name : 'Kramer'
					}
				}, {
					data : {
						id : 'g',
						name : 'George'
					}
				} ],
				edges : [ {
					data : {
						source : 'j',
						target : 'e'
					}
				}, {
					data : {
						source : 'j',
						target : 'k'
					}
				}, {
					data : {
						source : 'j',
						target : 'g'
					}
				}, {
					data : {
						source : 'e',
						target : 'j'
					}
				}, {
					data : {
						source : 'e',
						target : 'k'
					}
				}, {
					data : {
						source : 'k',
						target : 'j'
					}
				}, {
					data : {
						source : 'k',
						target : 'e'
					}
				}, {
					data : {
						source : 'k',
						target : 'g'
					}
				}, {
					data : {
						source : 'g',
						target : 'j'
					}
				} ]
			},
			layout : {
				name : 'grid',
				padding : 10
			}
		});

		var store = Ext.create('Ext.data.TreeStore', {
			root : {
				expanded : true,
				children : [ {
					text : "detention",
					leaf : true
				}, {
					text : "homework",
					expanded : true,
					children : [ {
						text : "book report",
						leaf : true
					}, {
						text : "algebra",
						leaf : true
					} ]
				}, {
					text : "buy lottery tickets",
					leaf : true
				} ]
			}
		});
		var tree = Ext.create('Ext.tree.Panel', {
			title : '网络图节点',
			titleAlign : 'center',
			flex : 2,
			frame : true,
			width : 320,
			height : 800,
			store : store,
			rootVisible : false,
			renderTo : 'tree'
		});
	});
</script>
</head>
<body>
	<div style="width: 100%; height: 100%">
		<div id='cy' style="float:left;position:absolute; width: 70%; height: 90%;border: 1px solid blue"></div>
		<div id='tree' style="float:right;position:relative ;width: 20%; height: 90%;border: 1px solid red"></div>
	</div>
</body>
</html>