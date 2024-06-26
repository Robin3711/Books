import { useNavigate, useParams, NavLink } from "react-router-dom";
import { FormEvent, useState, useEffect } from "react";
import { get_author, get_book_from_author, remove_book, update_author , add_book} from "../api";
import { EditableText } from '../utils/editableText';


export function Author() {
    const [author, setAuthor] = useState<Author | null>(null);  // author
    const { authorID } = useParams();

    const [errorMessage, setErrorMessage] = useState<string>("");   // error message

    const [isLoading, setIsLoading] = useState(true); // loading

    // useEffect
    useEffect(() => {
        if (authorID) {
            const id = parseInt(authorID);
            if (!isNaN(id)) {
                loadAuthor(id);
            }
        }
    }, [authorID]);

    // <-- api functions -->
    async function loadAuthor(id: number) {
        try {
            setIsLoading(true);
            const authorData = await get_author(id);
            setIsLoading(false);
            setAuthor(authorData);
        } catch (error: any) {
            setErrorMessage(error.message);
            return;

        }
        setErrorMessage("");
    }

    // update author
    async function updateAuthor(authorID: number, authorUpdateData: AuthorUpdateData) {
        try {
            setIsLoading(true);
            await update_author(authorID, authorUpdateData);
            setIsLoading(false);
        } catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }

// <-- handler functions -->
    
    async function updateFirstname(value: string) {
        if (authorID !== undefined) {
            await updateAuthor(parseInt(authorID), { firstname : value});
            loadAuthor(parseInt(authorID));
        }
    }       

    async function updateLastname(value: string) {
        if (authorID !== undefined) {
            await updateAuthor(parseInt(authorID), { lastname: value });
            loadAuthor(parseInt(authorID));
        }
    }

    return (<>
        <div>
            {isLoading ? <p>Chargement...</p> : <h1>&nbsp;{author?.firstname} {author?.lastname}</h1>}
            {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
            <h3>Modifier l'auteur</h3>
            <EditableText value={author?.firstname.toString() ?? ""} onUpdate={updateFirstname} />
            <br/>
            <br/>
            <EditableText value={author?.lastname.toString() ?? ""} onUpdate={updateLastname} />
        </div>
        <AuthorBooks authorId={authorID} />
    </>
    );
}

interface AuthorBooksProps {
    authorId: string | undefined
}


function AuthorBooks({ authorId }: AuthorBooksProps) {
    const [books, setBooks] = useState<Book[]>([]);

    const [errorMessage, setErrorMessage] = useState<string>("");

    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        if (authorId) {
            const id = parseInt(authorId);
            if (!isNaN(id)) {
                loadBooks(id);
            }
        }
    }, [authorId]);

    // <-- api functions -->
    async function loadBooks(id: number) {
        try {
            setIsLoading(true);
            const res = await get_book_from_author(id);
            setIsLoading(false);
            setBooks(res.books);
        }
        catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }

    // <-- handler functions -->
    async function handleRemove(bookID: number) {
        try {

            await remove_book(bookID);
            loadBooks(parseInt(authorId?? "-1"));
        }
        catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }
    async function handleAdd(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const form = e.currentTarget;
        const titre = form.titre.value;
        const publication_year = Number(form.publication_year.value);
        try {

            await add_book(authorId ?? "", { title:titre, publication_year });
            loadBooks(parseInt(authorId?? "-1"));
        }
        catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }
    return <>
        <div>
            <h2>Books</h2>
            {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
            {!isLoading ? (<>
                <form onSubmit={handleAdd}>
                    <input type="text" name="titre" placeholder={"titre"} />
                    <br />
                    <input type="number" name="publication_year" placeholder={"Année de publication"} />
                    <br/>
                    <button type="submit">Ajouter</button>
                </form>
                <ul>
                    {books.map((book) => (
                        <li>
                            <NavLink to={"/books/"+book.id.toString()}>{book.title}</NavLink>
                            <button className="small danger" onClick={() => handleRemove(book.id)}>X</button>
                        </li>
                    ))}
                </ul>

            </>) : (<p>Chargement...</p>)}

        </div>
    </>;
}