import { Component, OnInit } from '@angular/core';
import { Food } from '../foods/food-model';
import { OrderService } from '../order.service';
import { Subscription } from 'rxjs';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { UserModel } from '../auth/user.model';
import { CouponModel } from '../profile/coupon.model';
import { ProfileService } from '../profile/profile.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  isMatch:boolean= true;
  user:UserModel;
  paymentMethod;
  private emptyListenerSub: Subscription;
  paymentMethods: string[] = ["Card", "Cash", "Nice Card"];
  isEmpty = true;
  totalCost = 0;
  selectedCoupon:CouponModel;
  coupons:CouponModel[];
  constructor(private orderService: OrderService,private authService:AuthService,private profileService:ProfileService) { }

  ngOnInit() {
    this.isMatch ;
    this.user = this.authService.getUser();
    if(!this.user){
      this.user = {    
        id:null,
        username: null,
        firstname:"",
        lastname:"",
        email: "",
        phonenumber: "",
        authorities: null,
        status:null,
        addresses: []
      };
    } else {
      this.coupons = [];
      this.profileService.getCoupons().subscribe(res => {
        res.forEach(item => {
          this.coupons.push({
            id: item.id,
            name: item.type.name,
            percentage: item.type.percent / 100
          });
        });
      });
    }
    this.isEmpty = this.orderService.isOrderItemEmpty();
    this.totalCost = this.orderService.getTotalCost();
    this.emptyListenerSub = this.orderService.getItemEmptyListener()
    .subscribe( res => {
      this.isEmpty = res.isEmpty;
      this.totalCost = res.totalCost;
    });
  }

  onSubmit(form:NgForm){
    if(form.valid){
      this.orderService.takeAnOrder(this.user,this.selectedCoupon);
    }
  }
  adressChangeStatus(isMatch:boolean) {
    this.isMatch = isMatch;
    console.log(this.isMatch);
  }

  loadDetails(form:NgForm){
    this.user.firstname = form.value.firstName;
    this.user.lastname = form.value.lastName;
    this.user.phonenumber = form.value.phoneNumber;
    this.user.email = form.value.emailaddress;
    if(this.authService.getAuth()){
      if(this.coupons.find(coupon => coupon.id == form.value.coupon)){
        this.selectedCoupon = this.coupons.find(coupon => coupon.id == form.value.coupon);
      }
    }
    this.user.addresses = [];
    this.user.addresses.push({
      postCode: form.value.postCode,
      address: form.value.state.concat(", ",form.value.city,", ",form.value.address),
      type: "TRANSPORT"
    });

    if(!this.isMatch){
      console.log("Lefut");
      this.user.addresses.push( {
        postCode: form.value.postCode2,
        address: form.value.state.concat(", ",form.value.city2,", ",form.value.address2),
        type: "BILLING"
      });
    }
  }
}
