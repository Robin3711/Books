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
    const[firstname, setFirstname] = useState("");
    const [pageSize, setPageSize] = useState(10);

    // loading 
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => { loadAuthors(); }, [currentPage, lastname,firstname]);
    useEffect(() => { setCurrentPage(1); }, [lastname, firstname]);    

    async function addAuthor(AuthorCreationData: AuthorCreationData) {
        setIsLoading(true);
        await add_author(AuthorCreationData);
        setIsLoading(false);
        loadAuthors();
    }

    async function removeAuthor(authorID: number) {
        setIsLoading(true);
        await remove_author(authorID);
        setIsLoading(false);
        loadAuthors();
    }   

    async function loadAuthors() {
        setIsLoading(true);
        const res = await get_authors({ page: currentPage, pageSize: pageSize, lastname, firstname });
        setIsLoading(false);
        setAuthors(res.authors);
        setTotalAuthors(res.totalCount);
    }

    async function handleAdd(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const form = e.currentTarget;
        const firstname = form.firstname.value;
        const lastname = form.lastname.value;
        try {       // try catch ici mais pas dans get ?
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
        const firstname = form.firstname.value;
        setLastname(lastname);
        setFirstname(firstname);
    }

    return (    
        <div id="container">
            <div id="sidebar">
                <form onSubmit={handleFilter}>
                    <input type="text" name="lastname" defaultValue={""} />
                    <input type="text" name="firstname" defaultValue={""} />
                    <button type="submit">Filtrer</button>
                </form>
                <Pagination page={currentPage} pageSize={pageSize} total={totalAuthors} onPageChange={setCurrentPage} /> 
                <form onSubmit={handleAdd}>
                    <input type="text" name="firstname" defaultValue={"prénom"} />
                    <input type="text" name="lastname" defaultValue={"nom de famille"} />
                    <button type="submit">Ajouter</button>
                </form>
                {!isLoading ? ( <ul>
                    {authors.map((author) => (
                        <li key={author.id}>
                            <NavLink to={author.id.toString()}>{author.firstname} {author.lastname} </NavLink>
                            <button className="small danger" onClick={() => handleRemove(author.id)}>X</button>
                        </li>
                    ))}
                </ul>) : (<p>Chargement...</p>)}
                {errorMessage !== "" && <p className="danger">{errorMessage}</p>}
            </div>
            <div id="info">
                <Outlet />
            </div>
        </div>
    )
}

















































