import { prisma } from '../db';
import { Prisma } from '@prisma/client';
import type { Request, Response } from 'express';
import { HttpError } from '../error';
import { assert } from 'superstruct';
import { BookCreationData, BookUpdateData } from '../validation/book';

export async function get_all(req: Request, res: Response) {
  const { title, author,tag, include, skip, take, order } = req.query;
  const filter: Prisma.BookWhereInput = {};

  let orderBy: { [key: string]: 'asc' | 'desc' } = { title: 'asc' };

  if (order && (order === 'title' || order === 'rating' || order === 'publication_year')) {   // rating order dont work yet
    orderBy = { [order]: 'asc' }; 
  }

  if (title) {
    filter.title = { contains: String(title) };
  }

  if (author) {
    filter.author = {
      OR: [
        { firstname: { contains: String(author) } },
        { lastname: { contains: String(author) } }
      ]
    };
  }

  if (tag) {
    filter.tags = {
      some: {
        name: { contains: String(tag) }
      }
    };
  }

  const assoc: Prisma.BookInclude = {};
  if (include === 'author') {
    assoc.author = { select: { id: true, firstname: true, lastname: true } };
  }

  const books = await prisma.book.findMany({
    where: filter,
    include: assoc,
    orderBy: orderBy,
    skip: skip ? Number(skip) : undefined,
    take: take ? Number(take) : undefined
  });

  const bookCount = await prisma.book.count({ where: filter });

  res.header('X-Total-Count', String(bookCount));
  res.json(books);
};


export async function get_one(req: Request, res: Response) {
  const { include } = req.query;
  const assoc: Prisma.BookInclude = {};
  if (include === 'author') {
    assoc.author = { select: { id: true, firstname: true, lastname: true } };
  }
  if (include === 'tags') {
    assoc.tags = { select: { id: true, name: true } };
  }
  if (include === 'comments')
  {
    assoc.comments = {
      select: { id: true, text: true, author: true, createdAt : true }};
  }
  if (include === 'ratings')
  {
    assoc.ratings = {
      select: { id: true, rating: true, author: true }};
  }
  if (include === 'all')
  {
    assoc.author = { select: { id: true, firstname: true, lastname: true } };
    assoc.tags = { select: { id: true, name: true } };
    assoc.comments = {
      select: { id: true, text: true, author: true, createdAt : true }};
    assoc.ratings = {
      select: { id: true, rating: true, author: true }};
  }

  const book = await prisma.book.findUnique({
    where: {
      id: Number(req.params.book_id)
    },
    include: assoc,
  });
  if (!book) {
    throw new HttpError('Book not found', 404);
  }
  res.json(book);
};

export async function get_all_of_author(req: Request, res: Response) {
  const { title } = req.query;
  const filter: Prisma.BookWhereInput = {};
  if (title) {
    filter.title = { contains: String(title) };
  }
  const author = await prisma.author.findUnique({
    where: {
      id: Number(req.params.author_id),
    },
    include: {
      books: {
        where: filter,
      }
    }
  });
  if (!author) {
    throw new HttpError('Author not found', 404);
  }
  res.json(author.books);
};

export async function create_one_of_author(req: Request, res: Response) {
  assert(req.body, BookCreationData);
  const book = await prisma.book.create({
    data: {
      ...req.body,
      author: {
        connect: {
          id: Number(req.params.author_id)
        }
      }
    }
  });
  res.status(201).json(book);
};

export async function update_one(req: Request, res: Response) {
  assert(req.body, BookUpdateData);
  try {
    const book = await prisma.book.update({
      where: {
        id: Number(req.params.book_id)
      },
      data: req.body
    });
    res.json(book);
  }
  catch (err) {
    throw new HttpError('Book not found', 404);
  }
};

export async function delete_one(req: Request, res: Response) {
  try {
    await prisma.book.delete({
      where: {
        id: Number(req.params.book_id)
      }
    });
    res.status(204).send();
  }
  catch (err) {
    throw new HttpError('Book not found', 404);
  }
};
