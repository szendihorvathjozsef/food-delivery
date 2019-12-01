export interface UserModel {
    firstName: string;
    lastName: string;
    id: number;
    login: string;
    email: string;
    status: string;
    authorites: string[];
    addresses: {address: string, postCode: number, type: string}[];

}