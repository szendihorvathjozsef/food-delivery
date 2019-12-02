import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CouponService } from '../coupon-service/coupon.service';
import { ItemService } from '../item-service/item.service';
import { MatTableDataSource } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { element } from 'protractor';
import { AuthService } from 'src/app/auth/auth.service';


//táblázathoz a modell
export interface CouponElement {
  id: number,
  name: string,
  percent: number
}

// const ELEMENT_DATA: CouponElement[] = [
//   {id: 1, name: "napi10", percent: 20, itemType: "Desszert", user: null},
//   // {id: 2, name: "napi10", percent: 20, itemType: "Desszert"},
//   // {id: 3, name: "napi10", percent: 20, itemType: "Desszert"},
//   // {id: 4, name: "napi10", percent: 20, itemType: "Desszert"}
// ];
@Component({
  selector: 'app-coupon-add',
  templateUrl: './coupon-add.component.html',
  styleUrls: ['./coupon-add.component.css']
})

export class CouponAddComponent implements OnInit {


  constructor(private couponService: CouponService,private authService:AuthService) { }
  types: string[];
  dataSource = new MatTableDataSource<CouponElement>();
  ngOnInit() {
    this.couponService.getCoupon().subscribe(res => {
      this.dataSource = new MatTableDataSource<CouponElement>(res);
    });
  }


  //kupon hozzádas fül
  addNewCoupon(form: NgForm) {
    const name = form.value.name;
    const percent = form.value.percent;
    
    this.couponService.addNewCoupon(name, percent).subscribe((res) => {
      this.authService.openSnackBar("Coupon Added Successfully", "Success");
      form.resetForm();
      this.dataSource.data.push(res);
      this.dataSource.data = [...this.dataSource.data];
    });
  }

  //kupon listázás és törlés fül
  displayedColumns: string[] = ['select', 'id', 'name', 'percent'];
  
  selection = new SelectionModel<CouponElement>(true, []);


  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }


  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }


  checkboxLabel(row?: CouponElement): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;

  }

  deleteSend($event) {
    this.selection.selected.forEach(element => {
      this.dataSource.data = this.dataSource.data.filter(data => data.id !== element.id);
      this.couponService.deleteCoupon(element.id).subscribe(res => {
        this.authService.openSnackBar("Coupon Deleted Successfully", "Success");
      });
    });
  }

}
