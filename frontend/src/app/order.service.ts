import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { OrderItem } from './cart/order-table/order-item.model';
import { Food } from './foods/food-model';
import { Subject } from 'rxjs';
import { AuthService } from './auth/auth.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { UserModel } from './auth/user.model';
import { CouponModel } from './profile/coupon.model';

@Injectable({ providedIn: "root" })
export class OrderService {


    orderItems: OrderItem[] = [];
    itemCount: number = 0;
    totalCost: number = 0;
    private itemCountListener = new Subject<number>();
    private itemEmptyListener = new Subject<{ isEmpty: boolean, totalCost: number }>();
    private url: string = "http://localhost:8081";

    constructor(private http: HttpClient, private authService: AuthService, private router: Router) { }

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

    takeAnOrder(user:UserModel,coupon:CouponModel) {
        var order;

        const orders: { quantity: number, item: { id: number } }[] = [];
        this.orderItems.forEach(item => {
            orders.push({
                quantity: item.quantity,
                item: {
                    id: item.id
                }
            });
        });
        if (this.authService.getUser()) {
            if(coupon){
                this.totalCost -= this.totalCost * coupon.percentage;
            }
            order = {
                coupons: [{id:coupon.id}],
                order: {
                    totalCost: this.totalCost,
                    startTime: "2019-11-27 12:58",
                    endTime: "2019-11-27 13:58",
                    orders: orders,
                    user: {
                        id: this.authService.getUser().id
                    }
                }
            };
        } else {

            order = {
                order: {
                    totalCost: this.totalCost,
                    startTime: "2019-11-27 12:58",
                    endTime: "2019-11-27 13:58",
                    orders: orders,
                    user: {
                        login: "asd",
                        password: "valami",
                        firstName: user.firstname,
                        lastName: user.lastname,
                        email: user.email,
                        phoneNumber: user.phonenumber,
                        addresses: user.addresses
                    }
                }
            }
        }

            console.log(order);
            this.http.post(this.url + "/orders", order).subscribe(res => {
                this.authService.openSnackBar("Order Recorded", "Check Your Email Adress!");
                this.orderItems = [];
                this.totalCost = 0;
                this.itemCount = 0;
                this.itemCountListener.next(0);
                this.itemEmptyListener.next({ isEmpty: true, totalCost: 0 });
                this.router.navigate(['/']);
            });
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
