import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.scss']
})
export class VerifyComponent implements OnInit {

  constructor(private route:ActivatedRoute,private authService:AuthService) { }

  activationCode:string;
  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.activationCode = params.get("key");
    })
  }

}
