import { Component, OnInit } from '@angular/core';
import { ItemService } from '../item-service/item.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-coupon-add',
  templateUrl: './coupon-add.component.html',
  styleUrls: ['./coupon-add.component.css']
})
export class CouponAddComponent implements OnInit {

  constructor(private itemService: ItemService) { }

  ngOnInit() {
  }

  addNewType(form: NgForm)
  {
    const coupon = {
      name : form.value.name,
      percent : form.value.percent
    };
    this.itemService.addNewCoupon(coupon).subscribe((res) =>{
      console.log(res.isSuccess);
    });
  }

}
