import { AdressModel } from './adress.model';

export interface UserModel {
    id:number;
    username:string;
    firstname:string;
    lastname:string;
    email:string;
    phonenumber:string;
    authorities:string;
    status:string;
    addresses:AdressModel[];
}