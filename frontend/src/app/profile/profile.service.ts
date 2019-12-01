import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdressModel } from '../auth/adress.model';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private url: string = "http://localhost:8081";

  constructor(private http: HttpClient) { }

  getUserInformation(username: string) {
    return this.http.get<{
      id: number,
      login: string,
      firstName: string,
      lastName: string,
      email: string,
      status: string,
      createdBy: string,
      createdDate: string,
      lastModifiedBy: string,
      lastModifiedDate: string,
      authorities: string[],
      addresses: AdressModel[]
    }>(this.url + `/users/${username}`);
  }

  updateUserInformation(username:string, firstName:string, lastName:string, email:string,phoneNumber:string,addresses:AdressModel[]) {
    const account = {
      login: username,
      firstName: firstName,
      lastName: lastName,
      email: email,
      phoneNumber: phoneNumber,
      addresses: addresses
    }
    
    return this.http.post(this.url + "/account", account);
  }

  getCoupons() {
    return this.http.get<{id:number,type:{name:string,percent:number}}[]>(this.url + "/coupons/unused");
  }

  changePassword(currentPassword: string, newPassword:string){
    const passwordChange = {
      currentPassword: currentPassword,
      newPassword: newPassword
    }
    return this.http.post(this.url+"/account/change-password", passwordChange);
  }
}
