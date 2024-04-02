import { NavLink, Outlet } from 'react-router-dom';
import { get_books, remove_book } from '../api';
import { FormEvent, useEffect, useState } from 'react';
import { Pagination } from '../utils/pagination';
export default function Books() {
    const [books, setBooks] = useState<Book[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>("");
    const [currentPage, setCurrentPage] = useState(1);
    const [totalBooks, setTotalBooks] = useState(1);
    const [title, setTitle] = useState("");
    const [pageSize, setPageSize] = useState(10);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => { loadBooks(); }, [currentPage, title]);
    useEffect(() => { setCurrentPage(1); }, [title]);
    async function removeBooks(booksID: number) {
        setIsLoading(true);
        await remove_book(booksID);
        setIsLoading(false);
        loadBooks();
    }   
    async function loadBooks() {
        setIsLoading(true);
        const res = await get_books({ page: currentPage, pageSize: pageSize, title });
        setIsLoading(false);
        setBooks(res.books);
        setTotalBooks(res.totalCount);
    }
    async function handleRemove(booksID: number) {
        try {
            await removeBooks(booksID);
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
        const title = form.title_value.value;
        setTitle(title);
    }

    return (    
        <div id="container">
            <div id="sidebar">
                <form onSubmit={handleFilter}>
                    <input type="text" name="title_value" defaultValue={""} />
                    <button type="submit">Filtrer</button>
                </form>
                <Pagination page={currentPage} pageSize={pageSize} total={totalBooks} onPageChange={setCurrentPage} />
                {!isLoading ? ( <ul>
                    {books.map((book) => (
                        <li key={book.id}>
                            <NavLink to={book.id.toString()}>{book.title}</NavLink>
                            <button className="small danger" onClick={() => handleRemove(book.id)}>X</button>
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