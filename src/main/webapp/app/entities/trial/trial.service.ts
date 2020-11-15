import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ITrial } from 'app/shared/model/trial.model';

type EntityResponseType = HttpResponse<ITrial>;
type EntityArrayResponseType = HttpResponse<ITrial[]>;

@Injectable({ providedIn: 'root' })
export class TrialService {
  public resourceUrl = SERVER_API_URL + 'api/trials';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/trials';

  constructor(protected http: HttpClient) {}

  create(trial: ITrial): Observable<EntityResponseType> {
    return this.http.post<ITrial>(this.resourceUrl, trial, { observe: 'response' });
  }

  update(trial: ITrial): Observable<EntityResponseType> {
    return this.http.put<ITrial>(this.resourceUrl, trial, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrial[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
