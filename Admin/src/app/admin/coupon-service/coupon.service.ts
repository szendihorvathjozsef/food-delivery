import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CouponService {

  private url = "http://localhost:8081";

  constructor(private http: HttpClient) { }

  addNewCoupon(name: string, percent: number){
    const coupon ={
      "name": name,
      "percent": percent,
    }
    console.log(coupon)
    return this.http.post<{id:number,percent:number,name:string}>(this.url+"/coupon-types", coupon);
  }

  getCoupon()
  {
    return this.http.get<{id: number,
      name: string,
      percent: number
      }[]>(this.url+"/coupon-types");
  }

  deleteCoupon(id:number)
  {
    console.log(id);
    return this.http.delete(this.url+"/coupon-types/"+id);
  }
}
