import { CommonModule } from '@angular/common';
import { afterNextRender, Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { interval } from 'rxjs/internal/observable/interval';
import Swal from 'sweetalert2';
import { CartService } from '../../../services/cart/cart.service';
import { WebEventsComponent } from '../web-events/web-events.component';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent implements OnInit{

  // // interval : any
  // items: any[] = []
  ngOnInit(): void {
    
  // this.items=JSON.parse(localStorage.getItem('cartItems') || '[]');
    // this.interval = setInterval(()=>{this.items = JSON.parse(localStorage.getItem('cartItems') || '[]');}, 1000)
    //  this.getDetails();
  }

  cartService = inject(CartService);
  eventsItemReqObj: eventsItemReq;

  constructor(private router: Router) {
     this.eventsItemReqObj = new eventsItemReq();
    //  this.getDetails();
  }

  id: any = JSON.parse(localStorage.getItem('loginUserId') || '');
  items: any[] = []
   //items: any[]=JSON.parse(localStorage.getItem('cartItems') || '[]');

  deleteFromCart(item: any): void {
    this.cartService.delete(item);
  }

  // placeOrder() {
  //   this.router.navigate(['payment']);
  // }

  //  arrayOfObjects: eventsItemReq[] = [];

  // getDetails() {
  //   console.log("cart items", this.items);
  //   for (const data of this.items) {
  //     this.arrayOfObjects.push({ eventId: data.eventId, noOfTickets: data.noOfTickets });
  //   }
  //   console.log(this.arrayOfObjects);
  // }


  placeOrder() {
    this.items= JSON.parse(localStorage.getItem('cartItems') || '[]');
    console.log("ITEMS -> ",this.items)

    let  arrayOfObjects: eventsItemReq[] = [];
    for (const data of this.items) {
      console.log("length -->" ,data.noOfTickets)
      arrayOfObjects.push({ eventId: data.eventId, noOfTickets: data.noOfTickets });
    }


    if (arrayOfObjects.length == 0)
    Swal.fire("Cart Is Empty!");
      // alert("Cart Is Empty!");

    else {
      console.log("arr -> " ,arrayOfObjects);
      this.cartService.bookTickets(this.id, arrayOfObjects).subscribe(
        (res) => {        
          localStorage.removeItem('cartItems'); 
          this.items = [];
          console.log("items -> ", this.items);       
          // localStorage.setItem('cartItems',JSON.stringify([]));
          // alert("order placed");
           Swal.fire("order placed");
          this.router.navigate(['/viewbookings']);
          console.log(res);
        });
    }
  }
 
//   //  ngOnDestroy(){
//   //   this.interval.clear();
//   //  }
 }

export class eventsItemReq {
  eventId: string;
  noOfTickets: string;
  constructor() {
    this.eventId = '';
    this.noOfTickets = '';
  }


}
