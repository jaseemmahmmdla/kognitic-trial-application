import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KogniticApplicationSharedModule } from 'app/shared/shared.module';
import { LineOfTherapyComponent } from './line-of-therapy.component';
import { LineOfTherapyDetailComponent } from './line-of-therapy-detail.component';
import { LineOfTherapyUpdateComponent } from './line-of-therapy-update.component';
import { LineOfTherapyDeleteDialogComponent } from './line-of-therapy-delete-dialog.component';
import { lineOfTherapyRoute } from './line-of-therapy.route';

@NgModule({
  imports: [KogniticApplicationSharedModule, RouterModule.forChild(lineOfTherapyRoute)],
  declarations: [LineOfTherapyComponent, LineOfTherapyDetailComponent, LineOfTherapyUpdateComponent, LineOfTherapyDeleteDialogComponent],
  entryComponents: [LineOfTherapyDeleteDialogComponent],
})
export class KogniticApplicationLineOfTherapyModule {}
