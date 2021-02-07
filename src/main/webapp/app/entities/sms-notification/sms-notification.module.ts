import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMemoryH2SharedModule } from 'app/shared/shared.module';
import { SMSNotificationComponent } from './sms-notification.component';
import { SMSNotificationDetailComponent } from './sms-notification-detail.component';
import { SMSNotificationUpdateComponent } from './sms-notification-update.component';
import { SMSNotificationDeleteDialogComponent } from './sms-notification-delete-dialog.component';
import { sMSNotificationRoute } from './sms-notification.route';

@NgModule({
  imports: [TestMemoryH2SharedModule, RouterModule.forChild(sMSNotificationRoute)],
  declarations: [
    SMSNotificationComponent,
    SMSNotificationDetailComponent,
    SMSNotificationUpdateComponent,
    SMSNotificationDeleteDialogComponent,
  ],
  entryComponents: [SMSNotificationDeleteDialogComponent],
})
export class TestMemoryH2SMSNotificationModule {}
