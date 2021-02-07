import { Moment } from 'moment';
import { IDevice } from 'app/shared/model/device.model';
import { ITemperatureValues } from 'app/shared/model/temperature-values.model';

export interface ITemperature {
  id?: number;
  name?: string;
  address?: string;
  createTimestamp?: Moment;
  lastUpdateTimestamp?: Moment;
  device?: IDevice;
  temperatureValues?: ITemperatureValues[];
}

export class Temperature implements ITemperature {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public createTimestamp?: Moment,
    public lastUpdateTimestamp?: Moment,
    public device?: IDevice,
    public temperatureValues?: ITemperatureValues[]
  ) {}
}
