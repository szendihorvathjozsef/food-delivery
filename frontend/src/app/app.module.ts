
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import {ChartsModule} from 'ng2-charts';
import { FormsModule, FormControl, ReactiveFormsModule } from '@angular/forms';
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
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatRadioModule} from '@angular/material/radio';
import {MatMenuModule} from '@angular/material/menu';






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
import { PendingRentalsComponent } from './admin/pending-rentals/pending-rentals.component';
import { StatisticsComponent } from './admin/statistics/statistics.component';
import { ItemAddComponent } from './admin/item-add/item-add.component';
import { ItemEditComponent } from './admin/item-edit/item-edit.component';
import { TypeAddComponent } from './admin/type-add/type-add.component';
import { CouponAddComponent } from './admin/coupon-add/coupon-add.component';
import { DatePipe } from '@angular/common';
import { AllergenAddComponent } from './admin/allergen-add/allergen-add.component';
import { MatDatepicker, MatDatepickerModule, MatFormFieldModule, MatChipsModule, MatAutocompleteModule, MatNativeDateModule, MatFormFieldControl } from '@angular/material';
import { AdminGuard } from './auth/admin.guard';
import { VerifyComponent } from './verify/verify.component';



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
    ProfileComponent,
    PendingRentalsComponent,
    StatisticsComponent,
    ItemAddComponent,
    ItemEditComponent,
    TypeAddComponent,
    CouponAddComponent,
    AllergenAddComponent,
    VerifyComponent
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
    MatRadioModule,
    MatDatepickerModule,
    MatFormFieldModule,
    ChartsModule,
    MatChipsModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatMenuModule,
    RouterModule.forRoot([
      { path: '' , component: HomeComponent},
      { path: 'food' , component: FoodsComponent},
      { path: 'login' , component: LoginComponent},
      { path: 'register' , component: RegisterComponent},
      { path: 'cart' , component: CartComponent },
      { path: 'profile' , component: ProfileComponent, canActivate:[AuthGuard] },
      {path: 'statistics', component: StatisticsComponent, canActivate:[AdminGuard]},
      {path: 'pending-orders', component: PendingRentalsComponent, canActivate:[AdminGuard]},
      {path: 'item-edit', component: ItemEditComponent, canActivate:[AdminGuard]},
      {path: 'item-add', component: ItemAddComponent, canActivate:[AdminGuard]},
      {path: 'type-add', component: TypeAddComponent, canActivate:[AdminGuard]},
      {path: 'coupon', component: CouponAddComponent, canActivate:[AdminGuard]},
      {path: 'allergen-add', component: AllergenAddComponent, canActivate:[AdminGuard]},
      {path:'verify/:key',component:VerifyComponent},
      {path: '**', redirectTo: ''}
    ])
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, AuthGuard, AdminGuard, DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
