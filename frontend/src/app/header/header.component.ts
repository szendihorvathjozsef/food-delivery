import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { OrderService } from '../order.service';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  itemCount: number = 0;
  isAuth: boolean = false;
  isAdmin:boolean = false;

  private itemCountListenerSub: Subscription;
  private authListenerSub: Subscription;
  constructor(public orderService: OrderService, private authService:AuthService) { }

  ngOnInit() {
    this.itemCountListenerSub = this.orderService.getItemCountListener()
      .subscribe(itemCount => {
        this.itemCount = itemCount;
      });

    this.authListenerSub = this.authService.getAuthListener()
      .subscribe(({isAuth, isAdmin}) => {
        this.isAuth = isAuth,
        this.isAdmin = isAdmin
      });
    
    this.isAuth = this.authService.getAuth();
    this.isAdmin = this.authService.getisAdmin();
  }

  logout(){
    this.authService.logout();
  }

}
