import { IDevice } from 'app/shared/model/device.model';

export interface IDeviceProfile {
  id?: number;
  name?: string;
  devices?: IDevice[];
}

export class DeviceProfile implements IDeviceProfile {
  constructor(public id?: number, public name?: string, public devices?: IDevice[]) {}
}
