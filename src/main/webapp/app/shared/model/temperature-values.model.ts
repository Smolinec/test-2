import { Moment } from 'moment';
import { ITemperature } from 'app/shared/model/temperature.model';

export interface ITemperatureValues {
  id?: number;
  value?: number;
  timestamp?: Moment;
  temperature?: ITemperature;
  temperatures?: ITemperature[];
}

export class TemperatureValues implements ITemperatureValues {
  constructor(
    public id?: number,
    public value?: number,
    public timestamp?: Moment,
    public temperature?: ITemperature,
    public temperatures?: ITemperature[]
  ) {}
}
