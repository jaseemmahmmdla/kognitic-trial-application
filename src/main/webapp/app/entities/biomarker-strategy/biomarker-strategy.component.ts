import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBiomarkerStrategy } from 'app/shared/model/biomarker-strategy.model';
import { BiomarkerStrategyService } from './biomarker-strategy.service';
import { BiomarkerStrategyDeleteDialogComponent } from './biomarker-strategy-delete-dialog.component';

@Component({
  selector: 'jhi-biomarker-strategy',
  templateUrl: './biomarker-strategy.component.html',
})
export class BiomarkerStrategyComponent implements OnInit, OnDestroy {
  biomarkerStrategies?: IBiomarkerStrategy[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected biomarkerStrategyService: BiomarkerStrategyService,
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
      this.biomarkerStrategyService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBiomarkerStrategy[]>) => (this.biomarkerStrategies = res.body || []));
      return;
    }

    this.biomarkerStrategyService
      .query()
      .subscribe((res: HttpResponse<IBiomarkerStrategy[]>) => (this.biomarkerStrategies = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBiomarkerStrategies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBiomarkerStrategy): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBiomarkerStrategies(): void {
    this.eventSubscriber = this.eventManager.subscribe('biomarkerStrategyListModification', () => this.loadAll());
  }

  delete(biomarkerStrategy: IBiomarkerStrategy): void {
    const modalRef = this.modalService.open(BiomarkerStrategyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.biomarkerStrategy = biomarkerStrategy;
  }
}
