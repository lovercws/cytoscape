<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网络图</title>
<link rel="stylesheet" type="text/css" href="resources/css/ext-all.css"/>
<script type="text/javascript" src="js/ext-all.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/ext-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript">
document.oncontextmenu=function(){return false;}
var checkboxNodes=[];//节点详情的 父节点列表
var nodeId;//节点的id
var detailNodeForm;//节点详情表单
Ext.onReady(function(){
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
	//获取父节点
	function getParentNodes(editNodeId){
		var str=handleRequest('NetworkNodeServlet','getCheckboxNodes',editNodeId);
		checkboxNodes=Ext.JSON.decode(str);
	}
	function createDetailNodeForm(){
		//节点的详情
    	detailNodeForm=Ext.create('Ext.form.Panel',{
    		frame:true,
    		title:'网络图节点信息',
    		titleAlign:'center',
    		padding:0,
    		id:'form',
    		flex:1,
    		xtype:'form',
    		defaults:{
    			labelWidth:50
    		},
    		defaultType: 'textfield',
    		items:[{
    			xtype:'fieldset',
    	        title: '节点',
    	        collapsible: true,
    	        defaultType: 'textfield',
    	        defaults: {anchor: '100%'},
    	        layout: 'anchor',
    	        items :[{
    				fieldLabel:'名称',
    				name:'name',
    				labelWidth:50,
    				margin:'20 0 0 0',
    				allowBlank:false
    			},
    			{
    				xtype      : 'fieldcontainer',
    	            fieldLabel : '形状',
    	            name:'shape',
    	            margin:'20 0 0 0',
    	            labelWidth:50,
    	            defaultType: 'radiofield',
    	            layout: {
    	            	type:'table',
    	            	columns: 3
    	            },
    	            items: [
    	                {
    	                    boxLabel  : '圆形',
    	                    name      : 'shape',
    	                    inputValue: 'circle',
    	                    margin:'0 0 0 0',
    	                    checked:true
    	                }, {
    	                    boxLabel  : '矩形',
    	                    name      : 'shape',
    	                    inputValue: 'rectangle',
    	                    margin:'0 0 0 20'
    	                }, {
    	                    boxLabel  : '圆角矩形',
    	                    name      : 'shape',
    	                    inputValue: 'roundRectangle',
    	                    margin:'0 0 0 20'
    	                }, {
    	                    boxLabel  : '椭圆',
    	                    name      : 'shape',
    	                    inputValue: 'ellipse',
    	                    margin:'0 0 0 0'
    	                },{
    	                    boxLabel  : '三角形',
    	                    name      : 'shape',
    	                    inputValue: 'triangle',
    	                    margin:'0 0 0 20'
    	                },{
    	                    boxLabel  : '五边形',
    	                    name      : 'shape',
    	                    inputValue: 'pentagon',
    	                    margin:'0 0 0 20'
    	                },{
    	                    boxLabel  : '六边形',
    	                    name      : 'shape',
    	                    inputValue: 'hexagon',
    	                    margin:'0 0 0 0'
    	                },{
    	                    boxLabel  : '七边形',
    	                    name      : 'shape',
    	                    inputValue: 'heptagon',
    	                    margin:'0 0 0 20'
    	                },{
    	                    boxLabel  : '八边形',
    	                    name      : 'shape',
    	                    inputValue: 'octagon',
    	                    margin:'0 0 0 20'
    	                },{
    	                    boxLabel  : '星行',
    	                    name      : 'shape',
    	                    inputValue: 'star',
    	                    margin:'0 0 0 0'
    	                },{
    	                    boxLabel  : '菱形',
    	                    name      : 'shape',
    	                    inputValue: 'diamond',
    	                    margin:'0 0 0 20'
    	                },{
    	                    boxLabel  : 'v字形',
    	                    name      : 'shape',
    	                    inputValue: 'vee',
    	                    margin:'0 0 0 20'
    	                },{
    	                    boxLabel  : '偏菱形',
    	                    name      : 'shape',
    	                    inputValue: 'rhomboid',
    	                    margin:'0 0 0 0'
    	                },{
    	                    boxLabel  : '多边形 ',
    	                    name      : 'shape',
    	                    inputValue: 'polygon',
    	                    margin:'0 0 0 20'
    	                },{
    	                    boxLabel  : '图片',
    	                    name      : 'shape',
    	                    inputValue: 'picutre',
    	                    margin:'0 0 0 20'
    	                }
    	            ]
    			},{
    				xtype:'fieldcontainer',
    				fieldLabel:'节点颜色',
    				name:'color',
    				layout: 'hbox',
    				labelWidth:50,
    				margin:'20 0 10 0',
    				items:[{
    					xtype:'textfield',
    					name:'backgroundColor',
    					id:'backgroundColor',
    				},{
    					xtype:'button',
    					text:'选择',
    					menu: colorPicker
    				}]
    			}]
    		},{
    			xtype:'fieldset',
    	        title: '连接线',
    	        collapsible: true,
    	        defaultType: 'textfield',
    	        defaults: {anchor: '100%'},
    	        layout: 'anchor',
    	        items :[{
    				xtype      : 'fieldcontainer',
    	            fieldLabel : '父节点',
    	            labelWidth:50,
    	            margin:'20 0 0 0',
    	            name:'parentNode',
    	            defaultType: 'checkboxfield',
    	            layout: 'vbox',
    	            items: checkboxNodes
    			},{
    				xtype      : 'fieldcontainer',
    	            fieldLabel : '类型',
    	            labelWidth:50,
    	            margin:'20 0 0 0',
    	            defaultType: 'radiofield',
    	            layout: 'hbox',
    	            items: [
    	                {
    	                    boxLabel  : '实线',
    	                    name      : 'lineStyle',
    	                    inputValue: 'solid',
    	                    margin:'0 0 0 0',
    	                    checked:true
    	                }, {
    	                    boxLabel  : '点线',
    	                    name      : 'lineStyle',
    	                    inputValue: 'dotted',
    	                    margin:'0 0 0 20'
    	                }, {
    	                    boxLabel  : '虚线',
    	                    name      : 'lineStyle',
    	                    inputValue: 'dashed',
    	                    margin:'0 0 0 20'
    	                }
    	            ]
    			},{
    				xtype      : 'fieldcontainer',
    	            fieldLabel : '方向',
    	            labelWidth:50,
    	            margin:'20 0 0 0',
    	            defaultType: 'radiofield',
    	            layout: 'hbox',
    	            hidden:true,
    	            items: [
    	                {
    	                    boxLabel  : '节点->父节点',
    	                    name      : 'direction',
    	                    inputValue: 'ntp',
    	                    margin:'0 0 0 0',
    	                    checked:true
    	                }, {
    	                    boxLabel  : '父节点->节点',
    	                    name      : 'direction',
    	                    inputValue: 'ptn',
    	                    margin:'0 0 0 20'
    	                }
    	            ]
    			},{
    				xtype      : 'fieldcontainer',
    	            fieldLabel : '线类型',
    	            labelWidth:50,
    	            margin:'20 0 0 0',
    	            defaultType: 'radiofield',
    	            layout: 'hbox',
    	            items: [
    	                {
    	                    boxLabel  : '直线',
    	                    name      : 'curveStyle',
    	                    inputValue: 'bezier',
    	                    margin:'0 0 0 0',
    	                    checked:true
    	                }, {
    	                    boxLabel  : '曲线',
    	                    name      : 'curveStyle',
    	                    inputValue: 'unbundled-bezier',
    	                    margin:'0 0 0 20'
    	                }
    	            ]
    			},{
    				xtype:'fieldcontainer',
    				fieldLabel:'线颜色',
    				layout: 'hbox',
    				labelWidth:50,
    				margin:'20 0 10 0',
    				items:[{
    					xtype:'textfield',
    					name:'lineColor',
    					id:'lineColor',
    				},{
    					xtype:'button',
    					text:'选择',
    					menu: lineColorPicker
    				}]
    		},{
    				xtype:'numberfield',
    				fieldLabel:'粗细粒度',
    				labelWidth:60,
    				margin:'20 0 0 0',
    				name:'width',
    				value: 1,
    		        maxValue: 99,
    		        minValue: 0
    			},{
    				xtype:'hiddenfield',
    				name:'id'
    			}]
    		}],
    		bbar:['->',{
    			text:'保存',
    			handler:function(){
    				var form=Ext.getCmp('form').getForm();
    				console.log(form.getValues());
    				if(form.isValid()){
    					form.submit({
    						method:'POST',
    						url:'NetworkNodeServlet',
    						params:{type:'save'},
    						success: function(form, action) {
    						       window.location.reload();
    						    },
    						    failure: function(form, action) {
    						    	Ext.Msg.alert('提示', action.result.msg);
    						    }
    					});
    				}
    			}
    		},{
    			text:'关闭',
    			handler:function(){
    				var activeTab=tab.getActiveTab();
    				tab.remove(activeTab);
    			}
    		},'->']
    	});
	}
	//网络图节点数据源
	var store = Ext.create('Ext.data.TreeStore', {
		autoLoad:false,
	    proxy:{type:'ajax',url:'NetworkNodeServlet?type=getTreeNodes',reader:{type:'json'}},
	});
	//添加tab isNew是否是新创建节点
	function addTab(nodeType){
		//如果不存在节点详情 则创建
	    var activeTab=Ext.getCmp('detailNode');
		if(activeTab){
			tab.remove(activeTab);
		}
		
		var editNodeId;
		//获取复选框节点
		if(nodeType=='edit'){
			editNodeId=nodeId;
		}
		getParentNodes(editNodeId);
	    createDetailNodeForm();
	    
		activeTab=tab.add({
			id:'detailNode',
    		title: '节点详情',
	        layout:{
			    type:"vbox",
			    align:'stretch'
			},
			items:detailNodeForm
    	});
		detailNodeForm.getForm().reset();
		tab.setActiveTab(activeTab);
		
		//如果是创建新的节点
		if(nodeType=='create'){
		}
		//如果是编辑节点
		else if(nodeType=='edit'){
			var str=handleRequest('NetworkNodeServlet','getDetailNode',nodeId);
			var values=Ext.JSON.decode(str);
			detailNodeForm.getForm().setValues(values);//填充form表单
			
			var parentNode=values.parentNode;
			var nodes=parentNode.split(',');
			for(var i=0,len=nodes.length;i<len;i++){
				var checkbox=Ext.getCmp(nodes[i]);
				if(checkbox){
					checkbox.setValue(nodes[i]);
				}
			}
		}
	}
	//网络图节点
	var tree=Ext.create('Ext.tree.Panel', {
	    title: '网络图节点',
	    titleAlign:'center',
	    flex:2,
	    padding:0,
	    frame:true,
	    store: store,
	    rootVisible: false,
	    listeners:{
	    	itemcontextmenu:function(view,record,item,index,e,eOpts){
	    		nodeId=record.data.id;
	    		treeMenu.showAt(e.getXY());
	    	},
	    	itemdblclick:function(VIEW,record,item,index,e,eOpts){
	    		nodeId=record.data.id;
	    		if(nodeId=='root'){
	    			//addTab("create");
	    		}else{
	    			addTab("edit");
	    		}
	    	}
	    }
	});
	//按钮组
	var treeMenu=Ext.create('Ext.menu.Menu', {
	    width: 100,
	    margin: '0 0 10 0',
	    items: [{
	        text: '添加',
	        handler:function(){
	        	addTab('create');
	        }
	    },{
	        text: '编辑',
	        handler:function(){
	        	addTab('edit');
	        }
	    },{
	        text: '删除',
	        handler:function(){
	        	Ext.MessageBox.confirm('提示','你确定要删除吗?',function(btn){
	        		if(btn=='yes'){
	        			handleRequest('NetworkNodeServlet', 'delete', nodeId);
	        			window.location.reload();
	        		}
	        	});
	        }
	    }]
	});
	//节点颜色选择器
	var colorPicker = Ext.create('Ext.menu.ColorPicker', {
	    value: '000000',
	    listeners:{
	    	select:function(picker,color,eOpts){
	    		Ext.getCmp('backgroundColor').setValue(color);
	    	}
	    }
	});
	//线颜色选择器
	var lineColorPicker = Ext.create('Ext.menu.ColorPicker', {
	    value: '000000',
	    listeners:{
	    	select:function(picker,color,eOpts){
	    		Ext.getCmp('lineColor').setValue(color);
	    	}
	    }
	});
	
	//tab
	var tab=Ext.create('Ext.tab.Panel', {
		flex:2,
		activeTab:0,
		tabPosition:'bottom',
	    items: [{
	        title: '网路图节点',
	        items:tree,
	        layout:{
			    type:"vbox",
			    align:'stretch'
			}
	    }]
	}); 
	
	//网络图
	var cytoscope=Ext.create('Ext.panel.Panel',{
		title:'网络图Demo1',
		padding:0,
		frame:true,
		titleAlign:'center',
		flex:8,
		html:'<iframe frameborder="0" width="100%" height="100%"  src="cytoscape.jsp"></iframe>'
	});
	//Main布局
	Ext.create('Ext.container.Viewport',{
		layout:{
		    type:"hbox",
		    align:'stretch'
		},
		items:[cytoscope,tab]
	});
});
</script>
</head>
<body>
</body>
</html>