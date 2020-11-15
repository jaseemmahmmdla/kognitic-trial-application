import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { BiomarkerComponent } from './biomarker.component';
import { BiomarkerDetailComponent } from './biomarker-detail.component';
import { BiomarkerUpdateComponent } from './biomarker-update.component';
import { BiomarkerDeleteDialogComponent } from './biomarker-delete-dialog.component';
import { biomarkerRoute } from './biomarker.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(biomarkerRoute)],
  declarations: [BiomarkerComponent, BiomarkerDetailComponent, BiomarkerUpdateComponent, BiomarkerDeleteDialogComponent],
  entryComponents: [BiomarkerDeleteDialogComponent],
})
export class KogniticApplicationBiomarkerModule {}
