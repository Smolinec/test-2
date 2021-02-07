import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TestMemoryH2TestModule } from '../../../test.module';
import { TemperatureValuesUpdateComponent } from 'app/entities/temperature-values/temperature-values-update.component';
import { TemperatureValuesService } from 'app/entities/temperature-values/temperature-values.service';
import { TemperatureValues } from 'app/shared/model/temperature-values.model';

describe('Component Tests', () => {
  describe('TemperatureValues Management Update Component', () => {
    let comp: TemperatureValuesUpdateComponent;
    let fixture: ComponentFixture<TemperatureValuesUpdateComponent>;
    let service: TemperatureValuesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TestMemoryH2TestModule],
        declarations: [TemperatureValuesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TemperatureValuesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TemperatureValuesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TemperatureValuesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TemperatureValues(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TemperatureValues();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
