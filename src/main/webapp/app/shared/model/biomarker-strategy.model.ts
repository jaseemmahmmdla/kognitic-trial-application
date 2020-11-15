import { IBiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';
import { IBiomarker } from 'app/shared/model/biomarker.model';

export interface IBiomarkerStrategy {
  id?: number;
  biomarkerStrategy?: string;
  biomarkerMutations?: IBiomarkerMutation[];
  biomarker?: IBiomarker;
}

export class BiomarkerStrategy implements IBiomarkerStrategy {
  constructor(
    public id?: number,
    public biomarkerStrategy?: string,
    public biomarkerMutations?: IBiomarkerMutation[],
    public biomarker?: IBiomarker
  ) {}
}
