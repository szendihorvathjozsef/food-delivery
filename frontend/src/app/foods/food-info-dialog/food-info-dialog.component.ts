import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Food } from '../food-model';

@Component({
  selector: 'app-food-info-dialog',
  templateUrl: './food-info-dialog.component.html',
  styleUrls: ['./food-info-dialog.component.scss']
})
export class FoodInfoDialogComponent{

  protein:string = "55%";
  avrageKcal: number;
  constructor(@Inject(MAT_DIALOG_DATA) public food:Food) {
      this.avrageKcal = (food.kcal / 1800)*100;
  }



}
