import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITemperature } from 'app/shared/model/temperature.model';
import { TemperatureService } from './temperature.service';
import { TemperatureDeleteDialogComponent } from './temperature-delete-dialog.component';

@Component({
  selector: 'jhi-temperature',
  templateUrl: './temperature.component.html',
})
export class TemperatureComponent implements OnInit, OnDestroy {
  temperatures?: ITemperature[];
  eventSubscriber?: Subscription;

  constructor(
    protected temperatureService: TemperatureService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.temperatureService.query().subscribe((res: HttpResponse<ITemperature[]>) => (this.temperatures = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTemperatures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITemperature): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTemperatures(): void {
    this.eventSubscriber = this.eventManager.subscribe('temperatureListModification', () => this.loadAll());
  }

  delete(temperature: ITemperature): void {
    const modalRef = this.modalService.open(TemperatureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.temperature = temperature;
  }
}
