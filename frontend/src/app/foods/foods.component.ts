import { Component, OnInit } from '@angular/core';
import { Food } from './food-model';
import { MatDialog } from '@angular/material/dialog';
import foods from '../../assets/foods.json';

@Component({
  selector: 'app-foods',
  templateUrl: './foods.component.html',
  styleUrls: ['./foods.component.scss']
})
export class FoodsComponent implements OnInit {

  itemTypes = ["Pizza's","Hamburgers","Pasta's","Desserts","Drinks"];
  foods: Food[] = [];

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
    foods.foods.forEach(food => {
      this.foods.push(food);
    });
  }

}
