import { Moment } from 'moment';
import { IPost } from 'app/shared/model/core/post.model';
import { IDegaUser } from 'app/shared/model/core/dega-user.model';
import { IOrganization } from 'app/shared/model/core/organization.model';

export interface IMedia {
    id?: string;
    name?: string;
    type?: string;
    url?: string;
    fileSize?: string;
    dimensions?: string;
    title?: string;
    caption?: string;
    altText?: string;
    description?: string;
    uploadedBy?: string;
    publishedDate?: Moment;
    lastUpdatedDate?: Moment;
    slug?: string;
    clientId?: string;
    createdDate?: Moment;
    relativeURL?: string;
    sourceURL?: string;
    posts?: IPost[];
    degaUsers?: IDegaUser[];
    organizationLogos?: IOrganization[];
    organizationMobileLogos?: IOrganization[];
    organizationFavicons?: IOrganization[];
    organizationMobileIcons?: IOrganization[];
}

export class Media implements IMedia {
    constructor(
        public id?: string,
        public name?: string,
        public type?: string,
        public url?: string,
        public fileSize?: string,
        public dimensions?: string,
        public title?: string,
        public caption?: string,
        public altText?: string,
        public description?: string,
        public uploadedBy?: string,
        public publishedDate?: Moment,
        public lastUpdatedDate?: Moment,
        public slug?: string,
        public clientId?: string,
        public createdDate?: Moment,
        public relativeURL?: string,
        public sourceURL?: string,
        public posts?: IPost[],
        public degaUsers?: IDegaUser[],
        public organizationLogos?: IOrganization[],
        public organizationMobileLogos?: IOrganization[],
        public organizationFavicons?: IOrganization[],
        public organizationMobileIcons?: IOrganization[]
    ) {}
}
