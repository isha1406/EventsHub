import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebEventsComponent } from './web-events.component';

describe('WebEventsComponent', () => {
  let component: WebEventsComponent;
  let fixture: ComponentFixture<WebEventsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WebEventsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WebEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
