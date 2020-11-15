import { IIndication } from 'app/shared/model/indication.model';

export interface IStage {
  id?: number;
  stage?: string;
  indication?: IIndication;
}

export class Stage implements IStage {
  constructor(public id?: number, public stage?: string, public indication?: IIndication) {}
}
