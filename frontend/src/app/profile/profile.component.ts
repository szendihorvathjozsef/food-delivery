import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CouponModel } from './coupon.model';
import { ProfileService } from './profile.service';
import { AuthService } from '../auth/auth.service';
import { AdressModel } from '../auth/adress.model';
import { concat } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  

  username: string;
  userAdress: AdressModel[];
  status:string;

  adressData:{ icon:string, name:string, title:string, value:string }[];
  contactsData:{ icon:string, name:string, title:string, value:string }[];
  coupons: CouponModel[] = [];

  editMode:string = 'none';
  constructor(private profileService:ProfileService,private authService: AuthService) { }

  ngOnInit() {
    this.username = this.authService.getUserName();
    this.profileService.getUserInformation(this.username).subscribe(res => {
      this.username = res.login;
      this.userAdress = res.addresses;
      this.status = res.status;

      this.adressData = [
        {icon: "drafts", name: "postCode", title: "Post Code:", value: "8200"},//this.userAdress[0].postCode.toString() },
        {icon: "location_city", name: "adress", title: "Adress:", value: "Veszprém Vár utca 20."}//this.userAdress[0].address }
      ]

      this.contactsData = [
        {icon: "email", name: "email", title: "Email Adress:", value: res.email },
        {icon: "phone", name: "phone", title: "State:", value: "+36205669872" },
        {icon: "person", name: "name", title: "Full Name:", value: res.firstName.concat(" ", res.lastName) }
      ];
    });

    this.profileService.getCoupons().subscribe(res => {
      res.forEach(coupon => {
        this.coupons.push({
          id: coupon.id,
          name: coupon.name,
          percentage: coupon.percent / 100,
          itemType: coupon.itemType
        });
      });
    });
  }

  doubleClick(editMode: string){
    this.editMode = editMode;
  }

  editData(dataName:string,object:any,type:string){
    if(type === "address"){
      this.adressData.find(data => data.name == data.name).value = object.value;
    } else if(type === "contact") {
      this.contactsData.find(data => data.name == data.name).value = object.value;
    }
    
    const name:string[] = this.contactsData.find(data => data.name == "name").value.split(" ");
    const username:string = this.authService.getUserName().toString();
    const user = {
      username: username,
      firstName: name[0],
      lastName: name[1],
      email: this.contactsData.find(data => data.name == "email").value,
      status: this.status,
      authorities: ['USER']

    };

    this.profileService.updateUserInformation(user).subscribe(res=>{
      console.log(res);
    });
    this.editMode = 'none';
  }

  changePassword(form: NgForm){
    if(form.valid){
      const values = form.value;
      if(values.newPassword == values.newPasswordverify){
        console.log(values.oldPassword);
        this.profileService.changePassword(values.oldPassword,values.newPassword).subscribe(res => {
          if(res == null){
            form.resetForm();
            this.authService.openSnackBar("Password Updated","Success");
          }
        });
      }
    }
  }

  useCoupon(id:number){
    console.log(id);
  }

}
