import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { interval } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private items: any[] = JSON.parse(localStorage.getItem('cartItems') || '[]');

  constructor(private http: HttpClient) { }

  private API_URL = 'http://localhost:8080/api/user/booking';

  bookTickets(userId: number, bookings: any[]) {
    return this.http.post(`${this.API_URL}/${userId}`, bookings);
  }


  addtoCart(events: any) {
    let item = this.items.find((i) => i.eventId === events.eventId);
    if (item) {
      item.noOfTickets++;
      // console.log(item.noOfTickets);
      Swal.fire("Event Already in Cart, Quantity Increased");
    }
    else {
      this.items.push({ ...events, noOfTickets: 1 });
      Swal.fire("Event added to Cart");
    }
    localStorage.setItem('cartItems', JSON.stringify(this.items));

  }

  getItems() {
     return this.items;
    // let interval : any
    // interval = setInterval(()=>{this.items = JSON.parse(localStorage.getItem('cartItems') || '[]');}, 1000)
    // return interval;
  }

  delete(item: any) {
    this.items = this.items.filter((i) => i.eventId !== item.eventId);
    localStorage.setItem('cartItems', JSON.stringify(this.items));
  }

  inQuantity(id: number) {
    let item = this.items.find((i) => i.eventId === id);
    if (item) {
      item.noOfTickets++;
      console.log(item.noOfTickets);
    }
    localStorage.setItem('cartItems', JSON.stringify(this.items));
  }

  deQuantity(id: number) {
    let item = this.items.find((i) => i.eventId === id);
    console.log(item);
    if (item) {
      console.log(item.not);
      if (item.noOfTickets > 1)
        item.noOfTickets--;
      else
        item.noOfTickets = 1;

    }
    localStorage.setItem('cartItems', JSON.stringify(this.items));
  }

  getTotal() {
    return this.items.reduce((acc, item) => {
      return acc + item.price * item.noOfTickets;
    }, 0);
  }

  getTotalTickets() {
    return this.items.reduce((acc, item) => {
      return acc + item.noOfTickets;
    }, 0);
  }

  getItemsAmount(price: number, noOfTickets: number) {
    return price * noOfTickets;
  }

  getTotalItems(){
    return this.items.length;
  }

}


