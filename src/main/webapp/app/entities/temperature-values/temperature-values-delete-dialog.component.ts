import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITemperatureValues } from 'app/shared/model/temperature-values.model';
import { TemperatureValuesService } from './temperature-values.service';

@Component({
  templateUrl: './temperature-values-delete-dialog.component.html',
})
export class TemperatureValuesDeleteDialogComponent {
  temperatureValues?: ITemperatureValues;

  constructor(
    protected temperatureValuesService: TemperatureValuesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.temperatureValuesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('temperatureValuesListModification');
      this.activeModal.close();
    });
  }
}
