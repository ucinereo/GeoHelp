import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EventRegistrationComponent } from './event-registration/event-registration.component';
import { EventMonitoringComponent } from './event-monitoring/event-monitoring.component';
import { EventOverviewComponent } from './event-overview/event-overview.component';
import { LocationSelectorComponent } from './location-selector/location-selector.component';
import { HeatmapComponent } from './heatmap/heatmap.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { LeafletDrawModule } from '@asymmetrik/ngx-leaflet-draw';
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { QRCodeModule } from 'angularx-qrcode';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    EventRegistrationComponent,
    EventMonitoringComponent,
    EventOverviewComponent,
    LocationSelectorComponent,
    HeatmapComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    LeafletModule,
    LeafletDrawModule,
    QRCodeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
