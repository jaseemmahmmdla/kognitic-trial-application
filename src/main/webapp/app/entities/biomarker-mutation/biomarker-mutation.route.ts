import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBiomarkerMutation, BiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';
import { BiomarkerMutationService } from './biomarker-mutation.service';
import { BiomarkerMutationComponent } from './biomarker-mutation.component';
import { BiomarkerMutationDetailComponent } from './biomarker-mutation-detail.component';
import { BiomarkerMutationUpdateComponent } from './biomarker-mutation-update.component';

@Injectable({ providedIn: 'root' })
export class BiomarkerMutationResolve implements Resolve<IBiomarkerMutation> {
  constructor(private service: BiomarkerMutationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBiomarkerMutation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((biomarkerMutation: HttpResponse<BiomarkerMutation>) => {
          if (biomarkerMutation.body) {
            return of(biomarkerMutation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BiomarkerMutation());
  }
}

export const biomarkerMutationRoute: Routes = [
  {
    path: '',
    component: BiomarkerMutationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarkerMutation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BiomarkerMutationDetailComponent,
    resolve: {
      biomarkerMutation: BiomarkerMutationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarkerMutation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BiomarkerMutationUpdateComponent,
    resolve: {
      biomarkerMutation: BiomarkerMutationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarkerMutation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BiomarkerMutationUpdateComponent,
    resolve: {
      biomarkerMutation: BiomarkerMutationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarkerMutation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
