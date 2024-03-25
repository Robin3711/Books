const apiBasename = 'http://localhost:3000';
1
interface getAuthorsParams {
    page?: number;
    pageSize?: number;
    lastname?: string;
}

export async function get_authors(params: getAuthorsParams): Promise<{ authors: Author[], totalCount: number }> {
    params.page = params.page || 1;
    params.pageSize = params.pageSize || 10;

    const take = params.pageSize;
    const skip = (params.page - 1) * take;

    const res = await fetch(`${apiBasename}/authors?take=${take}&skip=${skip}&lastname=${params.lastname || ''}`);
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg);
    }

    const totalCount = parseInt(res.headers.get('X-Total-Count') || '0', 10);

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
