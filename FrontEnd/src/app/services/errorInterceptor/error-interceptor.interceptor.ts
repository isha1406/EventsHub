import { HttpHandler, HttpHandlerFn, HttpInterceptorFn } from '@angular/common/http';
import { throwError } from 'rxjs/internal/observable/throwError';
import { catchError } from 'rxjs/internal/operators/catchError';
import Swal from 'sweetalert2';

export const errorInterceptorInterceptor: HttpInterceptorFn = (req, next:HttpHandlerFn) => {
  return next(req).pipe(catchError((error) =>{
    console.log('Caught in errorInterceptor', error.message);
     Swal.fire(error.error.text);
    //  alert(error.error.text);
    // alert(error.text);
    // console.log(error.text);
    return throwError(() => error);
  }));
};
