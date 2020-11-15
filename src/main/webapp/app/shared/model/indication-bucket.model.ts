import { IIndication } from 'app/shared/model/indication.model';

export interface IIndicationBucket {
  id?: number;
  indicationBucket?: string;
  indication?: IIndication;
}

export class IndicationBucket implements IIndicationBucket {
  constructor(public id?: number, public indicationBucket?: string, public indication?: IIndication) {}
}
