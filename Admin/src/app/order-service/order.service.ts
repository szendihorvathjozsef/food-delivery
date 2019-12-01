import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private url = "http://localhost:8081";

  constructor(private http: HttpClient) { }

  getPendingRentals() {
    return this.http.get<{
      id:number,
      orders: {
        quantity: number,
        item: {
          name: string
        }
      }[],
      totalCost:number,
      user: {
        addresses: {
          address:string,
          postCode:number
        }[],
        firstName:string,
        lastName:string,
        phoneNumber:string
      }
    }[]>
      (this.url + "/orders/in-progress");
  }

  finishRentals(id: number[]) {
    const ids = {
      "ids": id
    }
    return this.http.post<{ isSuccess: boolean }>(this.url + "/orders/finished", ids);
  }

  getDailyStat(date: string) {
    return this.http.get<{ name: string, quantity: number }[]>(this.url + "/statistics/day/" + date);
  }

  getPeriodStat(startDate: string, endDate: string) {
    console.log(startDate + " " + endDate);
    return this.http.get<{ date: Date, income: number }[]>(this.url + "/statistics/between/" + startDate + "/" + endDate);
  }
}
