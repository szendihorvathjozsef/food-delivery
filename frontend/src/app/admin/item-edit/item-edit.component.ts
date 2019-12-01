import { Component, OnInit } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { EtelModel } from '../pending-rentals/etel.model';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { ItemService } from '../item-service/item.service';
import { AllergenModel } from '../pending-rentals/allergen.model';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.css']
})



export class ItemEditComponent implements OnInit {
  myControl = new FormControl();

  items: EtelModel[] = [];
  types:string[] = [];
  filteredOptions: Observable<EtelModel[]>;
  selected: EtelModel;
  listAllergen: { name: string, selected: boolean }[] = [];


  constructor(private itemService: ItemService) {

  }
  ngOnInit() {
    this.itemService.getTypes().subscribe(res => {
      this.types = res;
    });
    this.itemService.getAllergens().subscribe((res) => {
      res.forEach(element => {
        this.listAllergen.push({ name: element.toString(), selected: false });
      });
    });

    this.itemService.getItems().subscribe((res) => {
      res.forEach(item => {
        this.items.push({
          id: item.id,
          picture: null,
          name: item.name,
          price: item.price,
          kcal: item.kcal,
          protein: item.protein,
          fat: item.fat,
          carbs: item.carbs,
          foodType: item.itemType,
          allergens: item.allergens
        });
      });
    });

    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))

    );
  }

  modifyItem(form: NgForm) {
    const allergens: string[] = [];

    this.listAllergen.forEach(allergen => {
      if(allergen.selected){
        allergens.push(allergen.name);
      }
    });
    const values = form.value;

    const food: EtelModel = {
      id: this.selected.id,
      name: this.selected.name,
      price: values.price,
      kcal: values.kcal,
      protein: values.protein,
      fat: values.fat,
      carbs: values.carbs,
      foodType: values.foodType,
      picture: null,
      allergens: allergens
    }

    this.itemService.modifyItem(food).subscribe((res) => {
      console.log(res);
      this.selected = null;
      this.filteredOptions = new Observable();
      form.reset();
    });
  }


  private _filter(value: string): EtelModel[] {
    console.log(this.items);
    if (value !== undefined) {
      const filterValue = value.toLowerCase();
      return this.items.filter(option => {
        if (option.name.toLowerCase().includes(filterValue)) {
          if (option.name == value) {
            this.selected = option;
            this.selected.allergens.forEach(allergen => {
            this.listAllergen.forEach(element => {
              if(element.name == allergen)
              {
                element.selected = true;
              }
            });
            });
          }
          console.log(this.selected);
          return option;
        }
      }
      );
    }
  }

  // test
  changeSelected($event, category): void {
    category.selected = $event.selected;
  }
  changeAllergenStatus($event, allergenName) {
    const index = this.listAllergen.indexOf(this.listAllergen.find(item => item.name == allergenName));
    this.listAllergen[index].selected = $event.selected;
  }

}