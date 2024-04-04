import { useNavigate, useParams, NavLink } from "react-router-dom";
import { useState, useEffect, FormEvent } from "react";
import { get_book, get_author, remove_book, update_book, get_tags, remove_tag, add_tag } from "../api";
import { EditableText } from "../utils/editableText";


export function Book() {
    const [book, setbook] = useState<Book | null>(null);
    const [author, setAuthor] = useState<Author>();
    const [errorMessage, setErrorMessage] = useState<string>("");
    const { bookID } = useParams();
    const [tags, setTags] = useState<Tag[]>([]);

    const [isLoading, setIsLoading] = useState(true);

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
            const tags = await get_tags();
            setTags(tags);
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

    async function removeTag(tagID: number) {
        if (bookID !== undefined) {
            setIsLoading(true);
            await remove_tag(parseInt(bookID), tagID);
            setIsLoading(false);
                loadBook(parseInt(bookID));
            }
        if (bookID !== undefined) {

            loadBook(parseInt(bookID));
        }
    }

    async function handleAddTag(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        if (bookID !== undefined && e.currentTarget.tags.value !== "0") {
            setIsLoading(true);
            await add_tag(parseInt(bookID), e.currentTarget.tags.value);
            setIsLoading(false);
            loadBook(parseInt(bookID));
        }
    }

    return (<>
        
            {isLoading ? <p>Chargement...</p> : <>
                <div>
                    <h1>&nbsp;{book?.title}</h1>
                    <hr/>
                    <h4>Date de publication : {book?.publication_year}</h4>
                <NavLink to={"/authors/" + author?.id.toString()}>Author: {author?.firstname} {author?.lastname}</NavLink>
                <ul>
                    {book?.tags?.map((tag, index) => {
                        return <li key={index}>{tag.name} <button onClick={() => removeTag(tag.id)}>Remove</button></li>
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
            </>}
        </>
    );
}
