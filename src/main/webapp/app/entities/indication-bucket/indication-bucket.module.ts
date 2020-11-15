import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { IndicationBucketComponent } from './indication-bucket.component';
import { IndicationBucketDetailComponent } from './indication-bucket-detail.component';
import { IndicationBucketUpdateComponent } from './indication-bucket-update.component';
import { IndicationBucketDeleteDialogComponent } from './indication-bucket-delete-dialog.component';
import { indicationBucketRoute } from './indication-bucket.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(indicationBucketRoute)],
  declarations: [
    IndicationBucketComponent,
    IndicationBucketDetailComponent,
    IndicationBucketUpdateComponent,
    IndicationBucketDeleteDialogComponent,
  ],
  entryComponents: [IndicationBucketDeleteDialogComponent],
})
export class KogniticApplicationIndicationBucketModule {}
