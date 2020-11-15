import { IBiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';
import { ITrial } from 'app/shared/model/trial.model';

export interface IBiomarker {
  id?: number;
  biomarker?: string;
  biomarkerStrategies?: IBiomarkerStrategy[];
  trial?: ITrial;
}

export class Biomarker implements IBiomarker {
  constructor(public id?: number, public biomarker?: string, public biomarkerStrategies?: IBiomarkerStrategy[], public trial?: ITrial) {}
}
