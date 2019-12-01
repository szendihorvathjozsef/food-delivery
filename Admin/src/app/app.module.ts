import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {MatMenuModule} from '@angular/material/menu';
import {MatCardModule} from '@angular/material/card';
import {RouterModule} from '@angular/router';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatListModule} from '@angular/material/list';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatChipsModule} from '@angular/material/chips';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {ReactiveFormsModule, FormsModule } from '@angular/forms'
import {MatTabsModule} from '@angular/material/tabs';
import {ChartsModule} from 'ng2-charts';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/';
import {AutocompleteLibModule} from 'angular-ng-autocomplete';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatTableModule} from '@angular/material/table';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PendingRentalsComponent } from './admin/pending-rentals/pending-rentals.component';
import { HeaderComponent } from './admin/header/header.component';
import { StatisticsComponent } from './admin/statistics/statistics.component';
import { ItemAddComponent } from './admin/item-add/item-add.component';
import { ItemEditComponent } from './admin/item-edit/item-edit.component';
import { TypeAddComponent } from './admin/type-add/type-add.component';
import { HttpClientModule } from '@angular/common/http';
import { CouponAddComponent } from './admin/coupon-add/coupon-add.component';
import { DatePipe } from '@angular/common';
import { AllergenAddComponent } from './admin/allergen-add/allergen-add.component';





@NgModule({
  declarations: [
    AppComponent,
    PendingRentalsComponent,
    HeaderComponent,
    StatisticsComponent,
    ItemAddComponent,
    ItemEditComponent,
    TypeAddComponent,
    CouponAddComponent,
    AllergenAddComponent
  ],
  imports: [
    BrowserModule,
    MatExpansionModule,
    AutocompleteLibModule,
    AppRoutingModule,
    ChartsModule,
    MatTableModule,
    BrowserAnimationsModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatNativeDateModule,
    MatCardModule,
    MatMenuModule,
    MatTabsModule,
    MatChipsModule,
    MatCheckboxModule,
    MatToolbarModule,
    MatListModule,
    MatFormFieldModule,
    MatDatepickerModule,
    MatSelectModule,
    MatInputModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: 'statistics', component: StatisticsComponent},
      {path: 'pending-rentals', component: PendingRentalsComponent},
      {path: 'item-edit', component: ItemEditComponent},
      {path: 'item-add', component: ItemAddComponent},
      {path: 'type-add', component: TypeAddComponent},
      {path: 'coupon-add', component: CouponAddComponent},
      {path: 'allergen-add', component: AllergenAddComponent},
      ]),
  ],
  providers: [DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
