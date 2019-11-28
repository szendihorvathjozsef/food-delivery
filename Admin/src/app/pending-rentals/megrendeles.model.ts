import { EtelModel } from './etel.model';
import { UserModel } from './user-model';

export interface MegrenelesModel {
    id: number;
    user: UserModel;
    totalCost: number;
    status: string;
    startTime: Date;
    endTime: Date;
    orders: { quantity:number, item:EtelModel}[];
}