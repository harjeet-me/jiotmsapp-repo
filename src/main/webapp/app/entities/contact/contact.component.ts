import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';
import { ContactDeleteDialogComponent } from './contact-delete-dialog.component';

@Component({
  selector: 'jhi-contact',
  templateUrl: './contact.component.html'
})
export class ContactComponent implements OnInit, OnDestroy {
  contacts: IContact[];
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    protected contactService: ContactService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
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
        .subscribe((res: HttpResponse<IContact[]>) => (this.contacts = res.body));
      return;
    }
    this.contactService.query().subscribe((res: HttpResponse<IContact[]>) => {
      this.contacts = res.body;
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
    this.registerChangeInContacts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContact) {
    return item.id;
  }

  registerChangeInContacts() {
    this.eventSubscriber = this.eventManager.subscribe('contactListModification', () => this.loadAll());
  }

  delete(contact: IContact) {
    const modalRef = this.modalService.open(ContactDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contact = contact;
  }
}
