import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ILineOfTherapy } from 'app/shared/model/line-of-therapy.model';

type EntityResponseType = HttpResponse<ILineOfTherapy>;
type EntityArrayResponseType = HttpResponse<ILineOfTherapy[]>;

@Injectable({ providedIn: 'root' })
export class LineOfTherapyService {
  public resourceUrl = SERVER_API_URL + 'api/line-of-therapies';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/line-of-therapies';

  constructor(protected http: HttpClient) {}

  create(lineOfTherapy: ILineOfTherapy): Observable<EntityResponseType> {
    return this.http.post<ILineOfTherapy>(this.resourceUrl, lineOfTherapy, { observe: 'response' });
  }

  update(lineOfTherapy: ILineOfTherapy): Observable<EntityResponseType> {
    return this.http.put<ILineOfTherapy>(this.resourceUrl, lineOfTherapy, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILineOfTherapy>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILineOfTherapy[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILineOfTherapy[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
