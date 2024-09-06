import { CommonModule, Time } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BookingsService } from '../../../services/bookings/bookings.service';

@Component({
  selector: 'app-view-booking',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-booking.component.html',
  styleUrl: './view-booking.component.css'
})
export class ViewBookingComponent {

  constructor(private bookingSrv: BookingsService, private router: Router) {
    this.getuserbookings();
  }

  bookingItemObj:any={
    "noOfTickets":0,
    "bookingEventsPrice":0,
    "date":"",
    "time":"",
    "image":"",
    "name":""
   }

  bId:any;
  bookingsList: any[] = [];
  id: any = JSON.parse(localStorage.getItem('loginUserId') || '');

  // ngOnInit(): void {
  //   this.getuserbookings();
  // }

  getuserbookings() {
    this.bookingSrv.getBookingsByUserId(this.id).subscribe(
      (res: any) => {
        this.bookingsList = res;
      },
    )
  }


  download(noOfTickets:number, price:number, date:Date, time:Time, img:string,name:string){
      this.bookingItemObj.noOfTickets=noOfTickets;
      this.bookingItemObj.bookingEventsPrice=price;
      this.bookingItemObj.date = date;
      this.bookingItemObj.time=time;
      this.bookingItemObj.image=img;
      this.bookingItemObj.name=name;
      localStorage.setItem('bobj',JSON.stringify(this.bookingItemObj));
      this.router.navigate(['/ticket']);
  }
 

}
