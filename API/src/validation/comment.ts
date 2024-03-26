import { object, string, optional, size, refine } from 'superstruct';
import { isInt } from 'validator';

export const CommentCreationData = object({
    text: size(string(), 1, 1000),
    author: size(string(), 1, 50),
});

export const CommentUpdateData = object({
    text: size(string(), 1, 1000), 
});

