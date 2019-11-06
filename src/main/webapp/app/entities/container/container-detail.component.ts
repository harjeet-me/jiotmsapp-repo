import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContainer } from 'app/shared/model/container.model';

@Component({
  selector: 'jhi-container-detail',
  templateUrl: './container-detail.component.html'
})
export class ContainerDetailComponent implements OnInit {
  container: IContainer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ container }) => {
      this.container = container;
    });
  }

  previousState() {
    window.history.back();
  }
}
