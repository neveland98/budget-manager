import { Category } from './category';

export interface Transaction {
    transactionId: number;
    userId: number;
    description: string;
    amount: number;
    charge: boolean;
    date: Date;
    category: Category;
}