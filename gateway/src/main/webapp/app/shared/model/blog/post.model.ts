import { Moment } from 'moment';
import { ITag } from 'app/shared/model/blog/tag.model';

export interface IPost {
    id?: number;
    title?: string;
    content?: any;
    date?: Moment;
    blogName?: string;
    blogId?: number;
    tags?: ITag[];
}

export class Post implements IPost {
    constructor(
        public id?: number,
        public title?: string,
        public content?: any,
        public date?: Moment,
        public blogName?: string,
        public blogId?: number,
        public tags?: ITag[]
    ) {}
}
