import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  username: string = "Dohny Johny";
  postCode: string = "1015";
  state: string = "Pest";
  adress: string = "Budapest, Sajt utca 4.";
  email:string = "dohn.john@gmail.com";
  phone:string = "+36205669872";
  fullName: string = "Dohn Jon Andreas"
  adressData:{ icon:string, name:string, title:string, value:string }[] = [
    {icon: "drafts", name: "postCode", title: "Post Code:", value: this.postCode },
    {icon: "flag", name: "state", title: "State:", value: this.state },
    {icon: "location_city", name: "adress", title: "Adress:", value: this.adress }
  ]

  contactsData:{ icon:string, name:string, title:string, value:string }[] = [
    {icon: "email", name: "email", title: "Email Adress:", value: this.email },
    {icon: "phone", name: "phone", title: "State:", value: this.phone },
    {icon: "person", name: "name", title: "Full Name:", value: this.adress }
  ]
  editMode:string = 'none';
  constructor() { }

  ngOnInit() {
  }

  doubleClick(editMode: string){
    this.editMode = editMode;
    console.log(editMode);
  }

  changePassword(form: NgForm){
    if(form.valid){
      if(form.value.newPassword == form.value.newPasswordverify){
        console.log("Change Password Auth Service");
      }
    }
  }

}
