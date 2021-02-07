import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMemoryH2SharedModule } from 'app/shared/shared.module';
import { TemperatureValuesComponent } from './temperature-values.component';
import { TemperatureValuesDetailComponent } from './temperature-values-detail.component';
import { TemperatureValuesUpdateComponent } from './temperature-values-update.component';
import { TemperatureValuesDeleteDialogComponent } from './temperature-values-delete-dialog.component';
import { temperatureValuesRoute } from './temperature-values.route';

@NgModule({
  imports: [TestMemoryH2SharedModule, RouterModule.forChild(temperatureValuesRoute)],
  declarations: [
    TemperatureValuesComponent,
    TemperatureValuesDetailComponent,
    TemperatureValuesUpdateComponent,
    TemperatureValuesDeleteDialogComponent,
  ],
  entryComponents: [TemperatureValuesDeleteDialogComponent],
})
export class TestMemoryH2TemperatureValuesModule {}
