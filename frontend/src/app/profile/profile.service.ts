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

  updateUserInformation(data:{username: string, firstName: string, lastName: string, email: string, status: string, authorities: string[]}) {
    const user = {
      login: data.username,
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      status: data.status,
      authorities: data.authorities
    }

    return this.http.put(this.url + "/users", user);
  }

  getCoupons() {
    return this.http.get<{id:number,name:string,percent:number,itemType:string,user:string}[]>(this.url + "/coupons");
  }

  changePassword(currentPassword: string, newPassword:string){
    const passwordChange = {
      currentPassword: currentPassword,
      newPassword: newPassword
    }
    return this.http.post(this.url+"/account/change-password", passwordChange);
  }
}
