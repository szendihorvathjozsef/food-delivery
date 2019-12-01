import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { EtelModel } from '../pending-rentals/etel.model';
import { ItemService } from '../item-service/item.service';
import { NgForm } from '@angular/forms';


@Component({
  selector: 'app-item-add',
  templateUrl: './item-add.component.html',
  styleUrls: ['./item-add.component.css']
})
export class ItemAddComponent implements OnInit {

  constructor(private itemService: ItemService) { }
  types:string[];
  file=null;

  ngOnInit() {

    this.itemService.getTypes().subscribe(res => {
      this.types = res;
    });

    this.itemService.getAllergens().subscribe((res) => {
      res.forEach(element => {
        this.listAllergen.push({name: element.toString(), selected:false});
      });
    });
  }
  listAllergen: {name:string,selected:boolean}[] = [];



  addNewItem(form: NgForm) {
    const values = form.value;
    const allergens: string[] = [];

    this.listAllergen.forEach(allergen => {
      if(allergen.selected){
        allergens.push(allergen.name);
      }
    });
    const food: EtelModel = {
      id: null,
      name: values.name,
      price: values.price,
      kcal: values.kcal,
      protein: values.protein,
      fat: values.fat,
      carbs: values.carbs,
      foodType: values.type,
      picture: null,
      allergens: allergens
    }

    if(this.file){
      const uploadData = new FormData();
      uploadData.append('file', this.file, this.file.name);
      uploadData.append('itemName',values.name);
      this.itemService.addNewItem(food).subscribe((res) => {
        this.itemService.uploadImage(uploadData).subscribe(res => {
          console.log(res);
        });
      });
    }

  }

  changeAllergenStatus($event, allergenName) {
    const index = this.listAllergen.indexOf(this.listAllergen.find(item => item.name == allergenName));
    this.listAllergen[index].selected = $event.selected;
  }


  onFileChanged(event){
    this.file = event.target.files[0];
  }

}
