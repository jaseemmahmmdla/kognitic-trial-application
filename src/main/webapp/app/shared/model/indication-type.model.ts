import { IIndication } from 'app/shared/model/indication.model';

export interface IIndicationType {
  id?: number;
  indicationType?: string;
  indication?: IIndication;
}

export class IndicationType implements IIndicationType {
  constructor(public id?: number, public indicationType?: string, public indication?: IIndication) {}
}
