import { BookingItems } from "./booking-items";
import { User } from "./user";

export interface Booking {
    bookingId:number;
	user:User;
	bookingItemsList:BookingItems[];
	totalAmount:number;
	bookingStatus:string;
}
