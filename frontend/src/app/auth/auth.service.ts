import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdressModel } from './adress.model';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = "http://localhost:8081";
  private isAuth = false;
  private token = null;
  private username = null;
  private authListener = new Subject<boolean>();

  constructor(private http: HttpClient, private router: Router, private _snackBar: MatSnackBar) { }

  loginUser(username: string, password: string, rememberMe: boolean) {
    const user = {
      username: username,
      password: password,
      rememberMe: rememberMe
    }
    this.http.post<{ token: string }>(this.url + "/authentication", user).subscribe(res => {
      if (res.token) {
        this.token = res.token;
        this.username = username;
        this.isAuth = true;
        this.authListener.next(true);
        this.saveAuthData(this.token,this.username);
        this.router.navigate(['/']);
        this.openSnackBar("Successfully Logged In","Welcome!");
      } else {
        console.log("Wrong Password or Username");
      }
    });
  }

  registerUser(username: string, password: string, firstName: string, lastName: string, email: string, addresses: AdressModel[]) {
    console.log(addresses);
    const registrationDetails = {
      login: username,
      password: password,
      firstName: firstName,
      lastName: lastName,
      email: email,
      addresses: addresses
    };
    this.http.post(this.url + "/register", registrationDetails).subscribe(res => {
      console.log(res);
      this.router.navigate(['/']);
      this.openSnackBar("Successfully Registered!","");
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  logout() {
    this.token = null;
    this.isAuth = false;
    this.username = null;
    this.authListener.next(false);
    this.clearAuthData();
    this.router.navigate(['/']);
  }

  autoAuthUser(){
    const authInformation = this.getAuthData();
    if(authInformation) {
      this.token = authInformation.token;
      this.username = authInformation.username;
      this.isAuth = true;
      this.authListener.next(true);
    }
  }

  private saveAuthData(token:string,username:string){
    localStorage.setItem('token', token);
    localStorage.setItem('username', username);
  }

  private clearAuthData() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  }

  private getAuthData(){
    const token = localStorage.getItem('token');
    const username = localStorage.getItem('username');
    if(!token){
      return;
    } 
    return {
      token: token,
      username: username
    }
  }

  getUserName(){
    return this.username;
  }

  getAuthListener() {
    return this.authListener;
  }

  getAuth() {
    return this.isAuth;
  }

  getToken() {
    return this.token;
  }
}
