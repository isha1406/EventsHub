import { Component, inject } from '@angular/core';
import { ViewBookingComponent } from '../../view-booking/view-booking.component';

@Component({
  selector: 'app-ticket',
  standalone: true,
  imports: [ViewBookingComponent],
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.css'
})
export class TicketComponent {

  // x=inject(ViewBookingComponent);
  

  bookingItemObj1=JSON.parse(localStorage.getItem('bobj') || '[]');
  //   "noOfTickets":0,
  //   "bookingEventsPrice":0,
  //   "date":"",
  //   "time":"",
  //   "image":""
  //  }
  
  download()
  {
    window.print();
  }
  
   
}
