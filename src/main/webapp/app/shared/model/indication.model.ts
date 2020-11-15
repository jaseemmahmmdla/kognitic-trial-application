import { IStage } from 'app/shared/model/stage.model';
import { IIndicationType } from 'app/shared/model/indication-type.model';
import { IIndicationBucket } from 'app/shared/model/indication-bucket.model';
import { ILineOfTherapy } from 'app/shared/model/line-of-therapy.model';
import { ITrial } from 'app/shared/model/trial.model';

export interface IIndication {
  id?: number;
  indication?: string;
  stages?: IStage[];
  indicationTypes?: IIndicationType[];
  indicationBuckets?: IIndicationBucket[];
  lineOfTherapies?: ILineOfTherapy[];
  trial?: ITrial;
}

export class Indication implements IIndication {
  constructor(
    public id?: number,
    public indication?: string,
    public stages?: IStage[],
    public indicationTypes?: IIndicationType[],
    public indicationBuckets?: IIndicationBucket[],
    public lineOfTherapies?: ILineOfTherapy[],
    public trial?: ITrial
  ) {}
}
