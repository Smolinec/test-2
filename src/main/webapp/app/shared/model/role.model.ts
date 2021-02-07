import { IUser } from 'app/core/user/user.model';

export interface IRole {
  id?: number;
  name?: string;
  user?: IUser;
}

export class Role implements IRole {
  constructor(public id?: number, public name?: string, public user?: IUser) {}
}
