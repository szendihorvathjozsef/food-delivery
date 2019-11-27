export interface Food {
  id: number;
  picture: string;
  name: string;
  price: number;
  allergens: string[];
  kcal: number;
  protein: number;
  fat: number;
  carbs: number;
  foodType: string;
}
