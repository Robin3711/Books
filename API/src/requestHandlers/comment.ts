import { prisma } from '../db';
import { Prisma } from '@prisma/client';
import type { Request, Response } from 'express';
import { HttpError } from '../error';
import { assert, date } from 'superstruct';
import { CommentCreationData, CommentUpdateData } from '../validation/comment';

export async function get_all_of_book(req: Request, res: Response) {
    const comments = await prisma.comment.findMany({
        where: {
            bookId: Number(req.params.book_id)
        }
    });
    res.json(comments);
}

export async function create_one(req: Request, res: Response) {
    assert(req.body, CommentCreationData);
    const comment = await prisma.comment.create({
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
    res.status(201).json(comment);
}

export async function update_one(req: Request, res: Response) {
    assert(req.body, CommentUpdateData);
    const comment = await prisma.comment.update({
        where: {
            id: Number(req.params.comment_id)
        },
        data: {
            text: req.body.text,
            updatedAt: new Date()
        }
    });
    res.json(comment);
}

export async function delete_one(req: Request, res: Response) {
    await prisma.comment.delete({
        where: {
            id: Number(req.params.comment_id)
        }
    });
    res.status(204).send();
}