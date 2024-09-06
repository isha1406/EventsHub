import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { BookingsService } from '../../../services/bookings/bookings.service';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css'
})
export class BookingComponent implements OnInit {

  constructor(private bookingSrv: BookingsService) {
  }

  bookingsList: any[] = [];

  ngOnInit(): void {
    this.getAllBookings();
  }

  getAllBookings() {
    this.bookingSrv.getAllBookings().subscribe(
      (res: any) => {
        console.log(res);
        this.bookingsList = res;
      }
    );
  }

}
