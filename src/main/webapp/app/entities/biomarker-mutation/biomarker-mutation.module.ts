import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { BiomarkerMutationComponent } from './biomarker-mutation.component';
import { BiomarkerMutationDetailComponent } from './biomarker-mutation-detail.component';
import { BiomarkerMutationUpdateComponent } from './biomarker-mutation-update.component';
import { BiomarkerMutationDeleteDialogComponent } from './biomarker-mutation-delete-dialog.component';
import { biomarkerMutationRoute } from './biomarker-mutation.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(biomarkerMutationRoute)],
  declarations: [
    BiomarkerMutationComponent,
    BiomarkerMutationDetailComponent,
    BiomarkerMutationUpdateComponent,
    BiomarkerMutationDeleteDialogComponent,
  ],
  entryComponents: [BiomarkerMutationDeleteDialogComponent],
})
export class KogniticApplicationBiomarkerMutationModule {}
