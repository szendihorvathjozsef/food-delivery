import { Component, OnInit } from '@angular/core';
import { Food } from './food-model';

@Component({
  selector: 'app-foods',
  templateUrl: './foods.component.html',
  styleUrls: ['./foods.component.scss']
})
export class FoodsComponent implements OnInit {

  /*foods: Food[] = [
    {id: 1, name:"Kacsa", }
  ]*/

  constructor() { }

  ngOnInit() {
  }

}
