import { Component, OnInit, Input } from '@angular/core';
import { OrderItem } from './order-item.model';
import { OrderService } from 'src/app/order.service';

@Component({
  selector: 'app-order-table',
  templateUrl: './order-table.component.html',
  styleUrls: ['./order-table.component.scss']
})
export class OrderTableComponent implements OnInit {

  isEditable:boolean = false;
  displayedColumns: string[] = ['name', 'quantity', 'price','remove'];
  dataSource: OrderItem[] = [];
  @Input('maxquantity') maxQuantity: number = 10; // defaul 10
  quantityArray:number[] = [];

  constructor(public orderService: OrderService) { }

  ngOnInit() {
    this.initQuantityArray();
    this.dataSource = this.orderService.getOrderItems();
    if(this.dataSource.length == 0){
      this.dataSource.push({
         id: "noItemInCart", name: "There Is No Item In Your Cart", quantity: NaN, price: NaN
      });
    }
  }

  initQuantityArray(){
    for (var _i = 0; _i < this.maxQuantity; _i++) {
      this.quantityArray.push(_i+1);
    }
  }

  removeItem(id:string){
    if(id != "noItemInCart"){
      const orderIndex: number = this.orderService.findOrderItemById(id);
  
      if(orderIndex != -1){
        
        if(this.dataSource.length == 0){
          this.dataSource.push({
             id: "noItemInCart", name: "There Is No Item In Your Cart", quantity: NaN, price: NaN
          });
        } else {
          this.dataSource.splice(orderIndex,1);
          this.orderService.deleteOrderItem(orderIndex);
        }
        this.dataSource = [...this.dataSource];
      }
    }
  }

  quantityChange(quantity:number, id:string){
    this.orderService.changeQuantity(quantity,id);
  }


}
