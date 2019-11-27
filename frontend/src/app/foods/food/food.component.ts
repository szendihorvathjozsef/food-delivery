import { Component, OnInit, Input } from '@angular/core';
import { Food } from '../food-model';
import { FoodInfoDialogComponent } from '../food-info-dialog/food-info-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { OrderService } from 'src/app/order.service';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.scss']
})
export class FoodComponent {

  @Input('food') food:Food;
  constructor(
    public dialog:MatDialog, 
    public snackbar: MatSnackBar,
    public orderService: OrderService) 
    { }

  openDialog(food:Food){
    this.dialog.open(FoodInfoDialogComponent,{
      width: '70%',
      data: food
    });
  }

  appItemToCart(food:Food){
    this.orderService.addOrderItem(food);
    this.snackbar.open(food.name +" added successfully to Cart", "Close", {
      duration: 2000,
    });
    
  }

}
