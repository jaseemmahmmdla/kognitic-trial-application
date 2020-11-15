import { IIndication } from 'app/shared/model/indication.model';
import { IBiomarker } from 'app/shared/model/biomarker.model';

export interface ITrial {
  id?: number;
  trialId?: string;
  trialName?: string;
  indications?: IIndication[];
  biomarkers?: IBiomarker[];
}

export class Trial implements ITrial {
  constructor(
    public id?: number,
    public trialId?: string,
    public trialName?: string,
    public indications?: IIndication[],
    public biomarkers?: IBiomarker[]
  ) {}
}
