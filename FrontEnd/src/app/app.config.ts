import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, ErrorHandler } from '@angular/core';
import { provideRouter } from '@angular/router';
import { GlobalErrorHandler } from '../../global-error-handler';

import { routes } from './app.routes';
import { errorInterceptorInterceptor } from './services/errorInterceptor/error-interceptor.interceptor';
import { customInterceptor } from './services/interceptor/custom.interceptor';

export const appConfig: ApplicationConfig = {
  // providers: [
  //   provideRouter(routes),
  //   provideHttpClient(),
  //    provideHttpClient(withInterceptors([customInterceptor,errorInterceptorInterceptor])), 
  // ]

  providers: [provideRouter(routes),provideHttpClient(withInterceptors([customInterceptor,errorInterceptorInterceptor])),{
    provide: ErrorHandler,
     useClass: GlobalErrorHandler
  }]
};
