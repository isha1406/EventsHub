import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) { }

  loginUser(obj: any){
    return this.http.post("http://localhost:8080/auth/login",obj);
  }

  register(obj: any){
    return this.http.post("http://localhost:8080/auth/register",obj);
  }
  
}
