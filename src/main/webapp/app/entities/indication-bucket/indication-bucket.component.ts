import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndicationBucket } from 'app/shared/model/indication-bucket.model';
import { IndicationBucketService } from './indication-bucket.service';
import { IndicationBucketDeleteDialogComponent } from './indication-bucket-delete-dialog.component';

@Component({
  selector: 'jhi-indication-bucket',
  templateUrl: './indication-bucket.component.html',
})
export class IndicationBucketComponent implements OnInit, OnDestroy {
  indicationBuckets?: IIndicationBucket[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected indicationBucketService: IndicationBucketService,
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
      this.indicationBucketService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IIndicationBucket[]>) => (this.indicationBuckets = res.body || []));
      return;
    }

    this.indicationBucketService.query().subscribe((res: HttpResponse<IIndicationBucket[]>) => (this.indicationBuckets = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIndicationBuckets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIndicationBucket): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIndicationBuckets(): void {
    this.eventSubscriber = this.eventManager.subscribe('indicationBucketListModification', () => this.loadAll());
  }

  delete(indicationBucket: IIndicationBucket): void {
    const modalRef = this.modalService.open(IndicationBucketDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.indicationBucket = indicationBucket;
  }
}
