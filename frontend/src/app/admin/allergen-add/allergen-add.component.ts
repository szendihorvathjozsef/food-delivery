import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AllergenService } from '../allergen-service/allergen.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-allergen-add',
  templateUrl: './allergen-add.component.html',
  styleUrls: ['./allergen-add.component.css']
})
export class AllergenAddComponent implements OnInit {

  constructor(private allergenService: AllergenService,private authService:AuthService) { }

  ngOnInit() {
  }

  addNewAllergen(form: NgForm)
  {
    const allergen:string = form.value.allergen;
    form.resetForm();
    this.allergenService.addNewAllergen(allergen).subscribe((res) =>{
      if(res) {
        this.authService.openSnackBar("Allergen Added Successfully", "Success");
        
      } else {
        this.authService.openSnackBar("Error", "Error");
      }
    });
  }
}
