import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventMonitoringComponent } from './event-monitoring.component';

describe('EventMonitoringComponent', () => {
  let component: EventMonitoringComponent;
  let fixture: ComponentFixture<EventMonitoringComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventMonitoringComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventMonitoringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
