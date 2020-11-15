import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IBiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';

type EntityResponseType = HttpResponse<IBiomarkerMutation>;
type EntityArrayResponseType = HttpResponse<IBiomarkerMutation[]>;

@Injectable({ providedIn: 'root' })
export class BiomarkerMutationService {
  public resourceUrl = SERVER_API_URL + 'api/biomarker-mutations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/biomarker-mutations';

  constructor(protected http: HttpClient) {}

  create(biomarkerMutation: IBiomarkerMutation): Observable<EntityResponseType> {
    return this.http.post<IBiomarkerMutation>(this.resourceUrl, biomarkerMutation, { observe: 'response' });
  }

  update(biomarkerMutation: IBiomarkerMutation): Observable<EntityResponseType> {
    return this.http.put<IBiomarkerMutation>(this.resourceUrl, biomarkerMutation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBiomarkerMutation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiomarkerMutation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBiomarkerMutation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
