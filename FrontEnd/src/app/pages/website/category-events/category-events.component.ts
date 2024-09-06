import { CommonModule, Time } from '@angular/common';
import { Component, inject, OnInit, ɵUSE_RUNTIME_DEPS_TRACKER_FOR_JIT } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from '../../../services/event/event.service';
import { map } from 'rxjs/operators';
import { Events } from '../../../models/events';
import { CartService } from '../../../services/cart/cart.service';
@Component({
  selector: 'app-category-events',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './category-events.component.html',
  styleUrl: './category-events.component.css'
})
export class CategoryEventsComponent {

  activeCategoryId: number = 0;
  constructor(private activatedRoute: ActivatedRoute, private eventsSrv: EventService) {
    this.activatedRoute.params.subscribe(res => {
      this.activeCategoryId = res['id'];
      this.loadProducts();
    })
  }

  cartService = inject(CartService);

  // ngOnInit(): void {
  //  this.loadProducts();
  // }

  eventsList: Events[] = [];

  addToCart(events: any) {
    this.cartService.addtoCart(events);
  }

  loadProducts() {
    this.eventsSrv.getAllEventsByCategory(this.activeCategoryId).subscribe(
      (res: any) => {
        this.eventsList = res.eventsList;
        console.log(res);
        console.log(this.eventsList);
      }
    );

  }

}


