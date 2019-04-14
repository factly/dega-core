import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoreSharedModule } from 'app/shared';
import {
  DegaUserComponent,
  DegaUserDetailComponent,
  DegaUserUpdateComponent,
  DegaUserDeletePopupComponent,
  DegaUserDeleteDialogComponent,
  degaUserRoute,
  degaUserPopupRoute
} from './';

const ENTITY_STATES = [...degaUserRoute, ...degaUserPopupRoute];

@NgModule({
  imports: [CoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DegaUserComponent,
    DegaUserDetailComponent,
    DegaUserUpdateComponent,
    DegaUserDeleteDialogComponent,
    DegaUserDeletePopupComponent
  ],
  entryComponents: [DegaUserComponent, DegaUserUpdateComponent, DegaUserDeleteDialogComponent, DegaUserDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoreDegaUserModule {}
