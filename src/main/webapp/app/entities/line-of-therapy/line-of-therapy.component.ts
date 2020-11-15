import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILineOfTherapy } from 'app/shared/model/line-of-therapy.model';
import { LineOfTherapyService } from './line-of-therapy.service';
import { LineOfTherapyDeleteDialogComponent } from './line-of-therapy-delete-dialog.component';

@Component({
  selector: 'jhi-line-of-therapy',
  templateUrl: './line-of-therapy.component.html',
})
export class LineOfTherapyComponent implements OnInit, OnDestroy {
  lineOfTherapies?: ILineOfTherapy[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected lineOfTherapyService: LineOfTherapyService,
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
      this.lineOfTherapyService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ILineOfTherapy[]>) => (this.lineOfTherapies = res.body || []));
      return;
    }

    this.lineOfTherapyService.query().subscribe((res: HttpResponse<ILineOfTherapy[]>) => (this.lineOfTherapies = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLineOfTherapies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILineOfTherapy): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLineOfTherapies(): void {
    this.eventSubscriber = this.eventManager.subscribe('lineOfTherapyListModification', () => this.loadAll());
  }

  delete(lineOfTherapy: ILineOfTherapy): void {
    const modalRef = this.modalService.open(LineOfTherapyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lineOfTherapy = lineOfTherapy;
  }
}
