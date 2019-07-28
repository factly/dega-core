import { Moment } from 'moment';
import { IRoleMapping } from 'app/shared/model/core/role-mapping.model';

export interface IRole {
    id?: string;
    name?: string;
    clientId?: string;
    isDefault?: boolean;
    slug?: string;
    createdDate?: Moment;
    lastUpdatedDate?: Moment;
    keycloakId?: string;
    keycloakName?: string;
    roleMappings?: IRoleMapping[];
}

export class Role implements IRole {
    constructor(
        public id?: string,
        public name?: string,
        public clientId?: string,
        public isDefault?: boolean,
        public slug?: string,
        public createdDate?: Moment,
        public lastUpdatedDate?: Moment,
        public keycloakId?: string,
        public keycloakName?: string,
        public roleMappings?: IRoleMapping[]
    ) {
        this.isDefault = this.isDefault || false;
    }
}
