import { NavLink, Outlet } from 'react-router-dom';
import { add_author, get_authors, remove_author } from '../api';
import { FormEvent, useEffect, useState } from 'react';
import { Pagination } from '../utils/pagination';

export function Authors() {
    const [authors, setAuthors] = useState<Author[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>("");
    const [currentPage, setCurrentPage] = useState(1);
    const [totalAuthors, setTotalAuthors] = useState(1);
    const [lastname, setLastname] = useState("");

    useEffect(() => { loadAuthors(); }, [currentPage, lastname]);
    useEffect(() => { setCurrentPage(1); }, [lastname]);    

    async function addAuthor(AuthorCreationData: AuthorCreationData) {
        await add_author(AuthorCreationData);
        loadAuthors();
    }

    async function removeAuthor(authorID: number) {
        await remove_author(authorID);
        loadAuthors();
    }

    async function loadAuthors() {
        const res = await get_authors({ page: currentPage, pageSize: 10, lastname});
        setAuthors(res.authors);
        setTotalAuthors(res.totalCount);
    }

    async function handleAdd(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const form = e.currentTarget;
        const firstname = form.firstname.value;
        const lastname = form.lastname.value;
        try {
            await addAuthor({ firstname, lastname });
        }
        catch (error : any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }

    async function handleRemove(authorID: number) {
        try {
            await removeAuthor(authorID);
        }
        catch (error : any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }

    async function handleFilter(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const form = e.currentTarget;
        const lastname = form.lastname.value;
        setLastname(lastname);
    }

    return (    
        <div id="container">
            <div id="sidebar">
                <form onSubmit={handleFilter}>
                    <input type="text" name="lastname" defaultValue={"nom de famille"} />
                    <button type="submit">Filtrer</button>
                </form>
                <Pagination page={currentPage} pageSize={10} total={totalAuthors} onPageChange={setCurrentPage} /> 
                <form onSubmit={handleAdd}>
                    <input type="text" name="firstname" defaultValue={"prénom"} />
                    <input type="text" name="lastname" defaultValue={"nom de famille"} />
                    <button type="submit">Ajouter</button>
                </form>
                <ul>
                    {authors.map((author) => (
                        <li>
                            <NavLink to={author.id.toString()}>{author.firstname} {author.lastname} </NavLink>
                            <button className="small danger" onClick={() => handleRemove(author.id)}>X</button>
                        </li>
                    ))}
                </ul>
                {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
            </div>
            <div id="info">
                <Outlet />
            </div>
        </div>
    )
}

















































