import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth.service';
import { concat } from 'rxjs';
import { AdressModel } from '../adress.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private authService:AuthService) { }

  ngOnInit() {
  }

  register(registerForm: NgForm){
    const values = registerForm.value;
    if(registerForm.valid){
      if(values.password === values.confpassword){
        const adresses:AdressModel[] = [];
        adresses.push({ postCode: values.postCode, adress: values.state.concat(", ",values.city,", ",values.adress), type: "TRANSPORT"});
        this.authService.registerUser(
          values.username,
          values.password,
          values.firstName,
          values.lastName,
          values.email,
          adresses
        );
      } else {
        console.log("Password Not matching!");
      }
    } else {
      console.log("INVALID");
    }
  }

}
