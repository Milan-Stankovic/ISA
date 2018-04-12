(function() {
    'use strict';
    
    angular
	.module('app')
	.directive('myMaps', myMaps);
    
    myMaps.$inject = ['$parse'];
	
    function myMaps($parse) {
        return {
        	restrict:'E', //Element Type
            template:'<div></div>', //Defining myApp div
            replace:true, //Allowing replacing
            link: function(scope, element, attributes){

                //Initializing Coordinates
                var myLatLng = new google.maps.LatLng(YourLat,YourLong); 
                var mapOptions = {
                    center: myLatLng, //Center of our map based on LatLong Coordinates
                    zoom: 5, //How much we want to initialize our zoom in the map
                    //Map type, you can check them in APIs documentation
                    mapTypeId: google.maps.MapTypeId.ROADMAP 
                    };

                //Attaching our features & options to the map
                var map = new google.maps.Map(document.getElementById(attributes.id),
                              mapOptions);
                //Putting a marker on the center
                var marker = new google.maps.Marker({
                    position: myLatLng,
                    map: map,
                    title:"My town"
                });
                marker.setMap(map); //Setting the marker up
            }
        };
    }
})();