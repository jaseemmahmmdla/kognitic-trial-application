import { IIndication } from 'app/shared/model/indication.model';

export interface ILineOfTherapy {
  id?: number;
  lot?: string;
  indication?: IIndication;
}

export class LineOfTherapy implements ILineOfTherapy {
  constructor(public id?: number, public lot?: string, public indication?: IIndication) {}
}
