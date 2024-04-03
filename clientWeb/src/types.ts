interface Author {
    id: number;
    firstname: String;
    lastname: String;
    books?: Book[];
}

interface Book {
    id: number;
    title: String;
    publication_year: number;
    authorID: number;
    author: Author;
    tags?: Tag[];
}

interface Tag {
    id: number;
    name: String;
    books?: Book[];
}

interface AuthorCreationData {
    firstname: String;
    lastname: String;
}

interface AuthorUpdateData {
    firstname?: String;
    lastname?: String;
}

interface BookCreationData {
    title: String;
    publication_year: number;
    authorID: number;
}

interface BookUpdateData {
    title?: String;
    publication_year?: number;
    tags?: Tag[];
}