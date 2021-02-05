import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISMSNotification } from 'app/shared/model/sms-notification.model';
import { SMSNotificationService } from './sms-notification.service';
import { SMSNotificationDeleteDialogComponent } from './sms-notification-delete-dialog.component';

@Component({
  selector: 'jhi-sms-notification',
  templateUrl: './sms-notification.component.html',
})
export class SMSNotificationComponent implements OnInit, OnDestroy {
  sMSNotifications?: ISMSNotification[];
  eventSubscriber?: Subscription;

  constructor(
    protected sMSNotificationService: SMSNotificationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.sMSNotificationService.query().subscribe((res: HttpResponse<ISMSNotification[]>) => (this.sMSNotifications = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSMSNotifications();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISMSNotification): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSMSNotifications(): void {
    this.eventSubscriber = this.eventManager.subscribe('sMSNotificationListModification', () => this.loadAll());
  }

  delete(sMSNotification: ISMSNotification): void {
    const modalRef = this.modalService.open(SMSNotificationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sMSNotification = sMSNotification;
  }
}
