import { IBiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';

export interface IBiomarkerMutation {
  id?: number;
  biomarkerMutation?: string;
  biomarkerStrategy?: IBiomarkerStrategy;
}

export class BiomarkerMutation implements IBiomarkerMutation {
  constructor(public id?: number, public biomarkerMutation?: string, public biomarkerStrategy?: IBiomarkerStrategy) {}
}
