import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdressModel } from './adress.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = "http://localhost:8081";
  private isAuth = false;
  constructor(private http: HttpClient) { }

  loginUser(username: string, password: string, rememberMe:boolean){
    const user = {
      username: username,
      password: password,
      rememberMe: rememberMe
    }
    this.http.post<{ isSucess:boolean }>(this.url,user);
  }

  registerUser(username:string, password:string, firstName:string, lastName:string, email:string, addresses:AdressModel[]){
    const registrationDetails = {
      login: username,
      password: password,
      firstName: firstName,
      lastName: lastName,
      email: email,
      addresses: addresses
    };
    this.http.post(this.url+"/register", registrationDetails).subscribe(res => {
      console.log(res);
      console.log("registered");
    });
  }



  getAuth(){
    return this.isAuth;
  }
}
