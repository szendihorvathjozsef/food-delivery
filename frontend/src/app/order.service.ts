import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { OrderItem } from './order-table/order-item.model';
import { Food } from '../foods/food-model';
import { Subject } from 'rxjs';
import { AuthService } from './auth/auth.service';
import { DatePipe } from '@angular/common';

@Injectable({ providedIn: "root" })
export class OrderService {


    orderItems: OrderItem[] = [];
    itemCount: number = 0;
    totalCost: number = 0;
    private itemCountListener = new Subject<number>();
    private itemEmptyListener = new Subject<{ isEmpty: boolean, totalCost: number }>();
    private url: string = "http://localhost:8081";

    constructor(private http: HttpClient,private authService:AuthService,private datePipe: DatePipe) { }

    addOrderItem(food: Food) {
        this.itemCount++;
        this.itemCountListener.next(this.itemCount);
        this.totalCost += food.price;
        this.itemEmptyListener.next({ isEmpty: false, totalCost: this.totalCost });
        const index = this.findOrderItemById(food.id);
        if (index == -1) {
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

    deleteOrderItem(index) {
        this.itemCount -= this.orderItems[index].quantity;
        this.totalCost -= (this.orderItems[index].quantity * this.orderItems[index].price);
        this.itemCountListener.next(this.itemCount);
        this.orderItems.splice(index, 1);
        this.itemEmptyListener.next({ isEmpty: this.isOrderItemEmpty(), totalCost: this.totalCost });
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

    isOrderItemEmpty(): boolean {
        if (this.orderItems.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    getTotalCost(): number {
        var total = 0;
        this.orderItems.forEach(item => {
            total += item.quantity * item.price
        });

        return total;
    }

    changeQuantity(quantity: number, id: number) {
        const itemIndex: number = this.findOrderItemById(id);
        this.itemCount += (quantity - this.orderItems[itemIndex].quantity);
        this.orderItems[itemIndex].quantity = quantity;
        this.itemCountListener.next(this.itemCount);
    }

    takeAnOrder(totalCost:number,startTime:Date, orders:{quantity:number,item:{ id:number }}[]) {
        var id = null;
        if(this.authService.getAuth()){
            this.http.get<{id:number}>(this.url + `/users/${this.authService.getUserName()}`).subscribe(res => {
                id = res.id;
            });
        }
        //const now = this.datePipe.transform(Date.now(),)  
        const order = {
            totalCost: this.totalCost,
            startTime: startTime,
            endTime: null,
            orders: orders,
            user: {
                id: id
            }
        }
        this.http.post(this.url + "/orders", order);
    }

    // Getters

    getItemCountListener() {
        return this.itemCountListener;
    }

    getItemEmptyListener() {
        return this.itemEmptyListener;
    }

    getOrderItems() {
        return [...this.orderItems];
    }
}
