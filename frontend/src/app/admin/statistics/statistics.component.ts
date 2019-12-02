import { Component, OnInit } from '@angular/core';
import { OrderService } from '../order-service/order.service';
import { FormControl, NgForm } from '@angular/forms';
import { DatePipe, getLocaleDateFormat } from '@angular/common';

import * as _moment from 'moment';
import { defaultColors } from 'ng2-charts';
const moment = _moment;

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {


  public startDate = new FormControl(new Date());
  public endDate = new FormControl(new Date());
 
  public pieChartLabels: string []  = [""];
  public pieChartData: number [] = [1];
  public pieChartType = 'pie';
  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    defaultColors: false,
    defaults: true
  };


  
  public lineChartLabels: string []=  ["vsdvs", "sdvgdsg"] ;
  public lineChartType = 'line';
  public lineChartLegend = true;
  public lineChartData: number[] = [21, 45];
  dailyPicker = new Date();
  startDatePicker = new Date(_moment.now()-644000000);
  endDatePicker = new Date();
 
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
      this.pieChartColors = [];
      res.forEach(element => {
        this.pieChartLabels.push(element.name);
        this.pieChartData.push(element.quantity);
        
      });
      if (res.length == 0)
      {
        this.pieChartData.push(1);
        this.pieChartLabels.push("");
      }
    });
  }

  getPeriodStatStart(event)
  {
    const start = this.datePipe.transform(event, 'yyyy-MM-dd');
    const end = this.datePipe.transform(this.endDate.value, 'yyyy-MM-dd');
    console.log("startPick");
    console.log(start);
    console.log(end);
    if(end != null)
    {
      this.orderService.getPeriodStat(start, end).subscribe((res) => {
        res.forEach(element => {
          this.lineChartLabels.push(this.datePipe.transform(element.date, 'yyyy-MM-dd'));
          this.lineChartData.push(element.income);
        });
        if (res.length == 0)
        {
          this.pieChartData.push(0);
          this.pieChartLabels.push("");
        }
      });
    }
  }

  
  getPeriodStatEnd(event)
  {
    const end = this.datePipe.transform(event, 'yyyy-MM-dd');
    const start = this.datePipe.transform(this.startDate.value, 'yyyy-MM-dd');
    console.log("endPick");
    console.log(start);
    console.log(end);
    if(start != null)
    {
      this.orderService.getPeriodStat(start, end).subscribe((res) => {
        res.forEach(element => {
          this.lineChartLabels.push(element.date.toDateString());
          this.lineChartData.push(element.income);
        });
        if (res.length == 0)
        {
          this.pieChartData.push(0);
          this.pieChartLabels.push("");
        }
      });
    }
  }
}