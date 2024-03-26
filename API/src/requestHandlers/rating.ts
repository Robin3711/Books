import { prisma } from '../db';
import { Prisma } from '@prisma/client';
import type { Request, Response } from 'express';
import { HttpError } from '../error';
import { assert, date } from 'superstruct';
import { RatingCreationData, RatingUpdateData } from '../validation/rating';

export async function get_all_of_book(req: Request, res: Response) {
    const ratings = await prisma.rating.findMany({
        where: {
            bookId: Number(req.params.book_id)
        }
    });
    res.json(ratings);
}

export async function create_one(req: Request, res: Response) {
    assert(req.body, RatingCreationData);
    const rating = await prisma.rating.create({
        data: {
            ...req.body,
            book: {
                connect: {
                    id: Number(req.params.book_id)
                }
            },
            createdAt: new Date(),
            updatedAt: new Date()
        }
    });
    res.status(201).json(rating);
}

export async function update_one(req: Request, res: Response) {
    assert(req.body, RatingUpdateData);
    const rating = await prisma.rating.update({
        where: {
            id: Number(req.params.rating_id)
        },
        data: {
            rating: req.body.rating,
            updatedAt: new Date()
        }
    });
    res.status(201).json(rating);
}

export async function delete_one(req: Request, res: Response) {
    await prisma.rating.delete({
        where: {
            id: Number(req.params.rating_id)
        }
    });
    res.status(204).send();
}