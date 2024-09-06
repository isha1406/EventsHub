import { Time } from "@angular/common";
import { Category } from "./category";

export interface Events {
    eventId:number,
    eventName:string,
	description:string,
	price:number,
    quantity:number,
    location:string,
    image:string,
    date:Date,
    time:Time,
    categories:Category
}
