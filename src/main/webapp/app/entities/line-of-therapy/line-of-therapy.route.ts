import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILineOfTherapy, LineOfTherapy } from 'app/shared/model/line-of-therapy.model';
import { LineOfTherapyService } from './line-of-therapy.service';
import { LineOfTherapyComponent } from './line-of-therapy.component';
import { LineOfTherapyDetailComponent } from './line-of-therapy-detail.component';
import { LineOfTherapyUpdateComponent } from './line-of-therapy-update.component';

@Injectable({ providedIn: 'root' })
export class LineOfTherapyResolve implements Resolve<ILineOfTherapy> {
  constructor(private service: LineOfTherapyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILineOfTherapy> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lineOfTherapy: HttpResponse<LineOfTherapy>) => {
          if (lineOfTherapy.body) {
            return of(lineOfTherapy.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LineOfTherapy());
  }
}

export const lineOfTherapyRoute: Routes = [
  {
    path: '',
    component: LineOfTherapyComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.lineOfTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LineOfTherapyDetailComponent,
    resolve: {
      lineOfTherapy: LineOfTherapyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.lineOfTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LineOfTherapyUpdateComponent,
    resolve: {
      lineOfTherapy: LineOfTherapyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.lineOfTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LineOfTherapyUpdateComponent,
    resolve: {
      lineOfTherapy: LineOfTherapyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.lineOfTherapy.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
