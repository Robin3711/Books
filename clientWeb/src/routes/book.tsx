import { useNavigate, useParams, NavLink } from "react-router-dom";
import { useState, useEffect, FormEvent } from "react";
import { get_book, get_author, remove_book, update_book, get_tags, remove_tag, add_tag } from "../api";
import { EditableText } from "../utils/editableText";


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
            </>}
        </>
    );
}
