import { object, string, optional, size, number } from 'superstruct';

export const RatingCreationData = object({
    rating: number(),
    author: size(string(), 1, 50),
});

export const RatingUpdateData = object({
    rating : number(),
});