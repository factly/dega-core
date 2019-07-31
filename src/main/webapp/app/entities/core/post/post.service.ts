import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPost } from 'app/shared/model/core/post.model';

type EntityResponseType = HttpResponse<IPost>;
type EntityArrayResponseType = HttpResponse<IPost[]>;

@Injectable({ providedIn: 'root' })
export class PostService {
    public resourceUrl = SERVER_API_URL + 'api/posts';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/posts';

    constructor(private http: HttpClient) {}

    create(post: IPost): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(post);
        return this.http
            .post<IPost>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(post: IPost): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(post);
        return this.http
            .put<IPost>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<IPost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPost[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPost[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(post: IPost): IPost {
        const copy: IPost = Object.assign({}, post, {
            publishedDate: post.publishedDate != null && post.publishedDate.isValid() ? post.publishedDate.toJSON() : null,
            lastUpdatedDate: post.lastUpdatedDate != null && post.lastUpdatedDate.isValid() ? post.lastUpdatedDate.toJSON() : null,
            createdDate: post.createdDate != null && post.createdDate.isValid() ? post.createdDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.publishedDate = res.body.publishedDate != null ? moment(res.body.publishedDate) : null;
            res.body.lastUpdatedDate = res.body.lastUpdatedDate != null ? moment(res.body.lastUpdatedDate) : null;
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((post: IPost) => {
                post.publishedDate = post.publishedDate != null ? moment(post.publishedDate) : null;
                post.lastUpdatedDate = post.lastUpdatedDate != null ? moment(post.lastUpdatedDate) : null;
                post.createdDate = post.createdDate != null ? moment(post.createdDate) : null;
            });
        }
        return res;
    }
}
