import { useNavigate, useParams , NavLink} from "react-router-dom";
import { useState, useEffect } from "react";
import { get_book , get_author , remove_book} from "../api";


export function Book() {
    const [book, setbook] = useState<Book | null>(null);
    const [errorMessage, setErrorMessage] = useState<string>("");
    const { bookID } = useParams();

    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        if (bookID) {
            const id = parseInt(bookID);
            if (!isNaN(id)) {
                loadBook(id);
            }
        }
    }, [bookID]);

    async function loadBook(id: number) {
        try {
            setIsLoading(true);
            const bookData = await get_book(id);
            setIsLoading(false);
            setbook(bookData);
        } catch (error : any) {
            setErrorMessage(error.message);
            return;
            
        }
        setErrorMessage("");
    }

    return (<>
        <div>
            {isLoading ? <p>Chargement...</p> : <h1>&nbsp;{book?.title}</h1>}
            {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
        </div>
        <BookAuthor bookID={bookID} />
        </>
    );
}
interface bookAuthorProps{
    bookID : string | undefined
}
function BookAuthor({ bookID } : bookAuthorProps){
    const [book, setbook] = useState<Book>();
    const [author, setAuthor] = useState<Author>();
    const [errorMessage, setErrorMessage] = useState<string>("");
    const [isLoading, setIsLoading] = useState(true);
    useEffect(() => {
        if (bookID) {
            const id = parseInt(bookID);
            if (!isNaN(id)) {
                loadbook(id);
            }
        }
    }, [bookID]);
    async function loadbook(id: number) {
        try {
            setIsLoading(true);
            const res = await get_book(id);
            //const res_author = await get_author(res.authorID);
            setIsLoading(false);
            setbook(res);
            //setAuthor(res_author);
            setErrorMessage("");
        }
        catch(error : any){
            setErrorMessage(error.message);
            return;
        }
        
    }
    return<>
        <div>
            {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
            {!isLoading ? (<>
                <h2>{book?.publication_year}</h2>
                <label>Author:{author?.firstname} {author?.lastname}</label>
                </>
            ) : (<p>Chargement...</p>)}
        </div>
    </>;
}