import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITemperatureValues, TemperatureValues } from 'app/shared/model/temperature-values.model';
import { TemperatureValuesService } from './temperature-values.service';
import { ITemperature } from 'app/shared/model/temperature.model';
import { TemperatureService } from 'app/entities/temperature/temperature.service';

@Component({
  selector: 'jhi-temperature-values-update',
  templateUrl: './temperature-values-update.component.html',
})
export class TemperatureValuesUpdateComponent implements OnInit {
  isSaving = false;
  temperatures: ITemperature[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    timestamp: [],
    temperature: [],
    temperatures: [],
  });

  constructor(
    protected temperatureValuesService: TemperatureValuesService,
    protected temperatureService: TemperatureService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ temperatureValues }) => {
      if (!temperatureValues.id) {
        const today = moment().startOf('day');
        temperatureValues.timestamp = today;
      }

      this.updateForm(temperatureValues);

      this.temperatureService.query().subscribe((res: HttpResponse<ITemperature[]>) => (this.temperatures = res.body || []));
    });
  }

  updateForm(temperatureValues: ITemperatureValues): void {
    this.editForm.patchValue({
      id: temperatureValues.id,
      value: temperatureValues.value,
      timestamp: temperatureValues.timestamp ? temperatureValues.timestamp.format(DATE_TIME_FORMAT) : null,
      temperature: temperatureValues.temperature,
      temperatures: temperatureValues.temperatures,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const temperatureValues = this.createFromForm();
    if (temperatureValues.id !== undefined) {
      this.subscribeToSaveResponse(this.temperatureValuesService.update(temperatureValues));
    } else {
      this.subscribeToSaveResponse(this.temperatureValuesService.create(temperatureValues));
    }
  }

  private createFromForm(): ITemperatureValues {
    return {
      ...new TemperatureValues(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      timestamp: this.editForm.get(['timestamp'])!.value ? moment(this.editForm.get(['timestamp'])!.value, DATE_TIME_FORMAT) : undefined,
      temperature: this.editForm.get(['temperature'])!.value,
      temperatures: this.editForm.get(['temperatures'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITemperatureValues>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITemperature): any {
    return item.id;
  }

  getSelected(selectedVals: ITemperature[], option: ITemperature): ITemperature {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
