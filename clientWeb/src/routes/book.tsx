import { useNavigate, useParams, NavLink } from "react-router-dom";
import { useState, useEffect, FormEvent } from "react";
import { get_book, update_book, get_tags, remove_tag, add_tag , get_comment, remove_comment, add_comment} from "../api";
import { EditableText } from "../utils/editableText";
import { Pagination } from '../utils/pagination';

export function Book() {
    //Params
    const { bookID } = useParams();

    //States
    const [book, setbook] = useState<Book | null>(null);    
    const [author, setAuthor] = useState<Author>();
    const [tags, setTags] = useState<Tag[]>([]);

    const [errorMessage, setErrorMessage] = useState<string>("");

    const [isLoading, setIsLoading] = useState(true);
    const [isTagLoading, setIsTagLoading] = useState(true);


    //useEffects
    useEffect(() => {
        if (bookID) {
            const id = parseInt(bookID);
            if (!isNaN(id)) {
                loadBook(id);
            }
        }
    }, [bookID]);

    useEffect(() => {
        loadTags();
    }, []);

    // <-- api functions -->
    async function loadBook(id: number) {
        try {
            setIsLoading(true);
            const bookData = await get_book(id);
            setIsLoading(false);
            setbook(bookData);
            setAuthor(bookData.author);
        } catch (error: any) {
            setErrorMessage(error.message);
            return;

        }
        setErrorMessage("");
    }

    async function loadTags() {
        try {
            setIsTagLoading(true);
            const tags = await get_tags();
            setTags(tags);
            setIsTagLoading(false);
        } catch (error: any) {
            setErrorMessage(error.message);
        }
    }

    async function updateTitle(value: string) {
        if (bookID !== undefined) {
            setIsLoading(true);
            await update_book(parseInt(bookID), { title: value });
            setIsLoading(false);
            loadBook(parseInt(bookID));
        }
    }

    async function updatePublicationDate(value: string) {
        if (bookID !== undefined) {
            setIsLoading(true);
            await update_book(parseInt(bookID), { publication_year: Number(value) });
            setIsLoading(false);
            loadBook(parseInt(bookID));
        }
    }

    async function addTag(tagID: number) {
        if (bookID !== undefined) {
            setIsTagLoading(true);
            await add_tag(parseInt(bookID), tagID);
            setIsTagLoading(false);
            loadBook(parseInt(bookID));
        }
    }

    async function removeTag(tagID: number) {
        if (bookID !== undefined) {
            setIsTagLoading(true);
            await remove_tag(parseInt(bookID), tagID);
            setIsTagLoading(false);
            loadBook(parseInt(bookID));
            }
    }

    // <-- handler functions -->

    async function handleAddTag(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        if (bookID !== undefined && e.currentTarget.tags.value !== "0") {
            await addTag(parseInt(e.currentTarget.tags.value));
            loadBook(parseInt(bookID));
        }
    }

    async function handleRemoveTag(tagID: number) {
            await removeTag(tagID);
    }

    //Return

    return (<>
            {isLoading ? <p>Chargement...</p> : <>
                <div>
                    <h1>&nbsp;{book?.title}</h1>
                    <hr/>
                    <h4>Date de publication : {book?.publication_year}</h4>
                <NavLink to={"/authors/" + author?.id.toString()}>Author: {author?.firstname} {author?.lastname}</NavLink>
                <ul>
                    {book?.tags?.map((tag, index) => {
                        return <li key={index}>{tag.name} <button onClick={() => handleRemoveTag(tag.id)}>Remove</button></li>
                    })}
                </ul>
                <form onSubmit={handleAddTag}>
                    <select name="tags">
                        <option value={0}>-Choose a tag-</option>
                        {tags.map((tag, index) => (
                            <option value={tag?.id} key={index}>{tag?.name}</option>
                        ))}
                    </select>
                    <button>Add tag</button>
                </form>
                </div>
                <div>
                    {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
                    <p>modifier le livre</p>
                    <label>Titre : </label><EditableText value={book?.title.toString() ?? ""} onUpdate={updateTitle}/>
                    <br/>
                    <br/>
                    <label>Date de publication : </label><EditableText value={book?.publication_year.toString() ?? ""} onUpdate={updatePublicationDate} />  
                </div>   
                <Comment bookID={bookID ?? "-1"}/>             
            </>}
        </>
    );
}
interface commentProps{
    bookID : string
}
function Comment({bookID} : commentProps){
    const [isLoading, setIsLoading] = useState(true);
    const [comments, setcomment] = useState<BookComment[] | null>(null);
    const [errorMessage, setErrorMessage] = useState<string>("");
    const navigate = useNavigate(); // navigation
    useEffect(() => {
        if (bookID) {
            const id = parseInt(bookID);
            if (!isNaN(id)) {
                loadComment(id);
            }
        }
    }, [bookID]);
    async function loadComment(id: number) {
        try {
            setIsLoading(true);
            const commentData = await get_comment(id);
            setIsLoading(false);
            setcomment(commentData);
        } catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }
    async function handleAdd(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const form = e.currentTarget;
        const text = form.text.value;
        const author = form.author.value;
        try {

            await add_comment(bookID ?? "", { text, author });
            loadComment(parseInt(bookID?? "-1"));
        }
        catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }
    async function handleRemove(CommentID: number) {
        await remove_comment(CommentID);
        loadComment(parseInt(bookID?? "-1"));
    }
    return <div>
        <hr/>
        <h3>Commentaire</h3>
        <form onSubmit={handleAdd}>
                <input type="text" name="text" placeholder={"text"} />
                <input type="text" name="author" placeholder={"author"} />
                <button type="submit">Ajouter</button>
        </form>
        {!isLoading ? (<ul>
            {comments?.map((comment) => (
                <li key={comment.id}>
                    <label>Autheur : {comment.author}</label>
                    <p>{comment.text}</p>
                    <button className="small danger" onClick={() => handleRemove(comment.id)}>X</button>
                </li>
            ))}
        </ul>) : (<p>Chargement...</p>)}
        <hr/>
    </div>
}
