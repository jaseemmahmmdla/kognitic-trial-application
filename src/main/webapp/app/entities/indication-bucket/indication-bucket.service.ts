import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IIndicationBucket } from 'app/shared/model/indication-bucket.model';

type EntityResponseType = HttpResponse<IIndicationBucket>;
type EntityArrayResponseType = HttpResponse<IIndicationBucket[]>;

@Injectable({ providedIn: 'root' })
export class IndicationBucketService {
  public resourceUrl = SERVER_API_URL + 'api/indication-buckets';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/indication-buckets';

  constructor(protected http: HttpClient) {}

  create(indicationBucket: IIndicationBucket): Observable<EntityResponseType> {
    return this.http.post<IIndicationBucket>(this.resourceUrl, indicationBucket, { observe: 'response' });
  }

  update(indicationBucket: IIndicationBucket): Observable<EntityResponseType> {
    return this.http.put<IIndicationBucket>(this.resourceUrl, indicationBucket, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIndicationBucket>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndicationBucket[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndicationBucket[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
