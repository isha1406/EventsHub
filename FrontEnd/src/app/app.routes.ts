import { Routes } from '@angular/router';
import { BookingComponent } from './pages/admin/booking/booking.component';
import { CategoriesComponent } from './pages/admin/categories/categories.component';
import { EventsComponent } from './pages/admin/events/events.component';
import { LayoutComponent } from './pages/admin/layout/layout.component';
import { LoginComponent } from './pages/admin/login/login.component';
import { HomeComponent } from './pages/home/home/home.component';
import { AboutusComponent } from './pages/website/aboutus/aboutus/aboutus.component';
import { CartComponent } from './pages/website/cart/cart.component';
import { CategoryEventsComponent } from './pages/website/category-events/category-events.component';
import { LandingComponent } from './pages/website/landing/landing.component';
import { PaymentComponent } from './pages/website/payment/payment/payment.component';
import { TicketComponent } from './pages/website/ticket/ticket/ticket.component';
import { ViewBookingComponent } from './pages/website/view-booking/view-booking.component';
import { WebEventsComponent } from './pages/website/web-events/web-events.component';
import { authGuard } from './_auth/auth.guard';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'login',
        component: LoginComponent
    },

    // {
    //     path: 'allevents',
    //     component: WebEventsComponent
    // },
    {
        path: '',
        component: LandingComponent,
        children: [
            {
                path: 'allevents',
                component: WebEventsComponent,
                canActivate: [authGuard],
                data: { role: ['USER'] }
            },

            {
                path: 'events/:id',
                component: CategoryEventsComponent,
                canActivate: [authGuard],
                data: { role: ['USER'] }
            },

            {
                path: 'cart',
                component: CartComponent,
                canActivate: [authGuard],
                data: { role: ['USER'] }
            },
            {
                path: 'payment',
                component: PaymentComponent,
                canActivate: [authGuard],
                data: { role: ['USER'] }
            },

            {
                path: 'viewbookings',
                component: ViewBookingComponent,
                canActivate: [authGuard],
                data: { role: ['USER'] }
            },
            {
                path: 'aboutus',
                component: AboutusComponent,
                canActivate: [authGuard],
                data: { role: ['USER'] }
            },
        ]
    },

    {
        path: 'ticket',
        component: TicketComponent,
        canActivate: [authGuard],
        data: { role: ['USER'] }
    },


    {
        path: '',
        component: LayoutComponent,
        children: [
            {
                path: 'events',
                component: EventsComponent,  
                canActivate:[authGuard],
                data:{role:['ADMIN']}
            },
            {
                path: 'category',
                component: CategoriesComponent,
                title: 'Category',  
                canActivate:[authGuard],
                data:{role:['ADMIN']}
            },
            {
                path: 'bookings',
                component: BookingComponent,  
                canActivate:[authGuard],
                data:{role:['ADMIN']}
            }
        ]
    }
];
