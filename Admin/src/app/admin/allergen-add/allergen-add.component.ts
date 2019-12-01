import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AllergenService } from '../allergen-service/allergen.service';

@Component({
  selector: 'app-allergen-add',
  templateUrl: './allergen-add.component.html',
  styleUrls: ['./allergen-add.component.css']
})
export class AllergenAddComponent implements OnInit {

  constructor(private allergenService: AllergenService) { }

  ngOnInit() {
  }

  addNewAllergen(form: NgForm)
  {
    const allergen:string = form.value.allergen;
    this.allergenService.addNewAllergen(allergen).subscribe((res) =>{
      if(res === allergen) {
        console.log("Successfully Added A new Item type");
      } else {
        console.log(res);
      }
    });
  }
}
