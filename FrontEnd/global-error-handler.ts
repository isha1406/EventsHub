import { ErrorHandler, Injectable } from "@angular/core";
 
@Injectable()
export class GlobalErrorHandler implements ErrorHandler{
    handleError(error: any): void {
        //  console.log("An error has occurred", error?.message)
    }
}