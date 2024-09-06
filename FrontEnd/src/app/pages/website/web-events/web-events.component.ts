import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Events } from '../../../models/events';
import { CartService } from '../../../services/cart/cart.service';
import { EventService } from '../../../services/event/event.service';

@Component({
  selector: 'app-web-events',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './web-events.component.html',
  styleUrl: './web-events.component.css'
})
export class WebEventsComponent {
  //eventsList: any[] = [];
  eventsList: Events[] = [];
  categoryList: any[] = [];
  upcomingEventsList: any[] = [];

  constructor(private eventSrv: EventService, private router: Router) {
    this.getEvents();
    this.getUpcomingEvents();
  }

  cartService = inject(CartService);

  // ngOnInit(): void {
  //   this.getEvents();
  //   //.getCategory();
  // }



  addToCart(events: any) {
    this.cartService.addtoCart(events);
  }


  getEvents() {
    this.eventSrv.getAllEvents().subscribe(
      (res: any) => {
        this.eventsList = res;
      }
    );
  }

  getUpcomingEvents() {
    this.eventSrv.getUpcomingEvents().subscribe(
      (res: any) => {
        this.upcomingEventsList = res;
      }
    )
  }

  // getCategory(){
  //   this.eventSrv.getAllCategory().subscribe(
  //     {
  //       next:(res:any)=>{
  //        this.categoryList= res; 
  //       },
  //       error: (error: any) => {
  //         alert(error.error.text);
  //       }     
  //   }
  //   )
  // }

  navigateToEvents(id: number) {
    this.router.navigate(['/events', id])
  }
}
