import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';

type EntityResponseType = HttpResponse<IBiomarkerStrategy>;
type EntityArrayResponseType = HttpResponse<IBiomarkerStrategy[]>;

@Injectable({ providedIn: 'root' })
export class BiomarkerStrategyService {
  public resourceUrl = SERVER_API_URL + 'api/biomarker-strategies';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/biomarker-strategies';

  constructor(protected http: HttpClient) {}

  create(biomarkerStrategy: IBiomarkerStrategy): Observable<EntityResponseType> {
    return this.http.post<IBiomarkerStrategy>(this.resourceUrl, biomarkerStrategy, { observe: 'response' });
  }

  update(biomarkerStrategy: IBiomarkerStrategy): Observable<EntityResponseType> {
    return this.http.put<IBiomarkerStrategy>(this.resourceUrl, biomarkerStrategy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBiomarkerStrategy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiomarkerStrategy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiomarkerStrategy[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
