import { Component, OnInit } from '@angular/core';
import { Food } from './food-model';
import { MatDialog } from '@angular/material/dialog';
import foods from '../../assets/foods.json';
import { FoodService } from './food.service';

@Component({
  selector: 'app-foods',
  templateUrl: './foods.component.html',
  styleUrls: ['./foods.component.scss']
})
export class FoodsComponent implements OnInit {

  itemTypes = [];
  foods: Food[] = [];

  constructor(public dialog: MatDialog,private foodService:FoodService) { }

  ngOnInit() {
    this.foodService.getTypes().subscribe( res => {
      res.forEach(item => {
        if(!this.itemTypes.find(type => type == item.itemType)){
          this.itemTypes.push(item.itemType);
        }

        this.foods.push({
          id: item.id,
          picture: item.imageName,
          name: item.name,
          price: item.price,
          allergens: item.allergens,
          kcal: item.kcal,
          protein: item.protein,
          fat: item.fat,
          carbs: item.carbs,
          foodType: item.itemType,
        });
      });
      
      console.log(res);
    });
  }

}
