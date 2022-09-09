import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventOverviewComponent } from './event-overview.component';

describe('EventOverviewComponent', () => {
  let component: EventOverviewComponent;
  let fixture: ComponentFixture<EventOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EventOverviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EventOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
