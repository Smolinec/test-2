import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDevice } from 'app/shared/model/device.model';
import { DeviceService } from './device.service';
import { DeviceDeleteDialogComponent } from './device-delete-dialog.component';

@Component({
  selector: 'jhi-device',
  templateUrl: './device.component.html',
})
export class DeviceComponent implements OnInit, OnDestroy {
  devices?: IDevice[];
  eventSubscriber?: Subscription;

  constructor(protected deviceService: DeviceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.deviceService.query().subscribe((res: HttpResponse<IDevice[]>) => (this.devices = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDevices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDevice): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDevices(): void {
    this.eventSubscriber = this.eventManager.subscribe('deviceListModification', () => this.loadAll());
  }

  delete(device: IDevice): void {
    const modalRef = this.modalService.open(DeviceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.device = device;
  }
}
