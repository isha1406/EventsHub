import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Events } from '../../models/events';
import { Category } from '../../models/category';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private http:HttpClient) { }

  getAllCategory(){
   return this.http.get<Category[]>("http://localhost:8080/api/categories/getAll");
  }

  getAllEventsByCategory(id:number){
    return this.http.get<Events[]>("http://localhost:8080/api/events/getevents/"+id);
  }

  getAllEvents(){
    return this.http.get<Events[]>("http://localhost:8080/api/events/viewall");
  }

  saveEvent(obj: any,id:any){
    return this.http.post("http://localhost:8080/api/admin/events/create/"+id,obj);
  }

  updateEvent(id:any,obj:any){
    return this.http.put(" http://localhost:8080/api/admin/events/update/"+id,obj);
  }

  deleteEvent(id: any){
    return this.http.delete("http://localhost:8080/api/admin/events/delete/"+id);
  }

  pastEvents(){
    return this.http.get<Events[]>("http://localhost:8080/api/admin/events/pastevents");
  }

  getUpcomingEvents(){
    return this.http.get<Events[]>("http://localhost:8080/api/events/upcomingevents");
  }

}
