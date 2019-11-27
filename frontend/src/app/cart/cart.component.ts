import { Component, OnInit } from '@angular/core';
import { Food } from '../foods/food-model';
import { OrderService } from '../order.service';
import { Subscription } from 'rxjs';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  private emptyListenerSub: Subscription;
  paymentMethods: string[] = ["Card", "Cash", "Nice Card"];
  isEmpty = true;
  totalCost = 0;

  constructor(public orderService: OrderService) { }

  ngOnInit() {
    this.isEmpty = this.orderService.isOrderItemEmpty();
    this.totalCost = this.orderService.getTotalCost();
    this.emptyListenerSub = this.orderService.getItemEmptyListener()
    .subscribe( res => {
      this.isEmpty = res.isEmpty;
      this.totalCost = res.totalCost;
    });
  }

  onSubmit(form:NgForm){
    if(form.valid){
      console.log(form.value);
    }
  }
}