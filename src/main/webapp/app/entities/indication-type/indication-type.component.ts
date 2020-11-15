import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndicationType } from 'app/shared/model/indication-type.model';
import { IndicationTypeService } from './indication-type.service';
import { IndicationTypeDeleteDialogComponent } from './indication-type-delete-dialog.component';

@Component({
  selector: 'jhi-indication-type',
  templateUrl: './indication-type.component.html',
})
export class IndicationTypeComponent implements OnInit, OnDestroy {
  indicationTypes?: IIndicationType[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected indicationTypeService: IndicationTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.indicationTypeService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IIndicationType[]>) => (this.indicationTypes = res.body || []));
      return;
    }

    this.indicationTypeService.query().subscribe((res: HttpResponse<IIndicationType[]>) => (this.indicationTypes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIndicationTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIndicationType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIndicationTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('indicationTypeListModification', () => this.loadAll());
  }

  delete(indicationType: IIndicationType): void {
    const modalRef = this.modalService.open(IndicationTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.indicationType = indicationType;
  }
}
