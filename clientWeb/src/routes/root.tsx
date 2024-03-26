import React from 'react'
import { Link, NavLink, Outlet } from 'react-router-dom'
import '../index.css'

export default function Root() {
  return (<>
    <header>
      <h2><NavLink to="/">W42</NavLink></h2>
      <ul>
        <li> <NavLink to="/authors"> Authors</NavLink> </li>
        <li> <NavLink to="/books"> Books</NavLink> </li>
      </ul>
    </header>
    <main>
      <Outlet />
    </main>
    <footer>
      <em>Made with React & react-router</em>
    </footer>
  </>);
}
