import { Component, AfterViewInit, Output, EventEmitter } from '@angular/core';
import { latLng, tileLayer } from 'leaflet';
import * as L from 'leaflet';
import 'leaflet.heat/dist/leaflet-heat.js';

@Component({
  selector: 'app-heatmap',
  templateUrl: './heatmap.component.html',
  styleUrls: ['./heatmap.component.scss']
})
export class HeatmapComponent {
  @Output()
  onClick = new EventEmitter<Array<number>>();

  points: Array<Array<number>> = [];
  map: any;

  optionsSpec: any = {
		layers: [{ url: 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', attribution: 'Open Street Map' }],
		zoom: 20,
		center: [ 47.3764, 8.5476 ],
    heatRadius: 25,
    heatBlur: 20
	};

  heatLayer: any = (L as any).heatLayer([], {radius: this.optionsSpec.heatRadius, blur: this.optionsSpec.heatBlur});

	// Leaflet bindings
	zoom = this.optionsSpec.zoom;
	center = latLng(this.optionsSpec.center);
	options = {
		layers: [ tileLayer(this.optionsSpec.layers[0].url, { attribution: this.optionsSpec.layers[0].attribution }) ],
		zoom: this.optionsSpec.zoom,
		center: latLng(this.optionsSpec.center)
	};

  onMapReady(map: any) {
    this.heatLayer.addTo(map);
    this.map = map;
    map.on('click', (e: any) => {
      this.onClick.emit(e);
    });
  }

  public add(long: number, lat: number) : void {
    let coords = [lat, long, 1.0];
    this.heatLayer.addLatLng(coords);
    this.points.push(coords);
  }

  public remove(long: number, lat: number) : void {
    for (let i = 0; i < this.points.length; i++) {
      if (this.points[i][0] == long && this.points[i][1] == lat) {
        this.points.splice(i, 1);
        this.heatLayer.setLatLngs(this.points);
        return;
      }
    }
  }
}
