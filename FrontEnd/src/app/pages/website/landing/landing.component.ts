import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { EventService } from '../../../services/event/event.service';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [RouterOutlet, CommonModule, RouterLink],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

  eventsList: any[] = [];
  categoryList: any[] = [];
  

  loginUserName: any = localStorage.getItem('loginUserName');
  // loginbtn:boolean=true;
  // token:any=localStorage.getItem('loginUserToken');
  

  constructor(private eventSrv: EventService, private router: Router) {
    // this.getEvents();
    this.getCategory();
    // this.checkloginUser();
  }

  // ngOnInit(): void {
  //   this.getEvents();
  //   this.getCategory();
  // }

  // getEvents(){
  //   this.eventSrv.getAllEvents().subscribe(
  //     {
  //       next:(res:any)=>{
  //       this.eventsList=res;
  //     },
  //     error: (error: any) => {
  //       alert(error.error.text);
  //     }     
  //   }
  //   );
  // }

  getCategory() {
    this.eventSrv.getAllCategory().subscribe(
      (res: any) => {
        this.categoryList = res;
      }
    )
  }

  navigateToEvents(id: number) {
    this.router.navigate(['/events', id])
  }

  logout(): void {   
    localStorage.removeItem('loginUserId');
    localStorage.removeItem('loginUserToken');
    localStorage.removeItem('cartItems');
    localStorage.removeItem('loginUserName');
    localStorage.removeItem('loginUserRole');
    localStorage.removeItem('bobj');
    // this.loginbtn=true;
     this.router.navigate(['/login']);
  }

  // login(){
  //   this.router.navigate(['/login']);
  // }

  bookings() {
    this.router.navigate(['/viewbookings']);
  }

  viewCart() {
    this.router.navigate(['/cart']);
  }

  eventshub() {
    this.router.navigate(['/allevents']);
  }

  aboutus() {
    this.router.navigate(['/aboutus']);
  }

  // checkloginUser(){
  //   if(localStorage.getItem('loginUserToken')){
  //   this.loginbtn=false;
  //   }
  //   else{
  //   this.loginbtn=true;
  //   }
  // }

}
