import { Component, OnInit } from '@angular/core';
import { OrderService } from '../order-service/order.service';
import { FormControl, NgForm } from '@angular/forms';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {


  public oneDayDate = new FormControl(new Date());
  public startDate = new FormControl(new Date());
  public endDate = new FormControl(new Date());


  public pieChartLabels: string []  = ["valami", "fdg"];
  public pieChartData: number [] = [2, 32];
  public pieChartType = 'pie';

  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true
  };


  
  public lineChartLabels: Date[] ;
  public lineChartType = 'line';
  public lineChartLegend = true;
  public lineChartData: number[];
 
  constructor(private orderService: OrderService, private datePipe: DatePipe) { }

  ngOnInit() {

  }

  getDailystat(date:Date)
  {
    console.log(this.datePipe.transform(date, 'yyyy-MM-dd'));
    this.orderService.getDailyStat(this.datePipe.transform(date, 'yyyy-MM-dd')).subscribe((res) => {
      console.log(res);
      this.pieChartLabels = [];
      this.pieChartData = [];
      res.forEach(element => {
        this.pieChartLabels.push(element.name);
        this.pieChartData.push(element.quantity);
      });
    });
  }

  getPeriodStat()
  {
    this.orderService.getPeriodStat(this.startDate.value, this.endDate.value).subscribe((res) => {
      res.item.forEach(element => {
        this.lineChartLabels.push(element.date);
        this.lineChartData.push(element.income);
      });
    });
  }
}