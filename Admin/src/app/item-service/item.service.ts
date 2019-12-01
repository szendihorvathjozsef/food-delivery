import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EtelModel } from '../pending-rentals/etel.model';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private url = "http://localhost:8081";


  constructor(private http: HttpClient) { }

  addNewItem (food: EtelModel){
    const item = {
      "name": food.name,
      "price": food.price,
      "kcal": food.kcal,
      "protein": food.protein,
      "fat": food.fat,
      "carbs": food.carbs,
      "itemType": food.foodType,
      "allergens": food.allergens
    }
    return this.http.post(this.url+"/items", item);
  }

  getTypes(){
    return this.http.get<string[]>(this.url+"/item-types");
  }

  addNewType(type: string){
    return this.http.post<string>(this.url+"/item-types", type);
  }

  getItems ()
  {
    return this.http.get<{id: number,
      name: string,
      price: number,
      kcal: number,
      protein: number,
      fat: number,
      carbs: number,
      imageName: string,
      itemType: string,
      allergens: string[]}[]>(this.url + "/items");
  }

  modifyItem(food: EtelModel)
  {
    const item = {
      "id": food.id,
      "name": food.name,
      "price": food.price,
      "kcal": food.kcal,
      "protein": food.protein,
      "fat": food.fat,
      "carbs": food.carbs,
      "itemType": food.foodType,
      "allergens": food.allergens
    }
    console.log(item);
    return this.http.put(this.url + "/items", item);
  }

  getAllergens ()
  {
    return this.http.get<{item: string}[]>(this.url+"/item-allergens");
  }
}
