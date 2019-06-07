export interface IBlog {
    id?: number;
    name?: string;
    description?: string;
}

export class Blog implements IBlog {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
