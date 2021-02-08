import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TestMemoryH2TestModule } from '../../../test.module';
import { TemperatureValuesComponent } from 'app/entities/temperature-values/temperature-values.component';
import { TemperatureValuesService } from 'app/entities/temperature-values/temperature-values.service';
import { TemperatureValues } from 'app/shared/model/temperature-values.model';

describe('Component Tests', () => {
  describe('TemperatureValues Management Component', () => {
    let comp: TemperatureValuesComponent;
    let fixture: ComponentFixture<TemperatureValuesComponent>;
    let service: TemperatureValuesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestMemoryH2TestModule],
        declarations: [TemperatureValuesComponent],
      })
        .overrideTemplate(TemperatureValuesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TemperatureValuesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TemperatureValuesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TemperatureValues(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.temperatureValues && comp.temperatureValues[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
