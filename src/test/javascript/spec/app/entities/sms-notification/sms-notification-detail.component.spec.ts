import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestMemoryH2TestModule } from '../../../test.module';
import { SMSNotificationDetailComponent } from 'app/entities/sms-notification/sms-notification-detail.component';
import { SMSNotification } from 'app/shared/model/sms-notification.model';

describe('Component Tests', () => {
  describe('SMSNotification Management Detail Component', () => {
    let comp: SMSNotificationDetailComponent;
    let fixture: ComponentFixture<SMSNotificationDetailComponent>;
    const route = ({ data: of({ sMSNotification: new SMSNotification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestMemoryH2TestModule],
        declarations: [SMSNotificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SMSNotificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SMSNotificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sMSNotification on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sMSNotification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
