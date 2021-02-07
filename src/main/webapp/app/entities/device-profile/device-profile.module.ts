import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TestMemoryH2SharedModule } from 'app/shared/shared.module';
import { DeviceProfileComponent } from './device-profile.component';
import { DeviceProfileDetailComponent } from './device-profile-detail.component';
import { DeviceProfileUpdateComponent } from './device-profile-update.component';
import { DeviceProfileDeleteDialogComponent } from './device-profile-delete-dialog.component';
import { deviceProfileRoute } from './device-profile.route';

@NgModule({
  imports: [TestMemoryH2SharedModule, RouterModule.forChild(deviceProfileRoute)],
  declarations: [DeviceProfileComponent, DeviceProfileDetailComponent, DeviceProfileUpdateComponent, DeviceProfileDeleteDialogComponent],
  entryComponents: [DeviceProfileDeleteDialogComponent],
})
export class TestMemoryH2DeviceProfileModule {}
