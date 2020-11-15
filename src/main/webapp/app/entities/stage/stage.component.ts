import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStage } from 'app/shared/model/stage.model';
import { StageService } from './stage.service';
import { StageDeleteDialogComponent } from './stage-delete-dialog.component';

@Component({
  selector: 'jhi-stage',
  templateUrl: './stage.component.html',
})
export class StageComponent implements OnInit, OnDestroy {
  stages?: IStage[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected stageService: StageService,
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
      this.stageService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IStage[]>) => (this.stages = res.body || []));
      return;
    }

    this.stageService.query().subscribe((res: HttpResponse<IStage[]>) => (this.stages = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStages(): void {
    this.eventSubscriber = this.eventManager.subscribe('stageListModification', () => this.loadAll());
  }

  delete(stage: IStage): void {
    const modalRef = this.modalService.open(StageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stage = stage;
  }
}
