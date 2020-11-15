import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { TrialComponent } from './trial.component';
import { TrialDetailComponent } from './trial-detail.component';
import { TrialUpdateComponent } from './trial-update.component';
import { TrialDeleteDialogComponent } from './trial-delete-dialog.component';
import { trialRoute } from './trial.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(trialRoute)],
  declarations: [TrialComponent, TrialDetailComponent, TrialUpdateComponent, TrialDeleteDialogComponent],
  entryComponents: [TrialDeleteDialogComponent],
})
export class KogniticApplicationTrialModule {}
