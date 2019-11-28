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
        this.isAuth = true;
        this.authListener.next(true);
        this.router.navigate(['/']);
        this.openSnackBar("Successfully Logged In","Welcome!");
      } else {
        console.log("Wrong Password or Username");
      }
    });
  }

  registerUser(username: string, password: string, firstName: string, lastName: string, email: string, addresses: AdressModel[]) {
    const registrationDetails = {
      login: username,
      password: password,
      firstName: firstName,
      lastName: lastName,
      email: email,
      address: addresses
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
    this.authListener.next(false);
    this.router.navigate(['/']);
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
