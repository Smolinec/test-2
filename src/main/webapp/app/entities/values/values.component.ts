import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IValues } from 'app/shared/model/values.model';
import { ValuesService } from './values.service';
import { ValuesDeleteDialogComponent } from './values-delete-dialog.component';

@Component({
  selector: 'jhi-values',
  templateUrl: './values.component.html',
})
export class ValuesComponent implements OnInit, OnDestroy {
  values?: IValues[];
  eventSubscriber?: Subscription;

  constructor(protected valuesService: ValuesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.valuesService.query().subscribe((res: HttpResponse<IValues[]>) => (this.values = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInValues();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IValues): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInValues(): void {
    this.eventSubscriber = this.eventManager.subscribe('valuesListModification', () => this.loadAll());
  }

  delete(values: IValues): void {
    const modalRef = this.modalService.open(ValuesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.values = values;
  }
}
