import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'place',
        loadChildren: () => import('./place/place.module').then(m => m.TestMemoryH2PlaceModule),
      },
      {
        path: 'device',
        loadChildren: () => import('./device/device.module').then(m => m.TestMemoryH2DeviceModule),
      },
      {
        path: 'temperature',
        loadChildren: () => import('./temperature/temperature.module').then(m => m.TestMemoryH2TemperatureModule),
      },
      {
        path: 'values',
        loadChildren: () => import('./values/values.module').then(m => m.TestMemoryH2ValuesModule),
      },
      {
        path: 'web-user',
        loadChildren: () => import('./web-user/web-user.module').then(m => m.TestMemoryH2WebUserModule),
      },
      {
        path: 'role',
        loadChildren: () => import('./role/role.module').then(m => m.TestMemoryH2RoleModule),
      },
      {
        path: 'push-notification-token',
        loadChildren: () =>
          import('./push-notification-token/push-notification-token.module').then(m => m.TestMemoryH2PushNotificationTokenModule),
      },
      {
        path: 'application',
        loadChildren: () => import('./application/application.module').then(m => m.TestMemoryH2ApplicationModule),
      },
      {
        path: 'device-profile',
        loadChildren: () => import('./device-profile/device-profile.module').then(m => m.TestMemoryH2DeviceProfileModule),
      },
      {
        path: 'device-configuration',
        loadChildren: () => import('./device-configuration/device-configuration.module').then(m => m.TestMemoryH2DeviceConfigurationModule),
      },
      {
        path: 'sms-notification',
        loadChildren: () => import('./sms-notification/sms-notification.module').then(m => m.TestMemoryH2SMSNotificationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TestMemoryH2EntityModule {}
