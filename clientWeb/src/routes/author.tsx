import { useNavigate, useParams , NavLink} from "react-router-dom";
import { useState, useEffect } from "react";
import { get_author , get_book_from_author , remove_book} from "../api";


export function Author() {
    const [author, setAuthor] = useState<Author | null>(null);
    const [errorMessage, setErrorMessage] = useState<string>("");
    const { authorID } = useParams();

    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        if (authorID) {
            const id = parseInt(authorID);
            if (!isNaN(id)) {
                loadAuthor(id);
            }
        }
    }, [authorID]);

    async function loadAuthor(id: number) {
        try {
            setIsLoading(true);
            const authorData = await get_author(id);
            setIsLoading(false);
            setAuthor(authorData);
        } catch (error : any) {
            setErrorMessage(error.message);
            return;
            
        }
        setErrorMessage("");
    }

    return (<>
        <div>
            {isLoading ? <p>Chargement...</p> : <h1>&nbsp;{author?.firstname} {author?.lastname}</h1>}
            {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
        </div>
        <AuthorBooks authorId={authorID}/>
        </>
    );
}
interface AuthorBooksProps{
    authorId : string | undefined
}
function AuthorBooks({ authorId } : AuthorBooksProps){
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
    }, [books]);
    async function loadBooks(id: number) {
        try {
            setIsLoading(true);
            const res = await get_book_from_author(id);
            setIsLoading(false);
            setBooks(res.books);
            setErrorMessage("");
        }
        catch(error : any){
            setErrorMessage(error.message);
            return;
        }
        
    }
    async function handleRemove(bookID: number) {
        try {
            setIsLoading(true);
            await remove_book(bookID);
            setIsLoading(false);
        }
        catch (error : any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }
    return<>
        <div>
            <h2>Books</h2>
            {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
            {!isLoading ? (
            <ul>
                    {books.map((book) => (
                        <li>
                            <NavLink to={book.id.toString()}>{book.title}</NavLink>
                            <button className="small danger" onClick={() => handleRemove(book.id)}>X</button>
                        </li>
                    ))}
                </ul>) : (<p>Chargement...</p>)}
        </div>
    </>;
}