import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IPushNotificationToken {
  id?: number;
  token?: string;
  timestamp?: Moment;
  users?: IUser[];
}

export class PushNotificationToken implements IPushNotificationToken {
  constructor(public id?: number, public token?: string, public timestamp?: Moment, public users?: IUser[]) {}
}
