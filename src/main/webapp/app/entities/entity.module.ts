import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'trial',
        loadChildren: () => import('./trial/trial.module').then(m => m.KogniticApplicationTrialModule),
      },
      {
        path: 'indication',
        loadChildren: () => import('./indication/indication.module').then(m => m.KogniticApplicationIndicationModule),
      },
      {
        path: 'indication-type',
        loadChildren: () => import('./indication-type/indication-type.module').then(m => m.KogniticApplicationIndicationTypeModule),
      },
      {
        path: 'indication-bucket',
        loadChildren: () => import('./indication-bucket/indication-bucket.module').then(m => m.KogniticApplicationIndicationBucketModule),
      },
      {
        path: 'biomarker',
        loadChildren: () => import('./biomarker/biomarker.module').then(m => m.KogniticApplicationBiomarkerModule),
      },
      {
        path: 'biomarker-strategy',
        loadChildren: () =>
          import('./biomarker-strategy/biomarker-strategy.module').then(m => m.KogniticApplicationBiomarkerStrategyModule),
      },
      {
        path: 'biomarker-mutation',
        loadChildren: () =>
          import('./biomarker-mutation/biomarker-mutation.module').then(m => m.KogniticApplicationBiomarkerMutationModule),
      },
      {
        path: 'line-of-therapy',
        loadChildren: () => import('./line-of-therapy/line-of-therapy.module').then(m => m.KogniticApplicationLineOfTherapyModule),
      },
      {
        path: 'stage',
        loadChildren: () => import('./stage/stage.module').then(m => m.KogniticApplicationStageModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class KogniticApplicationEntityModule {}
