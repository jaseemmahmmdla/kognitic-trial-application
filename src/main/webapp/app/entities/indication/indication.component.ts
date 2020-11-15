import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndication } from 'app/shared/model/indication.model';
import { IndicationService } from './indication.service';
import { IndicationDeleteDialogComponent } from './indication-delete-dialog.component';

@Component({
  selector: 'jhi-indication',
  templateUrl: './indication.component.html',
})
export class IndicationComponent implements OnInit, OnDestroy {
  indications?: IIndication[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected indicationService: IndicationService,
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
      this.indicationService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IIndication[]>) => (this.indications = res.body || []));
      return;
    }

    this.indicationService.query().subscribe((res: HttpResponse<IIndication[]>) => (this.indications = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIndications();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIndication): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIndications(): void {
    this.eventSubscriber = this.eventManager.subscribe('indicationListModification', () => this.loadAll());
  }

  delete(indication: IIndication): void {
    const modalRef = this.modalService.open(IndicationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.indication = indication;
  }
}
