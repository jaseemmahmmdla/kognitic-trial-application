import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBiomarker } from 'app/shared/model/biomarker.model';
import { BiomarkerService } from './biomarker.service';
import { BiomarkerDeleteDialogComponent } from './biomarker-delete-dialog.component';

@Component({
  selector: 'jhi-biomarker',
  templateUrl: './biomarker.component.html',
})
export class BiomarkerComponent implements OnInit, OnDestroy {
  biomarkers?: IBiomarker[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected biomarkerService: BiomarkerService,
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
      this.biomarkerService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBiomarker[]>) => (this.biomarkers = res.body || []));
      return;
    }

    this.biomarkerService.query().subscribe((res: HttpResponse<IBiomarker[]>) => (this.biomarkers = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBiomarkers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBiomarker): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBiomarkers(): void {
    this.eventSubscriber = this.eventManager.subscribe('biomarkerListModification', () => this.loadAll());
  }

  delete(biomarker: IBiomarker): void {
    const modalRef = this.modalService.open(BiomarkerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.biomarker = biomarker;
  }
}
