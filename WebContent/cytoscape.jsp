<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cytoscope网络图</title>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, minimal-ui">
<script type="text/javascript" src="./js/cytoscape.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css"/>
<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js" charset="UTF-8"></script>
<style type="text/css">
#cy {
	width: 1330px;
	height: 870px;
	display: block;
	margin: 0px; padding : 0px;
	background-color: white;
	padding: 0px;
	overflow: hidden;
	z-index: 999;
}
div{
    left:0px;
    top: 0px;
}
</style>
<script type="text/javascript">
var data;//网络图的节点和样式
document.oncontextmenu=function(){return false;}
document.addEventListener('DOMContentLoaded', function() { // on dom ready
	//ajax请求
	function handleRequest(url,type,value){
		 var str;
		 Ext.Ajax.request({
		    	method:'POST',
		    	url:url,
		    	params:{type:type,value:value},
		    	async:false,
		    	success:function(response){
		    		str=response.responseText;
		    	},
		    	failure:function(response){
		    	}
		    });
		 return str;
	}
	//闭包获取网络图数据
	(function(){
		var str=handleRequest('NetworkGraphServlet', 'getNetworkGraph', null);
		data=Ext.JSON.decode(str);
		console.log(data);
	})();
	
	var cy = cytoscape({
		container : document.querySelector('#cy'),
		boxSelectionEnabled : false,
		autounselectify : true,
		style : data.style,
		elements : data.elements,
		layout : {
			name : 'circle',
			padding : 10
		}
	});

 	//设置节点的坐标位置
	cy.filter(function(i, element){
		 if( element.isNode() && element.data("positionX") >0.0 ){
			 //alert("宽度 "+element.width());
			 //alert("高度"+element.height());
			 element.renderedPosition({
				 x : element.data("positionX"),
				 y : element.data("positionY")
			 });
		    return true;
		 }
		 return false;
    }); 
	
	//拖动节点
	cy.on('tapend', 'node', function(e){
		var position=e.cyRenderedPosition;
		var node = e.cyTarget; 
		handleRequest("NetworkGraphServlet", "updatePosition", "{x:'"+position.x+"',y:'"+position.y+"',id:'"+node.data("id")+"'}")
	});
	//点击节点
	cy.on('tap', 'node', function(e){
		console.log(e);
	});
	//点击连接线
	cy.on('tap', 'edge', function(e){
		console.log(e);
	});
	
	//当只有一个节点的时候 要缩放节点
	var nodes=cy.collection("node");
	if(nodes.length==1){
		var element=nodes[0];
		//节点缩放
		cy.zoom({
			  level: 1.0, // the zoom level
			  renderedPosition: { x: 300, y: 150 }
		});
		
		var positionX=element.data("positionX");
		if(positionX<40){
			positionX=40;
		}
		
		var positionY=element.data("positionY");
		if(positionY<30){
			positionY=30;
		}
		//节点位置
		element.renderedPosition({
			 x : positionX,
			 y : positionY
		 });
	}
	
	//禁用放大和缩小、面板移动
	cy.userZoomingEnabled(false);
	cy.userPanningEnabled(false);
	
	
});	
</script>
</head>
<body>
	<div id="cy"></div>
</body>
</html>