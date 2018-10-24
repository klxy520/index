// loading

var loadCon = "<div id='container'><div id='load_canvas'></div><p class='load_describe'></p></div>";
var body = "";
var load = {
	beforeLoad:function(){
		if(!body){
			body = document.getElementsByTagName('body')[0];
		}
	},
	loading:function(str){
		load.beforeLoad();
		var load_describe = document.getElementsByClassName('load_describe')[0];
		var newText = document.createTextNode(str);
		load_describe.appendChild(newText);
	},
    loadClose:function(){
        var container = document.getElementById("container");
        container.style.display = "none";
    }
}

function loadAlert(tagId, str){
	var container=document.getElementById("container")
	if(container != null){
		$("#container").remove();
	}
	$("#"+tagId).append(loadCon);

// loading
	var loaders = [
        {
            width: 100,
            height: 100,
            stepsPerFrame: 1,
            trailLength: 1,
            pointDistance: .05,
            strokeColor: '#B3B3B3',
            fps: 20,
            setup: function() {
                this._.lineWidth = 4;
            },

            step: function(point, index) {
                var cx = this.padding + 50,
                cy = this.padding + 50,
                _ = this._,
                angle = (Math.PI / 180) * (point.progress * 360),
                innerRadius = index === 1 ? 10 : 25;
                _.beginPath();
                _.moveTo(point.x, point.y);
                _.lineTo(
	                (Math.cos(angle) * innerRadius) + cx,
	                (Math.sin(angle) * innerRadius) + cy
                );
                _.closePath();
                _.stroke();
            },

            path: [
            	['arc', 50, 50, 40, 0, 360]
            ]
        }
    ];
	var a, load_canvas = document.getElementById('load_canvas');
	var container = document.getElementById("container");
	var canvasTop=container.offsetHeight/2-40
	load_canvas.style.marginTop=canvasTop+"px"
    for (var i = -1,l = loaders.length; ++i < l;) {
        a = new Sonic(loaders[i]);
        load_canvas.appendChild(a.canvas);
         a.canvas.style.marginLeft = (load_canvas.offsetWidth - a.fullWidth) / 2 + 'px';
        a.play();
    }

	load.loading(str);
//    $("#container").css({
//        height:$(document).height()
//    })
}

