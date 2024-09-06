import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { CartService } from '../../../../services/cart/cart.service';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent {
  eventsItemReqObj: eventsItemReq;
  // order:any;
  // items:any[]=[]
  // cartItems:number;
  // isCartEmpty:boolean=true;
  // todaysDate:Date= new Date();
  // todaysDateString:string=this.todaysDate.toISOString().substring(0,10);
 
  cartService = inject(CartService);
  // orderService=inject(OrderService);
 
   isButtonClicked : boolean=false;
  paymentObj:any={
    "userName":"",
    "cardNumber":"",
    "expiry":"",
    "cvv":""
  }
 
  constructor(private router:Router){
    this.eventsItemReqObj = new eventsItemReq();
    // this.cartItems=0;
    // // this.todaysDate=Date.now()
    // console.log(this.todaysDate)
    // console.log(this.todaysDateString)
    // this.todaysDate=Date.now();
  }
 
  id: any = JSON.parse(localStorage.getItem('loginUserId') || '');
  items: any[] = []

  // id:any;
  // userId:any=JSON.parse(localStorage.getItem('loginUserId')||'');
  // totalDays:any;
 
 
  // ngOnInit(){
  //   this.items=JSON.parse(localStorage.getItem('cartItems')||'[]');
  //   this.cartItems=this.items.length;
  //   if(this.cartItems!=0){this.isCartEmpty=false}
  // }
 
  placeOrder() {
    this.isButtonClicked=true;
    this.items= JSON.parse(localStorage.getItem('cartItems') || '[]');
    console.log("ITEMS -> ",this.items)

    let  arrayOfObjects: eventsItemReq[] = [];
    for (const data of this.items) {
      console.log("length -->" ,data.noOfTickets)
      arrayOfObjects.push({ eventId: data.eventId, noOfTickets: data.noOfTickets });
    }


    if(arrayOfObjects.length == 0)
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
 
  //  ngOnDestroy(){
  //   this.interval.clear();
  //  }

 
  cancelPayment(){
    this.router.navigate(['cart'])
  }
 
  // calculateNoOfDays(item:any,startDateString:string,endDateString:string){
  //   console.log("calculate called , item",item);
   
  //   // let startDateHtml=document.getElementById('startDate')  as HTMLInputElement;
  //   // let endDateHtml=document.getElementById('endDate') as HTMLInputElement;
  //   // let startDateString=startDateHtml?.value;
  //   // let endDateString=endDateHtml?.value;
 
  //   // console.log(startDateHtml);
  //   // console.log(endDateHtml);
 
  //   let startDate:Date = new Date(startDateString);
  //   let endDate:Date = new Date(endDateString);
 
  //   console.log(startDate);
  //   console.log(endDate);
 
 
  //   // let Daysss = endDate.getDate() - startDate.getDate();
  //   // console.log("dayssss",Daysss);
  //   // item.noOfDays=Daysss
  //   var diff = Math.abs(endDate.getTime() - startDate.getTime());
  //   var diffDays = Math.ceil(diff / (1000 * 3600 * 24));
  //   item.noOfDays=diffDays
  //   // console.log("Diff in Days: " + diffDays);
  //   let serviceItem =this.items.find((i)=>i.serviceId===item.serviceId);
   
  //   serviceItem.noOfDays=diffDays;
  //   console.log(serviceItem,serviceItem.noOfDays);
 
  //   console.log(item)
  //   // this.items = this.items.map(i=> { return i.serviceId=== item.serviceId ? serviceItem: i});
  //   // console.log(this.items)
  //   // var noOfDays = this.endDate.getDate() - this.startDate.getDate();
  //   // console.log(noOfDays);
  // }
 
 
}
 
export class eventsItemReq {
  eventId: string;
  noOfTickets: string;
  constructor() {
    this.eventId = '';
    this.noOfTickets = '';
  }

}
