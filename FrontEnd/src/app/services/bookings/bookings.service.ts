import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BookingsService {

  constructor(private http:HttpClient) { }

  private API_URL = 'http://localhost:8080/api/pdf/createpdf';

  getAllBookings(){
  return this.http.get("http://localhost:8080/api/admin/booking/viewall");
  }

  getBookingsByUserId(uid:number){
    return this.http.get("http://localhost:8080/api/user/booking/getbyuser/"+uid);
  }

  getTicket(bId:number,bItemId:number)
  {
    return this.http.get(`${this.API_URL}/${bId}/${bItemId}`);
    //return this.http.get("http://localhost:8080/api/user/booking/getbyuser/"+bid);
  }
}
