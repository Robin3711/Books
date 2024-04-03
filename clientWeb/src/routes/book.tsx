import { useNavigate, useParams , NavLink} from "react-router-dom";
import { useState, useEffect } from "react";
import { get_book , get_author , remove_book, update_book,get_tags } from "../api";
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

    async function loadBook(id: number) {
        try {
            setIsLoading(true);
            const bookData = await get_book(id);
            setIsLoading(false);
            setbook(bookData);
            setAuthor(bookData.author);
        } catch (error : any) {
            setErrorMessage(error.message);
            return;
            
        }
        setErrorMessage("");
    }

    async function updateTitle(value: string) {
        if (bookID !== undefined) {
            await update_book(parseInt(bookID), { title: value });
            loadBook(parseInt(bookID));
        }
    }

    async function updatePublicationDate(value: string) {
        if (bookID !== undefined) {
            await update_book(parseInt(bookID), { publication_year: Number(value) });
            loadBook(parseInt(bookID));
        }
    }
        
    async function removeTag(tagName: string) {
        if (bookID !== undefined) {
            await update_book(parseInt(bookID), { tags: book?.tags?.filter(tag => tag.name !== tagName) });
            loadBook(parseInt(bookID));
        }
    }

    async function getTags() {
        try {
            const tags = await get_tags();
            setTags(tags);
        } catch (error : any) {
            setErrorMessage(error.message);
        }
    }


    async function handleAddTag() {
        const tag = document.getElementById("tag") as HTMLInputElement;
        if (bookID !== undefined) {
            //await update_book(parseInt(bookID), { tags: [...book?.tags ?? []}] });
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
                        return <li key={index}>{tag.name} <button onClick={() => removeTag(String(tag.name))}>Remove</button></li>
                    })}
                </ul>
                <form>
                    <input list="tags" name="tags" id="tag" onChange={getTags} />
                    <datalist id="tags">
                    {tags.map((tag, index) => (
                        <option value={tag?.name.toString()} key={index} />
                    ))}
                    </datalist>
                    <button onClick={handleAddTag}>AJOUTER</button>
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
