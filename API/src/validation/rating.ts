import { object, string, optional, size, number, max, min, integer} from 'superstruct';



export const RatingCreationData = object({
    rating: min(max(integer(), 5), 0),
    author: size(string(),1, 50),
});

export const RatingUpdateData = object({
    rating : integer(),
});