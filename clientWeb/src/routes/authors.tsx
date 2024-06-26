import { NavLink, Outlet, redirect } from 'react-router-dom';
import { add_author, get_authors, remove_author } from '../api';
import { FormEvent, useEffect, useState } from 'react';
import { Pagination } from '../utils/pagination';
import { useNavigate } from "react-router-dom";



export function Authors() {
    const [authors, setAuthors] = useState<Author[]>([]);   // list of authors

    const [errorMessage, setErrorMessage] = useState<string>(""); // error message

    const [currentPage, setCurrentPage] = useState(1); // pagination
    const [totalAuthors, setTotalAuthors] = useState(1);
    const [pageSize, /*setPageSize*/] = useState(10);

    const [lastname, setLastname] = useState(""); // filters
    const [firstname, setFirstname] = useState("");

    const [isLoading, setIsLoading] = useState(true); // loading

    // useEffects 
    useEffect(() => { loadAuthors(); }, [currentPage, lastname, firstname]);
    useEffect(() => { setCurrentPage(1); }, [lastname, firstname]);

    // <-- api functions -->

    async function addAuthor(AuthorCreationData: AuthorCreationData) {
        try {
            setIsLoading(true);
            await add_author(AuthorCreationData);
            setIsLoading(false);
        }
        catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
        loadAuthors();        
    }

    async function removeAuthor(authorID: number) {
        try {
            setIsLoading(true);
            await remove_author(authorID);
            setIsLoading(false);
        }
        catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
        loadAuthors();
    }

    async function loadAuthors() {
        try {
            setIsLoading(true);
            const res = await get_authors({ page: currentPage, pageSize: pageSize, lastname, firstname });
            setIsLoading(false);
            setAuthors(res.authors);
            setTotalAuthors(res.totalCount);
        }
        catch (error: any) {
            setErrorMessage(error.message);
            return;
        }
        setErrorMessage("");
    }

    //  <-- handler functions -->

    const navigate = useNavigate(); // navigation

    async function handleAdd(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const form = e.currentTarget;
        const firstname = form.firstname.value;
        const lastname = form.lastname.value;
        /*const auth = */await addAuthor({ firstname, lastname });
        //navigate("/authors/" + auth.id);
    }

    async function handleRemove(authorID: number) {
        await removeAuthor(authorID);
        navigate("/authors");   // ne pas naviguer si une erreur
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
            <form onSubmit={handleAdd}>
                    <input type="text" name="firstname" placeholder={"prénom"} />
                    <input type="text" name="lastname" placeholder={"nom de famille"} />
                    <button type="submit">Ajouter</button>
            </form>
            <hr/>
            <form onSubmit={handleFilter}>
                    <input type="text" name="firstname" defaultValue={""} placeholder={"prénom"} />
                    <input type="text" name="lastname" defaultValue={""} placeholder={"nom de famille"} />
                <button type="submit">Filtrer</button>
            </form>                
                <hr/>
                <Pagination page={currentPage} pageSize={pageSize} total={totalAuthors} onPageChange={setCurrentPage} />
                {!isLoading ? (<ul>
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

















































