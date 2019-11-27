import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService:AuthService) { }

  ngOnInit() {
  }

  login(loginForm:NgForm){
    const values = loginForm.value;
    if(loginForm.valid){
      var rememberMe = true;
      if(values.rememberMe === ""){
        rememberMe = false;
      }
      this.authService.loginUser(values.username,values.password,rememberMe);
      //
    } else {
      console.log("Error");
    }
  }

}
