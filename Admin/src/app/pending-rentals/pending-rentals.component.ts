import { Component, OnInit } from '@angular/core';
import { EtelModel } from './etel.model';


@Component({
  selector: 'app-pending-rentals',
  templateUrl: './pending-rentals.component.html',
  styleUrls: ['./pending-rentals.component.css']
})
export class PendingRentalsComponent implements OnInit {


  lista:EtelModel[]= [
    {nev: "Rántot hús", db:2, megrendelo: "Jóska"},
    {nev: "Gyros", db:1, megrendelo: "Pista"},
    {nev: "Brassói", db:1, megrendelo: "Laci"},
    {nev: "Cordon blue", db:1, megrendelo: "Jakab"}
  ];
  

  constructor() { }

  ngOnInit() {
  }
}
