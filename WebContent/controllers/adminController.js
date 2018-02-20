'use strict';

app.controller('adminController', ['$scope','$http', 'flashService', '$window', '$rootScope',function(s,$http, FlashS, $window, rootS){

   
  s.noVisibleAdmin = false;
  s.hijosView = false;
  s.personal = [];
  s.top = 0;


var margin = {top: 20, right: 120, bottom: 20, left: 120},
    width = 800 - margin.right - margin.left,
    height = 400 - margin.top - margin.bottom;


  var svg = d3.select("svg").append("g")
    .attr("width", width + margin.right + margin.left)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


  s.getInformacion = function(){
    s.data = [];
      s.data.cod = rootS.codigoLogin;
     FlashS.peticion('GET','/get', s.data, rootS.token, rootS.tokenR).then(function (r) {
                 s.personal = r.data.lstUser;
                 s.rootInfo =  r.data.usuario;
                 s.info = r.data.usuario;
                  s.noVisibleAdmin = true;
                },
                function (error) {
                   $window.location.href = '#!/login';
                }); 

  };
//regresa
  s.viewLiderInmediato = function (){
     s.data = [];
      s.data.cod =  s.info.fk_cod_distribuido_user;
      FlashS.peticion('GET','/get', s.data, rootS.token, rootS.tokenR).then(function (r) {
                 s.personal = r.data.lstUser;
                 s.info = r.data.usuario;
                 s.hijosView = s.rootInfo.cod_distribuidor != r.data.usuario.cod_distribuidor ;
                },
                function (error) {
                   $window.location.href = '#!/login';
                }); 
  };
//suma
  s.viewModalTeam = function () {
        s.data = [];
      s.data.cod = this.p.cod_distribuidor;
      s.hijosView = true;
       FlashS.peticion('GET','/get', s.data, rootS.token, rootS.tokenR).then(function (r) {
                 s.personal = r.data.lstUser;
                 s.info = r.data.usuario;
                  s.noVisibleAdmin = true;
                },
                function (error) {
                   $window.location.href = '#!/login';
                });

  }; 


  s.createGraficChart = function (){
     $("#modalCreateApp").modal({backdrop: 'static', keyboard: false});
      $("#modalCreateApp").draggable({
     
      handle: ".modal-header"
  });
          svg.selectAll("*").remove();
        s.data = [];
      s.data.cod = s.info.cod_distribuidor;
FlashS.peticion('GET','/obtenerGrafica', s.data, rootS.token, rootS.tokenR).then(function (r) {
                s.createD3Grafic(r.data);
                },
                function (error) {
                   $window.location.href = '#!/login';
                }); 
  };

    s.cancelModal = function (){
         $('#modalCreateApp').modal('hide');
    };

 s.getInformacion ();

  
/**

Seccion para crear la grafica por cliente
*/
s.createD3Grafic = function (datosjSON){

var i = 0,
    duration = 750,
    root;

var tree = d3.layout.tree()
    .size([height, width]);

var diagonal = d3.svg.diagonal()
    .projection(function(d) { return [d.y, d.x]; });


 svg = d3.select("svg").append("g")
    .attr("width", width + margin.right + margin.left)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

//d3.json("flare.json", function(error, flare) {
//aqui van los datos;
  var flare = datosjSON;
  root = flare;
  root.x0 = height / 2;
  root.y0 = 0;

  function collapse(d) {
    if (d.children) {
      d._children = d.children;
      d._children.forEach(collapse);
      d.children = null;
    }
  }

  root.children.forEach(collapse);
  update(root);
//});

d3.select(self.frameElement).style("height", "800px");

function update(source) {

  // Compute the new tree layout.
  var nodes = tree.nodes(root).reverse(),
      links = tree.links(nodes);

  // Normalize for fixed-depth.
  nodes.forEach(function(d) { d.y = d.depth * 180; });

  // Update the nodes…
  var node = svg.selectAll("g.node")
      .data(nodes, function(d) { return d.id || (d.id = ++i); });

  // Enter any new nodes at the parent's previous position.
  var nodeEnter = node.enter().append("g")
      .attr("class", "node")
      .attr("transform", function(d) { return "translate(" + source.y0 + "," + source.x0 + ")"; })
      .on("click", click);

  nodeEnter.append("circle")
      .attr("r", 1e-6)
      .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

  nodeEnter.append("text")
      .attr("x", function(d) { return d.children || d._children ? -10 : 10; })
      .attr("dy", ".35em")
      .attr("text-anchor", function(d) { return d.children || d._children ? "end" : "start"; })
      .text(function(d) { return d.name; })
      .style("fill-opacity", 1e-6);

  // Transition nodes to their new position.
  var nodeUpdate = node.transition()
      .duration(duration)
      .attr("transform", function(d) { return "translate(" + d.y + "," + d.x + ")"; });

  nodeUpdate.select("circle")
      .attr("r", 4.5)
      .style("fill", function(d) { return d._children ? "lightsteelblue" : "#fff"; });

  nodeUpdate.select("text")
      .style("fill-opacity", 1);

  // Transition exiting nodes to the parent's new position.
  var nodeExit = node.exit().transition()
      .duration(duration)
      .attr("transform", function(d) { return "translate(" + source.y + "," + source.x + ")"; })
      .remove();

  nodeExit.select("circle")
      .attr("r", 1e-6);

  nodeExit.select("text")
      .style("fill-opacity", 1e-6);

  // Update the links…
  var link = svg.selectAll("path.link")
      .data(links, function(d) { return d.target.id; });

  // Enter any new links at the parent's previous position.
  link.enter().insert("path", "g")
      .attr("class", "link")
      .attr("d", function(d) {
        var o = {x: source.x0, y: source.y0};
        return diagonal({source: o, target: o});
      });

  // Transition links to their new position.
  link.transition()
      .duration(duration)
      .attr("d", diagonal);

  // Transition exiting nodes to the parent's new position.
  link.exit().transition()
      .duration(duration)
      .attr("d", function(d) {
        var o = {x: source.x, y: source.y};
        return diagonal({source: o, target: o});
      })
      .remove();

  // Stash the old positions for transition.
  nodes.forEach(function(d) {
    d.x0 = d.x;
    d.y0 = d.y;
  });
}

// Toggle children on click.
function click(d) {
  if (d.children) {
    d._children = d.children;
    d.children = null;
  } else {
    d.children = d._children;
    d._children = null;
  }
  update(d);
}


function expand(d){   
    var children = (d.children)?d.children:d._children;
    if (d._children) {        
        d.children = d._children;
        d._children = null;       
    }
    if(children)
      children.forEach(expand);
}

function expandAll(){
    expand(root); 
    update(root);
}
expandAll();

  };

}]);