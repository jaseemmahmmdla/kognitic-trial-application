import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { BiomarkerStrategyComponent } from './biomarker-strategy.component';
import { BiomarkerStrategyDetailComponent } from './biomarker-strategy-detail.component';
import { BiomarkerStrategyUpdateComponent } from './biomarker-strategy-update.component';
import { BiomarkerStrategyDeleteDialogComponent } from './biomarker-strategy-delete-dialog.component';
import { biomarkerStrategyRoute } from './biomarker-strategy.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(biomarkerStrategyRoute)],
  declarations: [
    BiomarkerStrategyComponent,
    BiomarkerStrategyDetailComponent,
    BiomarkerStrategyUpdateComponent,
    BiomarkerStrategyDeleteDialogComponent,
  ],
  entryComponents: [BiomarkerStrategyDeleteDialogComponent],
})
export class KogniticApplicationBiomarkerStrategyModule {}
