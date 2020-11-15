import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IIndicationType } from 'app/shared/model/indication-type.model';

type EntityResponseType = HttpResponse<IIndicationType>;
type EntityArrayResponseType = HttpResponse<IIndicationType[]>;

@Injectable({ providedIn: 'root' })
export class IndicationTypeService {
  public resourceUrl = SERVER_API_URL + 'api/indication-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/indication-types';

  constructor(protected http: HttpClient) {}

  create(indicationType: IIndicationType): Observable<EntityResponseType> {
    return this.http.post<IIndicationType>(this.resourceUrl, indicationType, { observe: 'response' });
  }

  update(indicationType: IIndicationType): Observable<EntityResponseType> {
    return this.http.put<IIndicationType>(this.resourceUrl, indicationType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIndicationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndicationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndicationType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
