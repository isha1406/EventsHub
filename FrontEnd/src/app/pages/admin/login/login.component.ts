import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginObj: Login;
  registerObj: Register;
  isLoginClicked : boolean=false;
  isRegisterClicked:boolean=false;

  login: boolean = true;
  register: boolean = false;

  constructor(private http: HttpClient, private router: Router) {
    this.loginObj = new Login();
    this.registerObj = new Register();
  }

  onRegister() {

    // if (this.registerObj.email == '' || this.registerObj.firstName == '' ||
    //   this.registerObj.lastName == '' || this.registerObj.mobileNumber == '' || this.registerObj.password == '') {
    //   alert("All Details Are Required!!")
    // }

    
      this.http.post('http://localhost:8080/auth/register', this.registerObj).subscribe(
        //   (res: any) => {
        //   if (res) {
        //     console.log("Registered Successfully");
        //     console.log(res, this.router)
        //     this.register = false;
        //     this.login = true;
        //   } 
        // }
        {
          next: (res: any) => {
            if (res) {
              // console.log("Registered Successfully");
              // console.log(res, this.router)
              this.register = false;
              this.login = true;
              Swal.fire("User Registered Successfully!");
            }
          }
          ,
          error: (error: any) => {
            console.log(error);
            Swal.fire(error.error.text);
            // alert(error.error.text);
          }
        }
      )
    
  }


  onLogin() {
    // if (this.loginObj.username == '' || this.loginObj.password == '') {
    //   alert("Enter Email and Password");
    // }

     
    this.http.post("http://localhost:8080/auth/login", this.loginObj).subscribe(
      {
        next: (res: any) => {
          Swal.fire({
            icon: "success",
            title: "Login Success, Welcome!",
            showConfirmButton: false,
            timer: 1000
          });
          // alert("Login Success, Welcome!");
          localStorage.setItem('loginUserToken', res.token);
          localStorage.setItem('loginUserId', res.user.userId);
          localStorage.setItem('loginUserName', res.user.firstName);
          localStorage.setItem('loginUserRole', res.user.role);
          if (localStorage.getItem('loginUserRole') == 'USER') {
            this.router.navigate(['/allevents'])
          }
          else {
            this.router.navigate(['/events']);
          }
        }
        ,
        error: (error: any) => {
          Swal.fire(error.error.text);
        }
      }
    )
    
  }

  visiblityLogin() {
    this.login = true;
    this.register = false;
  }

  visiblityRegister() {
    this.login = false;
    this.register = true;
  }

}


export class Login {
  username: string;
  password: string;
  constructor() {
    this.username = '';
    this.password = '';
  }
}

export class Register {
  email: string;
  firstName: string;
  lastName: string;
  mobileNumber: string;
  password: string;
  constructor() {
    this.email = '';
    this.firstName = '';
    this.lastName = '';
    this.mobileNumber = '';
    this.password = '';
  }
}











