<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset=utf-8 />
<title>GeneMANIA export</title>
<script src="js/cytoscape.min.js"></script>
<script src="js/jquery.js"></script>
<style type="text/css">
body { 
  font: 14px helvetica neue, helvetica, arial, sans-serif;
}

#cy {
  height: 100%;
  width: 100%;
  position: absolute;
  left: 0;
  top: 0;
}
</style>
<script type="text/javascript">
$(function(){ // on dom ready

	var cy = cytoscape({
	  container: document.getElementById('cy'),
	  
	  style: cytoscape.stylesheet()
	    .selector('node')
	      .css({
	        'font-size': 10,
	        'content': 'data(name)',
	        'text-valign': 'center',
	        'color': 'white',
	        'text-outline-width': 2,
	        'text-outline-color': '#888',
	        'min-zoomed-font-size': 8,
	        'width': 'mapData(score, 0, 1, 20, 50)',
	        'height': 'mapData(score, 0, 1, 20, 50)'
	      })
	    .selector('node[node_type = "query"]')
	      .css({
	        'background-color': '#666',
	        'text-outline-color': '#666'
	      })
	    .selector('node:selected')
	      .css({
	        'background-color': '#000',
	        'text-outline-color': '#000'
	      })
	    .selector('edge')
	      .css({
	        'curve-style': 'haystack',
	        'opacity': 0.333,
	        'width': 'mapData(normalized_max_weight, 0, 0.01, 1, 2)'
	      })
	    .selector('edge[data_type = "Predicted"]')
	      .css({
	        'line-color': '#F6C28C'
	      })
	    .selector('edge[data_type = "Physical interactions"]')
	      .css({
	        'line-color': '#EAA2A3'
	      })
	    .selector('edge[data_type = "Shared protein domains"]')
	      .css({
	        'line-color': '#DAD4A8'
	      })
	    .selector('edge[data_type = "Co-expression"]')
	      .css({
	        'line-color': '#D0B7D3'
	      })
	    .selector('edge[data_type = "Pathway"]')
	      .css({
	        'line-color': '#9BD8DD'
	      })
	    .selector('edge[data_type = "Co-localization"]')
	      .css({
	        'line-color': '#A0B3D8'
	      })
	  .selector('edge:selected')
	    .css({
	      opacity: 1
	    }),
	  
	    elements: {
	        nodes: [
	          {
	            data: { id: 'cy', name: 'Cytoscape.js',node_type:'query' }
	          },
	          
	          {
	            data: { id: 'core', name: 'Core', parent: 'api' },
	            position: { x: 0, y: 0 }
	          },
	          
	          {
	            data: { id: 'eles', name: 'Collection', parent: 'api',node_type:'query' },
	            position: { x: 150, y: 150 }
	          },
	          
	          {
	            data: { id: 'style', name: 'Stylesheet', parent: 'api',node_type:'query' },
	            position: { x: 0, y: 150 }
	          },
	          
	          {
	            data: { id: 'selector', name: 'Selector', parent: 'api' },
	            position: { x: -150, y: 150 }
	          },
	          
	          {
	            data: { id: 'ext', name: 'Extensions', parent: 'cy' ,node_type:'query'}
	          },
	          
	          {
	            data: { id: 'corefn', name: 'Core Function', parent: 'ext',node_type:'query' },
	            classes: 'ext',
	            position: { x: 350, y: -140 }
	          },
	          
	          {
	            data: { id: 'elesfn', name: 'Collection Function', parent: 'ext' },
	            classes: 'ext',
	            position: { x: 350, y: 0 }
	          },
	          
	          {
	            data: { id: 'layout', name: 'Layout', parent: 'ext',node_type:'query' },
	            classes: 'ext',
	            position: { x: 350, y: 140 }
	          },
	          
	          {
	            data: { id: 'renderer', name: 'Renderer', parent: 'ext' },
	            classes: 'ext',
	            position: { x: 350, y: 280 }
	          },
	          
	          {
	            data: { id: 'api', name: 'Core API', parent: 'cy',node_type:'query' }
	          },
	          
	          {
	            data: { id: 'app', name: 'Client' },
	            position: { x: 0, y: 480 }
	          }
	        ],
	        edges: [
	          { data: { source: 'core', target: 'eles',data_type:'Predicted' } },
	          { data: { source: 'core', target: 'ext' ,data_type:'Co-expression'} },
	          { data: { source: 'core', target: 'style' ,data_type:'Pathway'} },
	          { data: { source: 'style', target: 'selector',data_type:'Co-expression' } },
	          { data: { source: 'core', target: 'selector',data_type:'Pathway' } },
	          { data: { source: 'elesfn', target: 'eles' ,data_type:'Predicted'}},
	          { data: { source: 'corefn', target: 'core' ,data_type:'Co-expression'}},
	          { data: { source: 'layout', target: 'api' ,data_type:'Predicted'}},
	          { data: { source: 'renderer', target: 'api' ,data_type:'Pathway'}},
	          { data: { source: 'app', target: 'api', name: 'use' ,data_type:'Co-expression'} },
	          { data: { source: 'app', target: 'ext', name: 'register' ,data_type:'Pathway'}}
	        ]
	      },
	  
	  layout: {
	    name: 'concentric',
	    concentric: function(){
	      return this.data('score');
	    },
	    levelWidth: function(nodes){
	      return 0.5;
	    },
	    padding: 10
	  }
	});
	  
	}); // on dom ready
</script>
</head>
<body>
<div id="cy"></div>
</body>
</html>
