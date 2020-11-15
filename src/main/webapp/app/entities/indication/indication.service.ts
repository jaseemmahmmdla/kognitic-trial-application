import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IIndication } from 'app/shared/model/indication.model';

type EntityResponseType = HttpResponse<IIndication>;
type EntityArrayResponseType = HttpResponse<IIndication[]>;

@Injectable({ providedIn: 'root' })
export class IndicationService {
  public resourceUrl = SERVER_API_URL + 'api/indications';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/indications';

  constructor(protected http: HttpClient) {}

  create(indication: IIndication): Observable<EntityResponseType> {
    return this.http.post<IIndication>(this.resourceUrl, indication, { observe: 'response' });
  }

  update(indication: IIndication): Observable<EntityResponseType> {
    return this.http.put<IIndication>(this.resourceUrl, indication, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIndication>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndication[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndication[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
