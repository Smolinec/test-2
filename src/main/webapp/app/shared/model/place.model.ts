import { IUser } from 'app/core/user/user.model';

export interface IPlace {
  id?: number;
  name?: string;
  user?: IUser;
}

export class Place implements IPlace {
  constructor(public id?: number, public name?: string, public user?: IUser) {}
}
