import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrial, Trial } from 'app/shared/model/trial.model';
import { TrialService } from './trial.service';
import { TrialComponent } from './trial.component';
import { TrialDetailComponent } from './trial-detail.component';
import { TrialUpdateComponent } from './trial-update.component';

@Injectable({ providedIn: 'root' })
export class TrialResolve implements Resolve<ITrial> {
  constructor(private service: TrialService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrial> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trial: HttpResponse<Trial>) => {
          if (trial.body) {
            return of(trial.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Trial());
  }
}

export const trialRoute: Routes = [
  {
    path: '',
    component: TrialComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'kogniticApplicationApp.trial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrialDetailComponent,
    resolve: {
      trial: TrialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.trial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrialUpdateComponent,
    resolve: {
      trial: TrialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.trial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrialUpdateComponent,
    resolve: {
      trial: TrialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.trial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
