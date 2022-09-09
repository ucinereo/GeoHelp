import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardComponent} from "./dashboard/dashboard.component";
import {EventRegistrationComponent} from "./event-registration/event-registration.component";
import {EventOverviewComponent} from "./event-overview/event-overview.component";
import {EventMonitoringComponent} from "./event-monitoring/event-monitoring.component";

// defining routes for the website
const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'register-event', component: EventRegistrationComponent },
  { path: 'event-overview', component: EventOverviewComponent },
  { path: 'event-monitoring', component: EventMonitoringComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
