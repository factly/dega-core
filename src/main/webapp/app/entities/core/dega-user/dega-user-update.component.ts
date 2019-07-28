import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IDegaUser } from 'app/shared/model/core/dega-user.model';
import { DegaUserService } from './dega-user.service';
import { IOrganization } from 'app/shared/model/core/organization.model';
import { OrganizationService } from 'app/entities/core/organization';
import { IPost } from 'app/shared/model/core/post.model';
import { PostService } from 'app/entities/core/post';
import { IRoleMapping } from 'app/shared/model/core/role-mapping.model';
import { RoleMappingService } from 'app/entities/core/role-mapping';
import { IMedia } from 'app/shared/model/core/media.model';
import { MediaService } from 'app/entities/core/media';

@Component({
    selector: 'jhi-dega-user-update',
    templateUrl: './dega-user-update.component.html'
})
export class DegaUserUpdateComponent implements OnInit {
    degaUser: IDegaUser;
    isSaving: boolean;

    organizations: IOrganization[];

    posts: IPost[];

    rolemappings: IRoleMapping[];

    media: IMedia[];
    createdDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private degaUserService: DegaUserService,
        private organizationService: OrganizationService,
        private postService: PostService,
        private roleMappingService: RoleMappingService,
        private mediaService: MediaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ degaUser }) => {
            this.degaUser = degaUser;
            this.createdDate = this.degaUser.createdDate != null ? this.degaUser.createdDate.format(DATE_TIME_FORMAT) : null;
        });
        this.organizationService.query().subscribe(
            (res: HttpResponse<IOrganization[]>) => {
                this.organizations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.postService.query().subscribe(
            (res: HttpResponse<IPost[]>) => {
                this.posts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.roleMappingService.query().subscribe(
            (res: HttpResponse<IRoleMapping[]>) => {
                this.rolemappings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.mediaService.query().subscribe(
            (res: HttpResponse<IMedia[]>) => {
                this.media = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.degaUser.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        if (this.degaUser.id !== undefined) {
            this.subscribeToSaveResponse(this.degaUserService.update(this.degaUser));
        } else {
            this.subscribeToSaveResponse(this.degaUserService.create(this.degaUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDegaUser>>) {
        result.subscribe((res: HttpResponse<IDegaUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackOrganizationById(index: number, item: IOrganization) {
        return item.id;
    }

    trackPostById(index: number, item: IPost) {
        return item.id;
    }

    trackRoleMappingById(index: number, item: IRoleMapping) {
        return item.id;
    }

    trackMediaById(index: number, item: IMedia) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
