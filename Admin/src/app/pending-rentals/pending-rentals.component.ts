import { Component, OnInit } from '@angular/core';
import { EtelModel } from './etel.model';
import { OrderService } from '../order-service/order.service';
import { MegrenelesModel } from './megrendeles.model';


@Component({
  selector: 'app-pending-rentals',
  templateUrl: './pending-rentals.component.html',
  styleUrls: ['./pending-rentals.component.css']
})
export class PendingRentalsComponent implements OnInit {
  pendingList: MegrenelesModel[] = [
    {
      id: 6,
      user: {
        firstName: "Szende",
        lastName: "Zsombor",
        id: 12,
        login: "Beregos",
        email: "zsombor-",
        status: "string",
        authorites: ["", ""],
        addresses: [{ address: "Veszprém Vár utca 20.", postCode: 8200, type: "TRANSPORT" }]
      },
      totalCost: 600,
      status: "Pending",
      startTime: new Date('2019-11-28'),
      endTime: new Date('2019-11-28'),
      orders: [
        {
          quantity: 6,
          item: {
            id: 1,
            picture: "string",
            name: "pizza",
            price: 300,
            kcal: 500,
            protein: 10,
            fat: 10,
            carbs: 10,
            foodType: "jó",
            allergens: ["Pizza", "valami"]
          }
        }
      ]
    }
  ]
  panelOpenState = false;

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.orderService.getPendingRentals().subscribe((res) => {
      this.pendingList = [...res.item];
    });
  }

  //egy ID-t fogunk visszaadni mindig nem egy tömböt
  modifyRentals(id: number[]) {

    this.orderService.modifyRentals(id).subscribe((res) => {
      console.log(res.isSuccess);
    });
  }

  lista = [
    { name: "valaki", foods: ["valgdf", "gfdgfd"] },
    { name: "valaki", foods: ["valgdf", "gfdgfd"] },
  ];
}
