import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBiomarker } from 'app/shared/model/biomarker.model';

type EntityResponseType = HttpResponse<IBiomarker>;
type EntityArrayResponseType = HttpResponse<IBiomarker[]>;

@Injectable({ providedIn: 'root' })
export class BiomarkerService {
  public resourceUrl = SERVER_API_URL + 'api/biomarkers';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/biomarkers';

  constructor(protected http: HttpClient) {}

  create(biomarker: IBiomarker): Observable<EntityResponseType> {
    return this.http.post<IBiomarker>(this.resourceUrl, biomarker, { observe: 'response' });
  }

  update(biomarker: IBiomarker): Observable<EntityResponseType> {
    return this.http.put<IBiomarker>(this.resourceUrl, biomarker, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBiomarker>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiomarker[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiomarker[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
