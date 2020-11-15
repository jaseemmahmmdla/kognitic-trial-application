import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { IndicationTypeComponent } from './indication-type.component';
import { IndicationTypeDetailComponent } from './indication-type-detail.component';
import { IndicationTypeUpdateComponent } from './indication-type-update.component';
import { IndicationTypeDeleteDialogComponent } from './indication-type-delete-dialog.component';
import { indicationTypeRoute } from './indication-type.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(indicationTypeRoute)],
  declarations: [
    IndicationTypeComponent,
    IndicationTypeDetailComponent,
    IndicationTypeUpdateComponent,
    IndicationTypeDeleteDialogComponent,
  ],
  entryComponents: [IndicationTypeDeleteDialogComponent],
})
export class KogniticApplicationIndicationTypeModule {}
