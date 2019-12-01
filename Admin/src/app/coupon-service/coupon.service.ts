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
    return this.http.post<any>(this.url+"/coupons", coupon);
  }

  getCoupon()
  {
    return this.http.get<{id: number,
      name: string,
      percent: number,
      itemType: string,
      user: string
      }[]>(this.url+"/coupons");
  }

  deleteCoupon(id:number)
  {
    console.log(id);
    return this.http.delete(this.url+"/coupons/"+id);
  }
}
