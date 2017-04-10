$(document).ready(function(){
	var loc = new google.maps.LatLng(37.584712, 126.924808);
	var map = new google.maps.Map(document.getElementById('map_canvas'), {
	    zoom: 17,
	      center: loc
	      ,mapTypeId: google.maps.MapTypeId.ROADMAP
	      ,draggable: true
	      ,disableDefaultUI: true
	      ,disableDoubleClickZoom: true
	      ,draggableCursor: false
	      ,keyboardShortcuts: false
	      ,scrollwheel:true
	    });
	var marker =  new google.maps.Marker({
        position: loc
        ,map: map
        ,animation: google.maps.Animation.DROP
        ,icon: "img/marker.png"
       })
	
});