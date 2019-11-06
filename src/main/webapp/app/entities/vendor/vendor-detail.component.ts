import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVendor } from 'app/shared/model/vendor.model';

@Component({
  selector: 'jhi-vendor-detail',
  templateUrl: './vendor-detail.component.html'
})
export class VendorDetailComponent implements OnInit {
  vendor: IVendor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vendor }) => {
      this.vendor = vendor;
    });
  }

  previousState() {
    window.history.back();
  }
}
