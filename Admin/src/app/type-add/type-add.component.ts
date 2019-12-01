import { Component, OnInit } from '@angular/core';
import {FormControl, NgForm} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators'
import { ItemService } from '../item-service/item.service';



@Component({
  selector: 'app-type-add',
  templateUrl: './type-add.component.html',
  styleUrls: ['./type-add.component.css']
})
export class TypeAddComponent implements OnInit {

  constructor(private itemService: ItemService) { }

  ngOnInit() {
   
  }

  addNewType(form: NgForm)
  {
    const type = form.value.type;
    this.itemService.addNewType(type).subscribe((res) =>{
      if(res === type) {
        console.log("Successfully Added A new Item type");
      } else {
        console.log(res);
      }
    });
  }

}
