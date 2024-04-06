import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import { createBrowserRouter } from 'react-router-dom'
import { RouterProvider } from 'react-router-dom'
import { Navigate } from 'react-router-dom'
import Root from './routes/root'
import { Authors } from './routes/authors'
import Books from './routes/books'
import { Author } from './routes/author'
import { Book } from './routes/book'

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <Root />
    ),
    children: [
      {
        index: true,
        element: (
          <>
            veuillez sélectionner une section ci-dessus
          </>
        ),
      },
      {
        path: "authors",
        element: (
          <Authors />
        ),
        children: [
          {
            index: true,
            element: (
              <>
                &nbsp;
                "veuillez sélectionner un auteur"
              </>
            ),
          },
          {
            path: ":authorID",
            element: (
              <Author/>
            ),
          }
        ],
      },
      {
        path: "books",
        element: (
          <Books />
        ),
        children: [
          {
            index: true,
            element: (
              <>
                &nbsp;
                "veuillez sélectionner un livre"
              </>
            ),
          },
          {
            path: ":bookID",
            element: (
              <Book/>
            ),
          }
        ],
      },
    ],
  },
  {
    path: "/*",
    element: (
      <Navigate to="/" replace />
    ),
  },
]);


ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)
