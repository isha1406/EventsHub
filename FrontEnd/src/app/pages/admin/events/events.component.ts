import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { EventService } from '../../../services/event/event.service';

@Component({
  selector: 'app-events',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './events.component.html',
  styleUrl: './events.component.css'
})
export class EventsComponent implements OnInit {

  eventsObj: any = {
    "eventId": 0,
    "eventName": "",
    "description": "",
    "price": "",
    "quantity": "",
    "location": "",
    "image": "",
    "date": "",
    "time": "",
  }

  categoryObj: any = {
    "categoryId": "",
    "categoryName": ""
  }


  isSidePanelVisible: boolean = false;
  resetVisible: boolean = true;
  newString: boolean = true;
  categoryVisible: boolean = true;


  categoryList: any[] = [];
  eventsList: any[] = [];
  // pastEventsList: any[]=[];
  upcomingEventsList: any[] = [];


  constructor(private eventSrv: EventService) {
  }

  ngOnInit(): void {
    this.getEvents();
    this.getCategory();
    // this.getPastEvents();
    this.getUpcomingEvents();
  }

  getCategory() {
    this.eventSrv.getAllCategory().subscribe(
      (res: any) => {
        this.categoryList = res;
      }
    )
  }

  getEvents() {
    this.eventSrv.getAllEvents().subscribe(
      (res: any) => {
        this.eventsList = res;
      }
    );
  }

  // getPastEvents(){
  //   this.eventSrv.pastEvents().subscribe(
  //     {
  //       next:(res:any)=>{
  //         this.pastEventsList=res;
  //       },
  //       error:(error:any)=>{
  //         alert(error.error.text);
  //       }
  //     }
  //       )
  // }

  getUpcomingEvents() {
    this.eventSrv.getUpcomingEvents().subscribe(
      (res: any) => {
        this.upcomingEventsList = res;
      }
    )
  }

  onUpdate() {
    if (this.eventsObj.eventName == '' || this.eventsObj.description == '' || this.eventsObj.price == '' ||
      this.eventsObj.quantity == '' || this.eventsObj.location == '' || this.eventsObj.image == '' ||
      this.eventsObj.date == '' || this.eventsObj.time == '')
      alert("Fill All Details");

    else {
      this.eventSrv.updateEvent(this.eventsObj.eventId, this.eventsObj).subscribe(
        (res: any) => {
          if (res) {
            console.log("updated" + res);
            // alert("Event Updated");
            Swal.fire({
              icon: "success",
              title: "Event Updated",
              showConfirmButton: false,
              timer: 1500
            });
            this.getEvents();
          }
        }
      )
      this.closeSidePanel();
    }
  }

  onSave() {

    console.log(this.eventsObj);
    console.log(this.categoryObj);

    if (this.eventsObj.eventName == '' || this.eventsObj.description == '' || this.eventsObj.price == '' ||
      this.eventsObj.quantity == '' || this.eventsObj.location == '' || this.eventsObj.image == '' ||
      this.eventsObj.date == '' || this.eventsObj.time == '' || this.categoryObj.categoryName == '')
      alert("Fill All Details");

    else {
      this.eventSrv.saveEvent(this.eventsObj, this.categoryObj.categoryId).subscribe(
        (res: any) => {
          if (res) {
            console.log(res);
            // alert("Event Added");
            Swal.fire({
              icon: "success",
              title: "Event Added",
              showConfirmButton: false,
              timer: 1500
            });
            this.getEvents();
          }
        }
      )
      this.closeSidePanel();
    }
  }


  onEdit(item: any) {
    this.eventsObj = item;
    this.openSidePanel();
    this.newString = false;
    this.resetVisible = false;
    this.categoryVisible = false;
  }

  onDelete(item: any) {
    // const isDelete = confirm("Are you sure?");
    // if (isDelete) {
    //   this.eventSrv.deleteEvent(item.eventId).subscribe(
    //     (res: any) => {
    //       if (res) {
    //         alert("Event Deleted");
    //         this.getEvents();
    //         this.onReset();
    //       }
    //     }
    //   )
    // }
    Swal.fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        this.eventSrv.deleteEvent(item.eventId).subscribe(
          (res: any) => {
            if (res) {           
              this.getEvents();
            }
          }
        )  
      }
    });
  }


  onReset() {
    this.eventsObj = {
      "eventId": 0,
      "eventName": "",
      "description": "",
      "price": "",
      "quantity": "",
      "location": "",
      "image": "",
      "date": "",
      "time": "",
    }

    this.categoryObj = {
      "categoryId": "",
      "categoryName": ""
    }
  }

  openSidePanel() {
    this.isSidePanelVisible = true;
    this.newString = true;
    this.resetVisible = true;
    this.categoryVisible = true;
  }

  closeSidePanel() {
    this.isSidePanelVisible = false;
    this.onReset();
  }

}
