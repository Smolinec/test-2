import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TestMemoryH2TestModule } from '../../../test.module';
import { TemperatureValuesDetailComponent } from 'app/entities/temperature-values/temperature-values-detail.component';
import { TemperatureValues } from 'app/shared/model/temperature-values.model';

describe('Component Tests', () => {
  describe('TemperatureValues Management Detail Component', () => {
    let comp: TemperatureValuesDetailComponent;
    let fixture: ComponentFixture<TemperatureValuesDetailComponent>;
    const route = ({ data: of({ temperatureValues: new TemperatureValues(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestMemoryH2TestModule],
        declarations: [TemperatureValuesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TemperatureValuesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TemperatureValuesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load temperatureValues on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.temperatureValues).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
