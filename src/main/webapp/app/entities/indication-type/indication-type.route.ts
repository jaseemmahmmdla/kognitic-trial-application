import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIndicationType, IndicationType } from 'app/shared/model/indication-type.model';
import { IndicationTypeService } from './indication-type.service';
import { IndicationTypeComponent } from './indication-type.component';
import { IndicationTypeDetailComponent } from './indication-type-detail.component';
import { IndicationTypeUpdateComponent } from './indication-type-update.component';

@Injectable({ providedIn: 'root' })
export class IndicationTypeResolve implements Resolve<IIndicationType> {
  constructor(private service: IndicationTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndicationType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((indicationType: HttpResponse<IndicationType>) => {
          if (indicationType.body) {
            return of(indicationType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IndicationType());
  }
}

export const indicationTypeRoute: Routes = [
  {
    path: '',
    component: IndicationTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indicationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicationTypeDetailComponent,
    resolve: {
      indicationType: IndicationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indicationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicationTypeUpdateComponent,
    resolve: {
      indicationType: IndicationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indicationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicationTypeUpdateComponent,
    resolve: {
      indicationType: IndicationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indicationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
