<template>
  <div ref="map-root"
       style="width: 100%; height: 500px;">
  </div>
</template>

<script>
  import View from 'ol/View'
  import Map from 'ol/Map'
  import TileLayer from 'ol/layer/Tile'
  import OSM from 'ol/source/OSM'
  import Feature from 'ol/Feature'
  import Point from 'ol/geom/Point'
  import * as proj from 'ol/proj'
  import Style from 'ol/style/Style'
  import Icon from 'ol/style/Icon'
  import * as layer from 'ol/layer'
  import Vector from 'ol/source/Vector'
  

  // importing the OpenLayers stylesheet is required for having
  // good looking buttons!
  import 'ol/ol.css'

  export default {
    name: 'MapContainer',
    components: {},
    props: {
        coordinates: Array,
    },

    data() {
        return {
            lon: null,
            lat: null,
            map: null,
            markerFeature: null,
            
        }
    },

    mounted() {
        setTimeout(this.initMap, 200);
    },

   

    methods: {
        initMap() {
            console.log('alo');
            console.log(this.coordinates);
           
            this.markerFeature = new Feature({
                geometry: new Point(proj.fromLonLat([this.coordinates[0], this.coordinates[1]])),
            });

            this.markerFeature.setStyle(new Style({
                image: new Icon({
                    scale: 1,
                    src: 'https://openlayers.org/en/latest/examples/data/icon.png'
                })
            }));

            var vectorLayer = new layer.Vector({
                source: new Vector({
                    features: [this.markerFeature],
					wrapX: true,
                }),
				wrapX: false,
            });

            console.log(this.lon, this.lat);
            
            const center = proj.transform(this.coordinates, 'EPSG:4326', 'EPSG:3857');

            // this is where we create the OpenLayers map
            new Map({
            // the map will be created using the 'map-root' ref
                target: this.$refs['map-root'],
                layers: [
                // adding a background tiled layer
                new TileLayer({
                    source: new OSM({wrapX:false}) // tiles are served by OpenStreetMap
                }),
                vectorLayer
                ],

                // the map view will initially show the whole world
                
                view: new View({
                zoom: 17,
                center: center,
                    }),
            });
        
    },

    
    },
  }
</script>

<style scoped>

</style>