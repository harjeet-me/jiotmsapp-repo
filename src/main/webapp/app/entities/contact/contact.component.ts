import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IContact } from 'app/shared/model/contact.model';
import { AccountService } from 'app/core/auth/account.service';
import { ContactService } from './contact.service';

@Component({
  selector: 'jhi-contact',
  templateUrl: './contact.component.html'
})
export class ContactComponent implements OnInit, OnDestroy {
  contacts: IContact[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected contactService: ContactService,
    protected eventManager: JhiEventManager,
    protected activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.contactService
        .search({
          query: this.currentSearch
        })
        .pipe(
          filter((res: HttpResponse<IContact[]>) => res.ok),
          map((res: HttpResponse<IContact[]>) => res.body)
        )
        .subscribe((res: IContact[]) => (this.contacts = res));
      return;
    }
    this.contactService
      .query()
      .pipe(
        filter((res: HttpResponse<IContact[]>) => res.ok),
        map((res: HttpResponse<IContact[]>) => res.body)
      )
      .subscribe((res: IContact[]) => {
        this.contacts = res;
        this.currentSearch = '';
      });
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInContacts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContact) {
    return item.id;
  }

  registerChangeInContacts() {
    this.eventSubscriber = this.eventManager.subscribe('contactListModification', response => this.loadAll());
  }
}
