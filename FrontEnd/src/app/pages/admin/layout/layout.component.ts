import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, Router } from '@angular/router';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

  constructor(private router: Router) {}

  loginUserName:any=localStorage.getItem('loginUserName');

  logout(): void{
    localStorage.removeItem('loginUserId');
    localStorage.removeItem('loginUserToken');
    localStorage.removeItem('cartItems');
    localStorage.removeItem('loginUserName');
    this.router.navigateByUrl('/login'); 
  }
}
