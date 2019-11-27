export interface Food {
  id: string;
  picture: string;
  name: string;
  price: number;
  allergens: { allergen: string, isConatin: boolean}[];
  kcal: number;
  protein: number;
  fat: number;
  carbs: number;
  foodType: string;
}
