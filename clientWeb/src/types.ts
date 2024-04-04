interface Author {
    id: number;
    firstname: string;
    lastname: string;
    books?: Book[];
}

interface Book {
    id: number;
    title: string;
    publication_year: number;
    authorID: number;
    author: Author;
    tags?: Tag[];
}

interface Tag {
    id: number;
    name: string;
    books?: Book[];
}

interface AuthorCreationData {
    firstname: string;
    lastname: string;
}

interface AuthorUpdateData {
    firstname?: string;
    lastname?: string;
}

interface BookCreationData {
    title: string;
    publication_year?: number;
}

interface BookUpdateData {
    title?: string;
    publication_year?: number;
    tags?: Tag[];
}
