const apiBasename = 'http://localhost:3000';
1
interface getAuthorsParams {
    page?: number;
    pageSize?: number;
    lastname?: string;
    firstname?: string;
}

export async function get_authors(params: getAuthorsParams): Promise<{ authors: Author[], totalCount: number }> {
    params.page = params.page || 1;
    params.pageSize = params.pageSize || 10;

    const take = params.pageSize;
    const skip = (params.page - 1) * take;

    const res = await fetch(`${apiBasename}/authors?take=${take}&skip=${skip}&lastname=${params.lastname || ''}&firstname=${params.firstname || ''}`);
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }

    const totalCount = parseInt(res.headers.get('X-Total-Count') || '0', params.pageSize);

    const authors = await res.json();

    return { authors, totalCount };
}


export async function add_author(authorCreationData: AuthorCreationData) {
    const res = await fetch(`${apiBasename}/authors/`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(authorCreationData),
    });
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
    const author = await res.json();
    return author;
}

export async function remove_author(authorID: number) {
    const res = await fetch(`${apiBasename}/authors/${authorID}`, {
        method: "DELETE",
    });
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
}

export async function get_author(authorID: number): Promise<Author> {
    const res = await fetch(`${apiBasename}/authors/${authorID}`);
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
    const author = await res.json();
    return author;
}

export async function update_author(authorID: number, authorUpdateData: AuthorUpdateData) {
    const res = await fetch(`${apiBasename}/authors/${authorID}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(authorUpdateData),
    });
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
    const author = await res.json();
    return author;
}

export async function get_book_from_author(authorID: number): Promise<{ books: Book[]}> {
    const res = await fetch(`${apiBasename}/authors/${authorID}/books`);
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
    const books = await res.json();

    return { books};
}
export async function remove_book(bookID:number) {
    const res = await fetch(`${apiBasename}/books/${bookID}`, {
        method: "DELETE",
    });
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
}
export async function add_book(authorID: string , bookCreationData: BookCreationData) { 
    const res = await fetch(`${apiBasename}/authors/${authorID}/books`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(bookCreationData),
    });
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
    const author = await res.json();
    return author;
}
interface getAuthorsParams {
    page?: number;
    pageSize?: number;
    title?: string;
}
export async function get_books(params: getAuthorsParams): Promise<{ books: Book[], totalCount: number }> {
    params.page = params.page || 1;
    params.pageSize = params.pageSize || 10;

    const take = params.pageSize;
    const skip = (params.page - 1) * take;

    const res = await fetch(`${apiBasename}/books?take=${take}&skip=${skip}&title=${params.title || ''}`);
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }

    const totalCount = parseInt(res.headers.get('X-Total-Count') || '0', params.pageSize);

    const books = await res.json();

    return { books, totalCount };
}
export async function get_book(bookID: number): Promise<Book> {
    const res = await fetch(`${apiBasename}/books/${bookID}?include=all`);
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
    const book = await res.json();
    return book;
}

export async function update_book(bookID: number, bookUpdateData: BookUpdateData) {
    const res = await fetch(`${apiBasename}/books/${bookID}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(bookUpdateData),
    });
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
    const book = await res.json();
    return book;
}

export async function get_tags(): Promise<Tag[]> {
    const res = await fetch(`${apiBasename}/tags`);
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }
    const tags = await res.json();
    return tags;
}