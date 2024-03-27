import { useNavigate, useParams , NavLink} from "react-router-dom";
import { useState, useEffect } from "react";
import { get_author , get_book_from_author , remove_Book} from "../api";

export function Author() {
    const [author, setAuthor] = useState<Author | null>(null);
    const [errorMessage, setErrorMessage] = useState<string>("");
    const { authorID } = useParams();

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
            const authorData = await get_author(id);
            setAuthor(authorData);
        } catch (error : any) {
            setErrorMessage(error.message);
            return;
            
        }
        setErrorMessage("");
    }

    return (<>
        <div>
            &nbsp; {author?.firstname} {author?.lastname} 
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
    useEffect(() => {
        if (authorId) {
            const id = parseInt(authorId);
            if (!isNaN(id)) {
                loadBooks(id);
            }
        }
    }, [books]);
    async function loadBooks(id: number) {
        try{
            const res = await get_book_from_author(id);
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
            await remove_Book(bookID);
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
            <ul>
                    {books.map((book) => (
                        <li>
                            <NavLink to={book.id.toString()}>{book.title}</NavLink>
                            <button className="small danger" onClick={() => handleRemove(book.id)}>X</button>
                        </li>
                    ))}
                </ul>
        </div>
    </>;
}