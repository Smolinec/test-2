import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITemperatureValues, TemperatureValues } from 'app/shared/model/temperature-values.model';
import { TemperatureValuesService } from './temperature-values.service';
import { TemperatureValuesComponent } from './temperature-values.component';
import { TemperatureValuesDetailComponent } from './temperature-values-detail.component';
import { TemperatureValuesUpdateComponent } from './temperature-values-update.component';

@Injectable({ providedIn: 'root' })
export class TemperatureValuesResolve implements Resolve<ITemperatureValues> {
  constructor(private service: TemperatureValuesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITemperatureValues> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((temperatureValues: HttpResponse<TemperatureValues>) => {
          if (temperatureValues.body) {
            return of(temperatureValues.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TemperatureValues());
  }
}

export const temperatureValuesRoute: Routes = [
  {
    path: '',
    component: TemperatureValuesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testMemoryH2App.temperatureValues.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TemperatureValuesDetailComponent,
    resolve: {
      temperatureValues: TemperatureValuesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testMemoryH2App.temperatureValues.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TemperatureValuesUpdateComponent,
    resolve: {
      temperatureValues: TemperatureValuesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testMemoryH2App.temperatureValues.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TemperatureValuesUpdateComponent,
    resolve: {
      temperatureValues: TemperatureValuesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'testMemoryH2App.temperatureValues.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
