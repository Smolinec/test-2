import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMemoryH2SharedModule } from 'app/shared/shared.module';
import { ValuesComponent } from './values.component';
import { ValuesDetailComponent } from './values-detail.component';
import { ValuesUpdateComponent } from './values-update.component';
import { ValuesDeleteDialogComponent } from './values-delete-dialog.component';
import { valuesRoute } from './values.route';

@NgModule({
  imports: [TestMemoryH2SharedModule, RouterModule.forChild(valuesRoute)],
  declarations: [ValuesComponent, ValuesDetailComponent, ValuesUpdateComponent, ValuesDeleteDialogComponent],
  entryComponents: [ValuesDeleteDialogComponent],
})
export class TestMemoryH2ValuesModule {}
