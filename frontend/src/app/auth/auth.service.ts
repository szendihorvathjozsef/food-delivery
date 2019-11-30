import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdressModel } from './adress.model';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserModel } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = "http://localhost:8081";
  private isAuth = false;
  private token = null;
  private user: UserModel = null;
  private authListener = new Subject<boolean>();

  constructor(private http: HttpClient, private router: Router, private _snackBar: MatSnackBar) {   }

  loginUser(username: string, password: string, rememberMe: boolean) {
    const user = {
      username: username,
      password: password,
      rememberMe: rememberMe
    }
    this.http.post<{ token: string }>(this.url + "/authentication", user).subscribe(res => {
      if (res.token) {
        this.token = res.token;

        this.http.get<{
          id: number,
          login: string,
          firstName: string,
          lastName: string,
          email: string,
          phoneNumber: string,
          authorities: string[],
          status: string,
          addresses:AdressModel[]
        }>(this.url + "/account").subscribe(res => {
          if (res) {
            this.user = {
              id: res.id,
              username: res.login,
              firstname: res.firstName,
              lastname: res.lastName,
              email: res.email,
              phonenumber: res.phoneNumber,
              authorities: res.authorities[0],
              status: res.status,
              addresses:res.addresses
            }
            this.isAuth = true;
            
            this.saveAuthData(this.user);
            this.authListener.next(true);
            this.router.navigate(['/']);
            this.openSnackBar("Successfully Logged In", "Welcome!");
          }
        });
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
      this.openSnackBar("Successfully Registered!", "");
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
    this.clearAuthData();
    this.router.navigate(['/']);
  }

  autoAuthUser() {
    const authInformation = this.getAuthData();
    if (authInformation) {
      this.token = authInformation.token;
      this.user = authInformation.user;
      this.isAuth = true;
      this.authListener.next(true);
    }
  }

  private saveAuthData(user: UserModel) {
    localStorage.setItem('token', this.token);
    localStorage.setItem('id', user.id.toString());
    localStorage.setItem('username', user.username);
    localStorage.setItem('firstname', user.firstname);
    localStorage.setItem('lastname', user.lastname);
    localStorage.setItem('email', user.email);
    localStorage.setItem('phonenumber', user.phonenumber);
    localStorage.setItem('status', user.status);
    localStorage.setItem('authorities', user.authorities);
    localStorage.setItem('adressnum', user.addresses.length.toString());
    var i = 0;
    user.addresses.forEach(adress => {
      localStorage.setItem('postCode'+i, adress.postCode.toString());
      localStorage.setItem('adress'+i, adress.address);
      localStorage.setItem('type'+i, adress.type);
      i++;
    });


  }

  private clearAuthData() {
    localStorage.removeItem('token');
    localStorage.removeItem('id');
    localStorage.removeItem('username');
    localStorage.removeItem('firstname');
    localStorage.removeItem('lastname');
    localStorage.removeItem('email');
    localStorage.removeItem('phonenumber');
    localStorage.removeItem('status');
    localStorage.removeItem('authorities');
    localStorage.removeItem('adressnum');
    var i = 0;
    this.user.addresses.forEach(adress => {
      localStorage.removeItem(`postCode${i}`);
      localStorage.removeItem(`adress${i}`);
      localStorage.removeItem(`type${i}`);
      i++;
    });
    this.user = null;
  }

  private getAuthData() {
    const token: string = localStorage.getItem('token');
    const id: number = +localStorage.getItem('id');
    const username: string = localStorage.getItem('username');
    const firstname: string = localStorage.getItem('firstname');
    const lastname: string = localStorage.getItem('lastname');
    const email: string = localStorage.getItem('email');
    const phonenumber: string = localStorage.getItem('phonenumber');
    const status: string = localStorage.getItem('status');
    const authorities: string = localStorage.getItem('authorities');
    const adressCount = +localStorage.getItem('adressnum');
    const addresses: AdressModel[] = [];
    for (let i = 0; i < adressCount; i++) {
      addresses.push({
        postCode: +localStorage.getItem(`postCode${i}`),
        address: localStorage.getItem(`adress${i}`),
        type: localStorage.getItem(`type${i}`)
      });
      
    }

    if (!token) {
      return;
    }
    return {
      token: token,
      user:
      {
        id: id,
        username: username,
        firstname: firstname,
        lastname: lastname,
        email: email,
        phonenumber: phonenumber,
        status: status,
        authorities: authorities,
        addresses: addresses
      }
    }
  }

  modifyUser(firstName: string,lastName: string,email: string,phoneNumber: string, address: AdressModel){
    this.user.firstname = firstName;
    this.user.lastname = lastName;
    this.user.email = email;
    this.user.phonenumber = phoneNumber;
    this.user.addresses[0] = address;
    this.saveAuthData(this.user);
  }

  getUser() {
    return this.user;
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
