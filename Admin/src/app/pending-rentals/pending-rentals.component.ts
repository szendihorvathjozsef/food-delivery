import { Component, OnInit } from '@angular/core';
import { EtelModel } from './etel.model';
import { OrderService } from '../order-service/order.service';
import { MegrenelesModel } from './megrendeles.model';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'app-pending-rentals',
  templateUrl: './pending-rentals.component.html',
  styleUrls: ['./pending-rentals.component.css']
})
export class PendingRentalsComponent implements OnInit {
  orders: MegrenelesModel[] = []
  panelOpenState = false;
  /*orders: {
    name: string,
    addresses:
    {
      postCode: number,
      address: string,
      type: string
    }[],
    orders:
    {
      quantity: number,
      item: 
      {
        name: string,
        id: number
      }
    }[],
    id: number
  } [] = [];*/
  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.orderService.getPendingRentals().subscribe(res => {
      console.log("ASD" + res[0].id);
      console.log(res);
      const orders: { quantity: number, item: { name: string } }[] = [];
      res.forEach(result => {
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
      console.log(this.orders);
    })


  }

  //egy ID-t fogunk visszaadni mindig nem egy tömböt


  send($event, id) {
    if ($event.index == 3) {

      console.log("Elkészült clicked " + id);
      const finished: number[] = [id];
      this.orders = this.orders.filter(data => data.id !== id);
      this.orderService.finishRentals(finished).subscribe(res => {
        console.log(res);
      });
    }
  }

}
