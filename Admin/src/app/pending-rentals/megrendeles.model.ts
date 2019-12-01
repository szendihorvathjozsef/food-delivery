import { EtelModel } from './etel.model';
import { UserModel } from './user-model';

export interface MegrenelesModel {
    id: number,
    orders: {
        quantity: number,
        item: {
          name: string
        }
      }[],
      totalCost:number,
      user: {
        addresses: {
          address:string,
          postCode:number
        },
        firstname:string,
        lastname:string,
        phonenumber:string
    }
}