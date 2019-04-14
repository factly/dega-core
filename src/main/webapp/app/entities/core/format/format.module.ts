import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CoreSharedModule } from 'app/shared';
import {
  FormatComponent,
  FormatDetailComponent,
  FormatUpdateComponent,
  FormatDeletePopupComponent,
  FormatDeleteDialogComponent,
  formatRoute,
  formatPopupRoute
} from './';

const ENTITY_STATES = [...formatRoute, ...formatPopupRoute];

@NgModule({
  imports: [CoreSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FormatComponent, FormatDetailComponent, FormatUpdateComponent, FormatDeleteDialogComponent, FormatDeletePopupComponent],
  entryComponents: [FormatComponent, FormatUpdateComponent, FormatDeleteDialogComponent, FormatDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoreFormatModule {}
