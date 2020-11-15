import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBiomarkerStrategy, BiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';
import { BiomarkerStrategyService } from './biomarker-strategy.service';
import { BiomarkerStrategyComponent } from './biomarker-strategy.component';
import { BiomarkerStrategyDetailComponent } from './biomarker-strategy-detail.component';
import { BiomarkerStrategyUpdateComponent } from './biomarker-strategy-update.component';

@Injectable({ providedIn: 'root' })
export class BiomarkerStrategyResolve implements Resolve<IBiomarkerStrategy> {
  constructor(private service: BiomarkerStrategyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBiomarkerStrategy> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((biomarkerStrategy: HttpResponse<BiomarkerStrategy>) => {
          if (biomarkerStrategy.body) {
            return of(biomarkerStrategy.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BiomarkerStrategy());
  }
}

export const biomarkerStrategyRoute: Routes = [
  {
    path: '',
    component: BiomarkerStrategyComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarkerStrategy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BiomarkerStrategyDetailComponent,
    resolve: {
      biomarkerStrategy: BiomarkerStrategyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarkerStrategy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BiomarkerStrategyUpdateComponent,
    resolve: {
      biomarkerStrategy: BiomarkerStrategyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarkerStrategy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BiomarkerStrategyUpdateComponent,
    resolve: {
      biomarkerStrategy: BiomarkerStrategyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.biomarkerStrategy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
