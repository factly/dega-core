/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CoreTestModule } from '../../../../test.module';
import { RoleMappingDeleteDialogComponent } from 'app/entities/core/role-mapping/role-mapping-delete-dialog.component';
import { RoleMappingService } from 'app/entities/core/role-mapping/role-mapping.service';

describe('Component Tests', () => {
    describe('RoleMapping Management Delete Component', () => {
        let comp: RoleMappingDeleteDialogComponent;
        let fixture: ComponentFixture<RoleMappingDeleteDialogComponent>;
        let service: RoleMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoreTestModule],
                declarations: [RoleMappingDeleteDialogComponent]
            })
                .overrideTemplate(RoleMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoleMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoleMappingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
