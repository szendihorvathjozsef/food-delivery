import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import { EtelModel } from '../pending-rentals/etel.model';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.css']
})



export class ItemEditComponent implements OnInit {
  myControl = new FormControl();

  lista:EtelModel[]= [
    {nev: "Rántott hús", db:2, megrendelo: "Jóska"},
    {nev: "Gyros", db:1, megrendelo: "Pista"},
    {nev: "Brassói", db:1, megrendelo: "Laci"},
    {nev: "Cordon blue", db:1, megrendelo: "Jakab"}
  ];
  filteredOptions: Observable <EtelModel[]>;
  selected: EtelModel;



  constructor() {
    
   }
  ngOnInit() {
    console.log("Lefut");
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value =>  this._filter(value))
      
    );
  }


  private _filter(value: string): EtelModel[] {
    console.log("Lefut belul");
    if (value !== undefined)
    {
      const filterValue = value.toLowerCase();
      return this.lista.filter(option =>  {
        if (option.nev.toLowerCase().includes(filterValue)) {
          if(option.nev == value){
            this.selected = option;
          }
          console.log(this.selected);
          return option; 
        }
      }
        
        /*i*/
      );
    }
  }


 
}

