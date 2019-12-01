import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AllergenService {

  private url = "http://localhost:8081";

  constructor(private http: HttpClient) { }

  addNewAllergen(allergenparam: string){
    const allergen = {
      allergen:allergenparam
    }
    return this.http.post<string>(this.url+"/item-allergens",allergen);
  }
}
