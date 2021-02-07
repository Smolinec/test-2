import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITemperatureValues } from 'app/shared/model/temperature-values.model';
import { TemperatureValuesService } from './temperature-values.service';
import { TemperatureValuesDeleteDialogComponent } from './temperature-values-delete-dialog.component';

@Component({
  selector: 'jhi-temperature-values',
  templateUrl: './temperature-values.component.html',
})
export class TemperatureValuesComponent implements OnInit, OnDestroy {
  temperatureValues?: ITemperatureValues[];
  eventSubscriber?: Subscription;

  constructor(
    protected temperatureValuesService: TemperatureValuesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.temperatureValuesService.query().subscribe((res: HttpResponse<ITemperatureValues[]>) => (this.temperatureValues = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTemperatureValues();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITemperatureValues): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTemperatureValues(): void {
    this.eventSubscriber = this.eventManager.subscribe('temperatureValuesListModification', () => this.loadAll());
  }

  delete(temperatureValues: ITemperatureValues): void {
    const modalRef = this.modalService.open(TemperatureValuesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.temperatureValues = temperatureValues;
  }
}
