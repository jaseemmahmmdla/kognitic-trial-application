import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIndication, Indication } from 'app/shared/model/indication.model';
import { IndicationService } from './indication.service';
import { IndicationComponent } from './indication.component';
import { IndicationDetailComponent } from './indication-detail.component';
import { IndicationUpdateComponent } from './indication-update.component';

@Injectable({ providedIn: 'root' })
export class IndicationResolve implements Resolve<IIndication> {
  constructor(private service: IndicationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndication> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((indication: HttpResponse<Indication>) => {
          if (indication.body) {
            return of(indication.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Indication());
  }
}

export const indicationRoute: Routes = [
  {
    path: '',
    component: IndicationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicationDetailComponent,
    resolve: {
      indication: IndicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicationUpdateComponent,
    resolve: {
      indication: IndicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicationUpdateComponent,
    resolve: {
      indication: IndicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];