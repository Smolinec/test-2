import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMemoryH2SharedModule } from 'app/shared/shared.module';
import { WebUserComponent } from './web-user.component';
import { WebUserDetailComponent } from './web-user-detail.component';
import { WebUserUpdateComponent } from './web-user-update.component';
import { WebUserDeleteDialogComponent } from './web-user-delete-dialog.component';
import { webUserRoute } from './web-user.route';

@NgModule({
  imports: [TestMemoryH2SharedModule, RouterModule.forChild(webUserRoute)],
  declarations: [WebUserComponent, WebUserDetailComponent, WebUserUpdateComponent, WebUserDeleteDialogComponent],
  entryComponents: [WebUserDeleteDialogComponent],
})
export class TestMemoryH2WebUserModule {}
