import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBiomarkerMutation } from 'app/shared/model/biomarker-mutation.model';
import { BiomarkerMutationService } from './biomarker-mutation.service';
import { BiomarkerMutationDeleteDialogComponent } from './biomarker-mutation-delete-dialog.component';

@Component({
  selector: 'jhi-biomarker-mutation',
  templateUrl: './biomarker-mutation.component.html',
})
export class BiomarkerMutationComponent implements OnInit, OnDestroy {
  biomarkerMutations?: IBiomarkerMutation[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected biomarkerMutationService: BiomarkerMutationService,
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
      this.biomarkerMutationService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IBiomarkerMutation[]>) => (this.biomarkerMutations = res.body || []));
      return;
    }

    this.biomarkerMutationService
      .query()
      .subscribe((res: HttpResponse<IBiomarkerMutation[]>) => (this.biomarkerMutations = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBiomarkerMutations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBiomarkerMutation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBiomarkerMutations(): void {
    this.eventSubscriber = this.eventManager.subscribe('biomarkerMutationListModification', () => this.loadAll());
  }

  delete(biomarkerMutation: IBiomarkerMutation): void {
    const modalRef = this.modalService.open(BiomarkerMutationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.biomarkerMutation = biomarkerMutation;
  }
}
