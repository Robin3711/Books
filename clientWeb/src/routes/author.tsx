import { useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { get_author } from "../api";

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

    return (
        <div>
            &nbsp; {author?.firstname} {author?.lastname} 
            {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
        </div>
        
    );
}
