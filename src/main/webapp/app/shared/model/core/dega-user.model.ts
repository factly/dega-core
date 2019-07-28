import { Moment } from 'moment';
import { IOrganization } from 'app/shared/model/core/organization.model';
import { IPost } from 'app/shared/model/core/post.model';
import { IRoleMapping } from 'app/shared/model/core/role-mapping.model';

export interface IDegaUser {
    id?: string;
    firstName?: string;
    lastName?: string;
    displayName?: string;
    website?: string;
    facebookURL?: string;
    twitterURL?: string;
    instagramURL?: string;
    linkedinURL?: string;
    githubURL?: string;
    profilePicture?: string;
    description?: string;
    slug?: string;
    enabled?: boolean;
    emailVerified?: boolean;
    email?: string;
    createdDate?: Moment;
    keycloakId?: string;
    isSuperAdmin?: boolean;
    organizations?: IOrganization[];
    organizationDefaultName?: string;
    organizationDefaultId?: string;
    organizationCurrentName?: string;
    organizationCurrentId?: string;
    posts?: IPost[];
    roleMappings?: IRoleMapping[];
    mediaId?: string;
}

export class DegaUser implements IDegaUser {
    constructor(
        public id?: string,
        public firstName?: string,
        public lastName?: string,
        public displayName?: string,
        public website?: string,
        public facebookURL?: string,
        public twitterURL?: string,
        public instagramURL?: string,
        public linkedinURL?: string,
        public githubURL?: string,
        public profilePicture?: string,
        public description?: string,
        public slug?: string,
        public enabled?: boolean,
        public emailVerified?: boolean,
        public email?: string,
        public createdDate?: Moment,
        public keycloakId?: string,
        public isSuperAdmin?: boolean,
        public organizations?: IOrganization[],
        public organizationDefaultName?: string,
        public organizationDefaultId?: string,
        public organizationCurrentName?: string,
        public organizationCurrentId?: string,
        public posts?: IPost[],
        public roleMappings?: IRoleMapping[],
        public mediaId?: string
    ) {
        this.enabled = this.enabled || false;
        this.emailVerified = this.emailVerified || false;
        this.isSuperAdmin = this.isSuperAdmin || false;
    }
}
