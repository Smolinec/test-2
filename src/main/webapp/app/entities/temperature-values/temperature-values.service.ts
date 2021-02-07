import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITemperatureValues } from 'app/shared/model/temperature-values.model';

type EntityResponseType = HttpResponse<ITemperatureValues>;
type EntityArrayResponseType = HttpResponse<ITemperatureValues[]>;

@Injectable({ providedIn: 'root' })
export class TemperatureValuesService {
  public resourceUrl = SERVER_API_URL + 'api/temperature-values';

  constructor(protected http: HttpClient) {}

  create(temperatureValues: ITemperatureValues): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(temperatureValues);
    return this.http
      .post<ITemperatureValues>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(temperatureValues: ITemperatureValues): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(temperatureValues);
    return this.http
      .put<ITemperatureValues>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITemperatureValues>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITemperatureValues[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(temperatureValues: ITemperatureValues): ITemperatureValues {
    const copy: ITemperatureValues = Object.assign({}, temperatureValues, {
      timestamp: temperatureValues.timestamp && temperatureValues.timestamp.isValid() ? temperatureValues.timestamp.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timestamp = res.body.timestamp ? moment(res.body.timestamp) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((temperatureValues: ITemperatureValues) => {
        temperatureValues.timestamp = temperatureValues.timestamp ? moment(temperatureValues.timestamp) : undefined;
      });
    }
    return res;
  }
}
