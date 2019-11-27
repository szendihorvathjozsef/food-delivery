import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { OrderService } from '../order.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  itemCount: number = 0;
  private itemCountListenerSub: Subscription;
  constructor(public orderService: OrderService) { }

  ngOnInit() {
    this.itemCountListenerSub = this.orderService.getItemCountListener()
      .subscribe( itemCount => {
        this.itemCount = itemCount;
      });
  }

}
