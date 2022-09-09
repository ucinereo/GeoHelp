import { Component, Output, EventEmitter } from '@angular/core';
import { latLng, tileLayer, FeatureGroup, featureGroup } from 'leaflet';

@Component({
  selector: 'app-location-selector',
  templateUrl: './location-selector.component.html',
  styleUrls: ['./location-selector.component.scss']
})
export class LocationSelectorComponent {
  @Output()
  onCreate = new EventEmitter<Array<number>>();

  map: any;
  toolbar: any;
  // variable to either show the create or delete button
  drawEnabled = true;

  optionsSpec: any = {
    layers: [{ url: 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', attribution: 'Open Street Map' }],
    zoom: 20,
    center: [47.3764, 8.5476],
  };

  // Leaflet bindings
  zoom = this.optionsSpec.zoom;
  center = latLng(this.optionsSpec.center);
  options = {
    layers: [tileLayer(this.optionsSpec.layers[0].url, { attribution: this.optionsSpec.layers[0].attribution })],
    zoom: this.optionsSpec.zoom,
    center: latLng(this.optionsSpec.center)
  };

  onMapReady(map: any) {
    this.map = map;
  }

  drawnItems: FeatureGroup = featureGroup();
  // options to show the create button
  drawOptionsFull: any = {
    draw: {
        marker: false,
        circle: false,
        rectangle: { 
          showArea: false,
          shapeOptions: {
            color: '#e056fd'
          }
        }, // disable showArea
        polyline: false,
        polygon: false,
        circlemarker: false,
    },
    edit: {
      featureGroup: this.drawnItems,
      edit: false,
      remove: false,
      poly: true,
    }
  };
  // options to show the delete button
  drawOptionsEditOnly: any = {
    draw: false,
    edit: {
      featureGroup: this.drawnItems,
      edit: false,
      remove: true
    }
  };

  public onDrawCreated(e: any) {
    this.drawnItems.addLayer(e.layer);
    this.drawEnabled = false;
    // get the upper left and lower right coordinates
    let coordinates = e.layer.getLatLngs()[0];
    coordinates = [coordinates[0], coordinates[2]]
    this.onCreate.emit(coordinates);
  }

  public onDrawDeleted(e: any) {
    if (this.drawnItems.getLayers().length === 0) {
      this.drawEnabled = true;
    }
  }
}
