/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RoleService } from 'app/entities/core/role/role.service';
import { IRole, Role } from 'app/shared/model/core/role.model';

describe('Service Tests', () => {
    describe('Role Service', () => {
        let injector: TestBed;
        let service: RoleService;
        let httpMock: HttpTestingController;
        let elemDefault: IRole;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RoleService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Role('ID', 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA', currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find('123')
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Role', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Role(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Role', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        clientId: 'BBBBBB',
                        isDefault: true,
                        slug: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Role', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        clientId: 'BBBBBB',
                        isDefault: true,
                        slug: 'BBBBBB',
                        createdDate: currentDate.format(DATE_TIME_FORMAT),
                        lastUpdatedDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdDate: currentDate,
                        lastUpdatedDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Role', async () => {
                const rxPromise = service.delete('123').subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
