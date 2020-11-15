import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { IndicationComponent } from './indication.component';
import { IndicationDetailComponent } from './indication-detail.component';
import { IndicationUpdateComponent } from './indication-update.component';
import { IndicationDeleteDialogComponent } from './indication-delete-dialog.component';
import { indicationRoute } from './indication.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(indicationRoute)],
  declarations: [IndicationComponent, IndicationDetailComponent, IndicationUpdateComponent, IndicationDeleteDialogComponent],
  entryComponents: [IndicationDeleteDialogComponent],
})
export class KogniticApplicationIndicationModule {}
