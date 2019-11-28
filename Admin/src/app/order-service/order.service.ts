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
    return this.http.get<{item: MegrenelesModel[]}>(this.url)
  }

  modifyRentals(id: number[])
  {
    return this.http.post<{isSuccess: boolean}>(this.url, id);
  }

  getDailyStat(date:string)
  {
    return this.http.get<{name:string, quantity:number }[]>(this.url+"/statistics/day/"+date);
  }

  getPeriodStat(startDate: Date, endDate: Date)
  {
    return this.http.get<{item: {date: Date, income: number }[]}>(this.url)
  }
}
