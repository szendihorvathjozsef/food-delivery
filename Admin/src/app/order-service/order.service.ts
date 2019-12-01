import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MegrenelesModel } from '../pending-rentals/megrendeles.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private url = "http://localhost:8081";

  constructor(private http: HttpClient) { }

  getPendingRentals()
  {
    return this.http.get<{
      "orders": {
        "id": number
        "totalCost": number,
        "startTime": string,
        "endTime": string,
        "orders": [
            {
                "quantity": number,
                "item": {
                  "name": string
                  "id": number
                }
            }
        ],
        "user": {
            "login": string,
            "password": string,
            "firstName": string,
            "lastName": string,
            "email": string,
            "phoneNumber": string,
            "addresses": 
                {
                    "postCode": number,
                    "address": string,
                    "type": string
                }[]
        }
      }
    }[]>(this.url + "/orders/in-progress/")
  }

  modifyRentals(id: number[])
  {
    console.log(id);
    return this.http.post<{isSuccess: boolean}>(this.url, id);
  }

  getDailyStat(date:string)
  {
    return this.http.get<{name:string, quantity:number }[]>(this.url+"/statistics/day/"+date);
  }

  getPeriodStat(startDate: string, endDate: string)
  {
    console.log(startDate + " " + endDate);
    return this.http.get<{date: Date, income: number }[]>(this.url + "/statistics/between/"+startDate +"/"+endDate);
  }
}
