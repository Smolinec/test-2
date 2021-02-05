import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMemoryH2SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [TestMemoryH2SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class TestMemoryH2HomeModule {}
