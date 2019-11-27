import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OrderItem } from './cart/order-table/order-item.model';
import { Food } from './foods/food-model';
import { Subject } from 'rxjs';

@Injectable({ providedIn: "root" })
export class OrderService {

    orderItems: OrderItem[] = [];
    itemCount: number = 0;
    totalCost: number = 0;
    private itemCountListener = new Subject<number>();
    private itemEmptyListener = new Subject<{isEmpty: boolean, totalCost: number}>();

    constructor(private http: HttpClient) { }

    addOrderItem(food: Food) {
        this.itemCount++;
        this.itemCountListener.next(this.itemCount);
        this.totalCost+= food.price;
        this.itemEmptyListener.next({isEmpty: false, totalCost: this.totalCost });
        const index = this.findOrderItemById(food.id);
        if(index == -1){
            this.orderItems.push({
                id: food.id,
                name: food.name,
                quantity: 1,
                price: food.price
            });
        } else {
            this.orderItems[index].quantity++;
        }

    }

    deleteOrderItem(index){
        this.itemCount-= this.orderItems[index].quantity;
        this.totalCost -= (this.orderItems[index].quantity * this.orderItems[index].price);
        this.itemCountListener.next(this.itemCount);
        this.orderItems.splice(index,1);
        this.itemEmptyListener.next({isEmpty: this.isOrderItemEmpty(), totalCost: this.totalCost });
    }

    findOrderItemById(id: number): number {
        let i = 0;
        let index = -1;
        this.orderItems.forEach(element => {
            if (element.id === id) {
                index = i;
            }
            i++;
        });
        return index;
    }

    isOrderItemEmpty():boolean {
        if(this.orderItems.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    getTotalCost(): number{
        var total = 0;
        this.orderItems.forEach(item => {
            total +=  item.quantity * item.price
        });

        return total;
    }

    changeQuantity(quantity:number,id:number){
        const itemIndex: number = this.findOrderItemById(id);
        this.itemCount += (quantity - this.orderItems[itemIndex].quantity);
        this.orderItems[itemIndex].quantity = quantity;
        this.itemCountListener.next(this.itemCount);
    }

    // Getters

    getItemCountListener(){
        return this.itemCountListener;
    }

    getItemEmptyListener(){
        return this.itemEmptyListener;
    }

    getOrderItems() {
        return [...this.orderItems];
    }
}
