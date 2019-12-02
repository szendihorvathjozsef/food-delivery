import { Component, OnInit } from '@angular/core';
import { EtelModel } from './etel.model';
import { OrderService } from '../order-service/order.service';
import { MegrenelesModel } from './megrendeles.model';
import { NgForm } from '@angular/forms';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { AuthService } from 'src/app/auth/auth.service';


@Component({
  selector: 'app-pending-rentals',
  templateUrl: './pending-rentals.component.html',
  styleUrls: ['./pending-rentals.component.css']
})
export class PendingRentalsComponent implements OnInit {
  orders: MegrenelesModel[] = []
  panelOpenState = false;

  constructor(private orderService: OrderService, private authService: AuthService) { }

  private stompClient = null;
  greetings: string[] = [];
  disabled = true;
  ngOnInit() {
    this.connect();
    this.orderService.getPendingRentals().subscribe(res => {
      var orders: { quantity: number, item: { name: string } }[] = [];
      res.forEach(result => {
        orders = [];
        result.orders.forEach(order => {
          orders.push({
            quantity: order.quantity,
            item: {
              name: order.item.name
            }
          });
        });

        this.orders.push({
          id: result.id,
          orders: orders,
          totalCost: result.totalCost,
          user: {
            addresses: {
              address: result.user.addresses[0].address,
              postCode: result.user.addresses[0].postCode
            },
            firstname: result.user.firstName,
            lastname: result.user.lastName,
            phonenumber: result.user.phoneNumber
          }
        });

      });
    })


  }

  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.greetings = [];
    }
  }

  // Realtime part
  connect() {
    const socket = new SockJS('http://localhost:8081/topic');
    this.stompClient = Stomp.Stomp.over(socket);
    console.log(this.orders);
    const _this = this;
    this.stompClient.connect({}, function (frame) {
      _this.setConnected(true);
      console.log('Connected: ' + frame);

      _this.stompClient.subscribe('/topic/incoming', (res) => {
        const result = JSON.parse(res.body);
        _this.saveData(result);
        console.log(result);

      });
    });
  }

  saveData(result: {
    id: number,
    orders: {
      quantity: number,
      item: {
        name: string
      }
    }[],
    totalCost: number,
    user: {
      addresses: {
        address: string,
        postCode: number
      }[],
      firstName: string,
      lastName: string,
      phoneNumber: string
    }
  }) {
    const orders = [];
    result.orders.forEach(order => {
      orders.push({
        quantity: order.quantity,
        item: {
          name: order.item.name
        }
      });
    });

    this.orders.push({
      id: result.id,
      orders: orders,
      totalCost: result.totalCost,
      user: {
        addresses: {
          address: result.user.addresses[0].address,
          postCode: result.user.addresses[0].postCode
        },
        firstname: result.user.firstName,
        lastname: result.user.lastName,
        phonenumber: result.user.phoneNumber
      }
    });

    this.authService.openSnackBar("New Order Arrived", "Yeeee");
  }

  //egy ID-t fogunk visszaadni mindig nem egy tömböt


  send($event, id) {
    if ($event.index == 3) {

      console.log("Elkészült clicked " + id);
      const finished: number[] = [id];
      this.orders = this.orders.filter(data => data.id !== id);
      this.orderService.finishRentals(finished).subscribe(res => {
        this.authService.openSnackBar("Order Finished Successfully", "Success");
      });
    }
  }

}
