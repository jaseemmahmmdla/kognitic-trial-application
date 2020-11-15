import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStage, Stage } from 'app/shared/model/stage.model';
import { StageService } from './stage.service';
import { StageComponent } from './stage.component';
import { StageDetailComponent } from './stage-detail.component';
import { StageUpdateComponent } from './stage-update.component';

@Injectable({ providedIn: 'root' })
export class StageResolve implements Resolve<IStage> {
  constructor(private service: StageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((stage: HttpResponse<Stage>) => {
          if (stage.body) {
            return of(stage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Stage());
  }
}

export const stageRoute: Routes = [
  {
    path: '',
    component: StageComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.stage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StageDetailComponent,
    resolve: {
      stage: StageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.stage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StageUpdateComponent,
    resolve: {
      stage: StageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.stage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StageUpdateComponent,
    resolve: {
      stage: StageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'kogniticApplicationApp.stage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
