import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoreSharedModule } from 'app/shared';
import {
    OrganizationComponent,
    OrganizationDetailComponent,
    OrganizationUpdateComponent,
    OrganizationDeletePopupComponent,
    OrganizationDeleteDialogComponent,
    organizationRoute,
    organizationPopupRoute
} from './';

const ENTITY_STATES = [...organizationRoute, ...organizationPopupRoute];

@NgModule({
    imports: [CoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrganizationComponent,
        OrganizationDetailComponent,
        OrganizationUpdateComponent,
        OrganizationDeleteDialogComponent,
        OrganizationDeletePopupComponent
    ],
    entryComponents: [
        OrganizationComponent,
        OrganizationUpdateComponent,
        OrganizationDeleteDialogComponent,
        OrganizationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoreOrganizationModule {}
