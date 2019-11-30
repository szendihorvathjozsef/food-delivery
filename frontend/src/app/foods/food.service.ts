import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  private url: string = "http://localhost:8081";
  constructor(private http: HttpClient) { }

  getTypes() {
    return this.http.get<{
      id:number,
      name:string,
      price:number,
      protein:number,
      fat:number,
      carbs:number,
      imageName:string,
      itemType:string,
      allergens:string[],
      kcal:number
    }[]>(this.url + "/items");
  }
}
