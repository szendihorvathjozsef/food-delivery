
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatBadgeModule } from '@angular/material/badge';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatExpansionModule} from '@angular/material/expansion';






import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FoodsComponent } from './foods/foods.component';
import { FoodInfoDialogComponent } from './foods/food-info-dialog/food-info-dialog.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { CartComponent } from './cart/cart.component';
import { FoodComponent } from './foods/food/food.component';
import { OrderTableComponent } from './cart/order-table/order-table.component';
import { ProfileComponent } from './profile/profile.component';
import { AuthInterceptor } from './auth/auth-interceptor';
import { AuthGuard } from './auth/auth.guard';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FoodsComponent,
    FoodInfoDialogComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    CartComponent,
    FoodComponent,
    OrderTableComponent,
    ProfileComponent
  ],
  entryComponents: [
    FoodInfoDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MDBBootstrapModule.forRoot(),
    FormsModule,
    MatDialogModule,
    MatButtonModule,
    MatStepperModule,
    MatToolbarModule,
    MatTabsModule,
    MatCardModule,
    MatSnackBarModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    HttpClientModule,
    MatBadgeModule,
    MatMenuModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
    MatCheckboxModule,
    MatExpansionModule,
    RouterModule.forRoot([
      { path: '' , component: HomeComponent},
      { path: 'food' , component: FoodsComponent},
      { path: 'login' , component: LoginComponent},
      { path: 'register' , component: RegisterComponent},
      { path: 'cart' , component: CartComponent },
      { path: 'profile' , component: ProfileComponent, canActivate:[AuthGuard] },
      {path: '**', redirectTo: ''}
    ])
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
