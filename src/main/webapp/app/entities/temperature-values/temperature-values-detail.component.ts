import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITemperatureValues } from 'app/shared/model/temperature-values.model';

@Component({
  selector: 'jhi-temperature-values-detail',
  templateUrl: './temperature-values-detail.component.html',
})
export class TemperatureValuesDetailComponent implements OnInit {
  temperatureValues: ITemperatureValues | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ temperatureValues }) => (this.temperatureValues = temperatureValues));
  }

  previousState(): void {
    window.history.back();
  }
}
