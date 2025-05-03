import React from 'react';
import './App.css';
import {Navbar} from "./layout/NavbarAndFooter/Navbar";
import {Footer} from "./layout/NavbarAndFooter/Footer";
import {HomePage} from "./layout/HomePage/HomePage";
import {SearchBooksPage} from "./layout/SearchBookPage/SearchBooksPage";
import {Redirect, Route, Switch} from "react-router-dom";

//react is SPA (single page application)

export const  App = () => {
  return (
      <div className='d-flex flex-column min-vh-100'>
          <Navbar/>
          <div className='flex-grow-1'>
              <Switch>
                  <Route path='/' exact> {/*Empty route and empty route only*/}
                      <Redirect to='/home'/>
                  </Route>
                  <Route path='/home' >
                      <HomePage/>
                  </Route>
                  <Route path='/search' >
                      <SearchBooksPage/>
                  </Route>
              </Switch>
          </div>
          <Footer/>
      </div>
  );
}
