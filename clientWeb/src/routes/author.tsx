import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { get_author } from "../api";

export function Author() {
    const [author, setAuthor] = useState<Author>(); 

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
        } catch (error) {
            console.error("Error loading author:", error);
        }
    }

    return (
        <div>
            &nbsp; {author?.firstname} {author?.lastname} 
        </div>
    );
}
