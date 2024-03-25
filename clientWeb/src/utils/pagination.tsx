interface PaginationParams {
    page: number;
    pageSize: number;
    total: number;
    onPageChange: (page: number) => void;
}

export function Pagination(params: PaginationParams) {
    return (
        <div className="pagination">
            <button onClick={
                () => {
                    if (params.page > 1)
                    params.onPageChange(params.page - 1);
                }            
            } >&lt;-</button>
            <span>Page {params.page}</span>
            <button onClick={
                () => {
                    if (params.page < params.total / params.pageSize)
                    params.onPageChange(params.page + 1);
                }
            }>-&gt;</button>
        </div>
    )
}