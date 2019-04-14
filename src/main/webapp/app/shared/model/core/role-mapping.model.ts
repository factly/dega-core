import { IDegaUser } from 'app/shared/model/core/dega-user.model';

export interface IRoleMapping {
    id?: string;
    degaUserDisplayName?: string;
    degaUserId?: string;
    organizationName?: string;
    organizationId?: string;
    roleName?: string;
    roleId?: string;
    degaUserRoleMappings?: IDegaUser[];
}

export class RoleMapping implements IRoleMapping {
    constructor(
        public id?: string,
        public degaUserDisplayName?: string,
        public degaUserId?: string,
        public organizationName?: string,
        public organizationId?: string,
        public roleName?: string,
        public roleId?: string,
        public degaUserRoleMappings?: IDegaUser[]
    ) {}
}
