import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmileIconComponent } from './smile-icon.component';

describe('SmileIconComponent', () => {
  let component: SmileIconComponent;
  let fixture: ComponentFixture<SmileIconComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SmileIconComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SmileIconComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
