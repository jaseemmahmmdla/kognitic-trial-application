import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBiomarker, Biomarker } from 'app/shared/model/biomarker.model';
import { BiomarkerService } from './biomarker.service';
import { BiomarkerComponent } from './biomarker.component';
import { BiomarkerDetailComponent } from './biomarker-detail.component';
import { BiomarkerUpdateComponent } from './biomarker-update.component';

@Injectable({ providedIn: 'root' })
export class BiomarkerResolve implements Resolve<IBiomarker> {
  constructor(private service: BiomarkerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBiomarker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((biomarker: HttpResponse<Biomarker>) => {
          if (biomarker.body) {
            return of(biomarker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Biomarker());
  }
}

export const biomarkerRoute: Routes = [
  {
    path: '',
    component: BiomarkerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BiomarkerDetailComponent,
    resolve: {
      biomarker: BiomarkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BiomarkerUpdateComponent,
    resolve: {
      biomarker: BiomarkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BiomarkerUpdateComponent,
    resolve: {
      biomarker: BiomarkerResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarker.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
