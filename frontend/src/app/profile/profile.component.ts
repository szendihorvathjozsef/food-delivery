import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CouponModel } from './coupon.model';
import { ProfileService } from './profile.service';
import { AuthService } from '../auth/auth.service';
import { AdressModel } from '../auth/adress.model';

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
    this.username = this.authService.getUser().username;
    const user = this.authService.getUser();
    this.username = user.username;
    this.adressData = [
      {icon: "drafts", name: "postCode", title: "Post Code:", value: user.addresses[0].postCode.toString() },
      {icon: "location_city", name: "address", title: "Adress:", value: user.addresses[0].address }
    ]

    this.contactsData = [
      {icon: "email", name: "email", title: "Email Adress:", value: user.email },
      {icon: "phone", name: "phone", title: "State:", value: user.phonenumber },
      {icon: "person", name: "name", title: "Full Name:", value: user.firstname.concat(" ", user.lastname) }
    ];

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

  editData(form:NgForm){
    const firstname:string = form.value.name.split(" ")[0];
    const lastname:string = form.value.name.split(" ")[1];
    const email:string = form.value.email;
    const phonenumber:string = form.value.phone;
    const address:AdressModel = {
      postCode: +form.value.postCode,
      address: form.value.address,
      type:"TRANSPORT"
    };

    this.contactsData[0].value = email;
    this.contactsData[1].value = phonenumber;
    this.contactsData[2].value = form.value.name;
    this.authService.modifyUser(firstname,lastname,email,phonenumber,address);

    this.profileService.updateUserInformation(this.username,firstname,lastname,email,phonenumber,this.authService.getUser().addresses).subscribe(res=>{
      this.authService.openSnackBar("Your Details Changed!","");
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
