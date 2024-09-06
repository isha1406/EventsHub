import { Events } from "./events";

export interface BookingItems {
    bookingItemId:number;
	noOfTickets:number;
	bookingEventsPrice:number;
	events:Events;
}