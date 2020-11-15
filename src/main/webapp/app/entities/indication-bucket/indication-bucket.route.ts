import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIndicationBucket, IndicationBucket } from 'app/shared/model/indication-bucket.model';
import { IndicationBucketService } from './indication-bucket.service';
import { IndicationBucketComponent } from './indication-bucket.component';
import { IndicationBucketDetailComponent } from './indication-bucket-detail.component';
import { IndicationBucketUpdateComponent } from './indication-bucket-update.component';

@Injectable({ providedIn: 'root' })
export class IndicationBucketResolve implements Resolve<IIndicationBucket> {
  constructor(private service: IndicationBucketService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndicationBucket> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((indicationBucket: HttpResponse<IndicationBucket>) => {
          if (indicationBucket.body) {
            return of(indicationBucket.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IndicationBucket());
  }
}

export const indicationBucketRoute: Routes = [
  {
    path: '',
    component: IndicationBucketComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indicationBucket.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicationBucketDetailComponent,
    resolve: {
      indicationBucket: IndicationBucketResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indicationBucket.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicationBucketUpdateComponent,
    resolve: {
      indicationBucket: IndicationBucketResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indicationBucket.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicationBucketUpdateComponent,
    resolve: {
      indicationBucket: IndicationBucketResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.indicationBucket.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
