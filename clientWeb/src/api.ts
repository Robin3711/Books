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
export async function add_book(authorCreationData: AuthorCreationData) { 
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