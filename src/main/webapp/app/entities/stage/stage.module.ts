import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { StageComponent } from './stage.component';
import { StageDetailComponent } from './stage-detail.component';
import { StageUpdateComponent } from './stage-update.component';
import { StageDeleteDialogComponent } from './stage-delete-dialog.component';
import { stageRoute } from './stage.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(stageRoute)],
  declarations: [StageComponent, StageDetailComponent, StageUpdateComponent, StageDeleteDialogComponent],
  entryComponents: [StageDeleteDialogComponent],
})
export class KogniticApplicationStageModule {}
