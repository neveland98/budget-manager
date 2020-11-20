export interface Transaction {
    id: number;
    userId: number;
    description: string;
    amount: number;
    charge: boolean;
}