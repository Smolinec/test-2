import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWebUser } from 'app/shared/model/web-user.model';
import { WebUserService } from './web-user.service';
import { WebUserDeleteDialogComponent } from './web-user-delete-dialog.component';

@Component({
  selector: 'jhi-web-user',
  templateUrl: './web-user.component.html',
})
export class WebUserComponent implements OnInit, OnDestroy {
  webUsers?: IWebUser[];
  eventSubscriber?: Subscription;

  constructor(protected webUserService: WebUserService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.webUserService.query().subscribe((res: HttpResponse<IWebUser[]>) => (this.webUsers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWebUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWebUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWebUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('webUserListModification', () => this.loadAll());
  }

  delete(webUser: IWebUser): void {
    const modalRef = this.modalService.open(WebUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.webUser = webUser;
  }
}
