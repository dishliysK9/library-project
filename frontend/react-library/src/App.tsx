import React from 'react';
import './App.css';
import {Navbar} from "./layout/NavbarAndFooter/Navbar";
import {Footer} from "./layout/NavbarAndFooter/Footer";
import {HomePage} from "./layout/HomePage/HomePage";
import {SearchBooksPage} from "./layout/SearchBookPage/SearchBooksPage";

export const  App = () => {
  return (
      <div>
          <Navbar/>
          {/*<HomePage/>*/}
          <SearchBooksPage/>
          <Footer/>
      </div>
  );
}
